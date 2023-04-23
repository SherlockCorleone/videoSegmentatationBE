package com.example.videoSegmentation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("sub_video")
public class SubVideo {
	@TableId(type = IdType.AUTO)
	private Long subVideoId;
	
	private String subVideoName;
	private Long parentVideoId;
	private String subVideoUrl;
	private Long sceneBoundaryId;
	
	@TableField(exist = false)
	private Integer sceneBoundaryType;
	@TableField(exist = false)
	private Integer shotBoundaryType;
	
	public SubVideo(String svn,Long pvi,String svu,Long sbi) {
		subVideoName=svn;
		parentVideoId=pvi;
		subVideoUrl=svu;
		sceneBoundaryId=sbi;
	}
	
}
