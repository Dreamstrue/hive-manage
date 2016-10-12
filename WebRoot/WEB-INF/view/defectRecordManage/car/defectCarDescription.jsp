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
<script src="${appPath}/resources/defect/js/MultiFileUp.js" type="text/javascript"></script>
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
	$('#ddlAssembly').change(function(){ 
		if (this.value == "")
	 		$('#ddlAssembly').css("color", "#B0B0B0");
	 	else
	 		$('#ddlAssembly').css("color", "#000");
	}) 
	$('#hfSubAssembly').change(function(){ 
		if (this.value == "")
	 		$('#hfSubAssembly').css("color", "#B0B0B0");
	 	else
	 		$('#hfSubAssembly').css("color", "#000");
	}) 
	$('#hfThirdAssembly').change(function(){ 
		if (this.value == "")
	 		$('#hfThirdAssembly').css("color", "#B0B0B0");
	 	else
	 		$('#hfThirdAssembly').css("color", "#000");
	}) 
	
	Assembly();
});

//总成
function Assembly(){
	jQuery.ajax({
		url: "${appPath}/interface/defect/assemblyList.action",
		data: "",
		type: "GET",
		success: function(msg){
			var obj = eval('(' + msg + ')');
			obj = obj.data;
			if(obj != ""){
				$("#ddlAssembly").empty();
				//var obj = eval(msg);
				var selectHtml = "<option value=''>*请选择总成</option>";
				if(obj.length>0){
					$(obj).each(function(index) {
		                  var key = obj[index];
		                  selectHtml +='<option value="'+key.id+'" style="color:black;">'+key.assemblyName+'</option>';
	       		    });
				}
				$("#ddlAssembly").html(selectHtml);
			}
		}
	})
}

//分总成
function SubAssembly(assemblyId){
	jQuery.ajax({
		url: "${appPath}/interface/defect/subAssemblyList.action",
		data: "assemblyId="+assemblyId,
		type: "GET",
		success: function(msg){
			var obj = eval('(' + msg + ')');
			obj = obj.data;
			if(obj != ""){
				$("#hfSubAssembly").empty();
				//var obj = eval(msg);
				var selectHtml = "<option value=''>*请选择分总成</option>";
				if(obj.length>0){
					$(obj).each(function(index) {
		                  var key = obj[index];
		                  selectHtml +='<option value="'+key.id+'" style="color:black;">'+key.subAssemblyName+'</option>';
	       		    });
				}else{
				}
				$("#hfSubAssembly").html(selectHtml);
			}
		}
	})
}

//三级总成
function ThirdAssembly(assemblyId, subAssemblyId){
	jQuery.ajax({
		url: "${appPath}/interface/defect/threeAssemblyList.action",
		data: "assemblyId="+assemblyId + "&subAssemblyId=" + subAssemblyId,
		type: "GET",
		success: function(msg){
			var obj = eval('(' + msg + ')');
			obj = obj.data;
			if(obj != ""){
				$("#hfThirdAssembly").empty();
				//var obj = eval(msg);
				var selectHtml = "<option value=''>请选择三级总成</option>";
				if(obj.length>0){
					$(obj).each(function(index) {
		                  var key = obj[index];
		                  selectHtml +='<option value="'+key.id+'" style="color:black;">'+key.threeLevelAssemblyName+'</option>';
	       		    });
				}
				$("#hfThirdAssembly").html(selectHtml);
			}
		}
	})
}

