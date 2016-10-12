package com.hive.clientinterface.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Service;

import com.hive.common.SystemCommon_Constant;
import com.hive.surveymanage.entity.Survey;
import com.hive.surveymanage.model.SurveyVo;
import com.hive.surveymanage.service.SurveyIndustryService;
import com.hive.systemconfig.entity.SysObjectParameconfig;
import com.hive.systemconfig.entity.SystemConfig;
import com.hive.systemconfig.service.SysObjectParameconfigService;
import com.hive.systemconfig.service.SystemConfigService;
import com.hive.util.DataUtil;

import dk.dao.BaseDao;
import dk.model.DataGrid;
import dk.model.RequestPage;
import dk.service.BaseService;

/**
 * @description 
 * @author 燕珂
 * @createtime 2014-4-8 上午10:22:28
 */
@Service
public class SurveyInterfaceService extends BaseService<Survey> {
	@Resource
	private BaseDao<Survey> surveyDao;
	@Override
	protected BaseDao<Survey> getDao() {
		return surveyDao;
	}
	@Resource
	private SystemConfigService systemConfigService;
	@Resource
	private SysObjectParameconfigService sysobjectParameConfigService;
	@Resource
	private SurveyIndustryService surveyIndustryService;
	
	public DataGrid dataGrid(RequestPage page,Long cateId,String type) {
		StringBuffer hql = new StringBuffer(); // 分页查询语句
		StringBuffer counthql = new StringBuffer();// 记录总条数语句
		StringBuffer sb = new StringBuffer();// 查询条件
		hql.append(" from " + getEntityName() + " where 1=1 ");
		counthql.append(" select count(*) from " + getEntityName()+ " where 1=1 ");
		// 审核通过 并且没有被删除
		sb.append("and valid='" + SystemCommon_Constant.VALID_STATUS_1 + "' and auditstatus='" + SystemCommon_Constant.AUDIT_STATUS_1 + "' ");
		//问卷根据行业类别分类查询，由于行业类别存在两级父子关系，所以当查询父级类别时也要把孩子类别问卷全部查到
		sb.append(" and industryid in (select s.id from SurveyIndustry s where (s.id='"+cateId+"' or pid='"+cateId+"') and s.valid='"+SystemCommon_Constant.VALID_STATUS_1+"')");
		
		if("all".equals(type)){
			sb.append(" and status='"+SystemCommon_Constant.SURVEY_STATUS_ON+"' ");
		}
		if("show".equals(type)){ //查询公示的问卷
			sb.append(" and isShow='"+SystemCommon_Constant.SIGN_YES_1+"' ");
		}
		sb.append(" order by createtime desc ");
		List<Survey> list = new ArrayList<Survey>();
		Long count = getDao().count(counthql.append(sb).toString(),new Object[0]).longValue(); // 总数
		list = getDao().find(page.getPage(), page.getRows(),hql.append(sb).toString(), new Object[0]);
		
		
		SystemConfig sc = systemConfigService.getIdByParameCode(SystemCommon_Constant.SURVEY_SET_CODE_ANONYMOUS);
		Long scId = 0L;
		if(sc!=null){
			scId = sc.getId();
		}
		
		
		SystemConfig s = systemConfigService.getIdByParameCode(SystemCommon_Constant.SURVEY_SET_CODE_ISRELSHOP);
		Long sid = 0L;
		if(s!=null){
			sid = s.getId();
		}
		
		List<SurveyVo> vlist = new ArrayList<SurveyVo>();
		for(int i=0;i<list.size();i++){
			//取得是否匿名投票的配置的默认值
			String anonymous = SystemCommon_Constant.SIGN_YES_1;
			//取得是否需要关联具体商铺的默认值
			String isRelShop = SystemCommon_Constant.SIGN_YES_0; //0-不需要
			Survey v = list.get(i); 
			SurveyVo vo = new SurveyVo();
			try { 
				PropertyUtils.copyProperties(vo, v);
				String industryName = surveyIndustryService.get(v.getIndustryid()).getText();
				vo.setIndustryName(industryName); //行业类别名称
				
				List plist = sysobjectParameConfigService.getConfigId("S_SURVEY",v.getId());
				if(DataUtil.listIsNotNull(plist)){
					//是否匿名
					if(plist.contains(scId)){
						SysObjectParameconfig f = sysobjectParameConfigService.getSysObjectParameconfig("S_SURVEY", v.getId(), scId);
						anonymous = f.getCurrentValue().toString();
					}
					//是否需要关联具体商铺
					if(plist.contains(sid)){
						SysObjectParameconfig f = sysobjectParameConfigService.getSysObjectParameconfig("S_SURVEY", v.getId(), sid);
						isRelShop = f.getCurrentValue().toString();
					}
				}
				vo.setAnonymous(anonymous);
				vo.setIsRelShop(isRelShop);
				vlist.add(vo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new DataGrid(count, vlist);
	}

	/**
	 * 
	 * @Description: 
	 * @author YangHui 
	 * @Created 2014-10-15
	 * @param id
	 * @return
	 */
	public SurveyVo getSurveyInfoById(Long id) {

		//是否匿名
		SystemConfig sc = systemConfigService.getIdByParameCode(SystemCommon_Constant.SURVEY_SET_CODE_ANONYMOUS);
		Long scId = 0L;
		if(sc!=null){
			scId = sc.getId();
		}
		
		//是否关联商户
		SystemConfig s = systemConfigService.getIdByParameCode(SystemCommon_Constant.SURVEY_SET_CODE_ISRELSHOP);
		Long sid = 0L;
		if(s!=null){
			sid = s.getId();
		}
		
		
		//取得是否匿名投票的配置的默认值
		String anonymous = SystemCommon_Constant.SIGN_YES_1;
		//取得是否需要关联具体商铺的默认值
		String isRelShop = SystemCommon_Constant.SIGN_YES_0; //0-不需要
		Survey v = get(id); 
		SurveyVo vo = new SurveyVo();
		try { 
			PropertyUtils.copyProperties(vo, v);
			String industryName = surveyIndustryService.get(v.getIndustryid()).getText();
			vo.setIndustryName(industryName); //行业类别名称
			
			List plist = sysobjectParameConfigService.getConfigId("S_SURVEY",v.getId());
			if(DataUtil.listIsNotNull(plist)){
				//是否匿名
				if(plist.contains(scId)){
					SysObjectParameconfig f = sysobjectParameConfigService.getSysObjectParameconfig("S_SURVEY", v.getId(), scId);
					anonymous = f.getCurrentValue().toString();
				}
				//是否需要关联具体商铺
				if(plist.contains(sid)){
					SysObjectParameconfig f = sysobjectParameConfigService.getSysObjectParameconfig("S_SURVEY", v.getId(), sid);
					isRelShop = f.getCurrentValue().toString();
				}
			}
			vo.setAnonymous(anonymous);
			vo.setIsRelShop(isRelShop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return vo;
	}
}
