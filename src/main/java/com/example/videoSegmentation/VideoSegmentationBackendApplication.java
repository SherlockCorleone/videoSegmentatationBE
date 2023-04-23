package com.example.videoSegmentation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.videoSegmentation.mapper")
public class VideoSegmentationBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoSegmentationBackendApplication.class, args);
	}

}
