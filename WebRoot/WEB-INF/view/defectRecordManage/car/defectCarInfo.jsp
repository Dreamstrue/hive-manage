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
<link href="${appPath}/resources/defect/css/datetime.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${appPath}/resources/defect/js/jqlearn.js"></script>
<script type="text/javascript" src="${appPath}/resources/defect/js/purchase.js"></script>
<script type="text/javascript" src="${appPath}/resources/defect/js/calendar.js"></script>
<script type="text/javascript">
$(function(){
	$('.box1 input').blur(function(){
		if(this.value=='')
		this.value=this.defaultValue;
		});

	//生产者光标事件
	$('#hfCompany').focus(function(event){
		var value = this.value;
		if(value=='*生产者'){
			this.value='';
			//ProducerKeyUp();
			hideAll();
		}
	});
	$('#hfBrand').focus(function(event){
		var value = this.value;
		if(value=='*品牌'){
			this.value='';
			document.getElementById("hfBrand").style.textDecoration="none";
			document.getElementById("hfBrand").style.color="#000000";
			//ProducerKeyUp();
			hideAll();
		}
	});
	$('#txtVehicleModel').focus(function(event){
		var value = this.value;
		if(value=='*车型名称'){
			this.value='';
			//ProducerKeyUp();
			hideAll();
		}
	});
		$('#txtVehicleModelSys').focus(function(event){
		var value = this.value;
		if(value=='*车型系列'){
			this.value='';
			//ProducerKeyUp();
			hideAll();
		}
	});
	$('#txtVINCode').focus(function(event){
		var value = this.value;
		if(value=='*VIN码/车架号'){
			this.value='';
			//ProducerKeyUp();
			hideAll();
		}
	});
	$('#txtEngineDisplacement').focus(function(event){
		var value = this.value;
		if(value=='发动机排量[升(L)]'){
			this.value='';
			//ProducerKeyUp();
			hideAll();
		}
	});
	$('#txtMileage').focus(function(event){
		var value = this.value;
		if(value=='*行驶里程[公里(KM)]'){
			this.value='';
			hideAll();
		}
	});
$('#txtEngineCode').focus(function(event){
		var value = this.value;
		if(value=='发动机号'){
			this.value='';
			hideAll();
		}
	});
$('#txtLicenseNum').focus(function(event){
		var value = this.value;
		if(value=='牌照号码'){
			this.value='';
			hideAll();
		}
	});
$('#txtPurchaseShop').focus(function(event){
	var value = this.value;
	if(value=='购买店铺'){
		this.value='';
		hideAll();
	}
});

// 设置下拉框颜色
$('#ddlYearType').change(function(){ 
	if (this.value == "")
 		$('#ddlYearType').css("color", "#B0B0B0");
 	else
 		$('#ddlYearType').css("color", "#000");
}) 
$('#hfVehicleModelSys').change(function(){ 
	if (this.value == "")
 		$('#hfVehicleModelSys').css("color", "#B0B0B0");
 	else
 		$('#hfVehicleModelSys').css("color", "#000");
}) 
$('#hfVehicleModel').change(function(){ 
	if (this.value == "")
 		$('#hfVehicleModel').css("color", "#B0B0B0");
 	else
 		$('#hfVehicleModel').css("color", "#000");
}) 
$('#hfVehicleTypeCode').change(function(){ 
	if (this.value == "")
 		$('#hfVehicleTypeCode').css("color", "#B0B0B0");
 	else
 		$('#hfVehicleTypeCode').css("color", "#000");
}) 
$('#ddlTransmissionType').change(function(){ 
	if (this.value == "") {
 		$('#ddlTransmissionType').css("color", "#B0B0B0");
 	} else {
 		$('#ddlTransmissionType').css("color", "#000");
 		$('#placeholder').css("color", "#B0B0B0");
 	}
}) 
$('#prodCountry').change(function(){ 
	if (this.value == "")
 		$('#prodCountry').css("color", "#B0B0B0");
 	else
 		$('#prodCountry').css("color", "#000");
}) 
$('#prodModel').change(function(){ 
	if (this.value == "")
 		$('#prodModel').css("color", "#B0B0B0");
 	else
 		$('#prodModel').css("color", "#000");
}) 

document.onkeydown=function(event){
	var e = event || window.event || arguments.callee.caller.arguments[0];
	if(e && e.keyCode==13){ // enter 键
		submits2();
	}
};

transmissionType();
});

