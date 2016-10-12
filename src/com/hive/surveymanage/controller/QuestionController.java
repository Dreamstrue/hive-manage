package com.hive.surveymanage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hive.common.SystemCommon_Constant;
import com.hive.surveymanage.entity.Option;
import com.hive.surveymanage.entity.Question;
import com.hive.surveymanage.service.OptionService;
import com.hive.surveymanage.service.QuestionService;
import com.hive.surveymanage.service.SurveyService;

import dk.controller.BaseController;
import dk.model.DataGrid;

/**
 * <p/>河南广帆信息技术有限公司版权所有
 * <p/>创 建 人：燕珂
 * <p/>创建日期：2013-11-7
 * <p/>创建时间：下午5:18:13
 * <p/>功能描述：问卷题目表Controller
 * <p/>===========================================================
 */
@Controller
@RequestMapping("/questionManage")
public class QuestionController extends BaseController{
	
	private final String PREFIX="surveymanage/surveyManage";
	
	@Resource
	private QuestionService questionService;
	
	@Resource
	private SurveyService surveService;
	@Resource
	private OptionService optionService;
	
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getQuestion(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id !=null && id != -1) {
			model.addAttribute("question", questionService.get(id));
		}
	}

	/**
	 * 功能描述：跳转至问题列表
	 * 创建时间:2013-11-8下午2:33:43
	 * 创建人: 燕珂
	 * 
	 * @param surveyid 投票问卷信息表ID
	 * @return
	 */
	@RequestMapping("/toQuestionList")
	public String toSurveyList(@RequestParam("surveyid") Long surveyid, Model model){
		model.addAttribute("survey", surveService.get(surveyid));
		return PREFIX + "/questionList";
	}
	
	/**
	 * 功能描述：问题信息列表数据加载
	 * 创建时间:2013-11-8下午2:33:43
	 * 创建人: 燕珂
	 * 
	 * @param surveyid 投票问卷信息表ID
	 * @return
	 */
	@RequestMapping("/listQuestion")
	@ResponseBody
	public DataGrid listQuestion(@RequestParam("surveyid") String surveyid){
		return questionService.datagrid(surveyid);
	}
	
	/**
	 * 功能描述：进入问题添加页面
	 * 创建时间:2013-11-8下午4:47:39
	 * 创建人: 燕珂
	 * 
	 * @param surveyid 问卷ID
	 * @return
	 */
	@RequestMapping("/toQuestionAdd")
	public String toQuestionAdd(@RequestParam("questiontype") String questiontype, @RequestParam("surveyid") Long surveyid, Model model){
		model.addAttribute("surveyid", surveyid);
		model.addAttribute("survey", surveService.get(surveyid));
		
		// 获取该问卷的问题的最大序号
		List<Question> questionList = questionService.listQuestion(surveyid);
		int maxSort = 0;
		if(questionList != null){
			maxSort = questionList.size() + 1;
		}
		model.addAttribute("maxSort", maxSort);
		
		String returnJsp = "";
		if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_SELECT.equals(questiontype)) 
			returnJsp = "addQuestion4Select";
		else if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_COMBINATIONSELECT.equals(questiontype)) 
			returnJsp = "addQuestion4CombinationSelect";
		else if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_SCORE.equals(questiontype)) 
			returnJsp = "addQuestion4Score";
		else if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_COMBINATIONSCORE.equals(questiontype)) 
			returnJsp = "addQuestion4CombinationScore";
		else if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_SORT.equals(questiontype)) 
			returnJsp = "addQuestion4Sort";
		else if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_OPEN.equals(questiontype)) 
			returnJsp = "addQuestion4Open";
		return PREFIX + "/" + returnJsp;
	}
	
	/**
	 * 功能描述：保存问题及问题选项
	 * 创建时间:2013-11-11上午11:12:45
	 * 创建人: 燕珂
	 * 
	 * @param question
	 * @return
	 */
	@RequestMapping("/saveQuestion4Select")
	@ResponseBody
	public Map<String,Object> saveQuestion4Select(Question question, @RequestParam("optionText") String[] optionTextArray, @RequestParam(value = "optionRequireInput") String optionRequireInput, HttpSession session){
		// 先保存问题
		Long curUserId = (Long)session.getAttribute("userId");
		question.setCreateid(curUserId);		
		question.setCreatetime(new Date());
		question.setValid(SystemCommon_Constant.VALID_STATUS_1); //初始化  1-可用
		questionService.save(question);
		
		// 再保存选项
		String[] optionRequireInputArray = optionRequireInput.split(",");
		Option option = new Option();
		int count = 0;
		for (int i = 0; i < optionTextArray.length; i++) {
			String optionText = optionTextArray[i];
			if (StringUtils.isNotBlank(optionText)) {
				count ++;
				option.setQuestionid(question.getId());
				option.setOptionText(optionText);
				option.setSort(count);
				option.setRequireinput(optionRequireInputArray[i]);
				option.setCreateid(curUserId);		
				option.setCreatetime(new Date());
				option.setValid(SystemCommon_Constant.VALID_STATUS_1);
				optionService.save(option);
			}
		}
		
		return success("保存成功", question);
	}
	
	/**
	 * 功能描述：保存问题及问题选项
	 * 创建时间:2013-11-11上午11:12:45
	 * 创建人: 燕珂
	 * 
	 * @param question
	 * @return
	 */
	@RequestMapping("/saveQuestion4CombinationSelect")
	@ResponseBody
	public Map<String,Object> saveQuestion4CombinationSelect(Question question, @RequestParam("optionText1") String[] optionText1Array, @RequestParam("optionText2") String[] optionText2Array, HttpSession session){
		// 先保存问题
		Long curUserId = (Long)session.getAttribute("userId");
		question.setCreateid(curUserId);		
		question.setCreatetime(new Date());
		question.setValid(SystemCommon_Constant.VALID_STATUS_1); //初始化  1-可用
		questionService.save(question);
		
		// 再保存选项
		Option option = new Option();
		int count = 0;
		for (int i = 0; i < optionText1Array.length; i++) {
			String optionText = optionText1Array[i];
			if (StringUtils.isNotBlank(optionText)) {
				count ++;
				option.setQuestionid(question.getId());
				option.setOptionText(optionText);
				option.setSort(count);
				option.setRequireinput("0");
				option.setOptiontype("1"); // 组合选择题显示在左侧的选项
				option.setCreateid(curUserId);		
				option.setCreatetime(new Date());
				option.setValid(SystemCommon_Constant.VALID_STATUS_1);
				optionService.save(option);
			}
		}
		
		// 再保存选项
		int count2 = 0;
		for (int i = 0; i < optionText2Array.length; i++) {
			String optionText = optionText2Array[i];
			if (StringUtils.isNotBlank(optionText)) {
				count2 ++;
				option.setQuestionid(question.getId());
				option.setOptionText(optionText);
				option.setSort(count2);
				option.setRequireinput("0");
				option.setOptiontype("2"); // 组合选择题显示在顶部的选项
				option.setCreateid(curUserId);		
				option.setCreatetime(new Date());
				option.setValid(SystemCommon_Constant.VALID_STATUS_1);
				optionService.save(option);
			}
		}
		
		return success("保存成功", question);
	}
	
	/**
	 * 功能描述：保存问题及问题选项
	 * 创建时间:2013-11-11上午11:12:45
	 * 创建人: 燕珂
	 * 
	 * @param question
	 * @return
	 */
	@RequestMapping("/saveQuestion4Score")
	@ResponseBody
	public Map<String,Object> saveQuestion4Score(Question question, @RequestParam("optionText") String[] optionTextArray, @RequestParam("score") int[] scoreArray, @RequestParam(value = "optionRequireInput") String optionRequireInput, HttpSession session){
		// 先保存问题
		Long curUserId = (Long)session.getAttribute("userId");
		question.setCreateid(curUserId);		
		question.setCreatetime(new Date());
		question.setValid(SystemCommon_Constant.VALID_STATUS_1); //初始化  1-可用
		questionService.save(question);
		
		// 再保存选项
		String[] optionRequireInputArray = optionRequireInput.split(",");
		Option option = new Option();
		int count = 0;
		for (int i = 0; i < optionTextArray.length; i++) {
			String optionText = optionTextArray[i];
			if (StringUtils.isNotBlank(optionText)) {
				count ++;
				option.setQuestionid(question.getId());
				option.setOptionText(optionText);
				option.setSort(count);
				option.setRequireinput(optionRequireInputArray[i]);
				option.setScore(scoreArray[i]);
				option.setCreateid(curUserId);		
				option.setCreatetime(new Date());
				option.setValid(SystemCommon_Constant.VALID_STATUS_1);
				optionService.save(option);
			}
		}
		
		return success("保存成功", question);
	}
	
	/**
	 * 功能描述：保存问题及问题选项
	 * 创建时间:2013-11-11上午11:12:45
	 * 创建人: 燕珂
	 * 
	 * @param question
	 * @return
	 */
	@RequestMapping("/saveQuestion4CombinationScore")
	@ResponseBody
	public Map<String,Object> saveQuestion4CombinationScore(Question question, @RequestParam("optionText1") String[] optionText1Array, @RequestParam("optionText2") String[] optionText2Array, @RequestParam("score") int[] scoreArray, HttpSession session){
		// 先保存问题
		Long curUserId = (Long)session.getAttribute("userId");
		question.setCreateid(curUserId);		
		question.setCreatetime(new Date());
		question.setValid(SystemCommon_Constant.VALID_STATUS_1); //初始化  1-可用
		questionService.save(question);
		
		// 再保存选项
		Option option = new Option();
		int count = 0;
		for (int i = 0; i < optionText1Array.length; i++) {
			String optionText = optionText1Array[i];
			if (StringUtils.isNotBlank(optionText)) {
				count ++;
				option.setQuestionid(question.getId());
				option.setOptionText(optionText);
				option.setSort(count);
				option.setRequireinput("0");
				option.setOptiontype("1"); // 组合选择题显示在左侧的选项
				option.setCreateid(curUserId);		
				option.setCreatetime(new Date());
				option.setValid(SystemCommon_Constant.VALID_STATUS_1);
				optionService.save(option);
			}
		}
		
		// 再保存选项
		int count2 = 0;
		for (int i = 0; i < optionText2Array.length; i++) {
			String optionText = optionText2Array[i];
			if (StringUtils.isNotBlank(optionText)) {
				count2 ++;
				option.setQuestionid(question.getId());
				option.setOptionText(optionText);
				option.setSort(count2);
				option.setRequireinput("0");
				option.setOptiontype("2"); // 组合选择题显示在顶部的选项
				option.setScore(scoreArray[i]);
				option.setCreateid(curUserId);		
				option.setCreatetime(new Date());
				option.setValid(SystemCommon_Constant.VALID_STATUS_1);
				optionService.save(option);
			}
		}
		
		return success("保存成功", question);
	}
	
	/**
	 * 功能描述：保存问题及问题选项
	 * 创建时间:2013-11-11上午11:12:45
	 * 创建人: 燕珂
	 * 
	 * @param question
	 * @return
	 */
	@RequestMapping("/saveQuestion4Sort")
	@ResponseBody
	public Map<String,Object> saveQuestion4Sort(Question question, @RequestParam("optionText") String[] optionTextArray, HttpSession session){
		// 先保存问题
		Long curUserId = (Long)session.getAttribute("userId");
		question.setCreateid(curUserId);		
		question.setCreatetime(new Date());
		question.setValid(SystemCommon_Constant.VALID_STATUS_1); //初始化  1-可用
		questionService.save(question);
		
		// 再保存选项
		Option option = new Option();
		int count = 0;
		for (int i = 0; i < optionTextArray.length; i++) {
			String optionText = optionTextArray[i];
			if (StringUtils.isNotBlank(optionText)) {
				count ++;
				option.setQuestionid(question.getId());
				option.setOptionText(optionText);
				option.setSort(count);
				option.setRequireinput("0");
				option.setCreateid(curUserId);		
				option.setCreatetime(new Date());
				option.setValid(SystemCommon_Constant.VALID_STATUS_1);
				optionService.save(option);
			}
		}
		
		return success("保存成功", question);
	}
	
	/**
	 * 功能描述：保存问题及问题选项
	 * 创建时间:2013-11-11上午11:12:45
	 * 创建人: 燕珂
	 * 
	 * @param question
	 * @return
	 */
	@RequestMapping("/saveQuestion4Open")
	@ResponseBody
	public Map<String,Object> saveQuestion4Open(Question question, HttpSession session){
		// 先保存问题
		Long curUserId = (Long)session.getAttribute("userId");
		question.setCreateid(curUserId);		
		question.setCreatetime(new Date());
		question.setValid(SystemCommon_Constant.VALID_STATUS_1); //初始化  1-可用
		questionService.save(question);
		
		return success("保存成功", question);
	}
	
	/**
	 * 功能描述：进入问题编辑页面
	 * 创建时间:2013-11-8下午1:49:39
	 * 创建人: 燕珂
	 * 
	 * @param questionId 问题Id
	 * @param model
	 * @return
	 */
	@RequestMapping("/toQuestionEdit")
	public String toQuestionEdit(@RequestParam("questiontype") String questiontype, @RequestParam(value = "questionid") Long questionid, Model model) {
		Question question = questionService.get(questionid);
		model.addAttribute("questionId", questionid);
		model.addAttribute("question", question);
		
		List<Option> optionList = optionService.listOption(questionid);
		model.addAttribute("optionList", optionList);
		
		String returnJsp = "";
		if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_SELECT.equals(questiontype)) {
			returnJsp = "editQuestion4Select";
		} else if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_COMBINATIONSELECT.equals(questiontype)) {
			List<Option> optionList1 = new ArrayList<Option>(); // 左侧选项
			List<Option> optionList2 = new ArrayList<Option>(); // 上侧选项
			for (int i = 0; i < optionList.size(); i++) {
				Option option = optionList.get(i);
				if ("1".equals(option.getOptiontype()))
					optionList1.add(option);
				else if ("2".equals(option.getOptiontype()))
					optionList2.add(option);
			}
			model.addAttribute("optionList1", optionList1);
			model.addAttribute("optionList2", optionList2);
			returnJsp = "editQuestion4CombinationSelect";
		} else if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_SCORE.equals(questiontype)) {
			returnJsp = "editQuestion4Score";
		} else if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_COMBINATIONSCORE.equals(questiontype)) {
			List<Option> optionList1 = new ArrayList<Option>(); // 左侧选项
			List<Option> optionList2 = new ArrayList<Option>(); // 上侧选项
			for (int i = 0; i < optionList.size(); i++) {
				Option option = optionList.get(i);
				if ("1".equals(option.getOptiontype()))
					optionList1.add(option);
				else if ("2".equals(option.getOptiontype()))
					optionList2.add(option);
			}
			model.addAttribute("optionList1", optionList1);
			model.addAttribute("optionList2", optionList2);
			returnJsp = "editQuestion4CombinationScore";
		} else if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_SORT.equals(questiontype)) {
			returnJsp = "editQuestion4Sort";
		} else if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_OPEN.equals(questiontype)) {
			returnJsp = "editQuestion4Open";
		}
		return PREFIX + "/" + returnJsp;
	}
	
	/**
	 * 功能描述：更新问题信息
	 * 创建时间:2013-11-8下午4:14:03
	 * 创建人: 燕珂
	 * 
	 * @param question
	 * @return
	 */
	@RequestMapping("/updateQuestion4Select")
	@ResponseBody
	public Map<String, Object> updateQuestion4Select(@ModelAttribute("question") Question question, @RequestParam("optionText") String[] optionTextArray, @RequestParam(value = "optionRequireInput") String optionRequireInput, HttpSession session){
		if(question == null){
			return error("操作失败");
		}
		
		// 先保存问题
		Question questionTemp = questionService.get(question.getId());
		
		Long curUserId = (Long)session.getAttribute("userId");
		question.setCreateid(questionTemp.getCreateid()); // 创建人
		question.setCreatetime(questionTemp.getCreatetime()); // 创建时间
		question.setModifyid(curUserId); // 修改人
		question.setModifytime(new Date());  // 修改时间
		question.setValid(questionTemp.getValid()); // 是否有效
		questionService.saveOrUpdate(question);
		
		// 再保存选项（删除旧的、添加新的）
		optionService.deleteOldRoleOptionByQuestionId(question.getId());
		String[] optionRequireInputArray = optionRequireInput.split(",");
		Option option = new Option();
		int count = 0;
		for (int i = 0; i < optionTextArray.length; i++) {
			String optionText = optionTextArray[i];
			if (StringUtils.isNotBlank(optionText)) {
				count ++;
				option.setQuestionid(question.getId());
				option.setOptionText(optionText);
				option.setSort(count);
				option.setRequireinput(optionRequireInputArray[i]);
				option.setCreateid(questionTemp.getCreateid());	// 创建人和时间就取问题的	
				option.setCreatetime(questionTemp.getCreatetime());
				question.setModifyid(curUserId); // 修改人
				question.setModifytime(new Date());  // 修改时间
				option.setValid(SystemCommon_Constant.VALID_STATUS_1);
				optionService.save(option);
			}
		}
		
		// 返回信息
		return success("修改成功", question);
	}
	
	/**
	 * 功能描述：更新问题信息
	 * 创建时间:2013-11-8下午4:14:03
	 * 创建人: 燕珂
	 * 
	 * @param question
	 * @return
	 */
	@RequestMapping("/updateQuestion4CombinationSelect")
	@ResponseBody
	public Map<String, Object> updateQuestion4CombinationSelect(Question question, @RequestParam("optionText1") String[] optionText1Array, @RequestParam("optionText2") String[] optionText2Array, HttpSession session){
		if(question == null){
			return error("操作失败");
		}
		
		// 先保存问题
		Question questionTemp = questionService.get(question.getId());
		
		Long curUserId = (Long)session.getAttribute("userId");
		question.setCreateid(questionTemp.getCreateid()); // 创建人
		question.setCreatetime(questionTemp.getCreatetime()); // 创建时间
		question.setModifyid(curUserId); // 修改人
		question.setModifytime(new Date());  // 修改时间
		question.setValid(questionTemp.getValid()); // 是否有效
		questionService.saveOrUpdate(question);
		
		// 再保存选项（删除旧的、添加新的）
		optionService.deleteOldRoleOptionByQuestionId(question.getId());

		Option option = new Option();
		int count = 0;
		for (int i = 0; i < optionText1Array.length; i++) {
			String optionText = optionText1Array[i];
			if (StringUtils.isNotBlank(optionText)) {
				count ++;
				option.setQuestionid(question.getId());
				option.setOptionText(optionText);
				option.setSort(count);
				option.setRequireinput("0");
				option.setOptiontype("1"); // 组合选择题显示在左侧的选项
				option.setCreateid(questionTemp.getCreateid());	// 创建人和时间就取问题的	
				option.setCreatetime(questionTemp.getCreatetime());
				question.setModifyid(curUserId); // 修改人
				question.setModifytime(new Date());  // 修改时间
				option.setValid(SystemCommon_Constant.VALID_STATUS_1);
				optionService.save(option);
			}
		}
		
		// 再保存选项
		int count2 = 0;
		for (int i = 0; i < optionText2Array.length; i++) {
			String optionText = optionText2Array[i];
			if (StringUtils.isNotBlank(optionText)) {
				count2 ++;
				option.setQuestionid(question.getId());
				option.setOptionText(optionText);
				option.setSort(count2);
				option.setRequireinput("0");
				option.setOptiontype("2"); // 组合选择题显示在顶部的选项
				option.setCreateid(questionTemp.getCreateid());	// 创建人和时间就取问题的	
				option.setCreatetime(questionTemp.getCreatetime());
				question.setModifyid(curUserId); // 修改人
				question.setModifytime(new Date());  // 修改时间
				option.setValid(SystemCommon_Constant.VALID_STATUS_1);
				optionService.save(option);
			}
		}
		
		// 返回信息
		return success("修改成功", question);
	}
	
	/**
	 * 功能描述：更新问题信息
	 * 创建时间:2013-11-8下午4:14:03
	 * 创建人: 燕珂
	 * 
	 * @param question
	 * @return
	 */
	@RequestMapping("/updateQuestion4Score")
	@ResponseBody
	public Map<String, Object> updateQuestion4Score(@ModelAttribute("question") Question question, @RequestParam("optionText") String[] optionTextArray, @RequestParam("score") int[] scoreArray, @RequestParam(value = "optionRequireInput") String optionRequireInput, HttpSession session){
		if(question == null){
			return error("操作失败");
		}
		
		// 先保存问题
		Question questionTemp = questionService.get(question.getId());
		
		Long curUserId = (Long)session.getAttribute("userId");
		question.setCreateid(questionTemp.getCreateid()); // 创建人
		question.setCreatetime(questionTemp.getCreatetime()); // 创建时间
		question.setModifyid(curUserId); // 修改人
		question.setModifytime(new Date());  // 修改时间
		question.setValid(questionTemp.getValid()); // 是否有效
		questionService.saveOrUpdate(question);
		
		// 再保存选项（删除旧的、添加新的）
		optionService.deleteOldRoleOptionByQuestionId(question.getId());
		String[] optionRequireInputArray = optionRequireInput.split(",");
		Option option = new Option();
		int count = 0;
		for (int i = 0; i < optionTextArray.length; i++) {
			String optionText = optionTextArray[i];
			if (StringUtils.isNotBlank(optionText)) {
				count ++;
				option.setQuestionid(question.getId());
				option.setOptionText(optionText);
				option.setSort(count);
				option.setRequireinput(optionRequireInputArray[i]);
				option.setScore(scoreArray[i]);
				option.setCreateid(questionTemp.getCreateid());	// 创建人和时间就取问题的	
				option.setCreatetime(questionTemp.getCreatetime());
				question.setModifyid(curUserId); // 修改人
				question.setModifytime(new Date());  // 修改时间
				option.setValid(SystemCommon_Constant.VALID_STATUS_1);
				optionService.save(option);
			}
		}
		
		// 返回信息
		return success("修改成功", question);
	}
	
	/**
	 * 功能描述：更新问题信息
	 * 创建时间:2013-11-8下午4:14:03
	 * 创建人: 燕珂
	 * 
	 * @param question
	 * @return
	 */
	@RequestMapping("/updateQuestion4CombinationScore")
	@ResponseBody
	public Map<String, Object> updateQuestion4CombinationScore(Question question, @RequestParam("optionText1") String[] optionText1Array, @RequestParam("optionText2") String[] optionText2Array, @RequestParam("score") int[] scoreArray, HttpSession session){
		if(question == null){
			return error("操作失败");
		}
		
		// 先保存问题
		Question questionTemp = questionService.get(question.getId());
		
		Long curUserId = (Long)session.getAttribute("userId");
		question.setCreateid(questionTemp.getCreateid()); // 创建人
		question.setCreatetime(questionTemp.getCreatetime()); // 创建时间
		question.setModifyid(curUserId); // 修改人
		question.setModifytime(new Date());  // 修改时间
		question.setValid(questionTemp.getValid()); // 是否有效
		questionService.saveOrUpdate(question);
		
		// 再保存选项（删除旧的、添加新的）
		optionService.deleteOldRoleOptionByQuestionId(question.getId());

		Option option = new Option();
		int count = 0;
		for (int i = 0; i < optionText1Array.length; i++) {
			String optionText = optionText1Array[i];
			if (StringUtils.isNotBlank(optionText)) {
				count ++;
				option.setQuestionid(question.getId());
				option.setOptionText(optionText);
				option.setSort(count);
				option.setRequireinput("0");
				option.setOptiontype("1"); // 组合选择题显示在左侧的选项
				option.setCreateid(questionTemp.getCreateid());	// 创建人和时间就取问题的	
				option.setCreatetime(questionTemp.getCreatetime());
				question.setModifyid(curUserId); // 修改人
				question.setModifytime(new Date());  // 修改时间
				option.setValid(SystemCommon_Constant.VALID_STATUS_1);
				optionService.save(option);
			}
		}
		
		// 再保存选项
		int count2 = 0;
		for (int i = 0; i < optionText2Array.length; i++) {
			String optionText = optionText2Array[i];
			if (StringUtils.isNotBlank(optionText)) {
				count2 ++;
				option.setQuestionid(question.getId());
				option.setOptionText(optionText);
				option.setSort(count2);
				option.setRequireinput("0");
				option.setOptiontype("2"); // 组合选择题显示在顶部的选项
				option.setScore(scoreArray[i]);
				option.setCreateid(questionTemp.getCreateid());	// 创建人和时间就取问题的	
				option.setCreatetime(questionTemp.getCreatetime());
				question.setModifyid(curUserId); // 修改人
				question.setModifytime(new Date());  // 修改时间
				option.setValid(SystemCommon_Constant.VALID_STATUS_1);
				optionService.save(option);
			}
		}
		
		// 返回信息
		return success("修改成功", question);
	}
	
	/**
	 * 功能描述：更新问题信息
	 * 创建时间:2013-11-8下午4:14:03
	 * 创建人: 燕珂
	 * 
	 * @param question
	 * @return
	 */
	@RequestMapping("/updateQuestion4Sort")
	@ResponseBody
	public Map<String, Object> updateQuestion4Sort(@ModelAttribute("question") Question question, @RequestParam("optionText") String[] optionTextArray, HttpSession session){
		if(question == null){
			return error("操作失败");
		}
		
		// 先保存问题
		Question questionTemp = questionService.get(question.getId());
		
		Long curUserId = (Long)session.getAttribute("userId");
		question.setCreateid(questionTemp.getCreateid()); // 创建人
		question.setCreatetime(questionTemp.getCreatetime()); // 创建时间
		question.setModifyid(curUserId); // 修改人
		question.setModifytime(new Date());  // 修改时间
		question.setValid(questionTemp.getValid()); // 是否有效
		questionService.saveOrUpdate(question);
		
		// 再保存选项（删除旧的、添加新的）
		optionService.deleteOldRoleOptionByQuestionId(question.getId());
		Option option = new Option();
		int count = 0;
		for (int i = 0; i < optionTextArray.length; i++) {
			String optionText = optionTextArray[i];
			if (StringUtils.isNotBlank(optionText)) {
				count ++;
				option.setQuestionid(question.getId());
				option.setOptionText(optionText);
				option.setSort(count);
				option.setRequireinput("0");
				option.setCreateid(questionTemp.getCreateid());	// 创建人和时间就取问题的	
				option.setCreatetime(questionTemp.getCreatetime());
				question.setModifyid(curUserId); // 修改人
				question.setModifytime(new Date());  // 修改时间
				option.setValid(SystemCommon_Constant.VALID_STATUS_1);
				optionService.save(option);
			}
		}
		
		// 返回信息
		return success("修改成功", question);
	}
	
	/**
	 * 功能描述：更新问题信息
	 * 创建时间:2013-11-8下午4:14:03
	 * 创建人: 燕珂
	 * 
	 * @param question
	 * @return
	 */
	@RequestMapping("/updateQuestion4Open")
	@ResponseBody
	public Map<String, Object> updateQuestion4Open(@ModelAttribute("question") Question question, HttpSession session,HttpServletRequest request){
		if(question == null){
			return error("操作失败");
		}
		
		
		
		// 先保存问题
		Question questionTemp = questionService.get(question.getId());
		
		Long curUserId = (Long)session.getAttribute("userId");
		question.setCreateid(questionTemp.getCreateid()); // 创建人
		question.setCreatetime(questionTemp.getCreatetime()); // 创建时间
		question.setModifyid(curUserId); // 修改人
		question.setModifytime(new Date());  // 修改时间
		question.setValid(questionTemp.getValid()); // 是否有效
		
		/**---------add by YangHui  2014-10-24 begin  为了方便处理问卷问题是否进行手动选择  -------------*/
		String mycheck = request.getParameter("myCheck");
		if("no".equals(mycheck)){
			question.setIsSelect(null);
			question.setQuestionCateId(null);
		}
		/**---------add by YangHui  2014-10-24 begin  为了方便处理问卷问题是否进行手动选择  -------------*/
		questionService.saveOrUpdate(question);
		
		// 返回信息
		return success("修改成功", question);
	}
	
	/**
	 * 功能描述：删除问题
	 * 创建时间:2013-11-11下午2:03:01
	 * 创建人: 燕珂
	 * 
	 * @param questionId
	 * @return
	 */
	@RequestMapping("/deleteQuestion")
	@ResponseBody
	public Map<String, Object> deleteQuestion(@RequestParam(value = "questionId") Long questionId) {
		questionService.delete(questionId);
		optionService.deleteOldRoleOptionByQuestionId(questionId);
		return success("删除成功");
	}
	
	/**
	 * 功能描述：进入问题编辑页面
	 * 创建时间:2013-11-8下午1:49:39
	 * 创建人: 燕珂
	 * 
	 * @param questionId 问题Id
	 * @param model
	 * @return
	 */
	@RequestMapping("/toQuestionView")
	public String toQuestionView(@RequestParam("questiontype") String questiontype, @RequestParam(value = "questionid") Long questionid, Model model) {
		Question question = questionService.get(questionid);
		model.addAttribute("questionId", questionid);
		model.addAttribute("question", question);
		
		List<Option> optionList = optionService.listOption(questionid);
		model.addAttribute("optionList", optionList);
		
		String returnJsp = "";
		if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_SELECT.equals(questiontype)) {
			returnJsp = "viewQuestion4Select";
		} else if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_COMBINATIONSELECT.equals(questiontype)) {
			List<Option> optionList1 = new ArrayList<Option>(); // 左侧选项
			List<Option> optionList2 = new ArrayList<Option>(); // 上侧选项
			for (int i = 0; i < optionList.size(); i++) {
				Option option = optionList.get(i);
				if ("1".equals(option.getOptiontype()))
					optionList1.add(option);
				else if ("2".equals(option.getOptiontype()))
					optionList2.add(option);
			}
			model.addAttribute("optionList1", optionList1);
			model.addAttribute("optionList2", optionList2);
			returnJsp = "viewQuestion4CombinationSelect";
		} else if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_SCORE.equals(questiontype)) {
			returnJsp = "viewQuestion4Score";
		} else if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_COMBINATIONSCORE.equals(questiontype)) {
			List<Option> optionList1 = new ArrayList<Option>(); // 左侧选项
			List<Option> optionList2 = new ArrayList<Option>(); // 上侧选项
			for (int i = 0; i < optionList.size(); i++) {
				Option option = optionList.get(i);
				if ("1".equals(option.getOptiontype()))
					optionList1.add(option);
				else if ("2".equals(option.getOptiontype()))
					optionList2.add(option);
			}
			model.addAttribute("optionList1", optionList1);
			model.addAttribute("optionList2", optionList2);
			returnJsp = "viewQuestion4CombinationScore";
		} else if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_SORT.equals(questiontype)) {
			returnJsp = "viewQuestion4Sort";
		} else if (SystemCommon_Constant.SURVEY_QUESTION_TYPE_OPEN.equals(questiontype)) {
			returnJsp = "viewQuestion4Open";
		}
		return PREFIX + "/" + returnJsp;
	}
}
