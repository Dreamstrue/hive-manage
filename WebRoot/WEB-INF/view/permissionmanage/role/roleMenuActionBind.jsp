<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/integrity.css" type="text/css"></link>
<script type="text/javascript">
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
	
	var firstLoad = true; // 标识是否第一次进入页面
	$(function() {
		$('#menu_manage_treegrid').treegrid({
			url : '${appPath}/role/menutreegrid.action?roleId=' + ${roleId},
			idField : 'id',
			treeField : 'text',
			parentField : 'pid',
			loadMsg:'正在加载数据，请稍后...', // 这个提示信息只有点“刷新”按钮时才会出来，第一次加载页面时并不会出来（实际上是一闪而过），所以下面通过 onBeforeLoad 手动弹出提示
			fit : true,
			fitColumns : false,
			border : false,
			nowrap:false, // 当列的内容超过设置的宽度时，自动换行
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				width : 150,
				hidden : true
			}, {
				field : 'text',
				title : '菜单名称',
				width : 200
			} ] ],
			columns : [ [ {
				field : 'viewCheckStr',
				title : '菜单访问权限',
				align:'center',
				width : 80,
				formatter : function(value, row, index) {
					return formatString(value);
				}
			}, {
				field : 'actionCheckStr',
				title : '表单动作权限',
				align:'left',
				width : 230,
				formatter : function(value, row, index) {
				//<input type="checkbox">增加<input type="checkbox">修改<input type="checkbox">删除
					return formatString(value);
				}
			}, {
				field : 'pid',
				title : '上级菜单ID',
				width : 150,
				hidden : true
			},{
				field : 'url',
				title : '全选',
				align:'center',
				width : 50,
				formatter : function(value, row, index) {
				//alert(row.id);
					return formatString('<input type="checkbox" id="menu_' + row.id + '" onclick="checkAll(\'menu_' + row.id + '\')" >');
				}
			} ] ],
			toolbar : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					role_menu_action_bind_fn();
				}
			}, '-', {
				text : '展开',
				iconCls : 'icon-redo',
				handler : function() {
					var node = $('#menu_manage_treegrid').treegrid('getSelected');
					if (node) {
						$('#menu_manage_treegrid').treegrid('expandAll', node.cid);
					} else {
						$('#menu_manage_treegrid').treegrid('expandAll');
					}
				}
			}, '-', {
				text : '折叠',
				iconCls : 'icon-undo',
				handler : function() {
					var node = $('#menu_manage_treegrid').treegrid('getSelected');
					if (node) {
						$('#menu_manage_treegrid').treegrid('collapseAll', node.cid);
					} else {
						$('#menu_manage_treegrid').treegrid('collapseAll');
					}
				}
			}, '-', {
				text : '刷新',
				iconCls : 'icon-reload',
				handler : function() {
					$('#menu_manage_treegrid').treegrid('reload');
				}
			}, '-', {
				text : ' <b>当前角色：${roleName}</b>',
				iconCls : ''
			}, {
				text : '返回角色列表',
				iconCls : '',
				handler : function() {
					location.href = '${pageContext.request.contextPath}/role/manage.action';
				}
			} ],    
        	onBeforeLoad:function(row,param){  
        		// 只有第一次加载页面时候弹出该提示，点击“刷新”按钮时会正常弹出上面配置的 loadMsg，就不需要再手动弹出提示了
        		if(firstLoad) {
		            $.messager.show({
						title:'提示',
						msg: "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style='font-size:14px;'>正在加载数据，请稍后...</span>",
						showType:'fade',
						timeout:3500, // 单位：毫秒。只要鼠标停留在提示框上，它就一直显示，从鼠标离开提示框开始计时（设置为 0 就不会自动消失了，必须手动关闭） 
						style:{
							left:250,
							top:document.body.scrollTop+document.documentElement.scrollTop + 216,
							bottom:''
						}
					});  
					firstLoad = false;
        		}
        	}
		});
	});

	/**
     * 这个菜单树上共有三种复选框，id 拼接规则分别如下（可通过 firebug 查看元素按钮查看）：
     * 菜单复选框：<input type="checkbox" id="140" onclick="checkAction(this)" name="menu_331_view" checked="checked"> --> id 里实际上存的是父菜单的 id，name 中间的数字是当前行菜单的 id
     * 动作复选框：<input type="checkbox" value="2" name="menu_331" disabled="disabled"> --> value 里存的是动作值，name 后面的数字是当前行菜单的 id
     * 全选复选框：<input type="checkbox" onclick="checkAll('menu_331')" id="menu_331"> --> id 里存的是当前行菜单的 id
	 */
	
	function menu_manage_add_fn() {
		$('<div/>').dialog({
			href : '${appPath}/menu/add.action',
			width : 300,
			height : 250,
			modal : true,
			title : '菜单添加',
			buttons : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#menu_manage_add_form').form('submit', {
						url : '${appPath}/menu/insert.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								d.dialog('destroy');
								$('#menu_manage_treegrid').treegrid('reload');
								//$('#layout_west_tree').tree('reload');  // 不知为何不执行，下面用了一种变通的方式
								window.parent.reloadWestTree();  
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
			}
		});
	}
	
	// 角色菜单动作绑定
	function role_menu_action_bind_fn() {
		var chk_menuView = []; 
		
		var chk_menuAction = []; 
		// 为了模仿实现 map 功能，定义两个数组，一个作为 key（放的是菜单 menuId，形如 menu_331），一个作为 value（放的是动作值 actionValue，形如 2），根据索引一一对应
		var map_key =[];    
		var map_value =[]; 
		   
		// 循环所有选中的复选框
	  	$('input[name^="menu"]:checked').each(function(){  
		  	var menuId = $(this).attr("name"); // 菜单 id
		  	
	  	  	if (menuId.indexOf("view") != -1) { // 菜单访问权限处理
	  	  		chk_menuView.push(menuId);
	  	  	} else { // 表单动作权限处理
	  	  		var menuId_index = jQuery.inArray(menuId, map_key); // 判断该菜单 id 是否已经存在于 “Map” 中
		  		var actionValue = $(this).val(); // 动作值
			  	if (menuId_index != -1) { // 已存在，只改变 value，key 不变
			  		map_value[menuId_index] = map_value[menuId_index]|parseInt(actionValue); // 把值进行运算后覆盖
			  	} else { // 不存在，key、value 都放进去
		  			map_key.push(menuId);
		  			map_value.push(actionValue);
			  	}
	  	  	}
	  	});  
	  	// 所有循环执行完之后再组装最后需要的参数
	  	for (var i = 0; i < map_key.length; i++) {
  			chk_menuAction.push(map_key[i] + "=" + map_value[i]); // 把 “Map” 遍历组合成一个字符串放到数组中  
 		}
	  	
	  /** 取消所有权限时候，就是没有任何选择了
	  if (chk_menuAction.length == 0) {
	  	alert("你还没有选择任何内容！");
	  	return false;
	  }
	  */
		$.ajax({
			url : '${pageContext.request.contextPath}/role/roleMenuActionBind.action',
			data : {
				roleId : ${roleId},
				menuViews : chk_menuView.join(','),
				menuActionValus : chk_menuAction.join(',')
			},
			dataType : 'json',
			success : function(result) {
				if (result.status) {
					$('#role_manage_datagrid').datagrid('load');
					$('#role_manage_datagrid').datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
				}
				$.messager.show({
					title : '提示',
					msg : result.msg
				});
			}
		});
	}
	
	// 选择动作
	function checkAction(obj) {
		var name = obj.name; // 形如：menu_331_view
		//var pid = obj.value; // 形如：166（借用 checkbox 的 value 属性存放 pid）
		var pid = obj.id; // 形如：166（借用 checkbox 的 id 属性存放 pid）
		name = name.substring(0, name.indexOf("_view")); // 形如：menu_331
		var id = name.substring(name.indexOf("_") + 1); // 形如：331 --> 自己的 id
		if (!obj.checked) {
			$('input[name="' + name + '"]').each(function(){ 
				$(this).attr("disabled", true);
				$(this).attr("checked", false);
			});
			
			// 当子菜单选中个数为0时，把父菜单的勾去掉
			//var checkNum = $('input[value="' + pid + '"]:checked').length; // 父菜单为 pid 的所有选中的菜单个数
			var checkNum = $('input[id="' + pid + '"]:checked').length; // 父菜单为 pid 的所有选中的菜单个数(之前借用的是 value 属性，但这有可能会跟动作 checkbox 的 value 值冲突：例如父菜单 id 为2，而添加动作的 value 也为2，那么就会影响这里得到的个数)
			if (checkNum == 0) {
				$('input[name="menu_' + pid + '_view"]').attr("checked", false);
			}
			
			// 如果取消的是文件夹，则把它所有孩子菜单的勾去掉(只处理第一级子菜单，非递归)
			$('input[id="' + id + '"]:checked').each(function(){ 
				$(this).attr("checked", false);
				
				var name = $(this).attr("name"); // 当前子菜单的 name，形如：menu_258_view
				name = name.substring(0, name.indexOf("_view")); // 形如：menu_258
				
				// 孩子菜单对应的动作也应该去掉
				$('input[name="' + name + '"]').each(function(){ 
					$(this).attr("disabled", true);
					$(this).attr("checked", false);
				});
				
				$("#" + name).attr("checked", false); // 把全选框的勾去掉
			});
		} else {
			$('input[name="' + name + '"]').each(function(){  
				$(this).attr("disabled", false);
			});
			
			// 选中子菜单时，把父菜单选中
			$('input[name="menu_' + pid + '_view"]').attr("checked", true);
		}	
	}
	
	// 全选
	function checkAll(menu_Id) { // menu_Id 形如：menu_331
		var checkAll = $("#" + menu_Id).attr("checked"); // 注意：就全选框是根据 id 获取的，其余的都是根据 name 获取
		
		var pid = $('input[name="' + menu_Id + '_view"]').attr("id"); // 对应父菜单 id
		
		// 访问权限处理
		if (checkAll == "checked") {
			$('input[name="' + menu_Id + '_view"]').attr("disabled", false);
			$('input[name="' + menu_Id + '_view"]').attr("checked", true);
			
			// 选中子菜单时，把父菜单选中
			$('input[name="menu_' + pid + '_view"]').attr("checked", true);
		} else {
			$('input[name="' + menu_Id + '_view"]').attr("checked", false);
			
			// 当子菜单选中个数为0时，把父菜单的勾去掉
			var checkNum = $('input[id="' + pid + '"]:checked').length; // 父菜单为 pid 的所有选中的菜单个数(之前借用的是 value 属性，但这有可能会跟动作 checkbox 的 value 值冲突：例如父菜单 id 为2，而添加动作的 value 也为2，那么就会影响这里得到的个数)
			if (checkNum == 0) {
				$('input[name="menu_' + pid + '_view"]').attr("checked", false);
			}
		}
		// 表单动作处理
		$('input[name="' + menu_Id + '"]').each(function(){ 
			if (checkAll == "checked") {
				// 表单动作处理
				$(this).attr("disabled", false);
				$(this).attr("checked", true);
			} else {
				$(this).attr("disabled", true);
				$(this).attr("checked", false);
			}
		});  
		  
		//return false;
		/**
		name = name.substring(0, name.indexOf("_view"));
		if (!obj.checked) {
			$('input[name="' + name + '"]').each(function(){ 
				$(this).attr("disabled", true);
				$(this).attr("checked", false);
			});
		} else {
			$('input[name="' + name + '"]').each(function(){  
				$(this).attr("disabled", false);
			});
		}	
		*/
	}
	
	function menu_manage_edit_fn(id) {
		if (id != undefined) {
			$('#menu_manage_treegrid').treegrid('select', id);
		}
		var node = $('#menu_manage_treegrid').treegrid('getSelected');
		$('<div/>').dialog({
			href : '${appPath}/menu/edit.action?id='+node.id,
			width : 300,
			height : 250,
			modal : true,
			title : '编辑菜单',
			buttons : [ {
				text : '确定',
				iconCls : 'icon-ok',
				handler : function() {
					var d = $(this).closest('.window-body');
					$('#menu_manage_edit_form').form('submit', {
						url : '${appPath}/menu/update.action',
						success : function(result) {
							var r = $.parseJSON(result);
							if (r.status) {
								d.dialog('destroy');
								$('#menu_manage_treegrid').treegrid('reload');
								//$('#layout_west_tree').tree('reload');  // 不知为何不执行，下面用了一种变通的方式
								window.parent.reloadWestTree(); 
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
			}
		});
	}
	
	
	function menu_manage_del_fn(id) {
		if (id != undefined) {
			$('#menu_manage_treegrid').treegrid('select', id);
		}
		var node = $('#menu_manage_treegrid').treegrid('getSelected');
		if (node) {
			$.messager.confirm('询问', '您确定要删除【' + node.text + '】？', function(b) {
				if (b) {
					$.post('${appPath}/menu/delete.action',{id:node.id},function(data){
						if (data.status) {
							$('#menu_manage_treegrid').treegrid('remove', node.id);
							//$('#layout_west_tree').tree('reload');  // 不知为何不执行，下面用了一种变通的方式
							window.parent.reloadWestTree();
						}
						$.messager.show({
							msg : data.msg,
							title : '提示'
						});
					},"json");
					
				}
			});
		}
	}
</script>
<%--
<div align="left" style="width:100%; height: 100%;">
<div id="div1" >当前位置：通知公告&nbsp;&gt;&nbsp;新增</div>
<div id="div2" align="right"><a id="btn" href="${pageContext.request.contextPath}/role/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<hr style="color: silver;border-style: solid;"/>
--%>
<div id="menu_manage_treegrid"></div>
<div id="menu_manage_contextMenu" class="easyui-menu" style="width:120px;display: none;">
	<div onclick="menu_manage_add_fn();" data-options="iconCls:'icon-add'">增加</div>
	<div onclick="menu_manage_edit_fn();" data-options="iconCls:'icon-edit'">编辑</div>
	<div onclick="menu_manage_del_fn();" data-options="iconCls:'icon-remove'">删除</div>
</div>