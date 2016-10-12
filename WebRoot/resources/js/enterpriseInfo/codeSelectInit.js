/*
 * 企业管理模块 代码表下拉框初始化
 * 《1》省、市、县 代码表
 * 《2》登记注册类型 代码表
 * 
 * */
var base_url = getRootPath()+"/";

// 用于存放行政区划数据的表单元素ID
var oldProvinceHidId = "oldProvince";
var oldCityHidId = "oldCity";
var oldDistrictHidId = "oldDistrict";

// 行政区划下拉框组件的ID
var provinceId = "cprovincecode";
var cityId = "ccitycode";
var districtId = "cdistrictcode";



$(function(){
	
	//-----------------------------------行政区划-----------------------------
	//加载省代码
	$.ajax({
		type : "get",
		async : false,
		url : base_url+'province/listAll.action',
		dataType : "json",
		success : function(data) {
			// 用于编辑时的回显
			var oldProvince = $("#"+oldProvinceHidId).val();
			var oldCity = $("#"+oldCityHidId).val();
			var oldDistrict = $("#"+oldDistrictHidId).val();
			
			for(var i=0;i<data.length;i++){
				if (oldProvince==data[i].provinceCode){
					$("#"+provinceId).append("<option value='"+data[i].provinceCode+"' selected>"+data[i].provinceName+"</option>");  
				}else{
					$("#"+provinceId).append("<option value='"+data[i].provinceCode+"'>"+data[i].provinceName+"</option>");  
				}
			}
			if(oldCity!=""&&oldCity!=null){
				autoCity(oldProvince, oldCity);
			}
			if(oldDistrict!=""&&oldDistrict!=null){
				autoDistrict(oldCity, oldDistrict);
			}	
		
		}
	});
	
	// 省份下拉框选中事件处理
	$("#"+provinceId).change(function(){
		// 加载市区下拉框中的数据，并设置选中项
		autoCity($(this).val(), null);
	});   

	// 市区下拉框选中事件处理
	$("#"+cityId).change(function(){
		// 加载县区下拉框中的数据，并设置选中项
		autoDistrict($(this).val(), null);
	}); 
	
	
	
	
	
	//-----------------------------------登记注册类型-----------------------------
	$.ajax({
		type : "get",
		async : false,
		url : base_url+'companyTypeCode/listCompanyType.action',
		dataType : "json",
		success : function(data) {
			var oldCompanyTypeCode = $("#oldCompanyTypeCode").val();
			var str1= new Array(); //定义一数组 
			str1 = oldCompanyTypeCode.split("_");
			
			for(var i=0;i<data.length;i++){
				$("#companyType").append("<option value='"+data[i].comTypCode+"'>"+data[i].comTypName+"</option>");  
			}
			
			// 用于编辑时下拉的回显
			if(oldCompanyTypeCode!=""&&str1.length>0){
				$("#companyType").val(str1[0]);
				for(var j=0;j<str1.length;j++){
					autoSubChangeType(str1[j], j, str1[j+1]);
				}
			}
			
		}
	});

	// 一级下拉框选项变化时的事件处理
	$("#companyType").change(function(){
		/*var count = $(this).nextAll("select").length;
		var s = 0;
		while(s < count){
			$(this).next().remove();
			s++;
		}
		autoSubChangeType($(this).val(),k=1,null);*/
		
		comTypeSelChanged(this, $(this).val(), 1);
	});
	
	
});// 页面加载后的初始化完成



//-----------------------------------行政区划-----------------------------

/*
 * 市区下拉框的选中
 * @param proCode 省份代码
 * @param oldCity 市区代码(用于下拉框的选中)
 * */
function autoCity(proCode, oldCity){
	//alert("----"+oldCity);
	if(proCode!="" && proCode!=null){
		$.ajax({
			type : "get",
			async : false,
			url : base_url+'city/listCity.action?proCode='+ proCode,
			dataType : "json",
			success : function(data) {
				$("#"+cityId).empty();
				$("#"+districtId).remove();
				if(data!=""){
					$("#"+cityId).append("<option value=''>请选择市区...</option>");
				}
				for(var i=0;i<data.length;i++){
					$("#"+cityId).append("<option value='"+data[i].cityCode+"'>"+data[i].cityName+"</option>");  
				}
				if(oldCity!=null){
					$("#"+cityId).val(oldCity);
				}
			
			}
		});
	}else{
		$("#"+cityId).empty();
		$("#"+districtId).remove();
		/*$.messager.show({
			title:'提示',
			msg:'请选择省份'
		});*/
	}
	
}// End Of autoCity()


/*
 * 县区下拉框的选中
 * @param cityCode 市区代码
 * @param oldArea  县区代码(用于下拉框的选中)
 * */
function autoDistrict(cityCode, oldArea){
	if(cityCode!="" && cityCode!=null){
		$.ajax({
			type : "get",
			async : false,
			url : base_url+'district/listDistrict.action?cityCode='+cityCode,
			dataType : "json",  
			success : function(data) {
				$("#"+districtId).remove();
				if(data!=""){
						$("#"+cityId).after("<select id='"+ districtId + "' name='" + districtId + "'></select>");
						$("#"+districtId).append("<option value=''>请选择县区...</option>");
				}
				for(var i=0;i<data.length;i++){
					$("#"+districtId).append("<option value='"+data[i].districtCode+"'>"+data[i].districtName+"</option>");  
				}
				if(oldArea!=null){
					$("#"+districtId).val(oldArea);
				}
			}
		});
	}else{
		$.messager.show({
			title:'提示',
			msg:'请选择市区'
		});
	}
}// End Of autoDistrict()




//-----------------------------------登记注册类型-----------------------------

/*
 * 将某一注册类型下拉框选择项改变时，先将其后面的子下拉框都给移除掉，然后再加载新的子下拉框
 * @param node 下拉项节点对象
 * @param nodeVal 下拉项节点值
 * @param hierarchyNO 代表层级的数字
 * 
 * */
function comTypeSelChanged(node,nodeVal,hierarchyNO){
	// 先用循环删除其后所有的子下拉框
	var count = $(node).nextAll("select").length;
	var s = 0;
	while(s < count){
		$(node).next().remove();
		s++;
	}
	hierarchyNO++;
	
	// 然后加载选中项的后面对应的子下拉框
	autoSubChangeType(nodeVal,hierarchyNO,null);
}

/*
 * 根据所选中的父节点获取其下子节点类别数据，并选中指定值
 * 
 * @param code 父节点代码
 * @param hierarchyNO 代表层级的数字
 * @param oldValue 用于子节点的选中
 * */
function autoSubChangeType(code,hierarchyNO,oldValue){
	if(code!="" && code!=null ){
		$.ajax({
			type : "get",
			async : false,
			url : base_url+'companyTypeCode/listCompanyType.action?parentCode='+code,
			dataType : "json",
			success : function(data) {
				if(data!=""){
						var sel = code+"_sel";
						$("#typeDiv").append("<select name='regType' id="+sel+" onchange='comTypeSelChanged(this,$(this).val(),"+hierarchyNO+")'></select>");
						$("#"+sel).append("<option value=''>请选择..."+"</option>"); 
						for(var i=0;i<data.length;i++){
							$("#"+sel).append("<option value='"+data[i].comTypCode+"'>"+data[i].comTypName+"</option>");  
						}
				}
				if(oldValue!=null){
					$("#"+sel).val(oldValue);
				}
			}
		});
	}
}

