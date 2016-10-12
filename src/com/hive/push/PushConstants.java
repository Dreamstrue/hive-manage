package com.hive.push;

/**
 * @Description
 *			百度推送的常量
 * @Created 
 *			2014-6-10
 * @author 
 *			Ryu Zheng
 *
 */
public class PushConstants {

	/** 百度推送的Api Key */
	public static final String BAIDU_PUSH_API_KEY = "3wLnPuOIPUDvRV2SB7Wge9YV";
	/** 百度推送的Secret Key */
	public static final String BAIDU_PUSH_SECRET_KEY = "UaXDA67WAIzYW1Zvc7bVV0KjahLuUW7L";
	
	/** 百度推送的消息类型：0：消息（透传给应用的消息体） */
	public static final Integer BAIDU_PUSH_MESSAGE_TYPE_MESSAGE = 0;
	/** 百度推送的消息类型：1：通知（对应设备上的消息通知） */
	public static final Integer BAIDU_PUSH_MESSAGE_TYPE_NOTIFICATION = 1;
	
	/** 百度推送的设备类型（取值范围为：1～5）：1：浏览器设备 */
	public static final Integer BAIDU_PUSH_DEVICE_TYPE_WEB = 1;
	/** 百度推送的设备类型（取值范围为：1～5）：2：PC设备 */
	public static final Integer BAIDU_PUSH_DEVICE_TYPE_PC = 2;
	/** 百度推送的设备类型（取值范围为：1～5）：3：Android设备 */
	public static final Integer BAIDU_PUSH_DEVICE_TYPE_ANDROID = 3;
	/** 百度推送的设备类型（取值范围为：1～5）：4：iOS设备 */
	public static final Integer BAIDU_PUSH_DEVICE_TYPE_IOS = 4;
	/** 百度推送的设备类型（取值范围为：1～5）：5：Windows Phone设备 */
	public static final Integer BAIDU_PUSH_DEVICE_TYPE_WP = 5;
	
	/** 百度推送点击通知后的行为: 1: 表示打开Url */
	public static final Integer BAIDU_PUSH_OPEN_TYPE_URL = 1;
	/** 百度推送点击通知后的行为: 2: 表示打开应用 */
	public static final Integer BAIDU_PUSH_OPEN_TYPE_APP = 2;
	
	
	
	/** 百度推送消息内容中的android自定义字段中自定义行为: 打开"消费资讯"明细页面 */
	public static final String BAIDU_PUSH_BEHAVIOR_NEWS_DETAIL = "news_detail";
	
	
}
