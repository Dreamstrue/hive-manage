<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>积分管理</title>
<jsp:include page="../common/inc.jsp"></jsp:include>
<style type="text/css">
body{margin:0px;padding:0px;}
.searchDiv{
	overflow: hidden;
	height: 60px; 
	border-bottom: 1px solid #99CCFF;
}
.tt{
	font-size: 12px;
	text-align: right;
}
</style>
</head>

<body>
	<script type="text/javascript">
   		$(function(){
   			$('#integral_manage_datagrid').datagrid({
   				url:'${appPath}/integralOil/integralOilDataGrid.action',
   				idField:'id',
   				fit:true,
   				fitColumns:true,
   				pagination:true,
   				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
   				rownumbers:true,
   				border:false,
   				animate:false,
   				frozenColumns:[[]],
   				columns:[[{
					field : 'chineseName',
					title : '姓名',
					width : 30
				},{
   					field:'userName',
   					title:'系统账号',
   					width:30,
   					align:'center'
   				},{
   					field:'userId',
   					title:'userId',
   					width:50,
   					hidden:true
   				},{
   					field:'mobilePhone',
   					title:'手机号',
   					width:50,
   					align:'center'
   				},{
   					field:'idCard',
   					title:'身份证',
   					width:60,
   					align:'center'
   				},{
   					field:'anonymousFlag',
   					title:'用户类型',
   					width:40,
   					align:'center'
   				},{
   					field:'totalIntegral',
   					title:'总积分',
   					width:50,
   					align:'center'
   				},{
   					field:'currentIntegral',
   					title:'当前积分',
   					align:'center',
   					width:50
   				},{
   					field:'usedIntegral',
   					title:'已消费积分',
   					width:50,
   					align:'center',
   					formatter:function(value,row,index){
   						if(value>0){
   							var html = "";
	   						html += '<a href="javascript:usedDetail('+ row.userId +')">'+value+'</a>';
	   						return html;
   						}else{
   							return value;
   						}
   					}
   				},{
   					field:'action',
   					title:'操作',
   					width:50,
   					align:'center',
   					formatter:function(value,row,index){
   							var html = "";
	   						html += '<a href="javascript:integralDetail('+ row.userId +')">积分明细</a>';
	   						html += '&nbsp;|&nbsp;'
	   						html += '<a href="javascript:integralExchange('+ row.userId +')">积分兑换</a>';
	   						return html;
   					}
   				}]],
   				view: detailview,
   				detailFormatter:function(index,row){
   	                return '<div style="padding:2px"><table id="ddv-' + index + '" style="width:100%;"></table></div>';
   	            },
   	            onExpandRow: function(index,row){
   	                $('#ddv-' + index).datagrid({
   	                        url: '${appPath}/integralOil/integralCategory.action?userId='+ row.userId,
   							fitColumns:true,
   	                        singleSelect:true,
   	                        rownumbers:true,
   	                        loadMsg:'',
   							height:'auto',
   	                        columns: [[
								{ title: '实体类型', field: 'entityCategory', hidden:true},
   	                        	{ title: '实体类型', field: 'entityCategoryName', align :'center',width: "15%"},
   	                            { title: '评价次数', field: 'counts', align :'center', width: "10%" },
   	                        	{ title: '总积分', field: 'totalIntegral', align :'center',width: "15%" },
   	                            { title: '当前积分', field: 'currentIntegral', align :'center', width: "15%" },
   	                            { title: '消费积分', field: 'usedIntegral', align :'center', width: "15%"},
   	                             {
   	                				field : 'action',
   	                				title : '动作',
   	                				width : "14%",
   	                				align :'center',
   	                				formatter : function(value2, row2, index2) {
   	                						var html = "";
   	                						html += '<a href="javascript:integralCateDetail('+ row.userId +','+row2.entityCategory+')">积分明细</a>';
   		                					return html;
   	                					}
   	                            }
   	                        ]],
   	                        onResize: function () {
   	                            $('#integral_manage_datagrid').datagrid('fixDetailRowHeight', index);
   	                        },
   	                        onLoadSuccess: function () {
   	                            setTimeout(function () {
   	                                $('#integral_manage_datagrid').datagrid('fixDetailRowHeight', index);
   	                            }, 0);
   	                        }
   	                    });
   	                    $('#integral_manage_datagrid').datagrid('fixDetailRowHeight', index);
   	            },
   				toolbar:[{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#integral_manage_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		//消费明细
   		function usedDetail(userId){
   			$('<div/>').dialog({
				href : '${appPath}/integralOil/usedDetail.action?userId='+userId,
				width :880,
				height : 400,
				modal : true,
				title : '消费明细',
				onClose : function() {
					$(this).dialog('destroy');
				}
			});
   		}
   		
   		//积分明细
   		function integralDetail(userId){
   			$('<div/>').dialog({
				href : '${appPath}/integralOil/integralDetail.action?userId='+userId,
				width :880,
				height : 400,
				modal : true,
				title : '积分明细',
				onClose : function() {
					$(this).dialog('destroy');
				}
			});
   		}
   		
   		//某个类别下的积分明细
   		function integralCateDetail(userId,entityCategory){
   			$('<div/>').dialog({
				href : '${appPath}/integralOil/integralCateDetail.action?userId='+userId+'&entityCategory='+entityCategory,
				width :880,
				height : 400,
				modal : true,
				title : '积分明细',
				onClose : function() {
					$(this).dialog('destroy');
				}
			});
   		}
   		
   		//积分兑换
   		function integralExchange(userId){
   			$('<div/>').dialog({
   				id : 'add_dialog',
				href : '${appPath}/integralOil/integralOilExchange.action?userId='+userId,
				width :500,
				height : 450,
				modal : true,
				title : '积分兑换',
				buttons : [ {
   					text : '增加',
   					iconCls : 'icon-add',
   					handler : function() {
   						var num = document.getElementById('prizeNum');
			    		var person = document.getElementById('consignee');
			    		var phone = document.getElementById('mobilePhone');
			    		var address = document.getElementById('address');
   						$('#integral_exchange_add_form').form('submit', {
   							url : '${appPath}/integral/saveIntend.action',
   							onSubmit:function(){
   								if(!$('#integral_exchange_add_form').form('validate')){
   									$.messager.alert('提示信息','输入项校验失败,不能提交表单!','error');
   									return false ;		//当表单验证不通过的时候 必须要return false 
   								}
   								var nums = $('#integral_exchange_add_form').find('#prizeNums').val();
   								var num = $('#integral_exchange_add_form').find('#prizeNum').val();
   								if(nums<num){
   									$.messager.alert('提示信息','库存数量不足，请重新申请!','error');
   									return false ;		//当表单验证不通过的时候 必须要return false 
   								}
   								var num = $('#integral_exchange_add_form').find('#prizeNum').val();
   								var excIntegral = $('#integral_exchange_add_form').find('#excIntegral').val();
   								var currentIntegral = $('#integral_exchange_add_form').find('#currentIntegral').val();
   								if(currentIntegral<(num*excIntegral)){
   									$.messager.alert('提示信息','积分不足，不能兑换','error');
   									return false ;		//当表单验证不通过的时候 必须要return false 
   								}
   							}, 
   							success : function(result) {
   								var r = $.parseJSON(result);
   								if (r.status) {
   									$("#add_dialog").dialog('destroy');
   									$('#sn_manage_datagrid').datagrid('load');
   								}
   								$.messager.show({
   									title : '提示',
   									msg : r.msg
   								});
   							},
   							error: function(robj) {
   								$.messager.show({
   									title : '提示',
   									msg : '生成失败！'
   								});
   							}
   					});
   				} 
   				}],
				onClose : function() {
					$(this).dialog('destroy');
				}
			});
   		}
   		function integral_manage_search_fn() {
			$('#integral_manage_datagrid').datagrid('load', serializeObject($('#integral_manage_search_form')));
		}
		function cleanForm() {
			$('#integral_manage_search_form input[class="inval"]').val('');
			$('#integral_manage_datagrid').datagrid('load', {});
		}
	</script>
	<div class="searchDiv">
		<form action="javascript:void(0);" id="integral_manage_search_form" >
			<table class="form" style="height:10%;">
				<tr>
					<td class="tt" width="80">用户名</td>
					<td width="160"><input class="inval" name="cusername" style="width:150px;"></td>
					<td class="tt" width="80">手机号</td>
					<td width="160"><input class="inval" name="cmobilephone" style="width:150px;"></td>
					<td class="tt" width="80">身份证</td>
					<td width="160"><input class="inval" name="cardno" style="width:150px;"></td>
					<td class="tt" width="80">用户类型</td>
					<td width="160">
					<select id="cc" class="easyui-combobox" name="cmembertype" style="width:150px;">
					    <option value="2">匿名</option>
					    <!-- <option value="1">企业</option> -->
					    <option value="0">个人</option>
					</select>
					</td>
				</tr>
				<tr align="center">
					<td colspan="8">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="integral_manage_search_fn();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" onclick="cleanForm();">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div  fit ="true" data-options="region:'center',border:false" style="height:90%;">
		<table id="integral_manage_datagrid"></table>
	</div>
</body>
</html>
