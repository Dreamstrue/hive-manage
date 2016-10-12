<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<script type="text/javascript">
		$(function() {
			$('input[class="read"]').attr("disabled","disabled");
		});
	
	function check(){
		$('#form').form('submit',{
			url:'${appPath}/intend/approveAction.action',
			success:function(result){
				var r = $.parseJSON(result);
				$.messager.show({
					title:'提示',
					msg:r.msg
				});
				if(r.status){
					setTimeout(function(){
						location.href='${appPath}/intend/manage.action';
					},1000);
				}
			}
		});
	}
	
	
	
	
</script>
<div align="left" style="width:70%; height: 100%;">
<div id="div1" >当前位置：兑换管理&nbsp;&gt;&nbsp;订单审核</div>
<div id="div2" align="right" style="width:75%;"><a id="btn" href="${appPath}/intend/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<div align="left" style="width:75%">
	<table class="integrity_table" style="width: 100%">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<input type="hidden" id="id" name="id" value="${vo.id}"/>
						<table >
							<tr>
								<td class="tt">订单号：</td>
								<td><input name="intendNo"  id="intendNo" value="${vo.intendNo}" class="read" style="width: 400px;" disabled="disabled"/></td>
							</tr>
							<tr>
								<td class="tt">收货人：</td>
								<td><input name="consignee"  id="consignee" value="${vo.consignee}" class="read" style="width: 400px;"/></td>
							</tr>
							<tr>
								<td class="tt">手机号：</td>
								<td><input name="mobilePhone"  id="mobilePhone" value="${vo.mobilePhone}" class="read" style="width: 400px;"/></td>
							</tr>
							<tr>
								<td class="tt">收货地址：</td>
								<td><input name="address"  id="address" value="${vo.address}" class="read" style="width: 400px;"/></td>
							</tr>
							<tr>
								<td class="tt">奖品类别：</td>
								<td>
									<input id="prizeCateId" name="prizeCateId" value="${vo.prizeCateId}" class="easyui-combotree" data-options="url:'${appPath}/prizeCate/allPrizeTree.action',parentField:'parentId',lines:true " style="width:200px;" disabled="disabled"/>
								</td>
							</tr>
							<tr>
								<td class="tt">奖品名称：</td>
								<td>
									<input id="prizeName" name="prizeName" value="${vo.prizeName}" class="read" style="width:400px;" />
								</td>
							</tr>
							<tr>
								<td class="tt">奖品数量：</td>
								<td><input name="prizeNum"  id="prizeNum" value="${vo.prizeNum}" class="read" style="width: 200px;"/></td>
							</tr>
							<tr>
								<td class="tt">兑换积分：</td>
								<td><input name="excIntegral"  id="excIntegral" value="${vo.excIntegral}" class="read" style="width: 200px;"/></td>
							</tr>
							<tr>
								<td class="tt">申请时间：</td>
								<td>
									<input name="applyTime"  id="applyTime"  value='<fmt:formatDate value="${vo.applyTime}" pattern="yyyy-MM-dd HH:mm:ss" />'  class="read" style="width: 200px;"/>
									
								</td>
							</tr>
							<tr>
								<td class="tt">备注：</td>
								<td>
									<textarea name="remark" id="remark"  rows="8" cols="60" style="width:400px;height:50px;" disabled="disabled">${vo.remark}</textarea>
								</td>
							</tr>
							<tr>
								<td class="tt">订单状态：</td>
								<td>
									<select id="intendStatus" name="intendStatus" style="width: 150px;">
										<option value="2">审核通过</option>
										<option value="3">审核不通过</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="tt">审核意见：</td>
								<td>
									<textarea name="auditOpinion" id="auditOpinion"  rows="8" cols="60" style="width:400px;height:50px;"></textarea>
								</td>
							</tr>
						</table>
						<p align="right" style="padding-right: 100px;">
								<input class="button" type="button" value="提交" onclick="check()"/>
				   			</p>
					</form>
				</div>
			</td>
		</tr>
	</table>
</div>
</div>
