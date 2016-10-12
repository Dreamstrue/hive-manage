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
		queryData();
		//显示查询记录
		$("#btn_record").click(function(){
			$.ajax({
				type : 'get',
				async : false,
				url : '${pageContext.request.contextPath}/integralOil/queryPrizeRecord.action',
				data : {
					'winPrizeId':$("#winPrizeId").val()
				},
				dataType : 'json',
				success : function(d) {
					var head =  "<table id='tab_record' class='table1'>"+
										"<tr><td align='center' colspan='4' style='font-weight : bold; line-height : 20px;'>兑奖详细记录表</td></tr>"+
								 "<tr><td align='center' style='width:25%;font-weight : bold; line-height : 20px;'>奖品名称</td>" +
					 			 "<td align='center' style='width:20%;font-weight:bold;line-height : 20px;'>兑奖人</td>" + 
								 "<td align='center' style='width:25%;font-weight : bold; line-height : 20px;'>兑奖人电话</td>" +
								 "<td align='center' style='width:50%;font-weight : bold; line-height : 20px;'>兑奖日期</td></tr>" 
					$("#div_tab").append("<div align='center'></div>");
					$("#div_tab").append(head);
					
					$.each(d,function(k){
						var content =	"<tr><td align='center' >"+this.prizeName+"</td>" + 
										"<td align='center' >"+this.prizeUser+"</td>" +
										"<td align='center' >"+this.prizePhone+"</td>" +
						 				"<td align='center' >"+this.createTime+"</td></tr>"
						$("#tab_record").append(content);
					});
					$("#div_tab").append("</table>");
					$("#btn_record").hide();			
				}
			});		
		});
	});

	function queryData(){
			$.ajax({
			type : 'get',
			async : false,
			url : '${pageContext.request.contextPath}/integralOil/queryWinPrizeInfo.action',
			data : {
				'winPrizeId':$("#winPrizeId").val()
			},
			dataType : 'json',
			success : function(d) {
				$("#warning").hide();
				$("#tab_record").remove();
				if(d!=null){
					/***评论信息**/
					$("#surveyName").text(d.surveyName);
					$("#industryName").text(d.industryName);
					$("#entityName").text(d.entityName);
					$("#voteUserName").text(d.voteUserName);
					$("#mobilePhone").text(d.mobilePhone);
// 					$("#idCard").text(d.idCard);
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
					if(d.istrue=="yes"){
					 $("#btn_record").show();
				    }else{
				     $("#btn_record").hide();
				    }
				}else{
					$("#div_sn").hide();
					$("#div_survery").hide();
					$("#btn_record").hide();
					$("#tab_record").remove();
					$("#warning").show();
					$("#warning").text("中奖信息未找到，请核实SN码和手机号是否正确！");
				}
			},
			error: function(robj) {
				$("#div_sn").hide();
				$("#div_survery").hide();
				$("#btn_record").hide();
				$("#warning").show();
				$("#warning").text("中奖信息未找到，请核实SN码和手机号是否正确！");
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
				<div id="div_survery"  border="1">
						<input type="hidden" id="winPrizeId" value="${winPrizeId }"/>
						<div align="center"  >
							<table class="table1" name="exchange-tb1" align="center" >
							<thead>
								<th colspan="2">评价信息详情</th>
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
<!-- 							  <tr> -->
<!-- 							    <td align="center"> <strong>评论人身份证号</strong></td> -->
<!-- 							    <td align="left" id="idCard"></td> -->
<!-- 							    </tr> -->
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
								<th colspan="2">中奖信息详情表</th>
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
							  <!--  
							  <tr>
							    <td align="center"> <strong>截至时间</strong></td>
							    <td align="left" id="endDate"></td>
							  </tr>
							  -->
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
		                <a href="#" name="btn_record" id="btn_record" class="easyui-linkbutton" style="width:80px;height:25px"><span style="font-size:16px">兑奖记录</span></a>
		            </div>
					<div id="div_tab" style="text-align:center;margin-top:15px">
<!-- 						<table id='tab_record' class='table1'> -->
<!-- 							<thead> -->
<!-- 								<tr> -->
<!-- 									<td colspan='4' align='center' style='font-weight : bold; line-height : 20px;height:30px;'>兑奖详细记录表</td> -->
<!-- 								</tr> -->
<!-- 								<tr> -->
<!-- 									 <td align='center' style='width:25%;font-weight : bold; line-height : 20px;'>奖品名称</td> -->
<!-- 						 			 <td align='center' style='width:20%;font-weight:bold;line-height : 20px;'>兑奖人</td>  -->
<!-- 									 <td align='center' style='width:25%;font-weight : bold; line-height : 20px;'>兑奖人电话</td> -->
<!-- 									 <td align='center' style='width:50%;font-weight : bold; line-height : 20px;'>兑奖日期</td> -->
<!-- 								</tr> -->
<!-- 							 </thead> -->
<!-- 						</table> -->
		            </div>
</div>
</body>
</html>