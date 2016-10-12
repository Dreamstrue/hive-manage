<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>企业质量承诺书</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../common/inc.jsp"></jsp:include>
  </head>
  
  <body>
    
	<div style="overflow:auto;padding:20px;">
		<form id="dataForm" name="dataForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="nquaproid" value="${qualityPromise.nquaproid}"/>
			<input type="hidden" name="nenterpriseid" value="${qualityPromise.nenterpriseid}"/>
			<table>
				<tr>
					<td align="right">行业分类：</td>
					<td>
						<input id="oldIndCatCode" type="hidden" value="${qualityPromise.cindcatcode}"/>
						<div id="tradeDiv">
							<select id="sectors" name="indCategory"><option value="">请选择...</option></select>
						</div>
						<%-- <input type="text" name="cindcatcode" class="easyui-validatebox" required="true" value="${qualityPromise.cindcatcode}"/><label class="red">*</label> --%>
					</td>
				</tr>
				<tr>
					<td align="right">标题：</td>
					<td><input type="text" name="ctitle" class="easyui-validatebox" required="true" value="${qualityPromise.ctitle}"/><label class="red">*</label></td>
				</tr>
				<tr>
					<td align="right">内容：</td>
					<td><textarea rows="2" cols="40"  name="ccontent" class="easyui-validatebox" required="true">${qualityPromise.ccontent}</textarea><label class="red">*</label></td>
				</tr>
				<tr>
					<td align="right">附件：</td>
					<td>
						<input type="file" name="annex" id="annex" style="width: 200px;"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/enterpriseInfo/industryCategory.js" charset="utf-8"></script>	
  </body>
</html>