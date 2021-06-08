package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.exception.GeneralException;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.MessageService;
import com.udacity.jwdnd.course1.cloudstorage.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

import static com.udacity.jwdnd.course1.cloudstorage.util.AppConstants.*;

@Controller
public class CredentialsController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/home/credentials")
    public String uploadFile(@Valid Credentials credential, RedirectAttributes redirAttrs, Locale locale){

        try {
            if (credential.getCredentialid() != null) {
                credentialsService.updateCredentials(credential);
                redirAttrs.addFlashAttribute(SUCESS_MESSAGE_TAG, messageService.buildSuccess(locale, "updated", "Credentials"));
            } else {
                credentialsService.insertCredentials(credential);
                redirAttrs.addFlashAttribute(SUCESS_MESSAGE_TAG, messageService.buildSuccess(locale, "created", "Credentials"));
            }
        }catch (GeneralException e){
            redirAttrs.addAttribute(ERROR_MESSAGE_TAG, e.getMessage());
        }


        return "redirect:/home";
    }

    @GetMapping("/home/credentials/delete/{id}")
    public String deleteUser(@PathVariable("id") int id , RedirectAttributes redirAttrs, Locale locale) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        try {
            credentialsService.deleteCredentials(id);
            redirAttrs.addFlashAttribute(SUCESS_MESSAGE_TAG, messageService.buildSuccess(locale, "deleted", "Credentials"));
        }catch (GeneralException e){
            redirAttrs.addFlashAttribute(ERROR_MESSAGE_TAG, e.getMessage());
        }
        return "redirect:/home";
    }
}
