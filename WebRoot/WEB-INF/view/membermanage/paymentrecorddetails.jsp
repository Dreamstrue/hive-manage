<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<jsp:include page="../common/inc.jsp"></jsp:include>
<SCRIPT src="${pageContext.request.contextPath}/resources/js/jquery.form.js" type=text/javascript></SCRIPT>
<script type="text/javascript"><!--
	var iconData = [ {
		value : '',
		text : '默认'
	}, {
		value : 'icon-add',
		text : 'icon-add'
	}, {
		value : 'icon-edit',
		text : 'icon-edit'
	}, {
		value : 'icon-remove',
		text : 'icon-remove'
	}, {
		value : 'icon-save',
		text : 'icon-save'
	}, {
		value : 'icon-cut',
		text : 'icon-cut'
	}, {
		value : 'icon-ok',
		text : 'icon-ok'
	}, {
		value : 'icon-no',
		text : 'icon-no'
	}, {
		value : 'icon-cancel',
		text : 'icon-cancel'
	}, {
		value : 'icon-reload',
		text : 'icon-reload'
	}, {
		value : 'icon-search',
		text : 'icon-search'
	}, {
		value : 'icon-print',
		text : 'icon-print'
	}, {
		value : 'icon-help',
		text : 'icon-help'
	}, {
		value : 'icon-undo',
		text : 'icon-undo'
	}, {
		value : 'icon-redo',
		text : 'icon-redo'
	}, {
		value : 'icon-back',
		text : 'icon-back'
	}, {
		value : 'icon-sum',
		text : 'icon-sum'
	}, {
		value : 'icon-tip',
		text : 'icon-tip'
	}, {
		value : 'icon-pie',
		text : 'icon-pie'
	}, {
		value : 'icon-map',
		text : 'icon-map'
	}, {
		value : 'icon-tableErr',
		text : 'icon-tableErr'
	}, {
		value : 'icon-table',
		text : 'icon-table'
	} ];
	
	$(function() {
		$('#member_manage_grid').datagrid({
				url : '${appPath}/membermanage/listpaymentrecorddetails.action?memberid=${param.memberid}&membername=${param.membername}',
				fit : true,
				fitColumns : true,
				border : false,
				pagination : true,
				striped:true,
				rownumbers : true,
				idField : 'npayrecid',
				pageSize : 20,
				pageList : [10, 20, 30, 40, 50, 60],
				sortName : 'dpaytime',
				sortOrder : 'desc',
				singleSelect:false,
				nowrap : false,
				toolbar : [ {
					text : '添加缴费',
					iconCls : 'icon-add',
					handler : function() {
						paymentInput();
					}
				},{
					text : '返回列表',
					iconCls : 'icon-reload',
					handler : function() {
						history.go(-1);
					}
				}],
				columns : [ [{
					field : 'npayrecid',
					title : '会员名称',
					width : 30,
					formatter : function (){
						return decodeURI('${param.membername}');
					}
					
				},{
					field : 'npaysum',
					title : '缴费金额',
					width : 30,
				},{
					field : 'npaytype',
					title : '缴费方式',
					width : 40,
					formatter : function(value, row, index) {
						if(value == 1)
						{
							return "现金";
						}else if(value == 2)
						{
							return "网银";
						}else
						{
							return "支票";
						}
					}
				},{
					field : 'dpaytime',
					title : '缴费时间',
					width : 40
				},{
					field : 'cremark',
					title : '备注',
					width : 40
				}
				]]
			});
	});
	
function paymentInput(){
		$('#memberIds').val('${param.memberid}');
		$('#memberNames').text(decodeURI('${param.membername}'));
		Open_Dialog(); 
	
}

function paymentAdd(){
	var param=$('#form1').formSerialize();
	$.post('${appPath}/membermanage/paymentadd.action',param, function (re) {
        if (re.status) {
        	$.messager.alert('提示',re.msg);
        	$('#mydialog').dialog('close');
        	$('#member_manage_grid').datagrid('load');
        }
    }, 'json');
    $('#member_manage_grid').datagrid('unselectAll');
}

function Open_Dialog() {
	$('#mydialog').show(); 
	$('#mydialog').dialog({ 
		collapsible: true, 
		maximizable: true, 
		modal:true,
		/**
		toolbar: [
			{ 
			text: '添加', 
			iconCls: 'icon-add', 
			handler: function() { 
			alert('添加数据') 
			} 
			},{ 
			text: '保存', 
			iconCls: 'icon-save', 
			handler: function() { 
			alert('保存数据')
			} 
			}
		], 
		*/
		buttons: [
			{
			text: '提交', 
			iconCls: 'icon-ok', 
			handler: paymentAdd 
			}, 
			{
			text: '取消', 
			handler: function() {
			$('#mydialog').dialog('close'); 
			} 
			}
		] 
	}); 
} 
--></script>
<table id="member_manage_grid"></table>
<!-- 弹出窗口：添加数据 -->  
<div id="win" title="My Window" style="width:300px;height:100px;">
    Some Content.
</div>
<div id="menu_manage_contextMenu" class="easyui-menu" style="width:120px;display: none;">
	<div onclick="menu_manage_add_fn();" data-options="iconCls:'icon-add'">增加</div>
	<div onclick="menu_manage_edit_fn();" data-options="iconCls:'icon-edit'">编辑</div>
	<div onclick="menu_manage_del_fn();" data-options="iconCls:'icon-remove'">删除</div>
</div>
<div id="mydialog" style="display:none;padding:5px;width:400px;height:300px;" title="会员缴费"> 
	<form id="form1" action="">
	<input type="hidden" id="memberIds" name="memberIds" value="" />
		<table>
		<tr>
		<td>缴费会员：</td><td><textarea id="memberNames" name='memberNames' readonly='readonly'></textarea></td>
		</tr>
		<tr>
		<td>缴费方式：</td><td><select id="paytype" name="paytype">
				  <option value ="1">现金</option>
				  <option value ="2">网银</option>
				  <option value ="3">支票</option>
				</select></td>
		</tr>
		<tr>
		<td>缴费金额￥：</td><td><input id="paysum" name="paysum" type="text" /></td>
		</tr>
		<tr>
		<td>备注：</td><td><textarea id="remark" name="remark"></textarea> </td>
		</tr>
		</table>
	</form>
</div> 
