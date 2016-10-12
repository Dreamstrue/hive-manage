<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>标签管理</title>
	<jsp:include page="../../common/inc.jsp"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.form.js"></script>
  <style>
  .textbox{
	  height : 20px;
	  margin : 0;
	  paddin : 0 2px;
	  box-sizing : content-box;
	  width : 110px;
  }
  </style>
  
  </head>
<body>

<div class="easyui-layout" fit ="true">
		<!-- 查询条件 -->
		<div region="north" split="true" title="查询条件" border="false" class="p-search" style="overflow: hidden; height:100px;padding-left: 10px; padding-top: 5px;">
			<form action="javascript:void(0);" id="searchForm" style="width: 100%">
               <div>		
				<label style="font-size: 12px;">批次编号:&nbsp;</label>
				<input name="batch" style="width: 120px;" />
				<label style="font-size: 12px;">SN码:&nbsp;</label>
				<input name="sn" style="width: 120px;" />
				<label style="font-size: 12px;">行业实体:&nbsp;</label>
				<input id="industryEntityId" name="industryEntityId" type="hidden"/>
				<input class="easyui-textbox" id="industryEntityText" name="industryEntityText" data-options="prompt:'点击选择实体名称'" style="width: 120px;" />
<!-- 				<input id="industryEntityText" onclick="queryAllIndustryEntity('industryEntityId','industryEntityText')" style="width: 120px;" /> -->
<!-- 				<input id="industryEntityId" name="industryEntityId" class="easyui-combobox" data-options="url:'${pageContext.request.contextPath}/industryEntityManage/allindustryEntityInfo.action',valueField:'id',textField:'entityName'" style="width:120px;" /> -->
				    </div><div>
				<label style="font-size: 12px;">黑名单状态:</label>
				<select class="easyui-combobox" id="blackList" name="blackList" data-options="prompt:'黑名单',editable:false,panelHeight:'auto'" style="width:120px">
											<option value="" ></option>
											<option value="0">正常</option>
											<option value="1">黑名单</option>
			     </select>
			     <label style="font-size: 12px;">是否查询：</label>
			     <select class="easyui-combobox" id="queryNum" name="queryNum" data-options="prompt:'查询次数',editable:false,panelHeight:'auto'" style="width:120px">
											<option value="" ></option>
											<option value="0">未查询</option>
											<option value="1">已查询</option>
			     </select>
				<label style="font-size: 12px;">印刷状态:</label>
				<select class="easyui-combobox" id="status" name="status" data-options="prompt:'标签状态',editable:false,panelHeight:'auto'" style="width:120px">
								<option value="" ></option>
								<option value="0">未印刷</option>
								<option value="5">印刷中</option>
								<option value="6">已印刷</option>
								<option value="1">入库</option>
								<option value="2">出库</option>
								<option value="3">绑定产品</option>
								<option value="4">已作废</option>
				</select>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="obj.search();">查询</a>
							&nbsp<a id="cleanBtn" class="easyui-linkbutton" data-options="plain: true,iconCls:'icon-clear'" >重置</a>
			</div>
			</form>
		</div>
	<!-- 数据列表 -->
	<div data-options="region:'center',border:false">
			<table id="blackList_manage_datagrid"></table>
	</div>
		
