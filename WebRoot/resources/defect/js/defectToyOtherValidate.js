/*
 * 儿童玩具和其他消费品数据采集表单JS验证
 * toy:1:儿童玩具
 * other：3：其他消费品
 * */

//表单提示信息
var prodNameVMsg = "*产品名称";
var prodTrademarkVMsg = "*产品商标";
var prodModelVMsg = "型号";
var prodTypeVMsg = "类别";
var buyshopNameVMsg = "商店名称";
var buyshopAdressVMsg = "商店地址";
var defectDescriptionVMsg="请描述产品的问题缺陷";
var reportusernameVMsg = "*姓名";
var reportuserphoneVMsg = "*手机";
var reportuseremailVMsg = "Email";
var usernameVMsg = "使用者姓名";
var cprovincecodeVMsg = "*省份";
var postCodeVMsg = "*邮政编码";
var reportadressVMsg = "*地址";


//页面获取焦点和失去焦点的效果
$(function(){
	
	/**
	 * 获取焦点 清空原有内容
	 */	
	//产品名称
	$("#prodName").focus(function() {
		if($("#prodName").val()==prodNameVMsg) {
			$("#prodName").val("");
		}
	})
	$("#prodName").blur(function() {
		if($("#prodName").val()==prodNameVMsg || $("#prodName").val()=="") {
			$("#prodName").val(prodNameVMsg);
		}
	})
	
	//商标
	$("#prodTrademark").focus(function() {
		if($("#prodTrademark").val()==prodTrademarkVMsg) {
			$("#prodTrademark").val("");
		}
	})
	$("#prodTrademark").blur(function() {
		if($("#prodTrademark").val()==prodTrademarkVMsg || $("#prodTrademark").val()=="") {
			$("#prodTrademark").val(prodTrademarkVMsg);
		}
	})
	
	//产品类别
	$("#prodType").focus(function() {
		if($("#prodType").val()==prodTypeVMsg) {
			$("#prodType").val("");
		}
	})
	$("#prodType").blur(function() {
		if($("#prodType").val()==prodTypeVMsg || $("#prodType").val()=="") {
			$("#prodType").val(prodTypeVMsg);
		}
	})
	
	//产品型号
	$("#prodModel").focus(function() {
		if($("#prodModel").val()==prodModelVMsg) {
			$("#prodModel").val("");
		}
	})
	$("#prodModel").blur(function() {
		if($("#prodModel").val()==prodModelVMsg || $("#prodModel").val()=="") {
			$("#prodModel").val(prodModelVMsg);
		}
	})
	
	//商店名称
	$("#buyshopName").focus(function() {
		if($("#buyshopName").val()==buyshopNameVMsg) {
			$("#buyshopName").val("");
		}
	})
	$("#buyshopName").blur(function() {
		if($("#buyshopName").val()==buyshopNameVMsg || $("#buyshopName").val()=="") {
			$("#buyshopName").val(buyshopNameVMsg);
		}
	})
	
	//商店地址
	$("#buyshopAdress").focus(function() {
		if($("#buyshopAdress").val()==buyshopAdressVMsg) {
			$("#buyshopAdress").val("");
		}
	})
	$("#buyshopAdress").blur(function() {
		if($("#buyshopAdress").val()==buyshopAdressVMsg || $("#buyshopAdress").val()=="") {
			$("#buyshopAdress").val(buyshopAdressVMsg);
		}
	})
	
	//缺陷信息描述
	$("#defectDescription").focus(function() {
		if($("#defectDescription").val()==defectDescriptionVMsg) {
			$("#defectDescription").val("");
		}
	})
	$("#defectDescription").blur(function() {
		if($("#defectDescription").val()==defectDescriptionVMsg || $("#defectDescription").val()=="") {
			$("#defectDescription").val(defectDescriptionVMsg);
		}
	})
	
	//*姓名
	$("#reportusername").focus(function() {
		if($("#reportusername").val().trim()==reportusernameVMsg) {
			$("#reportusername").val("");
		}
	})
	$("#reportusername").blur(function() {
		if($("#reportusername").val().trim()==reportusernameVMsg || $("#reportusername").val().trim()=="") {
			$("#reportusername").val(reportusernameVMsg);
		}
	})
	
	//*手机
	$("#reportuserphone").focus(function() {
		if($("#reportuserphone").val().trim()==reportuserphoneVMsg) {
			$("#reportuserphone").val("");
		}
	})
	$("#reportuserphone").blur(function() {
		if($("#reportuserphone").val().trim()==reportuserphoneVMsg || $("#reportuserphone").val().trim()=="") {
			$("#reportuserphone").val(reportuserphoneVMsg);
		}
	})

	//Email
	$("#reportuseremail").focus(function() {
		if($("#reportuseremail").val().trim()==reportuseremailVMsg) {
			$("#reportuseremail").val("");
		}
	})
	$("#reportuseremail").blur(function() {
		if($("#reportuseremail").val().trim()==reportuseremailVMsg || $("#reportuseremail").val().trim()=="") {
			$("#reportuseremail").val(reportuseremailVMsg);
		}
	})
	
	//使用者姓名
	$("#username").focus(function() {
		if($("#username").val().trim()==usernameVMsg) {
			$("#username").val("");
		}
	})
	$("#username").blur(function() {
		if($("#username").val().trim()==usernameVMsg || $("#username").val().trim()=="") {
			$("#username").val(usernameVMsg);
		}
	})
	
	//*邮政编码
	$("#postCode").focus(function() {
		if($("#postCode").val().trim()==postCodeVMsg) {
			$("#postCode").val("");
		}
	})
	$("#postCode").blur(function() {
		if($("#postCode").val().trim()==postCodeVMsg || $("#username").val().trim()=="") {
			$("#postCode").val(postCodeVMsg);
		}
	})

	//*地址
	$("#reportadress").focus(function() {
		if($("#reportadress").val().trim()==reportadressVMsg) {
			$("#reportadress").val("");
		}
	})
	$("#reportadress").blur(function() {
		if($("#reportadress").val().trim()==reportadressVMsg || $("#reportadress").val().trim()=="") {
			$("#reportadress").val(reportadressVMsg);
		}
	})
});

var phoneReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/; 
var postCodeReg =  /^[0-9]{6}$/;
var emailReg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;

function formValidate() {
	var result = true;
	$(".notNull").each(function(){
		var tagVal = $(this).val().trim();
		var tagName = $(this).attr("name");
		if(tagName=="prodName") {
			if(tagVal==prodNameVMsg ) {
				$("#prodName").focus();
				result = false;
				return result;
			}
		} else 
		if(tagName=="prodTrademark") {
			if(tagVal==prodTrademarkVMsg) {
				//resultBool = false;
				$("#prodTrademark").focus();
				result = false;
				return result;
			}
		} else 
		if(tagName=="buyDate") {
			if($("#buyDate").val().trim() == "") {
				alert("选择购买时间");
				result = false;
				return result;
			}
		}else 
		if(tagName=="defectDescription") {
			if(tagVal == defectDescriptionVMsg) {
				$("#defectDescription").focus();
				result = false;
				return result;
			}
		} else 
		if(tagName=="reportusername") {
			if(tagVal==reportusernameVMsg) {
				$("#reportusername").focus();
				result = false;
				return result;
			}
		} else 
		if(tagName=="reportuserphone") {
			if(tagVal==reportuserphoneVMsg || !phoneReg.test(tagVal)) {
				$("#reportuserphone").focus();
				result = false;
				return result;
			}
		} else 
		if(tagName=="postCode") {
			if(tagVal== postCodeVMsg || !postCodeReg.test(tagVal) ) {
				$("#postCode").focus();
				result = false;
				return result;
			}
		} else 
		if(tagName=="reportadress") {
			if(tagVal==reportadressVMsg) {
				$("#reportadress").focus();
				result = false;
				return result;
			}
		} /*else 
			if(tagName=="cprovincecode") {
				if(tagVal=="") {
				///	$("#cprovincecode").focus();
					result = false;
					return result;
				}
			} */else {
			result = true;
			return result;
		}
	});
	return result;
}