<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>企业产品信息</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../common/inc.jsp"></jsp:include>
  </head>
  
  <body>
    
	<div style="padding-top:20px;">
		 <form id="dataForm" name="dataForm" method="post" enctype="multipart/form-data">
		 	<input type="hidden" name="nentproid" value="${product.nentproid}"/>
			<input type="hidden" name="nenterpriseid" value="${product.nenterpriseid}"/>
    		<table>
				<tr>
					<td align="right">产品名称：</td><td><input type="text" name="cproductname" class="easyui-validatebox" required="true" value="${product.cproductname}"/><label class="red">*</label></td>
					<td align="right">执行标准编号：</td><td><input type="text" name="cimplementstandard" class="easyui-validatebox" required="true" value="${product.cimplementstandard}"/><label class="red">*</label></td>
				</tr>
				<tr>
					<td align="right">产品类别：</td>
					<td colspan="3">
						<input id="oldSortCode" type="hidden" value="${product.cprocatcode}"/>
						<div id="sortDiv">
							<select id="sortCode" name="proCat"><option value="">请选择...</option></select>
						</div>
					</td>
				</tr>
				<tr>
					<td align="right">行政许可类别：</td><td><input type="text" name="cadministrativelicensecategory" class="easyui-validatebox" required="true" value="${product.cadministrativelicensecategory}"/><label class="red">*</label></td>
					<td align="right">证书编号：</td><td><input type="text" name="ccertificateno" class="easyui-validatebox" required="true" value="${product.ccertificateno}"/><label class="red">*</label></td>
				</tr>
				<tr>
					<td align="right">发证单位：</td>
					<td colspan="3"><input type="text" name="ccertificationunit" class="easyui-validatebox" required="true" value="${product.ccertificationunit}"/><label class="red">*</label></td>
				</tr>
				<tr>
					<td align="right">获证日期：</td><td><input type="text" name="dgetcertificate" class="easyui-datebox" required="true" value="${product.dgetcertificate}"/><label class="red">*</label></td>
					<td align="right">有效截止日期：</td><td><input type="text" name="dcertificateend" class="easyui-datebox" required="true" value="${product.dcertificateend}"/><label class="red">*</label></td>
				</tr>
				<tr>
					<td align="right">年审情况：</td><td><input type="text" name="cyearexamine" class="easyui-validatebox" required="true" value="${product.cyearexamine}"/><label class="red">*</label></td>
					<td align="right">产品分级情况：</td><td><input type="text" name="crisklevel" value="${product.crisklevel}"/></td>
				</tr>
				<tr>
					<td align="right">产品销售收入(万元)：</td><td><input type="text" name="nsalenum" class="easyui-validatebox" required="true" value="${product.nsalenum}"/><label class="red">*</label></td>
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
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/enterpriseInfo/productCategory.js" charset="utf-8"></script>
  </body>
</html>