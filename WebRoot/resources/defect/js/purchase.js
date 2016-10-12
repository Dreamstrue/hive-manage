 //车型系列keyup
    $("hfBrand").keyup(function () {
       purchase(0);
    });
    //车型系列focus
    $("hfBrand").focus(function () {
       purchase(0);
    });
    //车型系列click
    $("hfBrand").click(function () {
        purchase(0);
    });
    //车型系列mouseover
    $("hfBrand").mouseover(function () { 
       purchase(0);
    });


function search(type){
	var wordText;
	var url;
	if(type==0){
		wordText = $("#hfBrand");
		url = '../interface/defect/carBrandList2.action';
	}
	
	if (wordText.val() != "") {
		$("body").bind("mousedown", onBodyDown_);
		jQuery.ajax({
			url: url,
			data: "brandName=" + wordText.val(),
			type: "GET",
			beforeSend:function(){
				$('#auto').html('<li>数据加载中。。</li>');
				$("#auto").css('display','block'); 
				cancelSelect();
			},
			success: function(msg){
				if(msg != ""||msg!=null){
					var obj = eval('(' + msg + ')');
					obj = obj.data;
					var temp = '';
					$(obj).each(function(index) {
		                  var key = obj[index];
		                  temp+='<li style="cursor:pointer;" brandId='+key.id+' onclick="clickBrand(this)" producername='+key.producername+' brandName='+key.brandname+'>'+key.brandname+'</li>';
		                  
					});
					if(temp.length>0){
						$('#auto').html(temp);
						$("#auto").css('display','block'); 
					}else{
						$('#auto').html('<li>请输入正确的品牌！</li>');
						$("#auto").css('display','block'); 
					}
					
				}else{
					$("#auto").css("display","none");
					$("#auto").html("");
					if(type==0){
						$("#txtBrand").val("");
						$("#hfBrand_").val("");
					}
				}
			}
		});
	}
}


function checkWord(type){
	var inputword;
	if(type==0){
		inputword = $("#hfBrand").val();
	}
	search(type);
}

function onBodyDown_(event){
	if (!(event.target.id == "auto" || event.target.id.indexOf("hfBrand")!=-1 || $(event.target).parents("#auto").length>0)) {
		hideMenu_();
	}
}

function hideMenu_(){
	$("#auto").fadeOut("fast");
	$("body").unbind("mousedown", onBodyDown_);
}

