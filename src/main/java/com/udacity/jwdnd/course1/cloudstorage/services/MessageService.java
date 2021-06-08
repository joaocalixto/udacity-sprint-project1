package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static com.udacity.jwdnd.course1.cloudstorage.util.AppConstants.DEFAULT_SUCCESS_MESSAGE;

@Service
public class MessageService {

    @Autowired
    private MessageSource messageSource;

    public String buildSuccess(Locale locale, String... args){
        return messageSource.getMessage(DEFAULT_SUCCESS_MESSAGE, args, locale);
    }

    public String getMessage(Locale locale, String key, String... args){
        return messageSource.getMessage(key, args, locale);
    }
}
