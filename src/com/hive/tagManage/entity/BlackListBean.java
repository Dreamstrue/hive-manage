package com.hive.tagManage.entity;


public class BlackListBean extends SNBase {

	private String strentityName;//企业名称
	private int intRank;//SN类别
	private String strBatch;//批次
	
	private String surveyId;//对应的问卷ID
	
	private String surveyTitle;//对应的问卷标题
	
	public BlackListBean(String strEnterpriseName, String strProductName,String strSpecifiName,String strBatch,int intRank) {
		super();
		this.strentityName = strEnterpriseName;
		this.strBatch=strBatch;
		this.intRank=intRank;
	}
	
	public BlackListBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getStrentityName() {
		return strentityName;
	}

	public void setStrentityName(String strentityName) {
		this.strentityName = strentityName;
	}

	public int getIntRank() {
		return intRank;
	}

	public void setIntRank(int intRank) {
		this.intRank = intRank;
	}

	public String getStrBatch() {
		return strBatch;
	}

	public void setStrBatch(String strBatch) {
		this.strBatch = strBatch;
	}

	public String getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(String surveyId) {
		this.surveyId = surveyId;
	}

	public String getSurveyTitle() {
		return surveyTitle;
	}

	public void setSurveyTitle(String surveyTitle) {
		this.surveyTitle = surveyTitle;
	}
	
}