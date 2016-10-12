<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<link rel="stylesheet" href="${appPath}/resources/js/plugin/Jcrop/css/jquery.Jcrop.min.css" type="text/css"></link>
<script type="text/javascript" src="${appPath}/resources/js/plugin/Jcrop/js/jquery.Jcrop.min.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/common/jQuery.UtrialAvatarCutter.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/defined/jquery.upload.js"></script>
<script type="text/javascript">
	function check(){
		$('#form').form('submit',{
			url:'${appPath}/cashPrizeInfo/update.action',
			success:function(result){
				var r = $.parseJSON(result);
				$.messager.show({
					title:'提示',
					msg:r.msg
				});
				if(r.status){
					setTimeout(function(){
						location.href='${appPath}/cashPrizeInfo/manage.action';
					},1000);
				}
			}
		});
	}

	
</script>
<div align="left" style="width:75%; height: 100%;">
<div id="div1" >当前位置：奖品管理&nbsp;&gt;&nbsp;修改</div>
<div id="div2" align="right"><a id="btn" href="${appPath}/cashPrizeInfo/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<div align="left" >
	<table class="integrity_table" style="width: 100%">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
					    <input type="hidden" id="id" name="id" value="${vo.id}"/>
						<input type="hidden" id="createId" name="createId" value="${vo.createId}" />
						<input type="hidden" id="createTime" name="createTime" value="${vo.createTime}" />
						<input type="hidden" id="valid" name="valid" value="${vo.valid}"/>
						<table >
							<tr>
								<td class="tt">奖品名称：</td>
								<td><input name="prizeName"  id="prizeName" value="${vo.prizeName}" class="easyui-validatebox" data-options="required:true" style="width: 480px;"/></td>
							</tr>
							<tr>
								<td class="tt">中奖人：</td>
								<td>
									<input id="prizeUser" name="prizeUser" value="${vo.prizeUser}" class="easyui-validatebox" data-options="required:true " style="width:480px;" />
								</td>
							</tr>
							<tr>
								<td class="tt">中奖人电话：</td>
								<td>
								<input id="prizePhone" name="prizePhone" value="${vo.prizePhone}" class="easyui-validatebox" data-options="required:true " style="width:480px;" />
								</td>
							</tr>
							<tr>
								<td class="tt">中奖SN编号：</td>
								<td>
								<input id="prizeSN" name="prizeSN" value="${vo.prizeSN}" class="easyui-validatebox" data-options="required:true " style="width:480px;" />
								</td>
							</tr>
							<tr>
								<td class="tt">领取数量：</td>
								<td><input name="prizeNum"  id="prizeNum" value="${vo.prizeNum}" class="easyui-validatebox" data-options="required:true" style="width: 480px;"/></td>
							</tr>
							
							<tr>
								<td class="tt">领奖备注：</td>
								<td>
									<textarea name="remark" id="remark"   rows="5" cols="20" style="width:480px;height:100px;">${vo.remark}</textarea>
								</td>
							</tr>
						</table>
						<p align="right" style="padding-right: 70px;">
								<input class="button" type="button" value="提交" onclick="check()"/>
				   			</p>
					</form>
				</div>
			</td>
		</tr>
	</table>
</div>
</div>
