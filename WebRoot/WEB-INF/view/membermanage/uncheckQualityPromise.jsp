<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>企业承诺书审核</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<jsp:include page="../common/inc.jsp"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.form.js"></script>
  </head>
  
  <body>
  	<div class="easyui-layout" fit="true">
		<!-- 查询条件 -->
		<div region="north" split="true" title="查询条件" border="false" class="p-search" style="overflow: hidden; height:80px; padding-left: 10px; padding-top: 5px;">
			<form action="javascript:void(0);" id="searchForm" style="width: 100%">
				<label style="font-size: 12px;">公司名称:</label>
				<input name="enterpriseName" style="width: 215px;" />
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFunc();">查询</a>
			</form>
		</div>
		<!-- 数据列表 -->
	  	<div region="center" border="false">
			<table id="member_manage_grid"></table>
	  	</div>
	</div>
	
	<!-- 弹出窗口：添加数据 --> 
	<div id="mydialog" style="display:none;padding:5px;width:400px;height:300px;" title="承诺书审核"> 
		<form id="form1" action="">
		<input type="hidden" id="ids" name="ids" value="" />
			<table>
			<tr>
			<td>审核结果：</td><td><select id="checktype" name="checktype">
					  <option value ="1">通过</option>
					  <option value ="2">不通过</option>
					</select></td>
			</tr>
			<tr>
			<td>审核意见：</td><td><textarea rows="5" cols="30" id="remark" class="easyui-validatebox" data-options="required:true" name="remark"></textarea> </td>
			</tr>
			</table>
		</form>
	</div>
  
  
	<script type="text/javascript">
		var searchFormName = "searchForm";
  		var gridName = "member_manage_grid";
		
		$(function() {
			$('#'+gridName).datagrid({
					url : '${appPath}/membermanage/listUncheckQualityPromise.action',
					fit : true,
					fitColumns : true,
					border : false,
					pagination : true,
					striped:true,
					rownumbers : true,
					idField : 'nentproid',
					pageSize : 20,
					pageList : [10, 20, 30, 40, 50, 60],
					sortName : '',
					sortOrder : 'desc',
					singleSelect:false,
					nowrap : false,
					toolbar : [
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
					{
						text : '批量审核',
						iconCls : 'icon-add',
						handler : function() {
							check(0);
						}
					} 
					</c:if>
					],
					columns : [ [
					{
						field : 'enterpriseName',
						title : '公司名称',
						width : 60
					}
					,{
						field : 'indCatName',
						title : '行业分类',
						width : 40
					}
					,{
						field : 'nquaproid',
						title : '承诺书照片',
						width : 30,
						formatter:function(value,row,index){
	   						//return formatString("<a target='_blank' href='${appPath}/membermanage/previewPicture.action?qualityPromiseId="+value+"'>点击查看</a>"); // 新窗口查看
	   						return formatString("<a href='javascript:;' onclick='fiximgwin(\"${appPath}/membermanage/previewPicture.action?qualityPromiseId="+value+"\")'>点击查看</a>"); // 弹出窗口查看
	   					}
					}
					,{
						field : 'dcreatetime',
						title : '创建时间',
						width : 30
					}
					,{
						field : 'cauditstatus',
						title : '审核状态',
						width : 40,
						formatter : function(value, row, index) {
							if(value == 0)
							{
								return "未审核";
							}else if(value == 1)
							{
								return "通过";
							}else if(value == 2)
							{
								return "未通过";
							}
						}
					}
					<c:if test="${(sessionScope.isAdmin == '1') || requestScope.canAudit}">
					,{
	   					field:'action',
	   					title:'审批',
	   					width:20,
	   					formatter:function(value,row,index){
	   						return formatString('<img onclick="check(1,\'{0}\');" title="查看详情并审批" src="{1}"/>', row.nquaproid, '${pageContext.request.contextPath}/resources/images/extjs_icons/book_open.png');
	   							
	   					}
	   				}
	   				</c:if>
					]]
				});
		});
		
		/*
		 * "查询"按钮的事件处理
		 *	
		*/
		function searchFunc(){
			$('#'+gridName).datagrid('load', serializeObject($('#'+searchFormName)));
		}
		
		function check(type,id,name){
			if(type==0){
				node = $('#'+gridName).datagrid('getSelections');
				if(node==''||node.length<1){
					$.messager.alert(
						'提示',
						'请选择要审核的企业承诺书'
					);
				}else{
					var ids='';
					var names='';
					$.each(node,function(){
						if(ids==''){
							ids+=this.nquaproid;
						}else{
							ids+=','+this.nquaproid;
						}
					});
					//alert(ids);
					$('#ids').val(ids);
					Open_Dialog(); 
				}
			}else if(type==1){
				$('#ids').val(id);
				Open_Dialog();
			}
		}
		
		function checkAdd(){
			if($("#form1").form('validate')){
				var param=$('#form1').formSerialize();
				$.post('${appPath}/membermanage/checkQualityPromise.action',param, function (re) {
			        if (re.status) {
			        	$.messager.alert('提示',re.msg);
			        	$('#mydialog').dialog('close');
			        	$('#'+gridName).datagrid('load');
			        }
			    }, 'json');
			    $('#'+gridName).datagrid('unselectAll');
			    window.parent.reloadWestTree();//刷新左侧菜单
		    }
		}
		
		function Open_Dialog() {
			$('#mydialog').show(); 
			$('#mydialog').dialog({ 
				collapsible: true, 
				maximizable: true, 
				modal:true,
				buttons: [
					{ 
					text: '提交', 
					iconCls: 'icon-ok', 
					handler: checkAdd 
					}, 
					{ 
					text: '取消', 
					handler: function() { 
					$('#mydialog').dialog('close'); 
					} 
					}
				] 
			}); 
		}
		
		// 弹出窗口查看图片
		function fiximgwin(url){ 
			// 固定窗口大小
			var winWidth = 600;
			var winHeight = 800;
			var moveLeft = (screen.width - winWidth)/2 -10; // screen.width 表示屏幕宽度，差值为弹出窗口左上角距离屏幕左边距离
			var moveTop = (screen.height - winHeight)/2 - 36; // 上面的 -10 是减去浏览器右侧滚动条的宽度，这儿的 -36是减去弹出窗口地址栏及标题的高度
			//打开一个空白窗口，并初始化大小 
		 	var imgwin=window.open('','img','left=' + moveLeft + ',top=' + moveTop + ',width=' + winWidth + ',height=' + winHeight + ',location=no') 
			imgwin.focus() //使窗口聚焦，成为当前窗口 
			//这里是关键代码，在图片加载完后调用resizeTo()和 
			//moveTo()方法调整窗口大小和位置 
		 	//var HTML="<html>\r\n<head>\r\n<title>图片浏览</title>\r\n</head>\r\n<body leftmargin=\"0\" topmargin=\"0\">\r\n<img src=\""+url+"\" onload=\"window.resizeTo(this.width+10,this.height+36);window.moveTo((screen.width-this.width)/2,(screen.height-this.height)/2)\">\r\n</body>\r\n</html>" // 窗口大小随着图片大小调整
		 	var HTML="<html>\r\n<head>\r\n<title>图片浏览</title>\r\n<style type=\"text/css\">.suofang {MARGIN: auto;WIDTH: " + winWidth + "px;HEIGHT: " + winHeight + "px;text-align:center;vertical-align:middle}.suofang img{MAX-WIDTH: 100%!important;MAX-HEIGHT: 100%!important;WIDTH:expression(this.width > " + winWidth + " ? \"" + winWidth + "px\" : this.width)!important;HEIGHT:expression(this.height > " + winHeight + " ? \"" + winHeight + "px\" : this.height)!important;}</style></head>\r\n<body leftmargin=\"0\" topmargin=\"0\">\r\n<div class='suofang'><img src=\""+url+"\" onload=\"window.moveTo(" + moveLeft + "," + moveTop + ")\"></div>\r\n</body>\r\n</html>" // 窗口大小固定
			var doc=imgwin.document 
		 	doc.write(HTML) // 向空白窗口写入代码 
		 	doc.close() // 关闭输入流，并强制发送数据显示。 
		} 
	</script>
</body>
</html>
