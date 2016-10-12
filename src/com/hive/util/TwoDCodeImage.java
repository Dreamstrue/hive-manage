package com.hive.util;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hive.common.SystemCommon_Constant;

public class TwoDCodeImage {

	/**
	 * @Description: 生成二维码图片
	 * @author YangHui 
	 * @Created 2014-8-8
	 * @param args
	 * @throws WriterException 
	 * @throws IOException 
	 */
	
	public static String writeImage() throws WriterException, IOException{
		String text = "http://www.baidu.com";
		int width = 300;
		int height = 300;
		//二维码图片的格式
		String format = "jpg";
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		//内容所使用的编码
		hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);
		//生产二维码
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		//取得配置文件配置的上传路径
		String rootPath = propertiesFileUtil.findValue("uploadPath");
		String imgPath = SystemCommon_Constant.FILE_UPLOAD_MAIN_DIRECTORY+"myImg.jpg";
		File outputFile = new File(rootPath+imgPath);
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
		return imgPath;
	}
	
	/**
	 * 
	 * @Description:
	 * @author YangHui 
	 * @Created 2014-10-16
	 * @param text  二维码内容
	 * @param imgName  二维码图片名称
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	public static String writeImage(String text,String imgName) throws WriterException, IOException{
		int width = 300;
		int height = 300;
		//二维码图片的格式
		String format = "jpg";
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		//内容所使用的编码
		hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);
		//生产二维码
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		//取得配置文件配置的上传路径
		String rootPath = propertiesFileUtil.findValue("uploadPath");
		
		// 如果目录不存在，则创建目录，否则会报空指针
		File uploadDir = new File(rootPath + SystemCommon_Constant.FILE_TWODCODE_MAIN_DIRECTORY);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		
		String imgPath = SystemCommon_Constant.FILE_TWODCODE_MAIN_DIRECTORY+imgName+".jpg";
		File outputFile = new File(rootPath+imgPath);
		if(outputFile.exists()){
			outputFile.delete();
		}
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
		return rootPath + imgPath;
	}
	
	public static void main(String[] args) {
		

	}

}
