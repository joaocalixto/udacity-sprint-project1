package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.data.Notes;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.exception.GeneralException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotesService {

    private final NotesMapper notesMapper;
    private final UserService userService;

    private Logger logger = LoggerFactory.getLogger(NotesService.class);

    public NotesService(NotesMapper notesMapper, UserService userService) {
        this.notesMapper = notesMapper;
        this.userService = userService;
    }

    public void insertNote(Notes notes) throws GeneralException {

        try {
            User userLoggedIn = userService.getUserLoggedIn();
            notes.setUserid(userLoggedIn.getUserId());

            notesMapper.insert(notes);
        }catch (Exception e){
            logger.error("Failed to insert Note", e);
            throw GeneralException.build();
        }
    }

    public void deleteNote(Integer noteId) throws GeneralException {
        try{
            notesMapper.delete(noteId);
        }catch (Exception e){
            logger.error("Failed to delete Note", e);
            throw GeneralException.build();
        }

    }

    public void updateNote(Notes note) throws GeneralException {
        try {
            notesMapper.update(note);
        }catch (Exception e){
            logger.error("Failed to update Note", e);
            throw GeneralException.build();
        }
    }

    public List<Notes> loadNotes() throws GeneralException {

        List<Notes> notesFromUser = new ArrayList<>();
        try {
            User userLoggedIn = userService.getUserLoggedIn();
            notesFromUser = notesMapper.getNotesFromUser(userLoggedIn.getUserId());
        }catch (Exception e){
            logger.error("Failed to update Note", e);
            throw GeneralException.build();
        }


        return notesFromUser;
    }
}
