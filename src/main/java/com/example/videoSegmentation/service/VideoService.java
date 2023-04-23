package com.example.videoSegmentation.service;

import java.io.InputStream;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.video.Segmentation.util.BaseExecuteException;
import com.example.videoSegmentation.dto.BaseExecution;
import com.example.videoSegmentation.entity.Video;

public interface VideoService extends IService<Video>{
	BaseExecution<Video> insertVideo(Video video,InputStream inputStream,String uploadPathStr) throws BaseExecuteException;
	BaseExecution<Video> selectVideoById(Long videoId);
	BaseExecution<Video> deleteVideoById(Long videoId);
}
