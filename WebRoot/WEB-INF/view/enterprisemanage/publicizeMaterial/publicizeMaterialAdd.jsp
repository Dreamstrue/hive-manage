<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>企业宣传资料</title>
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
			<input type="hidden" name="npubmatid" value="${publicizeMaterial.npubmatid}"/>
			<input type="hidden" name="nenterpriseid" value="${publicizeMaterial.nenterpriseid}"/>
			<table>
				<tr>
					<td align="right">企业荣誉：</td><td><input type="text" name="honor" class="easyui-validatebox" required="true" value="${publicizeMaterial.honor}"/><label class="red">*</label></td>
					<td align="right">名牌产品：</td><td><input type="text" name="nameplateProductIds" class="easyui-validatebox" required="true" value="${publicizeMaterial.nameplateProductIds}"/><label class="red">*</label></td>
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