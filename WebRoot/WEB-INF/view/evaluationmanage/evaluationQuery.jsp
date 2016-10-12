<%@ page language="java" import="java.util.*,com.hive.common.SystemCommon_Constant;" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>评论查询页面</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/metro/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/mobile.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/color.css">  
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/icon.css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.easyui.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.easyui.mobile.js" charset="UTF-8"></script>
    
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
	<!-- easyui portal插件 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/plugin/jquery-easyui-portal/portal.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/plugin/jquery-easyui-portal/jquery.portal.js" charset="UTF-8"></script>
	
	<!-- textarea 中的富编辑 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/plugin/kindeditor/themes/default/default.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/plugin/kindeditor/plugins/code/prettify.css" />
	<script charset="UTF-8" src="${pageContext.request.contextPath}/resources/js/plugin/kindeditor/kindeditor.js"></script>
	<script charset="UTF-8" src="${pageContext.request.contextPath}/resources/js/plugin/kindeditor/lang/zh_CN.js"></script>
	<script charset="UTF-8" src="${pageContext.request.contextPath}/resources/js/plugin/kindeditor/plugins/code/prettify.js"></script>
	<!-- 自己定义的样式和JS扩展 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/syCss.css" type="text/css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/icon.css" type="text/css"></link>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common/common.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common/jquery.cookie.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common/util.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common/DateUtil.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.form.js"></script>
	<script type="text/javascript">
	function vote_manage_fn(p) {
		var parame = "";
		var src = "";
		//获取选中的行
		var rows = $('#vote_questionnaire_datagrid').datagrid('getSelections');
		if (p == 'detail') {
			if (rows.length != 1) {
				$.messager.alert('提示','请选择一行（只能选择一行）您想要查看的内容');
				return;
			}
			var surveyId = rows[0].surveyId;
			var objectType = rows[0].objectType;
			var entityName = rows[0].entityName;
			var surveyPartakeUserId = rows[0].surveyPartakeUserId;
			var url = "${zxt_http_url}/survey/toVotePageDetail.action?id="+surveyId+"&objectType="+objectType+"&objectId=&objectName="+entityName+"&surveyPartakeUserId="+surveyPartakeUserId;
			parame = "查看问卷详情";
			// 固定窗口大小
			var winWidth = 1000;
			var winHeight = 800;
			var moveLeft = (screen.width - winWidth)/2; // screen.width 表示屏幕宽度，差值为弹出窗口左上角距离屏幕左边距离
			var moveTop = (screen.height - winHeight)/2 - 36; // 上面的 -10 是减去浏览器右侧滚动条的宽度，这儿的 -36是减去弹出窗口地址栏及标题的高度
			//打开一个空白窗口，并初始化大小 
		 	var imgwin=window.open(url ,parame,"left=10,top=10,width=1000,height=600,location=no,scrollbars=yes") 
			imgwin.focus() //使窗口聚焦，成为当前窗口 
		} else if (p == 'prize') {
			if (rows.length != 1) {
				$.messager.alert('提示','请选择一行（只能选择一行）您想要查看的内容');
				return;
			}
// 			var phone = rows[0].mobilePhone;//手机号 yyf
// 			var sn = rows[0].snCode;//snCode
			var winPrizeId = rows[0].winPrizeId; 
// 			var url = "${pageContext.request.contextPath}/integralOil/exchangeQueryForWeChat.action?sn="+sn+"&phone="+phone;
			var url = "${pageContext.request.contextPath}/integralOil/winPrizeInfo.action?winPrizeId="+winPrizeId;//20160628 yyf add 之前使用手机号和sn码查询中奖信息，现在修改为用中奖信息id查询
			parame = "查看中奖详情";
// 			alert("参数phone="+phone+"##sn="+sn);
			// 固定窗口大小
			var winWidth = 1000;
			var winHeight = 800;
			var moveLeft = (screen.width - winWidth)/2; // screen.width 表示屏幕宽度，差值为弹出窗口左上角距离屏幕左边距离
			var moveTop = (screen.height - winHeight)/2 - 36; // 上面的 -10 是减去浏览器右侧滚动条的宽度，这儿的 -36是减去弹出窗口地址栏及标题的高度
			//打开一个空白窗口，并初始化大小 
		 	var imgwin=window.open(url ,parame,"left=10,top=10,width=1300,height=600,location=no,scrollbars=yes") 
			imgwin.focus() //使窗口聚焦，成为当前窗口 
		}else {
			alert("无法识别的命令！");
		}
	}
	$(function() {
		//datagrid加载
		$('#vote_questionnaire_datagrid').datagrid({
// 			url : '${pageContext.request.contextPath}/evaluationManage/datagrid.action',
 			fit : false,
			border : false,
 			fitColumns : true,
			//height:330,
			scrollbarSize:0,
			striped: true ,	
			rownumbers : true,
			pagination : true,
			checkOnSelect : true,
			selectOnCheck : true,
			columns : [ [ {
				field : 'surveyId',
				title : 'surveyId',
				align : 'center',
				checkbox : true
			}, {
				field : 'entityName',
				title : '问卷对象',
				align : 'center',
				width : 150,
				formatter : function(value, row, index) {
					if (row.entityName != null) {
						return row.entityName;
					}else{
						return "空"
					}
				}
			}, {
				field : 'snCode',
				title : 'SN码',
				align : 'center',
				width : 200,
				formatter : function(value, row, index) {
					if (row.snCode != null) {
						return row.snCode;
					}else{
						return "空"
					}
				}
			}
// 			, {
// 				field : 'voteUserName',
// 				title : '评论人',
// 				width : 80,
// 				align : 'center',
// 				formatter : function(value, row, index) {
// 					if (row.voteUserName != null) {
// 						return row.voteUserName;
// 					}
// 				}
// 			}
			, {
				field : 'createTime',
				title : '时间',
				width : 180,
				align : 'center',
				formatter : function(value, row, index) {
					if (row.createTime != null) {
						var unixTimestamp = new Date(row.createTime);  
						return unixTimestamp.Format("yyyy-MM-dd hh:mm:ss");
					}
				}
			}, {
				field : 'getPrizeFlag',
				title : '领奖',
				width : 100,
				align : 'center'
			}] ],
			rowStyler : function(index, row) {
			},
			toolbar : '#tb'
		});
		var p = $('#vote_questionnaire_datagrid').datagrid('getPager');
		$(p).pagination({
		    showRefresh:false,
			pageSize : 10,//每页显示的记录条数，默认为10 
			pageList : [ 10, 50 ],//可以设置每页记录条数的列表 
			layout : [ 'list', 'first', 'prev', 'links', 'next', 'last', 'refresh' ]
		});
	});
	function vote_manage_search_fn() {
		if($("#telephone").textbox("getValue")==""){
			$.messager.alert("提示","手机号不能为空！","info");
			return;
		}
		if($("#codeInp").textbox("getValue")==""){
			$.messager.alert("提示","手机验证码不能为空！","info");
			return;
		}
		if (!$('#searchForm').form('validate')) {
			$.messager.alert("提示","您输入的手机号格式有误！！","info");
		} else {
			var telephone = $("#telephone").textbox("getValue");
			var snCode = $("#snCode").textbox("getValue");
			var beginTime = $("#beginTime").datebox("getValue");
			var endTime = $("#endTime").datebox("getValue");
			var code_ = $("#codeInp").textbox("getValue");
		//校验手机验证码
			 $.ajax({
	             type: "POST", //用POST方式传输
	             dataType: "JSON", //数据格式:JSON
	             url:"${pageContext.request.contextPath}/checkCode/checkCode.action",
	             data:{'phone':telephone,'code':code_},
	             async:true,
				 success : function(result) {
					if(result.status){
// 						alert("telephone="+telephone+"@@snCode="+snCode+"@@beginTime="+beginTime+"@@endTime="+endTime);
						$('#vote_questionnaire_datagrid').datagrid({url:'${pageContext.request.contextPath}/evaluationManage/datagrid.action?mobilePhone='+telephone+'&snCode='+snCode+'&beginTime='+beginTime+'&endTime='+endTime+'&validFlag=true'});
					}else{
						  $.messager.alert("提示",result.msg,"info");
					      return false;
					}
				
				 },
				 error : function() {
				 	$.messager.alert("提示","校验手机验证码失败，请联系管理员处理","info");
					return false;
				 }
	         });
		}
	}
	function clearForm(){
		$('#searchForm').form('clear');
		$('#vote_questionnaire_datagrid').datagrid('load' ,{});
	}
	//yyf 20160420 add 手机验证码
    var InterValObj; //timer变量，控制时间
    var count = <%=SystemCommon_Constant.REPEAT_TIME%>; //间隔函数，1秒执行
    var curCount = <%=SystemCommon_Constant.REPEAT_TIME%>;//当前剩余秒数
    var code = ""; //验证码
	var states = true;
	function sendValiCode() {
		if (!$('#searchForm').form('validate')) {
				$.messager.alert("提示","请先输入手机号！","info");
				return false;
		}
// 	alert("发送验证码");
// 	      if (states) {
// 	         states = false;
	         curCount = count;
// 			 curCount = 30;//测试
	         var dealType; //验证方式e
	         //移除获取验证码按钮
			 refreshElement("remove");
             $("#send_code").text(curCount + "秒");
	         InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
	         var phone=$("#telephone").val();
// 	         alert("发验证码的手机号："+phone);
	         //向后台发送处理数据
	         $.ajax({
	             type: "POST", //用POST方式传输
	             dataType: "JSON", //数据格式:JSON
	             url:"${pageContext.request.contextPath}/checkCode/checkMoblieCode.action",
	             data:{'phone':phone},
	             async:true,
				 success : function(result) {
					if(result.status){
						if(result.msg==1){
							$.messager.alert("提示",result.data,"info");
						}else{
							$.messager.alert("提示","请勿重复发送！","info");
							curCount=result.data;
						}
					}else{
						  $.messager.alert("提示",result.data,"info");
					      window.clearInterval(InterValObj);//停止计时器
					      refreshElement("action");
					      return false;
					}
				
				 },
				 error : function() {
				 	alert("error");
					return false;
				 }
	         });
// 	     }
	  };
	   //timer处理函数
 function SetRemainTime() {
     if (curCount == 0) {
         window.clearInterval(InterValObj);//停止计时器
         refreshElement("action");
//          $("#send_code").removeAttr("disabled");//启用按钮
//          $("#send_code").html("重新获取");
//          $("#send_code").attr("style", "background-color: #63c5fa");
         states = true;
     }else {
         states = false;
         curCount--;
         $("#send_code").text(curCount + "秒");
     }
 }
 //刷新获取验证码按钮
 function refreshElement(param){
 	if(param=="remove"){
	    //移除phoneCode
        $("#phoneCode").remove();
        var html = "<a id=\"phoneCode\" href=\"javascript:void(0)\" class=\"easyui-linkbutton c2\" style=\"width:25%;\"><span  id=\"send_code\" style=\"font-size:14px;height:30px;\"></span></a>";
        $("#pDiv").append(html);
        //重新解析渲染页面元素
        $.parser.parse($("#phoneCode").parent());
 	}else if(param=="action"){//重新给予onclick事件
 		//移除phoneCode
        $("#phoneCode").remove();
        var html = "<a id=\"phoneCode\" href=\"javascript:void(0)\" class=\"easyui-linkbutton c1\"  onclick=\"sendValiCode();\" style=\"width:25%;\"><span  id=\"send_code\" style=\"font-size:14px;height:30px;\">获取验证码</span></a>";
        $("#pDiv").append(html);
        //重新解析渲染页面元素
        $.parser.parse($("#phoneCode").parent());
 	}
 }
  </script>
