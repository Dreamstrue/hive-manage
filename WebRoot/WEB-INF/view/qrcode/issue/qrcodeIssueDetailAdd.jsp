<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.hive.common.SystemCommon_Constant"%>
<script type="text/javascript">
	$(function() {
	//批次列表的datagrid初始化
		$('#qrcode_batch_manage_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/qrcodeBatch/datagrid.action?status=<%=SystemCommon_Constant.QRCode_status_2%>&validCount=0',
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
				{ title : '批次编号',field : 'batchNumber', width : "30%",sortable:true, align :'center' },
				{ title : '二维码类别',field : 'qrcodeType', width : "20%", align :'center',
                	formatter: function(value) {
						if(value=='1') {
							return "url";
						}if(value=='2') {
							return "文本";
						}if(value=='3') {
							return "图片";
						} else{
							return "暂无";
						}
					}},
				{ title: '原始数量', field: 'amount', align :'center',width: "25%" },
				{ title: '有效数量', field: 'validAmount', align :'center',width: "25%",sortable:true,
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
				}
			] ],
			rowStyler:function(index,row){    
				if (row.isAllot==2){    
					return 'background-color:pink;color:blue;font-weight:bold;';    
				}    
			},    
			toolbar : '#detail_batch',
			onSelect:function(index,data){
// 				alert(data.linkPerson);
				$('#qrcode_manage_datagrid').datagrid({url:'${pageContext.request.contextPath}/qrcode/datagrid.action?qrcodeStatus=<%=SystemCommon_Constant.QRCode_status_2%>&qrcodeBatchId='+data.id})
			}
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
         //二维码列表
         $('#qrcode_manage_datagrid').datagrid({
// 			url : '${pageContext.request.contextPath}/qrcode/datagrid.action?qrcodeStatus=2',
			fit : true,
			fitColumns : true,
			border : false,
			rownumbers:true,
			pagination : true,
			idField : 'id',
			pageSize : 20,
			sortOrder : 'desc',
			checkOnSelect : true,
			selectOnCheck : true,
			columns : [[
				{ title : 'id',field : 'id', checkbox:true},
				{ title : '二维码编号',field : 'qrcodeNumber', width : "40%",align :'center'},
				{ title : '二维码类别',field : 'qrcodeType', width : "30%", align :'center',
                	formatter: function(value) {
						if(value=='1') {
							return "url";
						}if(value=='2') {
							return "文本";
						}if(value=='3') {
							return "图片";
						} else{
							return "暂无";
						}
					}},
                { title: '二维码状态', field: 'qrcodeStatus', align :'center',width: "30%",
                	formatter: function(value) {
						if(value==0) {
							return "未生效";
						} else if(value==1) {
							return "印刷中";
						} else if(value==2) {
							return "空闲";
						} else if(value==3) {
							return "待发放";
						} else if(value==4) {
							return "已发放";
						} else if(value==5) {
							return "已绑定";
						} else if(value==6) {
							return "作废";
						}else{
							return "未知";
						}
					}
                }
			] ],
			rowStyler:function(index,row){    
				if (row.isAllot==2){    
					return 'background-color:pink;color:blue;font-weight:bold;';    
				}    
			},    
			toolbar : '#detail_qrcode'
		});
		var p = $('#qrcode_manage_datagrid').datagrid('getPager');
		$(p).pagination({ 
       		pageSize: 20,//每页显示的记录条数，默认为10 
        	pageList: [20,50],//可以设置每页记录条数的列表 
        	layout:['list','first','prev','links','next','last','refresh']
        });
	});

