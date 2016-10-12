package com.hive.common;

import java.io.File;

import com.hive.util.PropertiesFileUtil;


/**
 * 
* Filename: SystemCommon_Constant.java  
* Description: 质讯通     管理平台     常量管理
* Copyright:Copyright (c)2013
* Company:  GuangFan 
* @author:  yanghui
* @version: 1.0  
* @Create:  2013-10-9  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2013-10-9 下午5:35:44				yanghui 	1.0
 */
public class SystemCommon_Constant {
	
	
	/**密码加密默认密钥*/
	public static final String DEFAULT_PASSWORD_KEY = "md5";

	
	/**日期格式 */
	public static final String DATE_PATTERN = "yyyy-MM-dd";
	public static final String DATE_PATTERN_2 = "yyyyMMdd";
	public static final String DATE_PATTERN_6 = "yyMMdd";
	/**
	 * 14位 日期格式
	 */
	public static final String TIME14_PATTERN = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME14_PATTERN2 = "yyyyMMddHHmmss";
	/**
	 * 6位 日期格式
	 */
	public static final String TIME6_PATTERN = "hh:mm:ss";
	
	
	
	/**审核状态:0-未审核*/
	public static final String AUDIT_STATUS_0 = "0";
	/**审核状态:1-审核通过*/
	public static final String AUDIT_STATUS_1 = "1";
	/**审核状态:2-审核不通过*/
	public static final String AUDIT_STATUS_2 = "2";
	
	/**有效状态:0-无效*/
	public static final String VALID_STATUS_0 = "0";
	/**有效状态:1-有效*/
	public static final String VALID_STATUS_1 = "1";
	
	/**是否展示:0-不展示*/
	public static final String IS_SHOW_0 = "0";
	/**是否展示:1-展示*/
	public static final String IS_SHOW_1 = "1";
	
	
	/**定义后台管理中如新闻标题   诚信资讯信息标题 等显示长度*/
	public static final String TITLE_SHOW_LENGTH = "30";
	
	
	
	/**是否标志:1-是*/
	public static final String SIGN_YES_1 = "1";
	/**是否标志:0-否*/
	public static final String SIGN_YES_0 = "0";
	
	
	/**定义系统上传附件存放的盘符   公共主目录*/
	public static final String  FILE_UPLOAD_SYSTEM_DRIVE_ROOT = "e:/";//盘符
	public static final String  FILE_UPLOAD_MAIN_DIRECTORY = "upload"+File.separator;//存放文件主目录
	public static final String  FILE_CONTENT_MAIN_DIRECTORY = "content"+File.separator;//存放内容主目录
	public static final String  FILE_ACTIVITY_MAIN_DIRECTORY = "activity"+File.separator;//存放内容主目录
	public static final String  FILE_TWODCODE_MAIN_DIRECTORY = "twocode"+File.separator;//存放二维码图片文件主目录
	
	
	/**上传附件时 磁盘目录结构*/
	public static final String ENT_INFO_01= "[01]QYXX"; //企业信息
	public static final String ENT_QYDJ_01= ENT_INFO_01+File.separator+"[01]QYDJ"; //企业登记信息
	public static final String ENT_QYZZ_02= ENT_INFO_01+File.separator+"[02]QYZZ"; //企业资质信息
	public static final String ENT_QYCP_03= ENT_INFO_01+File.separator+"[03]QYCP"; //企业产品信息
	public static final String ENT_QYXC_04= ENT_INFO_01+File.separator+"[04]QYXC"; //企业宣传资料
	public static final String ENT_QYCN_05= ENT_INFO_01+File.separator+"[05]QYCN"; //企业质量承诺书
	public static final String ENT_QYHJ_06= ENT_INFO_01+File.separator+"[06]QYHJ"; //企业获奖信息
	public static final String ENT_CPGQ_07= ENT_INFO_01+File.separator+"[07]CPGQ"; //企业产品供求信息
	public static final String ZZJGDM_02= "[02]ZZJGDM";//企业组织机构代码
	
	
	public static final String XWZX_02= "[02]XWZX"; //新闻资讯
	public static final String XWTP_01 = XWZX_02+File.separator+"[01]XWTP";//新闻图片
	public static final String XWFJ_02 = XWZX_02+File.separator+"[02]XWFJ";//新闻附近
	public static final String TZGG_03 = XWZX_02+File.separator+"[03]TZGG";//通知公告
	public static final String CXZX_04 = XWZX_02+File.separator+"[04]CXZX";//诚信资讯
	
