package com.hive.contentmanage.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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
import com.hive.contentmanage.entity.SpecialTopicContent;
import com.hive.contentmanage.entity.SpecialTopicInfo;
import com.hive.contentmanage.model.SpecialTopicBean;
import com.hive.contentmanage.service.SpecialTopicContentService;
import com.hive.contentmanage.service.SpecialTopicInfoService;
import com.hive.util.DateUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("special")
public class SpecialTopicInfoController extends BaseController {
	
	public static final String PREFIX = "contentmanage/specialTopic";
	public static final String OBJECT_TABLE = "F_SPECIALTOPICINFO";
	public static final String BUSINESS_DIR = SystemCommon_Constant.RDZT_08;
	
	@Resource
	private SpecialTopicInfoService infoService;
	@Resource
	private SpecialTopicContentService contentService;
	@Resource
	private AnnexService annexService;
	
	
	@RequestMapping("manage")
	public String manage(){
		return PREFIX+"/specialManage";
	}
	
	
	@RequestMapping("/treegrid")
	@ResponseBody
	public List<SpecialTopicInfo> treegrid(){
		List<SpecialTopicInfo> list = infoService.allSpecialInfos();
		return list;
	}
	
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(RequestPage page,
			@RequestParam(value = "keys", required = false, defaultValue = "") String keys,@RequestParam(value="pid") String pid){
		return infoService.DataGrid(page,keys,pid);
		
	}
	
	@RequestMapping("/specialList")
	@ResponseBody
	public List<SpecialTopicInfo> specialList(){
		List<SpecialTopicInfo> list = infoService.specialList();
		return list;
	}
	
	@RequestMapping("/add")
	public String add(){
		return PREFIX+"/specialAdd";
	}
	
