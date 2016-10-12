<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.hive.common.SystemCommon_Constant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<%
	String issueDetailId = request.getParameter("issueDetailId");
 %>

<script type="text/javascript">
	$(function() {  
		$('#tagList_manage_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/qrcode/datagrid.action?issueDetailId=<%=issueDetailId%>',
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
				{ title: '二维码编号',field : 'qrcodeNumber',align :'center', width : "25%"},
				{ title: '二维码类型', field: 'qrcodeType', align :'center',width: "28%",
					formatter:function(value,row,index){ 
			                if(value=='1') {
								return "url";
							}if(value=='2') {
								return "文本";
							}if(value=='3') {
								return "图片";
							} else{
								return "暂无";
							}  
		                }
				},
				{ title: '二维码状态', field: 'qrcodeStatus', align :'center',width: "28%",
					formatter:function(value,row,index){ 
		                	if(value!=null) {
            					if(value=='0') {
									return "未生效";
								}else if(value=='1'){
									return "印刷中";
								}else if(value=='2'){
									return "空闲";
								}else if(value=='3'){
									return "待发放";
								}else if(value=='4'){
									return "已发放";
								}else if(value=='5'){
									return "已绑定";
								}else if(value=='6'){
									return "作废";
								} else{
									return "未知";
								}
		                 	}   
		                }
				}
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
