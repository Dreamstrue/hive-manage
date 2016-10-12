//根据审核状态返回对应的中文信息
function back_zh_CN(value){
	if(value=='0'){
		return '<font>未审核</font>';
	}
	if(value=='1'){
		return '<font color="green">审核通过</font>';
	}
	if(value=='2'){
		return '<font color="red">审核未通过</font>';
	}
}


function intend_zh_CN(value){
	if(value=='1'){
		return '<font>兑换已申请</font>';
	}
	if(value=='2'){
		return '<font color="green">审核通过</font>';
	}
	if(value=='3'){
		return '<font color="red">审核未通过</font>';
	}
	if(value=='4'){
		return '<font>已发货</font>';
	}
	if(value=='5'){
		return '<font>确认收货</font>';
	}
}



// 格式化日期样式
function formatDate(value){
	var  t = new Date(value);
		var m = t.getMonth()+1;
		m = m<10?("0"+m):m;
		var d =(t.getDate()<10)?("0"+t.getDate()):t.getDate();
		var hour = (t.getHours()<10)?("0"+t.getHours()):t.getHours();
		var min = (t.getMinutes()<10)?("0"+t.getMinutes()):t.getMinutes();
		var sec = (t.getSeconds()<10)?("0"+t.getSeconds()):t.getSeconds();
		var s = t.getFullYear()+"-"+m+"-"+d+" "+hour+":"+min+":"+sec;
		return s;
}

function format_Date(){
		var  t = new Date();
		var m = t.getMonth()+1;
		m = m<10?("0"+m):m;
		var d =(t.getDate()<10)?("0"+t.getDate()):t.getDate();
//		var hour = (t.getHours()<10)?("0"+t.getHours()):t.getHours();
//		var min = (t.getMinutes()<10)?("0"+t.getMinutes()):t.getMinutes();
//		var sec = (t.getSeconds()<10)?("0"+t.getSeconds()):t.getSeconds();
		var s = t.getFullYear()+"-"+m+"-"+d;
		return s;
}


function format_before_Date(){
	var  t = new Date();
	var m = t.getMonth();
	var d =(t.getDate()<10)?("0"+t.getDate()):t.getDate();
	var s = t.getFullYear()+"-"+m+"-"+d;
	return s;
}

function validateValue(){
	var title =  $('#title').val();
	if(!checkStr(title)){
		showMessage('标题不能为空');
		return false;
	}return true;
}

function checkStr(data){
	if(data=="" || data== null){
		return false;
	}return true;
}


function showMessage(msg){
	$.messager.show({
		title:'提示',
		timeout:2000,
		msg:msg
	});
}

/*
 * 封装弹出提示框等操作
 * 
 */
var easyuiBox = new Object();

/**
 * 定义弹出消息框
 * 
 * @param msg
 */
easyuiBox.show = function(msg) {
	$.messager.show({
		title : '消息',
		msg : msg,
		showType : 'show'
	});
};

/**
 * 定义弹出提示框
 * 
 * @param msg
 */
easyuiBox.alert = function(msg) {
	$.messager.alert("提示",msg,'info');
};

/**
 * 定义弹出提示确认框
 * 
 * @param title
 * @param message
 * @param fn 选择确定之后执行的函数
 */
easyuiBox.confirm = function(title, message, fn) {
	$.messager.confirm(title, message, function(r) {
		if (r) {
			fn();
		}
	});
};

/**
 * 定义弹出错误消息提示框
 * 
 * @param msg
 */
easyuiBox.error = function(msg) {
	$.messager.alert('错误', msg, 'error');
};


function getWindowWidth(){
	var w=window.innerWidth	|| document.documentElement.clientWidth	|| document.body.clientWidth;

	return w;

}

function getWindowHeight(){
	var h=window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
	return h;
}



/**自定义的校验表单项是否修改 涉及到问卷行业类别，客户端菜单模块*/
function formIsDirty(form) {
	var defaultValue;
	var currentValue;
	  for (var i = 0; i < form.elements.length; i++) {
	    var element = form.elements[i];
	    var type = element.type;
	    var name = element.name;
	    if("text" == name || "menuName" == name){
	    	if (element.value != element.defaultValue) {
    			return true;
    		}
	    }else if("radio" == type){
	    	if (element.checked != element.defaultChecked) {
    			return true;
    		}
	    }else  if("defaultValue" == name){
	    	defaultValue = element.defaultValue;
	//    	alert('默认：'+defaultValue);
	    }else  if("currentValue" == name){
	    	currentValue = element.defaultValue;
//	    	alert('当前：'+currentValue);
	    }
	  }
	  if(defaultValue!=currentValue){
		  return true;
	  }
	  return false;
	}
