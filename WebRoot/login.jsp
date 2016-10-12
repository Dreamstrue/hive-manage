<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD id=Head1>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<link rel="shortcut icon" type="image/x-icon" href="${pageContext.request.contextPath}/favicon.ico" media="screen" />
<title>质讯通—综合管理平台</title>
<jsp:include page="WEB-INF/view/common/inc.jsp" />
<STYLE type=text/css>
	BODY {FONT-SIZE: 12px; COLOR: #ffffff; FONT-FAMILY: 宋体}
	TD {FONT-SIZE: 12px; COLOR: #ffffff; FONT-FAMILY: 宋体}
</STYLE>
<META content="MSHTML 6.00.6000.16809" name=GENERATOR></HEAD>
<BODY>
<FORM method="post" id="user_login_loginInputForm">
<DIV id=UpdatePanel1>
<DIV id=div1  style="LEFT: 0px; POSITION: absolute; TOP: 0px; BACKGROUND-COLOR: #0066ff"></DIV>
<DIV id=div2  style="LEFT: 0px; POSITION: absolute; TOP: 0px; BACKGROUND-COLOR: #0066ff"></DIV>
<SCRIPT language=JavaScript> 
$(function() {
	
	$('#user_login_loginInputForm').form({
		url : '${pageContext.request.contextPath}/security/login.action',
		success : function(result) {
			var r = $.parseJSON(result);
			if (r.status) {
				location.href='${pageContext.request.contextPath}/layout/index.action';
			}else{
				$.messager.show({
					title : '提示',
					msg : r.msg
				});
			}
		}
	});
	
    //点击切换验证码事件           
    $('#kaptchaImage').click(function () {//生成验证码   
    	$(this).hide().attr('src', '${pageContext.request.contextPath}/CaptchaImage/create.action?' + Math.floor(Math.random()*100) ).fadeIn(); 
    }); 
	
});

var speed=20;
var temp=new Array(); 
var clipright=document.body.clientWidth/2,clipleft=0 
for (i=1;i<=2;i++){ 
	temp[i]=eval("document.all.div"+i+".style");
	temp[i].width=document.body.clientWidth/2;
	temp[i].height=document.body.clientHeight;
	temp[i].left=(i-1)*parseInt(temp[i].width);
} 
function openit(){ 
	clipright-=speed;
	temp[1].clip="rect(0 "+clipright+" auto 0)";
	clipleft+=speed;
	temp[2].clip="rect(0 auto auto "+clipleft+")";
	if (clipright<=0)
		clearInterval(tim);
} 
tim=setInterval("openit()",100);
</SCRIPT>

<DIV>&nbsp;&nbsp; </DIV>
<DIV>
<TABLE cellSpacing=0 cellPadding=0 width=900 align=center border=0>
  <TBODY>
  <TR>
    <TD style="HEIGHT: 105px"><IMG src="${pageContext.request.contextPath}/resources/images/login/1.gif"  border=0></TD></TR>
  <TR>
    <TD background=${pageContext.request.contextPath}/resources/images/login/2.jpg height=300>
      <TABLE height=300 cellPadding=0 width=900 border=0>
        <TBODY>
        <TR>
          <TD colSpan=2 height=35></TD></TR>
        <TR>
          <TD width=360></TD>
          <TD>
            <TABLE cellSpacing=0 cellPadding=2 border=0>
              <TBODY>
              <TR>
                <TD style="HEIGHT: 28px" width=80>登 录 名：</TD>
                <TD style="HEIGHT: 28px" width=150><INPUT id=txtName style="WIDTH: 130px" name="username" value="admin"></TD>
                <TD style="HEIGHT: 28px" width=370><SPAN id=RequiredFieldValidator3 style="FONT-WEIGHT: bold; VISIBILITY: hidden; COLOR: white">请输入登录名</SPAN></TD>
              </TR>
              <TR>
                <TD style="HEIGHT: 28px">登录密码：</TD>
                <TD style="HEIGHT: 28px"><INPUT id=txtPwd style="WIDTH: 130px" type=password  name="password" value="admin"></TD>
                <TD style="HEIGHT: 28px"><SPAN id=RequiredFieldValidator4  style="FONT-WEIGHT: bold; VISIBILITY: hidden; COLOR: white">请输入密码</SPAN></TD></TR>
              <TR>
                <TD style="HEIGHT: 28px">验证码：</TD>
                <TD style="HEIGHT: 28px"><INPUT id=txtcode style="WIDTH: 130px" name="userInputCheckCode"></TD>
                <TD style="HEIGHT: 28px"><img id="kaptchaImage" src="${pageContext.request.contextPath}/CaptchaImage/create.action" alt="点击验证码刷新" width="75" height="23" />&nbsp;</TD></TR>
              <TR>
                <TD style="HEIGHT: 18px"></TD>
                <TD style="HEIGHT: 18px"></TD>
                <TD style="HEIGHT: 18px"></TD>
              </TR>
              <TR>
                <TD></TD>
                <TD><INPUT id=btn  style="BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px"  type=image src="${pageContext.request.contextPath}/resources/images/login/button.gif" name=btn> </TD>
              </TR>
            </TBODY>
          </TABLE>
        </TD>
       </TR>
     </TBODY>
   </TABLE>
  </TD>
  </TR>
  	<TR>
    	<TD><IMG src="${pageContext.request.contextPath}/resources/images/login/3.jpg" border=0></TD>
	</TR>
</TBODY>
</TABLE>
</DIV>
</DIV>
<DIV> 
</DIV>
</FORM>
</BODY>
</HTML>
