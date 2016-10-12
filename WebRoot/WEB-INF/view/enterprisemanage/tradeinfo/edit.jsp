<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/integrity.css" type="text/css"></link>
<script type="text/javascript">
		
	$(function() {
		var value = $('.np').val();
		if (value == '0') {
			$('#checkFile').hide();
		} else if (value = '1') {
			$('#checkFile').show();
		}
	});

	function approveAction(type) {
		$('#form')
				.form(
						'submit',
						{
							url : '${pageContext.request.contextPath}/tradeInfo/approveAction.action?type='
									+ type,
							success : function(result) {
								var r = $.parseJSON(result);
								$.messager.show({
									title : '提示',
									msg : r.msg
								});
								setTimeout(
										function() {
											location.href = '${pageContext.request.contextPath}/tradeInfo/manage.action';
										}, 1000);
							}
						});
	}
</script>
<div align="left" style="width:70%; height: 100%;">
<div id="div1" >当前位置：产品供求&nbsp;&gt;&nbsp;审核</div>
<div id="div2" align="right"><a id="btn" href="${pageContext.request.contextPath}/tradeInfo/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<hr style="color: silver;border-style: solid;"/>
<div align="left" style="width: 100%">
	<table class="integrity_table">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<input type="hidden" id="id" name="id" value="${vo.id}">
						<table >
							<tr>
								<th>供求类型：</th>
								<td>
									<c:if test="${vo.cbs=='2'}">
									<input type="radio" class="sign" name="cbs" value="1" disabled="disabled">供应&nbsp;&nbsp;<input type="radio" class="sign" name="cbs" value="2" checked="checked" disabled="disabled">求购
									</c:if>
									<c:if test="${vo.cbs=='1'}">
									<input type="radio" class="sign" name="cbs" value="1" checked="checked" disabled="disabled">供应&nbsp;&nbsp;<input type="radio" class="sign" name="cbs" value="2" disabled="disabled">求购
									</c:if>
								</td>
							</tr>
							<tr>
								<th>标题：</th>
								<td><input name="title"  id="title" value="${vo.ctitle}"  style="width: 400px;" disabled="disabled"/></td>
							</tr>
							<tr>
								<th>产品名称：</th>
								<td><input name="cproductname"  id="cproductname"  value="${vo.cproductname}" style="width: 400px;" disabled="disabled"/></td>
							</tr>
							<tr>
								<th>产品数量：</th>
								<td><input name="cproductnum"  id="cproductnum" value="${vo.cproductnum}" style="width: 400px;" disabled="disabled"/></td>
							</tr>
							<tr>
								<th>单位：</th>
								<td><input name="unit"  id="unit" value="${vo.unit}" style="width: 400px;" disabled="disabled"/></td>
							</tr>
							<tr>
								<th>(期望)价格：</th>
								<td><input name="cprice"  id="cprice" value="${vo.cprice}" style="width: 400px;" disabled="disabled"/></td>
							</tr>
							<tr>
								<th>联系人：</th>
								<td><input name="ccontactperson"  id="ccontactperson" value="${vo.ccontactperson}" style="width: 400px;" disabled="disabled"/></td>
							</tr>
							<tr>
								<th>联系电话：</th>
								<td><input name="ccontactphone"  id="ccontactphone" value="${vo.ccontactphone}" style="width: 400px;" disabled="disabled"/></td>
							</tr>
							<tr>
								<th>有效期起：</th>
								<td>
									<input type='text' name='dvalidbegin' id='dvalidbegin' value="${vo.dvalidbegin}" style='width:230px'  class='intxt' readonly="readonly"  onFocus="WdatePicker({minDate:'%y-%M-{%d}'})" disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<th>有效期止：</th>
								<td>
									<input type='text' name='dvalidend' id='dvalidend' value="${vo.dvalidend}" style='width:230px'  class='intxt' readonly="readonly"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'dvalidbegin\')}'})" disabled="disabled" />
								</td>
							</tr>
							<tr>
								<th>存在附件：</th>
								<td>
									<select class="np" name="chasannex" disabled="disabled">
									<c:if test="${vo.chasannex=='0'}">
										<option value="1">是</option>
										<option value="0" selected="selected">否</option>
									</c:if>
									<c:if test="${vo.chasannex=='1'}">
										<option value="1"  selected="selected">是</option>
										<option value="0">否</option>
									</c:if>
									</select>
								</td>
							</tr>
							<tr id="checkFile">
								<th>选择附件：</th>
								<td>
									<span>
										<c:if test="${!empty annex}">
											<c:forEach items="${annex}" var="t" varStatus="status">
											<div id="${t.id}">
											<a href="${appPath}/annex/download.action?id=${t.id}">${t.cfilename}</a>
											</div>
											</c:forEach>
										</c:if>
									</span>
								</td>
							</tr>
							<tr>
								<th>审核意见：</th>
								<td>
									<c:if test="${type=='approve'}">
										<textarea name="cauditopinion" id="cauditopinion"  style="width:400px;"></textarea>
									</c:if>
									<c:if test="${type=='detail'}">
										<textarea name="cauditopinion" id="cauditopinion"  style="width:400px;" disabled="disabled">${vo.cauditopinion}</textarea>
									</c:if>
								</td>
							</tr>
						</table>
						<c:if test="${type=='approve'}">
							<p align="right" style="padding-right: 70px;">
								<input type="button" value="通过" onclick="approveAction('success')"/>
								<input type="button" value="不通过" onclick="approveAction('error')" />
				   			</p>
						</c:if>
					</form>
				</div>
			</td>
		</tr>
	</table>
</div>
</div>
