<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<jsp:include page="../common/inc.jsp" />
<style>
.box1{
	width:90%; height:auto;
	overflow:hidden;
	margin:10px auto;
	padding:0 1%;
	background: #E0F2F9;
	}
.table-12 {
  width:95%;
	background-color: #E4F4F4;
	border: expression(this.border =   0);
	cellspacing: expression(this.cellSpacing =   1);
	cellpadding: expression(this.cellPadding =   2);
	border-collapse: collapse;
	margin-top: 5px;
}
/*两列或四列表单域名称样式,自动加载*/
.table-12 td {
	border: solid 1px #CCCCCC; 
	background-color: #FFFFFF;
	line-height:40px;
	font-size:18px;
	padding: 0px 20px;
}
/*两列或四列表单域名称样式,自动加载*/
.table-3 {
  width:95%;
	background-color: #E4F4F4;
	border: expression(this.border =   0);
	cellspacing: expression(this.cellSpacing =   1);
	cellpadding: expression(this.cellPadding =   2);
	border-collapse: collapse;
	margin-top: 10px;
}
.table-3 td {
	border: solid 1px #CCCCCC; 
	background-color: #FFFFFF;
	line-height:30px;
	font-size:15px;
	padding-left:2px;
}
/*两列或四列表单域名称样式,自动加载*/
.btn1 {
	background-color:#3A6EA5;
	outline: none;
	color: #FFFFFF;
	font-size: 1.5em;
	margin: 20px 20px;
	text-align:center;
	width: 10%;
	height: 45px;
	line-height:50px;
	border-radius:15px;
	border: none
	
}
input{
height:30px; 
width:450px; 
text-transform:uppercase; 
font-size:25px;
}

.Ent_menu {
    margin: auto auto;
    overflow: hidden;
}
.Ent_footer {
    border-top: 3px solid #2c3984;
    display: block;
}
.search_tab{border-collapse: collapse;margin-top: 20px;}
.search_tab td{ line-height: 55px;}
</style>
<script type="text/javascript">
$(function(){
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
	$("#div_sn").hide();
	$("#div_survery").hide();
	$("#btn_record").hide();
	$("#btn_submit").click(
		function(){
		if($("#sn").val()==""){
			$("#warning").text("SN编号不能为空")
		}else if(!myreg.test($("#phone").val())){
			$("#warning").text("请输入正确的手机号")
		}else{
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
				$("#tab_record").remove();
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
		});
	//显示查询记录
	$("#btn_record").click(function(){
		$.ajax({
			type : 'get',
			async : false,
			url : '${pageContext.request.contextPath}/integralOil/cashPrizeRecord.action',
			data : {
				'SNParam' : $("#sn").val()
			},
			dataType : 'json',
			success : function(d) {
				var head =  "<table id='tab_record' width='100%' border='0' align='center' class='table-3'>"+
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

</script> 
</head>
<body style="background: #E4F4F4">
<div class="box1" >
		  	<div style="background: #E0F2F9;width: 100%;margin: 0px auto;border-radius:8px;" class="report outbox" data-vip="0">
				<div align="center" style="line-height: 50px" >
						<strong><font size="6">中奖信息查询</font></strong>
				</div>
				<div class="Ent_footer"></div>
				<div align="center" style="text-align: center;margin-top: 10px;line-height: 40px">
						<font size="3">描述：通过您手中二维码下面的SN编号和抽奖时预留手机号来查询您的中奖和兑奖信息</font>
				</div>
				<div align="center">
					<form action="javascript:void(0);" id="manage_search_form" >
					    <table cellpadding="0" cellspacing="0" class="search_tab" width="100%" >
					      <tr>
					       <td width="40%" align="right"><a style="font-size:25px;">请输入卡片SN码 ：</a></td>
					       <td width="60%"><input type="text" name="sn" id="sn" onblur="this.value=this.value.toUpperCase()" placeholder="SN码为不加空格的纯数字" ></input></td>
					      </tr>
					       <tr>
					       <td  width="20%" align="right"><a style="font-size:25px;">请输入手机号码 ：</a></td>
					       <td><input type="text" name="phone" id="phone" onblur="this.value=this.value.toUpperCase()" placeholder="抽奖时预留的手机号" ></input></td>
					      </tr>
					      <tr>
					       <td colspan="2" align="center"><input type="button" name="btn_submit" id="btn_submit" value="查  询" class="btn1"/></td>
					      </tr>
					    </table>
					</form>
				</div>
				<div align="center" ><font id="warning" color="red"  size="5"></font></div>
				<div class="Ent_menu"  id="div_survery" >
						<div align="center"  >
							<table class="table-12" name="exchange-tb" align="center" >
							<tr><td align='center' colspan='7' style='font-weight : bold; line-height :30px;'>评价信息详情标</td></tr>
							  <tr>
							    <td align="center" width="25%"> <strong>问卷主题</strong></td>
							    <td align="center"  width="15%"> <strong>问卷类型</strong></td>
							    <td align="center"  width="12%"> <strong>问卷对象</strong></td>
							    <td align="center"  width="10%"> <strong>评论人</strong></td>
							    <td align="center"  width="10%"> <strong>评论人电话</strong></td>
							    <td align="center"  width="18%"> <strong>评论人身份证号</strong></td>
							    <td align="center"  width="10%"> <strong>评论时间</strong></td>
							  </tr>
							  <tr>
							    <td align="center" id="surveyName"></td>
							    <td align="center" id="industryName"></td>
							    <td align="center" id="entityName"></td>
							    <td align="center" id="voteUserName"></td>
							    <td align="center" id="mobilePhone"></td>
							    <td align="center" id="idCard"></td>
							    <td align="center" id="createTime"></td>
							  </tr>
							</table>
						</div>
					</div>
				<div class="Ent_menu"  id="div_sn" >
						<div align="center"  >
							<table class="table-12" name="exchange-tb" align="center" >
							<tr><td align='center' colspan='2' style='font-weight : bold; line-height :30px;'>中奖信息详情表</td></tr>
							  <tr>
							    <td align="center" style="width:16%"> <strong>奖品名称</strong></td>
							    <td align="left" id="prizeName"></td>
							  </tr>
							  <tr>
							    <td align="center" style="width:16%"> <strong>奖品提供方</strong></td>
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
				<div align="center" style="text-align: center;">
					<input type="button" name="btn_record" id="btn_record" value="兑奖记录" class="btn1"/>
				</div>
				<div id="div_tab" align="center"></div>
</div>
</body>
</html>