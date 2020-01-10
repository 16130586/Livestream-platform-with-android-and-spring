<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html class="no-js" lang="en">

<head>
	<p id="hidden-token" style="display:none">${hiddenToken}</p>
	<p id="hidden-userId" style="display:none">${userId}</p>
	<meta charset="utf-8">
	<meta http-equiv="x-ua-compatible" content="ie=edge">
	<title>My Profit | Yu Starter</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="shortcut icon" type="image/png"
		href="${pageContext.request.contextPath}/statics/template/admin-dashboard/images/icon/favicon.ico">
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
		integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/statics/template/admin-dashboard/css/font-awesome.min.css">
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/statics/template/admin-dashboard/css/themify-icons.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/template/admin-dashboard/css/metisMenu.css">
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/statics/template/admin-dashboard/css/owl.carousel.min.css">
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/statics/template/admin-dashboard/css/slicknav.min.css">
	<!-- amcharts css -->
	<link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css" type="text/css"
		media="all" />
	<!-- Start datatable css -->
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.18/css/dataTables.bootstrap4.min.css">
	<link rel="stylesheet" type="text/css"
		href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.bootstrap.min.css">
	<link rel="stylesheet" type="text/css"
		href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.jqueryui.min.css">
	<!-- style css -->
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/statics/template/admin-dashboard/css/typography.css">
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/statics/template/admin-dashboard/css/default-css.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/template/admin-dashboard/css/styles.css">
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/statics/template/admin-dashboard/css/responsive.css">
	<!-- modernizr css -->

	<script
		src="${pageContext.request.contextPath}/statics/template/admin-dashboard/js/vendor/modernizr-2.8.3.min.js"></script>
</head>

<body>
	<!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
	<!-- preloader area start -->
	<div id="preloader">
		<div class="loader"></div>
	</div>
	<!-- preloader area end -->
	<!-- page container area start -->
	<div class="">
		<!-- sidebar menu area start -->
		<!-- main content area start -->
		<div class="main-content">
			<!-- header area start -->
			<!-- <div class="header-area">
				<div class="row align-items-center">
					<!-- nav and search button -->
			<!-- <div class="col-md-6 col-sm-8 clearfix">
						<div class="nav-btn pull-left">
							<span></span> <span></span> <span></span>
						</div>
					</div>
				</div>
			</div> -->
			<!-- header area end -->
			<!-- page title area start -->
			<div class="page-title-area">
				<div class="row align-items-center">
					<div class="col-sm-6">
						<div class="breadcrumbs-area clearfix">
							<h4 class="page-title pull-left">My profit</h4>
							<ul class="breadcrumbs pull-left">
							</ul>
						</div>
					</div>
				</div>
			</div>
			<!-- page title area end -->
			<div class="main-content-inner">
				<div class="row  my-4 card">
					<div class="row col-md-12 my-2">
						<h3 class="mx-2">Profit history</h3>
					</div>
					<div class="row col-md-12 my-2">
						<div class="row col-md-12" style="padding:2rem">
							<span>Pick time(MM/dd/YYYY):</span><input class="form-control" type="date"
								value="${requestedDate}" id="datePicker">
							<button class="btn btn-success"
								style="margin-top:1rem;margin-left: auto; margin-right: 2rem;"
								onclick="applyProfit()">Apply</button>
						</div>
						<div class="row col-md-12" style="height: 10px;">
							<span style="opacity: 0;">a</span>
						</div>
					</div>
					<div class="row  my-4 card">
						<div class="row col-md-12 my-2">
							<h3 class="mx-4" >Profit earned</h3>
						</div>
						<div class="row col-md-12">
							<span id="profitEarned" class="text-success" style="margin-left: 2rem;" >$999999</span>
							<button class="btn btn-info" style="margin-left: auto;margin-right: 3rem;">Payment</button>
						</div>
					</div>
				</div>
				<!-- main content area end -->
				<!-- footer area start-->
				<!-- footer area end-->
			</div>
			<!-- page container area end -->
			<!-- jquery latest version -->
			<script
				src="${pageContext.request.contextPath}/statics/template/admin-dashboard/js/vendor/jquery-2.2.4.min.js"></script>
			<!-- bootstrap 4 js -->
			<script src="${pageContext.request.contextPath}/statics/template/admin-dashboard/js/popper.min.js"></script>
			<script
				src="${pageContext.request.contextPath}/statics/template/admin-dashboard/js/bootstrap.min.js"></script>
			<script
				src="${pageContext.request.contextPath}/statics/template/admin-dashboard/js/owl.carousel.min.js"></script>
			<script
				src="${pageContext.request.contextPath}/statics/template/admin-dashboard/js/metisMenu.min.js"></script>
			<script
				src="${pageContext.request.contextPath}/statics/template/admin-dashboard/js/jquery.slimscroll.min.js"></script>
			<script
				src="${pageContext.request.contextPath}/statics/template/admin-dashboard/js/jquery.slicknav.min.js"></script>

			<!-- Start datatable js -->
			<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
			<script src="https://cdn.datatables.net/1.10.18/js/jquery.dataTables.min.js"></script>
			<script src="https://cdn.datatables.net/1.10.18/js/dataTables.bootstrap4.min.js"></script>
			<script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
			<script src="https://cdn.datatables.net/responsive/2.2.3/js/responsive.bootstrap.min.js"></script>
			<!-- others plugins -->
			<script src="${pageContext.request.contextPath}/statics/template/admin-dashboard/js/plugins.js"></script>
			<script src="${pageContext.request.contextPath}/statics/template/admin-dashboard/js/scripts.js"></script>

			<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
			<script src="https://code.jquery.com/jquery-3.4.1.min.js"
				integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
			<script>
				function applyProfit() {
					var token = document.getElementById("hidden-token").textContent
					var userId = document.getElementById("hidden-userId").textContent
					var hostStatic = '${requestScope["host-static"]}'
					var context = "${pageContext.request.contextPath}"
					var pickedDate = document.getElementById('datePicker').value
					var dateArr = pickedDate.split("-")
					if (dateArr && dateArr.length == 3) {
						var builtUrl = hostStatic.concat(context ? "/" + context : "").concat("")
							.concat("/profit/").concat(userId).concat('/payment?token=').concat(token).concat("&month=")
							.concat(dateArr[1]).concat("&year=").concat(dateArr[0])
						console.log(builtUrl)
						window.location.assign(builtUrl)
					}
				}
				function navigateToPayForMe() {
					var token = document.getElementById("hidden-token").textContent
					var userId = document.getElementById("hidden-userId").textContent
					var hostStatic = '${requestScope["host-static"]}'
					var context = "${pageContext.request.contextPath}"
					var builtUrl = hostStatic.concat(context ? "/" + context : "").concat("")
						.concat("/profit/").concat(userId).concat('/payment?token=').concat(token)
					window.location.assign(builtUrl)
				}

				function loadProfit(displayerId) {
					var token = document.getElementById("hidden-token").textContent
					var userId = document.getElementById("hidden-userId").textContent
					var hostStatic = '${requestScope["host-static"]}'
					var context = "${pageContext.request.contextPath}"
					$.ajax({
						url: "/auth/profit/" + userId + "/payment",
						headers: {
							'Authorization': "Bearer " + token
						},
						type: 'POST',
						success: function (e) {
							if (e.statusCode < 400) {
								document.getElementById(displayerId).textContent = "$".concat(e.data)
							}
						},
						error: function (e) {
							console.log(e)
						}
					});
				}

				document.addEventListener("DOMContentLoaded", function () {
				})
			</script>
</body>

</html>