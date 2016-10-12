<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>会员缴费管理</title>
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
				<label style="font-size: 12px;">用户名:</label>
				<input name="userName" style="width: 215px;" />
				<label style="font-size: 12px;">会员类型:</label>
				<select name="memberType">
					<option value="">请选择</option>
					<option value="0">个人</option>
					<option value="1">企业</option>
				</select>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFunc();">查询</a>
			</form>
		</div>
		<!-- 数据列表 -->
	  	<div region="center" border="false">
			<table id="member_manage_grid"></table>
	  	</div>
	</div>
	
	<!-- 弹出窗口：添加数据 -->
	<div id="mydialog" style="display:none;padding:5px;width:400px;height:300px;" title="会员缴费"> 
		<form id="form1" action="">
		<input type="hidden" id="memberIds" name="memberIds" value="" />
			<table>
			<tr>
			<td>缴费会员：</td><td><textarea id="memberNames" name='memberNames' readonly='readonly'></textarea></td>
			</tr>
			<tr>
			<td>缴费方式：</td><td><select id="paytype" name="paytype">
					  <option value ="1">现金</option>
					  <option value ="2">网银</option>
					  <option value ="3">支票</option>
					</select></td>
			</tr>
			<tr>
			<td>缴费金额￥：</td><td><input id="paysum" name="paysum" type="text" /></td>
			</tr>
			<tr>
			<td>备注：</td><td><textarea id="remark" name="remark"></textarea> </td>
			</tr>
			</table>
		</form>
	</div>
  	
  	
	<script type="text/javascript">
		var searchFormName = "searchForm";
  		var gridName = "member_manage_grid";
		
		$(function() {
			$('#'+gridName).datagrid({
					url : '${appPath}/membermanage/listpaymentrecords.action',
					fit : true,
					fitColumns : true,
					border : false,
					pagination : true,
					striped:true,
					rownumbers : true,
					idField : 'nmemberid',
					pageSize : 20,
					pageList : [10, 20, 30, 40, 50, 60],
					sortName : 'paysum',
					sortOrder : 'desc',
					singleSelect:false,
					nowrap : false,
					toolbar : [ 
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAdd}">
					{
						text : '添加缴费',
						iconCls : 'icon-add',
						handler : function() {
							paymentInput();
						}
					}
					</c:if>
					],
					columns : [ [{
						field : 'cusername',
						title : '用户名',
						width : 30
					},{
						field : 'cmembertype',
						title : '会员类型',
						width : 40,
						formatter : function(value, row, index) {
							if(value == 0){
								return "个人";
							}else if(value == 1){
								return "企业";
							}else{
								return "个人";
							}
						}
					},{
						field : 'cmobilephone',
						title : '手机',
						width : 40
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
									return "已审核";
							}
						}
					},{
						field : 'paysum',
						title : '缴费总额',
						sortable:true,
						width : 40
					},{
						field : 'paytimes',
						title : '缴费次数',
						sortable:true,
						width : 40
					},{
	   					field:'action',
	   					title:'动作',
	   					width:20,
	   					formatter:function(value,row,index){
	   						return formatString('<img onclick="viewDetails(\'{0}\',\'{1}\');" title="查看详情" src="{2}"/>', row.nmemberid,row.cusername, '${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png');
	   							
	   					}
	   				}
					]]
				});
		});
		
		/*
		 * "查询"按钮的事件处理
		 *	
		*/
		function searchFunc(){
			$('#'+gridName).datagrid('load', serializeObject($('#'+searchFormName)));
		}
		
		function paymentInput(){
			var node = $('#'+gridName).datagrid('getSelections');
			
			if(node==''||node.length<1){
				$.messager.alert(
					'提示',
					'请选择要添加缴费的会员'
				);
			}else{
				var ids='';
				var names='';
				$.each(node,function(){
					if(ids==''){
						ids+=this.nmemberid;
						names+=this.cusername;
					}else{
						ids+=','+this.nmemberid;
						names+=','+this.cusername;
					}
				});
				$('#memberIds').val(ids);
				$('#memberNames').text(names);
				Open_Dialog(); 
				
			}
			
		}
		
		function paymentAdd(){
			var param=$('#form1').formSerialize();
			$.post('${appPath}/membermanage/paymentadd.action',param, function (re) {
		        if (re.status) {
		        	$.messager.alert('提示',re.msg);
		        	$('#mydialog').dialog('close');
		        	$('#'+gridName).datagrid('load');
		        }
		    }, 'json');
		    $('#'+gridName).datagrid('unselectAll');
		}
		
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
					handler: paymentAdd 
					}, 
					{ 
					text: '取消', 
					handler: function() { 
					$('#mydialog').dialog('close'); 
					} 
					}
				] 
			}); 
		}
	
   		//查看个人缴费详情
   		function viewDetails(id,name){
   			location.href = '${appPath}/membermanage/topaymentrecorddetails.action?memberid='+id+'&membername='+encodeURI(encodeURI(name));
   		}
	 
	</script>

</body>
</html>
