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
		    url:'${pageContext.request.contextPath}/activityManage/datagridForReward.action?activityId=${rewardActivityId}',
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
			    }
		    ]]
		});
    });
    </script>
</head>
<body>
    <div style="margin:10px 0;"></div>
        <div style="padding:10px 60px 20px 60px">
        <div class="easyui-layout" style="width:100%;height:700px;">
        <div data-options="region:'center',title:'活动信息设置'" style="width:60%;">
            <div class="easyui-layout" style="width:100%;height:100%;">
		        <div data-options="region:'north'" style="height:21%">
	                <form id="activityForm" method="post">
			            <table cellpadding="5">
			                <tr>
			                    <td>活动编号:</td>
			                    <td colspan="5">${vo.orderNum }</td>
			                </tr>
			                <tr>
			                    <td>活动主题:</td>
			                    <td colspan="5"><input class="easyui-textbox" type="text" name="theme" value="${vo.theme }" readonly="readonly" data-options="required:true,width:'800px'"></input></td>
			                </tr>
			                <tr>
			                    <td>活动类型:</td>
			                    <td>
			                    	<select class="easyui-combobox" name="activityType"  value="${vo.activityType }" readonly="readonly" data-options="editable:false,required:true,width:'150px'">
			                    		<option value="2">奖励活动</option>
			                    	</select>
								</td>
			                    <td>活动开始时间:</td>
			                    <td><input class="easyui-datetimebox" type="text" name="beginTimeStr"  readonly="readonly" value="${vo.beginTimeStr }" data-options="editable:false,required:true,width:'150px'"></input></td>
			                    <td>活动结束时间:</td>
			                    <td><input class="easyui-datetimebox" type="text" name="endTimeStr"  readonly="readonly" value="${vo.endTimeStr }" data-options="editable:false,required:true,width:'150px'"></input></td>
			                </tr>
			                <tr>
			                    <td>活动参与次数:</td>
			                    <td><input class="easyui-textbox" type="text" name="joinTimes"  readonly="readonly" value="${vo.joinTimes }" data-options="required:true,width:'150px'"></input></td>
			                    <td>参与时间间隔:</td>
			                    <td><input class="easyui-textbox" type="text" name="joinTimesPeriod" readonly="readonly" value="${vo.joinTimesPeriod }" data-options="width:'150px'"></input> 天</td>
			                </tr>
			            </table>
			        </form>

		        </div>
		        <div data-options="region:'center',title:'活动奖品设置'">
			        <table id="rewardTable">
					</table>
		        </div>
		    </div>
        </div>
    </div>
    </div>
</body>
</html>