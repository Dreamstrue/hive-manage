<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>企业获奖信息</title>
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
			<input type="hidden" name="nawaentid" value="${awardEnterprise.nawaentid}"/>
			<input type="hidden" name="nenterpriseid" value="${awardEnterprise.nenterpriseid}"/>
			<table>
				<tr>
					<td align="right">奖项：</td><td><input type="text" name="cawards" class="easyui-validatebox" required="true" value="${awardEnterprise.cawards}"/><label class="red">*</label></td>
					<td align="right">获奖时间：</td><td><input type="text" name="dawardtime" class="easyui-datebox" required="true" editable="false" value="${awardEnterprise.dawardtime}" /><label class="red">*</label></td>
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
  <script type="text/javascript">
  		$(function(){
			
  		});
  </script>	
  
 
  
</html>