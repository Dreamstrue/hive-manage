<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<div class="easyui-layout" fit="true">
		<!-- 数据列表 -->
	  	<div region="center" border="false">
			<table id="EntityInfoTable"></table>
	  	</div>
	  	<div id="ineqtoolbar" class="datagrid-toolbar"  style="padding-top: 3px">
		<table style="width: 100%">
			<tbody>
			<tr>
				<td align="center" width="100%">
					<form action="javascript:void(0);" id="searchForms" style="width: 100%">
						<input name="entityName" id="entityName" class="easyui-textbox" style="width: 180px;"  data-options="prompt:'请输入实体名称'"/>
			            <span>-</span>
			            <input id="entityType" name="entityType" class="easyui-combotree" data-options="editable:false,
						    url: '${appPath}/surveyIndustryManage/treegrid.action',
						    parentField:'pid',lines:true,
						    prompt:'请选择行业类别'">
			            <span>-</span>
			            <input id="entityCategory" name="entityCategory" class="easyui-combotree" data-options="editable:false,
						    url: '${appPath}/entityCategoryManage/allCategoryEntityInfo.action',
						    valueField:'id',
						    parentField:'pid',lines:true,
						    prompt:'请选择实体类别'">
			            <span>-</span>
			            <select class="easyui-combobox" name="cauditstatus" data-options="prompt:'审核状态状态',editable:false,panelHeight:'auto'" style="width:120px;">
							<option ></option>
							<option value=0>未审核</option>
							<option value=1>已审核</option>
						</select>
			            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFunc();">查询</a>
			    		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:true" onclick="cleanSearch();">重置</a>
				    </form>
				</td>
			</tr>
		</tbody></table>
	</div>
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
					selectOnCheck : true,
					singleSelect:true,
					nowrap : false,
					striped : true,
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
					]],    
					toolbar : '#ineqtoolbar'
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

	</script>
