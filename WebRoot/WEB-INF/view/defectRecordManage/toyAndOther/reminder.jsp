<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="../../common/inc_defact.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=0">
<title>国家质检总局缺陷产品管理中心</title>
</head>
<body>
<div class="main">
	<!--内容区-->
    <div class="wel_box">
    	<div class="wel_img"><img src="${appPath}/resources/defect/images/img1.jpg"></div>
        <div class="wel_txt">
        	<input type="hidden" name="peporttype" value="${defectType}">
        	<p>温馨提示</p>
            <p>消费者可在此提交${reminderStr}可能存在的缺陷信息，我们将对提交的信息完整性进行确认。 </p>
            <p>提交缺陷信息报告前请阅读以下内容：</p>
            <p>1、请确保您所提交报告的真实性和客观性，我们可能在后续的信息核实和缺陷调查工作中会与您取得联系，了解更多情况。</p>
            <p>2、对于使用不当造成的问题、经济纠纷问题、怀疑推测、制假售假等问题不在缺陷信息采集范围内。</p>
            <p>3、受工作职责所限，我们可能无法做到每份报告都做出回应，也无法调解消费纠纷、损失赔偿。请您理解。</p>
            <p>4、我们承诺您的个人信息将不会用于开展缺陷信息分析和缺陷调查工作之外的其他用途。</p>
            <p>5、如您仍有其他问题，欢迎您致电国家质检总局缺陷产品管理中心电话：010-59799616</p>
        </div>
        <div class="wel_butt"><a href="toFormPage.action?defectType=${defectType}">我同意</a></div>
    </div>
    
    <!--内容区-->
</div>
</body>

</html>
