<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/enterpriseInfo/codeSelectInit.js" charset="utf-8"></script>
	<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/enterpriseInfo/industryCategory.js" charset="utf-8"></script> -->
  </head>
  
  <body>
    <div id="tt" class="easyui-tabs" fit=true tabPosition="bottom">
		<div style="padding:10px;">
			<form id="dataForm" name="dataForm" method="post" enctype="multipart/form-data">
				<input type="hidden" id="nenterpriseid" name="nenterpriseid" value="${enterpriseInfo.nenterpriseid}"/>
		    	<table>
					<tr>
						<td align="right">企业名称：</td><td><input name="centerprisename"  type="text" class="easyui-validatebox" required="true" value="${enterpriseInfo.centerprisename}"/><label class="red">*</label></td>
						<%-- <td align="right">组织机构：</td><td><input name="corganizationcode" id="corganizationcode" type="text" class="easyui-validatebox" data-options="required:true,validType:'remote[${pageContext.request.contextPath}/enterpriseManage/existOfOrgCode.action]'" value="${enterpriseInfo.corganizationcode}" /><label class="red">*</label></td> --%>
						<td align="right">组织机构：</td><td><input name="corganizationcode" id="corganizationcode" type="text" class="easyui-validatebox" required="true" validType="remote['${pageContext.request.contextPath}/enterpriseManage/existOfOrgCode.action', 'orgCode']" value="${enterpriseInfo.corganizationcode}" invalidMessage="该组织机构代码已存在！"/><label class="red">*</label></td>
						<td align="right">成立时间：</td>
						<td><input name="destablishdate" type="text" class="easyui-datebox" required="true" editable="false" value="${enterpriseInfo.destablishdate}" /><label class="red">*</label></td>
					</tr>
					<tr>
						<td align="right">注册资本(万元)：</td><td><input name="nregcapital" type="text" class="easyui-validatebox" required="true" value="${enterpriseInfo.nregcapital}"/><label class="red">*</label></td>
						<td align="right">注册地址：</td>
						<td colspan="5"><input name="cregaddress" type="text" class="easyui-validatebox" required="true" style="width: 475px;" value="${enterpriseInfo.cregaddress}"/><label class="red">*</label></td>
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
						<td align="right">行业类别：</td>
						<td colspan="5">
						<!-- 
							 <input id="oldIndCatCode" type="hidden" value="${enterpriseInfo.cindcatcode}"/>
							 <div id="tradeDiv">
							 	<select id="sectors" name="indCategory"><option value="">请选择...</option></select>
							 </div>
						 -->
							 <input id="industryid" name="cindcatcode" value="${enterpriseInfo.cindcatcode }" class="easyui-combotree" data-options="url:'${appPath}/surveyIndustryManage/treegrid.action',parentField:'pid',lines:true,required:true " style="width:170px;" />
						</td>
					</tr>
					<tr>
						<td align="right">登记注册类型：</td>
						<td colspan="5">
							<input id="oldCompanyTypeCode" type="hidden" value="${enterpriseInfo.ccomtypcode}"/>
							<div id="typeDiv">
								<select id="companyType" name="regType"><option value="">请选择...</option></select>
							</div>
							
							<%-- <input name="ccomtypcode" type="text" class="easyui-validatebox" required="true" value="${enterpriseInfo.ccomtypcode}" /><label class="red">*</label> --%>
						</td>
					</tr>
					<tr>
						<td align="right">经营范围：</td>
						<td colspan="5"><textarea rows="2" cols="65" name="cbusiness" class="easyui-validatebox" required="true">${enterpriseInfo.cbusiness}</textarea><label class="red">*</label></td>
					</tr>
					<tr>
						<td align="right">企业简介：</td>
						<td colspan="5"><textarea rows="2" cols="65" name="csummary" type="text" class="easyui-validatebox" required="true">${enterpriseInfo.csummary}</textarea><label class="red">*</label></td>
					</tr>
					<tr>
						<td align="right">法定代表人：</td><td><input name="clegal" type="text" class="easyui-validatebox" required="true" value="${enterpriseInfo.clegal}"/><label class="red">*</label></td>
						<td align="right">法人手机：</td><td><input name="clegalphone" type="text" class="easyui-validatebox" required="true" value="${enterpriseInfo.clegalphone}" /><label class="red">*</label></td>
						<td align="right">营业执照编号：</td><td><input name="cbusinesslicenseno" type="text" class="easyui-validatebox" required="true" value="${enterpriseInfo.cbusinesslicenseno}"/><label class="red">*</label></td>
					</tr>
					<tr>
						<td align="right">注册商标名称：</td><td><input name="ctrademark" type="text" value="${enterpriseInfo.ctrademark}" /></td>
						<td align="right">登记批准日期：</td><td><input name="dapprove" type="text" class="easyui-validatebox" onfocus="WdatePicker()" readonly="readonly" required="true" value="${enterpriseInfo.dapprove}" /><label class="red">*</label></td>
						<td align="right">营业执照有效期至：</td><td><input name="dbusinesslicenseend" type="text" class="easyui-validatebox" onfocus="WdatePicker()" readonly="readonly" required="true" class="easyui-datebox" value="${enterpriseInfo.dbusinesslicenseend}" /><label class="red">*</label></td>
					</tr>
					<tr>
						<td align="right">联系人：</td><td><input name="ccontractperson" type="text" class="easyui-validatebox" required="true" value="${enterpriseInfo.ccontractperson}"/><label class="red">*</label></td>
						<td align="right">联系人手机：</td><td><input name="ccontractpersonphone" type="text" class="easyui-validatebox" required="true" value="${enterpriseInfo.ccontractpersonphone}" /><label class="red">*</label></td>
						<td align="right">办公固话：</td><td><input name="cofficetelephone" type="text" class="easyui-validatebox" required="true" value="${enterpriseInfo.cofficetelephone}" /><label class="red">*</label></td>
					</tr>
					<tr>
						<td align="right">邮政编码：</td><td><input name="czipcode" type="text" class="easyui-validatebox" required="true" value="${enterpriseInfo.czipcode}" /><label class="red">*</label></td>
						<td align="right">通讯地址：</td><td colspan="3"><input name="ccontractaddress" type="text" class="easyui-validatebox" required="true" style="width: 475px;" value="${enterpriseInfo.ccontractaddress}" /><label class="red">*</label></td>
					</tr>
					<tr>
						<td align="right">企业网站：</td><td><input name="csite" type="text" value="${enterpriseInfo.csite}" /></td>
						<td align="right">企业总人数：</td><td><input name="ipersonnum" type="text" value="${enterpriseInfo.ipersonnum}" /></td>
						<td align="right">专业技术人员初级资格人数：</td><td><input name="itechnicalpersonnumlow" type="text" value="${enterpriseInfo.itechnicalpersonnumlow}" /></td>
					</tr>
					<tr>
						<td align="right">专业技术人员中级资格人数：</td><td><input name="itechnicalpersonnummiddle" type="text" value="${enterpriseInfo.itechnicalpersonnummiddle}" /></td>
						<td align="right">专业技术人员高级资格人数：</td><td><input name="itechnicalpersonnumhigh" type="text" value="${enterpriseInfo.itechnicalpersonnumhigh}" /></td>
						<td align="right">国税税务登记号：</td><td><input name="ccountrytaxno" type="text" value="${enterpriseInfo.ccountrytaxno}" /></td>
					</tr>
					<tr>
						<td align="right">地税税务登记号：</td><td><input name="clocalgovtaxno" type="text" value="${enterpriseInfo.clocalgovtaxno}" /></td>
						<td align="right">开户银行：</td><td><input name="caccountbank" type="text" value="${enterpriseInfo.caccountbank}" /></td>
						<td align="right">开户账号：</td><td><input name="caccountno" type="text" value="${enterpriseInfo.caccountno}" /></td>
					</tr>
					<tr>
						<td colspan="6"><hr color="#36ACE3" size="1"></td>
					</tr>
					<tr>
						<td align="right">营业执照图片：</td>
						<td colspan="3">
							<input type="file" name="businessLicencePic" id="businessLicencePic" style="width: 200px;"/>
							<label class="red">*</label>
						</td>
					</tr>
					<tr>
						<td align="right">组织机构代码证图片：</td>
						<td colspan="3">
							<input type="file" name="orgCodePic" id="orgCodePic" style="width: 200px;"/>
							<label class="red">*</label>
						</td>
					</tr>
					<tr>
						<td align="right">企业Logo图片：</td>
						<td colspan="3">
							<input type="file" name="entLogoPic" id="entLogoPic" style="width: 200px;"/>
							<label class="red">*</label>
						</td>
					</tr>
					<tr>
						<td colspan="6"><hr color="#36ACE3" size="1"></td>
					</tr>
			    </table>
			    
			    <div style="text-align: center;">
					<input type="button" id="submitBtn" value="提交" />
					<input type="button" id="closeBtn" value="关闭" />
			    </div>
		    </form>
		</div>
		
	</div>
		
	<script type="text/javascript">
		$(function(){
		
			// "提交"按钮的事件处理
			$("#submitBtn").click(function(){
				//$(this).attr("disabled","disabled");
				
				/*保存企业登记信息成功后进入企业信息变更页面*/
				$('#dataForm').form('submit',{
					url:'${pageContext.request.contextPath}/enterpriseManage/saveEntInfo.action',
					onSubmit: function(){
				        // do some check
				        // return false to prevent submit;
				        // 先进行form中表单元素的校验
				        var isValid = $(this).form('validate');
						if (!isValid){
							return false;
						}
						
				        
				        // 然后进行上传的图片的校验
				        var $businessLicencePic = $("#businessLicencePic");
				        if($businessLicencePic.val()==""){
				        	easyuiBox.show("营业执照图片为必选项!");
							$businessLicencePic.focus();
							return false;
				        }
				        
				    },
					success:function(result){
						var resultObj = $.parseJSON(result);
	 					if(resultObj.status){
	 						var entInfoObj = resultObj.data;
							var entInfoId = entInfoObj.nenterpriseid;
	 						var url = "${pageContext.request.contextPath}/enterpriseManage/toEntInfoEdit.action?entInfoId="+entInfoId;
							var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
							parent.layout_center_addTabFun({title:'企业信息编辑', content:content, closable:true});
							parent.layout_center_closeTab('企业信息登记');
	 						
	 					}else{
	 						easyuiBox.alert(result.msg);
	 					}
						
					}
				});
				
			});
		
			// "关闭"按钮的事件处理
			$("#closeBtn").click(function(){
		        parent.layout_center_closeTab('企业信息登记');
			});
			
		});
		
	</script>
    
  </body>
</html>