	public static final String ZCFG_03= "[03]ZCFG";//政策法规
	public static final String XGFL_01 = ZCFG_03+File.separator+"[01]XGFL"; //相关法律
	public static final String XZFG_02 = ZCFG_03+File.separator+"[02]XZFG";
	public static final String BWGZ_03 = ZCFG_03+File.separator+"[03]BWGZ";
	public static final String SFJS_04 = ZCFG_03+File.separator+"[04]SFJS";
	public static final String DFXFGJGZ_05 = ZCFG_03+File.separator+"[05]DFXFGJGZ";
	public static final String GJGYJTY_06 = ZCFG_03+File.separator+"[06]GJGYJTY";
	public static final String ZCXWJ_07 = ZCFG_03+File.separator+"[07]ZCXWJ";
	
	public static final String HYBZ_04= "[04]HYBZ";//行业标准
	public static final String GB_01= HYBZ_04+File.separator+"[01]GB";//国标
	public static final String DB_02= HYBZ_04+File.separator+"[02]DB";//地标
	public static final String HB_03= HYBZ_04+File.separator+"[03]HB";//行标
	public static final String YB_04= HYBZ_04+File.separator+"[04]YB";//企标
	
	
	public static final String CXPG_05= "[05]CXPG";//诚信评估
	public static final String MJFT_06 = "[06]MJFT";//名家访谈
	public static final String MZZLBG_07= "[07]MZZLBG";//每周质量报告;
	
	public static final String RDZT_08= "[08]RDZT";//热点专题
	public static final String JSXX_09= "[09]JSXX";//警示信息
	public static final String YQJC_09_01= JSXX_09+File.separator+"[01]YQJC";//舆情监测
	public static final String QZXJ_09_02= JSXX_09+File.separator+"[02]QZXJ";//欺诈陷阱
	public static final String DXAL_09_01= JSXX_09+File.separator+"[03]DXAL";//典型案例
	public static final String BGT_09_01= JSXX_09+File.separator+"[04]BGT";//曝光台
	public static final String WQGS_09_01= JSXX_09+File.separator+"[05]WQGS";//维权故事
	public static final String WQCS_09_01= JSXX_09+File.separator+"[06]WQCS";//维权常识
	
	
	public static final String CXWH_10= "[10]CXWH";//诚信文化
	
	public static final String GYWM_11 = "[11]GYWM";//关于我们
	
	public static final String COMMON_CONTENT = "COMMONCONTENT";// 通用内容管理模块的内容以文件形式存放的文件夹
	//20160627 yyf add
	public static final String ACTIVITY_CONTENT = "ACTIVITYCONTENT";// 活动内容以文件形式存放的文件夹
	
	public static final String DZZZ_12_1= "[12]DZZZTP";//电子杂志图片
	public static final String DZZZ_12_2= "[12]DZZZFJ";//电子杂志附件
	
	/** 质讯通    附件存放目录结构   */
	public static final String XXGG_01 = "[01]XXGG";//信息公告
	public static final String JPMS_05 = "[05]JPMS";//奖品描述
	
	public static final String ANNEXT_TYPE_XWTP = "PICTURENEWS_IMAGE";//图片新闻图片
	public static final String ANNEXT_TYPE_XWFJ = "PICTURENEWS_FILE";//图片新闻附件类型
	
	public static final String ANNEXT_TYPE_ZXFJ = "INFONEWS_FILE";//新闻资讯附件类型
	public static final String ANNEXT_TYPE_TZFJ = "INFONOTICE_FILE";//新闻资讯附件类型
	
	/**附件类型 : 图片*/
	public static final String ANNEXT_TYPE_IMAGE = "image";
	public static final String ANNEXT_TYPE_ZIP = "ZIP";
	public static final String ANNEXT_TYPE_EXE = "EXE";
	/**附件类型 : 营业执照*/
	public static final String ANNEXT_TYPE_BUSINESS_LICENCE = "BUSINESS_LICENCE";
	/**附件类型 : 组织机构代码证*/
	public static final String ANNEXT_TYPE_ORG_CODE = "ORG_CODE";
	/**附件类型 : 企业Logo*/
	public static final String ANNEXT_TYPE_ENT_LOGO = "ENT_LOGO";
	
