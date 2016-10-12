<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>活动管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../common/inc.jsp" />
  </head>
  
  <body class="easyui-layout">
  <div data-options="region:'north',href:'',title:'条件查询'" style="height: 110px; overflow: hidden;">
        <form action="javascript:void(0);" id="searchForm" style="margin-bottom: 0px;">
            <table id="toolbar" style="width: 100%">
                <tbody>
                    <tr>
                        <td>活动编号：</td>
                        <td>
                        	<input class="easyui-textbox" name="orderNum" data-options="prompt:'请输入活动编号'" />
                        </td>
                        <td>活动主题：</td>
                        <td>
                        	<input class="easyui-textbox" name="theme" data-options="prompt:'请输入活动主题'" />
                        </td>
                        <td>活动类型：</td>
                        <td>
                     		<select class="easyui-combobox" name="activityType" data-options="editable:false,width:'150px'">
                     			<option value="">请选择</option>
	                    		<option value="1">抽奖活动</option>
	                    	</select>
                        </td>
                    </tr>
                        <tr>
                        <td>开始时间：</td>
                        <td>
                        	<input class="easyui-datebox" name="beginTime" data-options="prompt:'活动开始日期'"  />
                        </td>
                        <td>结束时间：</td>
                        <td>
                        	<input class="easyui-datebox" name="endTime" data-options="prompt:'活动结束日期'"  />
                        </td>
                        <td>活动状态：</td>
                        <td>
                        	<select class="easyui-combobox" name="status" data-options="editable:false,width:'150px'">
	                    		<option value="">请选择</option>
	                    		<option value="0">未启动</option>
	                    		<option value="1">已启动</option>
	                    		<option value="2">已结束</option>
	                    	</select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="12" align="center">
	                        <a id="btn1" class="easyui-linkbutton" data-options="iconCls: 'icon-search'" onclick="vote_manage_search_fn()">查询</a>
	                        <a id="cleanBtn" class="easyui-linkbutton" data-options="iconCls: 'icon-reload'">清空</a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
    </div>
        <div data-options="region:'center',border:false,title:'评论列表'">
        <table id="activity_datagrid"></table>
    </div>
    <div id="tb" class="easyui-toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="activity_manage_fn('addaward')">新增抽奖活动</a> 
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="activity_manage_fn('addreward')">新增奖励活动</a> 
        <!-- <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="activity_manage_fn('edit')">修改</a> 
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="activity_manage_fn('delete')">删除</a> 
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="activity_manage_fn('detail')">查看</a>  -->
    </div>
  </body>
  
  
  <script type="text/javascript">
	function activity_manage_fn(p) {
		var parame = "";
		var src = "";
		//获取选中的行
		var rows = $('#activity_datagrid').datagrid('getSelections');
		if (p == 'addaward') {
			var url = "${pageContext.request.contextPath}/activityManage/toAwardActivityAdd.action";
			parent.layout_center_addTabFun({
				title : "新增抽奖活动",
				closable : true,
				iconCls : "",
				content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>'
			});
		}else if (p == 'addreward') {
			var url = "${pageContext.request.contextPath}/activityManage/toRewardActivityAdd.action";
			parent.layout_center_addTabFun({
				title : "新增奖励活动",
				closable : true,
				iconCls : "",
				content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>'
			});
		}else {
			alert("无法识别的命令！");
		}
	}
	$(function() {
		//datagrid加载
		$('#activity_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/activityManage/datagrid.action',
			fit : true,
			height : 'auto',
			fitColumns : true,
			border : false,
			rownumbers : true,
			pagination : true,
			checkOnSelect : true,
			selectOnCheck : true,
			columns : [ [ {
				field : 'id',
				title : 'id',
				align : 'center',
				checkbox : true
			}, {
				field : 'orderNum',
				title : '活动编号',
				align : 'center',
				width : 100,
				formatter : function(value, row, index) {
					if (row.orderNum != null) {
						return row.orderNum;
					}
				}
			}, {
				field : 'theme',
				title : '活动主题',
				align : 'center',
				width : 300,
				formatter : function(value, row, index) {
					if (row.theme != null) {
						return row.theme;
					}
				}
			}, {
				field : 'activityTypeStr',
				title : '活动类型',
				align : 'center',
				width : 100,
				formatter : function(value, row, index) {
					if (row.activityTypeStr != null) {
						return row.activityTypeStr;
					}else{
						return "空"
					}
				}
			}, {
				field : 'beginTime',
				title : '开始时间',
				align : 'center',
				width : 100,
				formatter : function(value, row, index) {
					if (row.beginTime != null) {
						var unixTimestamp = new Date(row.beginTime);  
						return unixTimestamp.Format("yyyy-MM-dd hh:mm:ss");
					}
				}
			}, {
				field : 'endTime',
				title : '结束时间',
				width : 100,
				align : 'center',
				formatter : function(value, row, index) {
					if (row.endTime != null) {
						var unixTimestamp = new Date(row.endTime);  
						return unixTimestamp.Format("yyyy-MM-dd hh:mm:ss");
					}
				}
			}, {
				field : 'joinTimes',
				title : '允许参与次数',
				align : 'center',
				width : 100,
				formatter : function(value, row, index) {
					if (row.joinTimes != null) {
						return row.joinTimes;
					}else{
						return "空"
					}
				}
			}, {
				field : 'joinTimesPeriod',
				title : '参与间隔',
				width : 80,
				align : 'center',
				formatter : function(value, row, index) {
					if (row.joinTimesPeriod != null) {
						return row.joinTimesPeriod+"天";
					}
				}
			}, {
				field : 'status',
				title : '状态',
				width : 100,
				align : 'center',
				formatter : function(value, row, index) {
					if (row.status != null) {
						if(row.status=="0"){
							return "未启动";
						}else if(row.status=="1"){
							return "已启动";
						}else if(row.status=="2"){
							return "已结束";
						}else if(row.status=="3"){
							return "已暂停";
						}else{
							return "空";
						}
					}
				},
				styler: function (value, row, index) { 
						if(row.status=='1') {//已启动
			        		return 'background-color:#00DD00;color:white;font-weight:bold;';
						}
						if(row.status=='2') {//已结束
			        		return 'background-color:#FF3333;color:white;font-weight:bold;';
						}
						if(row.status=='3') {//已暂停
								return 'background-color:#FFFF33;color:red;font-weight:bold;';
						}
			    	}
			},{
				field : 'createTime',
				title : '创建时间',
				width : 180,
				align : 'center',
				formatter : function(value, row, index) {
					if (row.createTime != null) {
						var unixTimestamp = new Date(row.createTime);  
						return unixTimestamp.Format("yyyy-MM-dd hh:mm:ss");
					}
				}
			},
		        {field:'action',title:'操作',width:150,align:'center',
		            formatter:function(value,row,index){
			            if(row.status=="0"){
		                    var c = '<a href="#" onclick="checkView(this)">查看</a> ';
		                    var e = '<a href="#" onclick="editActivity(this)">修改</a> ';
		                    var d = '<a href="#" onclick="deleteActivity(this)">删除</a> ';
		                    var s = '<a href="#" onclick="startActivity(this)">启动</a> ';
							return c+e+d+s;
						}else if(row.status=="1"){
		                    var c = '<a href="#" onclick="checkView(this)">查看</a> ';
		                    var z = '<a href="#" onclick="stopActivity(this)">暂停</a> ';
		                    var s = '<a href="#" onclick="endActivity(this)">终止</a> ';
							return c+z+s;
						}else if(row.status=="3"){
		                    var c = '<a href="#" onclick="checkView(this)">查看</a> ';
		                    var s = '<a href="#" onclick="startActivity(this)">启动</a> ';
							return c+s;
						}else{
		                    var s = '<a href="#" onclick="checkView(this)">查看</a> ';
							return s;
						}
		            }
		        }
		    ] ],
			rowStyler : function(index, row) {
			},
			toolbar : '#tb'
		});
		var p = $('#activity_datagrid').datagrid('getPager');
		$(p).pagination({
			pageSize : 10,//每页显示的记录条数，默认为10 
			pageList : [ 10, 50 ],//可以设置每页记录条数的列表 
			layout : [ 'list', 'first', 'prev', 'links', 'next', 'last', 'refresh' ]
		});

		$("#cleanBtn").click(function() {
			$("#searchForm").form("clear");
			$('#surveyName').combobox('reload');
			$('#activity_datagrid').datagrid('load', {});
		});
	});
	function vote_manage_search_fn() {
		if (!$('#searchForm').form('validate')) {
			$.messager.alert({
				title : '提示',
				msg : '请输入符合规范的条件内容！'
			});
		} else {
			$('#activity_datagrid').datagrid('load', serializeObject($('#searchForm')));
		}
	}
	//查看活动详情
	function checkView(target){
    	//获取要删除的行
    	var index = parseInt($(target).closest('tr.datagrid-row').attr('datagrid-row-index'));
// 	    alert("要查看的行数为："+index);
	    var rows_ = $('#activity_datagrid').datagrid('getRows');
	    var row = rows_[index];
	
		var url = "${pageContext.request.contextPath}/activityManage/toActivityView.action?activityId="+row.id+"&activityType="+row.activityType
		parent.layout_center_addTabFun({
			title : "查看活动详情",
			closable : true,
			iconCls : "",
			content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>'
		});
	}
	//修改活动信息
	function editActivity(target){
    	//获取要删除的行
    	var index = parseInt($(target).closest('tr.datagrid-row').attr('datagrid-row-index'));
// 	    alert("要修改的行数为："+index);
	    var rows_ = $('#activity_datagrid').datagrid('getRows');
	    var row = rows_[index];
	
		var url = "${pageContext.request.contextPath}/activityManage/toActivityEdit.action?activityId="+row.id+"&activityType="+row.activityType
		parent.layout_center_addTabFun({
			title : "修改活动信息",
			closable : true,
			iconCls : "",
			content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>'
		});
	}
	//删除活动信息
	function deleteActivity(target){
    	//获取要删除的行
    	var index = parseInt($(target).closest('tr.datagrid-row').attr('datagrid-row-index'));
// 	    alert("要修改的行数为："+index);
	    var rows_ = $('#activity_datagrid').datagrid('getRows');
	    var row = rows_[index];
	
	   	$.messager.confirm('提示', '您确定要删除此活动吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/activityManage/deleteActivity.action?activityId="+row.id,
					function(result){
						if (result.status) {
							$('#activity_datagrid').datagrid('reload');
						}
						$.messager.show({
							title : '提示',
							msg : result.msg
						});
					},'json');
				}
		});
	}
	//启动活动
	function startActivity(target){
    	//获取要启动的行
    	var index = parseInt($(target).closest('tr.datagrid-row').attr('datagrid-row-index'));
// 	    alert("要修改的行数为："+index);
	    var rows_ = $('#activity_datagrid').datagrid('getRows');
	    var row = rows_[index];
	
	   	$.messager.confirm('提示', '您确定要启动此活动吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/activityManage/startActivity.action?activityId="+row.id+"&status="+row.status,
					function(result){
						if (result.status) {
							$('#activity_datagrid').datagrid('reload');
						}
						$.messager.show({
							title : '提示',
							msg : result.msg
						});
					},'json');
				}
		});
	}
	//终止活动
	function endActivity(target){
    	//获取要终止的行
    	var index = parseInt($(target).closest('tr.datagrid-row').attr('datagrid-row-index'));
// 	    alert("要修改的行数为："+index);
	    var rows_ = $('#activity_datagrid').datagrid('getRows');
	    var row = rows_[index];
	
	   	$.messager.confirm('提示', '您确定要终止此活动吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/activityManage/endActivity.action?activityId="+row.id,
					function(result){
						if (result.status) {
							$('#activity_datagrid').datagrid('reload');
						}
						$.messager.show({
							title : '提示',
							msg : result.msg
						});
					},'json');
				}
		});
	}
	//暂停活动
	function stopActivity(target){
    	//获取要暂停的行
    	var index = parseInt($(target).closest('tr.datagrid-row').attr('datagrid-row-index'));
// 	    alert("要修改的行数为："+index);
	    var rows_ = $('#activity_datagrid').datagrid('getRows');
	    var row = rows_[index];
	
	   	$.messager.confirm('提示', '您确定要暂停此活动吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/activityManage/stopActivity.action?activityId="+row.id,
					function(result){
						if (result.status) {
							$('#activity_datagrid').datagrid('reload');
						}
						$.messager.show({
							title : '提示',
							msg : result.msg
						});
					},'json');
				}
		});
	}
  </script>
</html>