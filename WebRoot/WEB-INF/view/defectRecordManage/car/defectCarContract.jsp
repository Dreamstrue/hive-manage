<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<HTML>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=0">
<title>国家质检总局缺陷产品管理中心</title>
<jsp:include page="../../common/inc.jsp" />
<link href="${appPath}/resources/defect/css/common.css" rel="stylesheet" type="text/css">
<link href="${appPath}/resources/defect/css/index.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${appPath}/resources/defect/js/jqlearn.js"></script>
<script type="text/javascript" src="${appPath}/resources/defect/js/cardCode.js"></script>
<script type="text/javascript">
$(function(){
	$('.box1 input').focus(function(){
		this.value='';
	});
	$('.box1 input').blur(function(){
		if(this.value=='')
		this.value=this.defaultValue;
	});

	$('input:radio').click(function() {
		var value = $(this).attr('default');
		var name = $(this).attr('name');
		name = '#rbl'+name;
		$(name).val(value);
	}); 

	$('#txtCardCode').click(function(){
		var option = $('#ddlCardType').val();
		if(option==0){
			$("#ddlCardType").focus();
			$("#tishi221").show();
		}
	});
	
	$('#ddlCardType').change(function(){
		var option = $(this).val();
		if(option==1){
			$('#txtCardCode').attr('maxlength',18);
		}else{
			$('#txtCardCode').attr('maxlength',100);
		}
	});
	
	// 设置下拉框颜色
	$('#rblSex').change(function(){ 
		if (this.value == "")
	 		$('#rblSex').css("color", "#B0B0B0");
	 	else
	 		$('#rblSex').css("color", "#000");
	}) 
	$('#ddlCardType').change(function(){ 
		if (this.value == "")
	 		$('#ddlCardType').css("color", "#B0B0B0");
	 	else
	 		$('#ddlCardType').css("color", "#000");
	}) 
	$('#ddlProvince').change(function(){ 
		if (this.value == "")
	 		$('#ddlProvince').css("color", "#B0B0B0");
	 	else
	 		$('#ddlProvince').css("color", "#000");
	}) 
	$('#ddlCity').change(function(){ 
		if (this.value == "")
	 		$('#ddlCity').css("color", "#B0B0B0");
	 	else
	 		$('#ddlCity').css("color", "#000");
	}) 
	$('#ddlArea').change(function(){ 
		if (this.value == "")
	 		$('#ddlArea').css("color", "#B0B0B0");
	 	else
	 		$('#ddlArea').css("color", "#000");
	}) 
	
	//CardType();
	Provience();
	
});

//证件类型
function CardType(){
	jQuery.ajax({
		url: "../assemblyAction.do?m=cardType",
		data: "",
		type: "POST",
		success: function(msg){
			if(msg != ""){
				$("#ddlCardType").empty();
				var obj = eval(msg);
				var selectHtml = "<option value='0'>*请选择证件类型</option>";
				if(obj.length>0){
					$(obj).each(function(index) {
		                  var key = obj[index];
		                  selectHtml +='<option value="'+key.id+'">'+key.dicDesc+'</option>';
	       		    });
				}
				$("#ddlCardType").html(selectHtml);
			}
		}
	})
}

//省
function Provience(){
	jQuery.ajax({
		url: "${appPath}/province/listAll.action",
		data: "",
		type: "GET",
		success: function(msg){
			if(msg != ""){
				$("#ddlProvince").empty();
				var obj = eval(msg);
				var selectHtml = "<option value=''>*请选择所在省市</option>";
				if(obj.length>0){
					$(obj).each(function(index) {
		                  var key = obj[index];
		                  selectHtml +='<option value="'+key.provinceCode+'" style="color:black">'+key.provinceName+'</option>';
	       		    });
				}
				$("#ddlProvince").html(selectHtml);
			}
		}
	})
}

//市
function City(provinceCode){
	jQuery.ajax({
		url: "${appPath}/city/listCity.action",
		data: "proCode="+provinceCode,
		type: "GET",
		success: function(msg){
			if(msg != ""){
				$("#ddlCity").empty();
				var obj = eval(msg);
				var selectHtml = "<option value=''>请选择所在地市</option>";
				if(obj.length>0){
					$(obj).each(function(index) {
		                  var key = obj[index];
		                  selectHtml +='<option value="'+key.cityCode+'" style="color:black">'+key.cityName+'</option>';
	       		    });
				}
				$("#ddlCity").html(selectHtml);
			}
		}
	})
}