//总成级联
function change(temp,name){
	if($(temp).find("option:selected").text()=='轮胎和车轮'){
		$('#div7').show();
	}else{
		if(name=='hfSubAssembly'&&$(temp).find("option:selected").text()!='轮胎和车轮'){
			$('#div7').hide();
		}
	}

	if(name=='hfSubAssembly'){
		SubAssembly($(temp).val());
		$('#').html('<option value="">所在三级分成</option>');
	}else if(name=='hfThirdAssembly'){
		ThirdAssembly($("#ddlAssembly").val(),$(temp).val());
	}
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

<script type="text/javascript">
       var maxsize = 2*1024*1024;//2M  
       var errMsg = "上传的附件文件不能超过2M！！！";  
       var tipMsg = "您的浏览器暂不支持计算上传文件的大小，确保上传文件不要超过2M，建议使用IE、FireFox、Chrome浏览器。";  
       var  browserCfg = {};  
       var ua = window.navigator.userAgent;  
       if (ua.indexOf("MSIE")>=1){  
           browserCfg.ie = true;  
       }else if(ua.indexOf("Firefox")>=1){  
           browserCfg.firefox = true;  
       }else if(ua.indexOf("Chrome")>=1){  
           browserCfg.chrome = true;  
       }  
       function checkfile(){  
           try{  
               var obj_file = document.getElementById("fileuploade");  
               if(obj_file.value==""){
                   alert("请先选择上传文件");  
                   return;
               }
               var filesize = 0;  
               if(browserCfg.firefox || browserCfg.chrome ){  
                   filesize = obj_file.files[0].size;  
               }else if(browserCfg.ie){  
                   var obj_img = document.getElementById('tempimg');  
                   obj_img.dynsrc=obj_file.value;  
                   filesize = obj_img.fileSize;  
               }else{  
					alert(tipMsg);  
					return;  
               }
               if(filesize==-1){  
                   alert(tipMsg);  
                   return;  
               }else if(filesize>maxsize){  
                   alert(errMsg);  
                   return;  
               }else{  
                   alert("文件大小符合要求");  
                   return;  
               }  
           }catch(e){  
               alert(e);  
           }  
       } 
	   
</script>
</head>
<body>
<div class="main">
<!--内容区-->
    <div class="wel_box" >
    <form method="post" enctype="multipart/form-data" id="form1" name="form1">
       <div class="title" id="div1">缺陷描述信息</div>
        <div class="box1" id="div2">
        	<div id="tishi111" style="display: none"><font color="red">请选择总成</font> </div>
           	<select class="in1 in11" name="assembly" id="ddlAssembly" onchange="change(this,'hfSubAssembly')"  style=color:#B0B0B0></select>
			<div id="tishi121" style="display: none"><font color="red">请选择分总成</font></div>
			<select class="in1 in11" name ="subAssembly" id="hfSubAssembly" onclick="hideAll()" onchange="change(this,'hfThirdAssembly')" style=color:#B0B0B0>
			<option value="">*请选择分总成</option>
			</select>
			<select class="in1 in11" name ="threeLevelAssembly" id="hfThirdAssembly" style=color:#B0B0B0><option value="">所在三级分成</option></select>
			<input class="in1 in11" maxlength="200" type="text" placeholder="具体部位" name="specificPosition" id="txtPosition">
         
         <div id="div7" style="display: none">
         	<div id="tishi130" style="display: none"><font color="red">请输入轮胎品牌</font> </div>
			<input class="in1 in11" type="text"  value="*轮胎品牌" onclick="hideAll()" name="txtTireBrand" id=txtTireBrand>
			<div id="tishi131" style="display: none"><font color="red">请输入轮胎规格</font> </div>
			
			 <font size="3">*下方输入轮胎规格<br/></font>
			<input class="in1" style="width:30%;" id="txtTireSize1" name="txtTireSize1" maxlength="3" onclick="hideAll()" type="text" value="">/
			<input class="in1" style="width:30%;" type="text" value="" id="txtTireSize2" name="txtTireSize2" maxlength="2" > R
			<input class="in1" style="width:30%;" type="text" value="" id="txtTireSize3" name="txtTireSize3" maxlength="2" >
			
		</div>
            <p class="p1">涉及零部件是否为出厂原装零部件：<br/><input type="radio" default="1" name="IsOriginalAccessories" checked="checked" value="1"/>是<input type="radio" default="0" name="IsOriginalAccessories" value="0"/>否</p>
           	<input type="hidden" id="rblIsOriginalAccessories" name="isOriginalAccessories" value="1"/>
           	<p class="p1">*缺陷描述：<div id="tishi141" style="display: none;"><font color="red">请输入缺陷描述</font></div></p>
            <textarea maxlength="240" class="in1 in12" name="defectDescription" onclick="hideAll()" id="txtDefectDesc"></textarea>
             <p class="p1"><font color="#FF0000"></font>其它情况说明：</p>
            <textarea maxlength="240" class="in1 in12" name="otherDescription" id="txtOthers"></textarea>
            <table id="attAchments"></table>
            <p class="p1"> 附件： <a id="attach" style="font-family: 宋体; font-size: 12pt;" title="如果您要添加多个附件，您只需多次点击“添加文件”即可。"
                  onclick="AddAttachments();" href="javascript:void(0);" name="attach">添加文件</a> （点击可从手机中选择文件，注意：文件大小不能超4M ）
              <br />
            </p> 
             
   	 		<p class="p1">是否发生交通事故：<input type="radio" name="IsTrafficAccident" default="1" value="1"/>是<input type="radio" name="IsTrafficAccident" default="0" value="0" />否</p>
            <input type="hidden" id="rblIsTrafficAccident" name="isTrafficAccident" value=""/>
            <p class="p1">是否造成人员伤亡：<input type="radio" name="IsCasualtie" default="1" value="1"/>是<input type="radio" name="IsCasualtie" default="0" value="0"/>否</p>
            <input type="hidden" id="rblIsCasualtie" name="isCasualtie" value=""/>
        </div>
		<div class="button" id="div3"><a href="#" onclick="javascript:hy1();">下一步</a></div>
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

	if($("#txtTireBrand").val()==""){
		$("#txtTireBrand").val('')
	}

	if($("#txtPosition").val()==""){
		$("#txtPosition").val('')
	}

	if($("#txtDefectDesc").val()==""){
		$("#txtDefectDesc").focus();
		$("#tishi141").show();
		return;
	}else{
		$("#tishi141").hide();
	}
	
	$('#form1').attr("action","${appPath}/defectCar/saveDefectCarDescription.action");
	$('#form1').submit();
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
		if(rblSex=="0"){
			$("#rblSex").focus();
			$("#tishi212").show(); 
			return false;
		}else{
			$("#tishi212").hide();
		}
		var cardtype=$("#ddlCardType").val();
		var card=$("#txtCardCode").val();
		if(cardtype=="0"){
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
		$("#form1").attr("action","../complaintsvehicleinfoAction.do?m=saveComplaintsVehicleinfo");
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

function fh(){
	$('#div1').show();
	$('#div2').show();
	$('#div3').show();
	$('#div4').hide();
	$('#div5').hide();
	$('#div6').hide();
}
</script>