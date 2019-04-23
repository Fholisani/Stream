package com.video.stream.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.video.stream.config.VideoConfigurations;

@Service
public class VideoService implements IVideoService {


	@Autowired
	private VideoConfigurations configuration;


	


	// @Autowired
	// public VideoService(VideoConfigurations fileStorageProperties) throws
	// Exception {
	// this.fileStorageLocation = Paths.get(fileStorageProperties.getUploaddir())
	// .toAbsolutePath().normalize();
	//
	// try {
	// Files.createDirectories(this.fileStorageLocation);
	// } catch (Exception ex) {
	// throw new Exception("Could not create the directory where the uploaded files
	// will be stored.", ex);
	// }
	// }
	@Override
	public String storeFile(MultipartFile file) throws Exception {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path fileStorageLocation = Paths.get(configuration.getUploaddir()).toAbsolutePath().normalize();
		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new Exception("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			Path targetLocation = fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	@Override
	public Resource loadFileAsResource(String fileName) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Path fileStorageLocation = Paths.get(configuration.getUploaddir()).toAbsolutePath().normalize();
		try {
			Path filePath = fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new FileNotFoundException("File not found " + fileName);
		}
	}

}
