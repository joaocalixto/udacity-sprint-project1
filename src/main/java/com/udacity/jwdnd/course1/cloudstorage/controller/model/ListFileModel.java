package com.udacity.jwdnd.course1.cloudstorage.controller.model;

import java.util.ArrayList;
import java.util.List;


public class ListFileModel{
        private String viewLink;
        private String deleteLink;

    public String getViewLink() {
        return viewLink;
    }

    public void setViewLink(String viewLink) {
        this.viewLink = viewLink;
    }

    public String getDeleteLink() {
        return deleteLink;
    }

    public void setDeleteLink(String deleteLink) {
        this.deleteLink = deleteLink;
    }
}