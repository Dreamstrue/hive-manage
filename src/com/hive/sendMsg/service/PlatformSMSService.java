package com.hive.sendMsg.service;

/***
 * platform平台的短信服务
 * 
 * 
 */
public interface PlatformSMSService {

	/**
	 * 短信发送方法
	 * @param sendAccNo 发送帐号，一般指登录者帐号
	 * @param phonenum	发送短信的目标手机号
	 * @param content   发送短信的内容 
	 * @param groupID   群组ID，可为空
	 * @param systemName 那个系统进行短信发送，如果为空，缺省填写IAPS
	 */
	public boolean sendSms(Long sendUserID,String phonenum, String content, String groupID, String systemName);

	
}

