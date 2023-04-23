package com.example.videoSegmentation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.videoSegmentation.dto.BaseExecution;
import com.example.videoSegmentation.entity.SceneBoundary;

public interface SceneBoundaryService extends IService<SceneBoundary>{
	BaseExecution<SceneBoundary> extraSceneBoundary(Integer extraSceneTpye,Integer extraShotTye,Long videoId);
	BaseExecution<SceneBoundary> selectSceneBoundary(Integer extraSceneTpye,Integer extraShotTye,Long videoId);
}
