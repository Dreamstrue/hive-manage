package com.hive.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.hive.common.entity.Annex;
import com.hive.enterprisemanage.entity.EnterpriseRoomPicture;
import com.hive.util.DataUtil;
import com.hive.util.DateUtil;
import com.hive.util.ImgCutUtil;
import com.hive.util.PropertiesFileUtil;
import com.hive.util.ZipHelper;

/**
 * 
 * Filename: AnnexFileUpLoad.java Description: 附件上传公共方法 Copyright:Copyright
 * (c)2013 Company: GuangFan
 * 
 * @author: yanghui
 * @version: 1.0
 * @Create: 2013-10-18 Modification History: Date Author Version
 *          ------------------------------------------------------------------
 *          2013-10-18 下午6:06:34 yanghui 1.0
 */
public class AnnexFileUpLoad {
	
	private static final String CONTENT_COMMON_PATH = SystemCommon_Constant.FILE_CONTENT_MAIN_DIRECTORY;
	
	private static final Logger logger = Logger.getLogger(AnnexFileUpLoad.class);

	/**
	 * 
	 * @Description:
	 * @author yanghui
	 * @Created 2013-10-18
	 * @param files
	 *            上传的文件
	 * @param session
	 *            上下文的会话
	 * @param objectId
	 *            关联对象的ID
	 * @param objectTable
	 *            业务关联对象所对应的数据表
	 * @param businessDir
	 *            不同业务所对应的附件存放目录
	 * @param annexType
	 *            上传附件类型 如针对企业信息管理模块 上传的附件类型用来区分为企业的徽标或企业的一些资质证书的图片等
	 * @return
	 */
	public static List<Annex> uploadFile(MultipartFile[] files,
			HttpSession session, Long objectId, String objectTable,
			String businessDir, String annexType) {

		List list = new ArrayList();
		if (files != null) {
			for (MultipartFile file : files) {
				
				list = commonAnnexList(file,session,objectId,objectTable,businessDir,annexType,list);
			}
		}

		return list;
	}
	
	
	/**
	 * 
	 * @Description: 上传单独附件或图片等处理过程
	 * @author yanghui 
	 * @Created 2013-10-24
	 * @param file
	 * @param session
	 * @param objectId
	 * @param objectTable
	 * @param businessDir
	 * @param annexType
	 * @return
	 */
	public static List<Annex> uploadImageFile(MultipartFile file, HttpSession session, Long objectId, String objectTable, String businessDir, String annexType){
		
		List<Annex> list = new ArrayList<Annex>();
		list = commonAnnexList(file, session, objectId, objectTable, businessDir, annexType,list);
		
		return list;
	}
	
	
	
	public static List<Annex>  commonAnnexList(MultipartFile file, HttpSession session, Long objectId, String objectTable, String businessDir, String annexType, List list){
		
		if (file.getSize() > 0) {
			// 说明存在上传附件
			PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
			//取得配置文件配置的上传路径
			 String path = propertiesFileUtil.findValue("uploadPath");
			 
			 
			// 取得当前的项目名称
	//		String path = session.getServletContext().getRealPath("/");
			 
			 String urlPath = "";//用来保存到数据库中的相对路径
			
			// 定义文件存放公共系统盘下的共同目录
			String filePath = path
					+ SystemCommon_Constant.FILE_UPLOAD_MAIN_DIRECTORY
					+ businessDir;
			File uploadDir = new File(filePath);
			// 1.目录不存在，创建目录
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			filePath += File.separator + ymd + File.separator;
			urlPath = SystemCommon_Constant.FILE_UPLOAD_MAIN_DIRECTORY
					+ businessDir+File.separator+ymd+File.separator;
			File dirFile = new File(filePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}

			// 2.获取关于上传文件的基本信息
			String fileName = file.getOriginalFilename();// 真实的文件名称,由于文件名称中存在中文字符，会在下载或读取的过程中出现乱码情况，这里要重新定义文件名称
			
			long fileSize = file.getSize();// 文件大小
			String fileExt = fileName.substring(fileName
					.lastIndexOf(".") + 1);// 文件扩展名---文件类型
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
			String ymd1 = sdf1.format(new Date());

			//为了防止磁盘中的文件有重复，这里对上传的文件名称做处理
			String newFileName =  ymd1+"_"+ new Random().nextInt(1000) + "." + fileExt;//通过时间戳定义的新文件名称
			File targetFile = new File(filePath, newFileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}

			try {
				file.transferTo(targetFile);
			} catch (Exception e) {
				// return error("上传文件失败");
			}
			Annex annex = new Annex();
			annex.setObjectTable(objectTable);
			annex.setObjectId(objectId);
			annex.setCfilename(fileName);
			annex.setCfiletype(fileExt);
			annex.setNfilesize(fileSize);
			annex.setCannextype(annexType);// 文件类型
			annex.setBbinarydata("");
			annex.setIdowncount(0L);// 下载次数
			annex.setCfilepath(urlPath + newFileName);// 文件路径
			annex.setDcreatetime(DateUtil.getTimestamp());
			annex.setNcreateid( session!=null?((Long) session.getAttribute("userId")):1L);
			annex.setCvalid(SystemCommon_Constant.VALID_STATUS_1);// 是否可用（默认上传的附近都是可用
																	// 的）

			list.add(annex);
		}
		return list;
	}
	
	
	/**
	 * 
	 * @Description: 通用校验是否存在上传附件
	 * @author yanghui 
	 * @Created 2013-10-24
	 * @param chasannex
	 * @param files
	 * @return
	 */
	public static boolean checkHasAnnex(String chasannex,MultipartFile[] files,int size){
		boolean flag = true;
		if(chasannex.equals(SystemCommon_Constant.SIGN_YES_1) && files==null && size==0){
			flag = false;
		}
		return flag;
	}

	
	
