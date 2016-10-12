<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>企业登记信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../common/inc.jsp" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/enterpriseInfo/codeSelectInit.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/enterpriseInfo/industryCategory.js" charset="utf-8"></script>
  </head>
  
  <body>
  	<div class="easyui-layout" fit="true">

  		<!-- 查询条件 -->
  		 <div id="searchDiv" region="north" split="true" title="查询条件" border="false" class="p-search" style="height:66px; padding-left: 10px; padding-top: 5px;">  
			<form action="javascript:void(0);" id="searchForm" style="width: 100%">
				<label style="font-size: 12px;">企业名称:</label>
				<input name="entName" style="width: 215px;" />
				<label style="font-size: 12px;">组织机构代码:</label>
				<input name="orgCode" style="width: 215px;" />
				<input type="checkbox" id="advSearToggle" name="advSearToggle"/><label for="advSearToggle">高级查询</label>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFunc();">查询</a>
				<!-- <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanSearch();">清空条件</a> -->
				
				<div id="advSearElementsDiv">
					<table style="width: 99%;" id="advSearElementsTable">
						<tbody>
							<tr>
								<td align="left" width="8%">行政区划：</td>
								<td align="left" width="92%">
									<select id="cprovincecode" name="cprovincecode"><option value="">请选择省份...</option></select>
									<select id="ccitycode" name="ccitycode"></select>
								</td>
							</tr>
							<tr>
								<td align="left">注册类型：</td>
								<td align="left">
									<input id="oldCompanyTypeCode" type="hidden" value="${enterpriseInfo.ccomtypcode}"/>
									<div id="typeDiv">
										<select id="companyType" name="regType"><option value="">请选择...</option></select>
									</div>
								</td>
							</tr>
							<tr>
								<td align="left">行业类别：</td>
								<td align="left">
									<input id="oldIndCatCode" type="hidden" value="${enterpriseInfo.cindcatcode}"/>
									<div id="tradeDiv">
										<input id="industryid" name="cindcatcode" value="${enterpriseInfo.cindcatcode }" class="easyui-combotree" data-options="url:'${appPath}/surveyIndustryManage/treegrid.action',parentField:'pid',lines:true" style="width:170px;" />
									</div>
								</td>
							</tr>
							<tr>
								<td align="left">法人代表：</td>
								<td align="left">
									<input type="text" id="legal" name="legal"/>
									通讯地址：
									<input type="text" id="contractAddress" name="contractAddress"/>
								</td>
							</tr>
						</tbody>
					</table>
					 
				</div>
				
			</form>
        </div>  
        <!-- 数据列表 -->
	  	<div region="center" border="false">
		    <table id="entInfoTable"></table>
	  	</div>
  	</div>
  </body>
  
  	<script type="text/javascript">
  	
  		var searchFormName = "searchForm";
  		var gridName = "entInfoTable";
		
		$(function(){

			// 高级查询选项的初始化处理
			var isShowAdvSear = $("#advSearToggle").attr("checked");
			showOrHideAdvSearDiv(isShowAdvSear);
			// 高级查询 复选框的事件处理
			$("#advSearToggle").click(function(){
				//判断是否被选中
				 var isChecked=$('#advSearToggle').is(':checked');
				 showOrHideAdvSearDiv(isChecked);
			});
				 
		
			// 列表数据的加载
			$("#"+gridName).datagrid({
				url : '${pageContext.request.contextPath}/enterpriseManage/listEntInfo.action',
				fit : true,
				fitColumns : true,
				border : false,
				pagination : true,
				rownumbers : true,
				idField : 'nenterpriseid',
				pageSize : 20,
				pageList : [10, 20, 30, 40, 50, 60],
				sortName : 'dcreatetime',
				sortOrder : 'desc',
				checkOnSelect : false,
				selectOnCheck : false,
				singleSelect:true,
				nowrap : false,
				toolbar : [ 
				<%--
				本来这儿这样判断就行了，可是 el 表达式不支持 按位与（&）运算，只能放在上面用 java 判断了
				<c:if test="${(sessionScope.userMenuActionMap[param.id] & 2) > 0}">
				--%>
				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAdd}">
				{
					text : '增加企业登记信息',
					iconCls : 'icon-add',
					handler : function() {
						addFunc();
					}
				},'-',
				</c:if>
				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
				{
					text : '修改企业登记信息',
					iconCls : 'icon-edit',
					handler : function() {
						editFunc();
					}
				},'-',
				</c:if>
				<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
				{
					text : '删除企业登记信息',
					iconCls : 'icon-remove',
					handler : function() {
						delFunc();
					}
				}
				</c:if>
				],
				columns : [[{
					field : 'nenterpriseid',
					title : '企业ID',
					hidden : true
				},{
					field : 'centerprisename',
					title : '企业名称',
					width : 150,
					sortable : true
				},{
					field : 'clegal',
					title : '法人代表',
					width : 50,
					hidden : false
				},{
					field : 'ccontractperson',
					title : '联系人',
					width : 50,
					hidden : false
				},{
					field : 'ccontractpersonphone',
					title : '联系人手机',
					width : 50,
					hidden : false
				},{
					field : 'ccontractaddress',
					title : '通讯地址',
					width : 50,
					hidden : false
				},{
					field : 'corganizationcode',
					title : '组织机构代码',
					width : 50,
					hidden : false
				},{
					field : 'destablishdate',
					title : '成立时间',
					width : 50,
					hidden : false
				},{
					field : 'dbusinesslicenseend',
					title : '营业执照有效期至',
					width : 50,
					hidden : false
				},{
					field : 'cauditstatus',
					title : '审核状态',
					width : 50,
					hidden : false,
					formatter : back_zh_CN
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
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
						html+="&nbsp;<img onclick=\"vote_questionnaire_edit_fn('"+row.id+"');\" title='查看/编辑问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png'/>";
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
	 					html += '&nbsp;<img title="审核" onclick="survey_manage_approve_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/book_open.png"/>';
	 					</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
						html+="&nbsp;<img onclick=\"vote_question_del_fn('"+row.id+"');\" title='删除问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png'/>";
						</c:if>
						if(row.isShow=='0'){
							html+="&nbsp;<img onclick=\"vote_show_true_fn('"+row.id+"');\" title='公示问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/eye.png'/>";
						}else if(row.isShow=='1'){
							html+="&nbsp;<img onclick=\"vote_show_flase_fn('"+row.id+"');\" title='屏蔽公示问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/delete.png'/>";
						}
						return html;
					} else if (row.cauditstatus == '1') {
						<%-- 这种动作字符串拼接方法比较好，方便对每个操作加权限。示范页面！ 	
						//html+="<img onclick=\"vote_questionnaire_find_fn('"+row.surveyid+"');\" title='查看问卷' src='${pageContext.request.contextPath}/static/img/extjs_icons/table/table.png'/>";
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canView}">
						html+="&nbsp;<img onclick=\"survey_count_fn('"+row.id+"');\" title='查看投票结果' src='${pageContext.request.contextPath}/resources/images/extjs_icons/table/table.png'/>";
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
						html+="&nbsp;<img onclick=\"survey_preview_fn('"+row.id+"');\" title='预览问卷' src='${pageContext.request.contextPath}/resources/images/preview.png'/>";
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
						html+="&nbsp;<img onclick=\"vote_question_del_fn('"+row.id+"');\" title='删除问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png'/>";
						</c:if>
						if(row.isShow=='0'){
							html+="&nbsp;<img onclick=\"vote_show_true_fn('"+row.id+"');\" title='公示问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/eye.png'/>";
						}else if(row.isShow=='1'){
							html+="&nbsp;<img onclick=\"vote_show_flase_fn('"+row.id+"');\" title='屏蔽公示问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/delete.png'/>";
						}
						--%>
						html+="&nbsp;<img onclick=\"create_twocode_fn('"+row.nenterpriseid+"');\" title='创建二维码' src='${pageContext.request.contextPath}/resources/images/extjs_icons/barcode.png'/>";
						return html;
					} else if (row.cauditstatus == '2') {
						<%-- 这种动作字符串拼接方法比较好，方便对每个操作加权限。示范页面！ --%>	
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
						html+="&nbsp;<img onclick=\"vote_questionnaire_edit_fn('"+row.id+"');\" title='查看/编辑问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png'/>";
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
						html+="&nbsp;<img onclick=\"vote_question_del_fn('"+row.id+"');\" title='删除问卷' src='${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png'/>";
						</c:if>
						return html;
					}
				}
				}
				</c:if>
				,{
					field : 'cvalid',
					title : '是否有效',
					width : 50,
					hidden : true
				}]]
			});
			
		});// End Of 页面加载完后的初始化
		
		
		// 显示或隐藏高级查询项内容
		function showOrHideAdvSearDiv(isShow){
			if(isShow){
				$("#searchDiv").css("height", "130px");
				$("#advSearElementsDiv").show();
			}else{
				$("#searchDiv").css("height", "30px");
				$("#advSearElementsDiv").hide();
			}
		}
		
		//==============================
		//添加企业登记信息
		function addFunc(){
			var url = "${pageContext.request.contextPath}/enterpriseManage/toEntInfoAdd.action";
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			parent.layout_center_addTabFun({title:'企业信息登记', content:content, closable:true});
		}
		
		//修改企业登记信息
		function editFunc(){
			var url = "${pageContext.request.contextPath}/enterpriseManage/toEntInfoEdit.action";
			var selectedRow = getSelectedRow();
			var entInfoId = selectedRow['nenterpriseid'];
			url = url + "?entInfoId="+entInfoId;
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			parent.layout_center_addTabFun({title:'企业信息编辑', content:content, closable:true});
		}
		
		<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
		// 如果没有删除权限，那么这个 js 方法也应该隐藏掉，否则用户通过“查看框架源代码”就看到了删除方法，就可以在地址栏直接请求删除地址了
		//删除企业登记信息
		function delFunc(){
			var selectedRow = getSelectedRow();
			
			if(selectedRow==''||selectedRow.length<1){
				easyuiBox.alert('请选择要删除的企业');
			}else{
				easyuiBox.confirm("确认", "确定要删除吗？", function(){
					var entInfoId = selectedRow['nenterpriseid'];
					var delUrl = "${pageContext.request.contextPath}/enterpriseManage/delEntInfo.action?entInfoId="+entInfoId;
					// 发送删除请求
		 			$.ajax({
		 				url : delUrl,
		 				dataType : 'json',
		 				success : function(result) {
		 					if(result.status){
		 						$('#'+gridName).datagrid('reload');
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
		
		/*
			获取列表的选中行
		*/
		function getSelectedRow(){
			var selectedRow = $('#'+gridName).datagrid('getSelected');
			if(!selectedRow){
				easyuiBox.error('请选择一条记录!');
				return;
			}else{
				return selectedRow;
			}
		}// End Of getSelectedRow()
		
		
		/*
			查询
		*/
		function searchFunc() {
			$('#'+gridName).datagrid('load', serializeObject($('#'+searchFormName)));
		$("#searchDiv").css("height", "28px");
		$("#advSearToggle").attr("checked", false);
		}// End Of search()
		
		/*
			清空查询条件
		*/
		function cleanSearch() {
			$('#'+searchFormName+' input').val('');
			$('#'+gridName).datagrid('load', {});
		}//End Of cleanSearch()
		
		//生产二维码图片
		function create_twocode_fn(id){
			//已经生产二维码图片
			$.post("${pageContext.request.contextPath}/enterpriseManage/createTwoCode.action",{id : id},function(r){
				if (r.status) {
					$.messager.show({
						title : '提示',
						msg : r.msg
					});
				}
			},'json');
		}
	</script>
  
</html>
