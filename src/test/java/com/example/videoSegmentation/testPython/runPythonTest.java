package com.example.videoSegmentation.testPython;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;



public class runPythonTest extends BaseTest {
	@Test
	@Ignore
	public void runPython() {
		String path="\\1\\bassl";
		String pythonName="./finetune/split_video.py";
		String command = "cmd.exe /c f: && cd "
                + path //此处插入python文件的路径
                + " && start D:\\anaconda\\python.exe " + pythonName;
		
		try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();	
            System.out.println(command);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
