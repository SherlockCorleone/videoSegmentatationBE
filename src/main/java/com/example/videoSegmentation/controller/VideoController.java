package com.example.videoSegmentation.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.video.Segmentation.util.PathUtil;
import com.example.video.Segmentation.util.RBody;
import com.example.videoSegmentation.dto.BaseExecution;
import com.example.videoSegmentation.entity.Video;
import com.example.videoSegmentation.enums.ExecuteStateEum;
import com.example.videoSegmentation.service.VideoService;

//@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求s
@RequestMapping("/video")
@RestController
public class VideoController {
	@Autowired
	VideoService videoService;

	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

	@PostMapping("/upload")
	@ResponseBody
	public RBody upload(@RequestParam(name="video") MultipartFile file, @RequestParam String filename) {
		// 判断文件
		if (file == null || file.isEmpty() || filename == null || filename.isEmpty())
			return RBody.error("文件不存在");

		InputStream inputStream;
		try {
			inputStream = file.getInputStream();
		} catch (IOException e) {
			return RBody.error("文件接收失败");
		}
		// 生成文件名
		String postfix[] =filename.split("\\.");
		String videoPrefix = "SC" + format.format(new Date())+"."+postfix[postfix.length-1];
		// 生成保存路径
		String basePath = PathUtil.getBasePath();
		String uploadPathStr = basePath + "video/";
		// 调用服务层
		Video video = new Video();
		video.setCreateDate(new Date());
		video.setVideoName(videoPrefix);
		video.setVideoUrl(uploadPathStr + videoPrefix);
		BaseExecution<Video> be = videoService.insertVideo(video, inputStream, uploadPathStr);
		if (be.getEum() == ExecuteStateEum.SUCCESS) {
			RBody rBody = RBody.ok(be.getEum().getStateInfo());
			rBody.put("video", be.getTemp());
			return rBody;
		} else {
			return RBody.error(be.getStateInfo());
		}
	}

	@GetMapping("/geturl")
	@ResponseBody
	public RBody download(@RequestParam Long videoId) {
		// 判断输入
		if (videoId == null || videoId < 0)
			return RBody.error("文件不存在");
		// 调用服务层
		BaseExecution<Video> be = videoService.selectVideoById(videoId);
		if (be.getEum() == ExecuteStateEum.SUCCESS) {
			RBody rBody = RBody.ok(be.getEum().getStateInfo());
			rBody.put("videoId", be.getTemp());
			return rBody;
		} else {
			return RBody.error(be.getStateInfo());
		}
	}

	@GetMapping("/delete")
	@ResponseBody
	public RBody delete(@RequestParam Long videoId) {
		// 判断输入
		if (videoId == null || videoId < 0)
			return RBody.error("文件不存在");
		// 调用服务层
		BaseExecution<Video> be = videoService.deleteVideoById(videoId);
		if (be.getEum() == ExecuteStateEum.SUCCESS) {
			RBody rBody = RBody.ok(be.getEum().getStateInfo());
			return rBody;
		} else {
			return RBody.error(be.getStateInfo());
		}
	}

	@PostMapping("/getsceneboundary")
	@RequestMapping
	public RBody getSceneBoundary(@RequestParam Long videoId) {
		// 判断输入
		if (videoId == null || videoId < 0)
			return RBody.error("文件不存在");
		return null;
	}
}
