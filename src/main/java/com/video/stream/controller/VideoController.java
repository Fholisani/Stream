package com.video.stream.controller;

import java.awt.PageAttributes.MediaType;

import com.video.stream.config.VideoConfigurations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.video.stream.model.MediaData;
import com.video.stream.service.VideoService;

@RestController
public class VideoController {
	
	private static final Logger logger = LoggerFactory.getLogger(VideoController.class);

	@Autowired
	private VideoService videoService;
	@Autowired
	private VideoConfigurations configuration;
	
	@RequestMapping(value="/upload", method= RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<MediaData> uploadFile(@RequestPart("mediadata") MediaData mediadata,
			@RequestPart("file") MultipartFile file) throws Exception {
		String fileName ;
		 try {
			 System.out.println(mediadata.toString());
			 boolean isAllowed = false;
			 logger.info("Allowed content type : " +configuration.getAllowedmimetypes().toString());
			 for(String allowedMimeType : configuration.getAllowedmimetypes()){
				 logger.info("Mime type  - "+ allowedMimeType + " - Compare : "+ file.getContentType() );
			 	if(allowedMimeType.equalsIgnoreCase(file.getContentType())){
			 		isAllowed = true;
			 		break;
				}

			 }
			 if(isAllowed){
				 fileName= videoService.storeFile(file);
				 logger.info("Successfully saved the file - "+ fileName);
			 }else{
				 return new ResponseEntity<MediaData>(mediadata,HttpStatus.UNSUPPORTED_MEDIA_TYPE);

			 }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception("Video upload : " + e);
		}
		return new ResponseEntity<MediaData>(mediadata,HttpStatus.OK);
	}

}
