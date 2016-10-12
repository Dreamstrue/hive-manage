<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="center" style="width: 100%">
	<form id="user_manage_add_form"  method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%">
		<input type="hidden" id="id" name="id" value="${vo.id }">
			<tr>
				<th style="width: 120px;">实体名称：</th>
				<td><c:if test="${vo.industryEntity==null}">暂无</c:if><c:if test="${vo.industryEntity!=null}">${vo.industryEntity.entityName}</c:if>
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">二维码路径：</th>
				<td width=300 style="word-break:break-all">${vo.qrcodeValue }
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">二维码编号：</th>
				<td>${vo.qrcodeNumber}
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">SN码：</th>
				<td>${vo.sn}
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">二维码类别：</th>
				<td>${vo.qrcodeTypeStr}
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">二维码内容：</th>
				<td width=300 style="word-break:break-all"><c:if test="${vo.content==null || vo.content==''}">暂无</c:if><c:if test="${vo.content!=null && vo.content!=''}">${vo.content }</c:if>
                </td>
			</tr>
			<tr>
			    <th style="width: 120px;">二维码：</th>
				<td >
				<a href="#" onclick="viewQrcodeDetail('${vo.qrcodeValue }')">
					<img src='${pageContext.request.contextPath}/industryEntityManage/viewBarcodeImg.action?id=${vo.id}&queryPath=${vo.qrcodeValue}'/></br>
					   [扫描二维码查看]
				</a>
				</td>
			</tr>
		</table>
	</form>
</div>
