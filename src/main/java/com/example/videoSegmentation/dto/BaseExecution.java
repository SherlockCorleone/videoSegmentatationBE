package com.example.videoSegmentation.dto;

import java.util.List;

import com.example.videoSegmentation.enums.ExecuteStateEum;

import lombok.Data;

@Data
public class BaseExecution<T> {
	private String stateInfo;
	private ExecuteStateEum eum;
	private List<T> TList;
	private T temp;

	private Long count;

	public BaseExecution() {}
	public BaseExecution(String stateinfo) {
		stateInfo=stateinfo;
	}
	public BaseExecution(ExecuteStateEum eum) {
		this.eum=eum;
	}
	public BaseExecution(ExecuteStateEum eum,T t) {
		this.eum=eum;
		this.temp=t;
	}
	public BaseExecution(ExecuteStateEum eum,List<T>list) {
		this.eum=eum;
		this.TList=list;
	}
}
