package com.example.videoSegmentation.testPython;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.videoSegmentation.dto.BaseExecution;
import com.example.videoSegmentation.entity.SubVideo;
import com.example.videoSegmentation.service.SubVideoService;

public class SubVideoSerivceTest extends BaseTest{
	@Autowired
	SubVideoService subVideoService;
	
	@Test
	public void splitVideo() {
		BaseExecution<SubVideo> be=subVideoService.extraSubVideo(9L,6L);
		System.out.println(be.getStateInfo());
	}
}
