package com.example.videoSegmentation.testPython;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.video.Segmentation.util.PathUtil;
import com.example.videoSegmentation.dto.BaseExecution;
import com.example.videoSegmentation.entity.Video;
import com.example.videoSegmentation.service.VideoService;

public class VideoSerivceTest extends BaseTest {
	@Autowired
	VideoService videoService;
	
	@Test
	
	public void insetTest() {
		File file =new File("F:/1/data/video/betest.mp4");
		if(!file.isFile()) {
			System.out.println("文件路劲错误");
			return ;
		}
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
		
			System.out.println("文件读取失败");
			return ;
		}
		String basePath = PathUtil.getBasePath();
		String uploadPathStr = basePath + "video/";
		System.out.println(file.getName());
		String s=file.getName();
		String postfix[] =s.split("\\.");
		
		String videoPrefix = "SC" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"."+postfix[postfix.length-1];
		Video video = new Video();
		video.setCreateDate(new Date());
		video.setVideoName(videoPrefix);
		video.setVideoUrl(uploadPathStr + videoPrefix);
		videoService.insertVideo(video,inputStream,uploadPathStr);
		System.out.println(video.getVideoUrl());
	}
	@Test
	@Ignore
	public void select() {
		BaseExecution<Video> v=videoService.selectVideoById(5L);
		
		System.out.println(v.getStateInfo());
	}
}
