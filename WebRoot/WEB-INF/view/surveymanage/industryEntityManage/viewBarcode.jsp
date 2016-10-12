<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="center" style="width: 100%">
	<form id="user_manage_add_form"  method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%">
		<input type="hidden" id="id" name="id" value="${vo.id }">
			<tr>
				<th style="width: 120px;">实体名称：</th>
				<td>${vo.entityName}
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">查询路径：</th>
				<td width=300 style="word-break:break-all">${vo.queryPath }
                </td>
			</tr>
			<tr>
			    <th style="width: 120px;">二维码：</th>
				<td >
				<a href="#" onclick="viewQrcodeDetail('${vo.queryPath }')">
					<img src='${pageContext.request.contextPath}/industryEntityManage/viewBarcodeImg.action?id=${vo.id}&queryPath=${vo.queryPath}'/></br>
					   [扫描二维码评价]
				</a>
				</td>
			</tr>
		</table>
	</form>
</div>
