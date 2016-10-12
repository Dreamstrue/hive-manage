/*
 * 行业分类代码表
 * */
var base_url = getRootPath()+"/";

$(function() {

	//-----------------------------------行业分类-----------------------------
	$.ajax({
		type : "get",
		async : false,
		url : base_url+'industryCategoryCode/listIndCat.action',
		dataType : "json",
		success : function(data) {
			var oldIndCatCode = $("#oldIndCatCode").val();
			var str1= new Array(); //定义一数组 
			str1 = oldIndCatCode.split("_");
			
			for(var i=0;i<data.length;i++){
				$("#sectors").append("<option value='"+data[i].indCatCode+"'>"+data[i].indCatName+"</option>");  
			}
			
			if(oldIndCatCode!=""&&str1.length>0){
				$("#sectors").val(str1[0]);
				for(var j=0;j<str1.length;j++){
					autoSubIndCode(str1[j],j,str1[j+1]);
				}
			}
		}
	});

	// 一级下拉框选项变化时的事件处理
	$("#sectors").change(function(){
		/*var count = $(this).nextAll("select").length;
		var s = 0;
		while(s < count){
			//alert('-移除第'+s+"--个");
			$(this).next().remove();
			s++;
		}
		autoSubIndCode($(this).val(),k=1,null);*/
		
		indCatSelChanged(this, $(this).val(), 1);
	});

});


//-----------------------------------行业分类-----------------------------
/*
 * 将某一行业分类下拉框选择项改变时，先将其后面的子下拉框都给移除掉，然后再加载新的子下拉框
 * @param node 下拉项节点对象
 * @param nodeVal 下拉项节点值
 * @param hierarchyNO 代表层级的数字
 * 
 * */
function indCatSelChanged(node,nodeVal,hierarchyNO){
	var count = $(node).nextAll("select").length;
	var s = 0;
	while(s < count){
		$(node).next().remove();
		s++;
	}
	hierarchyNO++;
	
	autoSubIndCode(nodeVal,hierarchyNO,null);
}

/*
 * 根据所选中的父节点获取其下子节点类别数据，并选中指定值
 * 
 * @param code 父节点代码
 * @param hierarchyNO 代表层级的数字
 * @param oldValue 用于子节点的选中
 * */
function autoSubIndCode(code,hierarchyNO,oldValue){
	if(code!="" && code!=null ){
		$.ajax({
			type : "get",
			async : false,
			url : base_url+'industryCategoryCode/listIndCat.action?parentCode='+code,
			dataType : "json",
			success : function(data) {
				if(data!=""){
						var sel = code+"_sel";
						$("#tradeDiv").append("<select name='indCategory' id="+sel+" onchange='indCatSelChanged(this,$(this).val(),"+hierarchyNO+")'></select>");
						$("#"+sel).append("<option value=''>请选择..."+"</option>"); 
						for(var i=0;i<data.length;i++){
							$("#"+sel).append("<option value='"+data[i].indCatCode+"'>"+data[i].indCatName+"</option>");  
						}
						if(oldValue!=null){
							$("#"+sel).val(oldValue);
						}
				}
			}
		});
	}
}