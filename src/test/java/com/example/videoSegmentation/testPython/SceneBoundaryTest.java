package com.example.videoSegmentation.testPython;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.videoSegmentation.dto.BaseExecution;
import com.example.videoSegmentation.entity.SceneBoundary;
import com.example.videoSegmentation.service.SceneBoundaryService;

public class SceneBoundaryTest extends BaseTest {
	@Autowired
	SceneBoundaryService sceneBoundaryService;
	
	@Test
	public void extra() {
		BaseExecution<SceneBoundary> be =sceneBoundaryService.extraSceneBoundary(2, 1, 9L);
//		System.out.println(be.getStateInfo());
		System.out.println(be.getTemp().getSceneBoundaryId());
		System.out.println(be.getTemp().getSceneBoundaryUrl());
		System.out.println(be.getTemp().getSceneBoundaryType());
		System.out.println(be.getTemp().getShotBoundaryType());
		System.out.println(be.getTemp().getVideoId());
		System.out.println(be.getTemp().getCreateDate());
	}
}