function purchase(type){
	document.getElementById("hfBrand").style.textDecoration="underline;";
	
	var highlightindex = -1;
	var wordInput;
	var wordText;
	var autoNode = $("#auto");
	if(type==0){
		
		wordInput = $("#hfBrand");
		wordText = $("#hfBrand").val();
		if(wordText == ""){
			$("#auto").css("display","none");
			$("#auto").html("");
		}else{
			checkWord(type);
		}
	}
	var wordInputOffset = wordInput.offset();
	$("#auto").hide().css("border", "1px black solid").css("position", "absolute").css(" height", "38px").css("top", wordInputOffset.top + wordInput.height() + 10 + "px").css("left", wordInputOffset.left + 2 + "px").width(wordInput.width()+5+"px");
}
//根据品牌固定国产/进口，自主/合资/进口
function initNature(comText){
	var url = '../complaintsvehicleinfoAction.do?m=brand';
	jQuery.ajax({
		url: url,
		data: "value="+comText+"&column=BrandId",
		type: "POST",
		success: function(msg){
			if(msg != ""){
				var obj = eval(msg);
				var selectHtml = "";
				$(obj).each(function(index) {
	                  var key = obj[index];
	                  $('#hfNatureImportValue').val(key.qualityType);
	                  var str="";
	                  if(key.qualityType==1){str="国产";}else if(key.qualityType==2){str="进口";}
	                  $('#txtNatureImportValue').val(str);
	                  var str2="";//自主-1/合资-2/进口-3
	                  if(key.brandNature==1){str2="自主";}else if(key.brandNature==2){str2="合资";}else if(key.brandNature==3){str2="进口";}
	                  $('#hfBrandNatureValue').val(key.brandNature);
	                  $('#txtBrandNatureValue').val(str2);
       		    });
				
			}
		}
	});
	
}
//点击品牌初始化生成者
function initCompany(comText){
	var url = '../complaintsvehicleinfoAction.do?m=brand';
	jQuery.ajax({
		url: url,
		data: "value="+comText+"&column=BrandId",
		type: "POST",
		success: function(msg){
			if(msg != ""){
				var obj = eval(msg);
				var selectHtml = "";
				$(obj).each(function(index) {
	                  var key = obj[index];
	                  selectHtml +='<option value="'+key.companyId+'">'+key.companyName+'</option>';
       		    });
				$("#hfCompany").html(selectHtml);
			}
		}
	});
	
}
//根据品牌和生产者id初始化车型系列
function initchexingxl(){
	var hfBrandid = $('#hfBrandId').val();
	var url = '../interface/defect/carBrandSeriesList.action';
	jQuery.ajax({
		url: url,
		data: "brandId="+hfBrandid,
		type: "GET",
		success: function(msg){
			if(msg != ""){
				//var obj = eval(msg);
				var obj = eval('(' + msg + ')');
				obj = obj.data;
				var selectHtml = "";
				$(obj).each(function(index) {
	                  var key = obj[index];
	                  selectHtml +='<option value="'+key.id+'">'+key.seriesname+'</option>';
       		    });
				$("#hfVehicleModelSys").html(selectHtml);
				$('#hfVehicleModelSys').css("color", "#000");
				//$("#txtVehicleModelSys").hide();
				//$("#hfVehicleModelSys").show();
				initchexingmc();//根据车型系列id查询车型名称
			}
		}
	});
}
//根据车型系列id查询车型名称
function initchexingmc(){
	var hfBrandid = $('#hfBrandId').val();
	var VehicleModelSysid = $("#hfVehicleModelSys").val();
	var url = '../interface/defect/carBrandSeriesModelList.action';
	jQuery.ajax({
		url: url,
		data: "brandId="+hfBrandid+"&seriesId=" + VehicleModelSysid,
		type: "GET",
		success: function(msg){
			var obj = eval('(' + msg + ')');
			obj = obj.data;
			if(obj != ""){
				//var obj = eval(msg);
				var selectHtml = "";
				$(obj).each(function(index) {
	                  var key = obj[index];
	                  selectHtml +='<option value="'+key.id+'">'+key.modelname+'</option>';
       		    });
				$("#hfVehicleModel").html(selectHtml);
				$('#hfVehicleModel').css("color", "#000");
				initjutixl();//根据车型名称查询具体车型
			}
		}
	});
}



//根据车型名称查询具体车型
function initjutixl(){
	var hfBrandid = $('#hfBrandId').val();
	var VehicleModelSysid = $("#hfVehicleModelSys").val();
	var txtVehicleModelid = $("#hfVehicleModel").val();
	var url = '../interface/defect/carBrandSeriesModelDetailList.action';
	jQuery.ajax({
		url: url,
		data: "brandId="+hfBrandid+"&seriesId=" + VehicleModelSysid + "&modelId=" + txtVehicleModelid,
		type: "GET",
		success: function(msg){
			if(msg != ""){
				//var obj = eval(msg);
				var obj = eval('(' + msg + ')');
				obj = obj.data;
				var selectHtml = "";
				$(obj).each(function(index) {
	                  var key = obj[index];
	                  selectHtml +='<option value="'+key.id+'">'+key.modeldetailname+'</option>';
       		    });
				$("#hfVehicleTypeCode").html(selectHtml);
				$('#hfVehicleTypeCode').css("color", "#000");
			}
		}
	});
}

function cancelSelect(){
	$('#hfCompany').html('<option value="">*生产者</option>');
	$('#hfVehicleModelSys').html('<option value="">*车型系列</option>');
	$('#hfVehicleModel').html('<option value="">*车型名称</option>');
	$('#hfVehicleTypeCode').html('<option value="">具体型号</option>');
}