function addqrcodeIssueDetail(){
// 	alert('detail');
	//获取选中的发放记录
	var rowIssue = $('#qrcode_issue_manage_datagrid').datagrid('getSelected');
	if(rowIssue!=null&&rowIssue.id!=null&&rowIssue.id!=''){
// 		alert("rowIssue.id="+rowIssue.id);
		$("#pid").val(rowIssue.id);
	}else{
		$.messager.alert("提示","请选择一条发放记录！","info");
		return;
	}
	//获取选中的批次
	var rowBatch = $('#qrcode_batch_manage_datagrid').datagrid('getSelected');
	if(rowBatch!=null&&rowBatch.batchNumber!=null&&rowBatch.batchNumber!=''){
// 		alert("rowBatch.batchNumber="+rowBatch.batchNumber);
		$("#batchNumber_detail").val(rowBatch.batchNumber);
		$("#qrcodeType_detail").val(rowBatch.qrcodeType);
	}else{
		$.messager.alert("提示","请选择一个批次！","info");
		return;
	}
	//获取选中的二维码
	var rows = $('#qrcode_manage_datagrid').datagrid('getSelections');
	var idsArr = [];
	if(rows.length>0){
		for(var i = 0; i<rows.length; i++){
			idsArr.push(rows[i].id);
		}
		var ids = idsArr.join(',');
// 		alert("ids="+ids);
		$("#detailQrcodeIds").val(ids);
	}else{
		$.messager.alert("提示","请至少选择一个二维码！","info");
		return;
	}

	$('#issue_detail_manage_add_form').form('submit',{
	    url:"${pageContext.request.contextPath}/qrcodeIssue/qrcodeIssueDetailAdd.action",
	    success:function(data){
			var r = $.parseJSON(data);
				if (r.status) {
					var pid_ = $("#pid").val();
// 					$('#qrcode_issue_detail_manage_datagrid').datagrid({url:'${pageContext.request.contextPath}/qrcodeIssue/datagridForDetail.action?pid='+pid_})
					$('#qrcode_issue_detail_manage_datagrid').datagrid({url:'${pageContext.request.contextPath}/qrcode/datagridForIssue.action?pid='+pid_})
					$("#add_dialog").dialog('destroy');
				}
				$.messager.alert("提示",r.msg,"info");
	    }
	});
}
function autoSetValues(enterpirseId){
// alert(enterpirseId);
	$.ajax({
		type: 'POST', 
		url: '${pageContext.request.contextPath}/enterpriseManage/findEnterpriseInfoById.action?id='+enterpirseId, 
		success: function(robj){ 
			var r = $.parseJSON(robj);
				$("#linkPerson").val(r.ccontractperson);
				$("#linkPhone").val(r.ccontractpersonphone);
				$("#linkAddress").text(r.ccontractaddress);
		},
		error: function(robj) {
			$.messager.show({
				title : '提示',
				msg : '数据获取失败！'
			});
        }

	});
}
</script>
<div class="easyui-layout" align="center" style="width: 100%;height: 100%;">
	<!-- <div data-options="region:'north',title:'test',collapsible:false" style="height:100px;">
		<form id="issue_detail_manage_add_form"  method="post" enctype="multipart/form-data">
			<input type="text" id="batchNumber_detail" name="batchNumber" />
			<input type="text" id="pid" name="pid"/>
			<input type="text" id="qrcodeType_detail" name="qrcodeType"/>
			<input type="text" id="valid" name="valid" value="1"/>
			<input type="text" id="detailQrcodeIds" name="detailQrcodeIds"/>
		</form>
	</div> -->
	<div data-options="region:'east',title:'二维码列表',collapsible:false" style="width:60%;">
		<table id="qrcode_manage_datagrid"></table>
	</div>
    <div data-options="region:'west',title:'二维码批次',collapsible:false" style="width:40%;">
    	<table id="qrcode_batch_manage_datagrid"></table>
    	
    	<form id="issue_detail_manage_add_form"  method="post" enctype="multipart/form-data">
			<input type="hidden" id="batchNumber_detail" name="batchNumber" />
			<input type="hidden" id="pid" name="pid"/>
			<input type="hidden" id="qrcodeType_detail" name="qrcodeType"/>
			<input type="hidden" id="valid" name="valid" value="1"/>
			<input type="hidden" id="detailQrcodeIds" name="detailQrcodeIds"/>
		</form>
    </div>

</div>