package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.data.Notes;
import com.udacity.jwdnd.course1.cloudstorage.exception.GeneralException;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileUploadService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.udacity.jwdnd.course1.cloudstorage.util.AppConstants.ERROR_MESSAGE_TAG;

@Controller
@RequestMapping
public class HomeController {


    @Autowired
    private FileUploadService storageService;

    @Autowired
    private NotesService notesService;

    @Autowired
    private CredentialsService credentialsService;

    @GetMapping("/home")
    public String root(@ModelAttribute("note") Notes note,
                       @ModelAttribute("credential") Credentials credential,
                       @ModelAttribute("generalMessageError") String erroMessage,
                       RedirectAttributes redirAttrs, Model model) throws GeneralException {


        return home(note,credential,erroMessage,redirAttrs, model);
    }

    @GetMapping("/")
    public String init(@ModelAttribute("note") Notes note,
                       @ModelAttribute("credential") Credentials credential,
                       @ModelAttribute("generalMessageError") String erroMessage,
                       RedirectAttributes redirAttrs, Model model) throws GeneralException {


        return home(note,credential,erroMessage,redirAttrs, model);
    }

    private String home(@ModelAttribute("note") Notes note,
                 @ModelAttribute("credential") Credentials credential,
                 @ModelAttribute("generalMessageError") String erroMessage,
                 RedirectAttributes redirAttrs, Model model){
        try{
            model.addAttribute("files", storageService.loadFiles());
            model.addAttribute("notes", notesService.loadNotes());
            model.addAttribute("credentials", credentialsService.loadCredentials());

        }catch (GeneralException e){
            model.addAttribute(ERROR_MESSAGE_TAG, e.getMessage());
            return "home";
        }


        return "home";
    }

}
