<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>行业实体添加页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../common/inc.jsp"></jsp:include>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/enterpriseInfo/codeSelectInit.js" charset="utf-8"></script>
  </head>
  <body>
    <div id="tt" class="easyui-tabs" fit=true tabPosition="bottom">
		<div title="实体信息添加" style="padding:10px;">
			<form id="dataForm" name="dataForm" method="post" enctype="multipart/form-data">
				<input type="hidden" id="nenterpriseid" name="id" value="${industryEntity.id}"/>
		    	<table>
					<tr>
						<td align="right">实体名称：</td><td><input name="entityName"  type="text" class="easyui-validatebox" required="true" value="${industryEntity.entityName}"/><label class="red">*</label></td>
						<td align="right">责任人：</td><td><input name="linkMan" id="linkMan" type="text" class="easyui-validatebox" required="true" value="${industryEntity.linkMan}"/><label class="red">*</label></td>
						<td align="right">联系电话：</td><td><input name="linkPhone" type="text" class="easyui-validatebox" required="true" value="${industryEntity.linkPhone}"/><label class="red">*</label></td>

					</tr>
					<tr>
						<td align="right">成立时间：</td>
						<td><input name="foundtime" type="text" class="easyui-datebox" required="true" editable="false" value="${industryEntity.foundtime}" /><label class="red">*</label></td>
						<td align="right">实体地址：</td>
						<td colspan="5"><input name="address" type="text" class="easyui-validatebox" required="true" style="width: 475px;" value="${industryEntity.address}"/><label class="red">*</label></td>
					</tr>
					<tr>
						<td align="right">行业类别：</td>
						<td >
							 <input id="industryid" name="entityType" value="${industryEntity.entityType }" class="easyui-combotree" data-options="url:'${appPath}/surveyIndustryManage/treegrid.action',parentField:'pid',lines:true,required:true "  /><label class="red">*</label>
						</td>
					    <td align="right">实体类型：</td>
					    <td><input id="entityCategory"  name="entityCategory"  class="easyui-combotree" value="${industryEntity.entityCategory}" data-options="url:'${appPath}/entityCategoryManage/treegrid.action',parentField:'pid',lines:true,required:true " /><label class="red">*</label></td>
					     <td align="right">外系统ID：</td><td><input name="otherId" type="text" class="easyui-validatebox"  value="${industryEntity.otherId}"/></td>
					</tr>
				    <tr>
						<td align="right">行政区划：</td>
						<td colspan="5">
							<input id="oldProvince" type="hidden"  value="${enterpriseInfo.cprovincecode}"/>
							<input id="oldCity" type="hidden" value="${enterpriseInfo.ccitycode}"/>
							<input id="oldDistrict" type="hidden" value="${enterpriseInfo.cdistrictcode}"/>
							<select id="cprovincecode" name="cprovincecode"><option value="">请选择省级...</option></select>
							<select id="ccitycode" name="ccitycode"></select>
						</td>
					</tr>
					<tr>
						<td align="right">登记类型：</td>
						<td colspan="5">
							<input id="oldCompanyTypeCode" type="hidden" value="${industryEntity.ccomtypcode}"/>
							<div id="typeDiv">
								<select id="companyType" name="regType"><option value="">请选择...</option></select>
							</div>
						</td>
					</tr>
					<tr>
						<td align="right">经营范围：</td>
						<td colspan="5"><textarea rows="2" cols="65" name="cbusiness" class="easyui-validatebox" required="true">${industryEntity.cbusiness}</textarea><label class="red">*</label></td>
					</tr>
					<tr>
						<td align="right">企业简介：</td>
						<td colspan="5"><textarea rows="2" cols="65" name="csummary" type="text" class="easyui-validatebox" required="true">${industryEntity.csummary}</textarea><label class="red">*</label></td>
					</tr>
					<tr>
						<td colspan="6"><hr color="#36ACE3" size="1"></td>
					</tr>
			    </table>
			    
				 <div style="text-align: center;">
					<input type="button" id="subBtn" value="提交" onclick="onsubcheck();"/>
					<input type="button" id="closeBtn" value="关闭" />
			    </div>
		    </form>
		
	</div>
	<script type="text/javascript">
	
	function onsubcheck(){
			$('#dataForm').form('submit',{
				url:'${pageContext.request.contextPath}/industryEntityManage/saveindusEntity.action',
				onSubmit: function(){
			        var isValid = $(this).form('validate');
					if (!isValid){
						return false;
					}
			    },
			    success:function(result){
					var resultObj = $.parseJSON(result);
 					if(resultObj.status){
 						 parent.layout_center_refreshTab('行业实体管理');
 						 parent.layout_center_closeTab('实体信息添加');
 					}else{
 						$.messager.show({
 							title:'提示',
 							msg:resultObj.msg
 						});
 					}
					
				}
			});
	}
			// "关闭"按钮的事件处理
			$("#closeBtn").click(function(){
		        parent.layout_center_closeTab('实体信息添加');
			});
			
		
	</script>
  </body>
</html>