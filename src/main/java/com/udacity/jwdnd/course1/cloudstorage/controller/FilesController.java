package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.exception.GeneralException;
import com.udacity.jwdnd.course1.cloudstorage.services.FileUploadService;
import com.udacity.jwdnd.course1.cloudstorage.services.MessageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.Locale;

import static com.udacity.jwdnd.course1.cloudstorage.util.AppConstants.*;

@Controller
public class FilesController {

    @Autowired
    private FileUploadService storageService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;


    @GetMapping("/home/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename, RedirectAttributes redirAttrs) {
        try{

            Resource file = storageService.loadAsResource(userService.getUserLoggedIn().getUsername()+ File.separator+ filename);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        }catch (GeneralException e){
            redirAttrs.addFlashAttribute(ERROR_MESSAGE_TAG, e.getMessage());
        }
        return null;
    }

    @GetMapping("/home/delete/files/{filename:.+}")
    public String deleteFile(@PathVariable String filename, RedirectAttributes redirAttrs, Locale locale) {

        try {
            storageService.deleteFile(filename);
            redirAttrs.addFlashAttribute(SUCESS_MESSAGE_TAG, messageService.buildSuccess(locale, "deleted", "File"));
        }catch (GeneralException e){
            redirAttrs.addFlashAttribute(ERROR_MESSAGE_TAG, e.getMessage());
        }

        return "redirect:/home";
    }

    @PostMapping("/home/file")
    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirAttrs, Locale locale){

        if(file.getSize() == 0){
            redirAttrs.addFlashAttribute(ERROR_MESSAGE_TAG, messageService.getMessage(locale, "invalid_file"));
            return "redirect:/home";
        }
        try {
            storageService.uploadFile(file);
            redirAttrs.addFlashAttribute(SUCESS_MESSAGE_TAG, messageService.buildSuccess(locale, "uploaded", "File"));
        }catch (GeneralException e){
            redirAttrs.addFlashAttribute(ERROR_MESSAGE_TAG, e.getMessage());
        }

        return "redirect:/home";
    }
}
