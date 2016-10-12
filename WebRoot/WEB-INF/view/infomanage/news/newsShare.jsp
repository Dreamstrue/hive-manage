<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息公告管理</title>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<style type="text/css">
body{margin:0px;padding:0px;}
.searchDiv{
	overflow: hidden;
	height: 32px; 
}
.tt{
	font-size: 12px;
	text-align: right;
}
</style>
</head>

<body>
	<script type="text/javascript">
		var newsId = ${newsId};
   		$(function(){
   			$('#news_share_statistics_datagrid').datagrid({
   				url:'${appPath}/news/shareDataGrid.action?newsId='+newsId,
   				idField:'id',
   				fit:true,
   				fitColumns:true,
   				pagination:true,
   				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
   				rownumbers:true,
   				border:false,
   				animate:false,
   				frozenColumns:[[]],
   				columns:[[{
   					field:'id',
   					title:'ID',
   					width:50,
   					hidden:true
   				},{
   					field:'userName',
   					title:'分享人',
   					width:80
   				},{
   					field:'gainDate',
   					title:'分享时间',
   					width:120,
   					align:'center',
   					formatter:function(value,row,index){
   						return formatDate(value);
   					}
   				}]],
   				toolbar:[{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#news_share_statistics_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		
   		
   		
   		function news_share_manage_search_fn() {
			$('#news_share_statistics_datagrid').datagrid('load', serializeObject($('#news_share_manage_search_form')));
		}
		function share_cleanForm() {
			$('#news_share_manage_search_form input[class="inval"]').val('');
			$('#news_share_statistics_datagrid').datagrid('load', {});
		}
	</script>
	<div class="searchDiv">
		<form action="javascript:void(0);" id="news_share_manage_search_form" >
			<table class="form" style="height:22px;">
				<tr>
					<td class="tt" >分享日期</td>
					<td width="160">
						<input class="inval" type='text' name='createTime' id='createTime' style='width:150px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />
					</td>
					<td>
						<input type="button" class="easyui-linkbutton" value="查询" onclick="news_share_manage_search_fn();">
						<input type="button" class="easyui-linkbutton" value="重置" onclick="share_cleanForm();">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:90%;width:100%;">
		<table id="news_share_statistics_datagrid"></table>
	</div>
</body>
</html>
