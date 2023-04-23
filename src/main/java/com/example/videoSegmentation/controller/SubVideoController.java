package com.example.videoSegmentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.video.Segmentation.util.RBody;
import com.example.videoSegmentation.dto.BaseExecution;
import com.example.videoSegmentation.entity.SubVideo;
import com.example.videoSegmentation.enums.ExecuteStateEum;
import com.example.videoSegmentation.service.SubVideoService;


@RequestMapping("/subvideo")
@RestController
public class SubVideoController {

	@Autowired
	SubVideoService subVideoService;
	
	@GetMapping("/split")
	public String test() {
		return "cheng";
	}
	@GetMapping("/splitvideo")
	public RBody getSubVideo(@RequestParam Long videoId,@RequestParam Long sceneBoundaryId) {
		if(videoId==null||sceneBoundaryId==null||videoId <0 ||sceneBoundaryId<0)
			return RBody.error("输入有误");
		BaseExecution<SubVideo> be=subVideoService.extraSubVideo(videoId, sceneBoundaryId);
		if (be.getEum() == ExecuteStateEum.SUCCESS) {
			RBody rBody = RBody.ok(be.getEum().getStateInfo());
			rBody.put("subVideoList", be.getTList());
			return rBody;
		} else {
			return RBody.error(be.getStateInfo());
		}
	}
}
