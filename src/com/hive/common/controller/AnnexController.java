package com.hive.common.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.prizemanage.entity.PrizeInfo;
import com.hive.prizemanage.service.PrizeInfoService;
import com.hive.util.PropertiesFileUtil;
import com.hive.util.Thumbnail;

import dk.controller.BaseController;
/**
 * 
* Filename: AnnexController.java  
* Description: 附件控制类
* Copyright:Copyright (c)2013
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2013-10-23  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2013-10-23 上午9:48:57				yanghui 	1.0
 */
@Controller
@RequestMapping("annex")
public class AnnexController extends BaseController {

	
	private static final String PREFIX = "annex";
	private static Logger logger = Logger.getLogger(AnnexController.class);
	
	@Resource
	private AnnexService annexService;
	
	@Resource
	private PrizeInfoService prizeInfoService;
	
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(@RequestParam(value="id") Long id){
		Annex nex = annexService.get(id);
		nex.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
		annexService.update(nex);
	}
	
	/**
	 * 
	 * @Description:
	 * @author yanghui 
	 * @Created 2013-10-23
	 * @param request
	 * @param response
	 * @param id   下载附件的ID
	 */
	@RequestMapping("/download")
	public void downLoad(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="id") Long id) {
		response.reset();
		response.setContentType("application/x-download");
		
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		//取得配置文件配置的上传路径 
		 String path = propertiesFileUtil.findValue("uploadPath");
		 
		//通过ID查找到相应附件存放的路径
		Annex nex = annexService.get(id);
		String fileurl = nex.getCfilepath();
//		String fileurl = request.getParameter("fileurl");
		fileurl = path+fileurl;
		logger.error(fileurl);
	//	String fileName = new File(fileurl).getName();
		String fileName = nex.getCfilename();
		String newFileName = "";
		try {
			newFileName = new String(fileName.getBytes("gb2312"),"ISO8859-1");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		try {
			response.addHeader("Content-Disposition", "attachment;filename="
					+ newFileName);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		java.io.OutputStream outp = null;
		java.io.FileInputStream in = null;
		try {
			outp = response.getOutputStream();
			in = new FileInputStream(fileurl);

			byte[] b = new byte[1024];
			int i = 0;

			while ((i = in.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			outp.flush();
		} catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
		}
	}
	
	
	
	/**
	 * 
	 * @Description: 针对奖品管理  奖品图片下载（因为没有存入附件表）
	 * @author yanghui 
	 * @Created 2014-3-7
	 * @param request
	 * @param response
	 * @param id
	 */
	@RequestMapping("/downloadImg")
	public void downloadImg(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="id") Long id) {
		response.reset();
		response.setContentType("application/x-download");
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		//取得配置文件配置的上传路径 
		 String path = propertiesFileUtil.findValue("uploadPath");
		//通过ID查找到相应附件存放的路径
		PrizeInfo info = prizeInfoService.get(id);
		String fileurl = info.getPicturePath();
		fileurl = path+fileurl;
		logger.error(fileurl);
		String fileName = info.getPictureName();
		String newFileName = "";
		try {
			newFileName = new String(fileName.getBytes("gb2312"),"ISO8859-1");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		try {
			response.addHeader("Content-Disposition", "attachment;filename="
					+ newFileName);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		java.io.OutputStream outp = null;
		java.io.FileInputStream in = null;
		try {
			outp = response.getOutputStream();
			in = new FileInputStream(fileurl);
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = in.read(b)) > 0) {
				outp.write(b, 0, i);
			}
			outp.flush();
		} catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
		}
	}
	
	
	@RequestMapping("loadPic")
	public void loadPic(@RequestParam(value="picPath") String picPath,HttpServletRequest request, HttpServletResponse response){
		AnnexFileUpLoad annexFileUpload = new AnnexFileUpLoad();
		annexFileUpload.loadPic(request, response,picPath);
	}
	
	
	
	/**
	 * 
	 * @Description:	上传图片的方法  
	 * @author yanghui 
	 * @Created 2014-3-26
	 * @param avatarFile
	 * @param request
	 * @param out
	 */
	  @RequestMapping("/uploadAvatar")
	  public void uploadAvatar(@RequestParam(value="picturefile", required=false) MultipartFile avatarFile, HttpServletRequest request, PrintWriter out)
	  {
	    List list = uploadAvatarFile(request, avatarFile);
	    String  jsonArray = JSONArray.toJSONString(list);
	    out.print(jsonArray);
	  }

	  private List uploadAvatarFile(HttpServletRequest request, MultipartFile file)
	  {
		  Map map = new HashMap();
		  List list = new ArrayList();
		  String path = "";
		  //这里定义上传图片的格式不能大于300×300像素
		int defaultWidth = 300;
		int defaultHeight = 300;
		
	    String uploadDir = "avatar" + File.separator + "temp";
	    String uploadDirAbsolutePath = request.getSession().getServletContext().getRealPath(uploadDir);
	    File uploadDirFile = new File(uploadDirAbsolutePath);
	    if (!uploadDirFile.exists()) {
	      uploadDirFile.mkdir();
	    }

	    if (file == null) {
	    	path = uploadDir + File.separator + "avatar.png";
	    	map.put("flag", "0");
	    	map.put("path", path);
	    	list.add(map);
	      return list;
	    }

	    String newFileAbsolutePath = "";
	    String newFileName = "";
	    if (file.isEmpty()) {
	      System.out.println("文件未上传");
	    }else {
	      String originalFilename = file.getOriginalFilename();
	      String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	      String ymd = sdf.format(new Date());
			
	      newFileName = ymd+"_"+ new Random().nextInt(1000) + suffix;

	      newFileAbsolutePath = uploadDirAbsolutePath + File.separator + newFileName;
	      File restore = new File(newFileAbsolutePath);
	      try {
	        file.transferTo(restore);  //把图片保存的磁盘里
	        try {
				BufferedImage srcImage = ImageIO.read(restore);
				int width = srcImage.getWidth();
				int height = srcImage.getHeight();
				if(width>defaultWidth || height>defaultHeight){
					map.put("flag", "1");
					map.put("message", "上传图片请控制在300×300像素内");
					list.add(map);
					return list;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	        Thumbnail.saveImageAsJpg(newFileAbsolutePath, newFileAbsolutePath, 310, 310, true);
	      }
	      catch (Exception e) {
	        throw new RuntimeException(e);
	      }
	    }

	    if (StringUtils.isNotBlank(uploadDir)) {
	      uploadDir = uploadDir.replace(File.separator, "/");
	    }
	    path = uploadDir + "/" + newFileName;
    	map.put("flag", "0");
    	map.put("path", path);
    	list.add(map);
	    return list;
	  }
}
