package com.example.videoSegmentation.dto;

public class SceneBoundaryDto {
	String video_id;
	Long shot_id;
	Integer bound_label;
	String start_time;
	Integer start_frame;
	String end_time;
	Integer end_frame;
	public String getVideo_id() {
		return video_id;
	}
	public void setVideo_id(String video_id) {
		this.video_id = video_id;
	}
	public Long getShot_id() {
		return shot_id;
	}
	public void setShot_id(Long shot_id) {
		this.shot_id = shot_id;
	}
	public Integer getBound_label() {
		return bound_label;
	}
	public void setBound_label(Integer bound_label) {
		this.bound_label = bound_label;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public Integer getStart_frame() {
		return start_frame;
	}
	public void setStart_frame(Integer start_frame) {
		this.start_frame = start_frame;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public Integer getEnd_frame() {
		return end_frame;
	}
	public void setEnd_frame(Integer end_frame) {
		this.end_frame = end_frame;
	}
}
