package com.example.video.Segmentation.util;


/*
 * 管理文件路径
 */
public class PathUtil {
	
//	private static String seperator =System.getProperty("file.separator");//获取不同操作系统的分隔符
	
	
	/**
	 * 获取设置好的根路径（所有的文件都保存在"F:/1/data/"文件夹下）
	 * @return
	 */
	public static String getBasePath() {
		String os=System.getProperty("os.name");
		String basePath;
		if(os.toLowerCase().startsWith("win")) {
			basePath="F:/1/data/"; 
		}else {
			basePath="/1/data/";
		}
//		basePath=basePath.replace("/", seperator);//针对不同系统替换
		return basePath;
	}
}