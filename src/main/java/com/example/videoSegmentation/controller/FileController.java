package com.example.videoSegmentation.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.video.Segmentation.util.PathUtil;

@RestController 	
public class FileController {
	
	String uploadPathStr = PathUtil.getBasePath() + "/video";
	public FileController() {}
	@GetMapping("/test")
	public String test() {
		return "test";
	}
	@PostMapping("/upload")
	public @ResponseBody boolean upload(@RequestParam MultipartFile file, @RequestParam String filename) {
		if (file == null || file.isEmpty() || filename == null || filename.isEmpty())
			return false;
		
		try (InputStream inputStream = file.getInputStream()) {
			Path uploadPath = Paths.get(uploadPathStr);
			if (!uploadPath.toFile().exists())
				uploadPath.toFile().mkdirs();
			Files.copy(inputStream, Paths.get(uploadPathStr).resolve(filename), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("upload file , filename is " + filename);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@PostMapping("/download")
	public ResponseEntity<FileSystemResource> download(@RequestParam String filename) {
		if (filename == null || filename.isEmpty())
			return null;
		File file = Paths.get(uploadPathStr).resolve(filename).toFile();
		if (file.exists() && file.canRead()) {
			System.out.println("download file , filename is " + filename);
			return ResponseEntity.ok()
					.contentType(file.getName().contains(".jpg") ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG)
					.body(new FileSystemResource(file));
		} else
			return null;
	}
}