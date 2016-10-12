<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>大转盘活动</title>

<link href="${pageContext.request.contextPath}/resources/css/wheelstyle.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/mobile.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/icon.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.easyui.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.easyui.mobile.js" charset="UTF-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
<!-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/wheelSurf/jquery-1.10.2.js"></script> -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/wheelSurf/awardRotate.js"></script>

<script type="text/javascript">
var turnplate={
		isWinPrize:[],				//是否有奖品
		restaraunts:[],				//大转盘奖品名称
		colors:[],					//大转盘奖品区块对应背景颜色
		outsideRadius:192,			//大转盘外圆的半径
		textRadius:155,				//大转盘奖品位置距离圆心的距离
		insideRadius:68,			//大转盘内圆的半径
		startAngle:0,				//开始角度
		
		bRotate:false				//false:停止;ture:旋转
};
// 	var num;
$(document).ready(function(){
	//动态添加大转盘的奖品与奖品区域背景颜色
	var isWinPrize = "${isWinPrize}";
	var pname = "${pname}";
	var cvalue = "${cvalue}";
	turnplate.isWinPrize = isWinPrize.split(",");
	turnplate.restaraunts = pname.split(",");
	turnplate.colors = cvalue.split(",");

	
	var rotateTimeOut = function (){
		$('#wheelcanvas').rotate({
			angle:0,//开始角度 
			animateTo:2160,//转动角度 
			duration:8000,//转动时间 
			callback:function (){
				alert('网络超时，请检查您的网络设置！');
			}
		});
	};

	//旋转转盘 item:奖品位置; txt：提示语;
	var rotateFn = function (item, txt, isWinPrize){
// 	alert("进入rotateFn方法");
		var angles = item * (360 / turnplate.restaraunts.length) - (360 / (turnplate.restaraunts.length*2));
		if(angles<270){
			angles = 270 - angles; 
		}else{
			angles = 360 - angles + 270;
		}
		$('#wheelcanvas').stopRotate();
		$('#wheelcanvas').rotate({
			angle:0,
			animateTo:angles+1800,
			duration:8000,
			callback:function (){
				if(isWinPrize=='lose'){
				
				}else if(isWinPrize=='win'){
					txt = '恭喜你！抽中了'+txt+'，敬请关注【质讯通微信公众账号:zlcxxh】领取活动奖品！';
				}else{
					txt = '神马鬼？！！';
				}
				$.messager.alert({
					title : '提示',
					msg : txt
				});
				turnplate.bRotate = !turnplate.bRotate;
			}
		});
	};

	$('.pointer').click(function (){
// 		alert("出发抽奖按钮！");
		if(turnplate.bRotate)return;
		turnplate.bRotate = !turnplate.bRotate;
		//获取随机数(奖品个数范围内)
// 		var item = rnd(1,turnplate.restaraunts.length);
		var item = obtainOrderNum();
		//奖品数量等于10,指针落在对应奖品区域的中心角度[252, 216, 180, 144, 108, 72, 36, 360, 324, 288]
		if(item!=0){
			rotateFn(item, turnplate.restaraunts[item-1], turnplate.isWinPrize[item-1]);
			console.log(item);
		}
	});
});

function rnd(n, m){
	//随机获取所中奖项编号
	var random = Math.floor(Math.random()*(m-n+1)+n);
	return random;
}


//页面所有元素加载完毕后执行drawRouletteWheel()方法对转盘进行渲染
window.onload=function(){
	drawRouletteWheel();
};