//区县
function Area(cityCode){
	jQuery.ajax({
		url: "${appPath}/district/listDistrict.action",
		data: "cityCode="+cityCode,
		type: "GET",
		success: function(msg){
			if(msg != ""){
				$("#ddlArea").empty();
				var obj = eval(msg);
				var selectHtml = "<option value=''>请选择所在区县</option>";
				if(obj.length>0){
					$(obj).each(function(index) {
		                  var key = obj[index];
		                  selectHtml +='<option value="'+key.districtCode+'" style="color:black">'+key.districtName+'</option>';
	       		    });
				}
				$("#ddlArea").html(selectHtml);
			}
		}
	})
}


//区县级联
function showOptionValue(temp,name){
	if(name=='city'){
		City($(temp).val());
		$('#ddlArea').html('<option value="">所在区县</option>');
	}else if(name=='area'){
		Area($(temp).val());
	}
}

document.onkeydown=function(event){
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if(e && e.keyCode==13){ // enter 键
		submits();
	}
};

</script>
     
</head>
<body>
<div class="main">
<!--内容区-->
    <div class="wel_box" >
    <form method="post" id="form1" name="form1">
<div class="title" id="div4">联系人信息</div>
        <div class="box1" id="div5">
        
        	<!-- ----------- -->
        	<table width="98%" align="center"><tr><td width="51%">
        	<div id="tishi211" style="display: none"><font color="red">请输入车主姓名</font> </div></td><td width="49%">
        	<div id="tishi212" style="display: none"><font color="red">请选择性别</font> </div></td></tr></table>
        	<input type="text"  class="in1 fl" name="carownername" id="txtOwnerName" onclick="hideAll()" onkeyup="document.getElementById('txtLinkMan').value=document.getElementById('txtOwnerName').value" placeholder="*车主姓名">

           <select name="carownersex" class="in1 fr" onclick="hideAll()" id="rblSex"  style=color:#B0B0B0><option value="" selected="selected" style=color:#B0B0B0>*性别</option><option value="1" style=color:black>男</option><option value="0" style=color:black>女</option></select>
        <table width="98%" align="center"><tr><td width="51%">
         <div id="tishi221" style="display: none"><font color="red">请选择证件类型</font> </div> </td><td width="49%">
         <div id="tishi222" style="display: none"><font color="red">请输入证件号码</font> </div>
         <div id="tishi223" style="display: none"><font color="red">请输入正确证件号码</font> </div></td></tr></table>
            <select class="in1 fl" name="certificatestype" onclick="hideAll()" id="ddlCardType" style=color:#B0B0B0 >
				<option value="" style=color:#B0B0B0>*证件类型</option>
           		<option value="1" style=color:black>身份证</option>
           		<option value="2" style=color:black>军官证</option>
           		<option value="3" style=color:black>企业执照</option>
           		<option value="4" style=color:black>护照</option>
           		<option value="5" style=color:black>士兵证</option>
           		<option value="6" style=color:black>其他</option>
            </select>
            <input class="in1 fr" type="text" maxlength="100" name="certificatescode" id="txtCardCode" onclick="hideAll()" placeholder="*证件号码">
            <table width="98%" align="center"><tr><td width="51%">
            <div id="tishi231" style="display: none"><font color="red">请输入联系人</font> </div></td><td width="49%">
            <div id="tishi232" style="display: none"><font color="red">请输入手机号码</font> </div>
            <div id="tishi233" style="display: none"><font color="red">请输入正确的手机号码</font> </div></td></tr></table>
            <input class="in1 fl" type="text" name="contactsname" id="txtLinkMan" onclick="hideAll()" placeholder="*联系人">
            <input class="in1 fr" type="text" name="contactsphone" id="txtPhone" onclick="hideAll()" maxlength="11" placeholder="*手机号码">
           <table width="98%" align="center"><tr><td width="51%">
            <div id="tishi241" style="display: none"><font color="red"></font> </div> </td><td width="49%">
            <div id="tishi242" style="display: none"><font color="red">请输入正确的电子信箱</font> </div></td></tr></table>
			<input class="in1 fl" type="text" name="contactstel" id="txtTel" placeholder="电话">
            <input class="in1 fr" type="text" name="contactsemail" id="txtEmail" placeholder="电子信箱">
            <table width="98%" align="center"><tr><td width="51%">
            <div id="tishi251" style="display:none"><font color="red">请选择所在省份</font> </div></td><td width="49%">
            <div id="tishi252" style="display:none"><font color="red"></font> </div></td></tr></table>
            <select class="in1 fl" size="1" name="cprovincecode" id="ddlProvince" onclick="hideAll()" onchange="showOptionValue(this,'city')" style=color:#B0B0B0>
            	<option value="">*所在省市</option>
            </select>
            <select class="in1 fr" name="ccitycode" id="ddlCity" onchange="showOptionValue(this,'area')" style=color:#B0B0B0><option value="">所在地市</option></select>
           <table width="98%" align="center"><tr><td width="51%">
           	<div id="tishi261" style="display: "></div></td><td width="49%">
            <div id="tishi262" style="display: none"><font color="red">请输入正确的邮政编码</font> </div></td></tr></table>
           	<select class="in1 fl" name="cdistrictcode" id="ddlArea" style=color:#B0B0B0><option value="">所在区县</option></select>
            <input class="in1 fr" name="postCode" id="txtPostCode" maxlength="6" type="text" placeholder="邮政编码">
            <input class="in1 in11 fl" name="contactsadress" id="txtLinkAddress" type="text" placeholder="联系地址">
              </div>
            
			<div class="button2" id="div6">
			<input style="float:none;" id="submit_btn" onclick="submits2()"  type="button" value="提交"> 
			<input style="float:none;" name="vin" type="reset" value="重置"> 
			</div>
			
			<div id="load" align="center" style="display: none">正在提交，请稍等</div>
        </form>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
