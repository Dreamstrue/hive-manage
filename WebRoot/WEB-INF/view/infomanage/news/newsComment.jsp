<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻资讯评论列表</title>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<style type="text/css">
body{margin:0px;padding:0px;}
.searchDiv_1{
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
   			$('#news_comment_datagrid').datagrid({
   				url:'${appPath}/news/commentDataGrid.action?newsId='+newsId,
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
   					title:'评论人',
   					align:'center',
   					width:80
   				},{
   					field:'commentDate',
   					title:'评论时间',
   					width:150,
   					align:'center',
   					formatter:function(value,row,index){
   						return formatDate(value);
   					}
   				},{
   					field:'content',
   					title:'评论内容',
   					align:'center',
   					rowspan:2,
   					width:490,
   					formatter:function(value,row,index){
   						/* 评论的内容可能比较长，这里按自定义的长度30进行截取 */
   						var html ='';
   						var size = 35;
   						var n = (value.length)/size;
   						var totalSize = value.length;
   						if(value.length>size){
   							for(var i=1;i<=n+1;i++){
	   							html += value.substring(size*(i-1),size*i);
	   							if(size*i>=totalSize){
	   								break;
	   							}
	   							html += '</br>';
   							}
   						}else{
   							html += value;
   						}
   						return html;
   					}
   				}]],
   				toolbar:[{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#news_comment_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		function news_comment_search_fn() {
			$('#news_comment_datagrid').datagrid('load', serializeObject($('#news_comment_search_form')));
		}
		function cleanForm() {
			$('#news_comment_search_form input[class="inval"]').val('');
			$('#news_comment_datagrid').datagrid('load', {});
		}
	</script>
	<div class="searchDiv_1">
		<form action="javascript:void(0);" id="news_comment_search_form" style="height:20px;">
			<table class="form" style="height:22px;">
				<tr>
					<td class="tt" width="50">开始日期</td>
					<td width="130">
						<input class="inval" type='text' name='beginDate' id='beginDate' style='width:120px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />
					</td>
					<td class="tt" width="50">结束日期</td>
					<td width="130">
						<input class="inval" type='text' name='endDate' id='endDate' style='width:120px'  class='intxt' readonly="readonly" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}'})"  />
					</td>
					<td>
						<input type="button" class="easyui-linkbutton" value="查询" onclick="news_comment_search_fn()">
						<input type="button" class="easyui-linkbutton" value="重置" onclick="cleanForm()">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:90%;width:100%;">
		<table id="news_comment_datagrid"></table>
	</div>
</body>
</html>
