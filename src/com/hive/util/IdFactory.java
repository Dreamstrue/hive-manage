package com.hive.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Random;


public class IdFactory {
	
	//主键ID 根据时间格式与随机数组成的number构造id
	public static String getId() {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
		for (int i = 0; i < 5; i++) {// 循环产生随机数字串
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}
	
	//获得uuid
	public static String getUuid(){
		String id=java.util.UUID.randomUUID().toString();
		return id.replace("-", "");
	}
	
	
	 
	//形成19为临时组织机构代码，一个企业名字 --> 生成唯一组织机构代码
	public static String getTempZzjgdm(String qymc){
		String md5_16=md5(qymc,16);
		String zzjgdm="LLL"+md5_16;
		return zzjgdm;
	}
	
	private static String md5(String input, int bit) {
		String code=null;
		try{
			code=code(input, bit);
		}catch(Exception e){
			e.printStackTrace();
		}
		return code;
	}
	private final static char[] hexDigits = { '0', '1', '2', '3', '4', '5','6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	private static String code(String input, int bit) throws Exception {
		try {
			MessageDigest md = MessageDigest.getInstance(System.getProperty(
					"MD5.algorithm", "MD5"));
			if (bit == 16)
				return bytesToHex(md.digest(input.getBytes("utf-8"))).substring(8, 24);
			return bytesToHex(md.digest(input.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("Could not found MD5 algorithm.", e);
		}
	}
	private static String bytesToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		int t;
		for (int i = 0; i < 16; i++) {
			t = bytes[i];
			if (t < 0)
				t += 256;
			sb.append(hexDigits[(t >>> 4)]);
			sb.append(hexDigits[(t % 16)]);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		//System.out.println(getTempNo("河南广帆信息技术有限公司"));
		//System.out.println(getTempNo("河南广番信息技术有限公司"));
	}
}
