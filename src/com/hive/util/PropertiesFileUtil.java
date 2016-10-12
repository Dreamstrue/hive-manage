package com.hive.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：YANGHUI
 * <p/>创建日期：2013-11-4
 * <p/>创建时间：上午11:31:22
 * <p/>功能描述：配置文件读取
 * <p/>===========================================================
 * <p/>修改历史
 * <p/>修改人                修改时间                修改原因
　
<p/>===========================================================
 */
public class PropertiesFileUtil {
	private static final Logger logger = Logger
			.getLogger(PropertiesFileUtil.class);
	Properties p = new Properties();
	private final String configPath = "config/config.properties";

	/**
	 * 根据key读取properties文件中的值
	 * @param key
	 * @return
	 */
	public String findValue(String key) {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(configPath);
		try {
			p.load(inputStream);
		} catch (IOException e) {
			logger.error("PropertiesFileUtil loadPath ERROR!",e);
		}
		return p.getProperty(key);
	}
	
}
