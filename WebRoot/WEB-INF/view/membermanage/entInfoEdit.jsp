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
	<jsp:include page="../common/inc.jsp"></jsp:include>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.form.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/enterpriseInfo/codeSelectInit.js" charset="utf-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/enterpriseInfo/industryCategory.js" charset="utf-8"></script>
  </head>
  
  <body>
  
    <div id="tt" class="easyui-tabs" fit="true" tabPosition="bottom">
		<div title="企业登记信息" style="padding:10px;">
			<form id="dataForm" name="dataForm" method="post" enctype="multipart/form-data">
				<input type="hidden" id="nenterpriseid" name="nenterpriseid" value="${enterpriseInfo.nenterpriseid}"/>
				<!-- 用于标识刚进入页面时是否有附件来控制附件列表的显示  -->
				<input type="hidden" name="annexExist" id="annexExist" value="${annexExist}"/>
				<!-- 用于存放编辑时删除的附件ID  -->
				<input type="hidden" name="annexDelIds" id="annexDelIds"/>
		    	<table width="100%">
					<tr>
						<td align="right" width="10%">企业名称：</td><td width="20%"><input name="centerprisename"  type="text" class="easyui-validatebox" required="true" value="${enterpriseInfo.centerprisename}"/><label class="red">*</label></td>
						<td align="right" width="10%">组织机构：</td><td width="20%"><input name="corganizationcode" type="text" class="easyui-validatebox" required="true" value="${enterpriseInfo.corganizationcode}" /><label class="red">*</label></td>
						<td align="right" width="10%">成立时间：</td>
						<td width="20%"><input name="destablishdate" type="text" required="true" editable="false" value="${enterpriseInfo.destablishdate}" /><label class="red">*</label></td>
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
						<td align="right">国民经济行业分类：</td>
						<td colspan="5">
							 <input id="oldIndCatCode" type="hidden" value="${enterpriseInfo.cindcatcode}"/>
							 <div id="tradeDiv">
							 	<select id="sectors" name="indCategory"><option value="">请选择...</option></select>
							 </div>
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
						<td align="right">登记批准日期：</td><td><input name="dapprove" type="text"  required="true" value="${enterpriseInfo.dapprove}" /><label class="red">*</label></td>
						<td align="right">营业执照有效期至：</td><td><input name="dbusinesslicenseend" type="text"  required="true"  value="${enterpriseInfo.dbusinesslicenseend}" /><label class="red">*</label></td>
					</tr>
					<tr>
						<td align="right">联系人：</td><td><input name="ccontractperson" type="text" class="easyui-validatebox" required="true" value="${enterpriseInfo.ccontractperson}"/><input id="moreLink"  type="button" title="更多联系人"  value="＋" onclick="javascript:openLinkPerson()"/><label class="red">*</label></td>
						<td align="right">联系人手机：</td><td><input name="ccontractpersonphone" type="text" class="easyui-validatebox" required="true" value="${enterpriseInfo.ccontractpersonphone}" /><label class="red">*</label></td>
						<td align="right">办公固话：</td><td><input name="cofficetelephone" type="text" class="easyui-validatebox" required="true" value="${enterpriseInfo.cofficetelephone}" /><label class="red">*</label></td>
						
					</tr>
					<tr id="linkPerson" style="display: none;width:100%">
					<td align="center" colspan="6" >
					<table width="100%" align="center" style="margin: 0 auto" id="linkp">
					</table>
					</td>
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
			    </table>
	    		<!-- <div id="linkPerson" style="display: none; margin-left: 110px;">
					<table id="linkp" style="border: solid 1px #cccccc;">
					</table>
				</div> -->
		    </form>
		    <form id='form1' name='form1' method="post" action="${appPath}/membermanage/checkenterprise.action" onsubmit='return ($("#form1").form("validate"))'>
		    	<input type="hidden" id="nenterpriseid" name="nenterpriseid" value="${enterpriseInfo.nenterpriseid}"/>
				<table>
					<tr>
						<td>审核结果：</td>
						<td>
							<select id="checktype" name="checktype">
								<option value ="1">通过</option>
								<option value ="2">不通过</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>审核意见：</td>
						<td>
							<textarea rows="5" cols="30" id="remark" class="easyui-validatebox" data-options="required:true" name="remark"></textarea>
						</td>
					</tr>
				</table>
		    	<input type="button" id="submit" onclick="javascript:checkAdd()" value="提交" />
				<input type="button" id="closeBtn" onclick="javascript:history.go(-1)" value="返回" />
		    </form>
		</div>
		
	</div>
		
	<script type="text/javascript">
		function openLinkPerson(){
			var div1 = document.getElementById("linkPerson");
			div1.style.display = "";
			var input=document.getElementById('moreLink');
			input.setAttribute("value","-");
			input.setAttribute("title","隐藏联系人");
			input.setAttribute("onclick","hiddenPerson()");
			
		}
		
		function hiddenPerson(){
			var div1 = document.getElementById("linkPerson");
			div1.style.display = "none";
			var input=document.getElementById('moreLink');
			input.setAttribute("value","＋");
			input.setAttribute("title","更多联系人");
			input.setAttribute("onclick","openLinkPerson()");
		}
		
		
		
		function checkAdd(){
			if($("#form1").form('validate')){
				var param=$('#form1').formSerialize();
				$.ajax({ 
			         type : "post", 
			         url : "${appPath}/membermanage/checkenterprise.action", 
			         data : param, 
			         dataType : "json", 
			         async : false, // 同步
			         success : function (re) {
				        if (re.status) {
				        	$.messager.alert('提示',re.msg);
				        }
				    }
			    });
			    //window.parent.reloadWestTree();//刷新左侧菜单
			    javascript:history.go(-1);
		    }
		}
		//用于存放编辑时删除的附件ID数组
  		var annexDelIds = [];
  		
		$(function(){
		
			// 页面加载完成后，如果存在附件，则隐藏附件选择框；如果不存在附件，则显示附件选择框
  			var annexExist = $('#annexExist').val();
  			if(annexExist && annexExist == "true"){
  				$('#annexSelectDiv').hide();
  			}else{
  				$('#annexContainerDiv').hide();
  			}
		
			// 进入编辑页面后如果企业登记信息ID不为空，就显示其他标签页
			var entInfoId = $('#nenterpriseid').val();
			if(entInfoId){
				 //displayOtherTabs(entInfoId);
			}
			
			
			// "提交"按钮的事件处理
			$("#submitBtn").click(function(){
				
				// 将要删除的附件ID数组中的数据写到表单中
				$('#annexDelIds').val(annexDelIds);
				/*保存企业登记信息成功后进入企业信息变更页面*/
				$('#dataForm').form('submit',{
					url:'${pageContext.request.contextPath}/enterpriseManage/updateEntInfo.action',
					success:function(result){
						var resultObj = $.parseJSON(result);
	 					if(resultObj.status){
	 						var entInfoObj = resultObj.data;
							var entInfoId = entInfoObj.nenterpriseid;
	 						var url = "${pageContext.request.contextPath}/enterpriseManage/toEntInfoEdit.action?entInfoId="+entInfoId;
							var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
							//parent.layout_center_addTabFun({title:'企业信息编辑', content:content, closable:true});
							parent.layout_center_refreshTab("企业信息编辑");
	 						
	 					}else{
	 						easyuiBox.alert(result.msg);
	 					}
						
					}
				});
				
			});
			
			// "关闭"按钮的事件处理
			$("#closeBtn").click(function(){
		        parent.layout_center_closeTab('企业信息编辑');
			});
			
		});
		
		
		/*
			显示其他的标签项
			@param 
				entInfoId 企业Id
		*/
		function displayOtherTabs(entInfoId){
		
			// 判断传递的企业ID是否为空，若不为空，则将该参数添加到Url中
			var entInfoIdParamStr = "";
			if(entInfoId){
				entInfoIdParamStr = "?entInfoId="+entInfoId;
			}
		
			var containerId = "tt";
			// 企业资质信息
	        var titleQualification = '企业资质信息';
	        var urlQualification = '${pageContext.request.contextPath}/enterpriseManage/toQualificationList.action'+entInfoIdParamStr;
	        addTab(containerId, titleQualification, urlQualification);
	        // 企业产品信息
	        var titleProduct = '企业产品信息';
	        var urlProduct = '${pageContext.request.contextPath}/enterpriseManage/toProductList.action'+entInfoIdParamStr;
	        addTab(containerId, titleProduct, urlProduct);
	        // 企业宣传资料
	        var titlePublicizeMaterial = '企业宣传资料';
	        var urlPublicizeMaterial = '${pageContext.request.contextPath}/enterpriseManage/toPublicizeMaterialList.action'+entInfoIdParamStr;
	        addTab(containerId, titlePublicizeMaterial, urlPublicizeMaterial);
	        // 企业质量承诺书
	        var titleQualityPromise = '企业质量承诺书';
	        var urlQualityPromise = '${pageContext.request.contextPath}/enterpriseManage/toQualityPromiseList.action'+entInfoIdParamStr;
	        addTab(containerId, titleQualityPromise, urlQualityPromise);
	        // 企业获奖信息
	        var titleAward = '企业获奖信息';
	        var urlAward = '${pageContext.request.contextPath}/enterpriseManage/toAwardList.action'+entInfoIdParamStr;
	        addTab(containerId, titleAward, urlAward);
	        
	        
	        $('#'+containerId).tabs('select', 0);
		}// End Of displayOtherTabs()
		
		/*
			在指定的Tab容器中添加标签页
		*/
		function addTab(containerId, title, url){
			if ($('#'+containerId).tabs('exists', title)){
				$('#'+containerId).tabs('select', title);
			} else {
				var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
				$('#'+containerId).tabs('add',{
					title:title,
					content:content,
					closable:false
				});
			}
		}// End Of addTab()
		
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
		
		
	//页面加载初始化
