package com.example.videoSegmentation.testPython;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.videoSegmentation.dto.BaseExecution;
import com.example.videoSegmentation.dto.SceneBoundaryDto;
import com.example.videoSegmentation.entity.SceneBoundary;
import com.example.videoSegmentation.service.SceneBoundaryService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;


public class SceneBoundaryTest extends BaseTest {
	@Autowired
	SceneBoundaryService sceneBoundaryService;
	
	@Test
	@Ignore
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
	@Test
	public void jsonTest() {
		BaseExecution<SceneBoundary> be =sceneBoundaryService.extraSceneBoundary(1, 2, 11L);
		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(be.getTemp().getSceneBoundaryUrl()));
			Gson gson=new Gson();
			List<SceneBoundaryDto> list=gson.fromJson(reader, new TypeToken<List<SceneBoundaryDto>>(){}.getType());
			System.out.print(list);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