function hideAll(){
	$('#tishi211').hide();
	$('#tishi212').hide();
	$('#tishi221').hide();
	$('#tishi222').hide();
	$('#tishi231').hide();
	$('#tishi232').hide();
	$('#tishi233').hide();
	$('#tishi242').hide();
	$('#tishi251').hide();
	$('#tishi262').hide();
}
function hy1(){

	var t1=$("#txtTireSize1").val();
	var t2=$("#txtTireSize2").val();
	var t3=$("#txtTireSize3").val();

	if(t1!="*轮胎规格"&&t2!=""&&t3!=""){
		if(!(valid_IsNumic(t1)&&valid_IsNumic(t2)&&valid_IsNumic(t3))){
			$("#txtTireSize1").val('');
			$("#txtTireSize2").val('');
			$("#txtTireSize3").val('');
			$("#txtTireSize1").focus();
			$("#tishi141").hide();
			$("#tishi131").show();
			return;
		
			}else{
				$("#txtTireSize").val(t1+"/"+t2+"R"+t3);
			}
	}else{
		$("#txtTireSize1").val('');
			$("#txtTireSize2").val('');
			$("#txtTireSize3").val('');
			$("#txtTireSize1").focus();
			$("#tishi141").hide();
			$("#tishi131").show();
	}

	var t=$("#ddlAssembly").val();
	if(t==""){
		$("#ddlAssembly").focus();
		$("#tishi111").show(); 
		return;
	}else{
		$("#tishi111").hide();
	}

	if($("#hfSubAssembly").val()==""){
		$("#hfSubAssembly").focus();
		$("#tishi121").show();
		return;
	}else{
		$("#tishi121").hide();
	}

	if($("#hfThirdAssembly").val()==""){
		$("#hfThirdAssembly").show();
	} 

	if(($("#ddlAssembly").find("option:selected").text()=='轮胎和车轮')){

		if($('#txtTireBrand').val()=='*轮胎品牌'){
			$("#txtTireSize1").val('');
			$('#txtTireBrand').force();
			$('#tishi130').show();
			return;
		}
		
		if(t1=="*轮胎规格"||t2==""||t3==""||t1==""){
			$("#txtTireSize1").val('');
			$("#txtTireSize1").focus();
			$("#tishi131").show();
			return;
		}
		
	}else{
		$('#txtTireBrand').val('');
		$("#txtTireSize").val('')

		$("#tishi131").hide();
	}

	if($("#txtTireBrand").val()=="*轮胎品牌"){
		$("#txtTireBrand").val('')
	}

	if($("#txtPosition").val()=="具体部位"){
		$("#txtPosition").val('')
	}

	if($("#txtDefectDesc").val()==""){
		$("#txtDefectDesc").focus();
		$("#tishi141").show();
		return;
	}else{
		$("#tishi141").hide();
	}
	
	$("#div1").hide();
	$("#div2").hide();
	$("#div3").hide();
	$("#div4").show();
	$("#div5").show();
	$("#div6").show();
}
function submits(){
WeixinJSBridge.invoke('getNetworkType', {}, function (e) {
WeixinJSBridge.log(e.err_msg);network=e.err_msg.split(":")[1];
if(network=="fail"){
window.location.href="http://www.dpac.gov.cn/";}
else{submits2();
}});
}
	function submits2(){
		var txtOwnerName=$("#txtOwnerName").val();
		if(txtOwnerName=="*车主姓名"||txtOwnerName==""){
			$("#txtOwnerName").focus();
			$("#tishi211").show();
			return false;
		}else{
			$("#tishi211").hide();
		}
		var rblSex=$("#rblSex").val();
		if(rblSex==""){
			$("#rblSex").focus();
			$("#tishi212").show(); 
			return false;
		}else{
			$("#tishi212").hide();
		}
		var cardtype=$("#ddlCardType").val();
		var card=$("#txtCardCode").val();
		if(cardtype==""){
			$("#ddlCardType").focus();
			$("#tishi221").show();
			return  false;
		}else{
			$("#tishi221").hide();
		}
		if(card=="*证件号码"||card==""){
			$("#txtCardCode").focus();
			$("#tishi222").show();
			return false;
		}else{
			$("#tishi222").hide();
		}
		if(cardtype=="1"&&!IdCardValidate(card)){
			$("#txtCardCode").focus();
			$("#tishi223").show();
			return false;
		}else{
			$("#tishi222").hide();
			$("#tishi223").hide();
		}
		var txtLinkMan=$("#txtLinkMan").val();
		if(txtLinkMan=="*联系人"){
			$("#txtLinkMan").focus();
			$("#tishi231").show();
			return  false;
		}else{
			$("#tishi231").hide();
		}
		var phone=$("#txtPhone").val();
		if(phone=="*手机号码"){
			$("#txtPhone").focus();
			$("#tishi232").show();
			return false;
		}else{
			$("#tishi232").hide();
		}
		if(!valid_IsPhone(phone)){
			$("#txtPhone").focus();
			$("#tishi233").show();
			return false;
		}else{
			$("#tishi233").hide();
		}
		var txtEmail=$("#txtEmail").val();
		if(txtEmail!="电子信箱"&&txtEmail!=""&&!valid_email(txtEmail)){
			$("#txtEmail").focus();
			$("#tishi242").show();
			return false;
		}else{
			$("#tishi242").hide();
		}
		
		if($("#ddlProvince").val()==""){
			$("#ddlProvince").focus();
			$("#tishi251").show(); 
			return false;
		}else{
			$("#tishi251").hide(); 
		}

		var txtPostCode=$("#txtPostCode").val();
		if(txtPostCode!="邮政编码"&&txtPostCode!=""&&!valid_postCode(txtPostCode)){
			$("#txtPostCode").val('');
			$("#txtPostCode").focus();
			$("#tishi262").show();
			return false;
		}else{
			$("#tishi262").hide();
		}
		if(txtEmail=="电子信箱"){$("#txtEmail").val('');}
		if(txtPostCode=="邮政编码"){$("#txtPostCode").val('');}

		if($('#txtLinkAddress').val()=="联系地址"){$("#txtLinkAddress").val('');}
		if($('#txtTel').val()=="电话"){$("#txtTel").val('');}
		if($('#ddlAssembly').val()!="837960FF-E463-441C-9EB5-55A9601628D4"){$("#txtTireSize").val('');}
		
		//$("#submit_btn").attr("disabled","disabled");
		$("#load").show();
		$("#form1").attr("action","${appPath}/defectCar/saveDefectCarAllInfo.action");
		$("#form1").submit();
	}
	function valid_CardCode(val) {
		var patten = new RegExp(/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/);
		return patten.test(val);
	}
	function valid_IsPhone(val) {
		var patten = new RegExp(/^1\d{10}$/);
		return patten.test(val);
	}
	function valid_email(email) {
		var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/);
		return patten.test(email);
	 }
	 
	function valid_postCode(postcode) {
		var patten = new RegExp(/^[0-9]{6}$/);
		return patten.test(postcode);
	}

	function valid_IsNumic(val) {
	    var patten = new RegExp(/^[0-9]*$/);
	    return patten.test(val);
	}

</script>