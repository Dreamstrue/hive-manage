package com.hive.util;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * 
* Filename: XMLParse.java  
* Description:XML解析类
* Copyright:Copyright (c)2014
* Company:  GuangFan 
* @author:  YangHui
* @version: 1.0  
* @Create:  2014-10-17  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2014-10-17 下午2:25:36				YangHui 	1.0
 */
public class XMLParse {

	/**
	 * @Description: 解析xml文件
	 * @author YangHui 
	 * @Created 2014-10-17
	 * @param args
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	
	
	public String xmlParse(String filePath) {
		String url = "";
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(filePath);
			doc.normalize();
			
			NodeList nodeList = doc.getElementsByTagName("updateInfo");
			for(int i=0;i<nodeList.getLength();i++){
				Element e = (Element) nodeList.item(i);
				url = e.getElementsByTagName("apkUrl").item(0).getFirstChild().getNodeValue();
	//			System.out.println(value);
			}
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return url;
		
		
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
//		xmlParse("D:/updateinfoEnt.xml");

	}

}
