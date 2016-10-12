<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.hive.common.SystemCommon_Constant"%>
<% String status_ =  SystemCommon_Constant.QRCode_status_3;%>
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
		$('#industryEntityText').textbox('textbox').focus(function() {
			queryAllIndustryEntity('entityId','industryEntityText');
    	}); 
		$('#qrcode_issue_manage_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/qrcodeIssue/datagrid.action',
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
				{ title : '发放编号',field : 'number', width : "8%",sortable:true, align :'center' },
				{ title : '实体名称',field : 'entityName', width : "15%", align :'center' },
				{ title: '联系人', field: 'linkPerson', align :'center',width: "5%" },
                { title: '联系电话', field: 'linkPhone', align :'center',width: "8%" },
                { title: '二维码类型', field: 'qrcodeTypeStr', align :'center',width: "8%" },
                { title: '二维码内容', field: 'qrcodeContent', align :'center',width: "8%" },
                { title: '发放状态', field: 'status', align :'center',width: "8%",
                	formatter: function(value) {
						if(value==0) {
							return "删除";
						} else if(value==1) {
							return "待发放";
						} else if(value==2) {
							return "已发放";
						}
					},
					styler: function (value, row, index) { 
						
						if(value==1) {
			        		return 'background-color:#FF3333;color:white;font-weight:bold;';
						}
						if(value==2) {
							return 'background-color:#00DD00;color:white;font-weight:bold;';
						}
			    	}
                },
                { title: '联系地址', field: 'linkAddress', align :'left',width: "20%" },
                { title: '创建人', field: 'creater', align :'center',width: "6%" },
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
					title : '操作',
					width : "8%",
					align :'center',
					formatter : function(value, row, index) {
						if (row.id == '0') {
							return '系统用户';
						} else {
							var html = "";
							if(row.status=='1'){
								html += '&nbsp;<a style="text-decoration:none;" href="javascript:issue_qrcode(\''+row.id+'\');">发放</a>';
							}
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
			toolbar : '#toolbar',
			onSelect:function(index,data){
// 				alert(data.linkPerson);
// 20160317 yyf 修改  不再加载发放明细直接加载发放二维码信息
// 				$('#qrcode_issue_detail_manage_datagrid').datagrid({url:'${pageContext.request.contextPath}/qrcodeIssue/datagridForDetail.action?pid='+data.id});
				$('#qrcode_issue_detail_manage_datagrid').datagrid({url:'${pageContext.request.contextPath}/qrcode/datagridForIssue.action?pid='+data.id});
			}
		});
		var p = $('#qrcode_issue_manage_datagrid').datagrid('getPager');
		$(p).pagination({ 
       		pageSize: 20,//每页显示的记录条数，默认为10 
        	pageList: [20,50],//可以设置每页记录条数的列表 
        	layout:['list','first','prev','links','next','last','refresh']
        });   
		
		 $("#cleanBtn").click(function () {
			 $("#searchForm").form("clear");
			  $('#qrcode_issue_manage_datagrid').datagrid('load', {});
         });
	    //发放明细datagrid
	    $('#qrcode_issue_detail_manage_datagrid').datagrid({
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
				{ title : '批次编号',field : 'batchNumber', width : "8%",sortable:true, align :'center',
					formatter:function(value,row,index){ 
		                	if(row.qrcodeBatchVo!=null) {
			                 	return row.qrcodeBatchVo.batchNumber;
		                 	}   
		                }
				},
				{ title : '二维码编号',field : 'qrcodeNumber', width : "15%",align :'center'},
				{ title : '二维码类别',field : 'qrcodeType', width : "10%", align :'center',
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
                { title: '二维码状态', field: 'qrcodeStatus', align :'center',width: "8%",
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
                },
                  { title: '创建人', field: 'createrName', align :'center',width: "6%",
					formatter:function(value,row,index){ 
		                	if(row.qrcodeBatchVo!=null) {
			                 	return row.qrcodeBatchVo.createrName;
		                 	}   
		                }
                 },
				{ field : 'createTime', title : '创建时间', align :'center', width: "12%",
                	formatter:function(value,row,index){ 
	                	if(row.qrcodeBatchVo!=null) {
		                	var unixTimestamp = new Date(row.qrcodeBatchVo.createTime);  
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
							//20160317 yyf 修改，注释掉
// 								html += '<a style="text-decoration:none;" href="javascript:user_manage_look_fn(\'' + row.id + '\');">查看</a>';
								if(row.qrcodeStatus == <%=status_%>){
								    html += '&nbsp;<a style="text-decoration:none;" href="javascript:deleteIssueDetail(\'' + row.id + '\');">删除</a>';
								}
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
			toolbar : '#toolbar_detail'
		});
		var p_detail = $('#qrcode_issue_detail_manage_datagrid').datagrid('getPager');
		$(p_detail).pagination({ 
       		pageSize: 10,//每页显示的记录条数，默认为10 
        	pageList: [10,20],//可以设置每页记录条数的列表 
        	layout:['list','first','prev','links','next','last','refresh']
        }); 
	});
	
	//查看批次信息
	function user_manage_look_fn(id) {
	
		var href = '${pageContext.request.contextPath}/qrcodeIssue/lookUpIssueDetailqrcodeList.action?issueDetailId='+id;
// 		var href = '${pageContext.request.contextPath}/qrcode/datagrid.action?issueDetailId='+id;
		$('#openXXXIframe')[0].src=href;
		$('#openRoleDiv').dialog('open');
	}
	
	function sn_manage_search_fn(param) {
		if(param=='detail'){
			$('#qrcode_issue_detail_manage_datagrid').datagrid('load', serializeObject($('#searchForm_detail')));
		}else{
			$('#qrcode_issue_manage_datagrid').datagrid('load', serializeObject($('#searchForm')));
		}
	}

	function qrcode_manage_add_fn(param){
		var  url_ = '${pageContext.request.contextPath}/qrcodeIssue/toQrcodeIssueAdd.action';
		var  title_ = '添加发放记录';
		var width_ = 450;
		var height_= 370;
		if(param=='detail'){
			var rowIssue = $('#qrcode_issue_manage_datagrid').datagrid('getSelected');
			if(rowIssue==null){
				$.messager.alert("提示","请先选择一条发放记录！","info");
				return;
			}
			if(rowIssue.status==2){
				$.messager.alert("提示","此记录已发放，请选中未发放的记录！","info");
				return;
			}
			url_ = '${pageContext.request.contextPath}/qrcodeIssue/toQrcodeIssueDetailAdd.action';
			title_ = '添加明细记录';
			width_=1000;
			height_=500;
		}
		$('<div/>').dialog({
			id : 'add_dialog',
			href : url_,
			width : width_,
			height : height_,
			modal : true,
			title : title_,
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
				if(param=='detail'){
					addqrcodeIssueDetail();
				}else{
					addqrcodeIssue();
				}
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
	//执行发放操作 yyf add
	function issue_qrcode(id){
		$.messager.confirm('请谨慎操作', '您确定要发放吗？', function(r) {
			if (r) {
					$.ajax({
						type: 'POST', 
						url: '${pageContext.request.contextPath}/qrcodeIssue/issueQrcode.action?id='+id, 
						success: function(robj){ 
							var r = $.parseJSON(robj);
							if(r.status){
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
								//刷新datagrid
								$("#qrcode_issue_manage_datagrid").datagrid("load");
								$('#qrcode_issue_detail_manage_datagrid').datagrid({url:'${pageContext.request.contextPath}/qrcode/datagridForIssue.action?pid='+id});
							}else{
								$.messager.alert("提示",r.msg,"info");
							}
						}
					});
				}
			});

	}
	//删除发放明细 yyf add
	function deleteIssueDetail(id){
		$.messager.confirm('请谨慎操作', '您确定要删除此二维码吗？', function(r) {
			if (r) {
					$.ajax({
						type: 'POST', 
						url: '${pageContext.request.contextPath}/qrcode/updateQrcode.action?id='+id,//这里的删除就相当于是更新二维码的数据
						success: function(robj){ 
							var r = $.parseJSON(robj);
							if(r.status){
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
								//刷新datagrid
								$("#qrcode_issue_detail_manage_datagrid").datagrid("load");
							}else{
								$.messager.alert("提示",r.msg,"info");
							}
						}
					});
				}
			});
	}
	//手工录入 yyf add
	function manual_manage_add_fn(){
	    var rowIssue = $('#qrcode_issue_manage_datagrid').datagrid('getSelected');
		if(rowIssue==null){
			$.messager.alert("提示","请先选择一条发放记录！","info");
			return;
		}
		if(rowIssue.status==2){
			$.messager.alert("提示","此记录已发放，请选中未发放的记录！","info");
			return;
		}
		var  url_ = '${pageContext.request.contextPath}/qrcodeIssue/toQrcodeIssueManual.action?issueId='+rowIssue.id;
		var  title_ = '手工录入发放明细';
		var width_ = 380;
		var height_= 290;
		$('<div/>').dialog({
			id : 'manual_dialog',
			href : url_,
			width : width_,
			height : height_,
			modal : true,
			title : title_,
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					addqrcodeIssueByManual();
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
</script>


<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="title:'二维码发放列表',region:'center',border:false">
		<table id="qrcode_issue_manage_datagrid"></table>
	</div>
		<div data-options="title:'二维码发放明细',region:'south',border:false" style="width:100%;height:50%;">
		<table id="qrcode_issue_detail_manage_datagrid"></table>
	</div>
	
	<div id="toolbar" class="datagrid-toolbar"  style="padding-top: 3px">
		<table style="width: 100%">
			<tbody>
			<tr>
				<td width="50%">
					<form action="javascript:void(0);" id="searchForm" style="margin-bottom: 0px">
			            <input class="easyui-textbox" name="number" data-options="prompt:'发放编号'" style="width: 100px;" />
			            <span>-</span>
           				<input id="entityId" name="entityId" type="hidden"/>
           				<input class="easyui-textbox" id="industryEntityText" name="industryEntityText" data-options="prompt:'点击选择实体名称'" style="width: 150px;" />
<!-- 						<input id="industryEntityText" onclick="queryAllIndustryEntity('entityId','industryEntityText')" style="width: 180px;" /> -->
<!-- 			            <input name="entityId" class="easyui-combobox" data-options="editable:false, -->
<!-- 						    valueField: 'id', -->
<!-- 						    textField: 'entityName', -->
<!-- 						    url: '${pageContext.request.contextPath}/industryEntityManage/allindustryEntityInfo.action', -->
<!-- 						    prompt:'请选择实体名称'"> -->
			            <span>-</span>
			            <select class="easyui-combobox" name="status" data-options="prompt:'发放状态',editable:false,panelHeight:'auto'" style="width:90px;">
							<option ></option>
							<option value=1>待发放</option>
							<option value=2>已发放</option>
						</select>
			            <a class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-search'" onclick="sn_manage_search_fn()" >查询</a>
			            <a id="cleanBtn" class="easyui-linkbutton" data-options="plain: true,iconCls:'icon-clear'" >重置</a>
				    </form>
				</td>
				<td align="right" width="40%">
					<a href="javascript:void(0);" class="easyui-linkbutton l-btn l-btn-plain" data-options="iconCls:'icon-note_add',plain: true" title="添加发放记录" onclick="qrcode_manage_add_fn();">添加发放记录</a>
					<div class="datagrid-btn-separator"></div>
				</td>
			</tr>
		</tbody></table>
	</div>
		<div id="toolbar_detail" class="datagrid-toolbar"  style="padding-top: 3px">
		<table style="width: 100%">
			<tbody>
			<tr>
				<td width="50%">
					<!-- <form action="javascript:void(0);" id="searchForm_detail" style="margin-bottom: 0px">
			            <input class="easyui-textbox" id="batchNumber" name="batchNumber" data-options="prompt:'批次编号'" style="width: 100px;" />
			            <span>-</span>
						<input id="qrcodeType" name="qrcodeType" class="easyui-combobox" style="width:150px;"
							data-options="
							valueField: 'label',
							textField: 'value',
							data: [{label: '001',value: '加油站'}],
							prompt:'请选择二维码类别'"/>
			            
			            <a class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-search'" onclick="sn_manage_search_fn('detail')" >查询</a>
			            <a id="cleanBtn" class="easyui-linkbutton" data-options="plain: true,iconCls:'icon-clear'" >重置</a>
				    </form> -->
				</td>
				<td align="right" width="40%">
				    <a href="javascript:void(0);" class="easyui-linkbutton l-btn l-btn-plain" data-options="iconCls:'icon-note_add',plain: true" title="添加发放明细" onclick="manual_manage_add_fn();">手工录入</a>
					<a href="javascript:void(0);" class="easyui-linkbutton l-btn l-btn-plain" data-options="iconCls:'icon-note_add',plain: true" title="添加发放明细" onclick="qrcode_manage_add_fn('detail');">添加发放明细</a>
					<div class="datagrid-btn-separator"></div>
				</td>
			</tr>
		</tbody></table>
	</div>
</div>

<div id="openRoleDiv" class="easyui-window" closed="true" modal="true" minimizable="false" title="查看二维码列表 " style="width:600px;height:380px;">
    <iframe scrolling="auto" id='openXXXIframe' frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
</div>