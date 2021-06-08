package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.Notes;
import com.udacity.jwdnd.course1.cloudstorage.exception.GeneralException;
import com.udacity.jwdnd.course1.cloudstorage.services.MessageService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

import static com.udacity.jwdnd.course1.cloudstorage.util.AppConstants.ERROR_MESSAGE_TAG;
import static com.udacity.jwdnd.course1.cloudstorage.util.AppConstants.SUCESS_MESSAGE_TAG;

@Controller
public class NotesController {

    @Autowired
    private NotesService notesService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/home/notes")
    public String updateNote(Notes note, RedirectAttributes redirAttrs, Locale locale){

        try {
            if (note.getNoteid() != null) {
                notesService.updateNote(note);
                redirAttrs.addFlashAttribute(SUCESS_MESSAGE_TAG, messageService.buildSuccess(locale, "updated", "Note"));
            } else {
                notesService.insertNote(note);
                redirAttrs.addFlashAttribute(SUCESS_MESSAGE_TAG, messageService.buildSuccess(locale, "created", "Note"));
            }
        }catch (GeneralException e){
            redirAttrs.addAttribute(ERROR_MESSAGE_TAG, e.getMessage());
        }

        return "redirect:/home";
    }

    @GetMapping("/home/notes/delete/{id}")
    public String deleteNote(@PathVariable("id") int id, RedirectAttributes redirAttrs, Locale locale) {
        try {
            notesService.deleteNote(id);
            redirAttrs.addFlashAttribute(SUCESS_MESSAGE_TAG, messageService.buildSuccess(locale, "deleted", "Note"));
        }catch (Exception e){
            redirAttrs.addAttribute(ERROR_MESSAGE_TAG, e.getMessage());
        }
        return "redirect:/home";
    }
}
