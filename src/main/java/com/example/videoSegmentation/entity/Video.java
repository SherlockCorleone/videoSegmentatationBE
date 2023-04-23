package com.example.videoSegmentation.entity;


import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("video")
public class Video {
	@TableId(type = IdType.AUTO)
	private Long videoId;
	
	private String videoName;
	private String videoUrl;
	private String shotKeyfUrl;
	private Date createDate;
	public Video() {
		
	}
}
