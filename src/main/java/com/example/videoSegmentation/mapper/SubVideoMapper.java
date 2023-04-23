package com.example.videoSegmentation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.videoSegmentation.entity.SubVideo;


public interface SubVideoMapper extends BaseMapper<SubVideo> {
	@Select("SELECT sub_video.*,scene_boundary.`scene_boundary_type`,scene_boundary.`shot_boundary_type` "+
            "FROM scene_boundary,sub_video "+
            "${ew.customSqlSegment} and scene_boundary.scene_boundary_id=sub_video.scene_boundary_id "+
            "ORDER BY sub_video_id")
	List<SubVideo> selectSubVideoDetail(@Param("ew")QueryWrapper<SubVideo> wapper);
}
