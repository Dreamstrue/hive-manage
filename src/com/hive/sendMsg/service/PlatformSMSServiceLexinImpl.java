/**
 * 
 */
package com.hive.sendMsg.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.hive.sendMsg.model.BizNumberUtil;
import com.hive.sendMsg.model.MD5;
import com.hive.util.PropertiesFileUtil;




/**
 * @description TODO
 * @date 下午01:47:01 2013-11-6
 * @author xutao
 *
 */
public class PlatformSMSServiceLexinImpl  implements PlatformSMSService{
	private static Logger log = Logger.getLogger(PlatformSMSServiceLexinImpl.class);

	final private static String smsUserName = "15003879831";
	final private static String smsPassword = "xxzx67184946";
	
	final private static String sendSmsUrl = "http://123.57.39.169/sdk/send?";//乐信通信接口URL  （也可以用：http://www.lx198.com/sdk/send?）
	
	static String smsSerUrl = ""; //SMS中转服务URL,从配置文件获取
	static String suffix = "【郑州质监】";//签名
	static {
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		smsSerUrl = propertiesFileUtil.findValue("SMSSERULR");//读取配置文件
	}
	
   /* 
   1 先转发可以访问外网的中转短信服务，然后再转发出去。
   
   2 发送字段说明
    
        字段名称	         是否可空	  说明
    accId	          否	          账号ID(暂时由本公司客服人员提供)
    accName	          否	         用户名(乐信登录账号)
    accPwd	          否	        密码(乐信登录密码32位MD5加密后转大写，如123456加密完以后为：E10ADC3949BA59ABBE56E057F20F883E)
    aimcodes	否	        手机号码(多个手机号码之间用英文半角“,”隔开,单次最多支持5000个号码)
    content	          否	         内容(内容长度请参照乐信(http://www.lx198.com)发送短信页面提示)短信内容最后必须带上签名例如:下午好【动力】
    schTime	          是	         定时时间(格式为:如为空则为即时短信,如需定时时间格式为“yyyy-mm-dd hh24:mi:ss”)
    bizId     	否	        业务ID(为了方便您的使用,请将当前时间格式化为“yyyymmddHHmmss”格式作为业务ID提交)
    dataType	是	        返回的数据(类型支持:json/xml/string 三种形式 默认string)
    
   3 返回值：
           多个响应参数之前以”;”隔开,格式为:成功或失败代码;成功或失败信息;成功条数;失败条数;计费条数;计费金额;余额;
	 */
	@Override
	public boolean sendSms(Long sendUserID,String phonenum, String content, String qfID, String systemName) {
		
		
		if(sendUserID == null) {
			log.error("短信发送失败,发送人帐号不能为空！" );
			return false; 
		}//end of if
		
		if(StringUtils.isBlank(phonenum) || StringUtils.isEmpty(phonenum)) {
			log.error("短信发送失败,接收人手机号不能为空！" );
			return false; 
		}//end of if
		
		if(StringUtils.isBlank(content) || StringUtils.isEmpty(content)) {
			log.error("短信发送失败,发送内容不能为空！" );
			return false; 
		}//end of if
		
		if(StringUtils.isBlank(systemName) || StringUtils.isEmpty(systemName)) {
			systemName = "IAPS";
		}//end of if
		
	    boolean            result = false;	
	    HttpURLConnection  con    = null;
	    PrintWriter        out    = null;
	    BufferedReader     in     = null;
		try {
			StringBuilder sbPara = new StringBuilder();				
			
			sbPara.append("sendsmsurl=");
			sbPara.append(URLEncoder.encode(sendSmsUrl,"gb2312"));
			sbPara.append("&accName=");
			sbPara.append(URLEncoder.encode(smsUserName,"gb2312"));
			sbPara.append("&accPwd=");
			sbPara.append(MD5.getMd5String(smsPassword));
			sbPara.append("&aimcodes=");
			sbPara.append(URLEncoder.encode(phonenum,"gb2312"));
			sbPara.append("&content=");
			sbPara.append(URLEncoder.encode(content+suffix,"gb2312"));
			sbPara.append("&bizId="+BizNumberUtil.createBizId());
			sbPara.append("&dataType=string");
			
			URL url = new URL(smsSerUrl); 
			con = (HttpURLConnection)url.openConnection(); 
			
			con.setRequestMethod("POST");
			con.setDoInput(true);
			con.setDoOutput(true); 
			
			out = new PrintWriter(con.getOutputStream());
			out.println(sbPara);
			out.flush();
			
			//设置编码,否则中文乱码	
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), "gb2312"));
	
			StringBuilder buff = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				buff.append(line);
			}//end of while
			
			
			String replyMsg = "未知错误，短信发送失败!";
			String[] strRetCode  = buff.toString().split(";"); 
			if((strRetCode!=null) && (strRetCode.length > 2)){
				String replyCode = strRetCode[0];
				replyMsg   = strRetCode[1];
				if(replyCode.equalsIgnoreCase("1")){
					result=true;
				}//end of if
			}//end of if
					
			
		} catch (Exception e) {
			
			e.printStackTrace();
			log.error("由于程序异常，短信发送失败！" + phonenum + "," + content);
		}finally{
			if (out != null ) out.close();
			try {
				if (in != null) in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (con != null ) con.disconnect();
		}//end of finally
		
		return result;
	}
	
	//测试
	public static void main(String[] agrs) {
		/**
		 * 20160307 yyf add test
		 */
		

		//PlatformSMSService smsService = new PlatformSMSServiceLexinImpl();
		//smsService.sendSms(new Long(1),"15003879831", "下午好","1","IPAS");
		//smsService.sendSms("15003879831", "市局下达7月份企业巡查计划！【质监局】");
			
		String strSms = "1《关于召开市局2014年元旦、春节期间质量安全排查整治专项行动动员大会的通知》已分别发至市局公共邮箱和市局信息化平台->内部通知栏目，请各单位尽快查收上报参会名单。市局办(12.31)";
		
		
		PlatformSMSService smsService = new PlatformSMSServiceLexinImpl();
		smsService.sendSms(new Long(1),"15838827839", strSms,"1","IPAS");
		//smsService.sendSms(new Long(1),"18624967971", strSms,"1","IPAS");
		//smsService.sendSms(new Long(1),"18937819997", strSms,"1","IPAS");
		
		System.out.println("发送完成！");
		
		//String strSms = "123456789";
	
//		int nSplitLen = 65;//定义截取长度
//		int i = 0;				
//		
//		while(i<(strSms.length()/nSplitLen)){
//			System.out.println(strSms.substring(i*nSplitLen,(i*nSplitLen+nSplitLen))+suffix);
//			i++;
//		}
//		System.out.println(strSms.substring(i*nSplitLen,(i*nSplitLen+(strSms.length()%nSplitLen)))+suffix);

	}

}