	/**
	 * 
	 * @Description: 通用校验是否存在上传附件(重写后的方法)  
	 * @author yanghui 
	 * @Created 2013-10-24
	 * @param chasannex
	 * @param idString  当该参数不为空时，说明有新的附件上传
	 * @return
	 */
	public static boolean checkHasAnnex(String chasannex,String idString,int size){
		boolean flag = true;
		//当选择存在附件，而此时并没有上传附件且原来不存在有效的附件，说明该记录本身并没有任何附件
		if(chasannex.equals(SystemCommon_Constant.SIGN_YES_1) && StringUtils.isEmpty(idString) && size==0){
			flag = false;
		}
		return flag;
	}

	/**
	 * 
	 * @Description:把全部内容写到文件中
	 * @author yanghui 
	 * @Created 2013-11-30
	 * @param content  内容
	 * @param businessDir  内容对应的栏目路劲
	 * @param oldPath  新增时保存的文件路径  在修改完成后，要删除该文件
	 * @param type 类型  在添加新的通知公告、资讯等信息时只有在审核的时候才能把自定义样式加入文件里，否则会出现样式重复
	 * @return
	 */
	public static String writeContentToFile(String content, String businessDir, String oldPath,String type) {
		//取得文件保存跟根路径
		 PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		 String path = propertiesFileUtil.findValue("uploadPath"); //根路径
		
		 String filePath = path+CONTENT_COMMON_PATH+businessDir;
		 File uploadDir = new File(filePath);
			//目录不存在，创建目录
			if (!uploadDir.exists()) {
	//			System.out.println("》》》》》目录不存在");
				uploadDir.mkdirs();
			}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		filePath += File.separator + ymd + File.separator;
		File dirFile = new File(filePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		//定义文件的扩展名
		String fileExt = ".html";
		//文件名
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String ymd1 = sdf1.format(new Date());
		String fileName = ymd1+"_"+new Random().nextInt(10000)+fileExt;
		String contentPath = filePath+fileName;
		
		//
		String urlPath = "";
		urlPath = SystemCommon_Constant.FILE_CONTENT_MAIN_DIRECTORY
				+ businessDir+File.separator+ymd+File.separator+fileName;
		
		//为了手机端显示的时候风格统一，这里需要对内容进行处理
		if("approve".equals(type)){
			
			StringBuffer str = new StringBuffer();
			str.append("<HTML><HEAD><TITLE></TITLE>");
			str.append("<style type='text/css'>");
			str.append(" body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,button,");
			str.append("textarea,p,blockquote,th,td,span{");
			str.append("margin:0;padding:2px 8px;line-height:28px;font-size:17px}");
			str.append("</style></HEAD><BODY>");
			
			//把content里面的一些特殊内部样式剔除（通过正则表达式实现）
			content = content.replaceAll("&nbsp;","");
			String regex_font = "font-size:\\d*px;";
			String regex_line = "line-height:\\d*px";
			java.util.regex.Pattern pattern;
			java.util.regex.Matcher matcher;
			
			//剔除内部样式 字体大小
			pattern = Pattern.compile(regex_font,Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(content);
			content = matcher.replaceAll("");
			
			//剔除内部样式行高
			pattern = Pattern.compile(regex_line,Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(content);
			content = matcher.replaceAll("");
			
			str.append(content);
			str.append("</BODY></HTML>");
			
			content = str.toString();
		}
		
		byte[] conByte = content.getBytes();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(contentPath);
			//首先把文件清空
			fos.write(conByte,0,conByte.length);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!DataUtil.isEmpty(oldPath)){
			//说明为修改或审核，需要把原来的文件删除
			File file = new File(path+oldPath);
			if(file.exists()){
				file.delete();
			}
		}
		
		
		return urlPath;
	}
	/**
	 * 
	 * @Description:复用writeContentToFile用来将活动详情内容写入文件
	 * @author yyf
	 * @Created 2016-6-27
	 * @param content  内容
	 * @param businessDir  内容对应的栏目路劲
	 * @param oldPath  新增时保存的文件路径  在修改完成后，要删除该文件
	 * @param type 类型  在添加新的通知公告、资讯等信息时只有在审核的时候才能把自定义样式加入文件里，否则会出现样式重复
	 * @return
	 */
	public static String writeActivityContentToFile(String content, String businessDir, String oldPath,String type) {
		//取得文件保存跟根路径
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		String path = propertiesFileUtil.findValue("uploadPath"); //根路径
		
		String filePath = path+SystemCommon_Constant.FILE_ACTIVITY_MAIN_DIRECTORY+businessDir;
		File uploadDir = new File(filePath);
		//目录不存在，创建目录
		if (!uploadDir.exists()) {
			//			System.out.println("》》》》》目录不存在");
			uploadDir.mkdirs();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		filePath += File.separator + ymd + File.separator;
		File dirFile = new File(filePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		//定义文件的扩展名
		String fileExt = ".html";
		//文件名
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
		String ymd1 = sdf1.format(new Date());
		String fileName = ymd1+"_"+new Random().nextInt(10000)+fileExt;
		String contentPath = filePath+fileName;
		
		//
		String urlPath = "";
		urlPath = SystemCommon_Constant.FILE_ACTIVITY_MAIN_DIRECTORY
				+ businessDir+File.separator+ymd+File.separator+fileName;
		
		//为了手机端显示的时候风格统一，这里需要对内容进行处理
		if("approve".equals(type)){
			
			StringBuffer str = new StringBuffer();
			str.append("<HTML><HEAD><TITLE></TITLE>");
			str.append("<style type='text/css'>");
			str.append(" body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,button,");
			str.append("textarea,p,blockquote,th,td,span{");
			str.append("margin:0;padding:2px 8px;line-height:28px;font-size:17px}");
			str.append("</style></HEAD><BODY>");
			
			//把content里面的一些特殊内部样式剔除（通过正则表达式实现）
			content = content.replaceAll("&nbsp;","");
			String regex_font = "font-size:\\d*px;";
			String regex_line = "line-height:\\d*px";
			java.util.regex.Pattern pattern;
			java.util.regex.Matcher matcher;
			
			//剔除内部样式 字体大小
			pattern = Pattern.compile(regex_font,Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(content);
			content = matcher.replaceAll("");
			
			//剔除内部样式行高
			pattern = Pattern.compile(regex_line,Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(content);
			content = matcher.replaceAll("");
			
			str.append(content);
			str.append("</BODY></HTML>");
			
			content = str.toString();
		}
		
		byte[] conByte = content.getBytes();
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(contentPath);
			//首先把文件清空
			fos.write(conByte,0,conByte.length);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!DataUtil.isEmpty(oldPath)){
			//说明为修改或审核，需要把原来的文件删除
			File file = new File(path+oldPath);
			if(file.exists()){
				file.delete();
			}
		}
		return urlPath;
	}


	/**
	 * 
	 * @Description: 从文件中读取内容
	 * @author yanghui 
	 * @Created 2013-11-30
	 * @param filePath
	 * @return
	 */
	public static byte[] getContentFromFile(String filePath) {
		byte[] b = "".getBytes();
		if(!DataUtil.isEmpty(filePath)){
			//取得文件保存跟根路径
			 PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
			 String path = propertiesFileUtil.findValue("uploadPath"); //根路径
			 filePath = path+filePath;
			 
			b = new byte[(int) (new File(filePath)).length()];
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(filePath);
				fis.read(b, 0, b.length);
				fis.close();
			} catch (Exception e) {
				logger.error("文件读取错误或找不到路径为"+filePath+"的文件");
			//	e.printStackTrace();
			}
		}
		return b;
	}


	
	/**
	 * 
	 * @Description:供电子杂志模块使用方法
	 * @author yanghui 
	 * @Created 2013-12-6
	 * @param exefile
	 * @param session
	 * @param nemagazineId
	 * @param objectTable
	 * @param businessDirAnnex
	 * @param object
	 * @param type  该参数是为了方便处理杂志附件为压缩文件处理
	 * @return
	 */
	public static List<Annex> uploadSingleFile(MultipartFile exefile,
			HttpSession session, Long nemagazineId, String objectTable,
			String businessDirAnnex, String  annexType, String type) {
		List<Annex> list = new ArrayList<Annex>();
		if(type.equals("type_zip")){
			list = commonAnnexList_toMagazine(exefile, session, nemagazineId,
					objectTable, businessDirAnnex, annexType, list);
		}else{
			list = commonAnnexList(exefile, session, nemagazineId,
					objectTable, businessDirAnnex, annexType, list);
		}
		return list;
	}
	
	
	
	public static List<Annex>  commonAnnexList_toMagazine(MultipartFile file, HttpSession session, Long objectId, String objectTable, String businessDir, String annexType, List list){
		
		if (file.getSize() > 0) {
			// 说明存在上传附件
			PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
			//取得配置文件配置的上传路径
			 String rootPath = propertiesFileUtil.findValue("uploadPath");
			 
			 String urlPath = "";//用来保存到数据库中的相对路径
			
			// 定义文件存放公共系统盘下的共同目录
			String filePath = rootPath
					+ SystemCommon_Constant.FILE_UPLOAD_MAIN_DIRECTORY
					+ businessDir;
			File uploadDir = new File(filePath);
			// 1.目录不存在，创建目录
			if (!uploadDir.exists()) {
				uploadDir.mkdirs();
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			filePath += File.separator + ymd + File.separator;
			urlPath = SystemCommon_Constant.FILE_UPLOAD_MAIN_DIRECTORY
					+ businessDir+File.separator+ymd+File.separator;
			File dirFile = new File(filePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}

			// 2.获取关于上传文件的基本信息
			String fileName = file.getOriginalFilename();// 真实的文件名称,由于文件名称中存在中文字符，会在下载或读取的过程中出现乱码情况，这里要重新定义文件名称
			
			long fileSize = file.getSize();// 文件大小
			String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);// 文件扩展名---文件类型
			
			String targetPath = "";//压缩包源文件位置
			targetPath = filePath+fileName; //路径和文件名组合成真实的源文件路径
			String zipPath = "";//待解压到的目录位置
			//上传的在线电子杂志需要在前端读取，考虑以后前后端项目部署在不同的环境下，这里需要将压缩包直接解压到前端项目下
			zipPath = propertiesFileUtil.findValue("zyzlcxw_service_path");
			String s_path ="upload"+File.separator+"emagazine"+File.separator;
			zipPath = zipPath+s_path;
			File zip_File = new File(zipPath);
			if(!zip_File.exists())zip_File.mkdirs();
			String beforeFileName = fileName.substring(0, fileName.lastIndexOf("."));//去除点和扩展名的后的 ;如fileName为1234.rar   这里得到的为1234
			zipPath = zipPath + beforeFileName; 
			//zmarke电子杂志制作工具在生成在线杂志时 有它自己的规则，所以这里要组装路径
			beforeFileName = beforeFileName+File.separator+"online.html";
			
			
			File targetFile = new File(targetPath);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}

			try {
				file.transferTo(targetFile);//拷贝文件结束
				
				//解压缩文件成功后，删除源文件
				ZipHelper.uf_DecompressFile(targetPath, zipPath);//解压文件
				
				/*File zipFile = new File(targetPath);
				if(zipFile.exists()){
					zipFile.delete();
				}*/
				
			} catch (Exception e) {
				// return error("上传文件失败");
			}
			Annex annex = new Annex();
			annex.setObjectTable(objectTable);
			annex.setObjectId(objectId);
			annex.setCfilename(fileName);
			annex.setCfiletype(fileExt);
			annex.setNfilesize(fileSize);
			annex.setCannextype(annexType);// 文件类型
			annex.setBbinarydata("");
			annex.setIdowncount(0L);// 下载次数
//			annex.setCfilepath(urlPath + beforeFileName);// 文件路径
			annex.setCfilepath(s_path+beforeFileName);
			annex.setDcreatetime(DateUtil.getTimestamp());
			annex.setNcreateid( (Long) session.getAttribute("userId"));
			annex.setCvalid(SystemCommon_Constant.VALID_STATUS_1);// 是否可用（默认上传的附件都是可用
			list.add(annex);
		}
		return list;
	}
	

	public static List commonUploadFiles(List<Annex> nexlist, String idString) {
		List list = new ArrayList();
		StringBuffer b = new StringBuffer();
		for(int i=0;i<nexlist.size();i++){
			Annex n = nexlist.get(i);
			b.append(n.getCfilename()).append(",");
		}
		String fileName = "";
		fileName= b.toString();
		fileName = fileName.substring(0,fileName.lastIndexOf(","));
		Map map = new HashMap();
		//这里Map中放入的顺序规定为如下顺序，因为这样方便前台的取值按顺序操作
		map.put("fileName", fileName);   //第一位
		map.put("idString", idString);	//第二位
		list.add(map);
		return list;
	}


	/**
	 * 
	 * @Description: 删除文件
	 * @author yanghui 
	 * @Created 2014-3-7
	 * @param oldpath
	 */
	public static void deleteFile(String oldpath) {
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		//取得配置文件配置的上传路径
		 String rootPath = propertiesFileUtil.findValue("uploadPath");
		 rootPath = rootPath+oldpath;
		 File file = new File(rootPath);
		 if(file.exists()){
			 file.delete();
		 }
	}
	
	
	
	  public void loadPic(HttpServletRequest request, HttpServletResponse response,String picPath)
	  {
	//    String picPath = request.getParameter("picPath");
	    try
	    {
	      String temp_picPath = new String(picPath.getBytes("ISO-8859-1"), "UTF-8");
	      picPath = URLDecoder.decode(temp_picPath, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	    ServletOutputStream os = null;
	    FileInputStream fis = null;
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0L);
	    response.setContentType("image/jpeg");
	    
	    PropertiesFileUtil pfu = new PropertiesFileUtil();
	    String filePath = pfu.findValue("uploadPath");

	    if (!DataUtil.isEmpty(picPath))
	    {
	      File photoFile = new File(filePath + picPath);
	      try {
	        if ((photoFile != null) && (photoFile.exists())) {
	          os = response.getOutputStream();
	          fis = new FileInputStream(photoFile);
	          byte[] buffer = new byte[1024];
	          int b;
	          while ((b = fis.read(buffer)) != -1)
	          {
	            os.write(buffer, 0, b);
	          }
	          os.flush();
	          os.close();
	          fis.close();
	        }
	      } catch (IOException e) {
	        logger.error("读取图片出错！", e);
	      }
	    }
	  }

	  /**
	   * 
	   * @Description:  在项目中  新闻资讯  奖品管理模块需要用到图片剪裁功能   这个方法包括新增和修改时的图片剪切
	   * 	在修改时需要判断picturePath与oldPicturePath是否相等，相等则说明图片没有被修改，否则图片为新传的图片
	   * @author yanghui 
	   * @Created 2014-3-27
	   * @param request
	   * @param picturePath  新增或修改时的图片路径
	   * @param oldPicturePath  修改时该图片的原来的路径（当为空时代表为新增操作） 
	   * @return
	   */
	  public String cutImage(HttpServletRequest request,String picturePath,String oldPicturePath){
			String newImg ="";
			if(StringUtils.isNotBlank(picturePath)){
				if(StringUtils.isEmpty(oldPicturePath)){ //说明是新增时剪切图片
					newImg = cutImgCommont(request,picturePath);
				}else{
					if(!picturePath.equals(oldPicturePath)){ //是修改时重新上传了图片进行剪切
						newImg = cutImgCommont(request,picturePath);
					}
				}
			}
			return newImg;
	  }
	  
	  
	  public String cutImgCommont(HttpServletRequest request, String picturePath){
		//用来剪裁图片的坐标及宽高
			String x = request.getParameter("x");
			String y = request.getParameter("y");
			String w = request.getParameter("w");
			String h = request.getParameter("h");
			String newImg ="";
			
			String saveDir = request.getSession().getServletContext().getRealPath("/");
			int index = picturePath.lastIndexOf(".");
		      String imgName = picturePath.substring(0, index);
		      String ext = picturePath.substring(index);
		      if (StringUtils.isNotBlank(imgName)) {
		        imgName = imgName.replace("temp/", "");
		      }
		      newImg = imgName + ext;
		      String sourcePath = saveDir + picturePath.replace("/", File.separator);
		      String descPath = saveDir + newImg.replace("/", File.separator);
		      int width = Integer.parseInt(w);
		      int height = Integer.parseInt(h);
		      int rx = Integer.parseInt(x);
		      int ry = Integer.parseInt(y);
		      ImgCutUtil.cut(rx, ry, width, height, sourcePath, descPath);
		      return newImg;
	  }


	  /**
	   * 
	   * @Description: 针对客户端上传企业空间中图片使用的方法
	   * @author YangHui 
	   * @Created 2014-6-5
	   * @param list2
	   * @return
	   */
	public static List<EnterpriseRoomPicture> uploadEnterpriseRoomPicture(Long id,
			List<MultipartFile> list2) {
		List<EnterpriseRoomPicture> list = new ArrayList<EnterpriseRoomPicture>();
		if (list2 != null) {
			for (MultipartFile file : list2) {
				if ( file!=null && file.getSize() > 0) {
					// 说明存在上传附件
					PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
					//取得配置文件配置的上传路径
					 String path = propertiesFileUtil.findValue("uploadPath");
					 String urlPath = "";//用来保存到数据库中的相对路径
					
					// 定义文件存放公共系统盘下的共同目录
					String filePath = path
							+ SystemCommon_Constant.FILE_UPLOAD_MAIN_DIRECTORY
							+ "enterprise";
					File uploadDir = new File(filePath);
					// 1.目录不存在，创建目录
					if (!uploadDir.exists()) {
						uploadDir.mkdirs();
					}

					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					String ymd = sdf.format(new Date());
					filePath += File.separator + ymd + File.separator;
					urlPath = SystemCommon_Constant.FILE_UPLOAD_MAIN_DIRECTORY
							+ "enterprise"+File.separator+ymd+File.separator;
					File dirFile = new File(filePath);
					if (!dirFile.exists()) {
						dirFile.mkdirs();
					}

					// 2.获取关于上传文件的基本信息
					String fileName = file.getOriginalFilename();// 真实的文件名称,由于文件名称中存在中文字符，会在下载或读取的过程中出现乱码情况，这里要重新定义文件名称
					
					long fileSize = file.getSize();// 文件大小
					String fileExt = fileName.substring(fileName
							.lastIndexOf(".") + 1);// 文件扩展名---文件类型
					
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
					String ymd1 = sdf1.format(new Date());

					//为了防止磁盘中的文件有重复，这里对上传的文件名称做处理
					String newFileName =  ymd1+"_"+ new Random().nextInt(1000) + "." + fileExt;//通过时间戳定义的新文件名称
					File targetFile = new File(filePath, newFileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					try {
						file.transferTo(targetFile);
					} catch (Exception e) {
						// return error("上传文件失败");
					}
					
					EnterpriseRoomPicture pic = new EnterpriseRoomPicture();
					pic.setCreateTime(DateUtil.getTimestamp());
					pic.setPicturePath(urlPath+newFileName);
					pic.setValid(SystemCommon_Constant.VALID_STATUS_1);
					pic.setParentId(id);
					list.add(pic);
				}
			}
		}
		return list;
	}


	/**
	 * 
	 * @Description: 判断磁盘上是否存在该路径所对应的文件
	 * @author YangHui 
	 * @Created 2014-6-23
	 * @param lj
	 * @return
	 */
	public static boolean checkFileIsExist(String lj) {
		boolean flag  = false;
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		//取得配置文件配置的上传路径
		 String path = propertiesFileUtil.findValue("uploadPath");
		 String filePath = path+lj;
		 File file = new File(filePath);
		 flag = file.exists();
		return flag;
	}
	
	
	
	
/**
 * 
 * @Description: 汽车类缺陷采集时上传附件处理方法
 * @author YangHui 
 * @Created 2014-6-26
 * @param list
 * @param session
 * @param objectId
 * @param objectTable
 * @param businessDir
 * @param annexType
 * @return
 */
	public static List<Annex> uploadFile(List<MultipartFile> list,
			HttpSession session, Long objectId, String objectTable,
			String businessDir, String annexType) {
		List list1 = new ArrayList();
		if (DataUtil.listIsNotNull(list)) {
			for (MultipartFile file : list) {
				list1 = commonAnnexList(file,session,objectId,objectTable,businessDir,annexType,list1);
			}
		}
		return list1;
	}
}
