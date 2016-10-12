<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>政策法规管理</title>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<style type="text/css">
body{margin:0px;padding:0px;}
.searchDiv{
	overflow: hidden;
	height: 30px; 
	border-bottom: 1px solid #99CCFF;
}
.tt{
	font-size: 12px;
	text-align: right;
}
</style>
</head>

<body>
	<div class="searchDiv">
		<form action="javascript:void(0);" id="policy_all_manage_search_form" style="width: 100%">
			<table class="form" style="width: 100%;height:30px;">
				<tr>
					<td class="tt" width="80">标题</td>
					<td>
						<input class="inval" name="keys" style="width: 215px;" />
						<input type="button" class="easyui-linkbutton" value="查询" onclick="policy_all_manage_search_fn()">
						<input type="button" class="easyui-linkbutton" value="重置" onclick="policy_all_manage_clean_form_fn()">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div style="height:420px;width:100%;">
		<table id="PolicyLaw_all_datagrid"></table>
	</div>
	<script type="text/javascript">
	
   		$(function(){
   			$('#PolicyLaw_all_datagrid').datagrid({
   				url:'${pageContext.request.contextPath}/policy/dataGrid.action',
   				idField:'id',
   				fit:true,
   				fitColumns:true,
   				pagination:true,
   				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
   				rownumbers:true,
   				border:false,
   				animate:false,
   				frozenColumns:[[{
   					field:'clawname',
   					title:'标题',
   					width:400
   				}]],
   				columns:[[{
   					field:'nlawid',
   					title:'ID',
   					width:50,
   					hidden:true
   				},{
   					field:'ncreateid',
   					title:'创建人',
   					width:80,
   					hidden:true
   				},{
   					field:'dcreatetime',
   					title:'创建时间',
   					width:100/* ,
   					formatter:function(value,row,index){
   						return formatDate(value);
   					} */
   				},{
   					field:'cauditstatus',
   					title:'审核状态',
   					width:80,
   					formatter:function(value,row,index){
   						return back_zh_CN(value);
   					}
   				},{
   					field:'cvalid',
   					title:'是否可用',
   					width:50,
   					formatter:function(value,row,index){
   						if(value=='1'){
   							return '<font>是</font>';
   						}else if(value=='0'){
							return '<font color="red">否</font>';					
   						}
   					}
   				},{
   					field:'action',
   					title:'动作',
   					width:60,
   					formatter:function(value,row,index){
   						if(row.cauditstatus =='1'){
   							//审核通过
   							if(row.cvalid=='1'){//可用
   								return formatString('<img onclick="policy_manage_del_fn(\'{0}\');" src="{1}"/>&nbsp;<img title="查看" onclick="policy_all_manage_detail_fn(\'{2}\');" src="{3}"/>', row.nlawid, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png',row.nlawid, '${pageContext.request.contextPath}/resources/images/extjs_icons/table/table.png');
   							}
   						}else if(row.cauditstatus=='0'){
   							return formatString('<img title="修改" onclick="policy_all_manage_edit_fn(\'{0}\');" src="{1}"/>&nbsp;<img onclick="policy_manage_approve_fn(\'{2}\');" src="{3}"/>&nbsp;<img onclick="policy_manage_del_fn(\'{4}\');" src="{5}"/>', row.nlawid, '${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png', row.nlawid, '${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png', row.nlawid, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
   						}else {
   							return formatString('<img title="修改" onclick="policy_all_manage_edit_fn(\'{0}\');" src="{1}"/>&nbsp;<img onclick="policy_manage_del_fn(\'{2}\');" src="{3}"/>', row.nlawid, '${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png', row.nlawid, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
   						}
   							
   					}
   				}]],
   				toolbar:[{
   					text:'新增',
   					iconCls:'icon-add',
   					handler:function(){
   						policy_all_manage_add_fn();
   					}
   				},'-',{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#PolicyLaw_all_datagrid').datagrid('reload');
   					}
   				}]
   			});
   		});
   		
   		
   		function policy_all_manage_add_fn(){
   			location.href = '${pageContext.request.contextPath}/policy/alladd.action?id='+'${backId}';
   		}
   		
   		//修改
   		function policy_all_manage_edit_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/policy/edit.action?id='+id+'&opType=update&backId='+'${backId}';
   			}
   		}
   		
   		function policy_all_manage_detail_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/policy/detail.action?id='+id+'&backId='+'${backId}';
   			}
   		}
   		
   		//审核
   		function policy_manage_approve_fn(id){
   			if(id!=undefined){
   				location.href = '${pageContext.request.contextPath}/policy/approve.action?id='+id+'&opType=approve&backId='+'${backId}';
   			}
   		}
   		
   		
   		
   		//删除
   		function policy_manage_del_fn(id){
   			if(id!=undefined){
   			   $('#PolicyLaw_all_datagrid').datagrid('selectRecord',id);
   				var record = $('#PolicyLaw_all_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要删除【'+record.clawname+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/policy/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#PolicyLaw_all_datagrid').datagrid('reload');
   							}
   							$.messager.show({
   								msg:data.msg,
   								title:'提示'
   							});
   						},"json");
   					}
   				});
   			}
   				
   		}
   		
   		
   		/*重新设置为可用状态*/
   		function policy_manage_back_fn(id){
   			if(id!=undefined){
   			   $('#PolicyLaw_all_datagrid').datagrid('selectRecord',id);
   				var record = $('#PolicyLaw_all_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定恢复【'+record.clawname+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/policy/back.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#PolicyLaw_all_datagrid').datagrid('reload');
   							}
   							$.messager.show({
   								msg:data.msg,
   								title:'提示'
   							});
   						},"json");
   					}
   				});
   			}
   		}
   		
   		
   		function policy_all_manage_search_fn() {
			$('#PolicyLaw_all_datagrid').datagrid('load', serializeObject($('#policy_all_manage_search_form')));
		}
		function policy_all_manage_clean_form_fn() {
			$('#policy_all_manage_search_form input[class="inval"]').val('');
			$('#PolicyLaw_all_datagrid').datagrid('load', {});
		}
   		
   		
   	//格式化日期样式
	
		
		
	</script>
</body>
</html>
