<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">

	$(function() {
		/** 控制部门只能单选，而且只能选择最下级部门    add by yanghui 2014-01-09 begin */
		$('#department_manage_pid').combotree({
			onCheck:function(node,checked){
				var t = $('#department_manage_pid').combotree('tree');// 得到树对象
				var nodes = t.tree('getChecked');	// get checked nodes
				if(nodes.length==2){
					$.messager.show({
						title:'提示',
						msg:'只能选择一个部门',
						timeout:2000
					});
					t.tree('uncheck',node.target);
				}
			}
		});
		/** 控制部门只能单选，而且只能选择最下级部门    add by yanghui 2014-01-09 end */
		
		$('#user_manage_role_datagrid').combogrid({
			multiple : false,
			nowrap : false,
			url : '${pageContext.request.contextPath}/role/datagrid.action',
			panelWidth : 450,
			panelHeight : 200,
			idField : 'id',
			textField : 'name',
			pagination : true,
			fitColumns : true,
			rownumbers : true,
			editable : true,
			mode : 'remote',
			delay : 500,
			pageSize : 5,
			pageList : [ 5, 10 ],
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
			}, {
				field : 'name',
				title : '角色名称',
				width : 150
			} ] ]
		});
	});
	
</script>
<div align="center" style="width: 100%">
	<form id="user_manage_add_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th style="width: 70px;">用户名</th>
				<td>
					<input name="username" class="easyui-validatebox" data-options="required:true"  style="width: 170px;" />
				</td>
			</tr>
			<tr>
				<th>密码</th>
				<td>
					<input id="password" name="password" type="password" class="easyui-validatebox" data-options="required:true"  style="width: 170px;" />
				</td>
			</tr>
			<tr>
				<th>确认密码</th>
				<td>
					<input id="repassword" name="repassword" type="password" class="easyui-validatebox" required="required" validType="eqPwd['#password']" style="width: 170px;" />
				</td>
			</tr>
			<tr>
				<th style="width: 70px;">姓名</th>
				<td>
					<input name="fullname" style="width: 170px;" />
				</td>
			</tr>
			<tr>
				<th style="width: 85px;">是否管理员</th>
				<td>
					<input  type="radio" class="cdown" name="admin" value="1"/>是&nbsp;&nbsp;&nbsp;<input  type="radio" class="cdown" name="admin" value="0" checked="checked"/>否
				</td>
			</tr>
			<tr>
				<th style="width: 70px;">手机号</th>
				<td>
					<input name="mobilephone" style="width: 170px;" />
				</td>
			</tr>
			<tr>
				<th style="width: 70px;">邮箱</th>
				<td>
					<input name="email" style="width: 170px;" />
				</td>
			</tr>
			<tr>
				<th>所属部门</th>
				<td>
					<input id="department_manage_pid" name="departmentId" class="easyui-combotree" data-options="url:'${appPath}/department/treegrid.action',parentField : 'pid',lines : true,cascadeCheck:false,checkbox:true,multiple:true,onlyLeafCheck:true,required:true" style="width:175px;" />
				</td>
			</tr>
		</table>
	</form>
</div>