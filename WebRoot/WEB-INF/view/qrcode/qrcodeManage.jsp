<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/inc.jsp"></jsp:include>
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
		$('#qrcode_manage_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/qrcode/datagrid.action',
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
				{ title : '二维码编号',field : 'qrcodeNumber', width : "12%",align :'center'},
                { title: '二维码状态', field: 'qrcodeStatus', align :'center',width: "5%",
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
					},
					styler: function (value, row, index) { 
						if(value==2) {
			        		return 'background-color:blue;color:white;font-weight:bold;';
						}
						if(value==3) {
			        		return 'background-color:#FF3333;color:white;font-weight:bold;';
						}
						if(value==4) {
							return 'background-color:#00DD00;color:white;font-weight:bold;';
						}
			    	}
                },
                { title : '二维码类别',field : 'qrcodeType', width : "5%", align :'center',
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
                  { title: '领取实体', field: 'entityName', align :'center',width: "15%",
					formatter:function(value,row,index){ 
		                	if(row.industryEntity!=null) {
			                 	return row.industryEntity.entityName;
		                 	}else{
		                 		return "暂无";
		                 	}
		                }
                 },
                 { title : '二维码地址',field : 'qrcodeValue', width : "28%",align :'center'},
                  { title: '创建人', field: 'createrName', align :'center',width: "5%",
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
				},{
					field : 'action',
					title : '操作',
					width : "8%",
					align:'center',
					formatter : function(value, row, index) {
						var	html ="<img onclick=\"view_qrcode_fn('"+row.id+"')\" title='查看二维码' src='${pageContext.request.contextPath}/resources/images/extjs_icons/barcode.png'/>";
						if(row.qrcodeStatus>2&&row.qrcodeStatus<6){
							html +="&nbsp;<img onclick=\"edit_qrcode_content('"+row.id+"')\" title='修改二维码内容' src='${pageContext.request.contextPath}/resources/images/extjs_icons/book_edit.png'/>";
						}
						return html;
					}
				}
			] ],
			rowStyler:function(index,row){    
				if (row.isAllot==2){    
					return 'background-color:pink;color:blue;font-weight:bold;';    
				}    
			},    
			toolbar : '#toolbar'
		});
		var p = $('#qrcode_manage_datagrid').datagrid('getPager');
		$(p).pagination({ 
       		pageSize: 20,//每页显示的记录条数，默认为10 
        	pageList: [20,50],//可以设置每页记录条数的列表 
        	layout:['list','first','prev','links','next','last','refresh']
        });   
		
		 $("#cleanBtn").click(function () {
			 $("#searchForm").form("clear");
			  $('#qrcode_manage_datagrid').datagrid('load', {});
         });
	});
	
	//查看批次信息
// 	function user_manage_look_fn(id,qrcodeType) {
// 		var href = '${pageContext.request.contextPath}/qrcodeBatch/lookUpList.action?batchId='+id+'&qrcodeType='+qrcodeType;
// 		$('#openXXXIframe')[0].src=href;
// 		$('#openRoleDiv').dialog('open');
// 	}

	//采用jquery easyui loading css效果 
// 	function ajaxLoading(){ 
// 		$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body"); 
// 	    $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候……").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2}); 
// 	} 
// 	function ajaxLoadEnd(){ 
// 	     $(".datagrid-mask").remove(); 
// 	     $(".datagrid-mask-msg").remove();             
// 	} 
	
// 	function showProcess(isShow, title, msg) {
// 		if (!isShow) {
// 			$.messager.progress('close');
// 			return;
// 		}
// 		var win = $.messager.progress({
// 			title: title,
// 			msg: msg
// 		});
// 	}
	
	function sn_manage_search_fn() {
		$('#qrcode_manage_datagrid').datagrid('load', serializeObject($('#searchForm')));
	}

