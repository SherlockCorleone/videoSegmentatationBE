package com.example.videoSegmentation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("shot_keyf")
public class ShotKeyf {
	@TableId(type = IdType.AUTO)
	Long shotKeyfId;
	String shotKeyUrl;
	Integer shotKeyType;
	
	Long videoId;
}
