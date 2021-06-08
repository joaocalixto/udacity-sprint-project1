package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.config.StorageProperties;
import com.udacity.jwdnd.course1.cloudstorage.controller.FilesController;
import com.udacity.jwdnd.course1.cloudstorage.controller.HomeController;
import com.udacity.jwdnd.course1.cloudstorage.controller.model.ListFileModel;
import com.udacity.jwdnd.course1.cloudstorage.exception.GeneralException;
import com.udacity.jwdnd.course1.cloudstorage.util.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.udacity.jwdnd.course1.cloudstorage.util.AppConstants.DEFAULT_ERROR_MESSAGE;

@Service
public class FileUploadService {

    private final Path rootLocation;
    private Logger logger = LoggerFactory.getLogger(FileUploadService.class);

    @Autowired
    private UserService userService;

    @Autowired
    public FileUploadService(StorageProperties properties, UserService userService) {

        this.userService = userService;
        this.rootLocation = Paths.get(properties.getLocation());
    }

    public void uploadFile(MultipartFile file) throws GeneralException {

        // folderPath here is /sismed/temp/exames
        String folderPath = "files";
        String filePath = folderPath + File.separator + userService.getUserLoggedIn().getUsername()+ File.separator+ file.getOriginalFilename();

        // Copies Spring's multipartfile inputStream to /sismed/temp/exames (absolute path)
        try {
            if(Files.exists(Paths.get(filePath))){
                throw new GeneralException("Sorry, you can't upload a file with the same name. :( ");
            }

            Files.copy(file.getInputStream(), Paths.get(filePath));
        } catch (IOException e) {
            logger.error("Failed to insert file", e);
            throw new GeneralException(DEFAULT_ERROR_MESSAGE);
        }
    }
    public Stream<Path> loadAll() {
        try {
            Path path1 = Paths.get("files" + File.separator + userService.getUserLoggedIn().getUsername());

            if(!Files.exists(path1)){
                Files.createDirectory(path1);
            }
//            return Files.walk(path1)
//                    .filter(path -> !path.equals(path1))
//                    .map(path -> path1.relativize(path));
            return Files.walk(path1)
                    .filter(path -> !path.equals(path1));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read stored files", e);
        }

    }

    public  List<ListFileModel> loadFiles() throws GeneralException {
        List<ListFileModel> files = new ArrayList<>();

        try {
            files = loadAll().map(
                    path -> {
                        ListFileModel fileModel = new ListFileModel();
                        String serveFile = MvcUriComponentsBuilder.fromMethodName(FilesController.class,
                                "serveFile", path.getFileName().toString(), "").build().toUri().toString();
                        fileModel.setViewLink(serveFile);

                        String serveFileDelete = MvcUriComponentsBuilder.fromMethodName(FilesController.class,
                                "deleteFile", path.getFileName().toString(), "", "").build().toUri().toString();
                        fileModel.setViewLink(serveFile);
                        fileModel.setDeleteLink(serveFileDelete);

                        return fileModel;

                    }).collect(Collectors.toList());
        }catch (Exception e ){
            logger.error("Erro on load Files", e);
            throw new GeneralException(DEFAULT_ERROR_MESSAGE);
        }
        return files;
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public Resource loadAsResource(String filename) throws GeneralException {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new RuntimeException("Could not read file: " + filename);

            }
        } catch (Exception e) {
            logger.error("Could not read file: " + filename, e);
            throw new GeneralException(DEFAULT_ERROR_MESSAGE);
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void deleteFile(String filename) throws GeneralException {
        Path file = load(filename);
        try {
            Files.delete(file);
        } catch (IOException e) {
            logger.error("Could not read file: " + filename, e);
            throw new GeneralException(DEFAULT_ERROR_MESSAGE);
        }
    }

    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage", e);
        }
    }
}
