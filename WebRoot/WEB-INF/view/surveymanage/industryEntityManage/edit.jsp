<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>企业登记信息页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../../common/inc.jsp"></jsp:include>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jTemplates/jquery-jtemplates.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/enterpriseInfo/codeSelectInit.js" charset="utf-8"></script>
	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/enterpriseInfo/industryCategory.js" charset="utf-8"></script> -->
  </head>
  
  <body>
  
    <div id="tt" class="easyui-tabs" fit=true tabPosition="bottom">
		<div title="实体信息编辑" style="padding:10px;">
			<form id="dataForm" name="dataForm" method="post" enctype="multipart/form-data">
				<input type="hidden" id="id" name="id" value="${induEntity.id}"/>
				<input type="hidden" id="createid" name="createid" value="${induEntity.createid}"/>
				<input type="hidden" id="createtime" name="createtime" value="${induEntity.createtime}"/>
				<input type="hidden" id="objectId" name="objectId" value="${induEntity.objectId}"/>
				<input type="hidden" id="cauditstatus" name="cauditstatus" value="${induEntity.cauditstatus}"/>
				<input type="hidden" id="creatUserstatus" name="creatUserstatus" value="${induEntity.creatUserstatus}"/>
		    	<table>
					<tr>
					    <td align="right">实体名称：</td><td><input name="entityName"  type="text" class="easyui-validatebox" required="true" value="${induEntity.entityName}"/><label class="red">*</label></td>
						<td align="right">责任人：</td><td><input name="linkMan" id="linkMan" type="text" class="easyui-validatebox" required="true" value="${induEntity.linkMan}"/><label class="red">*</label></td>
						<td align="right">联系电话：</td><td><input name="linkPhone" type="text" class="easyui-validatebox" required="true" value="${induEntity.linkPhone}"/><label class="red">*</label></td>
					</tr>
					<tr>
						<td align="right">成立时间：</td>
						<td><input name="foundtime" type="text" class="easyui-datebox" required="true" editable="false" value="${induEntity.foundtime}" /><label class="red">*</label></td>
						<td align="right">实体地址：</td>
						<td colspan="5"><input name="address" type="text" class="easyui-validatebox" required="true" style="width: 475px;" value="${induEntity.address}"/><label class="red">*</label></td>
					</tr>
						<tr>
						<td align="right">行业类别：</td>
						<td >
							 <input id="industryid" name="entityType" value="${induEntity.entityType }" class="easyui-combotree" data-options="url:'${appPath}/surveyIndustryManage/treegrid.action',parentField:'pid',lines:true,required:true "  /><label class="red">*</label>
						</td>
					    <td align="right">实体类型：</td>
						<td >
							 <input id="entityCategoryid"  name="entityCategory" value="${induEntity.entityCategory }"  class="easyui-combotree" data-options="url:'${appPath}/entityCategoryManage/treegrid.action',parentField:'pid',lines:true,required:true " /><label class="red">*</label>
					    </td>
						<td align="right">外系统ID：</td><td><input name="otherId" type="text" class="easyui-validatebox"  value="${induEntity.otherId}"/></td>
					</tr>
					<tr>
						<td align="right">行政区划：</td>
						<td colspan="5">
							
							<input id="oldProvince" type="hidden"  value="${induEntity.cprovincecode}"/>
							<input id="oldCity" type="hidden" value="${induEntity.ccitycode}"/>
							<input id="oldDistrict" type="hidden" value="${induEntity.cdistrictcode}"/>
							<select id="cprovincecode" name="cprovincecode"><option value="">请选择省级...</option></select>
							<select id="ccitycode" name="ccitycode"></select>
						</td>
					</tr>
					<tr>
						<td align="right">登记注册类型：</td>
						<td colspan="5">
							<input id="oldCompanyTypeCode" type="hidden" value="${induEntity.ccomtypcode}"/>
							<div id="typeDiv">
								<select id="companyType" name="regType"><option value="">请选择...</option></select>
							</div>
						</td>
					</tr>
					<tr>
						<td align="right">经营范围：</td>
						<td colspan="5"><textarea rows="2" cols="65" name="cbusiness" class="easyui-validatebox" required="true">${induEntity.cbusiness}</textarea><label class="red">*</label></td>
					</tr>
					<tr>
						<td align="right">企业简介：</td>
						<td colspan="5"><textarea rows="2" cols="65" name="csummary" type="text" class="easyui-validatebox" required="true">${induEntity.csummary}</textarea><label class="red">*</label></td>
					</tr>
					<tr>
						<td colspan="6"><hr color="#36ACE3" size="1"></td>
					</tr>
			    </table>
			     <div style="text-align: center;">
			     	<input type="button" id="submitBtn" value="提交" />
					<input type="button" id="closeBtn" style="cursor:hand;" value="关闭" />
			     </div>
		    </form>
		</div>
		
	</div>
		
	<script type="text/javascript">
	
		//用于存放编辑时删除的附件ID数组
  		var annexDelIds = [];
  		
		$(function(){
			// "提交"按钮的事件处理
			$("#submitBtn").click(function(){
				/*保存企业登记信息成功后进入企业信息变更页面*/
				$('#dataForm').form('submit',{
					url:'${pageContext.request.contextPath}/industryEntityManage/updateEntityInfo.action',
					onSubmit: function(){
				        // do some check
				        // return false to prevent submit;
				        // 先进行form中表单元素的校验
				        var isValid = $(this).form('validate');
						if (!isValid){
							return false;
						}
				    },
					success:function(result){
						var resultObj = $.parseJSON(result);
	 					if(resultObj.status){
	 						// 给出提示2秒后跳转
	 						$.messager.show({
									title : '提示',
									msg : resultObj.msg
								});
							setTimeout(function(){
								 parent.layout_center_refreshTab('行业实体管理');
		 						 parent.layout_center_closeTab('实体信息编辑');
	 						},2000);
	 					}else{
	 						easyuiBox.alert(result.msg);
	 					}
						
					}
				});
				
			});
			
			// "关闭"按钮的事件处理
			$("#closeBtn").click(function(){
		        parent.layout_center_closeTab('实体信息编辑');
			});
			
		});
	</script>
  </body>
</html>