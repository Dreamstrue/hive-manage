<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
 		<title>质讯通</title>
		<meta http-equiv=”Content-Type” content=”text/html; charset=UTF-8>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<!-- Bootstrap -->
		<link rel="stylesheet" type="text/css" href="<%=path%>/resources/bootstrap/css/bootstrap.min.css" media="screen">
		
		<style>
			body {
			  padding-top: 50px;
			}
		</style>

		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	    <!--[if lt IE 9]>
	      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
	    <![endif]-->
	</head>

	<body>
		<div class="navbar navbar-inverse navbar-fixed-top">
			<div class="container">
				<div class="navbar-header">
					<a class="navbar-brand" href="#">质讯通</a>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
		
		<div class="container">
			<div class="text-center target">
				 <h1 class="" style="font-family: Microsoft YaHei;">质讯通</h1>

				<p class="lead" contenteditable="false" style="text-indent: 2em; text-align: left; font-family: Microsoft YaHei; font-size:14px; ">质讯通是质宝通科技有限公司自行研发的一款APP手机应用软件，服务于政府、企业和广大民众。质讯通的设计理念是：全民参与质量建设和质量监督。</p>
			</div>
			<div class="col-md-12 text-center" contenteditable="false">
				<button class="btn btn-primary btn-lg" onclick="downloadApk(0)">Android 版下载</button>
				<!-- <button class="btn btn-primary btn-lg" onclick="downloadApk(1)">企业版下载</button> -->
			</div>
		</div>
		<!-- /.container -->		

		<script type="text/javascript" src="<%=path%>/resources/bootstrap/js/jquery-1.10.2.min.js"></script>
		<script type="text/javascript" src="<%=path%>/resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript">
			// 质讯通大众版下载地址			
			var urlQualityInfo = "${address}";
			// 质讯通企业版下载地址
			var urlQualityInfoEnt = "${entAddress}";
		
			/*
			* 下载质讯通
			*/
			function downloadApk(sign){
				
				if(sign == 0){
					window.location.href = urlQualityInfo;
				} else if(sign == 1){
					window.location.href = urlQualityInfoEnt;
				}
			}
		</script>
	</body>
</html>
