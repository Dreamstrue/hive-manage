<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.hive.common.SystemCommon_Constant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<%
	String snBatchId = request.getParameter("snBatchId");
 %>

<script type="text/javascript">
	$(function() {  
		$('#tagList_manage_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/tagSNBatchController/getSNGridBySNBatch.action?snBatchId=<%=snBatchId%>',
			fit : true,
			fitColumns : true,
			border : false,
			rownumbers:true,
			pagination : true,
			idField : 'id',
			pageSize : 20,
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect:true,
			columns : [[
				{ title : '标签编号',field : 'bqbh',align :'center', width : "25%" },
				{ title: 'SN', field: 'sn', align :'center',width: "28%" },
// 				{ title: '父节点SN', field: 'psn', align :'center',width: "28%" },
                { title: '编号', field: 'sequenceNum', align :'center', width: "10%",sortable : true }
<%--                { title: '分配情况', field: 'isAllot', align :'center',width: "12%",--%>
<%--                	formatter: function(value) {--%>
<%--						if(value==1) {--%>
<%--							return "未分配";--%>
<%--						} else if(value==2) {--%>
<%--							return "已分配";--%>
<%--						}--%>
<%--					}--%>
<%--                },--%>
                <%-- { field : 'totalCount', title : '总数量', align :'center', width : "10%"}, --%>
<%--				{ field : 'alloter', title : '分配人', width : "15%", align :'center'},--%>
<%--				{ field : 'allotTime', title : '分配时间', width : "25%", align :'center', sortable : true,--%>
<%--					formatter:function(value,row,index){ --%>
<%--	                	if(value!=null) {--%>
<%--		                	var unixTimestamp = new Date(value);  --%>
<%--       		          	return unixTimestamp.toLocaleString(); --%>
<%--							return unixTimestamp.Format("yyyy-MM-dd hh:mm:ss"); --%>
<%--	                 	}   --%>
<%--	                }--%>
<%--				}--%>
			] ],
			rowStyler:function(index,row){    
				if (row.isAllot==2){    
					return 'background-color:pink;color:blue;font-weight:bold;';    
				}    
			}
		});
		var p = $('#tagList_manage_datagrid').datagrid('getPager');
		$(p).pagination({ 
       		pageSize: 20,//每页显示的记录条数，默认为10 
        	pageList: [20,50],//可以设置每页记录条数的列表 
        	layout:['list','first','prev','links','next','last','refresh']
        });   
	});
	
</script>
<div class="easyui-layout" data-options="fit : true,border : false">

	<div data-options="region:'center',border:false">
		<%-- <input type="hidden" id="dealerApplyId" value="${dealerApplyId}" > --%>
		<table id="tagList_manage_datagrid"></table>
	</div>
</div>
