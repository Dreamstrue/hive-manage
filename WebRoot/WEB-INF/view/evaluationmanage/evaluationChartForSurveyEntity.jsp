<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String zxtUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();//+ "/zxt"部署项目时候屏蔽;; // 项目访问全路径，形如：http://localhost:8080/zyzlcxw
	//String zxtUrl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ "/zxt";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>评价信息管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../common/inc.jsp" />
  </head>
  <script type="text/javascript">    
    //选择行业实体信息 时候 级联处 该实体  行业类别对应的 问卷列表  yf 20160303 add
	function onccSelect(){
    	if($('#entityName').combobox('getText')=="全部"){
    		var url = '${appPath}/surveyManage/findAllSurvey.action';
    		$('#surveyName').combobox('reload',url);
    		 var val = $('#entityName').combobox('getData');
    		 var entitys="";
    		 for(var i=0;i<val.length-1;i++){
     		   entitys=entitys+val[i].id+",";
     		   if(i==(val.length-2)){
     			  entitys= entitys.substring(0, entitys.length-1)
     		   }
    		 }
    		 $("#entityId").val(entitys);
    	}else{
		var industryEntityId = $('#entityName').combobox('getValue');
		 $("#entityId").val(industryEntityId);
		var url = '${pageContext.request.contextPath}/surveyManage/getSurveyByindustryEntity.action?industryEntityId='+industryEntityId;
		$('#surveyName').combobox('reload',url);
    	}
	}
