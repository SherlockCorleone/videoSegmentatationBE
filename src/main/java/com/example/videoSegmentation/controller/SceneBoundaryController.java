package com.example.videoSegmentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.video.Segmentation.util.RBody;
import com.example.videoSegmentation.dto.BaseExecution;
import com.example.videoSegmentation.entity.SceneBoundary;
import com.example.videoSegmentation.enums.ExecuteStateEum;
import com.example.videoSegmentation.service.SceneBoundaryService;

@RestController
@RequestMapping("/sceneboundary")
public class SceneBoundaryController {
	@Autowired
	SceneBoundaryService sceneBoundaryService;
	
	@GetMapping("/getboundary")
	public RBody getBoundary(@RequestParam Integer extraShotType,@RequestParam Integer extraSceneType, @RequestParam Long videoId) {
		//判断输入
		if(extraShotType==null||extraShotType<=0||extraShotType>2||extraSceneType<=0||extraSceneType>2||extraShotType==null||videoId==null||videoId<=0) {
			return RBody.error("参数有误");
		}
		//调用服务层
		BaseExecution<SceneBoundary> be= sceneBoundaryService.extraSceneBoundary(extraSceneType, extraShotType, videoId);
		if (be.getEum() == ExecuteStateEum.SUCCESS) {
			RBody rBody = RBody.ok(be.getEum().getStateInfo());
			rBody.put("sceneBoundary", be.getTemp());
			return rBody;
		} else {
			return RBody.error(be.getStateInfo());
		}
	}
}
