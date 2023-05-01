package com.example.videoSegmentation.controller;

import java.io.FileReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.video.Segmentation.util.RBody;
import com.example.videoSegmentation.dto.BaseExecution;
import com.example.videoSegmentation.dto.SceneBoundaryDto;
import com.example.videoSegmentation.entity.SceneBoundary;
import com.example.videoSegmentation.enums.ExecuteStateEum;
import com.example.videoSegmentation.service.SceneBoundaryService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
//@CrossOrigin(origins = {"*","null"}) //用于跨域请求，*代表允许响应所有的跨域请求s
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
			try {
				JsonReader reader;
				RBody rBody = RBody.ok(be.getEum().getStateInfo());
				rBody.put("sceneBoundary", be.getTemp());
				reader = new JsonReader(new FileReader(be.getTemp().getSceneBoundaryUrl()));
				Gson gson=new Gson();
				List<SceneBoundaryDto> list=gson.fromJson(reader, new TypeToken<List<SceneBoundaryDto>>(){}.getType());
				rBody.put("data",list);
				return rBody;
			} catch (Exception e) {
				return RBody.error("文件读取失败");
			}
		} else {
			return RBody.error(be.getStateInfo());
		}
	}
}
