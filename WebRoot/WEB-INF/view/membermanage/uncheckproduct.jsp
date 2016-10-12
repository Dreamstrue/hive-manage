<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>企业产品信息审核</title>
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
				<label style="font-size: 12px;">公司名称:</label>
				<input name="enterpriseName" style="width: 215px;" />
				<label style="font-size: 12px;">产品名称:</label>
				<input name="productName" style="width: 215px;" />
				<label style="font-size: 12px;">证书编号:</label>
				<input name="certificateNO" style="width: 215px;" />
				<label style="font-size: 12px;">发证单位:</label>
				<input name="certificationUnit" style="width: 215px;" />
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
					url : '${appPath}/membermanage/listUncheckProduct.action',
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
					toolbar : [
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
					{
						text : '批量审核',
						iconCls : 'icon-add',
						handler : function() {
							check(0);
						}
					} 
					</c:if>
					],
					columns : [[
					{
						field : 'enterpriseName',
						title : '公司名称',
						width : 40
					},
					{
						field : 'cproductname',
						title : '产品名称',
						width : 40
					}
					,{
						field : 'cimplementstandard',
						title : '执行标准编号',
						width : 30
					}
					,{
						field : 'cprocatcode',
						title : '产品类别代码',
						width : 30
					}
					,{
						field : 'cadministrativelicensecategory',
						title : '行政许可类别',
						width : 30
					}
					,{
						field : 'ccertificateno',
						title : '证书编号',
						width : 30
					}
					,{
						field : 'ccertificationunit',
						title : '发证单位',
						width : 30
					}
					,{
						field : 'dcertificateend',
						title : '有效截止日期',
						width : 30
					}
					,{
						field : 'cyearexamine',
						title : '年审情况',
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
								return "通过";
							}else if(value == 2)
							{
								return "未通过";
							}
						}
					}
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
					,{
	   					field:'action',
	   					title:'审批',
	   					width:20,
	   					formatter:function(value,row,index){
	   						return formatString('<img onclick="check(1,\'{0}\');" title="查看详情并审批" src="{1}"/>', row.nentproid, '${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png');
	   							
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
			if(type==0){
				node = $('#member_manage_grid').datagrid('getSelections');
				if(node==''||node.length<1){
					$.messager.alert(
						'提示',
						'请选择要审核的企业'
					);
				}else{
					var ids='';
					var names='';
					$.each(node,function(){
						if(ids==''){
							ids+=this.nintstyleid;
						}else{
							ids+=','+this.nintstyleid;
						}
					});
					$('#ids').val(ids);
					Open_Dialog(); 
				}
			}else if(type==1){
				$('#ids').val(id);
				Open_Dialog();
			}
		}
		
		function checkAdd(){
			if($("#form1").form('validate')){
				var param=$('#form1').formSerialize();
				$.post('${appPath}/membermanage/checkProduct.action',param, function (re) {
			        if (re.status) {
			        	$.messager.alert('提示',re.msg);
			        	$('#mydialog').dialog('close');
			        	$('#member_manage_grid').datagrid('load');
			        }
			    }, 'json');
			    $('#member_manage_grid').datagrid('unselectAll');
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