package com.example.video.Segmentation.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//读取进程缓冲区内容
public class CommandUtil extends Thread{
	
	     InputStream is;
	     String type;
	     
	     public CommandUtil(InputStream is, String type)
	     {
	         this.is = is;
	         this.type = type;
	     }
	     
	     public void run()
	     {
	         try
	         {
	             InputStreamReader isr = new InputStreamReader(is);
	             BufferedReader br = new BufferedReader(isr);
	             String line=null;
	             while ( (line = br.readLine()) != null)
	                 System.out.println(type + ">" + line);    
	             } catch (IOException ioe)
	               {
	                 ioe.printStackTrace();  
	               }
	     }
	 
}
