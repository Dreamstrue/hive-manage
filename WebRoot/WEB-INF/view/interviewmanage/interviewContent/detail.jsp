<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<script type="text/javascript">
</script>
<div align="left" style="width:70%; height: 100%;">
<div id="div1" >当前位置：名家访谈&nbsp;&gt;&nbsp;${interviewSubject}&nbsp;&gt;&nbsp;直播内容详情</div>
<div id="div2" align="right"><a id="btn" href="${pageContext.request.contextPath}/interviewContent/manage.action?interviewId=${vo.nintonlid }" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true">返回直播内容列表</a> </div>
<div align="left" >
	<table class="integrity_table" style="width: 100%">
		<tr>
			<td>
				<div>
					<table >
						<tr>
							<th style="width:5px;">详<br/>细<br/>内<br/>容<br/></th>
							<td>
								<div style="width:760px;border:1px solid silver;word-wrap: break-word; word-break: normal;">${vo.cdialoguecontent }</div>
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>
</div>
