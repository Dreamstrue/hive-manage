<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>抽奖活动</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/demo.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
	<!-- textarea 中的富编辑 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/plugin/kindeditor/themes/default/default.css" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/plugin/kindeditor/plugins/code/prettify.css" />
	<script charset="utf-8" src="${pageContext.request.contextPath}/resources/js/plugin/kindeditor/kindeditor.js"></script>
	<script charset="utf-8" src="${pageContext.request.contextPath}/resources/js/plugin/kindeditor/lang/zh_CN.js"></script>
	<script charset="utf-8" src="${pageContext.request.contextPath}/resources/js/plugin/kindeditor/plugins/code/prettify.js"></script>
    <script type="text/javascript">
    var editRow = -1; //定义全局变量：当前编辑的行,-1表示当前无编辑行
    $(function(){
    	$('#awardTable').datagrid({
		    url:'${pageContext.request.contextPath}/activityManage/datagridForAward.action?awardActivityId=${awardActivityId}',
		    fit : true,
		    singleSelect:true,
		    idField:'id',
		    columns:[[
		        {field:'prizeLevel',title:'奖品序号',width:80,align:'center',
		            formatter:function(value){
		                return value;
		            }
// 		            ,
// 		            editor:{
// 		                type:'numberbox',
// 				        options:{required:true}
// 		            }
		        },
		        {field:'prizeName',title:'奖品等级',width:150,align:'center',
		        	formatter:function(value){
		                return value;
		            },
			        editor:{
				        type:'validatebox',
				        options:{required:true}
			        }
			    },
		        {field:'awardName',title:'奖品名称',width:340,align:'center',
			        formatter:function(value,row,index){
			        	if(row.prizeInfoName!=null){
			                return row.prizeInfoName;
			        	}else{
			        		return "";
			        	}
			        },
			        editor:{
						type:'combobox',
							options:{
								url:'${pageContext.request.contextPath}/prizeInfo/findAllPrizeInfo.action',  
								valueField:'prizeName',
								textField:'prizeName',
								onSelect:function(rec){
									$('#prizeId').val(rec.id);
									$('#prizeInfoName').val(rec.prizeName);
								}
// 								required:true
							}
	
					}
				},
		        {field:'prizeCount',title:'奖品数量',width:100,align:'center',
		         	formatter:function(value){
			                return value;
		            },
			        editor:{
			        	type:'numberbox',
				        options:{required:true}
			        }
			        
			    },
		        {field:'prizeProbability',title:'中奖概率',width:100,align:'center',
		        	formatter:function(value){
			                return value;
		            },
		        	editor:{
			        	type:'numberbox',
			        	options:{required:true}
		        	}
// 		        	editor:{type:'numberbox',options:{precision:2}}
		        },
		        {field:'action',title:'操作',width:150,align:'center',
		            formatter:function(value,row,index){
		                if (row.editing){
		                    var s = '<a href="#" onclick="saverow(this)">保存</a> ';
// 		                    var c = '<a href="#" onclick="cancelrow(this)">Cancel</a>';
		                    return s;
		                } else {
		                    var e = '<a href="#" onclick="editAward(this)">修改</a> ';
		                    var d = '<a href="#" onclick="deleteAward(this)">删除</a>';
		                    return e+d;
		                }
		            }
		        }
		    ]],
		    onBeforeEdit:function(index,row){
		        row.editing = true;
		        $('#awardTable').datagrid('refreshRow', index);
		    },
		    onAfterEdit:function(index,row){
		        row.editing = false;
		        $('#awardTable').datagrid('refreshRow', index);
		    },
		    onCancelEdit:function(index,row){
		        row.editing = false;
		        $('#awardTable').datagrid('refreshRow', index);
		    }
		});
    });
    //新增奖品
    function addAward(){
//     alert(editRow);
   		$("#awardId").val("");
   		$("#prizeId").val("");
   		$("#prizeInfoName").val("");
		$("#prizeLevel").val("");
		$("#prizeName").val("");
		$("#prizeCount").val("");
		$("#remainCount").val("");
		$("#prizeProbability").val("");
    	var activityId = $("#activityId").val();
    	if(activityId==""){
    		$.messager.alert("提示","请先保存活动信息，点击‘保存信息’按钮！","info");
    		return false;
    	}
    	if(editRow!=-1){
    		$.messager.alert("提示","请先保存正在编辑的奖品信息！","info");
    		return false;
    	}
	    var data = $('#awardTable').datagrid('getData');
	    var index = data.rows.length;
    	if(index==12){
    		$.messager.alert("提示","抱歉！目前最多只支持12个奖品！","info");
    		return false;
    	}
    	$('#awardTable').datagrid('appendRow',{
			prizeLevel: index+1,
			prizeName: '谢谢参与',
		});
		$('#awardTable').datagrid('selectRow',index);
		$('#awardTable').datagrid('beginEdit',index);
		editRow = index;
    
    }
    //保存新增奖品
    function saverow(target){
   		var data = $('#awardTable').datagrid('getData');
   		var index = getRowIndex(target);
   		var row = data.rows[index];
   		
		var flag = $('#awardTable').datagrid('validateRow', index);
		if(!flag){
			$.messager.alert("提示","请将奖品信息填写完整！","info");
			return false;
		}
		$('#awardTable').datagrid('endEdit', index);
		editRow=-1;
		fillAwardForm(row);
		$('#awardFrom').form('submit',{
		    success:function(data){
				var r = $.parseJSON(data);
					if (r.status) {
						var awardActivityId = $("#awardActivityId").val();
// 						$.messager.alert("提示",r.msg,"info");
						$('#awardTable').datagrid({url:'${pageContext.request.contextPath}/activityManage/datagridForAward.action',    
							queryParams:{awardActivityId:awardActivityId}
						});
					}
		    }
		});
// 		datagridForAward
	}
    //修改奖品
    function editAward(target){
//     	alert(editRow);
	    //先判断当前是否有正在编辑状态的行
	    if(editRow!=-1){
	    	$.messager.alert("提示","请完成正在编辑的奖品！","info");
	    	return false;
	    }
	    //获取要编辑的行
	    var index = getRowIndex(target);
// 	    alert("要编辑的行数为："+index);
	    var rows_ = $('#awardTable').datagrid('getRows');
	    var row = rows_[index];
// 	    alert("获取编辑行的id="+row.id);
	    //开启编辑
        $('#awardTable').datagrid("beginEdit", index);
        var ed = $('#awardTable').datagrid('getEditor', {index:index,field:'awardName'});
		$(ed.target).combobox('setValue', row.prizeInfoName);
		$('#prizeId').val(row.prizeId);
		$('#prizeInfoName').val(row.prizeInfoName);
        //把当前开启编辑的行赋值给全局变量editRow
        editRow = index;
    }
    //删除奖品
    function deleteAward(target){
    	//获取要删除的行
	    var index = getRowIndex(target);
// 	    alert("要删除的行数为："+index);
	    var rows_ = $('#awardTable').datagrid('getRows');
	    var row = rows_[index];
// 	    alert("要删除的的id为："+row.id);
		var awardActivityId = $("#awardActivityId").val();
   		$.messager.confirm('提示', '您确定要删除此奖品吗？', function(r) {
		if (r) {
			$.post("${pageContext.request.contextPath}/activityManage/deleteAward.action?awardId="+row.id+"&awardActivityId="+awardActivityId,
				function(result){
					if (result.status) {
						$('#awardTable').datagrid('reload');
					}
					$.messager.show({
						title : '提示',
						msg : result.msg
					});
				},'json');
			}
		});
    }
	function cancelrow(target){
		$('#awardTable').datagrid('cancelEdit', getRowIndex(target));
	}
	function getRowIndex(target){
		var tr = $(target).closest('tr.datagrid-row');
		return parseInt(tr.attr('datagrid-row-index'));
	}
	function fillAwardForm(row){
// 	   		alert("prizeLevel="+
// 			row.prizeLevel+"#prizeName="+
// 			row.prizeName+"#prizeCount="+
// 			row.prizeCount+"#prizeProbability="+
// 			row.prizeProbability);
			$("#awardId").val(row.id);
			$("#prizeLevel").val(row.prizeLevel);
			$("#prizeName").val(row.prizeName);
			$("#prizeCount").val(row.prizeCount);
			$("#remainCount").val(row.prizeCount);
			$("#prizeProbability").val(row.prizeProbability);
	}
	//提交抽奖活动信息
    function addOrEditActivityInfo(){
		$('#activityForm').form('submit',{
		    url:"${pageContext.request.contextPath}/activityManage/addOrEditActivityInfo.action",
		    onSubmit: function(){
				return $(this).form('validate');
		    },
		    success:function(data){
				var r = $.parseJSON(data);
					if (r.status) {
// 						alert("activityId："+r.data.id);
// 						$("#activityId").val(r.data.id);
// 						$("#awardActivityId").val(r.data.sonId);
						$.messager.alert("提示",r.msg,"info");
						$("#saveActivityInfo").hide();
					}
		    }
		});
    }
    //预览抽奖
    function preview(){
    	var data = $('#awardTable').datagrid('getData');
	    var index = data.rows.length;
	    var activityId = $("#activityId").val();
    	//校验是否保存抽奖活动信息
    	if(activityId==""||index==0){
    		$.messager.alert("提示","请保存活动信息并添加奖品！","info");
    		return false;
    	}
    	if(editRow!=-1){
    		$.messager.alert("提示","请保存奖品信息再进行预览！","info");
    		return false;
    	}
    	$('#previewId').attr("src","${pageContext.request.contextPath}/activityManage/toWheelSurf.action?activityId="+activityId);
    }
    //完成
    function doneIt(){
    	if(editRow!=-1){
    		$.messager.alert("提示","请保存正在编辑的奖品信息！","info");
    		return false;
    	}
    	//保存一下活动信息
		$('#activityForm').form('submit',{
		    url:"${pageContext.request.contextPath}/activityManage/addOrEditActivityInfo.action",
		    onSubmit: function(){
				return $(this).form('validate');
		    }
		});
    	setTimeout(function () { parent.layout_center_closeTab("修改活动信息"); }, 1000)
	    
    }
    </script>
