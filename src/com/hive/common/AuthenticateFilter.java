package com.hive.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @description 验证是否登录过滤器
 * @author 燕珂
 * @createtime 2014-1-10 16:47:17
 */
public class AuthenticateFilter implements Filter {
	private static final long serialVersionUID = -7429420777736605452L;

	// 保存 web.xml 配置的强制转向地址
	private static String loginUrl = "";

	/**
	 * 过滤器配置对象
	 */
	protected FilterConfig filterConfig = null;

	public void destroy() {
		this.filterConfig = null;
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession s = request.getSession();
		
		Object loginUser =  s.getAttribute("user");  // 后台用户

		String userRequestUri = request.getRequestURI();  // 获取用户请求地址（这里面不包含参数）
		
		String redirectUri = "";  // 重定向回去的地址
		
		// 访问登录页面、生成验证码图片、发送登录请求、请求资源文件这几种情况不过滤
		if (loginUser == null && userRequestUri.indexOf("/login.jsp") == -1
				&& userRequestUri.indexOf("CaptchaImage/create.action") == -1
				&& userRequestUri.indexOf("security/login.action") == -1
				&& userRequestUri.indexOf("resources") == -1
				&& userRequestUri.indexOf(".jpg") == -1
				&& userRequestUri.indexOf("/interface/") == -1
				&& userRequestUri.indexOf("/defectOfToyAndOthers/") == -1 
				//&& userRequestUri.indexOf("/province/") == -1
				&& userRequestUri.indexOf("/defectCar/") == -1
				&& userRequestUri.indexOf("/province/list") == -1
				&& userRequestUri.indexOf("/city/list") == -1
				&& userRequestUri.indexOf("/district/list") == -1
				&& userRequestUri.indexOf("/evaluationManage") == -1
				&& userRequestUri.indexOf("/surveyIndustryManage/treegrid.action") == -1
				&& userRequestUri.indexOf("/surveyManage/findAllSurvey.action") == -1
				&& userRequestUri.indexOf("/cashPrizeInfo/manage.action") == -1
				&& userRequestUri.indexOf("/cashPrizeInfo/dataGrid.action") == -1
				&& userRequestUri.indexOf("/cashPrizeInfo/add.action") == -1
				&& userRequestUri.indexOf("/prizeInfo/allPrizeInfo.action") == -1
				&& userRequestUri.indexOf("/prizeInfo/findAllPrizeInfo.action") == -1
				&& userRequestUri.indexOf("/cashPrizeInfo/insert.action") == -1
				&& userRequestUri.indexOf("/evaluationManage/toQueryEvaluation.action") == -1
				&& userRequestUri.indexOf("/evaluationManage/toEvaluationByChart.action") == -1
				&& userRequestUri.indexOf("/integralOil/exchangeQueryForWeChat.action") == -1
				&& userRequestUri.indexOf("/integralOil/exchangeQuery.action") == -1
				&& userRequestUri.indexOf("/integralOil/queryExchange.action") == -1
				&& userRequestUri.indexOf("/integralOil/cashPrizeRecord.action") == -1
				&& userRequestUri.indexOf("/integralOil/cashPrizeOneRecord.action") == -1
				&& userRequestUri.indexOf("/integralOil/gasStationExpiryQueryForApp.action") == -1
				&& userRequestUri.indexOf("/integralOil/temporary.action") == -1
				&& userRequestUri.indexOf("/checkCode/checkMoblieCode.action") == -1
				&& userRequestUri.indexOf("/checkCode/checkCode.action") == -1
				&& userRequestUri.indexOf("/activityManage/toWheelSurf.action") == -1//yyf 20160620 add 跳转抽奖大转盘
				&& userRequestUri.indexOf("/activityManage/obtainOrderNum.action") == -1//yyf 20160620 add 跳转抽奖大转盘
				&& userRequestUri.indexOf("/industryEntityManage/getAllIndustryEntityList.action") == -1//zpf 20160624 add 获取实体列表
				&& userRequestUri.indexOf("/surveyManage/getSurveyByindustryEntity.action") == -1//zpf 20160624  add 获取问卷             供加油站访问
				&& userRequestUri.indexOf("/industryEntityManage/getAllIndustryEntityListForOther.action") == -1//zpf 20160624  add 根据外系统ID获得实体
				&& userRequestUri.indexOf("/qrcodeDispatcher.action") == -1//yyf 20160815 add
				&& userRequestUri.indexOf("/toShowSimpleQrcodeInfo.action") == -1//yyf 20160816 add
				&& userRequestUri.indexOf("/toBindQrcode.action") == -1//yyf 20160823 add 二维码绑定临时解决方案
				&& userRequestUri.indexOf("/checkPassword.action") == -1//yyf 20160823 add 二维码绑定临时解决方案密码校验
				&& userRequestUri.indexOf("/qrcodeIssueTemporaryAdd.action") == -1//yyf 20160824 add 二维码绑定临时解决方案绑定方法
//				&& userRequestUri.indexOf("evaluationQuery.jsp") == -1//测试使用，yyf
//				&& userRequestUri.indexOf("evaluationQuery1.jsp") == -1
//				&& userRequestUri.indexOf("exchangeQuery.jsp") == -1
				&& userRequestUri.indexOf("temporary.jsp") == -1
				) { 
			redirectUri = request.getContextPath() + loginUrl;
			response.sendRedirect(redirectUri);
		} else {  // 用户已登录则直接放行
			// 此处可以继续验证用户是否有访问该 url 的权限
			chain.doFilter(request, response);
		}
	} 

	/**
	 * 初始化过滤器
	 * 
	 * @param filterConfig 过滤配置对象
	 */
	public void init(FilterConfig filterConfig) throws ServletException {

		this.filterConfig = filterConfig;
		// 读取 web.xml 里配置的强制转向地址，给上面的变量赋值
		String lu2 = filterConfig.getInitParameter("loginUrl");
		
		if (lu2 != null)
			loginUrl = lu2;
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
}