function clickBrand(temp){
	$('#hfBrand').val($(temp).attr('brandName'));
	$('#hfBrandId').val($(temp).attr('brandId'));
	
	var selectHtml ='<option value="'+$(temp).attr('producername')+'" style="color:black">'+$(temp).attr('producername')+'</option>';
	$("#hfCompany").html(selectHtml);
	$('#hfCompany').css("color", "#000");
	
	//initCompany($(temp).attr('brandId'));//初始化生产者
	initchexingxl();//初始化车型系列
	//initNature($(temp).attr('brandId')); // 根据所选品牌设置 国产/进口 和 自主/合资/进口 我们数据库设计品牌时没有这两个属性，所以需要用户自己选择
	$("#auto").css('display','none'); 
}

//变速器类型（这个也应该根据所选车型进行联动的，我们没有做关联）
function transmissionType(){
	jQuery.ajax({
		url: "${appPath}/interface/defect/gearBoxList.action",
		data: "",
		type: "GET",
		success: function(msg){
			if(msg != ""){
				$("#ddlTransmissionType").empty();
				//var obj = eval(msg);
				var obj = eval('(' + msg + ')');
				obj = obj.data;
				var selectHtml = "<option value='' id='placeholder' style='color:B0B0B0'>变速器类型</option>";
				if(obj.length>0){
					$(obj).each(function(index) {
		                  var key = obj[index];
		                  selectHtml +='<option value="'+key.id+'" style=color:black>'+key.gearboxName+'</option>';
	       		    });
				}
				$("#ddlTransmissionType").html(selectHtml);
			}
		}
	})
}

function checkNum(obj){
	var re = /^-?[0-9]*(\.\d*)?$|^-?d^(\.\d*)?$/;
	return re.test(obj);
} 

function valid_postCode(postcode) {
	var patten = new RegExp(/^[0-9]{6}$/);
	return patten.test(postcode);
}
function valid_IsNumic(val) { 
	var patten = new RegExp(/^[0-9]*$/);
	return patten.test(val);
}
function valid_VIN(val) {
	var patten = new RegExp(/^(?!(?:\d+|[a-zA-Z]+)$)[\da-zA-Z]{17}$/);
	return patten.test(val);
}
function valid_CardCode(val) {
	var patten = new RegExp(/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/);
	return patten.test(val);
}

function submits(){
	
	WeixinJSBridge.invoke('getNetworkType', {}, function (e) {
		WeixinJSBridge.log(e.err_msg);network=e.err_msg.split(":")[1];
		if(network=="fail"){
			window.location.href="http://www.dpac.gov.cn/";
		}else{
			submits2();
		}
	});
	
	submits2();
}

