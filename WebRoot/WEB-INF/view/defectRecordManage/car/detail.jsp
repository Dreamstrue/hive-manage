<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="aa" class="easyui-accordion" data-options="" style="width:100%;height:300;">
    <div title="&nbsp;车辆信息&nbsp;" data-options="" style="overflow:auto;padding:10px;">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th style="width: 200px;">品牌 ：</th>
				<td>${vo.carbrandName}</td>
			</tr>
			<tr>
				<th style="width: 150px;">生产者：</th>
				<td>${vo.carproducername}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">年款</th>
				<td>${vo.carmodelyear}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">车型系列：</th>
				<td>
					${vo.carseriesName}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">车型名称 ：</th>
				<td>${vo.carmodelForName}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">具体型号 ：</th>
				<td>${vo.carmodeldetailedName}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">VIN码/车架号：</th>
				<td>${vo.carVin}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">变速器类型：</th>
				<td>${vo.carTransmissionName}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">发动机排量[升(L)]：</th>
				<td>${vo.carDischarge}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">发动机号：</th>
				<td>${vo.carNumber}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">购买日期：</th>
				<td>${vo.buyDate}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">行驶里程[公里(KM)] ：</th>
				<td>${vo.runKilometers}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">购买店铺：</th>
				<td>${vo.buyshopName}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">牌照号码：</th>
				<td>${vo.plateNumber}
				</td>
			</tr>
			<tr>
				<th style="width: 200px;">国产/进口 ：</th>
				<td><c:if test="${vo.prodCountry=='1'}">国产</c:if><c:if test="${vo.prodCountry=='2'}">进口</c:if></td>
			</tr>
			<tr>
				<th style="width: 200px;">自主/合资/进口 ：</th>
				<td><c:if test="${vo.prodModel=='1'}">自主</c:if><c:if test="${vo.prodModel=='2'}">合资</c:if><c:if test="${vo.prodModel=='3'}">进口</c:if></td>
			</tr>
		</table>
    </div>
     <div title="&nbsp;缺陷描述信息&nbsp;" data-options="" style="overflow:auto;padding:10px;">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th style="width: 200px;">总成 ：</th>
				<td>${vo.assemblyName}
                </td>
			</tr>
			<tr>
				<th style="width: 200px;">分总成：</th>
				<td>${vo.subAssemblyName}
                </td>
			</tr>
			<tr>
				<th style="width: 150px;">所在三级分成 ：</th>
				<td>${vo.threeLevelAssemblyName}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">具体部位：</th>
				<td>${vo.specificPosition}
				</td>
			</tr>
			<tr>
				<th style="width: 200px;">是否原厂零部件 ：</th>
				<td><c:if test="${vo.isOriginalAccessories=='0'}">否</c:if><c:if test="${vo.isOriginalAccessories=='1'}">是</c:if></td>
			</tr>
			<tr>
				<th style="width: 150px;">缺陷描述：</th>
				<td>${vo.defectDescription}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">其他情况说明：</th>
				<td>${vo.otherDescription}
				</td>
			</tr>
			<c:if test="${vo.isOriginalAccessories=='0'}">
			<tr>
				<th style="width: 150px;">附件 ：</th>
				<td>附件：
				<img width=200 height=150 src='${pageContext.request.contextPath}/annex/picture.action?id="+${vo.mainId} +"'/>
				</td>
			</tr>
			</c:if>
			<tr>
				<th style="width: 200px;">是否发生交通事故 ：</th>
				<td><c:if test="${vo.isTrafficAccident=='0'}">否</c:if><c:if test="${vo.isTrafficAccident=='1'}">是</c:if></td>
			</tr>
			<tr>
				<th style="width: 200px;">是否造成人员伤亡 ：</th>
				<td><c:if test="${vo.isCasualtie=='0'}">否</c:if><c:if test="${vo.isCasualtie=='1'}">是</c:if></td>
			</tr>
		</table>
     </div>
    <div title="&nbsp;联系人信息&nbsp;" data-options="" style="overflow:auto;padding:10px;">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th style="width: 200px;">车主姓名 ：</th>
				<td>${vo.carownername}
                </td>
			</tr>
			<tr>
				<th style="width: 200px;">性别 ：</th>
				<td><c:if test="${vo.carownersex=='0'}">女</c:if><c:if test="${vo.carownersex=='1'}">男</c:if></td>
			</tr>
			<tr>
				<th style="width: 200px;">证件类别：</th>
				<td>${vo.certificatestypeName}
                </td>
			</tr>
			<tr>
				<th style="width: 150px;">证件号 ：</th>
				<td>${vo.certificatescode}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">联系人：</th>
				<td>${vo.contactsname}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">手机号码：</th>
				<td>${vo.contactsphone}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">电话：</th>
				<td>${vo.contactstel}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">电子信箱：</th>
				<td>${vo.contactsemail}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">所在省市：</th>
				<td>${vo.cprovincecodeName}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">所在地市：</th>
				<td>${vo.ccitycodeName}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">所在区县：</th>
				<td>${vo.cdistrictcodeName}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">邮政编码 ：</th>
				<td>${vo.postCode}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">联系地址 ：</th>
				<td>${vo.contactsadress}
				</td>
			</tr>
		</table>
     </div>
  </div>
</div>