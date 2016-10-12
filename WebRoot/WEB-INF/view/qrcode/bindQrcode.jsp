<%@page import="com.hive.common.SystemCommon_Constant"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>二维码绑定</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/metro/easyui.css"></link>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/mobile.css"></link>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/color.css"></link>  
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/icon.css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.easyui.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.easyui.mobile.js" charset="UTF-8"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js" charset="UTF-8"></script>

    <style>
        .selects_other {
            position: inherit;
            left:-0.02rem;
            top:1.6rem;
            width:100%;
            display: none;
            z-index:999;
            border-top: 1px solid #ccc;
        }
        .selects_other li {
            border: 1px solid #ccc;
            border-top:none;
            display: block;
            width:100%;
            padding:0.1rem 0;
            text-indent: 0.5rem;
            height: 1.6rem;
            line-height: 1.6rem;
            background-color: #DFDFDF
        }
    </style>
<script type="text/javascript">
	$(function(){
	var path = "<%=SystemCommon_Constant.QRCODE_PATH%>";
	$('.icon-search').click(function () {
		//移除所有的li标签 
		$(".licl").remove();
		var r = $('#hyst').textbox("getValue");
// 		alert(r+"%%");
           $.ajax({
			type : 'POST',
			async : false,
			url : '${pageContext.request.contextPath}/industryEntityManage/getAllIndustryEntityList.action',
			data : 'q='+r,
			dataType : 'json',
			success : function(d) {
				    $.each(d,function(n,value) {   
// 			           alert(n+' '+value.entityName); 
			           	var html = "<li class='licl' idStr='"+value.id+"' nameStr='"+value.entityName+"' url_='"+path+value.id+"' onclick='selectedIt(this);' >"+value.entityName+"</li> ";
			           	$(".selects_other").append(html);
			        });
			        if(d.length==0){
			        	var html = "<li class='licl' idStr='' nameStr='' onclick='selectedIt(this);' >没有查询到符合条件的内容</li> ";
			           	$(".selects_other").append(html);
			        }
	            if ($(".selects_other").css("display") == 'none') {
		             $(".selects_other").show();
		        } 
			}
		});
	});
	//行业实体获取焦点时，清空之前的值
	$('#hyst').textbox('textbox').focus(function() {
		$("#hyst").textbox("setValue","");
		$("#contentId").textbox("setValue","");
		$("#entityId").val("");
    });
	})
	function selectedIt(p){
		var id = $(p).attr("idStr");
		var name = $(p).attr("nameStr");
		var url_ = $(p).attr("url_");
		$(".selects_other").css("display","none");
		$("#hyst").textbox("setValue",name);
		$("#contentId").textbox("setValue",url_);
		$("#entityId").val(id);
	// 	alert(id+"$$"+name);
	}
	function bindQrcode(){
		//获取绑定密码
		var password = $("#password").textbox("getValue");
// 		alert("密码："+password);
		var r = password;
		    if (r!=null&&r!=""){
		           $.ajax({
					type : 'POST',
					async : false,
					url : '${pageContext.request.contextPath}/qrcode/checkPassword.action',
					data : 'password='+r,
					dataType : 'json',
					success : function(d) {
						if(d.status){
							$('#bindQrcodeForm').form('submit',{
							    url:"${pageContext.request.contextPath}/qrcodeIssue/qrcodeIssueTemporaryAdd.action",
							    onSubmit: function(){
									return $(this).form('validate');
							    },
							    success:function(data){
									var r = $.parseJSON(data);
										$("#bindButton").remove();
										$.messager.alert("提示",r.msg,"info");
							    }
							});
						}else{
							$.messager.alert("提示",d.msg,"info");
						}
					}
				});
		    }else{
				$.messager.alert("提示","请输入绑定密码！","info");
		    }
	}
</script>
</head>
<body>
    <div class="easyui-navpanel"  style="position:relative;padding:1px">
        <header style="padding:1px;background-color: #289ED7;color:#FFFFFF;">
            <div class="m-toolbar">
                <div class="m-title">二维码绑定</div>
            </div>
        </header>
        <form id="bindQrcodeForm"  method="post" enctype="multipart/form-data">
        	<input type="hidden" id="status" name="status" value="1"/>
        	<input type="hidden" id="qrcodeId" name="qrcodeId" value="${vo.id }"/>
            <div style="margin-bottom:10px;margin-top:10px;">
                <label class="textbox-label" style="font-size:16px;">行业实体:</label>
                <input class="easyui-textbox" id="hyst" style="width:100%;" data-options="iconCls:'icon-search'" missingMessage="请输入实体名称点击查询" required="true">
                <input type="hidden" id="entityId" name="entityId" />
                <div class="selects_other">
            	</div>
            </div>
            <div style="margin-bottom:10px;">
                <label class="textbox-label" style="font-size:16px;">SN码:</label>
                <input class="easyui-textbox" name="sn" style="width:100%" value="${vo.sn}" readonly="readonly" required="true">
            </div>
            <div style="margin-bottom:10px;">
                <label class="textbox-label" style="font-size:16px;">二维码编号:</label>
                <input class="easyui-textbox" name="qrcodeNumber" style="width:100%" value="${vo.qrcodeNumber}" readonly="readonly" required="true">
            </div>
            <div style="margin-bottom:10px;">
                <label class="textbox-label" style="font-size:16px;">二维码类别:</label>
               	<input type="hidden" name="qrcodeType" value="1"/>
                <input class="easyui-textbox" style="width:100%" value="url" readonly="readonly" required="true">
            </div>
            <div style="margin-bottom:10px;">
                <label class="textbox-label" style="font-size:16px;">二维码路径:</label>
                <input class="easyui-textbox" data-options="multiline:true" style="width:100%;height:50px;" value="${vo.qrcodeValue}" readonly="readonly" required="true">
            </div>
            <div style="margin-bottom:10px;">
                <label class="textbox-label" style="font-size:16px;">绑定内容:</label>
                <input class="easyui-textbox" id="contentId" name="qrcodeContent" data-options="multiline:true" style="width:100%;height:50px;" value="" readonly="readonly" required="true">
            </div>
            <div style="margin-bottom:10px;">
                <label class="textbox-label" style="font-size:16px;">请输入绑定密码:</label>
                <input class="easyui-textbox" id="password" validType="length[1,32]" required="true" type="password"  style="width:100%;" required="true"/>
            </div>
        </form>
		<div style="text-align:center;margin-top:15px;margin-bottom:15px;">
              <a class="easyui-linkbutton" id="bindButton" plain="true" outline="true" style="width:80px;height:25px;background-color: #289ED7;color:#FFFFFF;" onclick="bindQrcode();"><span style="font-size:14px">提交</span></a> 
        </div>
    </div>
</body>
</html>