</head>
<body>
    <h2>请按要求填写活动内容:</h2>
    <p>1.奖品数量请限制在6到12个之间的偶数；</p>
    <p>2.所有奖品的中奖概率和为100，请合理分配中奖概率；</p>
    <p>3.务必设置一个空奖，推荐使用“谢谢参与”；</p>
    <div style="margin:20px 0;"></div>
        <div style="padding:10px 60px 20px 60px">
        <div class="easyui-layout" style="width:100%;height:930px;">
        <div data-options="region:'east',split:true" title="效果图预览" style="width:40%;">
        <iframe id="previewId" width="100%" height="100%" frameborder=0 scrolling=no src="${pageContext.request.contextPath}/activityManage/toWheelSurf.action?activityId=${vo.id}"></iframe>
        </div>
        <div data-options="region:'center',title:'活动信息设置'" style="width:60%;">
            <div class="easyui-layout" style="width:100%;height:900px;">
		        <div data-options="region:'north'" style="height:400px">
	                <form id="activityForm" method="post">
	                	<input type="hidden" name = "id" value="${vo.id }"/>
	                	<input type="hidden" name = "orderNum" value="${vo.orderNum }"/>
	                	<input type="hidden" name = "status" value="${vo.status }"/>
	                	<input type="hidden" name = "isValid" value="1"/>
	                	<input type="hidden" name = "createTimeStr" value="${vo.createTime }"/>
	                	<input type="hidden" name = "oldContent" value="${vo.oldContent }"/>
			            <table cellpadding="5">
			                <tr>
			                    <td>活动编号:</td>
			                    <td colspan="5">${vo.orderNum }</td>
			                </tr>
			                <tr>
			                    <td>活动主题:</td>
			                    <td colspan="5"><input class="easyui-textbox" type="text" name="theme" value="${vo.theme }" data-options="required:true,width:'800px'"></input></td>
			                </tr>
			                <tr>
			                    <td>活动类型:</td>
			                    <td>
			                    	<select class="easyui-combobox" name="activityType"  value="${vo.activityType }" data-options="editable:false,required:true,width:'150px'">
			                    		<option value="1">抽奖活动</option>
			                    	</select>
								</td>
			                    <td>活动开始时间:</td>
			                    <td><input class="easyui-datetimebox" type="text" name="beginTimeStr"  value="${vo.beginTimeStr }" data-options="editable:false,required:true,width:'150px'"></input></td>
			                    <td>活动结束时间:</td>
			                    <td><input class="easyui-datetimebox" type="text" name="endTimeStr"  value="${vo.endTimeStr }" data-options="editable:false,required:true,width:'150px'"></input></td>
			                </tr>
			                <tr>
			                    <td>活动参与次数:</td>
			                    <td><input class="easyui-textbox" type="text" name="joinTimes"  value="${vo.joinTimes }" data-options="required:true,width:'150px'"></input></td>
			                    <td>参与时间间隔:</td>
			                    <td><input class="easyui-textbox" type="text" name="joinTimesPeriod" value="${vo.joinTimesPeriod }" data-options="width:'150px'"></input> 天</td>
			                </tr>
			                <tr>
								<td>活动详情：</td>
								<td colspan="5">
									<textarea name="content" id="content" rows="8" cols="60" style="width:680px;height:240px;visibility:hidden;">${vo.content }</textarea>
									<script type="text/javascript">
										KindEditor.ready(function(K) {
											var editor1 = K.create('textarea[name="content"]', {
												cssPath : '${pageContext.request.contextPath}/resources/js/plugin/kindeditor/plugins/code/prettify.css',
												uploadJson : '${pageContext.request.contextPath}/resources/js/plugin/kindeditor/jsp/upload_json_zxt.jsp',
												fileManagerJson : '${pageContext.request.contextPath}/resources/js/plugin/kindeditor/jsp/file_manager_json.jsp',
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
			        </form>

		        </div>
		        <div data-options="region:'center',title:'活动奖品设置'">
			        <table id="awardTable">
					</table>
		        </div>
		        <div data-options="region:'south',split:true" style="height:50px;">
      			    <div style="text-align:center;padding:10px 0 0 0;">
			            <a id="saveActivityInfo" href="javascript:void(0)" class="easyui-linkbutton" onclick="addOrEditActivityInfo()">保存信息</a>
			            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addAward()">新增奖品</a>
			            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="preview()">效果预览</a>
			            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="doneIt()">完成</a>
			        </div>
		        </div>
		    </div>
        </div>
    </div>
       <form id="awardFrom" action="${pageContext.request.contextPath}/activityManage/addOrEditAward.action" method="post">
       		<input type="hidden" id="awardId" name="id" />
       		<input type="hidden" id="awardActivityId" name="awardActivityId" value="${awardActivityId}"/>
       		<input type="hidden" id="activityId" name="activityId" value="${vo.id}"/>
       		<input type="hidden" id="prizeId" name="prizeId" value=""/>
       		<input type="hidden" id="prizeInfoName" name="prizeInfoName" value=""/>
       		<input type="hidden" id="prizeLevel" name="prizeLevel" value=""/>
       		<input type="hidden" id="prizeName" name="prizeName" value=""/>
       		<input type="hidden" id="prizeCount" name="prizeCount" value=""/>
       		<input type="hidden" id="remainCount" name="remainCount" value=""/>
       		<input type="hidden" id="prizeProbability" name="prizeProbability" value=""/>
       </form>
    </div>
</body>
</html>