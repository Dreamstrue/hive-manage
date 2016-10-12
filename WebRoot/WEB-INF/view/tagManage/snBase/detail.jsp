<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div align="center" style="width: 100%">
	<form id="user_manage_add_form"  method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%">
		<input type="hidden" id="id" name="id" value="${vo.id }">
			<tr>
				<th style="width: 120px;">批次编号：</th>
				<td>${vo.batch}
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">SN码：</th>
				<td>${vo.sn}
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">行业实体：</th>
				<td>${vo.entityName }
				</td>
			</tr>
			
			<tr>
				<th style="width: 120px;">查询路径：</th>
				<td width=320 style="word-break:break-all">${vo.queryPath }
                </td>
			</tr>
			<tr>
				<th style="width: 120px;">查询次数：</th>
				<td>${vo.queryNum }
				</td>
			</tr>
			<tr>
				<th style="width: 120px;">黑名单：</th>
				<td>
				<c:if test="${vo.blackList=='0'}">正常</c:if>
				<c:if test="${vo.blackList=='1'}">黑名单</c:if>
				</td>
			</tr>
<!-- 			<tr> -->
<!-- 				<th style="width: 120px;">标签状态：</th> -->
<!-- 				<td> -->
<!-- 					<c:if test="${vo.status=='0'}">未印刷</c:if> -->
<!-- 				   <c:if test="${vo.status=='1'}">入库</c:if> -->
<!-- 				   <c:if test="${vo.status=='2'}">出库</c:if> -->
<!-- 				   <c:if test="${vo.status=='3'}">绑定产品</c:if> -->
<!-- 				   <c:if test="${vo.status=='4'}">已作废</c:if> -->
<!-- 				</td> -->
<!-- 			</tr> -->
			<tr>
			    <th style="width: 120px;">二维码：</th>
				<td >
				<a href="#" onclick="viewQrcodeDetail('${vo.queryPath }')">
					<img src='${pageContext.request.contextPath}/SNBaseController/viewQrcode.action?id=${vo.id}&queryPath=${vo.queryPath}'/></br>
					
					   [扫描二维码评价]
				</a>
				</td>
			</tr>
		</table>
	</form>
</div>
