/**
 * 
 */
package com.hive.clientinterface.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.util.DataUtil;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;

/**
 * Filename: AnnexInterfaceController.java Description: 附件接口 供文件、图片等的下载使用
 * Copyright:Copyright (c)2014 Company: GuangFan
 * 
 * @author: yanghui
 * @version: 1.0
 * @Create: 2014-5-9 Modification History: Date Author Version
 *          ------------------------------------------------------------------
 *          2014-5-9 下午1:52:33 yanghui 1.0
 */
@Controller
@RequestMapping("interface/annex")
public class AnnexInterfaceController extends BaseController {

	private static final Logger logger = Logger
			.getLogger(AnnexInterfaceController.class);

	@Resource
	private AnnexService annexService;

	/**
	 * 
	 * @Description: 加载图片
	 * @author yanghui
	 * @Created 2014-5-9
	 * @param picPath
	 * @param request
	 * @param response
	 */
	@RequestMapping("loadPic")
	public void loadPic(@RequestParam(value = "picPath") String picPath,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String temp_picPath = new String(picPath.getBytes("ISO-8859-1"),
					"UTF-8");
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

		if (!DataUtil.isEmpty(picPath)) {
			File photoFile = new File(filePath + picPath);
			try {
				if ((photoFile != null) && (photoFile.exists())) {
					os = response.getOutputStream();
					fis = new FileInputStream(photoFile);
					byte[] buffer = new byte[1024];
					int b;
					while ((b = fis.read(buffer)) != -1) {
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

	@RequestMapping("/download")
	public void downLoad(HttpServletRequest request,
			HttpServletResponse response, @RequestParam(value = "id") Long id) {
		response.reset();
		response.setContentType("application/x-download");

		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		// 取得配置文件配置的上传路径
		String path = propertiesFileUtil.findValue("uploadPath");

		// 通过ID查找到相应附件存放的路径
		Annex nex = annexService.get(id);
		String fileurl = nex.getCfilepath();
		fileurl = path + fileurl;
		logger.error(fileurl);
		String fileName = nex.getCfilename();
		String newFileName = "";
		try {
			newFileName = new String(fileName.getBytes("gb2312"), "ISO8859-1");
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
}
