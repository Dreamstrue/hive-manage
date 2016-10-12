<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String zxtUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();//+ "/zxt"部署项目时候屏蔽; // 项目访问全路径，形如：http://localhost:8080/zyzlcxw
	//String zxtUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ "/zxt";
	request.setAttribute("zxtUrl", zxtUrl);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>实体信息管理</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../common/inc.jsp"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.form.js"></script>
  </head>
  
  <body>
	<div class="easyui-layout" fit="true">
		<!-- 查询条件 -->
		<div region="north" split="true" title="查询条件" border="false" class="p-search" style="overflow: hidden; height:65px; padding-left: 10px; padding-top: 5px;">
			<form action="javascript:void(0);" id="searchForms" style="width: 100%">
				<label style="font-size: 12px;">实体名称:</label>
				<input name="entityName" id="entityName" class="easyui-validatebox" style="width: 180px;" />
				<label style="font-size: 12px;">行业类别:</label>
			    <input name="entityType" id="entityType" class="easyui-combotree" data-options="url:'${appPath}/surveyIndustryManage/treegrid.action',parentField:'pid',lines:true, " style="width: 180px;" />
				<label style="font-size: 12px;">实体类别:</label>
				<input class="easyui-combotree" id="entityCategory" name="entityCategory" data-options="url:'${appPath}/entityCategoryManage/allCategoryEntityInfo.action',valueField:'id',parentField:'pid',lines:true," style="width:180px;"/>
				<label style="font-size: 12px;">审核状态:</label>
				<select class="easyui-combobox" id="cauditstatus" name="cauditstatus" data-options="prompt:'审核状态',editable:false,panelHeight:'auto'" style="width:150px;">
								<option></option>
								<option value=0>未审核</option>
								<option value=1>已审核</option>
				</select>
				
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFunc();">查询</a>
			    <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:true" onclick="cleanSearch();">重置</a>
			</form>
		</div>
		<!-- 数据列表 -->
	  	<div region="center" border="false">
			<table id="EntityInfoTable"></table>
	  	</div>
	</div>
		<!-- 弹出窗口：添加数据 --> 
	<div id="mydialog" style="display:none;padding:5px;width:400px;height:300px;" title="实体信息审核"> 
		<form id="form1" action="">
		<input type="hidden" id="ids" name="ids" value="" />
			<table>
			<tr>
			<td>审核结果：</td><td><select id="checktype" name="checktype">
					  <option value ="1">通过</option>
					  <option value ="2">不通过</option>
					</select></td>
			</tr>
			<tr>
			<td>审核意见：</td><td><textarea rows="5" cols="30" id="remark" class="easyui-validatebox" data-options="required:true" name="remark"></textarea> </td>
			</tr>
			</table>
		</form>
	</div>
	
	<script type="text/javascript">
		
		var searchFormList = "searchForms";
  		var gridListName = "EntityInfoTable";
		
		$(function() {
			$('#'+gridListName).datagrid({
				    url : '${appPath}/industryEntityManage/datagrid.action',
					fit : true,
					fitColumns : true,
					border : false,
					pagination : true,
					striped:true,
					rownumbers : true,
					idField : 'id',
					pageSize : 20,
					pageList : [10, 20, 30, 40, 50, 60],
					sortName : 'id',
					sortOrder : 'desc',
					checkOnSelect : true,
					selectOnCheck : false,
					singleSelect:false,
					nowrap : false,
					striped : true,
					toolbar : [
					<%--
					本来这儿这样判断就行了，可是 el 表达式不支持 按位与（&）运算，只能放在上面用 java 判断了
					<c:if test="${(sessionScope.userMenuActionMap[param.id] & 2) > 0}">
					--%>
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAdd}">
					{
						text : '增加实体信息',
						iconCls : 'icon-add',
						handler : function() {
							addFunc();
						}
					},'-',
					</c:if>
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
					{
						text : '修改实体信息',
						iconCls : 'icon-edit',
						handler : function() {
							editFunc();
						}
					},'-',
					</c:if>
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
					{
						text : '删除实体信息',
						iconCls : 'icon-remove',
						handler : function() {
							delEntityFunc();
						}
					},'-',
					</c:if>
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
					{
						text : '批量导出',
						iconCls : 'icon-save',
						handler : function() {
							saveExcelFunc();
						}
					}
					</c:if>
					/* ,'-',
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
					{
						text : '审核',
						iconCls : 'icon-edit',
						handler : function() {
							checkMamber();
						}
					},
					</c:if> */
					],
					columns : [[{
						field : 'id',
						title : '企业ID',
						checkbox:true
					},{
						field : 'entityName',
						title : '实体名称',
						width : 100,
						align:'center',
						sortable : true
					},{
						field : 'address',
						title : '实体地址',
						width : 100,
						align:'center',
						sortable : true
					},{
						field : 'linkMan',
						title : '联系人',
						width : 50,
						align:'center',
						hidden : false
					},{
						field : 'linkPhone',
						title : '联系人手机',
						align:'center',
						width : 50,
						hidden : false
					},{
						field : 'entityCategory',
						title : '实体类型',
						align:'center',
						width : 50,
						hidden : false,
					},{
						field : 'foundtime',
						title : '成立时间',
						align:'center',
						width : 50,
						hidden : false
					},{
						field : 'entityTypeName',
						title : '行业类别',
						align:'center',
						width : 50,
						hidden : false
					},{
						field : 'surveyTitle',
						title : '绑定问卷',
						align:'center',
						width : 100,
						hidden : false
					},{
						field : 'entityType',
						title : '实体类别',
						hidden : true
					},{
						field : 'objectId',
						title : '实体类别',
						hidden : true
					},{
						field : 'creatUserstatus',
						title : '创建用户状态',
						width : 10,
						align:'center',
						hidden : true
					},{
						field : 'cauditstatus',
						title : '审核状态',
						align:'center',
						width : 40,
						formatter : function(value, row, index) {
							if(value == 0){
								return "未审核";
							}else if(value == 1){
								return "已通过";
							}else if(value == 2){
								return "未通过";
							}
						}
					}
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView || requestScope.canEdit || requestScope.canDelete}">
					,{
					field : 'action',
					title : '操作',
					width : 70,
					align:'center',
					formatter : function(value, row, index) {
						var html="";
						if (row.cauditstatus == '0') {
							<%-- 这种动作字符串拼接方法比较好，方便对每个操作加权限。示范页面！ --%>	
							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
		 					html += '<img title="审核" onclick="checkMamber(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/book_open.png"/>';
		 					</c:if>
							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
							html+="&nbsp;<img onclick=\"delFunc('"+row.id+"');\" title='逻辑删除' src='${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png'/>";
							</c:if>
							return html;
						} else if (row.cauditstatus == '1') {
							html+="<img onclick=\"survey_list_fn('"+row.entityType+"','"+row.objectId+"','"+row.entityName+"');\" title='查看问卷列表' src='${pageContext.request.contextPath}/resources/images/extjs_icons/table/table.png'/>";
							html+="&nbsp;<img onclick=\"survey_record_fn('"+row.objectId+"');\" title='查看评价记录' src='${pageContext.request.contextPath}/resources/images/extjs_icons/book_go.png'/>";
							html+="&nbsp;<img onclick=\"set_survey_fn('"+row.id+"');\" title='绑定/修改问卷问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/cog_edit.png'/>";
							html+="&nbsp;<img onclick=\"view_barcode_fn('"+row.id+"');\" title='查看二维码' src='${pageContext.request.contextPath}/resources/images/extjs_icons/barcode.png'/>";
							if(row.creatUserstatus == '0'){
							html+="&nbsp;<img onclick=\"create_user_fn('"+row.id+"');\" title='创建账户' src='${pageContext.request.contextPath}/resources/images/extjs_icons/bin.png'/>";
							}
							return html;
						} else if (row.cauditstatus == '2') {
							<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
							html+="&nbsp;<img onclick=\"delFunc('"+row.id+"');\" title='逻辑删除' src='${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png'/>";
							</c:if>
							return html;
						}
					}
					}
					</c:if>
					]]
			});
		});// 页面加载完后的初始化完毕
		
		/*
		 * "查询"按钮的事件处理
		 *	
		*/
		function searchFunc(){
			$('#'+gridListName).datagrid('load', serializeObject($('#'+searchFormList)));
			$('#'+gridListName).datagrid('clearSelections');
		}
		/*
		清空查询条件
		*/
		function cleanSearch() {
			$('#'+searchFormList+' input').val('');
			$('#'+gridListName).datagrid('load', {});
			$('#'+gridListName).datagrid('clearSelections');
		}//End Of cleanSearch()
	
		function checkMamber(id){
				$('#ids').val(id);
				Open_Dialog();
			
		}// End Of checkAllow()
		
		function checkAdd(){
			if($("#form1").form('validate')){
				var param=$('#form1').formSerialize();
				$.post('${pageContext.request.contextPath}/industryEntityManage/checkEntity.action',param, function (re) {
			        if (re.status) {
			        	$.messager.alert('提示',re.msg);
			        	$('#mydialog').dialog('close');
			        	$('#EntityInfoTable').datagrid('load');
			        }
			    }, 'json');
			    $('#EntityInfoTable').datagrid('unselectAll');
			  //  window.parent.reloadWestTree();//刷新左侧菜单
		    }
		}// End Of checkAdd()
		//==============================
		//添加企业登记信息
		function addFunc(){
			var url = "${pageContext.request.contextPath}/industryEntityManage/add.action";
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			parent.layout_center_addTabFun({title:'实体信息添加', content:content, closable:true});
		}
		
		//修改企业登记信息
		function editFunc(){
			var url = "${pageContext.request.contextPath}/industryEntityManage/toEntityInfoEdit.action";
			var selectedRow = getSelectedRow();
			var entInfoId = selectedRow['id'];
			url = url + "?entInfoId="+entInfoId;
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			parent.layout_center_addTabFun({title:'实体信息编辑', content:content, closable:true});
		}
		
		//批量导出Excel
		function saveExcelFunc(){
			var rows = $('#'+gridListName).datagrid('getChecked');
			if(rows.length > 0){
				$.messager.confirm('确定操作','您是将选中的 '+rows.length+' 个实体导出吗？',function(tag){
					if(tag){
						var ids = [];
						for(var i = 0; i<rows.length; i++){
							ids.push(rows[i].id);
						}
						var idss = ids.join(',');
						$('#'+gridListName).datagrid('clearChecked');
						$('#'+gridListName).datagrid('load');
						window.location.href ="${pageContext.request.contextPath}/industryEntityManage/entitySaveExcel.action?ids="+idss;
						/* $.ajax({    
							type:'get',    
 							url:'${pageContext.request.contextPath}/industryEntityManage/entitySaveExcel.action',    
						    data:{
							    "ids" : idss
							    },    
						    cache:false,    
						    dataType:'json',  
						    async:false, 
						    success:function(re){
								$('#'+gridName).datagrid('clearChecked');
								$('#'+gridName).datagrid('load');
							}
						});*/
					}	
					}); 
			}else {
				$.messager.alert('提示','请选择要导出的文件！','info');
			}
		}
		
		<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
		// 如果没有删除权限，那么这个 js 方法也应该隐藏掉，否则用户通过“查看框架源代码”就看到了删除方法，就可以在地址栏直接请求删除地址了
		//删除实体信息
		function delEntityFunc(){
			var selectedRow = getSelectedRow();
			if(selectedRow==''||selectedRow.length<1){
				easyuiBox.alert('请选择要删除的实体');
			}else{
				easyuiBox.confirm("确认", "确定要删除吗？", function(){
					var entInfoId = selectedRow['id'];
					var delUrl = "${pageContext.request.contextPath}/industryEntityManage/delInfo.action?entInfoId="+entInfoId;
					// 发送删除请求
		 			$.ajax({
		 				url : delUrl,
		 				dataType : 'json',
		 				success : function(result) {
		 					if(result.status){
		 						$('#'+gridListName).datagrid('reload');
		 						easyuiBox.show(result.msg);
		 					}else{
		 						easyuiBox.alert(result.msg);
		 					}
		 					
		 				},
		 				error : function(result) {
		 					easyuiBox.error("出现错误，请稍后重试！");
		 				}
		 			});
					
				});
			}
		}
		</c:if>
		//zhege是逻辑删除  上面是删除这条记录
		function delFunc(id){
			$.messager.confirm("确认", "确定要删除吗？", function(data){
			if(data){
				$.post("${pageContext.request.contextPath}/industryEntityManage/delLogicInfo.action",{entInfoId : id},function(r){
					if (r.status) {
						$.messager.show({
							title : '提示',
							msg : r.msg
						});
					  $('#'+gridListName).datagrid('reload');
					}else {
			        	$.messager.show({
							title : '提示',
							msg : re.msg
						});
			        	$('#'+gridListName).datagrid('load');
				        }
				},'json');
			}else {
	               $(this).closed;
	           }
			});
		}
		//查看二维码 
		function view_barcode_fn(id) {
			$('<div/>').dialog({
				href : '${pageContext.request.contextPath}/industryEntityManage/viewBarcodeInfo.action?entityId=' + id,
				width : 350,
				height : 300,
				modal : true,
				title : '查看二维码详情',
				onClose : function() {
					$(this).dialog('destroy');
				}
			});
		}
		//绑定问卷
		function set_survey_fn(id) {
			$('<div/>').dialog({
				id : 'binding_dialog',
				href : '${pageContext.request.contextPath}/industryEntityManage/bindingInfo.action?entityId=' + id,
				width : 350,
				height : 150,
				modal : true,
				title : '绑定问卷',
				buttons : [ {
					text : '绑定',
					iconCls : 'icon-ok',
					handler : function() {
						$('#sn_manage_add_form').form('submit', {
							url : '${appPath}/industryEntityManage/bindingSurveyInfo.action',
							success : function(result) {
								var r = $.parseJSON(result);
								if (r.status) {
									$("#binding_dialog").dialog('destroy');
									$('#'+gridListName).datagrid('load');
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
		/*
			获取列表的选中行
		*/
		function getSelectedRow(){
			var selectedRow = $('#'+gridListName).datagrid('getSelected');
			var rows = $('#'+gridListName).datagrid('getChecked');
			if(!selectedRow){
				easyuiBox.error('请选择一条记录!');
				return;
			}
			if(rows.length > 1){
				easyuiBox.error('只能选择一行!');
				return;
			}else{
				return selectedRow;
			}
		}// End Of getSelectedRow()
		//生产二维码图片
		function create_twocode_fn(id){
			//已经生产二维码图片
			$.post("${pageContext.request.contextPath}/industryEntityManage/createTwoCode.action",{id : id},function(r){
				if (r.status) {
					$.messager.show({
						title : '提示',
						msg : r.msg
					});
				}
			},'json');
		}
		//问卷列表
		function survey_list_fn(objectType,objectId,objectName){
			var url = "${zxt_url}/survey/surveryResultList.action?objectType="+objectType+"&objectId="+objectId+"&objectName="+objectName;
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			parent.layout_center_addTabFun({title:'问卷列表', content:content, closable:true});	
			/* // 固定窗口大小
				var winWidth = 700;
				var winHeight = 800;
				var moveLeft = (screen.width - winWidth)/2 -10; // screen.width 表示屏幕宽度，差值为弹出窗口左上角距离屏幕左边距离
				var moveTop = (screen.height - winHeight)/2 - 36; // 上面的 -10 是减去浏览器右侧滚动条的宽度，这儿的 -36是减去弹出窗口地址栏及标题的高度
				//打开一个空白窗口，并初始化大小 
			 	var imgwin=window.open(url ,'img','left=' + moveLeft + ',top=' + moveTop + ',width=' + winWidth + ',height=' + winHeight + ',location=no,scrollbars=yes') 
				imgwin.focus() //使窗口聚焦，成为当前窗口  */
		}
		//评价记录
		function survey_record_fn(objectId){
			var url = "${zxt_url}/surveyCountManage/toSurveyUserAnswer.action?objectId="+objectId;
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			parent.layout_center_addTabFun({title:'评价记录', content:content, closable:true});
			// 固定窗口大小
			/* 	var winWidth = 800;
				var winHeight = 800;
				var moveLeft = (screen.width - winWidth)/2 -10; // screen.width 表示屏幕宽度，差值为弹出窗口左上角距离屏幕左边距离
				var moveTop = (screen.height - winHeight)/2 - 36; // 上面的 -10 是减去浏览器右侧滚动条的宽度，这儿的 -36是减去弹出窗口地址栏及标题的高度
				//打开一个空白窗口，并初始化大小 
			 	var imgwin=window.open(url ,'img','left=' + moveLeft + ',top=' + moveTop + ',width=' + winWidth + ',height=' + winHeight + ',location=no,scrollbars=yes') 
				imgwin.focus() //使窗口聚焦，成为当前窗口  */
		}
		function Open_Dialog() {
			$('#mydialog').show(); 
			$('#mydialog').dialog({ 
				collapsible: true, 
				maximizable: true, 
				modal:true,
				buttons: [
					{ 
					text: '提交', 
					iconCls: 'icon-ok', 
					handler: checkAdd 
					}, 
					{ 
					text: '取消', 
					handler: function() { 
					$('#mydialog').dialog('close'); 
					} 
					}
				] 
			}); 
		}// End Of Open_Dialog()
		
		function viewQrcodeDetail(content){
			var href = content;
		    //document.location.href = "<c:url value='"+href+"'/>";
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+href+'" style="width:100%;height:100%;"></iframe>';
			parent.layout_center_addTabFun({title:'实体评价', content:content, closable:true});
		}
		 
		
		//创建用户
		function create_user_fn(id){
			$('<div/>').dialog({
				id : 'creatuser_dialog',
				href : '${pageContext.request.contextPath}/industryEntityManage/creatUser.action?entityId=' + id,
				width : 350,
				height : 200,
				modal : true,
				title : '创建账户',
				buttons : [ {
					text : '创建',
					iconCls : 'icon-ok',
					handler : function() {
						$('#manage_create_form').form('submit', {
							url : '${appPath}/industryEntityManage/creatLoginUser.action',
							success : function(result) {
								var r = $.parseJSON(result);
								if (r.status) {
									$("#creatuser_dialog").dialog('destroy');
									$('#'+gridListName).datagrid('load');
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
	
</body>
</html>