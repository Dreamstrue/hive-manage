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
				<!-- 用于标识刚进入页面时是否有附件来控制附件列表的显示  -->
			<input type="hidden" name="annexExist" id="annexExist" value="${annexExist}"/>
			<!-- 用于存放编辑时删除的附件ID  -->
			<input type="hidden" name="annexDelIds" id="annexDelIds"/>
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
					<td align="right">标题：</td><td><input type="text" name="ctitle" class="easyui-validatebox" required="true" value="${qualityPromise.ctitle}"/><label class="red">*</label></td>
				</tr>
				<tr>
					<td align="right">内容：</td><td colspan="3"><input type="text" name="ccontent" class="easyui-validatebox" required="true" value="${qualityPromise.ccontent}"/><label class="red">*</label></td>
				</tr>
				<tr>
					<td align="right">附件：</td>
					<td colspan="3">
						<div id="annexSelectDiv">
							<input type="file" name="annex" id="annex" style="width: 200px;"/>&#160;<label class="red">*</label>
						</div>
						<div id="annexContainerDiv">
							<a href="${appPath}/annex/download.action?id=${annexFile.id}">${annexFile.cfilename}</a>&nbsp;&nbsp;<input type="button" value="删除" onclick="removeExistAnnex(${annexFile.id});">
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<script type="text/javascript">
  	
  		//用于存放编辑时删除的附件ID数组
  		var annexDelIds = [];
  	
  		$(document).ready(function(){
  			
  			// 页面加载完成后，如果存在附件，则隐藏附件选择框；如果不存在附件，则显示附件选择框
  			var annexExist = $('#annexExist').val();
  			if(annexExist && annexExist == "true"){
  				$('#annexSelectDiv').hide();
  			}else{
  				$('#annexContainerDiv').hide();
  			}
  			
  		});
  
  		/*
  			删除已存在的附件【将附件容器隐藏，附件选择框显示】
  			@param annexId 删除的附件Id
  		*/
  		function removeExistAnnex(annexId){
  			
  			$.messager.confirm('提示','您确定要删除该附件?',function(b){
				if(b){
					annexDelIds.push(annexId);
					$('#annexContainerDiv').hide();
					$('#annexSelectDiv').show();
				}
			});
  			
  		}// End Of removeExistAnnex()
  
  </script>
	
  <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/enterpriseInfo/industryCategory.js" charset="utf-8"></script>
  </body>
</html>