function drawRouletteWheel() {    
  var canvas = document.getElementById("wheelcanvas");
  if (canvas.getContext) {//判断canvas是否具有一个getContext属性
	  //根据奖品个数计算圆周角度
	  var arc = Math.PI / (turnplate.restaraunts.length/2);
	  var ctx = canvas.getContext("2d");
	  //在给定矩形内清空一个矩形
	  ctx.clearRect(0,0,422,422);
	  //strokeStyle 属性设置或返回用于笔触的颜色、渐变或模式  
	  ctx.strokeStyle = "#FFBE04";
	  //font 属性设置或返回画布上文本内容的当前字体属性
	  ctx.font = '16px Microsoft YaHei';      
	  for(var i = 0; i < turnplate.restaraunts.length; i++) {       
		  var angle = turnplate.startAngle + i * arc;//弧长
		  ctx.fillStyle = turnplate.colors[i];
		  ctx.beginPath();
		  //arc(x,y,r,起始角,结束角,绘制方向) 方法创建弧/曲线（用于创建圆或部分圆）    
		  ctx.arc(211, 211, turnplate.outsideRadius, angle, angle + arc, false);    
		  ctx.arc(211, 211, turnplate.insideRadius, angle + arc, angle, true);
		  ctx.stroke();  
		  ctx.fill();
		  //锁画布(为了保存之前的画布状态)
		  ctx.save();   
		  
		  //----绘制奖品开始----
		  ctx.fillStyle = "#E5302F";
		  var text = turnplate.restaraunts[i];
		  var line_height = 17;
		  //translate方法重新映射画布上的 (0,0) 位置
		  ctx.translate(211 + Math.cos(angle + arc / 2) * turnplate.textRadius, 211 + Math.sin(angle + arc / 2) * turnplate.textRadius);
		  
		  //rotate方法旋转当前的绘图
// 		  alert("要执行rotate方法了");
		  ctx.rotate(angle + arc / 2 + Math.PI / 2);
		  
		  /** 下面代码根据奖品类型、奖品名称长度渲染不同效果，如字体、颜色、图片效果。(具体根据实际情况改变) **/
		  if(text.indexOf("M")>0){//流量包
			  var texts = text.split("M");
			  for(var j = 0; j<texts.length; j++){
				  ctx.font = j == 0?'bold 20px Microsoft YaHei':'16px Microsoft YaHei';
				  if(j == 0){
					  ctx.fillText(texts[j]+"M", -ctx.measureText(texts[j]+"M").width / 2, j * line_height);
				  }else{
					  ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
				  }
			  }
		  }else if(text.indexOf("M") == -1 && text.length>6){//奖品名称长度超过一定范围 
			  text = text.substring(0,6)+"||"+text.substring(6);
			  var texts = text.split("||");
			  for(var j = 0; j<texts.length; j++){
				  ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
			  }
		  }else{
			  //在画布上绘制填色的文本。文本的默认颜色是黑色
			  //measureText()方法返回包含一个对象，该对象包含以像素计的指定字体宽度
			  ctx.fillText(text, -ctx.measureText(text).width / 2, 0);
		  }
		  
		  //添加对应图标
		  if(text.indexOf("闪币")>0){
			  var img= document.getElementById("shan-img");
			  img.onload=function(){  
				  ctx.drawImage(img,-15,10);      
			  }; 
			  ctx.drawImage(img,-15,10);  
		  }else if(text.indexOf("谢谢参与")>=0){
			  var img= document.getElementById("sorry-img");
			  img.onload=function(){  
				  ctx.drawImage(img,-15,10);      
			  };  
			  ctx.drawImage(img,-15,10);  
		  }
		  //把当前画布返回（调整）到上一个save()状态之前 
		  ctx.restore();
		  //----绘制奖品结束----
	  }     
  } 
}
	//获取奖品序号
	function obtainOrderNum(){
		var num;
		$.ajax({
		   type: "GET",
		   async: false,
		   dataType:"json",
		   url: "${pageContext.request.contextPath}/activityManage/obtainOrderNum.action?awardActivityId=${awardActivityId}&anwserUserId=${anwserUserId}&obtainOrderNumFlag=${obtainOrderNumFlag}&surveyEvaluationId=${surveyEvaluationId}",
		   success: function(result){
		   		if(result.status){
			     	num = result.data;
		   		}else{
		   			$.messager.alert("提示",result.msg,"info");
		   			num = 0;
		   		}
		   }
		});
		return num;
	}
</script>
</head>
<body style="background:#e62d2d;overflow-x:hidden;">
    <img src="${pageContext.request.contextPath}/resources/images/wheelSurf/1.png" id="shan-img" style="display:none;" />
    <img src="${pageContext.request.contextPath}/resources/images/wheelSurf/2.png" id="sorry-img" style="display:none;" />
	<div class="banner">
		<div class="turnplate" style="background-image:url(${pageContext.request.contextPath}/resources/images/wheelSurf/turnplate-bg.png);background-size:100% 100%;">
			<canvas class="item" id="wheelcanvas" width="422px" height="422px"></canvas>
			<img class="pointer" src="${pageContext.request.contextPath}/resources/images/wheelSurf/turnplate-pointer.png"/>
		</div>
	</div>
	<div>
	${activityDetail }
<!-- 	<h1>活动详情介绍:</h1> -->
<!-- 		1.活动时间：2016年6月27日至2016年7月1日； -->
<!-- 		2.活动地点：郑州市各加油站； -->
<!-- 		3.活动内容：参与郑州市加油站质量评价问卷活动，可参与抽奖； -->
<!-- 		4.兑奖方式：请关注质讯通公众微信号进行查询； -->
	</div>
</body>
</html>