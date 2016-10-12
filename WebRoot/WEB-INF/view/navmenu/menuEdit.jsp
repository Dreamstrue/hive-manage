<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
		$(function() {
	});
</script>
<div align="center" style="width: 100%">
	<form id="navmenu_manage_edit_form" method="post" style="width: 100%">
		<input type="hidden" name="id" value="${vo.id}">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th>栏目名称</th>
				<td>
					<input name="text" value="${vo.text}" class="easyui-validatebox" data-options="required:true,validType:'CHECK_D'"  style="width: 170px;"/>
				</td> 
			</tr>
			<tr>
				<th>栏目地址</th>
				<td>
					<input name="menuHref" value="${vo.menuHref}" class="easyui-validatebox" data-options="required:true"  style="width: 170px;"/>
				</td> 
			</tr>
			<tr> 
				<th>菜单路径</th>
				<td>
					<input name="url" value="${vo.url }" class="easyui-validatebox" data-options="required:false"  style="width: 170px;"/>
				</td> 
			</tr>
			<%-- <tr>
				<th>是否可用</th>
				<td align="center">
					<c:if test="${vo.valid=='1'}">
						<input type='radio' name='valid' class='np' value='1' checked>是&nbsp;&nbsp;&nbsp;<input type='radio' name='valid' class='np' value='0'>否
					</c:if>
					<c:if test="${vo.valid=='0'}">
						<input type='radio' name='valid' class='np' value='1'>是&nbsp;&nbsp;&nbsp;<input type='radio' name='valid' class='np' value='0' checked>否
					</c:if>
				</td>
			</tr> --%>
			<tr>
				<th>上级菜单</th>
				<td>
					<input id="pid" name="pid" value="${vo.pid}" class="easyui-combotree" data-options="url:'${pageContext.request.contextPath}/navMenu/myNavMenu.action',parentField : 'pid',lines : true" style="width:170px;" />
				</td>
			</tr>
			<tr>
				<th>排序</th>
				<td>
					<input name="sortOrder" value="${vo.sortOrder}" class="easyui-numberspinner" data-options="min:1,max:999,editable:false,required:true" value="1" style="width: 175px;" />
				</td>
			</tr>
			<tr> 
				<th>对应实体</th>
				<td>
					<input name="conentity" value="${vo.conentity}" class="easyui-validatebox" data-options="required:false"  style="width: 170px;"/>
				</td> 
			</tr>
		</table>
	</form>
</div>