	/** 产品供求标志*/
	public static final String PRODUCT_SUPPLY_1 = "1";//供应
	public static final String PRODUCT_DEMAND_2 = "2";//求购
	
	
	/** 允许非登陆投票(0:不允许) */
	public final static String VOTE_LOGIN = "0";
	/** 允许非登陆投票(1:允许) */
	public final static String VOTE_NOLOGIN = "1";
	
	/** 附件表中表名常量: 企业信息正式表 */
	public final static String OBJECT_TABLE_ENTERPRISE = "E_ENTERPRISEINFO";
	/** 附件表中表名常量: 企业信息临时表 */
	public final static String OBJECT_TABLE_ENTERPRISE_TEMP = "E_ENTERPRISEINFOMODIFYTEMP";
	
	/** 是否叶子节点 */
	public static final String LEAF_YES = "1"; // 叶子节点
	public static final String LEAF_NO = "0"; // 非叶子节点
	
	/** 信息是否已阅读 */
	public static final String READ_YES = "1";
	public static final String READ_NO = "0";
	
	
	/** 信息是否订阅 */
	public static final String INFO_SIGN_YES = "1";
	public static final String INFO_SIGN_NO = "0";
	
	
	
	/** 订单状态: 1-订单已申请 */
	public static final String INTEND_STATUS_1 = "1"; 
	/** 订单状态: 2-审核通过 */
	public static final String INTEND_STATUS_2 = "2"; 
	/** 订单状态: 3-审核未通过 */
	public static final String INTEND_STATUS_3 = "3"; 
	/** 订单状态: 4-已发货 */
	public static final String INTEND_STATUS_4 = "4"; 
	/** 订单状态: 5-确认收货 */
	public static final String INTEND_STATUS_5 = "5"; 
	/** 订单状态: 6-订单已取消 */
	public static final String INTEND_STATUS_6 = "6"; 
	
	
	/**积分类别定义说明   根据数据库进行常量定义 匹配  */
	public static final String INTEGRAL_CATEGORY_COMMENT = "2";  //评论
	public static final String INTEGRAL_CATEGORY_SHARE = "3";   //分享
//	public static final String INTEGRAL_CATEGORY_VOTE = "4";   //投票
	
	/**系统参数配置    定义参数代码   唯一标识   用来查询参数的ID值*/
	public static final String SYSTEM_CONFIG_SHARE_INTEGRAL = "share_integral";   //分享积分
	public static final String SYSTEM_CONFIG_COMMENT_INTEGRAL = "comment_integral";   //评论积分
	
	/**积分明细处理时用到的常量说明   */
	public static final String INTEGRAL_DETAIL_INTEGRAL_TYPE_1 = "1";// 获取
	public static final String INTEGRAL_DETAIL_INTEGRAL_TYPE_2 = "2";// 消费
	
	
	/**客户端接口 返回参数     说明*/
	public static final String RESULT_KEY_RET = "ret";
	public static final String RESULT_KEY_MSG = "msg";
	public static final String RESULT_KEY_DATA = "data";
	public static final String RESULT_KEY_TOTAL = "total";
	public static final String RESULT_KEY_PAGENO = "pageNo";
	public static final String RESULT_KEY_VERSION = "version";
	public static final String CURRENT_INTEGRAL = "currentIntegral";
	public static final String RESULT_VALUE_RET_SUCCESS = "0";
	public static final String RESULT_VALUE_RET_FAIL = "1";
	public static final String RESULT_VALUE_RET_OTHER = "2"; //对企业秀展示时需要用到的状态
	
	
	/** 调查问卷题型 */
	public static final String SURVEY_QUESTION_TYPE_SELECT = "1"; // 选择题
	public static final String SURVEY_QUESTION_TYPE_SELECT_RADIO = "11"; // 选择题-单选
	public static final String SURVEY_QUESTION_TYPE_SELECT_CHECK = "12"; // 选择题-多选
	public static final String SURVEY_QUESTION_TYPE_COMBINATIONSELECT = "2"; // 组合选择题
	public static final String SURVEY_QUESTION_TYPE_COMBINATIONSELECT_RADIO = "21"; // 组合选择题-单选
	public static final String SURVEY_QUESTION_TYPE_COMBINATIONSELECT_CHECK = "22"; // 组合选择题-多选
	public static final String SURVEY_QUESTION_TYPE_SCORE = "3"; // 打分题
	public static final String SURVEY_QUESTION_TYPE_COMBINATIONSCORE = "4"; // 组合打分题
	public static final String SURVEY_QUESTION_TYPE_SORT = "5"; // 排序题
	public static final String SURVEY_QUESTION_TYPE_OPEN = "6"; // 开放题
	public static final String SURVEY_QUESTION_TYPE_OPEN_INPUT = "61"; // 开放题-单行
	public static final String SURVEY_QUESTION_TYPE_OPEN_TEXTAREA = "62"; // 开放题-多行
	
