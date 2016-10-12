<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>标签管理</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../common/inc.jsp"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.form.js"></script>
  </head>
 <body>
    <div class="easyui-layout" fit="true">
    <!-- 查询条件 -->
		<div region="north" split="true" title="查询条件" border="false" class="p-search" style="overflow: hidden; height:65px; padding-left: 10px; padding-top: 5px;">
			<form action="javascript:void(0);" id="searchForm" style="width: 100%">
				<label style="font-size: 12px;">批次编号:</label>
				<input name="batch" style="width: 180px;" />
				<label style="font-size: 12px;">行业实体:</label>
				<input id="industryEntityId_manage" name="industryEntityId" type="hidden"/>
				<input class="easyui-textbox" id="industryEntityText" name="industryEntityText" data-options="prompt:'点击选择实体名称'" style="width: 150px;" />
<!-- 				<input id="industryEntityText" onclick="queryAllIndustryEntity('industryEntityId','industryEntityText')" style="width: 180px;" /> -->
			    <%-- <input name="industryEntityId"  class="easyui-combobox" data-options="url:'${appPath}/industryEntityManage/allindustryEntityInfo.action',valueField:'id',textField:'entityName',lines:true, " style="width: 180px;" /> --%>
				<label style="font-size: 12px;">印刷情况:</label>
				<select class="easyui-combobox" id="status" name="status" data-options="prompt:'印刷状态',editable:false,panelHeight:'auto'" style="width:150px;">
								<option ></option>
								<option value=0>未印刷</option>
								<option value=1>印刷中</option>
								<option value=2>已印刷</option>
				</select>
				
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="sn_manage_search_fn();">查询</a>
				<a id="cleanBtn" class="easyui-linkbutton" data-options="plain: true,iconCls:'icon-clear'" >重置</a>
			</form>
		</div>
    
    <div region="center" border="false">
		<table id="sn_manage_datagrid"></table>
	</div>
</div>

<div id="openRoleDiv" class="easyui-window" closed="true" modal="true" minimizable="false" title="查看批次详细 " style="width:600px;height:380px;">
    <iframe scrolling="auto" id='openXXXIframe' frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
</div>

