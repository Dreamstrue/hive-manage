<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.hive.common.SystemCommon_Constant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
		String seqTypeStr = SystemCommon_Constant.SEQUENCE_EWMPC;
 %>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<style>
table {
	border-collapse: separate;
	border-spacing: 2px;
	border-color: gray;
}
</style>
<script type="text/javascript">
	$(function() {
		$('#qrcode_batch_manage_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/qrcodeBatch/datagrid.action',
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
				{ title : '批次编号',field : 'batchNumber', width : "8%",sortable:true, align :'center' },
				{ title: '原始数量', field: 'amount', align :'center',width: "10%" },
				{ title: '有效数量', field: 'validAmount', align :'center',width: "10%",sortable:true,
					styler: function (value, row, index) { 
						if(row.validAmount > (row.amount)*0.7) {
			        		return 'background-color:#00DD00;color:white;font-weight:bold;';
						}
						if(row.validAmount >= (row.amount)*0.4) {
								return 'background-color:#FFFF33;color:red;font-weight:bold;';
						}
						if(row.validAmount < (row.amount)*0.4) {
			        		return 'background-color:#FF3333;color:white;font-weight:bold;';
						}
			    	}
				},
				
                { title: '批次状态', field: 'status', align :'center',width: "8%",
                	formatter: function(value) {
						if(value==0) {
							return "未生效";
						} else if(value==1) {
							return "印刷中";
						} else if(value==2) {
							return "占用";
						}
					}
                },
                { title: '创建人', field: 'createrName', align :'center',width: "6%" },
				{ field : 'createTime', title : '创建时间', align :'center', width: "12%",
                	formatter:function(value,row,index){ 
	                	if(value!=null) {
		                	var unixTimestamp = new Date(value);  
		                 	return unixTimestamp.Format("yyyy-MM-dd hh:mm:ss");
	                 	}   
	                }
				}
                <c:if test="${(sessionScope.isAdmin == '1') || requestScope.canPutout || requestScope.canPrinted || requestScope.canAudit}">
				, {
					field : 'action',
					title : '动作',
					width : "8%",
					align :'center',
					formatter : function(value, row, index) {
						if (row.id == '0') {
							return '系统用户';
						} else {
							var html = "";
							html += '&nbsp;<img onclick="user_manage_look_fn(\'' + row.id + '\',\'' + row.qrcodeType + '\');" src="${appPath}/resources/images/extjs_icons/text_list_numbers.png" title="查看二维码列表"/>';
							html += '&nbsp<img onclick="user_manage_updateStatus_fn(\'' + row.id + '\');" src="${appPath}/resources/images/extjs_icons/table/table_relationship.png" title="更改印刷状态"/>';	
							html += '&nbsp;<img onclick="downLoadExcel(\'' + row.id + '\',\'' + row.batchNumber + '\');" src="${appPath}/resources/images/extjs_icons/table/table_save.png" title="导出EXCEL(导出所有空闲的二维码)"/>';
							return html;
						}
					}
				} 
				</c:if>
			] ],
			rowStyler:function(index,row){    
				if (row.isAllot==2){    
					return 'background-color:pink;color:blue;font-weight:bold;';    
				}    
			},    
			toolbar : '#toolbar'
		});
		var p = $('#qrcode_batch_manage_datagrid').datagrid('getPager');
		$(p).pagination({ 
       		pageSize: 20,//每页显示的记录条数，默认为10 
        	pageList: [20,50],//可以设置每页记录条数的列表 
        	layout:['list','first','prev','links','next','last','refresh']
        });   
		
		 $("#cleanBtn").click(function () {
			 $("#searchForm").form("clear");
			  $('#qrcode_batch_manage_datagrid').datagrid('load', {});
         });
	});
	
	//查看批次信息
	function user_manage_look_fn(id,qrcodeType) {
		var href = '${pageContext.request.contextPath}/qrcodeBatch/lookUpList.action?batchId='+id+'&qrcodeType='+qrcodeType;
		$('#openXXXIframe')[0].src=href;
		$('#openRoleDiv').dialog('open');
	}
	//导出
	function downLoadExcel(batchId,batchNum) {
		$.messager.confirm("操作提示", "您确定要导出第"+batchNum+"批次的二维码清单吗？", function (data) {
			if (data) {
				window.location.href = "${pageContext.request.contextPath}/qrcodeBatch/downLoadExcel.action?batchId="+batchId;
			} else {
	              	$(this).closed;
	          	}
		});
	}
	//采用jquery easyui loading css效果 
	function ajaxLoading(){ 
		$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body"); 
	    $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候……").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
	} 
	function ajaxLoadEnd(){ 
	     $(".datagrid-mask").remove(); 
	     $(".datagrid-mask-msg").remove();             
	} 
	
	function showProcess(isShow, title, msg) {
		if (!isShow) {
			$.messager.progress('close');
			return;
		}
		var win = $.messager.progress({
			title: title,
			msg: msg
		});
	}
	
	function sn_manage_search_fn() {
		$('#qrcode_batch_manage_datagrid').datagrid('load', serializeObject($('#searchForm')));
	}

	function qrcode_manage_add_fn(){
		$('<div/>').dialog({
			id : 'add_dialog',
			href : '${pageContext.request.contextPath}/qrcodeBatch/toQrcodeBatchAdd.action?seqType=<%=seqTypeStr%>',
			width : 340,
			height : 205,
			modal : true,
			title : '创建批次',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					addqrcodeBatch();
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
		function user_manage_updateStatus_fn(batchId) {
		$('<div/>').dialog({
			id : 'update_dialog',
			href : '${pageContext.request.contextPath}/qrcodeBatch/toEditStatus.action?id=' + batchId ,
			width : 200,
			height : 120,
			modal : true,
			title : '修改印刷状态',
			buttons : [ {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					$('#user_manage_add_form').form('submit', {
						url : '${appPath}/qrcodeBatch/editStatus.action',
						onSubmit: function(){
					    	$.messager.progress({title:'温馨提示',msg:'更新数据生成中...'});
							return $(this).form('validate');
					    },
						success : function(result) {
							$.messager.progress('close');
							var r = $.parseJSON(result);
							if (r.status) {
								$("#update_dialog").dialog('destroy');
								$('#qrcode_batch_manage_datagrid').datagrid('load');
							}
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
						}
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
</script>


<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'center',border:false">
		<table id="qrcode_batch_manage_datagrid"></table>
	</div>
	
	<div id="toolbar" class="datagrid-toolbar"  style="padding-top: 3px">
		<table style="width: 100%">
			<tbody>
			<tr>
				<td width="50%">
					<form action="javascript:void(0);" id="searchForm" style="margin-bottom: 0px">
			            <input class="easyui-textbox" id="batchNumber" name="batchNumber" data-options="prompt:'批次编号'" style="width: 100px;" />
			            <span>-</span>
			            <select class="easyui-combobox" name="status" data-options="prompt:'批次状态',editable:false,panelHeight:'auto'" style="width:90px;">
							<option ></option>
							<option value=0>未生效</option>
							<!--<option value=1>印刷中</option>  -->
							<option value=2>占用</option>
						</select>
			            <span>-</span>
			            <a class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-search'" onclick="sn_manage_search_fn()" >查询</a>
			            <a id="cleanBtn" class="easyui-linkbutton" data-options="plain: true,iconCls:'icon-clear'" >重置</a>
				    </form>
				</td>
				<td align="right" width="40%">
					<a href="javascript:void(0);" class="easyui-linkbutton l-btn l-btn-plain" data-options="iconCls:'icon-note_add',plain: true" title="创建二维码批次" onclick="qrcode_manage_add_fn();">创建二维码批次</a>
					<div class="datagrid-btn-separator"></div>
<%--					<a href="javascript:void(0);" class="easyui-linkbutton l-btn l-btn-plain" data-options="iconCls:'icon-table_go',plain:true" onclick="downLoadExcel();">导出</a>--%>
				</td>
			</tr>
		</tbody></table>
	</div>
</div>

<div id="openRoleDiv" class="easyui-window" closed="true" modal="true" minimizable="false" title="查看批次详细 " style="width:600px;height:380px;">
    <iframe scrolling="auto" id='openXXXIframe' frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
</div>