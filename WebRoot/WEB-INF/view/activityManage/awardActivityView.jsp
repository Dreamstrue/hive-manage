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
		        },
		        {field:'prizeName',title:'奖品等级',width:150,align:'center',
		        	formatter:function(value){
		                return value;
		            }
			    },
		        {field:'awardName',title:'奖品名称',width:340,align:'center',
			        formatter:function(value,row,index){
			        	if(row.prizeInfoName!=null){
			                return row.prizeInfoName;
			        	}else{
			        		return "";
			        	}
			        }
				},
		        {field:'prizeCount',title:'奖品数量',width:100,align:'center',
		         	formatter:function(value){
			                return value;
		            }
			    },
			    {field:'remainCount',title:'剩余数量',width:100,align:'center',
		         	formatter:function(value){
			                return value;
		            }
			    },
		        {field:'prizeProbability',title:'中奖概率',width:100,align:'center',
		        	formatter:function(value){
			                return value;
		            }
		        }
		    ]]
		});
    });
    </script>
</head>
<body>
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
			            <table cellpadding="5">
			                <tr>
			                    <td>活动编号:</td>
			                    <td colspan="5">${vo.orderNum }</td>
			                </tr>
			                <tr>
			                    <td>活动主题:</td>
			                    <td colspan="5">
			                    <input class="easyui-textbox" type="text" name="theme" value="${vo.theme }" readonly="readonly" data-options="required:true,width:'800px'"></input>
			                    </td>
			                </tr>
			                <tr>
			                    <td>活动类型:</td>
			                    <td>
									<input class="easyui-textbox" type="text" name="activityType" value="${vo.activityTypeStr }" readonly="readonly" data-options="required:true,width:'150px'"></input>
								</td>
			                    <td>活动开始时间:</td>
			                    <td>
			                    <input class="easyui-textbox" type="text" name="beginTime" value="${vo.beginTimeStr }" readonly="readonly" data-options="required:true,width:'150px'"></input>
								</td>
			                    <td>活动结束时间:</td>
			                    <td>
			                    <input class="easyui-textbox" type="text" name="endTime" value="${vo.endTimeStr }" readonly="readonly" data-options="required:true,width:'150px'"></input>
			                    </td>
			                </tr>
			                <tr>
			                    <td>活动参与次数:</td>
			                    <td>
			                    <input class="easyui-textbox" type="text" name="joinTimes" value="${vo.joinTimes }" readonly="readonly" data-options="required:true,width:'150px'"></input>
			                    </td>
			                    <td>参与时间间隔:</td>
			                    <td>
			                    <input class="easyui-textbox" type="text" name="joinTimesPeriod" value="${vo.joinTimesPeriod }" readonly="readonly" data-options="width:'150px'"></input> 天
			                    </td>
			                </tr>
			                <tr>
								<td>活动详情：</td>
								<td colspan="5">
									<textarea name="content" id="content" rows="8" cols="60" style="width:680px;height:240px;visibility:hidden;" readonly="readonly">${vo.content }</textarea>
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
										    editor1.readonly(true);      
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
		    </div>
        </div>
    </div>
    </div>
</body>
</html>