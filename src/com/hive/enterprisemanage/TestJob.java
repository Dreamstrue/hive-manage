package com.hive.enterprisemanage;

import com.hive.util.DateUtil;

public class TestJob {
	
	 public void execute(){  
	        try{  
	        	System.out.println("每隔5秒执行一次该方法-----  " + DateUtil.getTimestamp());
	         }catch(Exception ex){  
	             ex.printStackTrace();  
	         }  
	     }  

}