	/** 问卷状态：1-投票进行中 */
	public static final String SURVEY_STATUS_ON = "1";
	/** 问卷状态：4-投票已关闭 */
	public static final String SURVEY_STATUS_CLOSE = "4"; 
	/**
	 * 短信验证码重发时间（在这个时间内不允许重复发送，防止恶意发短信）
	 */
	public static final Integer REPEAT_TIME = 90; 
	/**
	 * 短信验证码失效时间（在这个时间内不允许重复发送，防止恶意发短信）
	 */
	public static final Integer INVALID_TIME = 600; 	
	/** 问卷设置标识代码: 同一 ip 重复投票*/
	public static final String SURVEY_SET_CODE_IPREPEAT = "ipRepeat";  
	/** 问卷设置标识代码: 同一用户重复投票*/
	public static final String SURVEY_SET_CODE_USERREPEAT = "userRepeat"; 
	/** 问卷设置标识代码: 投票后查看结果*/
	public static final String SURVEY_SET_CODE_VIEWAFTERVOTE = "viewAfterVote"; 
	/** 问卷设置标识代码: 是否允许匿名投票(1-允许，0-不允许)*/
	public static final String SURVEY_SET_CODE_ANONYMOUS = "anonymous"; 
	
	/** 问卷设置标识代码: 是否需要关联具体商铺(1-需要，0-不需要)*/
	public static final String SURVEY_SET_CODE_ISRELSHOP = "isRelShop"; 
	
	
	/**会员级别*/
	public static final String MEMBER_LEVEL_1 = "1";//默认为最低级
	
	/**是否为头条：1-是*/
	public static final String CONTENT_ISTOP_YES = "1";
	/**是否为头条：0-否*/
	public static final String CONTENT_ISTOP_NO = "0";
	
	/**评论是否被屏蔽：0-未被屏蔽 */
	public static final String REVIEW_SHIELD_STATUS_0 = "0"; //否 - 未被屏蔽
	/**评论是否被屏蔽：1-被屏蔽 */
	public static final String REVIEW_SHIELD_STATUS_1 = "1"; //是 - 被屏蔽
	
	/**发布范围（1-全部用户可见，2-关注用户可见）*/
	public static final String SEND_RANG_1 = "1"; 
	public static final String SEND_RANG_2 = "2";
	
	
	/**为消息推送定义的常量    根据信息类别表的ID进行模块的区分  */
	
	public static final String PUSH_MESSAGE_XFZX = "42"; //消费资讯
	public static final String PUSH_MESSAGE_RED = "85"; //红榜单
	public static final String PUSH_MESSAGE_BLACK = "94"; //黑榜单
	public static final String PUSH_MESSAGE_ZLQS = "103"; //质量强市
	public static final String PUSH_MESSAGE_ZCFG = "46";//政策法规
	
