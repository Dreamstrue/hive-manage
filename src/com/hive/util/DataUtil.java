package com.hive.util;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class DataUtil {
	

	/**
	 * 判断string是否为空
	 *
	 * @param string
	 * @return 如果为空则返回true，否则返回true
	 */
	public static boolean isEmpty(String string) {
		if (string == null || string.trim().length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 判断Object是否为null
	 *
	 * @param object
	 * @return 如果为空则返回true，否则返回true
	 */
	public static boolean isNull(Object object) {
		if (object == null )
			return true;
		else
			return false;
	}

	
	public static boolean listIsNotNull(List list) {
		if (list != null  && list.size()>0)
			return true;
		else
			return false;
	}
	
	/** 
     * 产生随机的4位数 
     * @return 
     */  
    public static String getSix(){  
        Random rad=new Random();  
          
        String result  = rad.nextInt(10000) +"";  
          
        if(result.length()!=4){  
            return getSix();  
        }  
        return result;  
    }
	
	/**
	 * 
	 * @Description: 随机产生6位数字和字母(大小写)的组合
	 * @author YangHui 
	 * @Created 2014-6-30
	 * @param length
	 * @return
	 */
	public static String getCharAndNumr(int length)     
	{     
	    String val = "";     
	             
	    Random random = new Random();     
	    for(int i = 0; i < length; i++)     
	    {     
	        String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字     
	                 
	        if("char".equalsIgnoreCase(charOrNum)) // 字符串     
	        {     
	            int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母     
	            val += (char) (choice + random.nextInt(26));     
	        }     
	        else if("num".equalsIgnoreCase(charOrNum)) // 数字     
	        {     
	            val += String.valueOf(random.nextInt(10));     
	        }     
	    }     
	             
	    return val;     
	}   
	
	
	/**
	 * 去除文本中的 html 标签
	 * @param htmlStr
	 * @return
	 */
	public static String getText(String htmlStr) {
		if (htmlStr == null || "".equals(htmlStr))
			return "";
		String textStr = "";
		java.util.regex.Pattern pattern;
		java.util.regex.Matcher matcher;

		try {
			String regEx_remark = "<!--.+?-->";
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> }    
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> }    
			String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式    
			String regEx_html1 = "<[^>]+";
			htmlStr = htmlStr.replaceAll("\n", "");
			htmlStr = htmlStr.replaceAll("\t", "");
			htmlStr = htmlStr.replaceAll("&nbsp;", "");
			pattern = Pattern.compile(regEx_remark);//过滤注释标签
			matcher = pattern.matcher(htmlStr);
			htmlStr = matcher.replaceAll("");

			pattern = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(htmlStr);
			htmlStr = matcher.replaceAll(""); //过滤script标签    

			pattern = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(htmlStr);
			htmlStr = matcher.replaceAll(""); //过滤style标签    

			pattern = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(htmlStr);
			htmlStr = matcher.replaceAll(""); //过滤html标签    

			pattern = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(htmlStr);
			htmlStr = matcher.replaceAll(""); //过滤html标签    

			textStr = htmlStr.trim();

		} catch (Exception e) {
			System.out.println("获取HTML中的text出错:");
			e.printStackTrace();
		}

		return textStr;//返回文本字符串
	}

	
	public  static void main(String[] args){
		String val = getCharAndNumr(6);
		System.out.println(val);
	}


	
	       
}
