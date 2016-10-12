<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="aa" class="easyui-accordion" data-options="" style="width:100%;height:300;">
    <div title="&nbsp;缺陷描述信息&nbsp;" data-options="" style="overflow:auto;padding:10px;">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th style="width: 200px;">缺陷类别 ：</th>
				<td><c:if test="${vo.peporttype=='1'}">玩具产品缺陷</c:if><c:if test="${vo.peporttype=='3'}">其他消费品缺陷</c:if></td>
			</tr>
			<tr>
				<th style="width: 150px;">产品名称：</th>
				<td>${vo.prodName}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">生产国别 ：</th>
				<td><c:if test="${vo.prodCountry=='1'}">国内生产</c:if><c:if test="${vo.prodCountry=='2'}">国外生产</c:if>
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">产品商标：</th>
				<td>
					${vo.prodTrademark}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">型号 ：</th>
				<td>${vo.prodModel}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">类别 ：</th>
				<td>${vo.prodType}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">商店名称 ：</th>
				<td>${vo.buyshopName}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">商店地址：</th>
				<td>${vo.buyshopAdress}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">购买日期：</th>
				<td>${vo.buyDate}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">缺陷描述 ：</th>
				<td>${vo.defectDescription}
				</td>
			</tr>
		</table>
    </div>
    <div title="&nbsp;联系人信息&nbsp;" data-options="" style="overflow:auto;padding:10px;">
		<table class="tableForm" style="width: 100%">
			<tr>
				<th style="width: 200px;">姓名 ：</th>
				<td>${vo.reportusername}
                </td>
			</tr>
			<tr>
				<th style="width: 200px;">手机：</th>
				<td>${vo.reportuserphone}
                </td>
			</tr>
			<tr>
				<th style="width: 150px;">Email ：</th>
				<td>${vo.reportuseremail}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">使用者姓名：</th>
				<td>${vo.username}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">所在省份 ：</th>
				<td>
				<c:if test="${vo.cprovincecode=='p1'}">北京市</c:if>
				<c:if test="${vo.cprovincecode=='p2'}">天津市</c:if>
				<c:if test="${vo.cprovincecode=='p3'}">上海市</c:if>
				<c:if test="${vo.cprovincecode=='p4'}">重庆市</c:if>
				<c:if test="${vo.cprovincecode=='p5'}">河北省</c:if>
				<c:if test="${vo.cprovincecode=='p6'}">山西省</c:if>
				<c:if test="${vo.cprovincecode=='p7'}">台湾省</c:if>
				<c:if test="${vo.cprovincecode=='p8'}">辽宁省</c:if>
				<c:if test="${vo.cprovincecode=='p9'}">吉林省</c:if>
				<c:if test="${vo.cprovincecode=='p10'}">黑龙江省</c:if>
				<c:if test="${vo.cprovincecode=='p11'}">江苏省</c:if>
				<c:if test="${vo.cprovincecode=='p12'}">浙江省</c:if>
				<c:if test="${vo.cprovincecode=='p13'}">安徽省</c:if>
				<c:if test="${vo.cprovincecode=='p14'}">福建省</c:if>
				<c:if test="${vo.cprovincecode=='p15'}">江西省</c:if>
				<c:if test="${vo.cprovincecode=='p16'}">山东省</c:if>
				<c:if test="${vo.cprovincecode=='p17'}">河南省</c:if>
				<c:if test="${vo.cprovincecode=='p18'}">湖北省</c:if>
				<c:if test="${vo.cprovincecode=='p19'}">湖南省</c:if>
				<c:if test="${vo.cprovincecode=='p20'}">广东省</c:if>
				<c:if test="${vo.cprovincecode=='p21'}">甘肃省</c:if>
				<c:if test="${vo.cprovincecode=='p22'}">四川省</c:if>
				<c:if test="${vo.cprovincecode=='p23'}">贵州省</c:if>
				<c:if test="${vo.cprovincecode=='p24'}">海南省</c:if>
				<c:if test="${vo.cprovincecode=='p25'}">云南省</c:if>
				<c:if test="${vo.cprovincecode=='p26'}">青海省</c:if>
				<c:if test="${vo.cprovincecode=='p27'}">陕西省</c:if>
				<c:if test="${vo.cprovincecode=='p28'}">广西壮族自治区</c:if>
				<c:if test="${vo.cprovincecode=='p29'}">西藏自治区</c:if>
				<c:if test="${vo.cprovincecode=='p30'}">宁夏回族自治区</c:if>
				<c:if test="${vo.cprovincecode=='p31'}">新疆维吾尔自治区</c:if>
				<c:if test="${vo.cprovincecode=='p32'}">内蒙古自治区</c:if>
				<c:if test="${vo.cprovincecode=='p33'}">澳门特别行政区</c:if>
				<c:if test="${vo.cprovincecode=='p34'}">香港特别行政区</c:if>
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">邮政编码 ：</th>
				<td>${vo.postCode}
				</td>
			</tr>
			<tr>
				<th style="width: 150px;">地址 ：</th>
				<td>${vo.reportadress}
				</td>
			</tr>
		</table>
     </div>
  </div>
</div>