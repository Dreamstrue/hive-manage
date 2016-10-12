package com.hive.contentmanage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.contentmanage.entity.Picturenews;
import com.hive.contentmanage.model.PictureNewBean;
import com.hive.contentmanage.service.PictureNewsService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("picture")
public class PictureNewsController extends BaseController {
	private static final String PREFIX = "contentmanage/picturenews";
	private static final String OBJECT_TABLE = "F_PICTURENEWS";
	private static final String BUSINESS_DIR_NEW = SystemCommon_Constant.XWTP_01;
	private static final String BUSINESS_DIR_ANNEX = SystemCommon_Constant.XWFJ_02;

	@Resource
	private PictureNewsService pictureNewsService;

	@Resource
	private AnnexService annexService;

	@RequestMapping("/manage")
	public String manage() {
		return PREFIX + "/pictureNewManage";
	}

	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(
			RequestPage page,
			@RequestParam(value = "keys", required = false, defaultValue = "") String keys) {
		return this.pictureNewsService.dataGrid(page, keys);
	}

	@RequestMapping("/add")
	public String add() {
		return PREFIX + "/pictureNewAdd";
	}

	@RequestMapping("/insert")
	@ResponseBody
	public Map<String, Object> insert(
			Picturenews picture,
			HttpSession session,
			String chasannex,
			@RequestParam(value = "imgfile", required = false) MultipartFile file,
			@RequestParam(value = "annexFile", required = false) MultipartFile annexFile) {
		String title = picture.getTitle();
		int size = this.pictureNewsService.getNavMenuByName(title);
		if (size > 0) {
			return error("新闻标题【" + title + "】已存在");
		}

		String filePath = AnnexFileUpLoad.writeContentToFile(
				picture.getContent(), BUSINESS_DIR_NEW, "","");
		picture.setContent(filePath);

		picture.setNcreateid((Long) session.getAttribute("userId"));
		picture.setCreateTime(DateUtil.getTimestamp());
		picture.setAuditStatus("0");
		picture.setValid("1");
		picture.setIviewcount(Long.valueOf(0L));

		if (file.getSize() > 0L) {
			List list = AnnexFileUpLoad.uploadImageFile(file, session,
					new Long(0L), OBJECT_TABLE, BUSINESS_DIR_NEW, SystemCommon_Constant.ANNEXT_TYPE_XWTP);
			String picpath = ((Annex) list.get(0)).getCfilepath();
			picture.setCpicpath(picpath);

			this.pictureNewsService.save(picture);
			this.annexService.saveAnnexList(list,
					String.valueOf(picture.getId()));
		} else {
			return error("请选择新闻图片");
		}
		if(chasannex.equals("1")){
			if (annexFile.getSize() > 0L) {
				List list1 = AnnexFileUpLoad.uploadImageFile(annexFile, session,
						picture.getId(), OBJECT_TABLE, BUSINESS_DIR_NEW, SystemCommon_Constant.ANNEXT_TYPE_XWFJ);
				this.annexService.saveAnnexList(list1,
						String.valueOf(picture.getId()));
			} 
		}
		return success("保存成功", picture);
	}

	@RequestMapping("/edit")
	public String edit(Model model, @RequestParam("id") Long id,
			@RequestParam("opType") String opType) {
		Picturenews in = (Picturenews) this.pictureNewsService.get(id);
		byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getContent());
		in.setContent(new String(bt));
		model.addAttribute("vo", in);
		model.addAttribute("opType", opType);