</head>
<body>
    <div class="easyui-navpanel"  style="position:relative;padding:1px">
        <header style="padding:1px">
            <div class="m-toolbar">
                <div class="m-title">评价查询</div>
<!--                 <div class="m-right"> -->
<!--                 	<a id="btn1" class="easyui-linkbutton" data-options="iconCls: 'icon-search'" onclick="vote_manage_search_fn()">查询</a> -->
<!-- 					<a id="clearbtn" data-options="iconCls: 'icon-clear'" class="easyui-linkbutton" onclick="clearForm()">清空</a> -->
<!--                 </div> -->
            </div>
        </header>

        <form id="searchForm" method="post" autocomplete="on">
        <div style="text-align:center;margin-top:15px">
			<input class="easyui-textbox" name="telephone" id="telephone" validType="mobile" prompt="请输入手机号" required="true" style="width:80%;">
		</div>
		<div id="pDiv" style="text-align:center;margin-top:5px">
			<input class="easyui-textbox" name="codeInp" id="codeInp" style="width:55%;padding:1px;">
			<a id="phoneCode" href="javascript:void(0)" class="easyui-linkbutton c1" onclick="sendValiCode();" style="width:25%;"><span  id="send_code" style="font-size:14px;height:30px;">获取验证码</span></a>
		</div>
		<div style="text-align:center;margin-top:5px">
			<input class="easyui-textbox" name="snCode" id="snCode" prompt="请输入SN码" style="width:80%">
		</div>
		<div style="text-align:center;margin-top:5px">
			<input class="easyui-datebox" name="beginTime" id="beginTime" prompt="请输入评论起始时间" style="width:80%">
		</div>
		<div style="text-align:center;margin-top:5px">
			<input class="easyui-datebox" name="endTime" id="endTime" prompt="请输入评论结束时间" style="width:80%">
		</div>
        <div style="text-align:center;margin-top:10px;margin-bottom:10px">
               <a href="javascript:void(0);" name="btn_submit" id="btn_submit" class="easyui-linkbutton c4" style="width:60px;height:25px" onclick="vote_manage_search_fn()"><span style="font-size:16px">查询</span></a>
               <a href="javascript:void(0);" name="btn_submit" id="btn_submit" class="easyui-linkbutton c4" style="width:60px;height:25px" onclick="clearForm()"><span style="font-size:16px">清空</span></a>
        </div>
		</form>

		<table id="vote_questionnaire_datagrid"></table>
	    <div id="tb" class="easyui-toolbar">
	        &nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="vote_manage_fn('detail')">评价详情</a>&nbsp;
	        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="vote_manage_fn('prize')">中奖详情</a>
	    </div>
    </div>
</body>
</html>