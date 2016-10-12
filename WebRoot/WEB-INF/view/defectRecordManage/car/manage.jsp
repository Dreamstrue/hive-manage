<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>汽车消费品管理</title>
	<jsp:include page="../../common/inc.jsp"/>
	<script type="text/javascript" src="${appPath}/resources/js/jquery.form.js"></script>
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
		<div region="north" split="true" title="查询条件" border="false" class="p-search" style="overflow: hidden; height:80px;padding-left: 10px; padding-top: 10px;">
			<form action="javascript:void(0);" id="searchForm" style="width: 100%">
               <div>		
				<label style="font-size: 12px;">车主姓名:</label>
				<input name="carownername" style="width: 120px;" />
				<label style="font-size: 12px;">车辆品牌:</label>
				<input name="carbrand" class="easyui-combobox" data-options="url:'${appPath}/defectCar/getCarBrandList.action',valueField:'id',textField:'brandname',lines:true, " style="width: 120px;" />
				<label style="font-size: 12px;">生成厂商:</label>
				<input name="carproducername" style="width: 120px;" />
				<label style="font-size: 12px;">年款：</label>
			      <select class="easyui-combobox" id="carmodelyear" name="carmodelyear" data-options="editable:false,panelHeight:'auto'" style="width:120px">
				<option value="" style=color:#B0B0B0>*请选择年款</option>
            
            		<option value="2015" style=color:black>2015</option>
            	
            		<option value="2014" style=color:black>2014</option>
            	
            		<option value="2013" style=color:black>2013</option>
            	
            		<option value="2012" style=color:black>2012</option>
            	
            		<option value="2011" style=color:black>2011</option>
            	
            		<option value="2010" style=color:black>2010</option>
            	
            		<option value="2009" style=color:black>2009</option>
            	
            		<option value="2008" style=color:black>2008</option>
            	
            		<option value="2007" style=color:black>2007</option>
            	
            		<option value="2006" style=color:black>2006</option>
            	
            </select>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="obj.search();">查询</a>
							&nbsp<a id="cleanBtn" class="easyui-linkbutton" data-options="plain: true,iconCls:'icon-clear'" >重置</a>
			</div>
			</form>
		</div>
	<!-- 数据列表 -->
	<div data-options="region:'center',border:false">
			<table id="carList_manage_datagrid"></table>
	</div>
		
</div>
<script type="text/javascript">
	var gridName = "carList_manage_datagrid";
	$(function() {   

		obj = {
				search : function(){
					$('#'+gridName).datagrid('load',{
						carownername : $('input[name="carownername"]').val(),
						carbrand : $('input[name="carbrand"]').val(),
						carproducername : $('input[name="carproducername"]').val(),
						carmodelyear : $('input[name="carmodelyear"]').val(),
					});
				}
		}
		
		$('#'+gridName).datagrid({
			url : '${appPath}/defectCar/queryList.action',
			fit : true,
			title : "缺陷产品列表",
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
// 						{
// 							text : '批量审核',
// 							iconCls : 'icon-add',
// 							handler : function() {
// 								user_manage_updateStatus_fn(null);
// 							}
// 						},
						{
		   					text:'刷新',
		   					iconCls:'icon-reload',
		   					handler:function(){
		   						$('#'+gridName).datagrid('reload');
		   					}
						}],
			columns : [[
				{ field : 'mainId', title : 'mainId' ,align :'center', hidden:true},
				{ field : 'carownername', title : '车主姓名 ', width : 50 ,align :'center'},
				{ field : 'carproducername', title : '生产者名称 ', width : 50 ,align :'center'},
				{ field : 'carbrandName', title : '车辆品牌 ', width : 50 ,align :'center'},
				{ field : 'carmodelForName', title : '车型名称', width : 50, align : 'center'},
				{ field : 'carmodelyear', title : '车款', width : 50, align :'center'},
				{ field : 'assemblyName', title : '总成', width : 50, align : 'center'},
				{ field : 'subAssemblyName', title : '分总成', width : 50, align : 'center'},
				{ field : 'reportdate', title : '报告日期', width : 50, align : 'center',
					formatter:function(value,row,index){ 
	                	if(value!=null) {
	                		var timestamp = new Date(value);  
		                 	return timestamp.Format("yyyy-MM-dd hh:mm:ss"); 
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
							html += '&nbsp;<img onclick="user_manage_look_fn(\'' + row.mainId + '\');" src="${appPath}/resources/images/extjs_icons/table/table.png" title="查看标签详情"/>';
							html += '&nbsp;<img title="删除" onclick="prize_manage_delete_fn(' + row.mainId + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
							//html+='&nbsp;<img onclick="create_twocode(\''+ row.id + '\',\'' + row.queryPath +  '\');"  src="${appPath}/resources/images/extjs_icons/barcode.png" title="创建二维码"/>';
							return html;                                 
					}
				} 
			]]
		});
        
		 $("#cleanBtn").click(function () {
			 $("#searchForm").form("clear");
			 $('#carList_manage_datagrid').datagrid('load', {});
         });
	});

	 
	//删除缺陷信息
		function prize_manage_delete_fn(id){
		alert(id);
			if(id!=undefined){
				$('#'+gridName).datagrid('selectRecord',id);
				var record = $('#'+gridName).datagrid('getSelected');
				$.messager.confirm('提示','确定删除该缺陷信息?',function(b){
					if(b){
						var url = '${appPath}/defectCar/delete.action';
						$.post(url,{id:id},function(data){
							if(data.status){
								$('#'+gridName).datagrid('reload');
							}
							$.messager.show({
								msg:data.msg,
								title:'提示'
							});
						},"json");
					}
				});
			}
		}
		
	//查看标签详情
	function user_manage_look_fn(id) {
		$('<div/>').dialog({
			href : '${appPath}/defectCar/viewDefectRecord.action?mainId=' + id,
			width : 600,
			height : 580,
			modal : true,
			title : '查看缺陷详情',
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}

</script>
</body>
</html>