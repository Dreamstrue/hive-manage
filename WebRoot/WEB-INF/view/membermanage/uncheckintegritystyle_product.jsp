<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
					url : '${appPath}/membermanage/listuncheckintegritystyle_product.action?cproductids=${cproductids}',
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
					toolbar : [{
						text : '返回列表',
						iconCls : 'icon-undo',
						handler : function() {
							//check(0);
							window.location.href = "${pageContext.request.contextPath}/membermanage/touncheckintegritystyle.action";
						}
					} ],
					columns : [[
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