	//添加专题下的栏目
	@RequestMapping("/addSub")
	public String addSub(Model model,@RequestParam(value="pid") String pid){
		model.addAttribute("pid", pid);
		return PREFIX+"/specialSubAdd";
	}

	
	/**
	 * 
	 * @Description: 新增专题以及专题下的子栏目专题
	 * @author yanghui 
	 * @Created 2013-10-29
	 * @param info   新增专题或栏目信息
	 * @param session
	 * @param file  上传的图片信息
	 * @param addType  可以为空，当为空时说明为新增主专题，专题图片必须要上传，当不为空时，为新增专题下栏目，专题图片可以不上传
	 * @return
	 */
	@RequestMapping("/insert")
	@ResponseBody
	public Map<String,Object> insert(SpecialTopicInfo info,HttpSession session,@RequestParam(value="imgfile") MultipartFile file,@RequestParam(value="addType",required=false,defaultValue="") String addType){
		
		info.setNcreateid((Long) session
				.getAttribute("userId"));
		info.setDcreatetime(DateUtil.getTimestamp());
		info.setCvalid(SystemCommon_Constant.VALID_STATUS_1);
		info.setCauditstatus(SystemCommon_Constant.AUDIT_STATUS_1);
		
		if(addType.equals("sub")){
			List<Annex> anlist = new ArrayList<Annex>();
			//添加专题下的栏目
			if(file.getSize()>0){
				anlist = AnnexFileUpLoad.uploadImageFile(file,session,new Long(0),OBJECT_TABLE,BUSINESS_DIR,SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
				String picpath = anlist.get(0).getCfilepath();
				info.setCspetoppicpath(picpath);
			}
			infoService.save(info);
			annexService.saveAnnexList(anlist,String.valueOf(info.getId()));
		}else {
			//添加主专题
			if(file.getSize()>0){
				List<Annex> list = AnnexFileUpLoad.uploadImageFile(file,session,new Long(0),OBJECT_TABLE,BUSINESS_DIR,SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
				String picpath = list.get(0).getCfilepath();
				info.setCspetoppicpath(picpath);
				infoService.save(info);
				annexService.saveAnnexList(list,String.valueOf(info.getId()));
			}else return error("请选择新闻图片");
			
		}
		return success("添加成功");
	}
	
	//修改专题
	@RequestMapping("/edit")
	public String edit(Model model,@RequestParam(value="id") Long id){
		model.addAttribute("vo", infoService.get(id));
		//取得专题图片信息
		List<Annex> imgList = annexService.getAnnexInfoByObjectIdAndAnnexType(id);
		if(imgList.size()>0){
			model.addAttribute("img", imgList.get(0));//因为只上传了一张图片新闻，这里就直接取出来
		}
		return PREFIX+"/specialEdit";
	}
	
	//修改专题下的栏目
	@RequestMapping("/editSub")
	public String editSub(Model model,@RequestParam(value="id") Long id){
		model.addAttribute("vo", infoService.get(id));
		//取得专题图片信息(可以没有图片)
		List<Annex> imgList = annexService.getAnnexInfoByObjectIdAndAnnexType(id);
		if(imgList.size()>0){
			model.addAttribute("img", imgList.get(0));//因为只上传了一张图片新闻，这里就直接取出来
		}
		
		return PREFIX+"/specialSubEdit";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Map<String,Object> update(SpecialTopicBean bean,HttpSession session,@RequestParam(value="imgfile") MultipartFile file){
		SpecialTopicInfo top = infoService.get(bean.getId());
		
		top.setNmodifyid((Long) session
				.getAttribute("userId"));
		top.setDmodifytime(DateUtil.getTimestamp());
		top.setText(bean.getText());
		top.setIsortorder(bean.getIsortorder());
		
		//处理上传新闻图片
		String imageExist = bean.getImageExist();
		if(imageExist.equals("yes")){
			//说明已经存在新闻图片，且该新闻图片是原来新增时的
			
		}else if(imageExist.equals("no")){
			//说明原来新增时上传的图片被删除了，这时需要判断是否存在新上传的图片
			if(file.getSize()>0){
				//存在新上传的图片
				List<Annex> list = AnnexFileUpLoad.uploadImageFile(file,session,bean.getId(),OBJECT_TABLE,BUSINESS_DIR,SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
				String picpath = list.get(0).getCfilepath();
				top.setCspetoppicpath(picpath);
				annexService.saveAnnexList(list,String.valueOf(bean.getId()));
				
				//保存新的图片后，把原来的图片设为不可用
				String virtualId = bean.getVirtualPid();
				if(StringUtils.isNotEmpty(virtualId)){
					Annex nex = annexService.get(Long.valueOf(virtualId));
					nex.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
					annexService.update(nex);
				}
			}else return error("请选择上传专题图片");
			
		}
		infoService.update(top);
		
		return success("修改成功");
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam(value = "id") String id) {
		if (StringUtils.isNotEmpty(id)) {
			SpecialTopicInfo inte = infoService.get(Long.valueOf(id));
			inte.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
			infoService.update(inte);
			return success("删除成功！");
		} else
			return error("请选择需要删除的对象");
	}

	@RequestMapping("/back")
	@ResponseBody
	public Map<String, Object> back(@RequestParam(value = "id") String id) {
		if (StringUtils.isNotEmpty(id)) {
			SpecialTopicInfo inte = infoService.get(Long.valueOf(id));
			inte.setCvalid(SystemCommon_Constant.VALID_STATUS_1);
			infoService.update(inte);
			return success("恢复成功！");
		} else
			return error("请选择需要恢复的对象");
	}
	
	
	//删除专题---循环删除下属的栏目
	@RequestMapping("/deleteAll")
	@ResponseBody
	public Map<String, Object> deleteAll(@RequestParam(value = "id") String id) {
		if (StringUtils.isNotEmpty(id)) {
			SpecialTopicInfo inte = infoService.get(Long.valueOf(id));
			inte.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
			infoService.update(inte);
			
			//當刪除的為專題時，可能存在子欄目信息，此時需要刪除子欄目以及欄目下的專題內容
			//如果list為空，可能不存在子欄目，或者它本身就屬於欄目
			List<SpecialTopicInfo> list = infoService.getSpecialInfoByPid(Long.valueOf(id));
			if(list.size()>0){
				for(SpecialTopicInfo info:list){
					info.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
					infoService.update(info);
					
					List<SpecialTopicContent> contentList = contentService.getContentListByPid(info.getId());
					for(SpecialTopicContent cont:contentList){
						if(cont!=null){
							cont.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
							contentService.update(cont);
						}
						
					}
				}
			}
			
			
			List<SpecialTopicContent> contentList = contentService.getContentListByPid(inte.getId());
			for(SpecialTopicContent cont:contentList){
				if(cont!=null){
					cont.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
					contentService.update(cont);
				}
				
			}
			
			return success("删除成功！");
		} else
			return error("请选择需要删除的对象");
	}

	@RequestMapping("/backAll")
	@ResponseBody
	public Map<String, Object> backAll(@RequestParam(value = "id") String id) {
		if (StringUtils.isNotEmpty(id)) {
			SpecialTopicInfo inte = infoService.get(Long.valueOf(id));
			inte.setCvalid(SystemCommon_Constant.VALID_STATUS_1);
			infoService.update(inte);
			
			List<SpecialTopicInfo> list = infoService.getSpecialInfoByPid(Long.valueOf(id));
			if(list.size()>0){
				for(SpecialTopicInfo info:list){
					info.setCvalid(SystemCommon_Constant.VALID_STATUS_1);
					infoService.update(info);
				}
			}
			return success("恢复成功！");
		} else
			return error("请选择需要恢复的对象");
	}
	
	
	/**
	 * 
	 * @Description: 修改专题下的栏目信息
	 * @author yanghui 
	 * @Created 2013-10-29
	 * @param bean
	 * @param session
	 * @param file
	 * @return
	 */
	@RequestMapping("/updateSub")
	@ResponseBody
	public Map<String,Object> updateSub(SpecialTopicBean bean,HttpSession session,@RequestParam(value="imgfile") MultipartFile file){
		SpecialTopicInfo top = infoService.get(bean.getId());
		
		top.setNmodifyid((Long) session
				.getAttribute("userId"));
		top.setDmodifytime(DateUtil.getTimestamp());
		top.setText(bean.getText());
		top.setIsortorder(bean.getIsortorder());
		
		//处理上传新闻图片
		String imageExist = bean.getImageExist();
		if(imageExist.equals("yes")){
			//说明已经存在新闻图片，且该新闻图片是原来新增时的
			
		}else if(imageExist.equals("no")){
			//说明原来新增时上传的图片被删除了，这时需要判断是否存在新上传的图片
			if(file.getSize()>0){
				//存在新上传的图片
				List<Annex> list = AnnexFileUpLoad.uploadImageFile(file,session,bean.getId(),OBJECT_TABLE,BUSINESS_DIR,SystemCommon_Constant.ANNEXT_TYPE_IMAGE);
				String picpath = list.get(0).getCfilepath();
				top.setCspetoppicpath(picpath);
				annexService.saveAnnexList(list,String.valueOf(bean.getId()));
				
				//保存新的图片后，把原来的图片设为不可用
				String virtualId = bean.getVirtualPid();
				if(StringUtils.isNotEmpty(virtualId)){
					Annex nex = annexService.get(Long.valueOf(virtualId));
					nex.setCvalid(SystemCommon_Constant.VALID_STATUS_0);
					annexService.update(nex);
				}
			}
		}
		infoService.update(top);
		
		return success("修改成功");
	}
}
