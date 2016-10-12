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
    <!-- <div id="tb" class="easyui-toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="activity_manage_fn('addaward')">新增抽奖活动</a> 
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="activity_manage_fn('edit')">修改</a> 
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="activity_manage_fn('delete')">删除</a> 
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="activity_manage_fn('detail')">查看</a> 
    </div> -->
  </body>
  
  
  <script type="text/javascript">
	$(function() {
		//datagrid加载
		$('#activity_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/activityManage/dataGridStartedActivity.action',
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
// 						if(row.status=='0') {
// 								return 'background-color:#FFFF33;color:red;font-weight:bold;';
// 						}
			    	}
			},
		        {field:'action',title:'操作',width:150,align:'center',
		            formatter:function(value,row,index){
			            if(row.status=="1"){
		                    var c = '<a href="#" onclick="checkView(this)">查看</a> ';
		                    var s = '<a href="#" onclick="allocationActivity(this)">分配</a> ';
							return c+s;
						}else{
		                    var s = '<a href="#" onclick="checkView(this)">查看</a> ';
							return s;
						}
		            }
		        }
		    ] ],
			view: detailview,
			detailFormatter:function(index,row){
                return '<div style="padding:2px"><table id="ddv-' + index + '" style="width:100%;"></table></div>';
            },
            onExpandRow: function(index,row){
            	
                $('#ddv-' + index).datagrid({
                        url: '${pageContext.request.contextPath}/activityObject/datagridForActionObject.action?activityId='+ row.id,
						fitColumns:true,
                        singleSelect:true,
                        rownumbers:true,
                        loadMsg:'',
						height:'auto',
                        columns: [[
                        	{ field: 'id', align :'center',hidden: true},
                        	{ title: '活动对象', field: 'objectName', align :'center',width: "15%"},
                            { title: '作用对象类型', field: 'actionObjectType', align :'center', width: "15%",
                            	formatter: function(value) {
									if(value==1) {
										return "行业实体";
									} else if(value==2) {
										return "标签批次";
									}
								}
                            },
                        	{ title: '活动作用对象', field: 'actionObjectName', align :'center',width: "15%"},
                        	{ title: '问卷主题', field: 'activityFormName', align :'center',width: "30%" },
                        	{ title: '状态', field: 'aostatus', align :'center',width: "5%",
                            	formatter: function(value) {
									if(value=='0') {
										return "已结束";
									}else if(value=='1') {
										return "已启动";
									}else if(value=='2') {
										return "已暂停";
									}else{
										return "未知";
									}
								} },
					        {field:'action',title:'操作',width:"10%",align:'center',
					            formatter:function(value,row,index){
						            if(row.aostatus=="1"){
					                    var c = '<a href="#" onclick="stopActionObject('+row.id+')">暂停</a> ';
					                    var s = '<a href="#" onclick="endActionObject('+row.id+')">终止</a> ';
										return c+s;
									}else if(row.aostatus=="2"){
					                    var c = '<a href="#" onclick="startActionObject('+row.id+')">启动</a> ';
					                    var s = '<a href="#" onclick="endActionObject('+row.id+')">终止</a> ';
										return c+s;
									}
					            }
					        }
                        ]],
                        onResize: function () {
                            $('#activity_datagrid').datagrid('fixDetailRowHeight', index);
                        },
                        onLoadSuccess: function () {
                            setTimeout(function () {
                                $('#activity_datagrid').datagrid('fixDetailRowHeight', index);
                            }, 0);
                        }
                    });
                    $('#activity_datagrid').datagrid('fixDetailRowHeight', index);
            }
// 			,
// 			toolbar : '#tb'
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
	//分配活动
	function allocationActivity(target){
	    //获取行
	    var index = parseInt($(target).closest('tr.datagrid-row').attr('datagrid-row-index'));
// 	    alert("要删除的行数为："+index);
	    var rows_ = $('#activity_datagrid').datagrid('getRows');
	    var row = rows_[index];
		$('<div/>').dialog({
			id : 'add_dialog',
			href : '${pageContext.request.contextPath}/activityObject/toActionObjectAdd.action?activityId='+row.id,
			width : 555,
			height : 280,
			modal : true,
			title : '分配活动',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					$('#allocationForm').form('submit', {
						url : '${pageContext.request.contextPath}/activityObject/actionObjectAdd.action',
						success : function(result) {
							var r = $.parseJSON(result);
							var type = $("#actionObjectType").combobox("getValue");
							var msg_ ="";
							if (r.status) {
								$("#add_dialog").dialog('destroy');
								$('#activity_datagrid').datagrid('load');
							}else{
								if(type=='1'){
									msg_ = "当前"+$("#objectName").val()+"实体";
								}else if(type=='2'){
									msg_ = "当前"+$("#batch").val()+"批次";
								}
							}
							$.messager.alert("提示",msg_+r.msg,"info");
// 							$.messager.show({
// 								title : '提示',
// 								msg : r.msg
// 							});
						}
					});
				}
			} ],
			onClose : function() {
				$(this).dialog('destroy');
			}
		});
	}
	//终止此对象的此次活动
	function endActionObject(id){
	   	$.messager.confirm('提示', '您确定要终止吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/activityObject/endActionObject.action?actionObjectId="+id,
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
	//暂停此对象的此次活动
	function stopActionObject(id){
	   	$.messager.confirm('提示', '您确定要暂停吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/activityObject/stopActionObject.action?actionObjectId="+id,
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
	//重新启动此对象的此次活动
	function startActionObject(id){
	   	$.messager.confirm('提示', '您确定要重新启动吗？', function(r) {
			if (r) {
				$.post("${pageContext.request.contextPath}/activityObject/startActionObject.action?actionObjectId="+id,
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