function submits2(){
	var BrandName=$("#hfBrand").val();
	var ddlYearType=$("#ddlYearType").val();
	var prodCountry=$("#prodCountry").val();
	var prodModel=$("#prodModel").val();
	
	var ddlTransmissionType=$("#ddlTransmissionType").val();
	var hfBrandId=$("#hfBrandId").val();
	var hfCompany=$("#hfCompany").val();
	var hfVehicleModelSys=$("#hfVehicleModelSys").val();
	

	if(BrandName==""){
		$("#hfBrand").focus();
		$("#tishi11").show();
		return;
	}else{
		$("#tishi11").hide();
	}
	//生产者
	if(hfCompany==""){
		$("#hfCompany").focus();
		$("#tishi13").show();
		return;
	}else{
		$("#tishi13").hide();
	}
	//车型系列
	if(hfVehicleModelSys==""){
		$("#hfVehicleModelSys").focus();
		$("#tishi14").show();
		return;
	}else{
		$("#tishi14").hide();
	}
	
	if(ddlYearType==""){
		$("#ddlYearType").focus();
		$("#tishi21").show();
		return;
	}else{
		$("#tishi21").hide();
	}
	if($("#hfVehicleModel").val()=="" || $("#hfVehicleModel").val() == null){
		$("#hfVehicleModel").focus();
		$("#tishiModelsID").show();
		return;
	}else {
		$("#tishiModelsID").hide();
	}
	if($("#txtVINCode").val()==""){
		$("#txtVINCode").focus();
		$("#tishi31").show();
		return;
	}else{
		$("#tishi31").hide();
	}
	if(!valid_VIN($("#txtVINCode").val())){
		$("#txtVINCode").focus();
		$("#tishi32").show();
		return;
	}else{
		$("#tishi32").hide();
	}
	
	if($("#txtPurchaseDate").val()==""){
		$("#txtPurchaseDate").focus();
		$("#tishi51").show();
		return;
	}else{
		$("#tishi51").hide();
	}
	
	var txtEngineDisplacement=$("#txtEngineDisplacement").val();
	if(txtEngineDisplacement=="发动机排量[升(L)]"||txtEngineDisplacement==''){
		$("#tishi41").hide();
		$("#txtEngineDisplacement").val('');
	}else{
		if(!checkNum(txtEngineDisplacement)){
			$("#txtEngineDisplacement").focus();
			$("#tishi41").show();
			return;
		}
	}

	var txtMileage=$("#txtMileage").val();
	//if(txtMileage=="行驶里程[公里(KM)]"||txtMileage==''){
	//	$("#tishi52").hide();
	//	$("#txtMileage").val('')
	//}else{
	//	if(!checkNum(txtMileage)){
	//		$("#txtMileage").focus();
	//		$("#tishi52").show();
	//		return;
	//	}
	//}
	if((txtMileage=="行驶里程[公里(KM)]")||(txtMileage=='') || (!checkNum(txtMileage))){
		$("#txtMileage").focus();
		$("#tishi52").show();
		return;
	}

	var txtEngineCode = $('#txtEngineCode').val();
	if(txtEngineCode=="发动机号"||txtEngineCode==''){
		$("#txtEngineCode").val('')
	}
	
	var txtPurchaseShop = $('#txtPurchaseShop').val();
	if(txtPurchaseShop=="购买店铺"||txtPurchaseShop==''){
		$("#txtPurchaseShop").val('')
	}
	
	var txtLicenseNum = $('#txtLicenseNum').val();
	if(txtLicenseNum=="牌照号码"||txtLicenseNum==''){
		$("#txtLicenseNum").val('')
	}

	if(prodCountry==""){
		$("#prodCountry").focus();
		$("#tishi81").show();
		return;
	}else{
		$("#tishi81").hide();
	}
	
	if(prodModel==""){
		$("#prodModel").focus();
		$("#tishi82").show();
		return;
	}else{
		$("#tishi82").hide();
	}

	$('#form1').attr("action","${appPath}/defectCar/saveDefectCarInfo.action");
	$('#form1').submit();
}

function hideAll(){
	$('#tishi11').hide();
	$('#tishi12').hide();
	$('#tishi21').hide();
	$('#tishi12').hide();
	$('#tishiModelsID').hide();
	$('#tishi31').hide();
	$('#tishi32').hide();
	$('#tishi41').hide();
	$('#tishi51').hide();
	$('#tishi52').hide();
}

