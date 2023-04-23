package com.example.videoSegmentation.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("scene_boundary")
public class SceneBoundary {
	public SceneBoundary() {
		// TODO Auto-generated constructor stub
	}
	@TableId(type = IdType.AUTO)
	Long sceneBoundaryId;
	
	String sceneBoundaryUrl;
	Integer sceneBoundaryType;
	Integer shotBoundaryType;
	Long videoId;
	Date createDate;
}
