package com.hive.surveymanage.model;

	import com.hive.surveymanage.entity.IndustryEntity;


	/**
	 * 实体信息列表实体
	 */
	public class IndustryEntityBean extends IndustryEntity{

		// Fields

		private String queryPath;//访问连接
		private String surveyTitle;//绑定问卷的标题
		private String entityTypeName;//实体类别名称
		public String getQueryPath() {
			return queryPath;
		}

		public void setQueryPath(String queryPath) {
			this.queryPath = queryPath;
		}

		public String getSurveyTitle() {
			return surveyTitle;
		}

		public void setSurveyTitle(String surveyTitle) {
			this.surveyTitle = surveyTitle;
		}

		public String getEntityTypeName() {
			return entityTypeName;
		}

		public void setEntityTypeName(String entityTypeName) {
			this.entityTypeName = entityTypeName;
		}
		

	}