</script>
</head>
<body>
<div class="main">
<!--内容区-->
    <div class="wel_box">
    <form method="post" id="form1">
        <div class="title">车辆信息</div>
        <div class="box1">
        	<div id="auto" class="autos" style="background:#e3f2fb;font-size:16px;margin-top:-5px;"><ul></ul></div>
        	<p class="p4"><div id="tishi11" style="display: none"><font color="red">请输入品牌</font></div><div id="tishi12" style="display: none"><font color="red">请输入并选择正确品牌</font></div></p>
           <div style="height: 40px;">
            <input class="in1 fl" type="text" placeholder="*品牌" onkeyup="purchase(0)" onkeypress="purchase(0)" onclick="purchase(0)" name="hfBrand" id="hfBrand" autocomplete="off">
            <input type="hidden" name="carbrand" id="hfBrandId" autocomplete="off">
            
            <div id="tishi13" style="display: none"><font color="red">&nbsp;&nbsp;请选择生产者</font></div>
            <select class="in1 fr" name="carproducername" onclick="hideAll()" id="hfCompany" style=color:#B0B0B0>
            	<option value="" style=color:#B0B0B0>*生产者</option>
            </select>
            </div>
           <div id="tishi21" style="display: none"><font color="red"><br/>请选择年款</font></div>
            <div>
            <select class="in1 fl" name="carmodelyear" onclick="hideAll()" id="ddlYearType" style=color:#B0B0B0 >
				<option value="" style=color:#B0B0B0>*请选择年款</option>
            
            		<option value="2015" style=color:black>2015</option>
            	
            		<option value="2014" style=color:black>2014</option>
            	
            		<option value="2013" style=color:black>2013</option>
            	
            		<option value="2012" style=color:black>2012</option>
            	
            		<option value="2011" style=color:black>2011</option>
            	
            		<option value="2010" style=color:black>2010</option>
            	
            		<option value="2009" style=color:black>2009</option>
            	
            		<option value="2008" style=color:black>2008</option>
            	
            		<option value="2007" style=color:black>2007</option>
            	
            		<option value="2006" style=color:black>2006</option>
            	
            </select>
           
           <div id="tishi14" style="display: none"><font color="red"><br/>请选择车型系列</font></div>
            <select class="in1 fr" id="hfVehicleModelSys" name="carseries" onclick="hideAll()" onchange="initchexingmc()" style=color:#B0B0B0>
            	<option value="" style=color:#B0B0B0>*车型系列</option>
            </select>
            <div id="tishiModelsID" style="display: none"><font color="red"><br/>请选择车型名称</font></div>
           	<input class="in1 fl" type="hidden" value="*车型名称" name="ModelsID" id="ModelsID">
            <select class="in1 fl" id="hfVehicleModel" name="carmodelname" onclick="hideAll()" onchange="initjutixl()" style=color:#B0B0B0>
            	<option value="" style=color:#B0B0B0>*车型名称</option>
            </select>
            <input class="in1 fr" type="hidden" value="具体型号" name="TypeCodeID" id="TypeCodeID">
            <select class="in1 fr" id="hfVehicleTypeCode" onclick="hideAll()" name="carmodeldetailed" style=color:#B0B0B0>
            	<option value="" style=color:#B0B0B0>具体型号</option>
            </select>
            </div>
             <div id="tishi31" style="display: none"><font color="red"><br/><br/><br/><br/><br/><br/><br/>请输入正确VIN码/车架号</font></div>
             <div id="tishi32" style="display: none"><font color="red"><br/><br/><br/><br/><br/><br/><br/>请输入正确VIN码/车架号</font></div>
           <div>
            <input class="in1 fl" type="text" placeholder="*VIN码/车架号" maxlength="17"  name="carVin" onclick="hideAll()" id="txtVINCode">
            <select class="in1 fr" name="carTransmission" id="ddlTransmissionType" style=color:#B0B0B0>
				<option value="" style=color:#B0B0B0>变速器类型</option>
			</select>
			</div>
			<table width="100%"><tr><td>
			<div  id="tishi41" style="display: none"><font color="red">请输入正确的发动机排量[升(L)] 如：1.2  2.0
			</font></div></td></tr></table>
            <div>
            <input class="in1 fl" type="text" placeholder="发动机排量[升(L)]" name="carDischarge" id="txtEngineDisplacement">
            <input class="in1 fr" type="text" placeholder="发动机号" name="carNumber" id="txtEngineCode">
           </div>
            <table width="100%"><tr><td width="51%">
            <div id="tishi51" style="display: none"><font style="color:red;font-size:12px;">请选择购买日期</font></div></td><td width="49%">
            <div id="tishi52" style="display: none"><font style="color:red;font-size:12px;">请输入正确的行驶里程 如: 213  800</font></div>
          </td></tr></table>
           <div>
            <input class="in1 fl" type="text" placeholder="*购买日期" readonly="readonly" onclick="hideAll()" name="buyDate" id="txtPurchaseDate">
            <input class="in1 fr" type="text" placeholder="*行驶里程[公里(KM)]" name="runKilometers" id="txtMileage">
           </div>
           
           <div>
            <input class="in1 fl" type="text" placeholder="购买店铺" name="buyshopName" id="txtPurchaseShop">
            <input class="in1 fr" type="text" placeholder="牌照号码" name="plateNumber" id="txtLicenseNum">
		</div>
		  <div id="tishi81" style="display:none;float: left;"><font color="red" >请选择国产/进口</font></div>
		<div>
            <input type="hidden" value="1" name="hfNatureImportValue"id="hfNatureImportValue">
          	
          
            <select class="in1 fl" name="prodCountry" onclick="hideAll()" id="prodCountry" style=color:#B0B0B0>
				<option value="" style=color:#B0B0B0>*国产/进口</option>
           		<option value="1" style=color:black>国产</option>
           		<option value="2" style=color:black>进口</option>
            </select>
          	<div id="tishi82" style="display:none;float: left;"><font color="red">&nbsp;&nbsp;请选择自主/合资/进口</font></div>
          	<select class="in1 fr" class="in1 fr" name="prodModel" onclick="hideAll()" id="prodModel" style=color:#B0B0B0>
				<option value="" style=color:#B0B0B0>*自主/合资/进口</option>
           		<option value="1" style=color:black>自主</option>
           		<option value="2" style=color:black>合资</option>
           		<option value="3" style=color:black>进口</option>
            </select>
		</div>
        </div>
        <div class="button"><a href="javascript:submits2();">下一步</a></div>
        </form>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
new Calender({id:'txtPurchaseDate',isSelect:!0});
</script>