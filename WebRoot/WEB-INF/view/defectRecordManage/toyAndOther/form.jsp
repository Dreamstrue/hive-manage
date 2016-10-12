<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../../common/inc_defact.jsp"></jsp:include>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=0">
	<title>国家质检总局缺陷产品管理中心</title>
	
	<script type="text/javascript">
		$(function() {
			//提交事件
			$("#submitBtn").click(function() {
				//表达验证
				var result = formValidate();
				if(result) {
					$("#form1").submit();
				}
			})
		})
	</script>
</head>
<body>
	<div class="main">
		<form method="post" id="form1" action="${pageContext.request.contextPath}/defectOfToyAndOthers/formSubmit.action">
			<!--内容区-->
			<div class="wel_box">
				<div class="title">${titleShowStr}缺陷信息</div>
				<div id="div2" class="box1">
					<div class="b1_tit">缺陷描述信息</div>
					<input type="hidden" id=peporttype name="peporttype" value="${peporttype}"> 
					<input type="text" id="prodName" name="prodName" value="*产品名称" maxlength="200" class="in1 in11 notNull">
					<select id="prodCountry" name="prodCountry" class="in1 in11">
						<option value="">请选择生产国别</option>
						<option value="1">国内生产</option>
						<option value="2">国外生产</option>
					</select>
					<input type="text" id="prodTrademark" name="prodTrademark" value="*产品商标" maxlength="200" class="in1 in11 notNull"> 
					<input type="text" id="prodModel" name="prodModel" value="型号" maxlength="200" class="in1 in11">
					<input type="text" id="prodType" name="prodType" value="类别" maxlength="200" class="in1 in11"> 
					<input type="text" id="buyshopName" name="buyshopName" value="商店名称" maxlength="200" class="in1 in11"> 
					<input type="text" id="buyshopAdress" name="buyshopAdress" value="商店地址" maxlength="200" class="in1 in11">
					<p class="p1">购买日期：</p><input type='text' name=buyDate id='buyDate' class="in1 in11 notNull" data-options="required:true" readonly="readonly" onfocus="WdatePicker({minDate:'%y-%M-%d'})"  />
					<p class="p1">*缺陷描述：</p><p></p>
					<textarea id="defectDescription" name="defectDescription" class="in1 in12 notNull" maxlength="240">请描述产品的问题缺陷</textarea>


					<div class="b1_tit">联系人</div>
					<div style="height: 40px;">
						<input type="text" id="reportusername" name="reportusername" value="*姓名" maxlength="200" class="in1 fl notNull"> 
						<input type="text" id="reportuserphone" name="reportuserphone" value="*手机" maxlength="200" class="in1 fr notNull">
					</div>
					<div style="height: 40px;">
						<input type="text" id="reportuseremail" name="reportuseremail" value="Email" maxlength="200" class="in1 fl"> 
						<input type="text" id="username" name="username" value="使用者姓名" maxlength="200" class="in1 fr">
					</div>
					<div style="height: 40px;">
						<select size="1" class="in1 fl notNull" name="cprovincecode" id="cprovincecode">
                  			<option value="">*所在省份</option><option value="p1">北京市</option><option value="p2">天津市</option><option value="p3">上海市</option><option value="p4">重庆市</option><option value="p5">河北省</option><option value="p6">山西省</option><option value="p7">台湾省</option><option value="p8">辽宁省</option><option value="p9">吉林省</option><option value="p10">黑龙江省</option><option value="p11">江苏省</option><option value="p12">浙江省</option><option value="p13">安徽省</option><option value="p14">福建省</option><option value="p15">江西省</option><option value="p16">山东省</option><option value="p17">河南省</option><option value="p18">湖北省</option><option value="p19">湖南省</option><option value="p20">广东省</option><option value="p21">甘肃省</option><option value="p22">四川省</option><option value="p23">贵州省</option><option value="p24">海南省</option><option value="p25">云南省</option><option value="p26">青海省</option><option value="p27">陕西省</option><option value="p28">广西壮族自治区</option><option value="p29">西藏自治区</option><option value="p30">宁夏回族自治区</option><option value="p31">新 疆维吾尔自治区</option><option value="p32">内蒙古自治区</option><option value="p33">澳门特别行政区</option><option value="p34">香港特别行政区</option></select>
						<input type="text" id="postCode" name="postCode" value="*邮政编码" maxlength="200" class="in1 fr notNull">
					</div>
					<div style="height: 40px;">
						<input type="text" id="reportadress" name="reportadress" value="*地址" maxlength="200" class="in1 in11 notNull"> 
					</div>
					<div class="button2">		
						<input type="button" style="float:none;" value="提交" id="submitBtn"> 
						<input type="reset" style="float:none;" value="重 置" name="return">
			    	</div>
				</div>
			</div>
		</form>
	</div>
</body>

</html>