// 	function qrcode_manage_add_fn(){
// 		$('<div/>').dialog({
// 			id : 'add_dialog',
// 			href : '${pageContext.request.contextPath}/qrcodeBatch/toqrcodeBatchAdd.action?seqType=zhengshupici',
// 			width : 340,
// 			height : 245,
// 			modal : true,
// 			title : '创建批次',
// 			buttons : [ {
// 				text : '增加',
// 				iconCls : 'icon-add',
// 				handler : function() {
// 					addqrcodeBatch();
// 				}
// 			} ],
// 			onClose : function() {
// 				$(this).dialog('destroy');
// 			}
// 		});
// 	}
	//查看二维码 
	function view_qrcode_fn(id) {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/qrcode/viewQrcodeInfo.action?qrcodeId='+id,
			width : 500,
			height : 400,
			modal : true,
			title : '查看二维码详情',
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
	//修改二维码内容 
	function edit_qrcode_content(id) {
		$('<div/>').dialog({
			id:'edit_qrcode_content_dialog',
			href : '${pageContext.request.contextPath}/qrcode/toEditQrcodeContent.action?qrcodeId='+id,
			width : 500,
			height : 400,
			modal : true,
			title : '修改二维码内容',
			buttons : [ {
				text : '修改',
				iconCls : 'icon-edit',
				handler : function() {
					edit_qrcode_content1();
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
	function viewQrcodeDetail(content){
		var href = content;
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+href+'" style="width:100%;height:100%;"></iframe>';
		parent.layout_center_addTabFun({title:'二维码', content:content, closable:true});
	}
</script>


<div class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'center',border:false">
		<table id="qrcode_manage_datagrid"></table>
	</div>
	
	<div id="toolbar" class="datagrid-toolbar"  style="padding-top: 3px">
		<table style="width: 100%">
			<tbody>
			<tr>
				<td width="50%">
					<form action="javascript:void(0);" id="searchForm" style="margin-bottom: 0px">
			            <input class="easyui-textbox" id="qrcodeBatchVo_batchNumber" name="qrcodeBatchVo.batchNumber" data-options="prompt:'批次编号'" style="width: 100px;" />
			            <span>-</span>
			            <input class="easyui-textbox" id="qrcodeNumber" name="qrcodeNumber" data-options="prompt:'二维码编号'" style="width: 150px;" />
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
						<input id="qrcodeType" name="qrcodeType" class="easyui-combobox" style="width:150px;"
							data-options="
							valueField: 'label',
							textField: 'value',
							data: [{label: '1',value: 'url'},{label: '2',value: '文本'},{label: '3',value: '图片'}],
							prompt:'请选择二维码类别'"/>
			             <!-- <input class="easyui-textbox" name="specifiName" data-options="prompt:'二维码类别'" style="width: 80px;" /> -->
			            <span>-</span>
			            <select class="easyui-combobox" name="qrcodeStatus" data-options="prompt:'二维码状态',editable:false,panelHeight:'auto'" style="width:90px;">
							<option ></option>
							<option value=0>未生效</option>
							<option value=1>印刷中</option>
							<option value=2>空闲</option>
							<option value=3>待发放</option>
							<option value=4>已发放</option>
						</select>
			            <span>-</span>
			            <a class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-search'" onclick="sn_manage_search_fn()" >查询</a>
			            <a id="cleanBtn" class="easyui-linkbutton" data-options="plain: true,iconCls:'icon-clear'" >重置</a>
				    </form>
				</td>
				<td align="right" width="40%">
<!-- 					<a href="javascript:void(0);" class="easyui-linkbutton l-btn l-btn-plain" data-options="iconCls:'icon-note_add',plain: true" title="创建二维码批次" onclick="qrcode_manage_add_fn();">创建二维码批次</a>
					<div class="datagrid-btn-separator"></div> -->
<%--					<a href="javascript:void(0);" class="easyui-linkbutton l-btn l-btn-plain" data-options="iconCls:'icon-table_go',plain:true" onclick="downLoadExcel();">导出</a>--%>
				</td>
			</tr>
		</tbody></table>
	</div>
</div>

<div id="openRoleDiv" class="easyui-window" closed="true" modal="true" minimizable="false" title="查看批次详细 " style="width:600px;height:380px;">
    <iframe scrolling="auto" id='openXXXIframe' frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
</div>