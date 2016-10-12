<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>积分管理</title>
<jsp:include page="../common/inc.jsp"></jsp:include>
</head>
<body>
<script type="text/javascript">
$('#prizeName').combobox({
	 onSelect: function () {
		 var prizeId = $('#prizeName').combobox('getValue');
			var url = '${appPath}/prizeInfo/getPrizeInfoById.action?id='+prizeId;
			$.ajax({ 
				 url: url, 
				 type:'post',    
				 cache:false,    
				 dataType:'json',    
				 success:function(data) {
			 		$('#prizeNums').val(data.prizeNum);   
			 		$('#excIntegral').val(data.excIntegral);  
			 		$('#validDate').val(data.validDate);  
			 		$('#prizeExplain').val(data.prizeExplain); 
			 		$('#prizeCateId').val(data.prizeCateId); 
			 		$('#prizeId').val(data.id); 
				 },    
				 error : function() {    
				 	alert("异常！");    
				 }    
	      	});
	}
})
	//选择规格信息 填充相应的价格单价
	function onprize(){
		alert("======");
		
		
	}
</script>
	<div align="center" style="width: 100%">
	<form id="integral_exchange_add_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%" id="tab">
		<input  type="hidden" name="userId" id="userId" value="${userId}"/>
		<input  type="hidden" name="prizeCateId" id="prizeCateId" value="${t.prizeCateId}"/>
		<input  type="hidden" name="prizeId" id="prizeId" value="${t.id}"/>
		     <tr>
		         <font color="#1E90FF" size="5">奖品兑换 </font><br>
		  		 <font color="red">提示：由于加油站的特殊性,所以加油站的积分只能兑换加油站相关的奖品！</font>
		     </tr>
			<tr>
				<th style="width: 70px;">可用积分</th>
				<td>
				   <input id="currentIntegral" name="currentIntegral" style="width: 300px;" value="${currentIntegral}" readonly="readonly" /> 
				</td>
			</tr>
			<tr>
				<th style="width: 70px;">奖品名称</th>
				<td>
					<input id="prizeName" name="prizeName" class="easyui-combobox" data-options="url:'${appPath}/prizeInfo/allPrizeInfo.action',valueField:'id',textField:'prizeName',required:true,editable:false"  style="width:300px;" />
				</td>
			</tr>
			<tr>
				<th style="width: 70px;">奖品数量</th>
				<td>
					<input id="prizeNums" name="prizeNums" value="${t.prizeNum}" readonly="readonly" style="width: 300px;" /> 
				</td>
			</tr>
			<tr>
				<th>兑换积分</th>
				<td>
					<input id="excIntegral" name="excIntegral" value="${t.excIntegral}" readonly="readonly" style="width:300px;" />
				</td>
			</tr>
			<tr>
				<th style="width: 85px;">奖品有效期</th>
				<td>
					<input  id="validDate" name="validDate" value="${t.validDate}" readonly="readonly"  style="width: 300px;"/> 
				</td>
			</tr>
			<tr>
				<th style="width: 85px;">奖品描述</th>
				<td>
					<input type="text" id="prizeExplain" name="prizeExplain" readonly="readonly"  style="width: 300px;"/>
				</td>
			</tr>
			<tr>
				<th style="width: 70px;">兑换数量</th>
				<td>
					<input name="prizeNum" id="prizeNum" class="easyui-numberbox" data-options="required:true" style="width: 300px;" /> 
				</td>
			</tr>
			<tr>
				<th style="width: 85px;">收货人</th>
				<td>
					<input name="consignee" id="consignee" class="easyui-validatebox" data-options="required:true" validtype="name" style="width: 300px;"/> 
				</td>
			</tr>
			<tr>
				<th style="width: 105px;">手机号</th>
				<td>
					<input name="mobilePhone" id="mobilePhone" class="easyui-validatebox" validtype="mobile" data-options="required:true" style="width:300px;"/>
				</td>
			</tr>
			<tr>
				<th style="width: 105px;">收货地址</th>
				<td>
					<input name="address" id="address" class="easyui-validatebox"  data-options="required:true"  style="width: 300px;"/>
				</td>
			</tr>
			<tr>
				<th style="width: 70px;">备注说明</th>
				<td>
					<textarea  name="remark" id="remark"  style="width:300px; height:40px;font-size:12px;" class="easyui-validatebox" validType="maxLength[150]" invalidMessage="请输入不要超过150个字符"></textarea>​
				</td>
			</tr>
		</table>
	</form>
</div>
	
</body>
</html>
