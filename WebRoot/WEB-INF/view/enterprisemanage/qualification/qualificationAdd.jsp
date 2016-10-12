<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>企业资质信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../common/inc.jsp"></jsp:include>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.form.js" charset="utf-8"></script>
  </head>
  
  <body>
    
	<div style="overflow:auto;padding:20px;">
		<form id="dataForm" name="dataForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="nquaid" value="${qualification.nquaid}"/>
			<input type="hidden" name="nenterpriseid" value="${qualification.nenterpriseid}"/>
			<table>
				<tr>
					<td align="right">证书名称：</td><td><input type="text" name="ccertificatename" class="easyui-validatebox" required="true" value="${qualification.ccertificatename}"/><label class="red">*</label></td>
					<td align="right">发证单位：</td><td><input type="text" name="ccertificationunit" class="easyui-validatebox" required="true" value="${qualification.ccertificationunit}"/><label class="red">*</label></td>
				</tr>
				<tr>
					<td align="right">证书编号：</td><td><input type="text" name="ccertificateno" class="easyui-validatebox" required="true" value="${qualification.ccertificateno}"/><label class="red">*</label></td>
					<td align="right">有效截止日期：</td><td><input type="text" name="dcertificateend" class="easyui-datebox" required=true editable="false" value="${qualification.dcertificateend}"/><label class="red">*</label></td>
				</tr>
				<tr>
					<td align="right">附件：</td>
					<td colspan="3">
						<input type="file" name="annex" id="annex" style="width: 200px;"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
		
  </body>
</html>