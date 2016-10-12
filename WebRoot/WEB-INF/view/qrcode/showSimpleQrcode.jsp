<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>二维码详情</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/metro/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/mobile.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/color.css">  
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/icon.css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.easyui.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.easyui.mobile.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>
<script type="text/javascript">
	function toBindQrcode_(){
		var id = $("#qrcodeId").val();
		location.href = "${pageContext.request.contextPath}/qrcode/toBindQrcode.action?qrcodeId="+id;
	}
</script>
</head>
<body>
    <div class="easyui-navpanel"  style="position:relative;padding:1px">
        <header style="padding:1px;background-color: #289ED7;color:#FFFFFF;">
            <div class="m-toolbar">
                <div class="m-title">二维码简要信息</div>
            </div>
        </header>

        <div style="text-align:center;margin-top:15px">
        	<input type="hidden" id="qrcodeId" value="${vo.id }"/>
       		<table class="tableForm" style="width: 100%">
				<tr>
					<th align="right" style="width: 120px;">SN码：</th>
					<td align="left">${vo.sn}
	                </td>
				</tr>
				<tr>
					<th align="right" style="width: 120px;">二维码编号：</th>
					<td align="left">${vo.qrcodeNumber}
	                </td>
				</tr>
				<tr>
					<th align="right" style="width: 120px;">二维码路径：</th>
					<td align="left" width=300 style="word-break:break-all">${vo.qrcodeValue }
	                </td>
				</tr>
			</table>
		</div>
		<div style="text-align:center;margin-top:30px">
              <a class="easyui-linkbutton" id="toBindButton" plain="true" outline="true" style="width:80px;height:25px;background-color: #289ED7;color:#FFFFFF;" onclick="toBindQrcode_()"><span style="font-size:14px">绑定</span></a> 
        </div>
    </div>
</body>
</html>