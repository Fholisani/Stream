package com.video.stream.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("video")
public class VideoConfigurations {

	private String uploaddir;

	public String getUploaddir() {
		return uploaddir;
	}

	public void setUploaddir(String uploaddir) {
		this.uploaddir = uploaddir;
	}


}
