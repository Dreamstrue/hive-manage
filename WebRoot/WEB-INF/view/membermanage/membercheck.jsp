<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>注册会员审核</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../common/inc.jsp"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.form.js"></script>
  </head>
  
  <body>
	<div class="easyui-layout" fit="true">
		<!-- 查询条件 -->
		<div region="north" split="true" title="查询条件" border="false" class="p-search" style="overflow: hidden; height:80px; padding-left: 10px; padding-top: 5px;">
			<form action="javascript:void(0);" id="searchForm" style="width: 100%">
				<label style="font-size: 12px;">姓名:</label>
				<input name="chinesename" style="width: 215px;" />&nbsp;&nbsp;
				<label style="font-size: 12px;">系统账号:</label>
				<input name="cusername" style="width: 215px;" />&nbsp;&nbsp;
				<label style="font-size: 12px;">手机号:</label>
				<input name="cmobilephone" style="width: 215px;" />
					<label style="font-size: 12px;">审核状态:</label>
				<select class="easyui-combobox" id="cmemberstatus" name="cmemberstatus" data-options="prompt:'审核状态',editable:false,panelHeight:'auto'" style="width:150px;">
								<option></option>
								<option value=0>未审核</option>
								<option value=1>已审核</option>
								<option value=2>未通过</option>
				</select>
				
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFunc();">查询</a>
			    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:true" onclick="cleanSearch();">重置</a>
			</form>
		</div>
		<!-- 数据列表 -->
	  	<div region="center" border="false">
			<table id="member_manage_grid"></table>
	  	</div>
	</div>

	<div id="menu_manage_contextMenu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="menu_manage_add_fn();" data-options="iconCls:'icon-add'">增加</div>
		<div onclick="menu_manage_edit_fn();" data-options="iconCls:'icon-edit'">编辑</div>
		<div onclick="menu_manage_del_fn();" data-options="iconCls:'icon-remove'">删除</div>
	</div>
	
		<!-- 弹出窗口：添加数据 --> 
	<div id="mydialog" style="display:none;padding:5px;width:400px;height:300px;" title="会员审核"> 
		<form id="form1" action="">
		<input type="hidden" id="ids" name="ids" value="" />
			<table>
			<tr>
			<td>审核结果：</td><td><select id="checktype" name="checktype">
					  <option value ="1">通过</option>
					  <option value ="2">不通过</option>
					</select></td>
			</tr>
			<tr>
			<td>审核意见：</td><td><textarea rows="5" cols="30" id="remark" class="easyui-validatebox" data-options="required:true" name="remark"></textarea> </td>
			</tr>
			</table>
		</form>
	</div>
	
	<script type="text/javascript">
		
		var searchFormName = "searchForm";
  		var gridName = "member_manage_grid";
		
		$(function() {
			$('#'+gridName).datagrid({
					url : '${appPath}/membermanage/findUser.action',
					fit : true,
					fitColumns : true,
					border : false,
					pagination : true,
					striped:true,
					rownumbers : true,
					idField : 'nmemberid',
					pageSize : 20,
					pageList : [10, 20, 30, 40, 50, 60],
					sortName : 'nmemberid',
					sortOrder : 'desc',
					checkOnSelect : false,
					selectOnCheck : false,
					singleSelect:true,
					nowrap : false,
					toolbar : [
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
					{
						text : '审核',
						iconCls : 'icon-edit',
						handler : function() {
							checkMamber();
						}
					},{
						text : '修改密码',
						iconCls : 'icon-edit',
						handler : function() {
							updatePassword();
						}
					},
					</c:if>
					{
						text : '刷新页面',
						iconCls : 'icon-reload',
						handler : function() {
							$('#'+gridName).datagrid('load');
						}
					}],
					columns : [[{
						field : 'chinesename',
						title : '姓名',
						width : 30
					},{
						field : 'cusername',
						title : '系统账号',
						width : 30
					},{
						field : 'cnickname',
						title : '昵称',
						width : 30,
						hidden : true
					},{
						field : 'csex',
						title : '性别',
						width : 20,
						formatter : function(value, row, index) {
							if(value == 0)
							{
								return "女";
							}
							else
							{
								return "男";
							}
						}
					},{
						field : 'iage',
						title : '年龄',
						sortable:true,
						width : 20
					},{
						field : 'ceducation',
						title : '学历',
						width : 20,
						hidden : true,
						formatter : function(value, row, index){
							if(value == 1){
								return "小学";
							}else if(value == 2){
								return "初中";
							}else if(value == 3){
								return "高中";
							}else if(value == 4){
								return "大专";
							}else if(value == 5){
								return "本科";
							}else if(value == 6){
								return "硕士";
							}else if(value == 7){
								return "博士";
							}
						}
					},{
						field : 'caddress',
						title : '地址',
						width : 40
					},{
						field : 'cemail',
						title : '电子信箱',
						width : 40
					},{
						field : 'cmobilephone',
						title : '手机',
						width : 40
					},{
						field : 'cmembertype',
						title : '会员类型',
						width : 40,
						formatter : function(value, row, index) {
							if(value == 1){
								return "企业";
							}else if(value == 0){
								return "个人";
							}else{
								return "匿名";
							}
						}
					},{
						field : 'cmemberlevel',
						title : '会员等级',
						width : 40
					},{
						field : 'dvalidend',
						title : '有效期至',
						width : 40
					},{
						field : 'cmemberstatus',
						title : '会员状态',
						width : 40,
						formatter : function(value, row, index) {
							if(value == 0){
								return "未审核";
							}else if(value == 1){
								return "已通过";
							}else if(value == 2){
								return "未通过";
							}
						}
					}
				]]
			});
		});// 页面加载完后的初始化完毕
		
		/*
		 * "查询"按钮的事件处理
		 *	
		*/
		function searchFunc(){
			$('#'+gridName).datagrid('load', serializeObject($('#'+searchFormName)));
		}
	
		/**
		function checkAllow(){
			var node = $('#'+gridName).datagrid('getSelections');
			
			if(node==''||node.length<1){
				$.messager.alert(
					'提示',
					'请选择要审核的会员'
				);
			}else{
				var ids='';
				$.each(node,function(){
					if(ids==''){
						ids+=this.nmemberid;
					}else{
						ids+=','+this.nmemberid;
					}
				}); 
				$.post('${appPath}/membermanage/checkmember.action','ids='+ids+'&status=1', function (re) {
		            if (re.status) {
		            	$.messager.alert(
							'提示',
							re.msg
						);
		            	$('#'+gridName).datagrid('load');
		            }
		        }, 'json');
		        $('#'+gridName).datagrid('unselectAll');
			}
			
		}// End Of checkAllow()
		*/
		function checkMamber(){
			var node = $('#'+gridName).datagrid('getSelections');
			if(node==''||node.length<1){
				$.messager.alert(
					'提示',
					'请选择要审核的会员'
				);
			}else{
				var ids='';
				var status='0';
				$.each(node,function(){
					status=this.cmemberstatus;
					if(ids==''){
						ids+=this.nmemberid;
					}else{
						ids+=','+this.nmemberid;
					}
				});
				$('#ids').val(ids);
				if(status==0){
     			 Open_Dialog();
				}else{
					$.messager.alert(
							'提示',
							'请选择未审核的会员'
						);
				}
			}
			
		}// End Of checkAllow()
		
		//修改密码
		function updatePassword(){
			var node = $('#'+gridName).datagrid('getSelections');
			if(node==''||node.length!=1){
				$.messager.alert(
					'提示',
					'请选择一个会员'
				);
			}else{
				var nmemberid=node[0].nmemberid;
				$('<div/>').dialog({
					id : 'password_dialog',
					href : '${pageContext.request.contextPath}/membermanage/updatePassword.action?nmemberid=' + nmemberid,
					width : 300,
					height : 200,
					modal : true,
					title : '更改密码',
					buttons : [ {
						text : '更改',
						iconCls : 'icon-ok',
						handler : function() {
							$('#manage_create_form').form('submit', {
								url : '${appPath}/membermanage/savePassword.action',
								success : function(result) {
									var r = $.parseJSON(result);
									if (r.status) {
										$("#password_dialog").dialog('destroy');
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
				
				
// 				var ids='';
// 				var status='0';
// 				$.each(node,function(){
// 					status=this.cmemberstatus;
// 					if(ids==''){
// 						ids+=this.nmemberid;
// 					}else{
// 						ids+=','+this.nmemberid;
// 					}
// 				});
// 				$('#ids').val(ids);
// 				if(status==0){
//      			 Open_Dialog();
// 				}else{
// 					$.messager.alert(
// 							'提示',
// 							'请选择未审核的会员'
// 						);
// 				}
			}
			
		}
		function checkAdd(){
			if($("#form1").form('validate')){
				var param=$('#form1').formSerialize();
				$.post('${appPath}/membermanage/checkmember.action',param, function (re) {
			        if (re.status) {
			        	$.messager.alert('提示',re.msg);
			        	$('#mydialog').dialog('close');
			        	$('#member_manage_grid').datagrid('load');
			        }
			    }, 'json');
			    $('#member_manage_grid').datagrid('unselectAll');
			    window.parent.reloadWestTree();//刷新左侧菜单
		    }
		}// End Of checkAdd()
	
		function menu_manage_edit_fn(id) {
			if (id != undefined) {
				$('#member_manage_grid').treegrid('select', id);
			}
			var node = $('#member_manage_grid').treegrid('getSelected');
			$('<div/>').dialog({
				id:'update_dialog',
				href : '${appPath}/menu/edit.action?id='+node.id,
				width : 300,
				height : 250,
				modal : true,
				title : '编辑菜单',
				buttons : [ {
					text : '确定',
					iconCls : 'icon-ok',
					handler : function() {
						var d = $(this).closest('.window-body');
						$('#menu_manage_edit_form').form('submit', {
							url : '${appPath}/menu/update.action',
							success : function(result) {
								var r = $.parseJSON(result);
								if (r.status) {
									$("#update_dialog").dialog('destroy');
									$('#member_manage_grid').treegrid('reload');
									//$('#layout_west_tree').tree('reload');  // 不知为何不执行，下面用了一种变通的方式
									window.parent.reloadWestTree(); 
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
		}// End Of menu_manage_edit_fn()
		
		function cleanSearch() {
			$('#'+searchFormName+' input').val('');
			$('#'+gridName).datagrid('load', {});
			$('#'+gridName).datagrid('clearSelections');
		}
		function menu_manage_del_fn(id) {
			if (id != undefined) {
				$('#member_manage_grid').treegrid('select', id);
			}
			var node = $('#member_manage_grid').treegrid('getSelected');
			if (node) {
				$.messager.confirm('询问', '您确定要删除【' + node.text + '】？', function(b) {
					if (b) {
						$.post('${appPath}/menu/delete.action',{id:node.id},function(data){
							if (data.status) {
								$('#member_manage_grid').treegrid('remove', node.id);
								//$('#layout_west_tree').tree('reload');  // 不知为何不执行，下面用了一种变通的方式
								window.parent.reloadWestTree();
							}
							$.messager.show({
								msg : data.msg,
								title : '提示'
							});
						},"json");
						
					}
				});
			}
		}// End Of menu_manage_del_fn()
		
		function Open_Dialog() {
			$('#mydialog').show(); 
			$('#mydialog').dialog({ 
				collapsible: true, 
				maximizable: true, 
				modal:true,
				buttons: [
					{ 
					text: '提交', 
					iconCls: 'icon-ok', 
					handler: checkAdd 
					}, 
					{ 
					text: '取消', 
					handler: function() { 
					$('#mydialog').dialog('close'); 
					} 
					}
				] 
			}); 
		}// End Of Open_Dialog()
	</script>
	
</body>
</html>