package com.udacity.jwdnd.course1.cloudstorage.exception;

import static com.udacity.jwdnd.course1.cloudstorage.util.AppConstants.DEFAULT_ERROR_MESSAGE;

public class GeneralException extends Exception{

    public GeneralException(String message) {
        super(message);
    }

    public static GeneralException build(){
        return new GeneralException(DEFAULT_ERROR_MESSAGE);
    }
}
