<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/inc.jsp"></jsp:include>
<style type="text/css">
body{margin:0px;padding:0px;}
.searchDiv{
	overflow: hidden;
	height: 30px; 
	border-bottom: 1px solid #99CCFF;
}
.tt{
	font-size: 12px;
	text-align: left;
}
</style>
<script type="text/javascript">
	$(function() {
		$('#pushinfo_manage_datagrid').datagrid({
			url : '${appPath}/pushinfo/datagrid.action',
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			rownumbers:true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id', // 默认按 id 降序排列
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect:true, // 列表页只能单选
			nowrap : false,
			frozenColumns : [ [ 
			<%-- 复选框就是为了批量删除，如果没有删除权限就不用显示了 --%>
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
			{
				title : '编号',
				field : 'id',
				width : 150,
				checkbox : true,
				hidden:true
			}, 
			</c:if>
			{
				title : '消息类型',
				field : 'objectType',
				width : 60,
				sortable : true,
				formatter:function(value,row,index){
					if (value == "info_notice")
						return "通知公告";
					else if (value == "info_news")
						return "新闻资讯";
					else if (value == "s_survey")
						return "问卷调查";
				}
			} , {
				title : '标题',
				field : 'title',
				width : 250,
				sortable : true
			} , {
				title : '描述',
				field : 'description',
				width : 190,
				sortable : true
			} , {
				title : '链接地址',
				field : 'url',
				width : 150,
				sortable : true
			} , {
				title : '消息打开方式',
				field : 'openType',
				width : 100,
				sortable : true,
				formatter:function(value,row,index){
					if (value == "0")
						return "在应用中打开";
					else if (value == "1")
						return "在浏览器中打开";
				}
			} , {
				title : '接收对象',
				field : 'receiveType',
				width : 60,
				sortable : true,
				align:'center',
				formatter:function(value,row,index){
					if (value == "0")
						return "全部";
					else if (value == "1")
						return "个人版";
					else if (value == "2")
						return "企业版";
				}
			} , {
				title : '定时推送',
				field : 'isTime',
				width : 60,
				align:'center',
				sortable : true,
				formatter:function(value,row,index){
					if (value == "0")
						return "否";
					else if (value == "1")
						return "是";
				}
			} , {
				title : '推送时间',
				field : 'pushTime',
				width : 130,
				sortable : true
			}] ],
			columns : [ [
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit || requestScope.canDelete}">
			{
				title : '动作',
				field : 'action',
				align :'center',
				width : 80,
				formatter : function(value, row, index) {
					//alert(row.id);
					if (row.id == '0') {
						return '系统动作';
					} else {
						//return formatString('<img onclick="pushinfo_manage_edit_fn(\'{0}\');" src="{1}" title="修改"/>&nbsp;<img onclick="pushinfo_manage_del_fn(\'{2}\');" src="{3}" title="删除"/>', row.id, '${appPath}/resources/images/extjs_icons/pencil.png', row.id, '${appPath}/resources/images/extjs_icons/cancel.png');
						var html = "";
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canEdit}">
						if (row.isTime == "1" && ParseDate(row.pushTime) > new Date()) // 只有对定时推送（而且设置的推送时间还没到）才有修改的意义
							html += '<img onclick="pushinfo_manage_edit_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/pencil.png" title="修改"/>';
						</c:if>
						<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canDelete}">
						html += '&nbsp;<img onclick="pushinfo_manage_del_fn(' + row.id + ');" src="${appPath}/resources/images/extjs_icons/cancel.png" title="删除"/>';
						</c:if>
						return html;
					}
				}
			} 
			</c:if>
			] ],
			toolbar : [ 
			<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAdd}"> 
			<%--
			{
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					pushinfo_manage_add_fn();
				}
			}, '-', 
			--%>
			</c:if>
			{
				text:'刷新',
				iconCls:'icon-reload',
				handler:function(){
					$('#pushinfo_manage_datagrid').datagrid('reload');
				}
			}
			]
		});
	});

	
	function pushinfo_manage_add_fn() {
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pushinfo/add.action',
			width : 400,
			height : 230,
			modal : true,
			title : '添加推送信息',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#pushinfo_manage_add_form').form('submit', {
						url : '${pageContext.request.contextPath}/pushinfo/insert.action',
						success : function(result) {
							try {
								var r = $.parseJSON(result);
								if (r.status) {
									//$('#pushinfo_manage_datagrid').datagrid('insertRow', {
									//	index : 0,
									//	row : r.data
									//});
									$('#pushinfo_manage_datagrid').datagrid('reload');
									d.dialog('destroy');
								}
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
							} catch (e) {
								$.messager.alert('提示', result);
							}
						}
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
	
	//单个删除
	function pushinfo_manage_del_fn(id) {
		$.messager.confirm('确认', '您确定要删除当前选中的记录吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/pushinfo/delete.action",{id : id},function(result){
					if (result.status) {
						$('#pushinfo_manage_datagrid').datagrid('load');
						$('#pushinfo_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
					}
					$.messager.show({
						title : '提示',
						msg : result.msg
					});
				},'json');
			}
		});
	}
	
	// 批量删除
	function pushinfo_manage_delAll_fn() {
		var rows = $('#pushinfo_manage_datagrid').datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '您确定要删除当前选中的记录吗？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					//alert(ids);
					//return false;
					$.ajax({
						url : '${pageContext.request.contextPath}/pushinfo/delete.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(result) {
							if (result.status) {
								$('#pushinfo_manage_datagrid').datagrid('load');
								$('#pushinfo_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
							}
							$.messager.show({
								title : '提示',
								msg : result.msg
							});
						}
					});
				}
			});
		} else {
			$.messager.show({
				title : '提示',
				msg : '请选择要删除的记录！'
			});
		}
	}
	
	
	
	function pushinfo_manage_edit_fn(id) {
		$('#pushinfo_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
		//alert(id);
		$('<div/>').dialog({
			href : '${pageContext.request.contextPath}/pushinfo/edit.action?id=' + id,
			width : 400,
			height : 300,
			modal : true,
			title : '编辑推送信息',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#pushinfo_manage_edit_form').form('submit', {
						url : '${pageContext.request.contextPath}/pushinfo/update.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								$('#pushinfo_manage_datagrid').datagrid('reload');
								d.dialog('destroy');
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
			},
			onLoad : function() {
				var index = $('#pushinfo_manage_datagrid').datagrid('getRowIndex', id);
				var rows = $('#pushinfo_manage_datagrid').datagrid('getRows');
				$('#pushinfo_manage_edit_form').form('load', {
										id:rows[index].id,
										name:rows[index].name,
										menu:stringToList(rows[index].menu),
										permission:stringToList(rows[index].permission)
										});
			}
		});
	}
	
	// 将字串解析成日期值
	function ParseDate(s) {    
	    var dv, reg = /^\d\d\d\d-\d\d-\d\d \d\d:\d\d:\d\d$/gi;
	    if (!reg.test(s)) {
	        window.alert("日期值格式错误!");
	        return null;
	    } else {
	        dv = new Date(Date.parse(s.replace(/-/g, "/")));
	        /* 比较日期值的各部分是否相同, 防止输入错误日期值, 如2013-08-33这种/ */
	
	        if (dv.getFullYear() != eval(s.substring(0, 4)) || dv.getMonth() + 1 != eval(s.substring(5, 7)) || dv.getDate() != eval(s.substring(8, 10))
	
	            || dv.getHours() != eval(s.substring(11, 13)) || dv.getMinutes() != eval(s.substring(14, 16)) || dv.getSeconds() != eval(s.substring(17, 19))
	      ) {
	
	            window.alert("日期值错误!");
	            return null;
	        }
	    }
	    return dv;
	}
	
	function pushinfo_manage_search_fn() {
		$('#pushinfo_manage_datagrid').datagrid('load', serializeObject($('#pushinfo_manage_search_form')));
	}
	
	function cleanForm() {
		$('#pushinfo_manage_search_form input[class="inval"]').val('');
		$('#objectType').combobox('clear');
		$('#pushinfo_manage_datagrid').datagrid('load', {});
	}
</script>
<div class="searchDiv">
		<form action="javascript:void(0);" id="pushinfo_manage_search_form" >
			<table class="form" style="width: 60%;height:30px;">
				<tr>
					<td class="tt" width="60"><b> 查询条件： </b></td>
					<td class="tt"> 标题</td>
					<td width="160"><input class="inval" name="title" style="width:150px;"></td>
					<td class="tt" width="60">消息类型</td>
					<td style="width:150px;" style="text-align: left;"><select id="objectType" name="objectType" class="easyui-combobox" style="width:150px;">
							<option value=""></option>
							<option value="info_notice">通知公告</option>
							<option value="info_news">新闻资讯</option>
							<option value="s_survey">问卷调查</option>
						</select>
					</td>
					<td style="text-align: left;">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="pushinfo_manage_search_fn();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" onclick="cleanForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
<table id="pushinfo_manage_datagrid"></table>