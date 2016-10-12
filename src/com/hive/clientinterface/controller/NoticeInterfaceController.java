package com.hive.clientinterface.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.clientinterface.service.NoticeInterfaceService;
import com.hive.common.AnnexFileUpLoad;
import com.hive.common.SystemCommon_Constant;
import com.hive.common.entity.Annex;
import com.hive.common.service.AnnexService;
import com.hive.contentmanage.entity.CommonContent;
import com.hive.infomanage.entity.InfoNotice;
import com.hive.infomanage.entity.NoticeReceive;
import com.hive.infomanage.service.NoticeReceiveService;
import com.hive.util.DateUtil;
import com.hive.util.PropertiesFileUtil;

import dk.controller.BaseController;
import dk.model.DataGrid;
import dk.model.RequestPage;

@Controller
@RequestMapping("interface/notice")
public class NoticeInterfaceController extends BaseController {
	
	private static final String OBJECT_TABLE = "INFO_NOTICE";
	@Resource
	private NoticeInterfaceService noticeInterfaceService;
	@Resource
	private AnnexService annexService;
	@Resource
	private NoticeReceiveService noticeReceiveService;
	
	/**
	 * 
	 * @Description:  通知公告列表
	 * @author yanghui 
	 * @Created 2014-3-18
	 * @param page
	 * @param bean
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="noticeList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> noticeList(RequestPage page,@RequestParam(value="userId",required=false) Long userId){
		DataGrid dataGrid = noticeInterfaceService.dataGrid(page, userId);
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, dataGrid);
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 查看某条通知公告详细信息
	 * @author yanghui 
	 * @Created 2014-3-18
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="noticeDetail",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> noticeDetail(@RequestParam(value="id") Long id) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		InfoNotice notice = new InfoNotice();
		if(id!=null && id!=0){
			notice =  noticeInterfaceService.get(id);
			byte[] b = AnnexFileUpLoad.getContentFromFile(notice.getContent());
			notice.setContent(new String(b));
		}
		//判断该明细是否存在附件
		List annexList = annexService.getAnnexListByObjectId(id,OBJECT_TABLE);
		if(annexList.size()>0){
			resultMap.put("annexList", annexList);
		}
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, notice);
		
		return resultMap;
	}
	
	/**
	 * 
	 * @Description: 标记通知公告已阅
	 * @author yanghui 
	 * @Created 2014-3-26
	 * @param noticeId
	 * @param userId
	 */
	@RequestMapping(value="noticeRead",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> noticeRead(@RequestParam(value="noticeId") Long noticeId,@RequestParam(value="userId") Long userId){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//首先判断该条信息是否已经被该用户阅读过
		boolean flag = noticeReceiveService.isReadByUser(noticeId,userId);
		NoticeReceive nr = new NoticeReceive();
		if(!flag){
			nr.setNoticeId(noticeId);
			nr.setUserId(userId);
			nr.setReceiveDate(DateUtil.getTimestamp());
			noticeReceiveService.save(nr);
		}
		
		resultMap.put(SystemCommon_Constant.RESULT_KEY_RET, SystemCommon_Constant.RESULT_VALUE_RET_SUCCESS);
		resultMap.put(SystemCommon_Constant.RESULT_KEY_MSG, "");
		resultMap.put(SystemCommon_Constant.RESULT_KEY_DATA, nr);
		return resultMap;
	}
	
	
	@RequestMapping(value="annexDownload",method=RequestMethod.GET)
	public void annexdownload(HttpServletRequest request,
			HttpServletResponse response,@RequestParam(value="id") Long id){
		response.reset();
		response.setContentType("application/x-download");
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		//取得配置文件配置的上传路径 
		 String path = propertiesFileUtil.findValue("uploadPath");
		//通过ID查找到相应附件存放的路径
		Annex nex = annexService.get(id);
		String fileurl = nex.getCfilepath();
		fileurl = path+fileurl;
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
	
	
	@RequestMapping("updatePath")
	@ResponseBody
	public Map<String,Object> updatePath(){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<InfoNotice> list = noticeInterfaceService.getNoticeList();
		for (int i = 0; i < list.size(); i++) {

			InfoNotice cc = list.get(i);
			String oldPath = cc.getContent();

			byte[] b = AnnexFileUpLoad.getContentFromFile(oldPath);
			cc.setContent(new String(b));

			// 取得新增时保存的文件路径（注意：路径保存在内容字段里）
			String filePath = AnnexFileUpLoad.writeContentToFile(
					cc.getContent(), "[01]XXGG", oldPath,"approve");

			cc.setContent(filePath);
			noticeInterfaceService.update(cc);

		}
		return resultMap;
	}

}
