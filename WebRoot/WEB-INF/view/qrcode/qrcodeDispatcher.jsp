<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui/jquery-1.11.2.min.js" charset="utf-8"></script>
<script type="text/javascript">
$(function() {
	var type = "${vo.qrcodeType}";
	if(type == '1'){
		location.href = "${vo.content}";
	}else{
// 		alert("${vo.qrcodeTypeStr}类型暂未开通！");
		location.href = "${pageContext.request.contextPath}${vo.showSimpleQrcodeUrl}";
	}
});
</script>
