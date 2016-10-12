<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>企业产品信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../common/inc.jsp"></jsp:include>
  </head>
  
  <body>
  	<input type="hidden" id="entInfoId" name="entInfoId" value="${entInfoId}"/>
	<table id="gridTable"></table>
  </body>
  
  <script type="text/javascript">
  	
  	var gridName = "gridTable";
  	var gridIdCol = "nentproid";
  
	$(function(){
		
		// 获取指定企业的产品信息列表
		var entInfoId = $('#entInfoId').val();
		var gridUrl = '${pageContext.request.contextPath}/enterpriseManage/listProduct.action?entInfoId='+entInfoId;
		if(entInfoId){
			$("#"+gridName).datagrid({
				url : gridUrl,
				fit : true,
				fitColumns : true,
				border : false,
				pagination : true,
				rownumbers : true,
				idField : 'nenterpriseid',
				pageSize : 20,
				pageList : [10, 20, 30, 40, 50, 60],
				sortName : 'dcreatetime',
				sortOrder : 'desc',
				checkOnSelect : false,
				selectOnCheck : false,
				singleSelect:true,
				nowrap : false,
				toolbar : [{
					text : '增加企业产品信息',
					iconCls : 'icon-add',
					handler : function() {
						addFunc();
					}
				},'-',{
					text : '编辑企业产品信息',
					iconCls : 'icon-edit',
					handler : function() {
						editFunc();
					}
				},'-',{
					text : '删除企业产品信息',
					iconCls : 'icon-remove',
					handler : function() {
						delFunc();
					}
				}],
				columns : [[{
					field : 'nentproid',
					title : '产品信息ID',
					hidden : true
				},{
					field : 'cproductname',
					title : '产品名称',
					width : 80
				},{
					field : 'cimplementstandard',
					title : '执行标准编号',
					width : 80
				},{
					field : 'cprocatname',
					title : '产品类别',
					width : 80
				},{
					field : 'cadministrativelicensecategory',
					title : '行政许可类别',
					width : 80
				},{
					field : 'chasannex',
					title : '附件',
					width : 50,
					formatter : function(value,row,index){
						var annex = row.annex;
						if(annex && annex != null){
							return "<a href='${pageContext.request.contextPath}/annex/download.action?id="+ annex.id +"' title='"+ annex.cfilename +"'>" + annex.cfilename + "</a>";
						}else{
							return "无";
						}
					}
				}]]
			});
		}//End if
		
	});
	
	
	//==============================
	
	var dialog;
	
	/*
		添加企业产品信息窗口
	*/
	function addFunc(){
		var entInfoId = $('#entInfoId').val();
		var url = "${pageContext.request.contextPath}/enterpriseManage/toProductAdd.action?entInfoId="+entInfoId;
		var title = '添加企业产品信息';
		
		dialog = $('<div/>').dialog({
			href : url,
			width :600,
			height : 400,
			modal : true,
			title : title,
			buttons:[{
				text:'提交',
				iconCls:'icon-ok',
				handler:function(){
					var d = $(this).closest('.window-body');
					$('#dataForm').form('submit',{
						url:'${pageContext.request.contextPath}/enterpriseManage/saveProduct.action',
						success:function(result){
							var resultObj = $.parseJSON(result);
		 					if(resultObj.status){
		 						$('#'+gridName).datagrid('load');
		 						d.dialog('destroy');
		 					}else{
		 						easyuiBox.alert(result.msg);
		 					}
							
						}
					});
				}
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					var d = $(this).closest('.window-body');
					d.dialog('destroy');
				}
			}],
			onClose : function(){
				$(this).dialog('destroy');
			},
			
		});
		
	}// End Of addFunc()
	
	/*
		编辑企业产品信息窗口
	*/
	function editFunc(){
		var entInfoId = $('#entInfoId').val();
		var url = "${pageContext.request.contextPath}/enterpriseManage/toProductEdit.action?entInfoId="+entInfoId;
		var title = '编辑企业宣传资料';
		var selectedRow = getSelectedRow();
		var productId = selectedRow[gridIdCol];
		url = url + "&productId="+productId;
		
		dialog = $('<div/>').dialog({
			href : url,
			width :600,
			height : 400,
			modal : true,
			title : title,
			buttons:[{
				text:'提交',
				iconCls:'icon-ok',
				handler:function(){
					var d = $(this).closest('.window-body');
					// 将要删除的附件ID数组中的数据写到表单中
					$('#annexDelIds').val(annexDelIds);
					$('#dataForm').form('submit',{
						url:'${pageContext.request.contextPath}/enterpriseManage/updateProduct.action',
						success:function(result){
							var resultObj = $.parseJSON(result);
		 					if(resultObj.status){
		 						$('#'+gridName).datagrid('load');
		 						d.dialog('destroy');
		 					}else{
		 						easyuiBox.alert(resultObj.msg);
		 					}
							
						}
					});
				}
			},{
				text:'关闭',
				iconCls:'icon-cancel',
				handler:function(){
					var d = $(this).closest('.window-body');
					d.dialog('destroy');
				}
			}],
			onClose : function(){
				$(this).dialog('destroy');
			},
			
		});
		
	}// End Of editFunc()
	
	/*
		删除一条宣传资料
	*/
	function delFunc(){
		var selectedRow = getSelectedRow();
		if(selectedRow==''||selectedRow.length<1){
			easyuiBox.alert('请选择要删除的数据');
		}else{
			easyuiBox.confirm("确认", "确定要删除吗？", function(){
				var productId = selectedRow[gridIdCol];
				var delUrl = "${pageContext.request.contextPath}/enterpriseManage/delProduct.action?productId="+productId;
				// 发送删除请求
	 			$.ajax({
	 				url : delUrl,
	 				dataType : 'json',
	 				success : function(result) {
	 					if(result.status){
	 						$('#'+gridName).datagrid('reload');
	 						easyuiBox.show(result.msg);
	 					}else{
	 						easyuiBox.alert(result.msg);
	 					}
	 					
	 				},
	 				error : function(result) {
	 					easyuiBox.error("出现错误，请稍后重试！");
	 				}
	 			});
				
			});
		}//End Of else()
		
	}// End Of delFunc()
	
	/*
		获取列表的选中行
	*/
	function getSelectedRow(){
		var selectedRow = $('#'+gridName).datagrid('getSelected');
		if(!selectedRow){
			easyuiBox.error('请选择一条记录!');
			return;
		}else{
			return selectedRow;
		}
	}
	
</script>
  
</html>