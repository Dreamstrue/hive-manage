<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<script type="text/javascript">
	$(function() {
		$('#layout_west_tree').tree({
			url : '${pageContext.request.contextPath}/menu/myMenu.action',
			parentField : 'pid',
			lines : true,
			onClick : function(node) {
				var url;
				if (node.attributes.url) {
				//		url = '${pageContext.request.contextPath}' + node.attributes.url+'?id='+node.id;
					url = node.attributes.url+'?id='+node.id+'&pid='+node.attributes.pid;  //后面的参数不可以删除，因为在后面的业务中  当点击树节点进行页面跳转时需要这个id参数。
				//	alert(url);
					if (url.indexOf('http://') > -1) {
						//url =  node.attributes.url+"?sessionId=${pageContext.session.id}";
					}else{//使用相对路径时，自动加上前缀
						url = '<%=basePath%>' + url;
					}
				} else {
					//url = '${pageContext.request.contextPath}/security/blank.action';
					//$.messager.alert('系统提示','【'+node.text+'】页面正在升级，请稍后再试...');
					return;
				}
				//|| url.indexOf('http://') > -1
				if (url.indexOf('druid') > -1 || url.indexOf('http://') > -1) {/*要查看连接池监控页面*/
					layout_center_addTabFun({
						title : node.text,
						closable : true,
						iconCls : node.iconCls,
						content : '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>'
					});
				} else {
					layout_center_addTabFun({
						title : node.text,
						closable : true,
						iconCls : node.iconCls,
						href : url
					});
				}
			},
			/**add by yanghui 2013-10-22   菜单树加载完成后，除根节点外，其他节点不自动展开   begin */
			onLoadSuccess:function(node,data){
				var root = $('#layout_west_tree').tree('getRoot');
				var children = $('#layout_west_tree').tree('getChildren',root.target);
				for(var i=0;i<children.length;i++){
					 $('#layout_west_tree').tree('collapse',children[i].target);
				}
			}
			/**add by yanghui 2013-10-22   菜单树加载完成后，除根节点外，其他节点不自动展开   end */
		});

	});
	
	// 刷新菜单树
	function reloadWestTree() {
		$('#layout_west_tree').tree({
			url : '${pageContext.request.contextPath}/menu/myMenu.action',
			parentField : 'pid',
			lines : true
		});
	}
</script>
<div class="easyui-accordion" data-options="fit:true,border:false" style="OVERFLOW-Y: auto; OVERFLOW-X:hidden">
	<ul id="layout_west_tree"></ul>
</div>