	/**
	 * 投诉举报处理的状态
	 * 00-提交待审核
	 * 01-已提交至相关部门
	 * 02-投诉或举报已失效
	 * 03-处理结束
	 * 04-一级审核通过
	 * 05-
	 */
	public static final String COMPLAIN_DEAL_STATE_00 = "00" ;
	public static final String COMPLAIN_DEAL_STATE_01 = "01" ;
	public static final String COMPLAIN_DEAL_STATE_02 = "02" ;
	public static final String COMPLAIN_DEAL_STATE_03 = "03" ;
	public static final String COMPLAIN_DEAL_STATE_04 = "04" ;
	
	
	/**
	 * 投诉举报处理的状态  修改后(后台管理)
	 * 00-投诉提交未审核
	 * 01-审核通过待派发
	 * 02-已派发待确认
	 * 03-已确认处理中
	 * 04-驳回重新处理
	 * 05-办结
	 * 06-无效投诉
	 * 07-处理完成待结案
	 */
	public static final String BACK_COMPLAIN_DEAL_STATE_00 = "00" ;
	public static final String BACK_COMPLAIN_DEAL_STATE_01 = "01" ;
	public static final String BACK_COMPLAIN_DEAL_STATE_02 = "02" ;
	public static final String BACK_COMPLAIN_DEAL_STATE_03 = "03" ;
	public static final String BACK_COMPLAIN_DEAL_STATE_04 = "04" ;
	public static final String BACK_COMPLAIN_DEAL_STATE_05 = "05" ;
	public static final String BACK_COMPLAIN_DEAL_STATE_06 = "06" ;
	public static final String BACK_COMPLAIN_DEAL_STATE_07 = "07" ;
	
	
	/**  Add by  YangHui 2014-01-08  
	 * 由于当时数据库修改后，导致现有数据库跟原来的需求有冲突，这里暂时设定
	 * 每个岗位的ID值作为节点参数
	 * 投诉处理步骤节点
	 * 1104-审核岗
	 * 1105-派发岗
	 * 1106-处理岗
	 * 1107-结案岗 
	 */
	public static final String COMPLAIN_DEAL_NODE_APPROVE = "1104" ;  
	public static final String COMPLAIN_DEAL_NODE_PAIFA = "1105" ;
	public static final String COMPLAIN_DEAL_NODE_BANLI = "1106" ;
	public static final String COMPLAIN_DEAL_NODE_JIEAN = "1107" ;

	/**
	 * 投诉
	 */
	public static final String COMPLAIN_INFO = "COMPLAIN_INFO";
	
	
	/** 客户端版本类型:企业版 */
	public static final String CLIENT_VERSION_TYPE_1 = "1";
	/** 客户端版本类型:大众版 */
	public static final String CLIENT_VERSION_TYPE_2 = "2";
	
	/** 客户端菜单名称常量标识: 质量评价*/
	public static final String MENU_FLAG_ZLPJ = "F_ZLPJ"; 
	/** 客户端菜单名称常量标识: 评价公示*/
	public static final String MENU_FLAG_PJGS = "F_PJGS"; 
	/** 客户端菜单名称常量标识: 我要爆料*/
	public static final String MENU_FLAG_WYBL = "F_WYBL"; 
	/** 客户端菜单名称常量标识: 我要投诉*/
	public static final String MENU_FLAG_WYTS = "F_WYTS"; 
	/** 客户端菜单名称常量标识: 舆情监测*/
	public static final String MENU_FLAG_YQJC = "F_YQJC"; 
	/** 客户端菜单名称常量标识: 通知公告*/
	public static final String MENU_FLAG_TZGG = "F_TZGG"; 
	/** 客户端菜单名称常量标识: 消费资讯*/
	public static final String MENU_FLAG_XFZX = "F_XFZX"; 
	/** 客户端菜单名称常量标识: 优惠打折*/
	public static final String MENU_FLAG_YHDZ = "F_YHDZ"; 
	/** 客户端菜单名称常量标识: 企业动态*/
	public static final String MENU_FLAG_QYDT = "F_QYDT"; 
	/** 客户端菜单名称常量标识: 红榜单*/
	public static final String MENU_FLAG_RED = "F_RED"; 
	/** 客户端菜单名称常量标识: 黑榜单*/
	public static final String MENU_FLAG_BLACK = "F_BLACK"; 
	/** 客户端菜单名称常量标识: 缺陷管理*/
	public static final String MENU_FLAG_QXGL = "F_QXGL"; 
	/** 客户端菜单名称常量标识: 政策法规*/
	public static final String MENU_FLAG_ZCFG = "F_ZCFG"; 
	
	
	/** 版本类别:客户端菜单-企业版 */
	public static final String V_CLIENT_MENU_1 = "V_CLIENT_MENU_1"; 
	/** 版本类别:客户端菜单-大众版 */
	public static final String V_CLIENT_MENU_2= "V_CLIENT_MENU_2"; 
	/** 版本类别:信息类别总称（包括：消费资讯类别，红黑榜单类别，政策法规类别等） */
	public static final String V_INFOCATE ="V_INFOCATE";
	/** 版本类别:问卷类别-质量评价 */
	public static final String V_VOTE = "V_VOTE";  
	/** 版本类别:问卷(质量评价)-行业类别 */
	public static final String V_VOTE_INDUSTRY = "V_VOTE_INDUSTRY";  
	/**二维码访问路径*/
	/**印刷厂批量印刷的二维码查询路径*/
	//质监局监控中心，66服务器外网路径
	//public static final String QRCODE_PATH = "http://123.15.36.11:8800/zbt/view/query/scanQRCodePage.jsp?SNId=";
	//测试开发，本机路径，
	//public static String QRCODE_PATH = "http://192.168.9.251:8080/zxt/survey/zxtTransfer.action?entityId=";
	public static String QRCODE_PATH = "http://www.zhixt.cn/zxt/survey/zxtTransfer.action?entityId=";
	public static String QRCODE_PATH_surveyIndustry = "http://www.zhixt.cn/zxt/survey/zxtTransfer.action?surveyIndustryId=";
	public static final Long PRIZE_CATEID=42L;//加油站积分奖品兑换类别Id
	
