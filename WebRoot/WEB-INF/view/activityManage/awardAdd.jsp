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
    <script type="text/javascript">
    $(function(){
    	$('#awardTable').datagrid({
		    url:'datagrid_data.json',
		    fit : true,
		    singleSelect:true,
		    idField:'id',
		    columns:[[
		        {field:'prizeLevel',title:'奖品序号',width:80,align:'center',
		            formatter:function(value){
// 		                for(var i=0; i<products.length; i++){
// 		                    if (products[i].productid == value) return products[i].name;
// 		                }
		                return value;
		            },
		            editor:{
		                type:'combobox',
		                options:{
		                    valueField:'productid',
		                    textField:'name',
		                    data:"",
		                    required:true
		                }
		            }
		        },
		        {field:'prizeName',title:'奖品名称',width:250,align:'center',editor:{type:'numberbox',options:{precision:1}}},
		        {field:'prizeCount',title:'奖品数量',width:100,align:'center',editor:'numberbox'},
		        {field:'prizeProbability',title:'中奖概率',width:100,align:'center',editor:'text'},
		        {field:'status',title:'状态',width:100,align:'center',
		            editor:{
		                type:'checkbox',
		                options:{
		                    on: 'P',
		                    off: ''
		                }
		            }
		        },
		        {field:'action',title:'操作',width:150,align:'center',
		            formatter:function(value,row,index){
		                if (row.editing){
		                    var s = '<a href="#" onclick="saverow('+index+')">Save</a> ';
		                    var c = '<a href="#" onclick="cancelrow('+index+')">Cancel</a>';
		                    return s+c;
		                } else {
		                    var e = '<a href="#" onclick="editrow('+index+')">Edit</a> ';
		                    var d = '<a href="#" onclick="deleterow('+index+')">Delete</a>';
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
    
    
    </script>
</head>
<body>
    <h2>请按要求填写活动内容</h2>
    <p></p>
    <div style="margin:20px 0;"></div>
        <div style="padding:10px 60px 20px 60px">
        <div class="easyui-layout" style="width:100%;height:700px;">
        <div data-options="region:'east',split:true" title="效果图预览" style="width:40%;"></div>
        <div data-options="region:'center',title:'活动信息设置'" style="width:60%;">
            <div class="easyui-layout" style="width:100%;height:100%;">
		        <div data-options="region:'north'" style="height:27%">
	                <form id="ff" method="post">
			            <table cellpadding="5">
			                <tr>
			                    <td>活动编号:</td>
			                    <td colspan="5">2016060602</td>
			                </tr>
			                <tr>
			                    <td>活动主题:</td>
			                    <td colspan="5"><input class="easyui-textbox" type="text" name="name" data-options="required:true,width:'800px'"></input></td>
			                </tr>
			                <tr>
			                    <td>活动类型:</td>
			                    <td>
			                    	<select class="easyui-combobox" name="language" data-options="required:true,width:'150px'">
			                    		<option value="ar">抽奖活动</option>
			                    	</select>
								</td>
			                    <td>活动开始时间:</td>
			                    <td><input class="easyui-datetimebox" type="text" name="subject" data-options="required:true,width:'150px'"></input></td>
			                    <td>活动结束时间:</td>
			                    <td><input class="easyui-datetimebox" type="text" name="subject" data-options="required:true,width:'150px'"></input></td>
			                </tr>
			                <tr>
			                    <td>活动参与次数:</td>
			                    <td><input class="easyui-textbox" type="text" name="name" data-options="required:true,width:'150px'"></input></td>
			                    <td>参与时间间隔:</td>
			                    <td><input class="easyui-textbox" type="text" name="name" data-options="required:true,width:'150px'"></input></td>
			                </tr>
			            </table>
			        </form>
			        <div style="text-align:center;padding:5px">
			            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">保存信息</a>
			            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">新增奖品</a>
			            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">效果预览</a>
			            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">完成</a>
			        </div>
		        </div>
		        <div data-options="region:'center',title:'活动奖品设置'">
			        <table id="awardTable">
						<thead>
							<tr>
								<th field="itemid" width="100" editor="{type:'validatebox',options:{required:true}}">Item ID</th>
								<th field="productid" width="100" editor="text">Product ID</th>
								<th field="listprice" width="100" align="right" editor="{type:'numberbox',options:{precision:1}}">List Price</th>
								<th field="unitcost" width="100" align="right" editor="numberbox">Unit Cost</th>
								<th field="attr1" width="150" editor="text">Attribute</th>
							</tr>
						</thead>
					</table>
		        </div>
		    </div>
        </div>
    </div>
    </div>
    <script>
        function submitForm(){
            $('#ff').form('submit');
        }
        function clearForm(){
            $('#ff').form('clear');
        }
    </script>
</body>
</html>