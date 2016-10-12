<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String zxtUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();//+ "/zxt"部署项目时候屏蔽;; // 项目访问全路径，形如：http://localhost:8080/zyzlcxw
	//String zxtUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ "/zxt";
	request.setAttribute("zxtUrl", zxtUrl);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>评价信息管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../common/inc.jsp" />
  </head>
  
  <body class="easyui-layout">

        <div data-options="region:'center',border:false">
        <table id="vote_questionnaire_datagrid"></table>
    </div>
    <div id="tb" class="easyui-toolbar">
        <!-- <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="vote_manage_fn('add')">新增</a> 
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="vote_manage_fn('edit')">修改</a> 
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="vote_manage_fn('delete')">删除</a> --> 
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="vote_manage_fn('detail')">查看</a> 
    </div>
  </body>
  
  
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
			var url = "${zxt_url}/survey/toVotePageDetail.action?id="+surveyId+"&objectType="+objectType+"&objectId=&objectName="+entityName+"&surveyPartakeUserId="+surveyPartakeUserId;
			$("#test").val(url);
			parame = "查看问卷详情";
		    window.open(url,'_blank');
// 			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
// 			parent.layout_center_addTabFun({title:parame, content:content, closable:true});
		}else {
			alert("无法识别的命令！");
		}
	}
	$(function() {
		//datagrid加载
		$('#vote_questionnaire_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/evaluationManage/datagridForchart.action?countTime=${countTime}&isMember=${isMember}&entityId=${entityId}&surveyId=${surveyId} ',
			fit : true,
			height : 'auto',
			fitColumns : true,
			border : false,
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
				field : 'surveyName',
				title : '问卷主题',
				align : 'center',
				width : 300,
				formatter : function(value, row, index) {
					if (row.surveyName != null) {
						return row.surveyName;
					}
				}
			}, {
				field : 'industryName',
				title : '问卷类型',
				align : 'center',
				width : 100,
				formatter : function(value, row, index) {
					if (row.industryName != null) {
						return row.industryName;
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
			}, {
				field : 'surveyBeginTime',
				title : '问卷开始时间',
				align : 'center',
				width : 100,
				formatter : function(value, row, index) {
					if (row.surveyBeginTime != null) {
						return row.surveyBeginTime;
					}
				}
			}, {
				field : 'surveyEndTime',
				title : '问卷结束时间',
				width : 100,
				align : 'center',
				formatter : function(value, row, index) {
					if (row.surveyEndTime != null) {
						return row.surveyEndTime;
					}
				}
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
				field : 'voteUserName',
				title : '评论人姓名',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					if (row.voteUserName != null) {
						return row.voteUserName;
					}
				}
			}, {
				field : 'mobilePhone',
				title : '评论人电话',
				width : 100,
				align : 'center',
				formatter : function(value, row, index) {
					if (row.mobilePhone != null) {
						return row.mobilePhone;
					}
				}
			}, {
				field : 'idCard',
				title : '评论人身份证号',
				width : 180,
				align : 'center',
				formatter : function(value, row, index) {
					if (row.idCard != null) {
						return row.idCard;
					}
				}
			}, {
				field : 'createTime',
				title : '评论时间',
				width : 180,
				align : 'center',
				formatter : function(value, row, index) {
					if (row.createTime != null) {
						var unixTimestamp = new Date(row.createTime);  
						return unixTimestamp.Format("yyyy-MM-dd hh:mm:ss");
					}
				}
			}] ],
			rowStyler : function(index, row) {
			},
			toolbar : '#tb'
		});
		var p = $('#vote_questionnaire_datagrid').datagrid('getPager');
		$(p).pagination({
			pageSize : 10,//每页显示的记录条数，默认为10 
			pageList : [ 10, 50 ],//可以设置每页记录条数的列表 
			layout : [ 'list', 'first', 'prev', 'links', 'next', 'last', 'refresh' ]
		});

		$("#cleanBtn").click(function() {
			$("#searchForm").form("clear");
			$('#surveyName').combobox('reload');
			$('#vote_questionnaire_datagrid').datagrid('load', {});
		});
	});
	function vote_manage_search_fn() {
		if (!$('#searchForm').form('validate')) {
			$.messager.alert({
				title : '提示',
				msg : '请输入符合规范的条件内容！'
			});
		} else {
			$('#vote_questionnaire_datagrid').datagrid('load', serializeObject($('#searchForm')));
		}
	}
	//问卷详情，这需要跳转到zxt系统
	function showSurveyDetail(objectType,objectId,objectName){
		var url = "${zxtUrl}/vsurvey/toVotePageDetail.action?id=312&objectType=jiayouzhan&objectId=41&objectName=westStationOil&surveyPartakeUserId=30";
// 		var url = "${zxtUrl}/survey/surveryResultList.action?objectType="+objectType+"&objectId="+objectId+"&objectName="+objectName;
			// 固定窗口大小
			var winWidth = 700;
			var winHeight = 800;
			var moveLeft = (screen.width - winWidth)/2 -10; // screen.width 表示屏幕宽度，差值为弹出窗口左上角距离屏幕左边距离
			var moveTop = (screen.height - winHeight)/2 - 36; // 上面的 -10 是减去浏览器右侧滚动条的宽度，这儿的 -36是减去弹出窗口地址栏及标题的高度
			//打开一个空白窗口，并初始化大小 
		 	var imgwin=window.open(url ,'img','left=' + moveLeft + ',top=' + moveTop + ',width=' + winWidth + ',height=' + winHeight + ',location=no,scrollbars=yes') 
			imgwin.focus() //使窗口聚焦，成为当前窗口 
	}
  </script>
</html>