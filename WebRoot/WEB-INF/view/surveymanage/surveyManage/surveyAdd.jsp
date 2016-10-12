<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	 <head>
	    <title>添加问卷</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<jsp:include page="../../common/inc.jsp" />
		<link rel="stylesheet" href="${appPath}/resources/js/plugin/Jcrop/css/jquery.Jcrop.min.css" type="text/css"></link>
		<script type="text/javascript" src="${appPath}/resources/js/plugin/Jcrop/js/jquery.Jcrop.min.js"></script>
		<script type="text/javascript" src="${appPath}/resources/js/common/jQuery.UtrialAvatarCutter.js"></script>
		<script type="text/javascript" src="${appPath}/resources/js/defined/jquery.upload.js"></script>
	 </head>
	
	<body>
		<div align="center" style="width: 100%;">
			<form id="vote_add_form" method="post" style="width: 100%">
				<!-- 原新闻图标路径 -->
		        <input type="hidden" name="picturePath" id="picturePath"/>
		        <!-- 新闻图标裁切的数据 -->
		        <input type="hidden" name="x" id="x"/>
		        <input type="hidden" name="y" id="y"/>
		        <input type="hidden" name="w" id="w"/>
		        <input type="hidden" name="h" id="h"/>
				<table class="tableForm" style="width: 100%;font-size: 12px;">
					<tr>
						<th>问卷标题</th>
						<td colspan="3">
							<input name="subject" style="width:550px;" class="easyui-validatebox" data-options="required:true,validType:'maxLength[100]'"/>
							<span style="color:red;margin-left:5px;">* 必填</span>
						</td>
					</tr>
					<tr>
						<th>问卷图标</th>
						<td>
							<!---缩略图-->
							<div id="preview">
						    	<img id="avatar" style="width: 60px; height: 60px;"
						    		src="${appPath}/avatar/avatar-small.png"
						    	 />&#160;<font color="#FF0000" size="2">(注意：选择图片不能超过200×200像素)</font>
					    	</div>
							<input name="picturefile"  id="picturefile"  type="hidden"  style="width: 200px;"/>
							<input type="button" onclick="doUpload()" value="浏览...">&nbsp;<input type="button" id="deleteBtn" style="display:none;" title="删除图标" onclick="deleteUpload()" value="X">
							<!--原始图-->
			                <div id="originalImgContainer" style="float:right;margin-right: 5px;">
			                	<!-- 文件上传列表 -->
			                	<div id="fileList" style="height:10px;"></div>
							</div>
						</td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<th>问卷说明</th>
						<td colspan="3">
							<textarea name="description" id="description"  rows="4" cols="58" style="width:666px;height:140px;visibility:hidden;"></textarea>
									<script type="text/javascript">
										KindEditor.ready(function(K) {
											var editor1 = K.create('textarea[name="description"]', {
												cssPath : '${appPath}/resources/js/plugin/kindeditor/plugins/code/prettify.css',
												uploadJson : '${appPath}/resources/js/plugin/kindeditor/jsp/upload_json.jsp',
												fileManagerJson : '${appPath}/resources/js/plugin/kindeditor/jsp/file_manager_json.jsp',
												allowFileManager : true,
												afterCreate : function() { 
      											   this.sync(); 
										        }, 
										        afterBlur:function(){ 
										            this.sync(); 
										        }      
											});
										});
									</script>
						</td>
					</tr>
					<tr>
						<th>结束语</th>
						<td colspan="3">
							<textarea name="enddescription" id="enddescription"  rows="4" cols="58" style="width:666px;height:140px;visibility:hidden;"></textarea>
									<script type="text/javascript">
										KindEditor.ready(function(K) {
											var editor1 = K.create('textarea[name="enddescription"]', {
												cssPath : '${appPath}/resources/js/plugin/kindeditor/plugins/code/prettify.css',
												uploadJson : '${appPath}/resources/js/plugin/kindeditor/jsp/upload_json.jsp',
												fileManagerJson : '${appPath}/resources/js/plugin/kindeditor/jsp/file_manager_json.jsp',
												allowFileManager : true,
												afterCreate : function() { 
      											   this.sync(); 
										        }, 
										        afterBlur:function(){ 
										            this.sync(); 
										        }      
											});
										});
									</script>
						</td>
					</tr>
					<tr>
						<th>所属行业</th>
						<td>
							<div id="typeDiv">
								<input  id="industryid" name="industryid" class="easyui-combotree" data-options="url:'${appPath}/surveyIndustryManage/treegrid.action',parentField:'pid',lines:true,required:true " style="width:170px;" />
								<span style="color:red;margin-left:5px;">* 必填</span>
							</div>
						</td>
						<th>问卷类别</th>
						<td>
							<div id="typeDiv">
								<input  id="categoryid" name="categoryid" class="easyui-combobox" data-options="lines:true,required:true" style="width:170px;" />
								<span style="color:red;margin-left:5px;">* 必填</span>
							</div>
						</td>	
					</tr>
					<tr>
						<th>开始时间</th>
						<td>
							<input id="startDate" name="begintime" class="easyui-datebox" data-options="required:true, editable:false, onSelect:openEndDateCalendar" onFocus="WdatePicker({minDate:'%y-%M-{%d}'})"/>
							<span style="color:red;margin-left:5px;">* 必填</span>
						</td>
						<th>结束时间</th>
						<td>
							<input id="endDate" name="endtime" class="easyui-datebox" data-options="required:true, editable:false, validType:'dateGE[\'startDate\']', invalidMessage: '结束时间不能早于开始时间'"/>
							<span style="color:red;margin-left:5px;">* 必填</span>
						</td>
					</tr>
					<tr>
						<th>数量限制</th>
						<td>
							<input name="numlimit" style="width:149px;" class="easyui-numberbox" min="1"/>
							<span style="color:red;margin-left:5px;">不填表示无数量限制</span>
							<!--<span style="color:red;margin-left:5px;">不填表示无数量限制</span> -->
						</td>
						<th>奖励积分</th>
						<td>
							<input name="integral" style="width:150px;" class="easyui-numberbox" min="1"/>
							<span style="color:red;margin-left:5px;">不填表示不奖励积分</span>
						</td>	
					</tr>
					<tr>
						<th>问卷标签</th>
						<td colspan="3">
							<input name="tags" style="width:149px;" class="easyui-validatebox" data-options="validType:'maxLength[100]'"/>
							<span style="color:red;margin-left:5px;">多个标签请用空格隔开</span>
						</td>
					</tr>
					<tr>
						<td colspan="4" style="background-color: #F4F4F4;text-align: center;width:50%;">
							<a id="saveSurveyBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" title="保存当前问卷后跳转至问卷列表页面">保存</a>
							<a id="closeBtn" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" title="关闭当前页面">关闭</a>
						</td>
					</tr>
				</table>
			</form>
		</div>


		<script type="text/javascript">
			//图片裁切器
			var cutter = new jQuery.UtrialAvatarCutter({
				//主图片所在容器ID
				content : "originalImgContainer",
				//缩略图配置,ID:所在容器ID;width,height:缩略图大小
				purviews : [{id:"preview",width:60,height:60}],
				//选择器默认大小
				selector : {width:120,height:120}
			});
			
			function doUpload() {
				// 上传方法
				$.upload({
						// 上传地址
						url: '${appPath}/annex/uploadAvatar.action', 
						// 文件域名字
						fileName: 'picturefile', 
						// 其他表单数据
						params: {},
						// 上传完成后, 返回json, text
						dataType: 'json',
						// 上传之前回调,return true表示可继续上传
						onSend: function() {
								return true;
						},
						// 上传之后回调
						onComplate: function(data) {
							if(data[0].flag=='1'){
								alert(data[0].message);
							}else{
								var path = data[0].path;
								//alert(path);
								// 记录下原图路径
								$("#picturePath").val(path);
								// 原图重新加载
								cutter.reload("${appPath}/"+path);
								$("#preview").attr("src", "${appPath}/"+path);
								
								// 把图片框和删除按钮放出来
								$("#originalImgContainer").css("display", "");
								$("#deleteBtn").css("display", "");
							}
						}
				});
			}
		
			// 删除图标
			function deleteUpload() {
				// 记录下原图路径
				$("#picturePath").val("");
				$("#avatar").attr("src", "${appPath}/avatar/avatar-small.png");
				$("#originalImgContainer").css("display", "none");
				
				$("#deleteBtn").css("display", "none"); // 隐藏删除按钮
			}
		
			// 页面加载完后的初始化
			$(function(){
				// 加载问卷类别（我们返回的 json 格式不能直接使用，参考：http://www.lihuoqing.cn/code/848.html）
				var url = "${appPath}/surveyCategoryManage/datagrid.action";
				$.getJSON(url, function(json) {
					$('#categoryid').combobox({
						data : json.rows, // 实际上就是先从返回的 json 中取得一个属性值，这个属性值又是一个 json 对象，我们要用的是这个 json 的属性
						valueField:'id',
						textField:'categoryname'
					});
				});
				
				// 设置开始时间为当前日期
				var s = format_Date();
				//$("#startDate").val(s); // 这种只能给普通的那种文本框赋值，形如：<input type='text' name='dvalidbegin' id='dvalidbegin' style='width:230px'  class='intxt' readonly="readonly"  onFocus="WdatePicker({minDate:'%y-%M-{%d}'})"/>&#160;<font color="#FF0000" size="2">*必填项</font>
				$('#startDate').datebox('setValue', s); // 取值用 $('#ID').datebox('getValue')
				
				// *****************************************************
				// "保存"按钮的单击事件的处理
				// *****************************************************
				$('#saveSurveyBtn').bind('click', function(){
						var description =  $('#description').val();
						//if (KindEditor.instances[0].isEmpty()) {
						//	ZENG.msgbox.show('问卷说明不能为空', 1, 3000);
						//	return false;
						//} else 
						if (description.length > 200) {
							easyuiBox.show('问卷说明长度超过最大限制（200字）');
							return false;
						}
						
						var enddescription =  $('#enddescription').val();
						if (enddescription.length > 200) {
							easyuiBox.show('结束语长度超过最大限制（200字）');
							return false;
						}
						
						// 设置表单中图标的裁切信息
				    	var cutterData = cutter.submit();
						if(cutterData.x!=undefined){ //说明提交了剪切图片
							if(cutterData){
								if(cutterData.x>0){
									$("#x").val(cutterData.x);
									$("#y").val(cutterData.y);
									$("#w").val(cutterData.w);
									$("#h").val(cutterData.h);
								}else{
									$.messager.show({
									title:'提示',
									msg:'图片横坐标应大于零，请重新截取'
								});
									return false;
								}
							}
					   	}
						
						$('#vote_add_form').form('submit',{
							url : '${pageContext.request.contextPath}/surveyManage/saveSurvey.action',
							error : function(result){
								var r = $.parseJSON(result);
								easyuiBox.show(r.msg);
							},
							success : function(result){
								var r = $.parseJSON(result);
								if (r.status) {
									$.messager.show({
										title : '提示',
										msg : r.msg
									});
								
									setTimeout(function(){
										var surveyObj = r.data;
										var surveyId = surveyObj.id;
										var url = "${pageContext.request.contextPath}/surveyManage/toSurveyEdit.action?surveyId="+surveyId;
										var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
										parent.layout_center_closeTab('编辑问卷'); // 先关闭再打开，否则如果之前有别的问卷处于编辑状态，就不会刷新了
										parent.layout_center_addTabFun({title:'编辑问卷', content:content, closable:true});
										parent.layout_center_refreshTab('问卷列表');
										// 然后关闭添加页面
										parent.layout_center_closeTab('添加问卷');
										
										/**
										easyuiBox.show(r.msg);
										// 关闭当前标签页，并刷新问卷列表页
										parent.layout_center_refreshTab('问卷列表'); // 必选先刷新，否则关闭之后就不会刷新了
										parent.layout_center_closeTab('添加问卷');
										*/
									}, 2000);  // 等待两秒后，跳转至其编辑页面
								}else{
									easyuiBox.show(r.msg);
								}
							}
						});
				});
				
				// *****************************************************
				// "保存并继续"按钮的单击事件的处理
				// *****************************************************
				$('#saveSurveyContinueBtn').bind('click', function(){
						$('#vote_add_form').form('submit',{
							url : '${pageContext.request.contextPath}/surveyManage/saveSurvey.action',
							error : function(result){
								var r = $.parseJSON(result);
								easyuiBox.show(r.msg);
							},
							success : function(result){
								var r = $.parseJSON(result);
								if (r.status) {
									// 如果添加成功后,则先打开其编辑页面
									var surveyObj = r.data;
									var surveyId = surveyObj.id;
									var url = "${pageContext.request.contextPath}/surveyManage/toSurveyEdit.action?surveyId="+surveyId;
									var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
									parent.layout_center_addTabFun({title:'编辑问卷', content:content, closable:true});
									// 然后关闭添加页面
									parent.layout_center_closeTab('添加问卷');
									
								}else{
									easyuiBox.show(r.msg);
								}
							}
							
						});
				});
				
				// *****************************************************
				// "关闭"按钮的单击事件的处理
				// *****************************************************
				$('#closeBtn').click(function(){
					parent.layout_center_closeTab('添加问卷');
				});
				
				
			});// End Of 页面加载完后的初始化
			
			
			// 投票开始时间选中后，投票结束时间控件显示选中面板，并且设置其值为开始时间
			function openEndDateCalendar(date){
				$("#endDate").combo('showPanel');
				$('#endDate').datebox('setValue',date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate());
			}
		
		</script>

	</body>
</html>