		List imgList = this.annexService.getAnnexInfoByObjectIdToPictureNew1(id, SystemCommon_Constant.ANNEXT_TYPE_XWTP);
		model.addAttribute("img", imgList.get(0));
		List list = this.annexService.getAnnexInfoByObjectIdToPictureNew1(id,SystemCommon_Constant.ANNEXT_TYPE_XWFJ);
		model.addAttribute("annex", list);
	    try {
	    	  model.addAttribute("ids", list.get(0));
		}
	    finally{
	    	return PREFIX + "/pictureNewEdit";
		}
		}
		

	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(
			PictureNewBean bean,
			HttpSession session,
			@RequestParam(value = "imgfile", required = false) MultipartFile file,
			@RequestParam(value = "files", required = false) MultipartFile files,
			@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "imgId", required = false) Long imgId,
			@RequestParam(value = "idString", required = false) String idString) {
		Picturenews picture = (Picturenews) this.pictureNewsService.get(bean
				.getId());
		String oldPath = picture.getContent();
		String filePath = AnnexFileUpLoad.writeContentToFile(bean.getContent(),
				BUSINESS_DIR_NEW, oldPath,"");
		try {
			PropertyUtils.copyProperties(picture, bean);
			picture.setContent(filePath);
		} catch (Exception e) {
			e.printStackTrace();
			return error("属性拷贝错误！");
		}

		picture.setNmodifyid((Long) session.getAttribute("userId"));
		picture.setDmodifytime(DateUtil.getTimestamp());

		picture.setAuditStatus("0");

		String imageExist = bean.getImageExist();
		if (!imageExist.equals("yes")) {
			if (imageExist.equals("no")) {
				if (file.getSize() > 0L) {
					Annex nex = (Annex) this.annexService.get(imgId);
					nex.setCvalid("0");
					this.annexService.update(nex);

					List list = AnnexFileUpLoad.uploadImageFile(file, session,
							bean.getId(), OBJECT_TABLE, BUSINESS_DIR_NEW,
							SystemCommon_Constant.ANNEXT_TYPE_XWTP);
					String picpath = ((Annex) list.get(0)).getCfilepath();
					picture.setCpicpath(picpath);
					this.annexService.saveAnnexList(list,
							String.valueOf(bean.getId()));
				} else {
					return error("请选择上传新闻图片");
				}
			}

		}
		//附件处理
		String fileExist = bean.getFileExist();
		if(!fileExist.equals("yes")){
			if (bean.getChasannex().equals("1")) {
				if (fileExist.equals("no")) {
					if (files.getSize() > 0L) {
						if (StringUtils.isNotBlank(ids)) {
							Annex nex = (Annex) this.annexService.get(Long.parseLong(ids));
							if (nex!=null) {
								nex.setCvalid("0");
								this.annexService.update(nex);
							}
						}
						List list = AnnexFileUpLoad.uploadImageFile(files, session,
								bean.getId(), OBJECT_TABLE, BUSINESS_DIR_NEW,
								SystemCommon_Constant.ANNEXT_TYPE_XWFJ);
						this.annexService.saveAnnexList(list,
								String.valueOf(bean.getId()));
						picture.setChasannex("1");
					} 
				}
			}else {
				if (StringUtils.isNotBlank(ids)) {
					Annex nex = (Annex) this.annexService.get(Long.parseLong(ids));
					if (nex!=null) {
						nex.setCvalid("0");
						this.annexService.update(nex);
					}
				}
				picture.setChasannex("0");
			}
		}
		Long createId = (Long) session.getAttribute("userId");
		this.pictureNewsService.update(picture);
		return success("修改成功");
	}

	@RequestMapping("detail")
	public String detail(Model model, @RequestParam("id") Long id) {
		Picturenews in = (Picturenews) this.pictureNewsService.get(id);
		byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getContent());
		in.setContent(new String(bt));
		model.addAttribute("vo", in);
		
		List imgList = this.annexService.getAnnexInfoByObjectIdToPictureNew1(id, SystemCommon_Constant.ANNEXT_TYPE_XWTP);
		if (imgList != null && imgList.size() > 0)
			model.addAttribute("img", imgList.get(0));
		List list = this.annexService.getAnnexInfoByObjectIdToPictureNew1(id,SystemCommon_Constant.ANNEXT_TYPE_XWFJ);
		model.addAttribute("annex", list);

		return PREFIX + "/pictureNewDetails";
	}

	@RequestMapping("/approve")
	public String approve(Model model, @RequestParam("id") Long id,
			@RequestParam("opType") String opType) {
		Picturenews in = (Picturenews) this.pictureNewsService.get(id);
		byte[] bt = AnnexFileUpLoad.getContentFromFile(in.getContent());
		in.setContent(new String(bt));
		model.addAttribute("vo", in);

		model.addAttribute("opType", opType);

		List imgList = this.annexService.getAnnexInfoByObjectIdToPictureNew1(id, SystemCommon_Constant.ANNEXT_TYPE_XWTP);
		model.addAttribute("img", imgList.get(0));

		List list = this.annexService.getAnnexInfoByObjectIdToPictureNew1(id,SystemCommon_Constant.ANNEXT_TYPE_XWFJ);
		model.addAttribute("annex", list);
		return PREFIX + "/pictureNewEdit";
	}

	

	@RequestMapping("/approveAction")
	@ResponseBody
	public Map<String, Object> approveAction(
			PictureNewBean bean,
			HttpSession session,
			@RequestParam(value = "imgfile", required = false) MultipartFile file,
			@RequestParam(value = "files", required = false) MultipartFile files,
			@RequestParam(value = "ids", required = false) String ids,
			@RequestParam(value = "imgId", required = false) String imgId,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "idString", required = false) String idString) {
																					
			
		Picturenews picture = (Picturenews) this.pictureNewsService.get(bean
				.getId());
		//查看是否成功上传了附件，以确定将pciture的chasannex值。
		/*	
		int size = this.annexService.getAllValidAnnexSize(bean.getId().toString(),
				"pictureNew");

		String chasannex = bean.getChasannex();

		boolean flag = AnnexFileUpLoad.checkHasAnnex(chasannex, idString, size);

		if (!flag) {
			bean.setChasannex("0");
		}
		 */
		String oldPath = picture.getContent();
		String filePath = AnnexFileUpLoad.writeContentToFile(bean.getContent(),
				BUSINESS_DIR_NEW, oldPath,"");
		try {
			PropertyUtils.copyProperties(picture, bean);
			picture.setContent(filePath);
		} catch (Exception e) {
			e.printStackTrace();
			return error("属性拷贝错误！");
		}
		picture.setNauditid((Long) session.getAttribute("userId"));
		picture.setDaudittime(DateUtil.getTimestamp());
		String msg = "";
		if (bean.getCauditstatus().equals("1")){
			picture.setAuditStatus(SystemCommon_Constant.AUDIT_STATUS_1);
			msg = "审核通过";
		}
		else if (bean.getCauditstatus().equals("2")) {
			picture.setAuditStatus(SystemCommon_Constant.AUDIT_STATUS_2);
			msg = "审核不通过";
		}

		String imageExist = bean.getImageExist();
		if (!imageExist.equals("yes")) {
			if (imageExist.equals("no")) {
				if (file.getSize() > 0L) {
					Annex nex = (Annex) this.annexService.get(imgId);
					nex.setCvalid("0");
					this.annexService.update(nex);

					List list = AnnexFileUpLoad.uploadImageFile(file, session,
							bean.getId(), OBJECT_TABLE, BUSINESS_DIR_NEW,
							"image");
					String picpath = ((Annex) list.get(0)).getCfilepath();
					picture.setCpicpath(picpath);
					this.annexService.saveAnnexList(list,
							String.valueOf(bean.getId()));
				} else {
					return error("请选择上传新闻图片");
				}
			}

		}
		//附件处理
		String fileExist = bean.getFileExist();
		if(!fileExist.equals("yes")){
		if (bean.getChasannex().equals("1")) {
				if (fileExist.equals("no")) {
					if (files.getSize() > 0L) {
						Annex nex = (Annex) this.annexService.get(ids);
						if (nex!=null) {
							nex.setCvalid("0");
							this.annexService.update(nex);
						}
						List list = AnnexFileUpLoad.uploadImageFile(files, session,
								bean.getId(), OBJECT_TABLE, BUSINESS_DIR_NEW,
								SystemCommon_Constant.ANNEXT_TYPE_XWFJ);
						this.annexService.saveAnnexList(list,
								String.valueOf(bean.getId()));
					} 
				}
		}else {
			Annex nex = (Annex) this.annexService.get(ids);
			if (nex!=null) {
				nex.setCvalid("0");
				this.annexService.update(nex);
			}
			picture.setChasannex("0");
		}
		}

		/*
		if (bean.getChasannex().equals("0")) {
			this.annexService.setAnnexIsValidByObjectId(bean.getId().toString(),
					"pictureNew");
		}
		*/
		//循环附件，此处不需要
		/*
		if (!ids.equals("")) {
			String[] arr = ids.split(",");

			if ((arr.length == size) && (StringUtils.isEmpty(idString))) {
				picture.setChasannex("0");
			}
			for (int i = 0; i < arr.length; i++) {
				this.annexService.setAnnexIsValidById(Long.valueOf(arr[i]),
						"pictureNew");
			}
		}
		 	*/
		this.pictureNewsService.update(picture);

		Long createId = (Long) session.getAttribute("userId");
		//this.annexService.updateAnnex(createId.toString(), picture.getId().toString(), idString);

		return success(msg);
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("id") String id) {
		if (StringUtils.isNotEmpty(id)) {
			Picturenews inte = (Picturenews) this.pictureNewsService.get(Long
					.valueOf(id));
			inte.setValid("0");
			this.pictureNewsService.update(inte);
			return success("删除成功！");
		}
		return error("请选择需要删除的对象");
	}

	@RequestMapping("/back")
	@ResponseBody
	public Map<String, Object> back(@RequestParam("id") String id) {
		if (StringUtils.isNotEmpty(id)) {
			Picturenews inte = (Picturenews) this.pictureNewsService.get(Long
					.valueOf(id));
			inte.setValid("1");
			this.pictureNewsService.update(inte);
			return success("恢复成功！");
		}
		return error("请选择需要删除的对象");
	}

	@RequestMapping("/uploadFiles")
	public void uploadFiles(
			@RequestParam(value = "file", required = false) MultipartFile[] files,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		List list = new ArrayList();
		Long objectId = new Long(0L);
		session.setAttribute("userId", new Long(0L));
		List nexlist = AnnexFileUpLoad.uploadFile(files, session, objectId,
				OBJECT_TABLE, BUSINESS_DIR_ANNEX, null);
		String idString = this.annexService.saveAnnexListTwo(nexlist, null);
		list = AnnexFileUpLoad.commonUploadFiles(nexlist, idString);
		String jsonArray = JSONArray.toJSONString(list);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.print(jsonArray);
	}
}