	//yf 20160304 add
	static{
		PropertiesFileUtil propertiesFileUtil = new PropertiesFileUtil();
		String QRCODE_PATH_ = propertiesFileUtil.findValue("QRCODE_PATH");
		if(null != QRCODE_PATH_ && !"".equals(QRCODE_PATH_)){
			QRCODE_PATH = QRCODE_PATH_;
		}
		
		String QRCODE_PATH_surveyIndustry_ = propertiesFileUtil.findValue("QRCODE_PATH_surveyIndustry");
		if(null != QRCODE_PATH_surveyIndustry_ && !"".equals(QRCODE_PATH_surveyIndustry_)){
			QRCODE_PATH_surveyIndustry = QRCODE_PATH_surveyIndustry_;
		}
	}
	
	/**
	 * 20160606 yyf add
	 * 活动类型
	 */
	/** 活动类型:抽奖活动 */
	public static final String ACTIVITY_TYPE_1 = "1" ;
	/** 活动类型:奖励活动 */
	public static final String ACTIVITY_TYPE_2 = "2" ;
	/** 活动状态:未启动 */
	public static final String ACTIVITY_STATUS_0 = "0" ;
	/** 活动状态:已启动 */
	public static final String ACTIVITY_STATUS_1 = "1" ;
	/** 活动状态:已结束 */
	public static final String ACTIVITY_STATUS_2 = "2" ;
	/** 活动状态:已暂停 */
	public static final String ACTIVITY_STATUS_3 = "3" ;
	//20160810 yyf add=============================================
	/** 二维码状态: 0 未印刷（20160818 未生效）*/
	public static final String QRCode_status_0 = "0";
	/** 二维码状态: 1 印刷中 */
	public static final String QRCode_status_1 = "1";
	/** 二维码状态: 2 已印刷（20160818 占用） */
	public static final String QRCode_status_2 = "2";
	/** 二维码状态: 3 待发放 */
	public static final String QRCode_status_3 = "3";
	/** 二维码状态: 4已发放*/
	public static final String QRCode_status_4 = "4";
	/** 二维码状态: 5 已绑定*/
	public static final String QRCode_status_5 = "5";
	/** 二维码状态: 6 作废 */
	public static final String QRCode_status_6 = "6";
	//20160812 yyf add=============================================
	/** 递增序列类型: EWMPC，即二维码批次的自增序列 */
	public static final String SEQUENCE_EWMPC = "erweimapici";
	/** 递增序列类型: EWMFP，即二维码分配的自增序列  */
	public static final String SEQUENCE_EWMFP = "erweimafenpei";
}
