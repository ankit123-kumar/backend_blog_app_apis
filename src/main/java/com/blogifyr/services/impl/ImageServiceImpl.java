package com.blogifyr.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogifyr.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		 String fileName = file.getOriginalFilename();
	
		 //file with random name generate
		   String randomId = UUID.randomUUID().toString();
		   String fileName1 =  randomId.concat(fileName.substring(fileName.lastIndexOf(".")));
		 
		 String filePath = path + File.separator+fileName1;
		 
		 //create folder if not created
		 File f = new File(path);
		 if(!f.exists()) {
			 f.mkdir();
		 }
		 
		 //file copy
		 
		 Files.copy(file.getInputStream(), Paths.get(filePath));
		   return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullpath = path+File.separator+fileName;
		InputStream is = new FileInputStream(fullpath);
		
		
		
		return is;
	}

}
