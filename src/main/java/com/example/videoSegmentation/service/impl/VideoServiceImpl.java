package com.example.videoSegmentation.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.video.Segmentation.util.BaseExecuteException;
import com.example.videoSegmentation.dto.BaseExecution;
import com.example.videoSegmentation.entity.Video;
import com.example.videoSegmentation.enums.ExecuteStateEum;
import com.example.videoSegmentation.mapper.VideoMapper;
import com.example.videoSegmentation.service.VideoService;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

	@Autowired
	VideoMapper videoMapper;

	@Override
	public BaseExecution<Video> insertVideo(Video video, InputStream inputStream, String uploadPathStr)
			throws BaseExecuteException {
		try {
			// 保存视频
			Path uploadPath = Paths.get(uploadPathStr);
			if (!uploadPath.toFile().exists())
				uploadPath.toFile().mkdirs();
			Files.copy(inputStream, Paths.get(uploadPathStr).resolve(video.getVideoName()),
					StandardCopyOption.REPLACE_EXISTING);

			// 更新数据库
			int effctedNum = videoMapper.insert(video);
			if (effctedNum <= 0) {
				throw new BaseExecuteException("数据库插入失败");
			}
			return new BaseExecution<Video>(ExecuteStateEum.SUCCESS, video);
		} catch (BaseExecuteException e) {
			return new BaseExecution<Video>(e.getMessage());
		} catch (IOException e) {
			return new BaseExecution<Video>("文件上传失败");
		} catch (Exception e) {
			return new BaseExecution<Video>("未知错误");
		}
	}

	@Override
	public BaseExecution<Video> selectVideoById(Long videoId) {
		try {
			Video video = videoMapper.selectById(videoId);
			if (video == null || video.getVideoUrl() == null)
				throw new BaseExecuteException("视频不存在");
			return new BaseExecution<Video>(ExecuteStateEum.SUCCESS, video);
		} catch (BaseExecuteException e) {
			return new BaseExecution<Video>(e.getMessage());
		}catch (Exception e) {
			return new BaseExecution<Video>("未知错误");
		}
	}

	@Override
	public BaseExecution<Video> deleteVideoById(Long videoId) {
		try {
			//TODO:删除文件 删除子视频
			int effctedNum = videoMapper.deleteById(videoId);
			if (effctedNum <= 0) {
				throw new BaseExecuteException("删除失败");
			}
			return new BaseExecution<Video>(ExecuteStateEum.SUCCESS);
		} catch (BaseExecuteException e) {
			return new BaseExecution<Video>(e.getMessage());
		}catch (Exception e) {
			return new BaseExecution<Video>("未知错误");
		}
	}

}
