//查询所有有效的行业实体信息
var base_url = getRootPath()+"/";
/*
 * valId，textId是两个需要回传的值的参数
 * fun 是回调函数，如果是函数名的必须加(),比如回调函数名为test，则fun参数为'test()';
 */
function queryAllIndustryEntity(valId,textId,fun){
//	alert(base_url);
	$('<div/>').dialog({
		id : 'ine_dialog',
		href : base_url+'industryEntityManage/queryIndustryEntity.action',
		width : 650,
		height : 400,
		collapsible: true,
        maximizable: true,
        resizable: true,
		modal : false,
		title : '行业实体列表',
		buttons : [ {
			text : '提交',
			iconCls : 'icon-add',
			handler : function() {
				var row = getSelectedRow();
				if(valId!=''){
					$("#"+valId).val(row.id);
				}
				$("#"+textId).textbox('setValue',row.entityName);
				if(fun!=undefined){
					eval(fun);//执行回调函数
				}
				$("#ine_dialog").dialog('destroy');
		} 
		}],
		onClose : function() {
			$(this).dialog('destroy');
		}
	});
}