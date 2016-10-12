<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
<jsp:include page="../common/inc.jsp"></jsp:include>
<link rel="stylesheet" href="${appPath}/resources/css/info.css" type="text/css"></link>
<link rel="stylesheet" href="${appPath}/resources/js/plugin/Jcrop/css/jquery.Jcrop.min.css" type="text/css"></link>
<script type="text/javascript" src="${appPath}/resources/js/plugin/Jcrop/js/jquery.Jcrop.min.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/common/jQuery.UtrialAvatarCutter.js"></script>
<script type="text/javascript" src="${appPath}/resources/js/defined/jquery.upload.js"></script>
<script type="text/javascript">

		//图片裁切器
		var cutter = new jQuery.UtrialAvatarCutter({
			//主图片所在容器ID
			content : "originalImgContainer",
			//缩略图配置,ID:所在容器ID;width,height:缩略图大小
			purviews : [{id:"preview",width:120,height:120}],
			//选择器默认大小
			selector : {width:120,height:120}
		});
		
		$(function() {
			
		});
	
	function check(){
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
		$('#form').form('submit',{
			url:'${appPath}/prizeInfo/insert.action',
			success:function(result){
				var r = $.parseJSON(result);
				$.messager.show({
					title:'提示',
					msg:r.msg
				});
				if(r.status){
					setTimeout(function(){
						location.href='${appPath}/prizeInfo/manage.action';
					},1000);
				}
			}
		});
	}
	
	function reload(){
		$('#form input[class!="button"]').val('');
		KindEditor.instances[0].html('');
	}
	
	
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
					// 记录下原图路径
					$("#picturePath").val(path);
					// 原图重新加载
					cutter.reload("${appPath}/"+path);
					$("#preview").attr("src", "${appPath}/"+path);
				}
			}
	});
}
</script>
<div align="left" style="width:75%; height: 100%;">
<div id="div1" >当前位置：奖品管理&nbsp;&gt;&nbsp;新增</div>
<div id="div2" align="right"><a id="btn" href="${appPath}/prizeInfo/manage.action" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true">返回列表</a> </div>
<div align="left" >
	<table class="integrity_table" style="width: 100%">
		<tr>
			<td>
				<div>
					<form  id="form" method="post" enctype="multipart/form-data">
						<!-- 原新闻图标路径 -->
				        <input type="hidden" name="picturePath" id="picturePath"/>
				        <!-- 新闻图标裁切的数据 -->
				        <input type="hidden" name="x" id="x"/>
				        <input type="hidden" name="y" id="y"/>
				        <input type="hidden" name="w" id="w"/>
				        <input type="hidden" name="h" id="h"/>
						<table >
							<tr>
								<td class="tt">奖品名称：</td>
								<td><input name="prizeName"  id="prizeName"  class="easyui-validatebox" data-options="required:true" style="width: 680px;"/></td>
							</tr>
							<tr>
								<td class="tt">奖品类别：</td>
								<td>
									<input id="prizeCateId" name="prizeCateId" class="easyui-combotree" data-options="url:'${appPath}/prizeCate/allPrizeTree.action',valueField:'id',textField:'infoCateName',required:true " style="width:200px;" />
								</td>
							</tr>
							<tr>
								<td class="tt">提供方：</td>
								<td>
								    <input id="entityCategory"  name="entityCategory"  class="easyui-combotree" value="" data-options="url:'${appPath}/entityCategoryManage/treegrid.action',parentField:'pid',lines:true,required:true "   style="width:200px;"/>
								</td>
							</tr>
							<tr>
								<td class="tt">奖品图片：</td>
								<td>
									<!---缩略图-->
									<div id="preview">
								    	<img id="avatar" style="width: 120px; height: 120px;"
								    		src="${appPath}/avatar/avatar-small.png"
								    	 />&#160;<font color="#FF0000" size="2">(注意：请选择图片200×200像素)</font>
							    	</div>
									<input name="picturefile"  id="picturefile"  type="hidden"  style="width: 200px;"/>
									<input type="button" onclick="doUpload()" value="点击上传">
									<!--原始图-->
					                <div id="originalImgContainer" style="float:right;margin-right: 5px;">
					                	<!-- 文件上传列表 -->
					                	<div id="fileList" style="height:10px;"></div>
									</div>
								</td>
							</tr>
							<tr>
								<td class="tt">奖品数量：</td>
								<td><input name="prizeNum"  id="prizeNum" class="easyui-validatebox" data-options="required:true" style="width: 200px;"/></td>
							</tr>
							<tr>
								<td class="tt">兑换积分：</td>
								<td><input name="excIntegral"  id="excIntegral" class="easyui-validatebox" data-options="required:true" style="width: 200px;"/></td>
							</tr>
							<tr>
								<td class="tt">奖品有效期：</td>
								<td>
									<input type='text' name=validDate id='validDate' style='width:200px'  class='easyui-validatebox' data-options="required:true" readonly="readonly" onfocus="WdatePicker({minDate:'%y-%M-%d'})"  />
								</td>
							</tr>
							<tr>
								<td class="tt">奖品说明：</td>
								<td>
									<textarea name="prizeExplain" id="prizeExplain"  rows="8" cols="60" style="width:680px;height:240px;visibility:hidden;"></textarea>
									<script type="text/javascript">
										KindEditor.ready(function(K) {
											var editor1 = K.create('textarea[name="prizeExplain"]', {
												cssPath : '${appPath}/resources/js/plugin/kindeditor/plugins/code/prettify.css',
												uploadJson : '${appPath}/resources/js/plugin/kindeditor/jsp/upload_json_zxt.jsp',
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
						</table>
						<p align="right" style="padding-right: 70px;">
								<input class="button" type="button" value="提交" onclick="check()"/>
								<input class="button" type="button" value="重置" onclick="reload()" />
				   			</p>
					</form>
				</div>
			</td>
		</tr>
	</table>
</div>
</div>
