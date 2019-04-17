package com.video.stream.service;

import java.io.FileNotFoundException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IVideoService {
	public String storeFile(MultipartFile file) throws Exception;
	public Resource loadFileAsResource(String fileName) throws FileNotFoundException;
}
