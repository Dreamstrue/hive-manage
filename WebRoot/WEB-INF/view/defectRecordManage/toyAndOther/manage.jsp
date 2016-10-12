<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>玩具消费品管理</title>
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
				<label style="font-size: 12px;">报告人姓名:</label>
				<input name="reportusername" style="width: 120px;" />
				<label style="font-size: 12px;">报告人电话:</label>
				<input name="reportuserphone" style="width: 120px;" />
				<label style="font-size: 12px;">产品名称:</label>
				<input name="prodName" style="width: 120px;" />
				<label style="font-size: 12px;">报告类别：</label>
			      <select class="easyui-combobox" id="peporttype" name="peporttype" data-options="editable:false,panelHeight:'auto'" style="width:120px">
											<option value="" ></option>
											<option value="1">玩具缺陷</option>
											<option value="3">其他消费品</option>
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

		obj = {
				search : function(){
					$('#'+gridName).datagrid('load',{
						peporttype : $('input[name="peporttype"]').val(),
						reportusername : $('input[name="reportusername"]').val(),
						reportuserphone : $('input[name="reportuserphone"]').val(),
						prodName : $('input[name="prodName"]').val(),
					});
				}
		}
		
		$('#'+gridName).datagrid({
			url : '${appPath}/defectOfToyAndOthers/queryList.action',
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
				{ field : 'id', title : 'id' ,align :'center', hidden:true},
				{ field : 'peporttype', title : '报告类别', width : 50, align :'center',
					formatter: function(value) {
					if(value=='1') {
							return "儿童玩具类";
						} else if(value=='3') {
							return "其他消费品";
						}
					}
				},
				{ field : 'reportusername', title : '报告人姓名 ', width : 50 ,align :'center'},
				{ field : 'reportuserphone', title : '报告人电话', width : 50, align : 'center'},
				{ field : 'reportuseremail', title : '报告人邮箱', width : 50, align :'center'},
				{ field : 'prodName', title : '产品名称', width : 50, align : 'center'},
				{ field : 'prodModel', title : '产品型号', width : 50, align : 'center'},
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
							html += '&nbsp;<img onclick="user_manage_look_fn(\'' + row.id + '\');" src="${appPath}/resources/images/extjs_icons/table/table.png" title="查看标签详情"/>';
							html += '&nbsp;<img title="删除" onclick="prize_manage_delete_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png"/>';
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

	 
	//删除缺陷信息
		function prize_manage_delete_fn(id){
			if(id!=undefined){
				$('#'+gridName).datagrid('selectRecord',id);
				var record = $('#'+gridName).datagrid('getSelected');
				$.messager.confirm('提示','确定删除该缺陷信息?',function(b){
					if(b){
						var url = '${appPath}/defectOfToyAndOthers/delete.action';
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
			href : '${appPath}/defectOfToyAndOthers/viewDefectRecord.action?defectId=' + id,
			width : 600,
			height : 380,
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