</div>
<script type="text/javascript">
	var gridName = "blackList_manage_datagrid";
	$(function() {   
		$('#industryEntityText').textbox('textbox').focus(function() {
			queryAllIndustryEntity('industryEntityId','industryEntityText');
    	}); 
		obj = {
				search : function(){
					$('#'+gridName).datagrid('load',{
						batch : $('input[name="batch"]').val(),
						sn : $('input[name="sn"]').val(),
						industryEntityId : $('input[name="industryEntityId"]').val(),
						blackList : $('input[name="blackList"]').val(),
						queryNum : $('input[name="queryNum"]').val(),
						status : $('input[name="status"]').val(),
					});
				},
				black : function(){
					var rows =  $('#'+gridName).datagrid('getChecked');
					if(rows.length > 0){
						$.messager.confirm('确定操作','您是将选中的 '+rows.length+' 个标签加入黑名单吗？',function(tag){
							if(tag){
								var ids = [];
								for(var i = 0; i<rows.length; i++){
									ids.push(rows[i].id);
								}
								var idss = ids.join(',');
								$.ajax({    
									type:'post',    
									url:'${pageContext.request.contextPath}/SNBaseController/markBlackInBatch.action',    
								    data:{
									    "ids" : idss
									    },    
								    cache:false,    
								    dataType:'json',  
								    async:false, 
								    success:function(re){
										if(re.status){
								    	$.messager.show({
											title : '提示',
											msg : re.msg
										});
										}else{
											alert(re.msg);
										}
										$('#'+gridName).datagrid('clearChecked');
										$('#'+gridName).datagrid('load');
									}
								});
							}
						});
					}else {
						$.messager.alert('提示','请选择需要加入黑名单的标签！','info');
					}
				}
		}
		
		$('#'+gridName).datagrid({
			url : '${pageContext.request.contextPath}/SNBaseController/queryList.action',
			fit : true,
			title : "标签管理列表",
			fitColumns : true,
			border : false,
			rownumbers : true,
			pagination : true,
			striped : true,
			idField : 'id',
			pageSize : 20,
			pageList : [20, 40, 100, 200 ],
			checkOnSelect : false,
			selectOnCheck : true,
			nowrap : true,
			toolbar : [
						{
							text : '批量加入黑名单',
							iconCls : 'icon-add',
							handler : function() {
								obj.black();
							}
						},
						{
							text : '批量修改状态',
							iconCls : 'icon-add',
							handler : function() {
								user_manage_updateStatus_fn(null);
							}
						}
						],
			columns : [[
				{ field : 'id', title : 'id' ,align :'center', checkbox:true},
				{ field : 'strBatch', title : '批次编号', width : 50, align : 'center'},
				{ field : 'sn', title : 'SN码 ', width : 80 ,align :'center'},
				{ field : 'strentityName', title : '行业实体', width : 120, align : 'center'},
				{ field : 'surveyTitle',title: '对应问卷',  align :'left',width: 150},
				{ field : 'queryPath', title : '查询路径', width : 100, align :'center'},
				{ field : 'queryNum', title : '是否查询', width : 50, align :'center',
					formatter: function(value) {
					if(value==0) {
							return "否";
						} else if(value==1) {
							return "是";
						}
					},
					styler: function (value, record, index) {
						if(value == '1'){
							return 'background-color:red';
						}
			        }
				},
				{ field : 'blackList', title : '黑名单', width : 50, align :'center',
					formatter: function(value) {
						if(value==0) {
							return "正常";
						} else if(value==1) {
							return "黑名单";
						}
					}
				},
				{ field : 'status', title : '标签状态', width : 80, align :'center',
					formatter: function(value) {
						if(value=='0') {
							return "未印刷";
						} else if(value=='1') {
							return "入库";
						}else if(value=='2') {
							return "出库";
						}else if(value=='3') {
							return "绑定产品";
						}else if(value=='4') {
							return "作废";
						}else if(value=='5') {
							return "印刷中";
						}else if(value=='6') {
							return "已印刷";
						}
					}
				},
				{
					field : 'action',
					title : '操作',
					width : 50,
					align :'center',
					formatter : function(value, row, index) {
							var html = "";
							if(row.blackList == 0 &&row.status>0){
								html += '<img onclick="blackList_manage_markBlackList_fn(\'' + row.id + '\');" src="${appPath}/resources/images/extjs_icons/lightning.png" title="加入黑名单"/>';
							}else if(row.blackList == 1){
								html += '<img onclick="blackList_manage_quitBlackList_fn(\'' + row.id + '\');" src="${appPath}/resources/images/extjs_icons/lightning_delete.png" title="退出黑名单"/>';
							}
							html += '&nbsp;<img onclick="user_manage_look_fn(\'' + row.id + '\');" src="${appPath}/resources/images/extjs_icons/table/table.png" title="查看标签详情"/>';
							html += '&nbsp<img onclick="user_manage_updateStatus_fn(\'' + row.id + '\');" src="${appPath}/resources/images/extjs_icons/table/table_relationship.png" title="更改标签状态"/>';	
							//html+='&nbsp;<img onclick="create_twocode(\''+ row.id + '\',\'' + row.queryPath +  '\');"  src="${appPath}/resources/images/extjs_icons/barcode.png" title="创建二维码"/>';
							return html;                                 
					}
				} 
			]]
		});
        
		 $("#cleanBtn").click(function () {
			 $("#searchForm").form("clear");
			 $('#blackList_manage_datagrid').datagrid('load', {});
         });
	});

	 function blackList_manage_markBlackList_fn(id){
			$.ajax({    
				type:'post',    
				url:'${pageContext.request.contextPath}/SNBaseController/markBlackList.action',    
			    data:{
				    "id" : id
				    },    
			    cache:false,    
			    dataType:'json',  
			    async:false, 
			    success:function(re){
					if(re.status){
			    	$.messager.show({
						title : '提示',
						msg : re.msg
					});
					}else{
						alert(re.msg);
					}
					$('#'+gridName).datagrid('clearChecked');
					$('#'+gridName).datagrid('load');
				}
			});
		}

	 function blackList_manage_quitBlackList_fn(id){
			$.ajax({    
				type:'post',    
				url:'${pageContext.request.contextPath}/SNBaseController/quitBlackList.action',    
			    data:{
				    "id" : id
				    },    
			    cache:false,    
			    dataType:'json',  
			    async:false, 
			    success:function(re){
					if(re !=null){
				    	$.messager.show({
							title : '提示',
							msg : re.msg
						});
					}
					$('#'+gridName).datagrid('clearChecked');
					$('#'+gridName).datagrid('load');
				}
			});
		}
	 
	 	function user_manage_updateStatus_fn(baseId) {
	 		var rows =  $('#'+gridName).datagrid('getChecked');
	 		if(rows.length > 0||baseId!=null){
			   var ids = [];
				for(var i = 0; i<rows.length; i++){
					ids.push(rows[i].id);
				}
			    var idss = ids.join(',');
			    if(baseId!=null){
			    	baseId=baseId;
			    }else{
			    baseId=idss.toString();
			    }
			  $('<div/>').dialog({
				id : 'update_dialog',
				href : '${pageContext.request.contextPath}/SNBaseController/edit.action?id=' + baseId ,
				width : 200,
				height : 120,
				modal : true,
				title : '修改标签状态',
				buttons : [ {
					text : '修改',
					iconCls : 'icon-edit',
					handler : function() {
						$('#user_manage_add_form').form('submit', {
							url : '${appPath}/SNBaseController/editSnBase.action',
							success : function(result) {
								var r = $.parseJSON(result);
								if (r.status) {
									$("#update_dialog").dialog('destroy');
									$('#blackList_manage_datagrid').datagrid('load');
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
		}else if(baseId==null){
			    	$.messager.alert('提示','请选择需要修改状态的标签！','info');
			    }
	}
	//查看标签详情
	function user_manage_look_fn(id) {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/SNBaseController/viewBaseInfo.action?baseId=' + id,
			width : 500,
			height : 400,
			modal : true,
			title : '查看标签详情',
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
	

	//生产二维码图片
	function create_twocode(id,queryPath){
		alert(queryPath);
	//   window.location.href = "${pageContext.request.contextPath}/SNBaseController/createCode.action?id="+id+"&=queryPath"+queryPath;
			$.post("${pageContext.request.contextPath}/SNBaseController/createCode.action",{id : id, queryPath : queryPath},function(r){
				if (r.status) {
					$('#'+gridName).datagrid('reload');
					$.messager.show({
						title : '提示',
						msg : r.msg,
						timeout: 8000
					});
				}
			},'json');
 		}
	
	
	
	function viewQrcodeDetail(content){
		var href = content;
	    //document.location.href = "<c:url value='"+href+"'/>";
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+href+'" style="width:100%;height:100%;"></iframe>';
		parent.layout_center_addTabFun({title:'二维码扫描', content:content, closable:true});
	}
	 
</script>
</body>
</html>