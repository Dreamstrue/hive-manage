<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻资讯统计列表</title>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<style type="text/css">
body{margin:0px;padding:0px;}
.searchDiv{
	overflow: hidden;
	height: 30px; 
	border-bottom: 1px solid #99CCFF;
}
.tt{
	font-size: 12px;
	text-align: right;
}
</style>
</head>

<body>
	<script type="text/javascript">
	
		var infoCateId = ${infoCateId};
   		$(function(){
   			$('#news_statistics_info_datagrid').datagrid({
   				url:'${appPath}/news/statisticsDataGrid.action?infoCateId='+infoCateId,
   				idField:'id',
   				fit:true,
   				fitColumns:true,
   				pagination:true,
   				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
   				rownumbers:true,
   				border:false,
   				animate:false,
   				singleSelect:true,
   				frozenColumns:[[{
   					field:'title',
   					title:'标题',
   					width:250
   				}]],
   				columns:[[{
   					field:'id',
   					title:'ID',
   					width:20,
   					hidden:true
   				},{
   					field:'commentNum',
   					title:'评论次数',
   					align:'center',
   					width:50,
   					formatter:function(value,row,index){
   						if(value>0){
   							var html = "";
	   						html += '<a href="javascript:commentDetail('+ row.id +')">'+value+'</a>';
	   						return html;
   						}else{
   							return value;
   						}
   					}
   				},{
   					field:'shareNum',
   					title:'分享次数',
   					width:50,
   					align:'center',
   					formatter:function(value,row,index){
   						if(value>0){
   							var html = "";
	   						html += '<a href="javascript:shareDetail('+ row.id +')">'+value+'</a>';
	   						return html;
   						}else{
   							return value;
   						}
   					}
   				}]],
   				toolbar:[{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#news_statistics_info_datagrid').datagrid('reload');
   					}
   				}],
   				onLoadSuccess:function(data){
   					var rows = $('#news_statistics_info_datagrid').datagrid('getRows');
   					if(rows.length==0){
   						document.getElementById('iframe').src='';
   					}
   				}
   			});
   			
   			
   		});
   		
   		//评论明细
   		function commentDetail(id){
   			var src = '${appPath}/news/commentDetail.action?id='+id;
   			document.getElementById('iframe').src=src;
   		}
   		
   		
   		//分享明细
   		function shareDetail(id){
   			var src = '${appPath}/news/shareDetail.action?id='+id;
   			document.getElementById('iframe').src=src;
   		}
   		
   		function news_statis_info_search_fn() {
			$('#news_statistics_info_datagrid').datagrid('load', serializeObject($('#news_manage_info_search_form')));
			document.getElementById('iframe').src='';
		}
		function cleanForm_info() {
			$('#news_manage_info_search_form input[class="inval"]').val('');
			$('#news_statistics_info_datagrid').datagrid('load', {});
		}
	</script>
	<div id="cc" class="easyui-layout" style="width:100%;height:100%;">  
	    <div data-options="region:'west',title:'资讯统计'" style="width:480px;height:90%">
			<div class="searchDiv">
				<form action="javascript:void(0);" id="news_manage_info_search_form" style="height:20px;" >
					<table class="form" style="height:20px;">
						<tr>
							<td class="tt" width="40">标题</td>
							<td width="130"><input class="inval" name="title" id = 'inval' style="width:120px;"></td>
							<td class="tt" width="50">创建日期</td>
							<td width="130">
								<input class="inval" type='text' name='createTime' id='createTime' style='width:120px'  class='intxt' readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />
							</td>
							<td>
								<input type="button" class="easyui-linkbutton" value="查询" onclick="news_statis_info_search_fn();">
								<input type="button" class="easyui-linkbutton" value="重置" onclick="cleanForm_info();">
							</td>
						</tr>
					</table>
				</form>
			</div>
			<div style="height:83.7%;width:100%;">
				<table id="news_statistics_info_datagrid"></table>
			</div>	
	    </div>  
	    <div data-options="region:'center',title:'评论或分享统计'" style="height:100%;">
	    	<iframe id="iframe" src="" frameborder="0" width="100%" height="90%" scrolling="no"></iframe>
	    </div>  
	</div>
</body>
</html>
