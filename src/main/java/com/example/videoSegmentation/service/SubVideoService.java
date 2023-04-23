package com.example.videoSegmentation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.videoSegmentation.dto.BaseExecution;
import com.example.videoSegmentation.entity.SubVideo;

public interface SubVideoService extends IService<SubVideo>{
	BaseExecution<SubVideo> extraSubVideo(Long videoId,Long sceneBoundaryId);
	BaseExecution<SubVideo> selectSubVideoList(Long videoId,Long sceneBoundaryId);
}
