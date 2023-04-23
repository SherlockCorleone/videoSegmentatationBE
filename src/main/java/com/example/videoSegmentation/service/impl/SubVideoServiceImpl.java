package com.example.videoSegmentation.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.video.Segmentation.util.BaseExecuteException;
import com.example.video.Segmentation.util.CommandUtil;
import com.example.video.Segmentation.util.PathUtil;
import com.example.videoSegmentation.dto.BaseExecution;
import com.example.videoSegmentation.entity.SceneBoundary;
import com.example.videoSegmentation.entity.SubVideo;
import com.example.videoSegmentation.entity.Video;
import com.example.videoSegmentation.enums.ExecuteStateEum;
import com.example.videoSegmentation.mapper.SceneBoundaryMapper;
import com.example.videoSegmentation.mapper.SubVideoMapper;
import com.example.videoSegmentation.mapper.VideoMapper;
import com.example.videoSegmentation.service.SubVideoService;

@Service
public class SubVideoServiceImpl extends ServiceImpl<SubVideoMapper, SubVideo> implements SubVideoService {

	@Autowired
	VideoMapper videoMapper;

	@Autowired
	SceneBoundaryMapper sceneBoundaryMapper;
	
	@Autowired
	SubVideoMapper subVideoMapper;

	@Value("${python.cmd}")
	String cmd;

	@Value("${python.video_split}")
	String videoSplit;

	@Override
	@Transactional
	public BaseExecution<SubVideo> extraSubVideo(Long videoId, Long sceneBoundaryId) {
		try {
			// 查找视频
			Video video = videoMapper.selectById(videoId);
			if (video == null || video.getVideoUrl() == null) {
				throw new BaseExecuteException("视频不存在");
			}
			// 检查边界是否存在
			QueryWrapper<SceneBoundary> q1 = new QueryWrapper<SceneBoundary>();
			q1.eq("video_id", videoId);
			q1.eq("scene_boundary_id", sceneBoundaryId);
			SceneBoundary sceneBoundary = sceneBoundaryMapper.selectOne(q1);
			if (sceneBoundary == null || sceneBoundary.getSceneBoundaryUrl() == null) {
				throw new BaseExecuteException("视频和边界不匹配");
			}
			// 检查是否分割过
			QueryWrapper<SubVideo> q = new QueryWrapper<SubVideo>();
			q.eq("parent_video_id", videoId);
			q.eq("scene_boundary.scene_boundary_id", sceneBoundaryId);
			List<SubVideo> subvideoList = subVideoMapper.selectSubVideoDetail(q);
			if (subvideoList != null && subvideoList.size() > 0)
				return new BaseExecution<SubVideo>(ExecuteStateEum.SUCCESS, subvideoList);

			// 分割视频
			String command = cmd + " " + videoSplit + " --video_path " + video.getVideoUrl() + " --shot_bound "
					+ sceneBoundary.getSceneBoundaryUrl();
			
			Process p = Runtime.getRuntime().exec(command);
			CommandUtil c1=new CommandUtil(p.getInputStream(),"INFO"); 
			c1.start();
			CommandUtil c2=new CommandUtil(p.getErrorStream(),"ERROR");
			c2.start();
			int retCode=p.waitFor();
			if(retCode != 0){
				throw new BaseExecuteException("分割视频失败");
		    }
			c1.interrupt();
			c2.interrupt();

			String splitVideoPath = PathUtil.getBasePath();
			if (sceneBoundary.getSceneBoundaryType() == 1)
				splitVideoPath += "split_video/BaSSL/";
			else
				splitVideoPath += "split_video/SCRL/";
			if (sceneBoundary.getShotBoundaryType() == 1)
				splitVideoPath += "traditional/";
			else
				splitVideoPath += "TransNetV2/";
			splitVideoPath+=video.getVideoName().split("\\.")[0];
			File file = new File(splitVideoPath);
			File[] files = file.listFiles();
			if (!file.isDirectory()||files.length<=0) {
				throw new BaseExecuteException("分割视频失败");
			}
			List<SubVideo> subVideoList = new ArrayList<SubVideo>();
			for (File f : files) {
				if (!f.isDirectory())
					subVideoList.add(
							new SubVideo(f.getName(), videoId, splitVideoPath + "/" + f.getName(), sceneBoundaryId));
			}
			// 更新数据库
			boolean b = saveBatch(subVideoList);
			if (!b) {
				throw new BaseExecuteException("数据库插入失败");
			}
			return new BaseExecution<SubVideo>(ExecuteStateEum.SUCCESS, subvideoList);
		} catch (BaseExecuteException e) {
			return new BaseExecution<SubVideo>(e.getMessage());
		} catch (Exception e) {
			return new BaseExecution<SubVideo>(e.getMessage());
		}
	}

	@Override
	public BaseExecution<SubVideo> selectSubVideoList(Long videoId, Long sceneBoundaryId) {
		try {
			QueryWrapper<SubVideo> q = new QueryWrapper<SubVideo>();
			q.eq("parent_video_id", videoId);
			if (sceneBoundaryId != null)
				q.eq("scene_boundary.scene_boundary_id", sceneBoundaryId);
			List<SubVideo> subVideoList = subVideoMapper.selectSubVideoDetail(q);
			return new BaseExecution<SubVideo>(ExecuteStateEum.SUCCESS, subVideoList);
		} catch (Exception e) {
			return new BaseExecution<SubVideo>("未知错误");
		}
	}
}
