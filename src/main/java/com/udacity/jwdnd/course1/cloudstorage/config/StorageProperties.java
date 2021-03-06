package com.udacity.jwdnd.course1.cloudstorage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String location = "files";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}