$(function(){
	//企业多个联系人加载
	var entInfoId = ${enterpriseInfo.nenterpriseid};
  		$.ajax({
  			type:'post',
  			async:false,
  			url:'${appPath}/membermanage/getLinkPerson.action',
  			data:{'entInfoId': entInfoId},
  			dataType:'json',
  			success:function(d){
  				var table = $('#linkp');
  				for(var i=0;i<d.length;i++){
   				var tr = '';
   				tr += '<tr><td align=\"right\" width=\"10%\">联系人：</td><td width=\"20%\"><input name=\"ccontractperson\" type=\"text\"  value=\"'+d[i].linkPersonName+'\"/></td>';
   				tr += '<td align=\"right\" width=\"10%\">联系人手机：</td><td width=\"20%\"><input name=\"ccontractpersonphone\" type=\"text\"  value=\"'+d[i].linkPhone+'\"/></td>';
   				tr += '<td width=\"10%\"></td><td width=\"20%\"></td></tr>';//这两列空白内容为使页面布局一致
   				table.append(tr);
  				}
  			}
  		});
  		
	$.each($("#dataForm :input"),function(){
		$(this).attr("disabled","disabled");
	});
	
	var input=document.getElementById('moreLink');
	input.removeAttribute('disabled');
	
	
	
}); 	
	</script>
    
  </body>
</html>