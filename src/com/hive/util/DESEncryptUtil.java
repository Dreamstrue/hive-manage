package com.hive.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.bouncycastle.util.encoders.Hex;

public class DESEncryptUtil {

	
	Key key;
	
	public DESEncryptUtil(String str){
		getKey(str); //生成密钥
	}
	
	/**
	 * 
	 * @Description: 根据参数生产KEY
	 * @author YangHui 
	 * @Created 2014-8-7
	 * @param str
	 */
	private void getKey(String str) {
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			_generator.init(new SecureRandom(str.getBytes()));
			this.key = _generator.generateKey();
			System.out.println("密钥: "+_generator.generateKey());
			_generator=null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	
	public static SecretKey getSecretKey(String keyStr){
		KeyGenerator kgen;
		try {
			kgen = KeyGenerator.getInstance("DES");
			kgen.init(new SecureRandom(keyStr.getBytes()));
			SecretKey key = kgen.generateKey();
			kgen = null;
			return key;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	


	/**
	 * 
	 * @Description:文件file进行加密并保存目标文件destFile中
	 * @author YangHui 
	 * @Created 2014-8-7
	 * @param file   待加密的文件
	 * @param destFile 加密后的文件
	 * @throws Exception
	 */
	public void encryptFile(String file, String destFile) throws Exception {   //【具体用法见主函数】
	    Cipher cipher = Cipher.getInstance("DES");
	    // cipher.init(Cipher.ENCRYPT_MODE, getKey());
	    cipher.init(Cipher.ENCRYPT_MODE, this.key);
	    InputStream is = new FileInputStream(file);
	    OutputStream out = new FileOutputStream(destFile);
	    CipherInputStream cis = new CipherInputStream(is, cipher);
	    byte[] buffer = new byte[1024];
	    int r;
	    while ((r = cis.read(buffer)) > 0) {
	        out.write(buffer, 0, r);
	    }
	    cis.close();
	    is.close();
	    out.close();
	  }



	/**
	  * 文件采用DES算法解密文件
	  *
	  * @param file 已加密的文件 如c:/加密后文件.txt
	  * @param destFile
	  *         解密后存放的文件名 如c:/ test/解密后文件.txt
	  */
	  public void decryptFile(String file, String dest) throws Exception {
	    Cipher cipher = Cipher.getInstance("DES");
	    cipher.init(Cipher.DECRYPT_MODE, this.key);
	    InputStream is = new FileInputStream(file);
	    OutputStream out = new FileOutputStream(dest);
	    CipherOutputStream cos = new CipherOutputStream(out, cipher);
	    byte[] buffer = new byte[1024];
	    int r;
	    while ((r = is.read(buffer)) >= 0) {
	        cos.write(buffer, 0, r);
	    }
	    cos.close();
	    out.close();
	    is.close();
	  }



	  
	  public static void testFile() throws Exception{
		  System.out.print("请输入加密密码：");
		  java.util.Scanner scan = new java.util.Scanner(System.in);
		  String td1=scan.nextLine();       //以上两行用来实现字符串的输入。
		  DESEncryptUtil td = new DESEncryptUtil(td1);               //【对称加密的密码所在】
		  td.encryptFile("e:/aaa/1.txt", "e:/aaa/2.txt"); //加密
		  System.out.println("加密完成！");
		  System.out.print("请输入解密密码：");
		  java.util.Scanner scanf = new java.util.Scanner(System.in);
		  String td2=scanf.nextLine();
		  DESEncryptUtil tdd = new DESEncryptUtil(td2);
		  tdd.decryptFile("e:/aaa/2.txt", "e:/aaa/3.txt"); //解密
		  System.out.println("解密完成！");
	  }


	  
	  
	  /**
	   * 
	   * @Description:加密
	   * @author YangHui 
	   * @Created 2014-8-7
	   * @param content 待加密的内容
	   * @param keyStr 待生产密钥的内容
	   * @return
	   */
	  public static  byte[] encrypt(String content,String keyStr){
		  try {
			SecretKey skey = getSecretKey(keyStr);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			byte[] result = cipher.doFinal(content.getBytes());
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		  return null;
	  }
	  
	 
	  /**
	   * 
	   * @Description: 解密
	   * @author YangHui 
	   * @Created 2014-8-7
	   * @param content 待解密的内容
	   * @param keyStr  生产密钥的内容
	   * @return
	   */
	  public static byte[] dencrypt(byte[] content,String keyStr){
		  try {
			SecretKey skey = getSecretKey(keyStr);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		  return null;
	  }
	  

	  
	  public static void testEncrpyt(String content,String keyStr){
		  //加密
		  System.out.println("加密前："+content);
		  byte[] encryptResult = encrypt(content, keyStr);
		  String str = new String (org.bouncycastle.util.encoders.Hex.encode(encryptResult));
		  System.out.println(str);
		  encryptResult = Hex.decode(str);
		  //解密
		  byte[] dencryptResult = dencrypt(encryptResult, keyStr);
		  System.out.println("解密后："+new String(dencryptResult));
	  }


	  
	  //-------------------------------------字符串转换成十六进制----------------------
	  public static void stringToBand_16(){
		  String string = "1a5bc3";
		  
		  byte[] b = org.bouncycastle.util.encoders.Hex.encode(string.getBytes());
		  String bstr = new String(b);
		  System.out.println(bstr);
		  
		  b = Hex.decode(bstr);
		  System.out.println(new String(b));
		  
	  }
	  
	  
	  
	  //---------------------------------RSA 加密   begin------------------------------------------------------
	  
	  public static long rsaEncrpyt(int baseNum,int key,long message){
		  
		  if(baseNum<1 || key < 1){
			  return 0L;
		  }
		  
		  long rsaMessage = 0L;
		  
		  //加密核心算法
	//	  System.out.println(Math.pow(message, key));
		  rsaMessage = Math.round(Math.pow(message, key)) % baseNum ;
		  
		  return rsaMessage;
		  
		  
	  }
	  
	  
	  
	  public static void rsaTest(int baseNum,int keyE,int keyS,long msg){
		  
		  /* 
		   * 1.定义两个不相等的质数 p=3, q=11    而在实际中，质数越大越好
		   * 2.计算基数baseNum(别名n) p*q=3*11=33;
		   * 3.计算n的欧拉函数  定义为φ(n)=(p-1)*(q-1)即为φ(n)=(3-1)*(11-1)=20
		   * 4.随机选择一个整数e,条件是1<e<φ(n),切e与φ(n)互为质数，定义keyE=3
		   * 5.计算e对φ(n)的模反元素d,所谓"模反元素"就是指有一个整数d，可以使得e*d被φ(n)除的余数为1,这里取得最小的一个整数定义为keyS=7
		   * 6.将n和e封装成公钥，n和d封装成私钥,所以公钥(33,3),私钥(33,7)
		   * 7.假设要传送的信息为m,并且m必须是整数切小于n,定义msg=24
		   * 8.
		   * 
		   */
		   
		  //基数 (选择两个不相等的质数 p = 3,q= 11，而在实际中，质数越大越好) 
	//	  baseNum = 3*11;
		  
		  //公钥
//		  keyE = 3;
		  
		  //密钥
//		  keyS = 7;
		  
	//	  int keyO=0;
		  /*for(int d=1;d<1000;d++){
			  
			  int k = 20*d+1;
			  if(k%keyE==0){
				  keyO = k/keyE;
				  System.out.println("最小整数："+keyO);
				  break;
			  }
		  }*/
		  
		  //未加密数据
		  msg = 24L;
		  
		  //加密后的数据
		  long encodeMsg = rsaEncrpyt(baseNum, keyE, msg);
		  
		  //解密后的数据
		  long decodeMsg = rsaEncrpyt(baseNum, keyS, encodeMsg);
		  
		  System.out.println("加密前："+msg);
		  System.out.println("加密后："+encodeMsg);
		  System.out.println("解密后："+decodeMsg);
	  }
	  
	  
	  public static void mod(int p,int q){
		  
		  int n = p*q;
		  int fn = (p-1)*(q-1);
		  
		  System.out.println("欧拉函数:"+fn);
		  
		  java.util.Scanner scan = new java.util.Scanner(System.in);
		  
		  int keyE = scan.nextInt();
		  int keyS = 0;
		  for(int d=1;d<1000;d++){
			  int k = fn*d+1;
			  if(k%keyE==0){
				  keyS = k/keyE;
				  System.out.println("最小整数："+keyS+"，循环取值d："+d);
				  break;
			  }
		  }
		  
		  long msg = 24L;
		  
		  rsaTest(n, keyE, keyS, msg);
		  
	  }
	  
	  //---------------------------------RSA 加密   end------------------------------------------------------
	  
	  
	  
	  
	  
	  
	  
	  
	  
 
	/**
	 * @Description:  DES对称加密
	 * @author YangHui 
	 * @Created 2014-8-7
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
	//	testFile();
		
		String content = "企业名称：索凌电器|产品名称：热水器|批次号：21|序号：20140807";
		String keyStr = "2DN3D33DFJK6";
	//	testEncrpyt(content, keyStr);
		
		
//		rsaTest();
		mod(3,11);
	}

}
