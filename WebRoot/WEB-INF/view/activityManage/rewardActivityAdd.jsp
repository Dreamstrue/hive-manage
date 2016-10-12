<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>奖励活动</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/demo.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-easyui-1.4.5/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
    var editRow = -1; //定义全局变量：当前编辑的行,-1表示当前无编辑行
    $(function(){
    	$('#rewardTable').datagrid({
		    fit : true,
		    singleSelect:true,
		    idField:'id',
		    columns:[[
		        {field:'prizeNum',title:'奖品序号',width:80,align:'center',
		            formatter:function(value){
		                return value;
		            }
		        },
		        {field:'prizeName',title:'奖品名称',width:500,align:'center',
			        formatter:function(value,row,index){
			        	if(row.prizeName!=null){
			                return row.prizeName;
			        	}else{
			        		return "";
			        	}
			        },
			        editor:{
						type:'combobox',
							options:{
								required:true,
								url:'${pageContext.request.contextPath}/prizeInfo/findAllPrizeInfo.action',  
								valueField:'prizeName',
								textField:'prizeName',
								onSelect:function(rec){
									$('#prizeId').val(rec.id);
									$('#prizeName').val(rec.prizeName);
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
		        {field:'action',title:'操作',width:300,align:'center',
		            formatter:function(value,row,index){
		                if (row.editing){
		                    var s = '<a href="#" onclick="saverow(this)">保存</a> ';
// 		                    var c = '<a href="#" onclick="cancelrow(this)">Cancel</a>';
		                    return s;
		                } else {
		                    var e = '<a href="#" onclick="editReward(this)">修改</a> ';
		                    var d = '<a href="#" onclick="deleteReward(this)">删除</a>';
		                    return e+d;
		                }
		            }
		        }
		    ]],
		    onBeforeEdit:function(index,row){
		        row.editing = true;
		        $('#rewardTable').datagrid('refreshRow', index);
		    },
		    onAfterEdit:function(index,row){
		        row.editing = false;
		        $('#rewardTable').datagrid('refreshRow', index);
		    },
		    onCancelEdit:function(index,row){
		        row.editing = false;
		        $('#rewardTable').datagrid('refreshRow', index);
		    }
		});
    });
    //新增奖品
    function addReward(){
//     alert(editRow);
   		$("#awardId").val("");
   		$("#prizeId").val("");
		$("#prizeName").val("");
		$("#prizeNum").val("");
		$("#prizeCount").val("");
		$("#remainCount").val("");
    	var activityId = $("#activityId").val();
    	if(activityId==""){
    		$.messager.alert("提示","请先保存活动信息，点击‘保存信息’按钮！","info");
    		return false;
    	}
    	if(editRow!=-1){
    		$.messager.alert("提示","请先保存正在编辑的奖品信息！","info");
    		return false;
    	}
	    var data = $('#rewardTable').datagrid('getData');
	    var index = data.rows.length;
    	$('#rewardTable').datagrid('appendRow',{
			prizeNum: index+1
		});
		$('#rewardTable').datagrid('selectRow',index);
		$('#rewardTable').datagrid('beginEdit',index);
		editRow = index;
    
    }
    //保存新增奖品
    function saverow(target){
   		var data = $('#rewardTable').datagrid('getData');
   		var index = getRowIndex(target);
   		var row = data.rows[index];
   		
		var flag = $('#rewardTable').datagrid('validateRow', index);
		if(!flag){
			$.messager.alert("提示","请将奖品信息填写完整！","info");
			return false;
		}
		$('#rewardTable').datagrid('endEdit', index);
		editRow=-1;
		fillRewardForm(row);
		$('#rewardFrom').form('submit',{
		    success:function(data){
				var r = $.parseJSON(data);
					if (r.status) {
						var activityId = $("#activityId").val();
// 						$.messager.alert("提示",r.msg,"info");
						$('#rewardTable').datagrid({url:'${pageContext.request.contextPath}/activityManage/datagridForReward.action',    
							queryParams:{activityId:activityId}
						});
					}
		    }
		});
// 		datagridForReward
	}
    //修改奖品
    function editReward(target){
//     	alert(editRow);
	    //先判断当前是否有正在编辑状态的行
	    if(editRow!=-1){
	    	$.messager.alert("提示","请完成正在编辑的奖品！","info");
	    	return false;
	    }
	    //获取要编辑的行
	    var index = getRowIndex(target);
// 	    alert("要编辑的行数为："+index);
	    var rows_ = $('#rewardTable').datagrid('getRows');
	    var row = rows_[index];
// 	    alert("获取编辑行的id="+row.id);
	    //开启编辑
        $('#rewardTable').datagrid("beginEdit", index);
        var ed = $('#rewardTable').datagrid('getEditor', {index:index,field:'prizeName'});
		$(ed.target).combobox('setValue', row.prizeName);
		$('#prizeId').val(row.prizeId);
		$('#prizeName').val(row.prizeName);
        //把当前开启编辑的行赋值给全局变量editRow
        editRow = index;
    }
    //删除奖品
    function deleteReward(target){
    	//获取要删除的行
	    var index = getRowIndex(target);
// 	    alert("要删除的行数为："+index);
	    var rows_ = $('#rewardTable').datagrid('getRows');
	    var row = rows_[index];
// 	    alert("要删除的的id为："+row.id);
		var activityId = $("#activityId").val();
   		$.messager.confirm('提示', '您确定要删除此奖品吗？', function(r) {
		if (r) {
			$.post("${pageContext.request.contextPath}/activityManage/deleteReward.action?awardId="+row.id+"&activityId="+activityId,
				function(result){
					if (result.status) {
						$('#rewardTable').datagrid('reload');
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
		$('#rewardTable').datagrid('cancelEdit', getRowIndex(target));
	}
	function getRowIndex(target){
		var tr = $(target).closest('tr.datagrid-row');
		return parseInt(tr.attr('datagrid-row-index'));
	}
	function fillRewardForm(row){
// 	   		alert("prizeNum="+
// 			row.prizeNum+"#prizeName="+
// 			row.prizeName+"#prizeCount="+
// 			row.prizeCount+");
			$("#awardId").val(row.id);
			$("#prizeNum").val(row.prizeNum);
			$("#prizeName").val(row.prizeName);
			$("#prizeCount").val(row.prizeCount);
			$("#remainCount").val(row.prizeCount);
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
// 						alert("activityId："+r.data);
						$("#activityId").val(r.data.sonId);
						$.messager.alert("提示",r.msg,"info");
						$("#saveActivityInfo").hide();
					}
		    }
		});
    }
    //完成
    function doneIt(){
    	if(editRow!=-1){
    		$.messager.alert("提示","请保存正在编辑的奖品信息！","info");
    		return false;
    	}
	    parent.layout_center_closeTab("新增奖励活动");
    }
    </script>
</head>
<body>
    <h2>请按要求填写活动内容:</h2>
    <div style="margin:10px 0;"></div>
        <div style="padding:10px 60px 20px 60px">
        <div class="easyui-layout" style="width:100%;height:700px;">
        <div data-options="region:'center',title:'活动信息设置'" style="width:60%;">
            <div class="easyui-layout" style="width:100%;height:100%;">
		        <div data-options="region:'north'" style="height:21%">
	                <form id="activityForm" method="post">
	                	<input type="hidden" name = "orderNum" value="${orderNum }"/>
	                	<input type="hidden" name = "status" value="0"/>
	                	<input type="hidden" name = "isValid" value="1"/>
			            <table cellpadding="5">
			                <tr>
			                    <td>活动编号:</td>
			                    <td colspan="5">${orderNum }</td>
			                </tr>
			                <tr>
			                    <td>活动主题:</td>
			                    <td colspan="5"><input class="easyui-textbox" type="text" name="theme" data-options="required:true,width:'800px'"></input></td>
			                </tr>
			                <tr>
			                    <td>活动类型:</td>
			                    <td>
			                    	<select class="easyui-combobox" name="activityType" data-options="editable:false,required:true,width:'150px'">
			                    		<option value="2">奖励活动</option>
			                    	</select>
								</td>
			                    <td>活动开始时间:</td>
			                    <td><input class="easyui-datetimebox" type="text" name="beginTimeStr" data-options="editable:false,required:true,width:'150px'"></input></td>
			                    <td>活动结束时间:</td>
			                    <td><input class="easyui-datetimebox" type="text" name="endTimeStr" data-options="editable:false,required:true,width:'150px'"></input></td>
			                </tr>
			                <tr>
			                    <td>活动参与次数:</td>
			                    <td><input class="easyui-textbox" type="text" name="joinTimes" value="1" data-options="required:true,width:'150px'"></input></td>
			                    <td>参与时间间隔:</td>
			                    <td><input class="easyui-textbox" type="text" name="joinTimesPeriod" data-options="width:'150px'"></input> 天</td>
			                </tr>
			            </table>
			        </form>

		        </div>
		        <div data-options="region:'center',title:'活动奖品设置'">
			        <table id="rewardTable">
					</table>
		        </div>
		        <div data-options="region:'south',split:true" style="height:50px;">
      			    <div style="text-align:center;padding:10px 0 0 0;">
			            <a id="saveActivityInfo" href="javascript:void(0)" class="easyui-linkbutton" onclick="addOrEditActivityInfo()">保存信息</a>
			            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="addReward()">新增奖品</a>
			            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="doneIt()">完成</a>
			        </div>
		        </div>
		    </div>
        </div>
    </div>
       <form id="rewardFrom" action="${pageContext.request.contextPath}/activityManage/addOrEditReward.action" method="post">
       		<input type="hidden" id="awardId" name="id" />
       		<input type="hidden" id="activityId" name="rewardId" value=""/>
       		<input type="hidden" id="prizeId" name="prizeId" value=""/>
       		<input type="hidden" id="prizeName" name="prizeName" value=""/>
       		<input type="hidden" id="prizeNum" name="prizeNum" value=""/>
       		<input type="hidden" id="prizeCount" name="prizeCount" value=""/>
       		<input type="hidden" id="remainCount" name="remainCount" value=""/>
       </form>
    </div>
</body>
</html>