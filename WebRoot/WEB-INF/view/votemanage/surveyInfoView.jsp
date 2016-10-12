<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	 <head>
	    <title>问卷信息查看</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<jsp:include page="../common/inc.jsp" />
	 </head>
	
	<body>
		<div align="center" style="width: 100%;">
			<br>
			<form id="dataForm" method="post" style="width: 100%">
				<input type="hidden" name="id" value="${surveyInfo.id}"/>
				<table class="tableForm" style="width: 100%;font-size: 12px;">
					<tr>
						<th>标题</th>
						<td colspan="3">
							<input name="subject" style="width:550px;" class="easyui-validatebox" data-options="required:true,validType:'maxLength[100]'" value="${surveyInfo.subject}"/>
							<span style="color:red;margin-left:20px;">必填</span>
						</td>
					</tr>
					<tr>
						<th>前言</th>
						<td colspan="3">
							<textarea name="preface" style="width:550px;height:70px;" class="easyui-validatebox" data-options="required:true,validType:'maxLength[2000]'">${surveyInfo.preface}</textarea>
							<span style="color:red;margin-left:20px;vertical-align:5px;">必填</span>
						</td>
					</tr>
					<tr>
						<th>问卷题目</th>
						<td colspan="3">
							<textarea name="question" style="width:550px;height:70px;" class="easyui-validatebox" data-options="required:true,validType:'maxLength[2000]'">${surveyInfo.question}</textarea>
							<span style="color:red;margin-left:20px;vertical-align:5px;">必填</span>
						</td>
					</tr>
					<tr>
						<th>有效期起</th>
						<td>
							<input id="startDate" name="validBegin" class="easyui-datebox" data-options="required:true, editable:false, onSelect:openEndDateCalendar" value="${surveyInfo.validBegin}"/>
						</td>
						<th>有效期止</th>
						<td>
							<input id="endDate" name="validEnd" class="easyui-datebox" data-options="required:true, editable:false, validType:'dateGE[\'startDate\']', invalidMessage: '有效期结束时间不能早于有效期开始时间'" value="${surveyInfo.validEnd}"/>
						</td>
					</tr>
				</table>
			</form>
		</div>

	</body>
</html>