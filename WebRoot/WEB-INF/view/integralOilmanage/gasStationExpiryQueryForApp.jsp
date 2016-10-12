<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>中奖查询页面</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/mobile.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/icon.css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.easyui.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.easyui.mobile.js" charset="UTF-8"></script>
    
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
	<!-- easyui portal插件 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/plugin/jquery-easyui-portal/portal.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/plugin/jquery-easyui-portal/jquery.portal.js" charset="UTF-8"></script>
	
	<!-- textarea 中的富编辑 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/plugin/kindeditor/themes/default/default.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/plugin/kindeditor/plugins/code/prettify.css" />
	<script charset="UTF-8" src="${pageContext.request.contextPath}/resources/js/plugin/kindeditor/kindeditor.js"></script>
	<script charset="UTF-8" src="${pageContext.request.contextPath}/resources/js/plugin/kindeditor/lang/zh_CN.js"></script>
	<script charset="UTF-8" src="${pageContext.request.contextPath}/resources/js/plugin/kindeditor/plugins/code/prettify.js"></script>
	<!-- 自己定义的样式和JS扩展 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/syCss.css" type="text/css"></link>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/icon.css" type="text/css"></link>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common/common.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common/jquery.cookie.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common/util.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common/DateUtil.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.form.js"></script>

<script type="text/javascript">
	$(function(){
		var isExistPrizeSN_ = "${isExistPrizeSN}";
		$("#btn_record").hide();
		$("#div_tab_1").hide();
		$("#div_tab_2").hide();
		$("#div_sn").hide();
		$("#div_survery").hide();
		if(isExistPrizeSN_=="true"){
			$.messager.alert("提示","此卡片已经兑换过奖品！请点击兑奖详情进行查看！","info");
			$("#div_tab_1").hide();
			$("#duijiang").hide();
			$("#btn_record").show();
		}
		//获取中奖信息和评价信息
		queryData();
		//获取兑奖记录
		$("#btn_record").click(function(){
			queryExpiryData();
		});
		//进行兑奖
		$("#btn_duijiang").click(function(){
			$("#duijiang").hide();
			queryExpiryData();
		});
	});
	//获取中奖信息和评价信息
	function queryData(){
			 $.ajax({
			type : 'get',
			async : false,
			url : '${pageContext.request.contextPath}/integralOil/queryExchange.action',
			data : {
				'sn' : $("#sn").val(),
				'phone':$("#phone").val()
			},
			dataType : 'json',
			success : function(d) {
				$("#warning").hide();
				if(d!=null){
					/***评论信息**/
					$("#surveyName").text(d.surveyName);
					$("#industryName").text(d.industryName);
					$("#entityName").text(d.entityName);
					$("#voteUserName").text(d.voteUserName);
					$("#mobilePhone").text(d.mobilePhone);
					$("#idCard").text(d.idCard);
					$("#createTime").text(d.createTime);
					/***中奖信息**/
					$("#prizeName").text(d.prizeName);
					$("#prizeSupplier").text(d.prizeSupplier);
					$("#prizeNum").text(d.prizeNum);
					$("#prizeUser").text(d.prizeUser);
					$("#prizePhone").text(d.prizePhone);
					$("#prizeAddress").text(d.prizeAddress+"(仅限参与质量评价活动的站点，详细信息请关注【质讯通微信公众账号:zlcxxh】查看【活动详情】!)");
					//$("#endDate").text(d.endDate);
					$("#isCash").text(d.isCash);
					$("#div_sn").show();
					$("#div_survery").show();
				}else{
					$("#div_sn").hide();
					$("#div_survery").hide();
					$("#btn_record").hide();
					$("#warning").show();
					$("#duijiang").hide();
					$("#warning").text("中奖信息未找到，请核实SN码和手机号是否正确！");
				}
			},
			error: function(robj) {
				$("#div_sn").hide();
				$("#div_survery").hide();
				$("#btn_record").hide();
				$("#warning").show();
				$("#duijiang").hide();
				$("#warning").text("中奖信息未找到，请核实SN码和手机号是否正确！");
			}
		});
	}
	//添加兑奖记录
	function addRecord(){
		$('#myform').form('submit',{
			url:'${pageContext.request.contextPath}/cashPrizeInfo/insert.action',
			success:function(result){
				var r = $.parseJSON(result);
				if(r.status){
					$.messager.alert("提示",r.msg,"info");
					setTimeout(function(){
						$("#duijiang").hide();
						queryExpiryData();
					},1000);
				}else{
					$.messager.alert("提示",r.msg,"info");
				}
			}
		});
	}
	//获取中奖纪录信息
	function queryExpiryData(){
// 	alert(33);
		$.ajax({
		type : 'get',
		async : false,
		url : '${pageContext.request.contextPath}/integralOil/cashPrizeOneRecord.action',
		data : {
			'SNParam' : $("#sn").val(),
			'createId' : '${createId}',
			'createName' : '${createName}',
			'createDeptId' : '${createDeptId}',
			'createDeptName' : '${createDeptName}'
		},
		dataType : 'json',
		success : function(d) {
// 			alert(d);
// 			var r = $.parseJSON(d);
// 			alert(d.id);
			
			if(d.id!=null&&d.id!=""){
				$("#div_tab_1").hide();
				 $("#div_tab_2").show();
				 if(d.createName==null||d.createName==""){
				 	$("#createName_").text("无");
				 }else{
				 	$("#createName_").text(d.createName);
				 }
				 if(d.createName==null||d.createName==""){
				 	$("#createOrgName_").text("无");
				 }else{
					$("#createOrgName_").text(d.createOrgName); 					   
				 }
				 if(d.createName==null||d.createName==""){
				 	 $("#prizePlace_").text("");
				 }else{
					 $("#prizePlace_").text(d.prizePlace);  					   
				 }
				 $("#prizeName_").text(d.prizeName);  					   
				 $("#prizeUser_").text(d.prizeUser);  						   
				 $("#prizePhone_").text(d.prizePhone);  					   
				 $("#remark_").text(d.remark);
			}else{
				$("#div_tab_1").show();
				$("#createId").val(d.createId);
				$("#createOrgId").val(d.createOrgId);
				$("#createName").textbox('setValue', d.createName);
				$("#createOrgName").textbox('setValue', d.createOrgName);
				$("#prizeSN").textbox('setValue', d.prizeSN);
				$("#prizeUserStr").textbox('setValue', d.prizeUser);
				$("#prizePhoneStr").textbox('setValue', d.prizePhone);
			}
			$("#btn_record").hide();		
		}
	});		
	}