</script> 
  <body>
  <div class="easyui-layout" data-options="fit : true,border : false" >
	<div data-options="region:'north',title:'查询条件',border:false" style="height: 100px; overflow: hidden;">
        <form action="javascript:void(0);" id="searchForm" style="margin-bottom: 10px;">
        <input type="hidden" id = "sepecialObjectId" value=""/>
        <table id="toolbar" style="width: 100%">
                <tbody>
                    <tr>
		                <div style="text-align:center;margin-top:10px">
					        <span style="margin-left: 20px;">行业实体：</span>
							<input class="easyui-combobox" name="entityName" id="entityName" data-options="valueField: 'id',textField: 'entityName', url:'${appPath}/industryEntityManage/getAllIndustryEntityList.action',editable:false,prompt:'请选择一个实体',onSelect:function(re){
								$('#sepecialObjectId').val(re.objectId);
								onccSelect();
							}"  style="width:15%;height: 25px;">
							<input id="entityId" name="entityId" value="" type="hidden" />
							<span style="margin-left: 20px;">问卷主题：</span>
							<input class="easyui-combobox" name="surveyName" id="surveyName" data-options="valueField: 'id',textField: 'subject', url:'${appPath}/surveyManage/findAllSurvey.action',editable:false,prompt:'请选择一个问卷'"  style="width:15%;height: 25px;">
							<span style="margin-left: 20px;">开始时间：</span>
		                    <input class="easyui-datebox" name="beginTime" id="beginTime" data-options="prompt:'开始日期'"  editable="false"/>
							<span style="margin-left: 20px;">结束时间：</span>
							<input class="easyui-datebox" name="endTime" id="endTime" data-options="prompt:'结束日期'"  editable="false"/>						
						</div>
				       </tr>
                    <tr>
                        <td colspan="12" align="center">
 						<a  href="javascript:void(0);" id="btn1" class="easyui-linkbutton" data-options="iconCls: 'icon-search'" style="width:80px;height:25px; margin-left: 40px;" onclick="vote_manage_search_fn()"><span style="font-size:16px">查询</span></a>
 						<a  href="javascript:void(0);" id="btn2" class="easyui-linkbutton" data-options="iconCls: 'icon-large-chart'" style="width:80px;height:25px; margin-left: 0;" onclick="vote_manage_search_fn_result()"><span style="font-size:16px">统计</span></a>
		                <a  href="javascript:void(0);" id="cleanBtn" class="easyui-linkbutton" data-options="iconCls: 'icon-cancel'" style="width:80px;height:25px"><span style="font-size:16px">清空</span></a>
		          	    <c:if test="${(sessionScope.isAdmin == '1') }">
		                  &nbsp; <a  href="javascript:void(0);" id="realodBtn" class="easyui-linkbutton" data-options="iconCls: 'icon-reload'" style="width:180px;height:25px;border-color: red" ><span style="font-size:16px">重新初始化数据</span></a>
	                    </c:if>
                        </td>
                    </tr>
                </tbody>
                </table>
        </form>
        <input id="gasIdList" name="gasIdList" value="${gasIdList}" type="hidden" />
        <input id="otherSystemMark" name="otherSystemMark" value="${otherSystemMark}" type="hidden" />
    </div>
    <div data-options="title:'查询结果',region:'center',border:false">
    <iframe id="iftess" scrolling="auto" frameborder="0"    style="width:100%;height:100%;"></iframe>
    </div>
    </div>
  </body>
  
  
  <script type="text/javascript">
	
	$(function() {
		
// 		$('#iftess').attr('src','${zxt_url}/SurveyChartsForEntityController/toSuveyChart.action?entityId=0');
		$("#cleanBtn").click(function() {
			$("#searchForm").form("clear");
			$('#entityName').combobox('reload');
			var url = '${pageContext.request.contextPath}/surveyManage/findAllSurvey.action';
			$('#surveyName').combobox('reload',url);
			$('#iftess').attr('src','');
		});
		$("#realodBtn").click(function() {
			var beginTime = $('#beginTime').datebox('getValue'); 
			var endTime = $('#endTime').datebox('getValue'); 
			var message="";
			var remoturl="";
			if(beginTime!=''||endTime!=''){
				if(beginTime!=''&&endTime!=''){
		           if (beginTime > endTime) {  
		               alert("结束时间不得小于开始时间。");  
		               return false;  
		           }
				}
				var aletmes="";
				if(beginTime!=''){
					aletmes=beginTime+"之后";
				}
				if(endTime!=''){
					aletmes+=endTime+"之前";
				}
				message="您将要初始化"+aletmes+"的数据，并重新加载，请谨慎操作！";
				remoturl="${zxt_url}/SurveyChartsForSurveyEntityController/dumpPartHistoryforSurveyEntity.action?beginTime="+beginTime+"&endTime="+endTime;
			}
			if(beginTime==''&&endTime==''){
				message="如果选择重新初始化数据，会将表数据清空，并重新加载，请谨慎操作！";
				remoturl="${zxt_url}/SurveyChartsForSurveyEntityController/dumpHistoryforSurveyEntity.action";
			}
			$.messager.confirm('警告！', message,function(r){
				if (r){
					beforeSend:showProcess(true, '温馨提示', '正在初始化数据...');//发送请求前打开进度条 
					 $.ajax({
							url : remoturl,
							data : {},
							dataType : 'json',
							success : function(r) {
								showProcess(false);
							    if (r.status){
									$.messager.show({
										title : '提示',
										msg : r.msg
									});
							    }
							 },
							error:function(result) {
								showProcess(false);
								$.messager.show({
									title : '提示',
									msg : '初始化失败'
								});
							}
						});
				}
			});
			$("#searchForm").form("clear");
			$('#entityName').combobox('reload');
			var url = '${pageContext.request.contextPath}/surveyManage/findAllSurvey.action';
			$('#surveyName').combobox('reload',url);
			$('#iftess').attr('src','');
		});
 		var otherSystemMark=$("#otherSystemMark").val();
		if(otherSystemMark=="jiayouzhan"){
 		    var gasIdList=$("#gasIdList").val();
			var url="${appPath}/industryEntityManage/getAllIndustryEntityListForOther.action?gasIdList="+gasIdList;
 			$('#entityName').combobox('reload',url);
		}
	});
	function vote_manage_search_fn() {
		//var entityId = $('#entityName').combobox('getValue');
		var entityId = $('#entityId').val();
		var surveyId = $('#surveyName').combobox('getValue');
		var beginTime = $('#beginTime').datebox('getValue'); 
		var endTime = $('#endTime').datebox('getValue'); 
		if(beginTime!=''&&endTime!=''){
           if (beginTime > endTime) {  
               alert("结束时间不得小于开始时间。");  
               return false;  
           }  
		}
		if (entityId==''&&surveyId=='') {
			$.messager.alert('温馨提示','请选择一个行业或者实体!','warning');
		} else {
			var url="${zxt_url}/SurveyChartsForSurveyEntityController/toSuveyChart.action?entityId="+entityId+"&surveyId="+surveyId+"&beginTime="+beginTime+"&endTime="+endTime;
			$('#iftess').attr('src',url);
		}
	}
	function vote_manage_search_fn_result() {//20160627 yyf add
		var objectId = $('#sepecialObjectId').val();
		var surveyId = $('#surveyName').combobox('getValue');
		var beginTime = $('#beginTime').datebox('getValue'); 
		var endTime = $('#endTime').datebox('getValue'); 
		if(objectId==""||surveyId==""){
			alert("行业实体和问卷主题不能为空！");  
               return false; 
		}
		if(beginTime!=''&&endTime!=''){
           if (beginTime > endTime) {  
               alert("结束时间不得小于等于开始时间。");  
               return false;  
           }  
		}
		if (entityId==''&&surveyId=='') {
			$.messager.alert('温馨提示','请选择一个行业或者实体!','warning');
		} else {
			var url="${zxt_url}/surveyCountManage/toSurveyCount1.action?objectId="+objectId+"&surveyId="+surveyId+"&beginTime="+beginTime+"&endTime="+endTime;
			$('#iftess').attr('src',url);
		}
	}
	
	function showProcess(isShow, title, msg) {
		if (!isShow) {
			$.messager.progress('close');
			return;
		}
		var win = $.messager.progress({
			title: title,
			msg: msg
		});
		setTimeout(function(){
			$.messager.progress('close');
		},15000)
	}
  </script>
</html>