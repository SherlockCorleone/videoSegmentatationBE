package com.example.videoSegmentation.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.video.Segmentation.util.CommandUtil;
import com.example.video.Segmentation.util.BaseExecuteException;
import com.example.video.Segmentation.util.PathUtil;
import com.example.videoSegmentation.dto.BaseExecution;
import com.example.videoSegmentation.entity.SceneBoundary;
import com.example.videoSegmentation.entity.Video;
import com.example.videoSegmentation.enums.ExecuteStateEum;
import com.example.videoSegmentation.mapper.SceneBoundaryMapper;
import com.example.videoSegmentation.mapper.VideoMapper;
import com.example.videoSegmentation.service.SceneBoundaryService;

@Service
public class SceneBoundaryServiceImpl extends ServiceImpl<SceneBoundaryMapper, SceneBoundary>
		implements SceneBoundaryService {
	@Autowired
	VideoMapper videoMapper;

	@Autowired
	SceneBoundaryMapper sceneBoundaryMapper;

	@Value("${python.scrl}")
	String scrl;

	@Value("${python.bassl}")
	String bassl;

	@Value("${python.cmd}")
	String cmd;

	@Override
	public BaseExecution<SceneBoundary> extraSceneBoundary(Integer extraSceneTpye, Integer extraShotTye, Long videoId) {
		try {
			// 查找视频
			Video video = videoMapper.selectById(videoId);
			if (video == null || video.getVideoUrl() == null) {
				throw new BaseExecuteException("视频不存在");
			}

			// 检查是否提取过
			QueryWrapper<SceneBoundary> q = new QueryWrapper<SceneBoundary>();
			q.eq("video_id", videoId);
			q.eq("scene_boundary_type", extraSceneTpye);
			q.eq("shot_boundary_type", extraShotTye);
			SceneBoundary s = sceneBoundaryMapper.selectOne(q);
			if (s != null)
				return new BaseExecution<SceneBoundary>(ExecuteStateEum.SUCCESS, s);

			// 提取视频边界
			String command;
			if (extraSceneTpye == 1)
				command = cmd + " " + bassl +" config.video_path="+video.getVideoUrl()+" config.getkeyf="+extraShotTye;
			else
				command = cmd + " " + scrl+" --video_path " + video.getVideoUrl() + " --getkeyf " + extraShotTye;		
			Process p = Runtime.getRuntime().exec(command);
			CommandUtil c1=new CommandUtil(p.getInputStream(),"INFO"); 
			c1.start();
			CommandUtil c2=new CommandUtil(p.getErrorStream(),"ERROR");
			c2.start();
			int retCode=p.waitFor();
			if(retCode != 0){
				throw new BaseExecuteException("提取视频边界失败");
		    }
			c1.interrupt();
			c2.interrupt();
			String boundaryPath = PathUtil.getBasePath();
			if (extraSceneTpye == 1)
				boundaryPath += "shot_bound/BaSSL";
			else
				boundaryPath += "shot_bound/SCRL";
			if (extraShotTye == 1)
				boundaryPath += "/traditional/" + video.getVideoName().split("\\.")[0] + ".ndjson";
			else
				boundaryPath += "/TransNetV2/" + video.getVideoName().split("\\.")[0] + ".ndjson";
			File file = new File(boundaryPath);
			if (!file.exists() || file.isDirectory()) {
				throw new BaseExecuteException("提取视频边界失败");
			}
			// 更新数据库
			SceneBoundary sceneBoundary = new SceneBoundary();
			sceneBoundary.setCreateDate(new Date());
			sceneBoundary.setSceneBoundaryType(extraSceneTpye);
			sceneBoundary.setSceneBoundaryUrl(boundaryPath);
			sceneBoundary.setShotBoundaryType(extraShotTye);
			sceneBoundary.setVideoId(videoId);

			int effnum = sceneBoundaryMapper.insert(sceneBoundary);
			if (effnum < 0)
				throw new BaseExecuteException("数据操作失败");
			return new BaseExecution<SceneBoundary>(ExecuteStateEum.SUCCESS, sceneBoundary);
		} catch (BaseExecuteException e) {
			return new BaseExecution<SceneBoundary>(e.getMessage());
		} catch (Exception e) {
			return new BaseExecution<SceneBoundary>(e.getMessage());
		}

	}
	@Override
	public BaseExecution<SceneBoundary> selectSceneBoundary(Integer extraSceneTpye, Integer extraShotTye,
			Long videoId) {
		try {
			QueryWrapper<SceneBoundary> q = new QueryWrapper<SceneBoundary>();
			if (extraSceneTpye != null)
				q.eq("scene_boundary_type", extraSceneTpye);
			if (extraShotTye != null)
				q.eq("shot_boundary_type", extraShotTye);
			List<SceneBoundary> sceneBoundaryList = sceneBoundaryMapper.selectList(q);
			return new BaseExecution<SceneBoundary>(ExecuteStateEum.SUCCESS, sceneBoundaryList);
		} catch (Exception e) {
			return new BaseExecution<SceneBoundary>("未知错误");
		}
	}

}
