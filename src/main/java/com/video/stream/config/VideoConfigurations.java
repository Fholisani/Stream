package com.video.stream.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("video")
public class VideoConfigurations {

	private String uploaddir;
	private String[] allowedmimetypes;

	public String getUploaddir() {
		return uploaddir;
	}

	public void setUploaddir(String uploaddir) {
		this.uploaddir = uploaddir;
	}

	public String[] getAllowedmimetypes() {
		return allowedmimetypes;
	}

	public void setAllowedmimetypes(String[] allowedmimetypes) {
		this.allowedmimetypes = allowedmimetypes;
	}
}

