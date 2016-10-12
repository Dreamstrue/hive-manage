<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>企业基本信息审核</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../common/inc.jsp"/>
	<script src="${pageContext.request.contextPath}/resources/js/jquery.form.js" type=text/javascript></script>
  </head>
  
  <body>
  	<div class="easyui-layout" fit="true">
  		<!-- 查询条件 -->
  		<div region="north" split="true" title="查询条件" border="false" class="p-search" style="overflow: hidden; height:80px; padding-left: 10px; padding-top: 5px;">
  			<form action="javascript:void(0);" id="searchForm" style="width: 100%">
				<label style="font-size: 12px;">公司名称:</label>
				<input name="entName" style="width: 215px;" />
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFunc();">查询</a>
			</form>
  		</div>
  		<!-- 数据列表 -->
	  	<div region="center" border="false" title="数据列表">
			<table id="member_manage_grid"></table>
	  	</div>
  	</div>
  
  	
	<!-- 弹出窗口：添加数据 --> 
	<div id="mydialog" style="display:none;padding:5px;width:400px;height:300px;" title="会员缴费"> 
		<form id="form1" action="">
		<input type="hidden" id="memberIds" name="memberIds" value="" />
			<table>
			<tr>
			<td>审核企业：</td><td><textarea id="memberNames" name='memberNames' readonly='readonly'></textarea></td>
			</tr>
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
					url : '${appPath}/membermanage/listuncheckenterprise.action',
					fit : true,
					fitColumns : true,
					border : false,
					pagination : true,
					striped:true,
					rownumbers : true,
					idField : 'nentproid',
					pageSize : 20,
					pageList : [10, 20, 30, 40, 50, 60],
					sortName : '',
					sortOrder : 'desc',
					singleSelect:false,
					nowrap : false,
					toolbar : [ ],
					
					columns : [ [
					
					{
						field : 'centerprisename',
						title : '公司名称',
						width : 40
					}
					,{
						field : 'corganizationcode',
						title : '组织机构代码证号',
						width : 30
					}
					,{
						field : 'destablishdate',
						title : '成立时间',
						width : 30
					}
					,{
						field : 'clegal',
						title : '法定代表人',
						width : 30
					}
					,{
						field : 'cbusinesslicenseno',
						title : '营业执照编号',
						width : 30
					}
					,{
						field : 'cauditstatus',
						title : '审核状态',
						width : 40,
						formatter : function(value, row, index) {
							if(value == 0)
							{
								return "未审核";
							}else if(value == 1)
							{
								return "已通过";
							}else if(value == 2)
							{
								return "未通过";
							}
						}
					}
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
					,{
	   					field:'action',
	   					title:'查看&审批',
	   					width:20,
	   					formatter:function(value,row,index){
	   						return formatString('<img onclick="check(1,\'{0}\');" title="查看详情并审批" src="{1}"/>', row.nenterpriseid, '${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png');
	   							
	   					}
	   				}
	   				</c:if>
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
		
		
		function check(type,id,name){
			location.href="${pageContext.request.contextPath}/membermanage/toEntInfoEdit.action?entInfoId="+id;
		}
	
		function checkAdd(){
			if($("#form1").form('validate')){
				var param=$('#form1').formSerialize();
				$.post('${appPath}/membermanage/checkintegritystyle.action',param, function (re) {
			        if (re.status) {
			        	$.messager.alert('提示',re.msg);
			        	$('#mydialog').dialog('close');
			        	$('#'+gridName).datagrid('load');
			        }
			    }, 'json');
			    $('#'+gridName).datagrid('unselectAll');
			    window.parent.reloadWestTree();//刷新左侧菜单
		    }
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
		}
	
	</script>
</body>
</html>
