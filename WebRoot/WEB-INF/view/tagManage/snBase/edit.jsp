<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">


</script>
<div align="center" style="width: 100%">
	<form id="user_manage_add_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%" id="tab">
				<input type="hidden" id="id" name="id" value="${vo.id }">
			
			<tr>
			<th style="width: 70px;">标签状态</th>
			<td>
             <select class="easyui-combobox" id="status" name="status" data-options="prompt:'标签状态',panelHeight:'auto',required:true" style="width:90px;">
                <c:if test="${vo.status=='0'}">
				<!--<option value=0 selected="selected">未印刷</option>
				<option value=1>入库</option>
				<option value=2>出库</option>
				<option value=3>绑定产品</option> -->
				<option value=4>已作废</option>
				</c:if>
				<c:if test="${vo.status=='1'}">
				<!-- <option value=0>未印刷</option>
				<option value=1 selected="selected">入库</option>
				<option value=2>出库</option>
				<option value=3>绑定产品</option>-->
				<option value=4>已作废</option>
				</c:if>
				 <c:if test="${vo.status=='2'}">
				<!-- <option value=0>未印刷</option>-->
				<!-- <option value=1>入库</option>-->
				<!-- <option value=2 selected="selected">出库</option>-->
				<!-- <option value=3>绑定产品</option>-->
				<option value=4>已作废</option>
				</c:if>
				 <c:if test="${vo.status=='3'}">
				<!-- <option value=0>未印刷</option>-->
				<!-- <option value=1>入库</option>-->
				<!-- <option value=2>出库</option>-->
				<!-- <option value=3 selected="selected">绑定产品</option>-->
				<option value=4>已作废</option>
				</c:if>
				 <c:if test="${vo.status=='4'}">
				<!-- <option value=0>未印刷</option>-->
				<!-- <option value=1>入库</option>-->
				<!-- <option value=2>出库</option>-->
				<<!-- option value=3>绑定产品</option>-->
				<option value=4 selected="selected">已作废</option>
				</c:if>
								
			</select>
			</td>
			</tr>
		
		</table>
	</form>
</div>