</script> 
<style type="text/css">

.table1{
width:100%;
border: solid 1px #ddd;
border-collapse: collapse;
}
.table1 td{
border: solid 1px #ddd;
height:20px;
}
.table1 th{
height:30px;
}

</style>
</head>
<body>
    <div class="easyui-navpanel"  style="position:relative;padding:1px">
        <header style="padding:1px">
            <div class="m-toolbar">
                <div class="m-title">中奖查询</div>
            </div>
        </header>
    			<div style="text-align:center;margin-top:15px">
			            <font id="warning" color="red"  size="2"></font>
			    </div>
				<div id="div_survery"  border="1">
				<input type = "hidden" id="sn" name="sn" value="${sn }">
				<input type = "hidden" id="phone" name="phone" value="${phone }">
						<div align="center"  >
							<table class="table1" name="exchange-tb1" align="center" >
							<thead>
								<th colspan="2" align="center">评价信息详情</th>
							</thead>
							  <tr>
							    <td align="center" width="25%"> <strong>问卷主题</strong></td>
							    <td align="left" width="75%" id="surveyName"></td>
							    </tr>
							  <tr>
							    <td align="center"> <strong>问卷类型</strong></td>
							    <td align="left" id="industryName"></td>
							    </tr>
							  <tr>
							    <td align="center"> <strong>问卷对象</strong></td>
							    <td align="left" id="entityName"></td>
							    </tr>
							  <tr>
							    <td align="center"> <strong>评论人</strong></td>
							    <td align="left" id="voteUserName"></td>
							    </tr>
							  <tr>
							    <td align="center"> <strong>评论人电话</strong></td>
							    <td align="left" id="mobilePhone"></td>
							    </tr>
							  <tr>
							    <td align="center"> <strong>评论人身份证号</strong></td>
							    <td align="left" id="idCard"></td>
							    </tr>
							  <tr>
							    <td align="center"> <strong>评论时间</strong></td>
							    <td align="left" id="createTime"></td>
							  </tr>
							</table>
						</div>
					</div>
					<div style="text-align:center;margin-top:15px">
				<div class="Ent_menu"  id="div_sn" >
						<div align="center"  >
							<table class="table1" name="exchange-tb" align="center" >
								<thead>
								<th colspan="2" align="center">中奖信息详情表</th>
								</thead>
							  <tr>
							    <td align="center" width="25%"> <strong>奖品名称</strong></td>
							    <td align="left"  width="75%" id="prizeName"></td>
							  </tr>
							   <tr>
							    <td align="center"> <strong>奖品提供方</strong></td>
							    <td align="left" id="prizeSupplier"></td>
							  </tr>
							  <tr>
							    <td align="center"> <strong>奖品数量</strong></td>
							    <td align="left" id="prizeNum"></td>
							  </tr>
							  <tr>
							    <td align="center"> <strong>中奖人</strong></td>
							    <td align="left" id="prizeUser"></td>
							  </tr>
							  <tr>
							    <td align="center"> <strong>中奖人电话</strong></td>
							    <td align="left" id="prizePhone"></td>
							  </tr>			  
							  <tr>
							    <td align="center"> <strong>领奖地址</strong></td>
							    <td align="left" id="prizeAddress"></td>
							  </tr>
							  <tr>
							    <td align="center"> <strong>是否兑奖</strong></td>
							    <td align="left" id="isCash">
							    </td>
							  </tr>
							</table>
						</div>
					</div>
		            </div>
					<div style="text-align:center;margin-top:15px">
		                <a href="#" name="btn_record" id="btn_record" class="easyui-linkbutton" style="width:80px;height:25px"><span style="font-size:16px">兑奖详情</span></a>
		            </div>
		            <div id="duijiang" style="text-align:center;margin-top:15px">
		                <a href="#" name="btn_duijiang" id="btn_duijiang" class="easyui-linkbutton" style="width:80px;height:25px"><span style="font-size:16px">兑奖</span></a>
		            </div>
					<div id="div_tab_1" style="text-align:center;margin-top:15px">
				        <form id="myform" method="post" enctype="multipart/form-data">
							<input id="createId" name="createId" type="hidden" value="${createId}"/>
							<input id="createOrgId" name="createOrgId" type="hidden" value="${createOrgId}"/>
							<div style="text-align:center;margin-top:15px">
						            <font id="warning" color="red"  size="2"><strong>请提交以下信息添加兑奖详情！</strong></font>
						    </div>
					        <div style="text-align:center;margin-top:5px">
								<input class="easyui-textbox" name="createName" id="createName" value="${createName }" readonly="readonly"  style="width:60%;">
							</div>
							<div style="text-align:center;margin-top:5px">
								<input class="easyui-textbox" name="createOrgName" id="createOrgName" value="${createOrgName }" readonly="readonly" style="width:60%">
							</div>
							<div style="text-align:center;margin-top:5px">
								<input class="easyui-combobox" name="prizeName" id="prizeName" data-options="prompt:'请选择奖品名称',url:'${pageContext.request.contextPath}/prizeInfo/findAllPrizeInfo.action',valueField:'prizeName',textField:'prizeName',required:true,editable:false" style="width:60%">
							</div>
							<div style="text-align:center;margin-top:5px">
								<input class="easyui-textbox" name="prizeUser" id="prizeUserStr" value="${prizeUser }" prompt="请输入中奖人名称" required="required" style="width:60%">
							</div>
					        <div style="text-align:center;margin-top:5px">
								<input class="easyui-textbox" name="prizePhone" id="prizePhoneStr" value="${prizePhone }" validType="mobile" prompt="请输入中奖人电话" required="true" style="width:60%;">
							</div>
					        <div style="text-align:center;margin-top:5px">
								<input class="easyui-textbox" name="prizeSN" id="prizeSN" validType="number" prompt="请输入中奖SN编号" readonly="readonly" required="true" style="width:60%;">
							</div>
					        <div style="text-align:center;margin-top:5px">
								<input class="easyui-textbox" name="prizeNum" id="prizeNum" validType="number" prompt="请输入领取数量" required="true" style="width:60%;">
							</div>
							<div style="text-align:center;margin-top:5px">
								<input class="easyui-textbox" data-options="multiline:true" name="prizePlace" id="prizePlace" prompt="请输入领取地址" style="width:60%;height:50px;">
							</div>
							<div style="text-align:center;margin-top:5px">
								<input class="easyui-textbox" data-options="multiline:true" name="remark" id="remark" prompt="请输入备注" style="width:60%;height:50px;">
							</div>
					        <div style="text-align:center;margin-top:10px;margin-bottom:10px">
					               <a href="javascript:void(0);" name="btn_submit" id="btn_submit" class="easyui-linkbutton" style="width:60px;height:25px" onclick="addRecord()"><span style="font-size:16px">提交</span></a>
					        </div>
						</form>
		            </div>
		            <div id="div_tab_2" style="text-align:center;margin-top:15px">
						<div align="center"  >
							<table class="table1" name="exchange-tb" align="center" >
								<thead>
								<th colspan="2" align="center">兑奖详情表</th>
								</thead>
							  <tr>
							    <td align="center" width="25%"> <strong>创建人</strong></td>
							    <td align="left"  width="75%" id="createName_"></td>
							  </tr>
							   <tr>
							    <td align="center"> <strong>创建人部门</strong></td>
							    <td align="left" id="createOrgName_"></td>
							  </tr>
							  <tr>
							    <td align="center"> <strong>中奖名称</strong></td>
							    <td align="left" id="prizeName_"></td>
							  </tr>
							  <tr>
							    <td align="center"> <strong>中奖人</strong></td>
							    <td align="left" id="prizeUser_"></td>
							  </tr>
							  <tr>
							    <td align="center"> <strong>中奖人电话</strong></td>
							    <td align="left" id="prizePhone_"></td>
							  </tr>			  
							  <tr>
							    <td align="center"> <strong>领奖地址</strong></td>
							    <td align="left" id="prizePlace_"></td>
							  </tr>
							  <tr>
							    <td align="center"> <strong>备注</strong></td>
							    <td align="left" id="remark_">
							    </td>
							  </tr>
							</table>
						</div>
		            </div>
		            
		            
</div>
</body>
</html>