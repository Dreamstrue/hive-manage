<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>

<script type="text/javascript">
		$(function() {
			//修改时，新闻图片的选择框隐藏，当对应的图片被删除后，在出现。
			$('#picImg').hide();
			$('#imageExist').val('yes');
			
			
			
			$('#special_info_datagrid').datagrid({
   				url:'${pageContext.request.contextPath}/special/dataGrid.action?pid='+${vo.id},
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
   					field:'text',
   					title:'专题栏目',
   					width:220
   				}]],
   				columns:[[{
   					field:'id',
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
   				}/* ,{
   					field:'cauditstatus',
   					title:'审核状态',
   					width:80,
   					formatter:function(value,row,index){
   						return back_zh_CN(value);
   					}
   				} */,{
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
   						if(row.cvalid=='1'){//可用
   							if(row.cauditstatus =='1'){
   								return formatString('<img onclick="special_info_manage_del_fn(\'{0}\');" src="{1}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
   							}else {
   								return formatString('<img alt="修改" onclick="special_info_manage_edit_fn(\'{0}\');" src="{1}"/>&nbsp;<img onclick="special_info_manage_del_fn(\'{2}\');" src="{3}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/pencil.png', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/cancel.png');
   							}
   						}else{
   							return formatString('<img onclick="special_info_manage_back_fn(\'{0}\');" src="{1}"/>', row.id, '${pageContext.request.contextPath}/resources/images/extjs_icons/tab_go.png');
   						}
   					}
   				}]],
   				toolbar:[{
   					text:'新增栏目',
   					iconCls:'icon-add',
   					handler:function(){
   						special_info_manage_add_fn();
   					}
   				},'-',{
   					text:'刷新',
   					iconCls:'icon-reload',
   					handler:function(){
   						$('#special_info_datagrid').datagrid('reload');
   					}
   				}]
   			});
		});
		
		
		function special_info_manage_add_fn(){
   			$('<div/>').dialog({
				href:'${pageContext.request.contextPath}/special/addSub.action?pid='+${vo.id},
				width:390,
				height:190,
				modal:true,
				title:'新增栏目',
				buttons:[{
					text:'增加',
					iconCls:'icon-add',
					handler:function(){
						var d = $(this).closest('.window-body');
						$('#special_info_add_form').form('submit',{
							url:'${pageContext.request.contextPath}/special/insert.action?addType=sub',
							success:function(result){
								var r = $.parseJSON(result);
								if(r.status){
									d.dialog('destroy');
									$('#special_info_datagrid').datagrid('reload');
								}
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
							}
						});
					}
				}],
				onClose:function(){
					$(this).dialog('destroy');
				}
			});
   		}
   		
   		
   		   function special_info_manage_edit_fn(id){
   			$('<div/>').dialog({
				href:'${pageContext.request.contextPath}/special/editSub.action?id='+id,
				width:490,
				height:220,
				modal:true,
				title:'修改栏目',
				buttons:[{
					text:'提交',
					iconCls:'icon-ok',
					handler:function(){
						var d = $(this).closest('.window-body');
						$('#special_info_manage_edit_form').form('submit',{
							url:'${pageContext.request.contextPath}/special/updateSub.action',
							success:function(result){
								var r = $.parseJSON(result);
								if(r.status){
									d.dialog('destroy');
									$('#special_info_datagrid').datagrid('reload');
								}
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
							}
						});
					}
				}],
				onClose:function(){
					$(this).dialog('destroy');
				}
			});
   		}
   		
   		
   		//删除
   		function special_info_manage_del_fn(id){
   			if(id!=undefined){
   			   $('#special_info_datagrid').datagrid('selectRecord',id);
   				var record = $('#special_info_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要删除【'+record.text+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/special/delete.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#special_info_datagrid').datagrid('reload');
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
   		function special_info_manage_back_fn(id){
   			if(id!=undefined){
   			   $('#special_info_datagrid').datagrid('selectRecord',id);
   				var record = $('#special_info_datagrid').datagrid('getSelected');
   				$.messager.confirm('提示','您确定要恢复【'+record.text+'】?',function(b){
   					if(b){
   						var url = '${pageContext.request.contextPath}/special/back.action';
   						$.post(url,{id:id},function(data){
   							if(data.status){
   								$('#special_info_datagrid').datagrid('reload');
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
   		
   		
   		function delAnnex(id,type){
		$.messager.confirm('提示','您确定要删除该附件?',function(b){
			if(b){
				$('#'+id).hide();
				if(type=='image'){
					$('#picImg').show();
					$('#imageExist').val('no');
				}
				/* var url = '${appPath}/annex/delete.action';
							$.post(url,{id:id},function(data){
							},"json"); */
			}
		});
	}

</script>
<div align="left" style="width: 100%">
	<form  id="special_info_edit_form" method="post" enctype="multipart/form-data">
	<input id="id" name="id" type="hidden" value="${vo.id }">
		<table >
			<tr>
				<th class="tt">专题名称：</th>
				<td>
					<input name="text"  id="text" value="${vo.text}" style="width: 250px;"/>&#160;<font color="#FF0000" size="2">*必填项</font>
				</td>
			</tr>
			<tr>
				<th class="tt">专题图片：</th>
				<td>
					<div id="picImg">
						<input  type="file" name="imgfile"  id="picture"  style="width: 250px;"/>&#160;<font color="#FF0000" size="2">*必填项</font>
					</div>
					<div id="${img.id}">
							<a href="${appPath}/annex/download.action?id=${img.id}"><img  src="${appPath}/annex/loadPic.action?picPath=${img.cfilepath}" width="100" height="80"/></a>&nbsp;&nbsp;<input type="button" value="删除" onclick="delAnnex(${img.id},'image')">
							<!-- 定义一个字段保存图片的ID,当新上传图片时，通过这个ID把原来的图片设置为不可用 -->
							<input type="hidden" name="virtualPid" id="virtualPid" value="${img.id}"/>
					</div>
					<!-- 自定义一个字段，方便后台操作新闻图片修改  -->
					<input type="hidden" name="imageExist" id="imageExist"/>
					
				</td>
			</tr>
			<tr>
				<th class="tt">排序：</th>
				<td>
					<input name="isortorder" class="easyui-numberspinner" data-options="min:1,max:999,editable:false,required:true" value="${vo.isortorder}" style="width: 250px;" />
					</td>
				</tr>
			</table>
		</form>
	</div>
<div style="height:220px;width:100%;">
		<table id="special_info_datagrid"></table>
</div>