<script type="text/javascript">


	$(function() { 
		$('#industryEntityText').textbox('textbox').focus(function() {
			queryAllIndustryEntity('industryEntityId_manage','industryEntityText');
    	}); 
		$('#sn_manage_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/tagSNBatchController/datagrid.action',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			striped:true,
			rownumbers : true,
			idField : 'id',
			pageSize : 20,
			pageList : [10, 20, 30, 40, 50, 60],
			sortName : 'id',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect:false,
			nowrap : false,
			toolbar : [
						{
							text : '批量生成SN序列号',
							iconCls : 'icon-add',
							handler : function() {
								sn_manage_add_fn('pre');
							}
						},
						{
							text : '后处理的SN批量生成',
							iconCls : 'icon-add',
							handler : function() {
								sn_manage_add_fn();
							}
						},
						{
							text : '批量导出',
							iconCls : 'icon-save',
							handler : function() {
								downLoad();
							}
						}
						],
			columns : [[
  				{ field : 'id',title : '批次ID',hidden : true},
				{ title : '批次编号',field : 'batch', width : 100,sortable:true, align :'center' },
				{ title: '原始数量', field: 'amount', align :'center',width:100 },
				{ title: '有效数量', field: 'validAmount', align :'center',width: 100,sortable:true,
					styler: function (value, row, index) { 
						if(row.validAmount > (row.amount)*0.7) {
			        		return 'background-color:#00DD00;color:white;font-weight:bold;';
						}
						if(row.validAmount >= (row.amount)*0.4) {
								return 'background-color:#FFFF33;color:red;font-weight:bold;';
						}
						if(row.validAmount < (row.amount)*0.4) {
			        		return 'background-color:#FF3333;color:white;font-weight:bold;';
						}
			    	}
				},
				{ title: '行业实体', field: 'entityName', align :'center',width: 150},
				{ title: '对应问卷', field: 'surveyTitle', align :'center',width: 200},
                { title: '印刷情况', field: 'status', align :'center',width: 100,
                	formatter: function(value) {
						if(value==0) {
							return "未印刷";
						} else if(value==1) {
							return "印刷中";
						} else if(value==2) {
							return "已印刷";
						}
					}
                },
                { title: '创建人', field: 'createrName', align :'center',width: 100 },
				{ field : 'createTime', title : '创建时间', align :'center', width: 120,
                	formatter:function(value,row,index){ 
	                	if(value!=null) {
	                		var timestamp = new Date(value);  
		                 	return timestamp.Format("yyyy-MM-dd hh:mm:ss"); 
	                 	}   
	                }
				}
                <c:if test="${(sessionScope.isAdmin == '1') || requestScope.canPutout || requestScope.canPrinted || requestScope.canAudit}">
				, {
					field : 'action',
					title : '动作',
					width : 100,
					align :'center',
					formatter : function(value, row, index) {
						if (row.id == '0') {
							return '系统用户';
						} else {
							var html = "";
							html += '&nbsp;<img onclick="user_manage_look_fn(\'' + row.id + '\');" src="${appPath}/resources/images/extjs_icons/text_list_numbers.png" title="查看标签列表"/>';
							if(row.hasStorageRecord != '1'){// 该批次 没有入库单  记录   可以变更批次印刷状态    yf 20150918 add
								html += '&nbsp<img onclick="user_manage_updateStatus_fn(\'' + row.id + '\');" src="${appPath}/resources/images/extjs_icons/table/table_relationship.png" title="更改印刷状态"/>';	
							}
							html += '&nbsp;<img onclick="downLoadExcel(\'' + row.batch + '\',\'' + row.id + '\',\'' + row.entityName +  '\');" src="${appPath}/resources/images/extjs_icons/table/table_save.png" title="导出EXCEL"/>';
							if(row.surveyTitle==''){
								html +='&nbsp;<img onclick=\"set_survey_fn(\''+row.id+'\');\" title="绑定/修改问卷" src="${appPath}/resources/images/extjs_icons/cog_edit.png"/>';
							}
							return html;
						}
					}
				} 
				</c:if>
			] ],
			rowStyler:function(index,row){    
				if (row.isAllot==2){    
					return 'background-color:pink;color:blue;font-weight:bold;';    
				}    
			}  
		});
		 $("#cleanBtn").click(function () {
			 $("#searchForm").form("clear");
			  $('#sn_manage_datagrid').datagrid('load', {});
         });
	});
	
	//查看批次信息
	function user_manage_look_fn(id) {
		var href = '${pageContext.request.contextPath}/tagSNBatchController/lookListOfSNBatchId.action?snBatchId='+id;
		$('#openXXXIframe')[0].src=href;
		$('#openRoleDiv').dialog('open');
	}

	//采用jquery easyui loading css效果 
	function ajaxLoading(){ 
		$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body"); 
	    $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候……").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
	} 
	function ajaxLoadEnd(){ 
	     $(".datagrid-mask").remove(); 
	     $(".datagrid-mask-msg").remove();             
	} 
	
	function showProcess(isShow, title, msg) {
		if (!isShow) {
			$.messager.progress('close');
			return;
		}
		var win = $.messager.progress({
			title: title,
			msg: msg
		});
	}
	
	function sn_manage_search_fn() {
		$('#sn_manage_datagrid').datagrid('load', serializeObject($('#searchForm')));
	}
	//后处理绑定问卷和实体
	function set_survey_fn(id) {
		$('<div/>').dialog({
			id : 'bind_dialog',
			href : '${pageContext.request.contextPath}/tagSNBatchController/toBindSurveyAndEntity.action?id=' + id,
			width : 340,
			height : 240,
			modal : true,
			title : '绑定实体和问卷',
			buttons : [ {
				text : '绑定',
				iconCls : 'icon-ok',
				handler : function() {
					$('#sn_manage_bind_form').form('submit', {
						url : '${pageContext.request.contextPath}/tagSNBatchController/bindSurveyAndEntity.action?id=' + id,
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								$("#bind_dialog").dialog('destroy');
								$('#sn_manage_datagrid').datagrid('load');
							}
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
						}
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
	function sn_manage_add_fn(param){
		var href_ = "";
		var url_ = "";
		var height_ = 335;
		if(param=='pre'){//pre表示预处理
			href_ = "${pageContext.request.contextPath}/tagSNBatchController/add.action";
			url_ = "${pageContext.request.contextPath}/tagSNBatchController/createSN.action";
		}else{//后处理
			href_ = "${pageContext.request.contextPath}/tagSNBatchController/after_add.action";
			url_ = "${pageContext.request.contextPath}/tagSNBatchController/createSNAfter.action";
			height_ = 240;
		}
		$('<div/>').dialog({
			id : 'add_dialog',
			href : href_,
			width : 550,
			height : height_,
			modal : true,
			title : '生成SN序列号',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					$('#sn_manage_add_form').form('submit', {
						url : url_,
						onSubmit:function(){
							if(!$('#sn_manage_add_form').form('validate')){
								$.messager.alert('提示信息','输入项校验失败,不能提交表单!','error');
								return false ;		//当表单验证不通过的时候 必须要return false 
							}
							var mount = $('#sn_manage_add_form').find('#nNumber').val();
							if(null == mount || "" == mount || mount<=0){
								$.messager.alert('提示信息','生成数量必须大于0!','error');
								return false ;		//当表单验证不通过的时候 必须要return false 
							}
							if(mount>1000){
								$.messager.alert('提示信息','生成数量的上限为1000!','error');
								return false ;		
							}
						beforeSend:showProcess(true, '温馨提示', '正在提交数据...')//发送请求前打开进度条 
						}, 
						success : function(result) {
							showProcess(false);
							var r = $.parseJSON(result);
							if (r.status) {
								$("#add_dialog").dialog('destroy');
								$('#sn_manage_datagrid').datagrid('load');
							}
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
						},
						error: function(robj) {
							showProcess(false);
							$.messager.show({
								title : '提示',
								msg : '生成失败！'
							});
						}
				});
			} 
			}],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
		function user_manage_updateStatus_fn(batchId) {
		$('<div/>').dialog({
			id : 'update_dialog',
			href : '${pageContext.request.contextPath}/tagSNBatchController/edit.action?id=' + batchId ,
			width : 200,
			height : 120,
			modal : true,
			title : '修改标签状态',
			buttons : [ {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					$('#user_manage_add_form').form('submit', {
						url : '${appPath}/tagSNBatchController/editSnBatch.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								$("#update_dialog").dialog('destroy');
								$('#sn_manage_datagrid').datagrid('load');
							}
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
						}
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
	
		function downLoadExcel(batch,id,entityName) {
			$.messager.confirm("操作提示", "您确定要导出第"+batch+"批次（"+entityName+"）的标签清单吗？", function (data) {
<%--		$.messager.confirm("操作提示", "您确定要导出第"+batch+"批次吗？", function (data) {--%>
			if (data) {
				window.location.href = "${pageContext.request.contextPath}/tagSNBatchController/dcExcel.action?batch="+batch+"&snBatchId="+id+"&entityName="+ encodeURI(encodeURI(entityName));
			} else {
               	$(this).closed;
           	}
		});
	}
	function downLoad() {
		var rows = $('#sn_manage_datagrid').datagrid('getSelections');
		var ids = [];
		if(rows.length>0){
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].id);
			}
			var idsStr = ids.join(',');
			$.messager.confirm("操作提示", "您确定要选中的标签清单吗？", function (data) {
				if (data) {
					window.location.href = "${pageContext.request.contextPath}/tagSNBatchController/dcExcelZip.action?ids="+idsStr;
				} else {
	               	$(this).closed;
	           	}
			});
		}else{
			$.messager.alert('提示信息','请至少选择一个批次!','info');
		}
	}
	function upLoadExcel() {
		$.messager.show({
			title : '提示',
			msg : "正在继续！"
		});
	}
</script>
</body>
</html>
