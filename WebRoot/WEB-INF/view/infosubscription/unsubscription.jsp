<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息公告管理</title>
<jsp:include page="../common/inc.jsp"></jsp:include>
<link id="artDialogSkin" href="${appPath}/resources/js/plugin/artDialog4.1.7/skins/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${appPath}/resources/js/plugin/artDialog4.1.7/artDialog.js" charset="utf-8"></script>
<style type="text/css">
body{margin:0px;padding:0px;}
.searchDiv{
	overflow: hidden;
	height: 60px; 
	border-bottom: 1px solid #99CCFF;
}
.tt{
	font-size: 12px;
	text-align: right;
}
</style>
</head>

<body>
	<script type="text/javascript">
   		$(function(){
   			$('#unsubscription_datagrid').datagrid({
   				url:'${appPath}/subscription/unsubDataGrid.action',
   				idField:'id',
   				fit:true,
   				fitColumns:true,
   				pagination:true,
   				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
   				rownumbers:true,
   				border:false,
   				animate:false,
   				singleSelect:true,
   				frozenColumns:[[/* {
				title : '编号',
				field : 'id',
				width : 10,
				checkbox : true
			}, */{
   					field:'userName',
   					title:'用户名',
   					width:150,
   					align:'center'
   				},{
   					field:'infoCateName',
   					title:'分类名称',
   					width:120,
   					align:'center'
   				}]],
   				columns:[[{
   					field:'userId',
   					title:'USERID',
   					width:50,
   					hidden:true
   				},{
   					field:'infoCateId',
   					title:'infoCateId',
   					width:50,
   					hidden:true
   				},{
   					field:'unSubTime',
   					title:'退订时间',
   					width:40,
   					align:'center'
   				},{
   					field:'unSubReason',
   					title:'退订原因',
   					width:120,
   					formatter:function(value,row,index){
   						var n = 30;
   						content = value.length>n?value.substring(0,n)+'...':value;
   						var html = "";
   						html += content ;
   						if(value.length>n){
   							html += '<a href="javascript:showDetail('+ row.id +')">详细</a>'
   						}
   						return html;
   					}
   				}]],
   				toolbar:[{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#unsubscription_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		function showDetail(id){
   			$('#unsubscription_datagrid').datagrid('selectRecord',id);
   			var record = $('#unsubscription_datagrid').datagrid('getSelected');
   			var content = record.unSubReason;
   			var dialog = art.dialog({
			    title: '详情',
			    content: content,
			    width: '34em',
			    hight:'80px',
			    lock:true,
			    background: '#FFFFFF', // 背景色
			    opacity: 0.3,
			    icon: 'succeed',
			    button: [{
			    	 name:'关闭'
			    }]
			});
			
			
   			/* $.messager.show({
   				title:'详情',
   				msg:value,
   				timeout:0,
   				width:500,
   				height:100,
   				modal:true,
   				style:{
					right:'',
					top:'100px',
					bottom:''
				}
   			}); */
   		}
   		
   		function unsubscription_manage_search_fn() {
			$('#unsubscription_datagrid').datagrid('load', serializeObject($('#unsubscription_manage_search_form')));
		}
		function cleanForm() {
			$('#unsubscription_manage_search_form input[class="inval"]').val('');
			$('#infoCateId').combobox('clear');
			$('#unsubscription_datagrid').datagrid('load', {});
		}
	</script>
	<div class="searchDiv">
		<form action="javascript:void(0);" id="unsubscription_manage_search_form" >
			<table class="form" style="height:60px;">
				<tr>
					<td class="tt" width="80">用户名</td>
					<td width="160">
						<input  id="userName" name="userName" class="inval"  style="width:150px;" />
					</td>
					<td class="tt" width="80">订阅分类</td>
					<td width="160">
						<input  id="infoCateId" name="infoCateId" class="easyui-combotree" data-options="url:'${appPath}/infoCate/allInfoTree.action',parentField:'parentId',lines:true  " style="width:150px;" />
					</td>
				</tr>
				<tr>
					<td class="tt" >退订日期</td>
					<td>
						<input class="inval" type='text' name=unSubTime id='unSubTime' style='width:150px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />
					</td>
					<td></td>
					<td style="margin-left: 30px; width: 120px;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="unsubscription_manage_search_fn();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="cleanForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:390px;width:100%;">
		<table id="unsubscription_datagrid"></table>
	</div>
</body>
</html>
