<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">


</script>
<div align="center" style="width: 100%">
	<form id="user_manage_add_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%" id="tab">
				<input type="hidden" id="id" name="id" value="${vo.id }">
			
			<tr>
			<th style="width: 70px;">批次状态</th>
			<td>
             <select class="easyui-combobox" id="status" name="status" data-options="prompt:'印刷状态',panelHeight:'auto',required:true" style="width:90px;">
                <c:if test="${vo.status=='0'}">
				<option value=0 selected="selected">未生效</option>
				<!-- <option value=1>印刷中</option> -->
				<option value=2>占用</option>
				</c:if>
				<c:if test="${vo.status=='1'}">
					<option value=1 selected="selected">印刷中</option>
					<option value=2>占用</option>
				</c:if>
				<c:if test="${vo.status=='2'}">
					<option value=0 >未生效</option>
					<option value=2 selected="selected">占用</option>
				</c:if>
				
								
			</select>
			</td>
			</tr>
		
		</table>
	</form>
</div>