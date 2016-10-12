<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
//选择级联对应旗下的产品
	function onccSelect(){
		var brandid = $('#brandid').combobox('getValue');
		var url = '${appPath}/defectCar/getCarBrandSeriesList.action?brandid='+brandid;
		$('#seriesid').combobox('reload',url);
	}
	//选择级联对应旗下名称
	function onserSelect(){
		var brandid = $('#brandid').combobox('getValue');
		var seriesid = $('#seriesid').combobox('getValue');
		var url = '${appPath}/defectCar/getCarBrandModelList.action?brandid='+brandid+'&seriesid='+seriesid;
		$('#modelid').combobox('reload',url);
	}
	
</script>

<div align="center" style="width: 100%">
	<form id="category_manage_add_form" method="post" style="width: 100%">
		<table class="tableForm" style="width: 100%;font-size: 13px;">
			<tr>
				<th>品牌名称</th>
				<td><input name="brandid" id="brandid" class="easyui-combobox" data-options="url:'${appPath}/defectCar/getCarBrandList.action',valueField:'id',textField:'brandname',required:true, onSelect:onccSelect"   /></td>
			</tr>
			<tr>
				<th>系列名称</th>
				<td><input name="seriesid" id="seriesid"  class="easyui-combobox" data-options="valueField:'id',textField:'seriesname',multiple:false,required:true,onSelect:onserSelect "/></td>
			</tr>
			<tr>
				<th>车型名称</th>
				<td><input name="modelid" id="modelid" class="easyui-combobox" data-options="valueField:'id',textField:'modelname',multiple:false,required:true" /></td>
			</tr>
			<tr>
				<th>具体名称</th>
				<td><input name="modeldetailname" value="${vo.modeldetailname }" class="easyui-validatebox" data-options="required:true" /></td>
			</tr>
			<tr>
				<th>排序值</th>
				<td >
					<input name="ordernum"  value="1"  class="easyui-numberspinner" data-options="min:1,max:999,required:true"/>
				</td>
			</tr>
		</table>
	</form>
</div>