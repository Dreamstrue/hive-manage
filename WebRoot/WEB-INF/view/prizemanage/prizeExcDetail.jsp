<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>奖品库存查询</title>
<jsp:include page="../common/inc.jsp"></jsp:include>
<style type="text/css">
body{margin:0px;padding:0px;}
.searchDiv{
	overflow: hidden;
	height: 60px; 
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
	
		var prizeId = ${prizeId};
   		$(function(){
   			$('#prize_exc_detail_datagrid').datagrid({
   				url:'${appPath}/prizeInfo/excDetailDataGrid.action?prizeId='+prizeId,
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
   					field:'prizeName',
   					title:'奖品名称',
   					width:130,
   					align:'center'
   				}]],
   				columns:[[{
   					field:'id',
   					title:'ID',
   					width:30,
   					hidden:true
   				},{
   					field:'prizeNum',
   					title:'数量',
   					width:30,
   					align:'center'
   				},{
   					field:'userName',
   					title:'兑换人',
   					width:60,
   					align:'center'
   				},{
   					field:'applyTime',
   					title:'兑换时间',
   					width:50,
   					align:'center'
   				}]],
   				toolbar:[{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#prize_exc_detail_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		
   		
   		
   		
   		function prize_exc_manage_search_fn() {
			$('#prize_exc_detail_datagrid').datagrid('load', serializeObject($('#prize_exc_manage_search_form')));
		}
		function exc_cleanForm() {
			$('#prize_exc_manage_search_form input[class="inval"]').val('');
			$('#prize_exc_detail_datagrid').datagrid('load', {});
		}
	</script>
	<div class="searchDiv">
		<form action="javascript:void(0);" id="prize_exc_manage_search_form" >
			<table class="form" style="height:60px;">
				<tr>
					<td class="tt" width="80">有效期起</td>
					<td width="160">
						<input name='beginDate' id='beginDate' style='width:150px'  class="inval"  readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"  />
					</td>
					<td class="tt" width="80">有效期止</td>
					<td width="160">
						<input name='endDate' id='endDate' style='width:150px'  class="inval"  readonly="readonly" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginDate\')}'})"  />
					</td>
					<td>
						<input type="button" class="easyui-linkbutton" value="查询" onclick="prize_exc_manage_search_fn();">
						<input type="button" class="easyui-linkbutton" value="重置" onclick="exc_cleanForm();">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:80%;width:100%;">
		<table id="prize_exc_detail_datagrid"></table>
	</div>
</body>
</html>
