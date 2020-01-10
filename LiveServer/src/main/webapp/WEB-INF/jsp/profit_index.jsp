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
						<h3 class="mx-2">Various counting in a day</h3>
					</div>
					<div class="row col-md-12 my-2">
						<div class="row col-md-12">
							<div class="col-md-4 m-auto" style="height: 15vh; background-color:#9673d3; color: white">
								<h3 style="margin-top: 5px; font-weight: 400;">View counts:</h3>
								<div style="text-align: center; margin-top: 15px; font-weight: 400;"><i
										class="fa fa-cloud-upload" aria-hidden="true"></i>&nbsp;&nbsp;<p id="txt-view"
										style="display: inline-block">-999999</p>
								</div>
							</div>
							<div class="col-md-4 m-auto" style="height: 15vh; background-color:#00897b; color: white">
								<h3 style="margin-top: 5px; font-weight: 400;">Comment counts:</h3>
								<div style="text-align: center; margin-top: 20px; font-weight: 400;"><i
										class="fa fa-cloud-upload" aria-hidden="true"></i>&nbsp;&nbsp;<p
										id="txt-comment" style="display: inline-block">-999999</p>
								</div>
							</div>
						</div>
						<div class="row col-md-12" style="height: 10px;">
							<span style="opacity: 0;">a</span>
						</div>
						<div class="row col-md-12">
							<div class="col-md-4 m-auto" style="height: 15vh; background-color: #e53935; color: white">
								<h3 style="margin-top: 5px; font-weight: 400;">Like counts:</h3>
								<div style="text-align: center; margin-top: 15px; font-weight: 400;"><i
										class="fa fa-cloud-upload" aria-hidden="true"></i>&nbsp;&nbsp;<p id="txt-like"
										style="display: inline-block">-999999</p>
								</div>
							</div>
							<div class="col-md-4 m-auto" style="height: 15vh; background-color: #ff9800; color: white">
								<h3 style="margin-top: 5px; font-weight: 400;">Report counts:</h3>
								<div style="text-align: center; margin-top: 15px; font-weight: 400;"><i
										class="fa fa-cloud-upload" aria-hidden="true"></i>&nbsp;&nbsp;<p id="txt-report"
										style="display: inline-block">-999999</p>
								</div>
							</div>
						</div>
					</div>

				</div>
				<div class="row  my-4 card">
					<div class="row col-md-12 my-2">
						<h3 class="mx-2">Profit</h3>
					</div>
					<div class="row col-md-12 my-2" style="padding: 0px 2rem;">
						<h1 id="current-profit"  class="mx-2 btn-success" style="padding: 10px;">$999999</h1>
						<button id="btnEarnProfit" onclick="navigateToPayForMe()" class="btn btn-success" style="margin-left: auto; margin-right: 2rem;">Pay for me</button>
					</div>
				</div>
				<div class="row  my-4">
					<form id="detailNovel" class="card col-md-12" data-startdateid="novelDate1"
						data-enddateid="novelDate2">
						<div class="row mt-3 ">
							<div class="row col-md-12 my-2">
								<h4 class="mx-2">Charting of views counting</h4>
							</div>
							<div class="row col-md-12">
								<div class="col-md-6">
									<span>From date(MM/dd/YYYY):</span> <input class="form-control" type="date"
										value="${startDate}" id="novelDate1">
								</div>
								<div class="col-md-6">
									<span>To date(MM/dd/YYYY):</span> <input class="form-control" type="date"
										value="${endDate}" id="novelDate2">
								</div>
								<div class="row col-md-12 mt-2" style="text-align: right;">
									<button id="btnSsNovel" type="button" class="btn btn-success"
										style="margin-left: auto;">Execute</button>
								</div>
							</div>
							<div class="row col-md-12">
								<div id="viewCountingChart" class="col-md-8"></div>
							</div>
						</div>
					</form>
				</div>
				<div class="row  my-4">
					<form id="detailChap" class="card col-md-12" data-startdateid="chapDate1"
						data-enddateid="chapDate2">
						<div class="row mt-3 ">
							<div class="row col-md-12 my-2">
								<h4 class="mx-2">Charting of comments counting</h4>
							</div>
							<div class="row col-md-12">
								<div class="col-md-6">
									<span>From date(MM/dd/YYYY):</span> <input class="form-control" type="date"
										value="${startDate}" id="chapDate1">
								</div>
								<div class="col-md-6">
									<span>To date(MM/dd/YYYY):</span> <input class="form-control" type="date"
										value="${endDate}" id="chapDate2">
								</div>
								<div class="row col-md-12 mt-2" style="text-align: right;">
									<button id="btnSsChap" type="button" class="btn btn-success"
										style="margin-left: auto;">Execute</button>
								</div>
							</div>
							<div class="row col-md-12">
								<div id="commentCountingChart" class="col-md-8"></div>
							</div>
						</div>
					</form>
				</div>
				<div class="row  my-4">
					<form id="detailAccount" class="card col-md-12" data-startdateid="accountDate1"
						data-enddateid="accountDate2">
						<div class="row mt-3 ">
							<div class="row col-md-12 my-2">
								<h4 class="mx-2">Charting of likes counting</h4>
							</div>
							<div class="row col-md-12">
								<div class="col-md-6">
									<span>From date(MM/dd/YYYY):</span> <input class="form-control" type="date"
										value="${startDate}" id="accountDate1">
								</div>
								<div class="col-md-6">
									<span>To date(MM/dd/YYYY):</span> <input class="form-control" type="date"
										value="${endDate}" id="accountDate2">
								</div>
								<div class="row col-md-12 mt-2" style="text-align: right;">
									<button id="btnSsAccount" type="button" class="btn btn-success"
										style="margin-left: auto;">Execute</button>
								</div>
							</div>
							<div class="row col-md-12">
								<div id="likeCountingChart" class="col-md-8"></div>
							</div>
						</div>
					</form>
				</div>
				<div class="row  my-4">
					<form id="detailThread" class="card col-md-12" data-startdateid="threadDate1"
						data-enddateid="threadDate2">
						<div class="row mt-3">
							<div class="row col-md-12 my-2">
								<h4 class="mx-4">Charting of reports counting</h4>
							</div>
							<div class="row col-md-12">
								<div class="col-md-6">
									<span>From date(MM/dd/YYYY):</span> <input class="form-control" type="date"
										value="${startDate}" id="threadDate1">
								</div>
								<div class="col-md-6">
									<span>To date(MM/dd/YYYY):</span> <input class="form-control" type="date"
										value="${endDate}" id="threadDate2">
								</div>
								<div class="row col-md-12 mt-2" style="text-align: right;">
									<button id="btnSsThread" type="button" class="btn btn-success"
										style="margin-left: auto;">Execute</button>
								</div>
							</div>
							<div class="row col-md-12">
								<div id="reportCountingChart" class="col-md-8"></div>
							</div>
						</div>
					</form>
				</div>

			</div>
		</div>
		<!-- main content area end -->
		<!-- footer area start-->
		<footer>
			<div class="footer-area"></div>
		</footer>
		<!-- footer area end-->
	</div>
	<!-- page container area end -->
	<!-- jquery latest version -->
	<script
		src="${pageContext.request.contextPath}/statics/template/admin-dashboard/js/vendor/jquery-2.2.4.min.js"></script>
	<!-- bootstrap 4 js -->
	<script src="${pageContext.request.contextPath}/statics/template/admin-dashboard/js/popper.min.js"></script>
	<script src="${pageContext.request.contextPath}/statics/template/admin-dashboard/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/statics/template/admin-dashboard/js/owl.carousel.min.js"></script>
	<script src="${pageContext.request.contextPath}/statics/template/admin-dashboard/js/metisMenu.min.js"></script>
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
	<script
			  src="https://code.jquery.com/jquery-3.4.1.min.js"
			  integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
			  crossorigin="anonymous"></script>
	<script>
		function extractOnPairJSON(raw) {
			var finalDataForChart = []
			for (var i = 0; i < raw.length; i++) {
				var entry = [];
				entry.push(raw[i]['k'])
				entry.push(raw[i]['v'])
				finalDataForChart.push(entry)
			}
			return finalDataForChart;
		}
		function extractOnPairJSONDate(raw) {
			var finalDataForChart = []
			for (var i = 0; i < raw.length; i++) {
				var entry = [];
				entry.push(new Date(raw[i]['k']))
				entry.push(raw[i]['v'])
				finalDataForChart.push(entry)
			}
			return finalDataForChart;
		}
		function extractOnTrippleJSON(raw) {
			var finalDataForChart = []
			for (var i = 0; i < raw.length; i++) {
				var entry = [];
				entry.push(raw[i]['k'])
				entry.push(raw[i]['v1'])
				entry.push(raw[i]['v2'])
				finalDataForChart.push(entry)
			}
			return finalDataForChart;
		}
		function doAjaxAndRedrawChart(formId, chart) {
			document.body.style.cursor = 'wait'
			var form = document.getElementById(formId)
			var startDateInput = document.getElementById(form.getAttribute('data-startdateid'))
			var endDateInput = document.getElementById(form.getAttribute('data-enddateid'))
			var startDate = $('#'.concat(startDateInput.getAttribute('id'))).val()
			var endDate = $('#'.concat(endDateInput.getAttribute('id'))).val()

			$.ajax(
				{
					type: 'post',
					url: '${pageContext.request.contextPath}/manage/admin/statistics/system',
					data: {
						"startDate": startDate,
						"endDate": endDate,
						"action": formId
					},
					success: function (response) {
						var json = JSON.parse(response)
						var validStartDate = json.k;
						var validEndDate = json.v1;
						var arrayDataTable = extractOnPairJSONDate(json.v2)

						startDateInput.value = validStartDate;
						endDateInput.value = validEndDate;
						$('#'.concat(startDateInput.getAttribute('id'))).attr("value", validStartDate)
						$('#'.concat(endDateInput.getAttribute('id'))).attr("value", validEndDate)

						if (arrayDataTable.length > 0) {
							var oldDataTable = chart.getDataTable()
							oldDataTable.removeRows(0, oldDataTable.getNumberOfRows());
							oldDataTable.addRows(arrayDataTable)
							chart.setDataTable(oldDataTable)
							chart.draw()
						}
						else {
							alert('Khoảng thời gian bạn chọn không có dữ liệu để hiển thị, xin chọn khoảng thời gian khác!')
						}
						document.body.style.cursor = 'default'
					},
					error: function () {
						document.body.style.cursor = 'default'
						alert("Vui lòng kiểm tra kết nối mạng!");
					}
				}
			);
		}
		function navigateToPayForMe(){
			var token = document.getElementById("hidden-token").textContent
			var userId = document.getElementById("hidden-userId").textContent
			var hostStatic = '${requestScope["host-static"]}'
			var context = "${pageContext.request.contextPath}"
			var builtUrl = hostStatic.concat(context ? "/" + context  : "").concat("")
			.concat("/profit/").concat(userId).concat('/payment?token=').concat(token)
			window.location.assign(builtUrl)
		}
		function getSimpleCountOnThisDate(displayerId, action) {
			var token = document.getElementById("hidden-token").textContent
			var hostStatic = '${requestScope["host-static"]}'
			var context = "${pageContext.request.contextPath}"
			$.ajax({
			url: "/auth/profit/count/" + action,
			headers: {
				'Authorization' : "Bearer " +token
			},
            type:'GET',
            success: function (e){
				if(e.statusCode < 400){
					document.getElementById(displayerId).textContent = e.data
				}
			},
            error: function (e){
				console.log(e)
			}
        });
		}
		function loadProfit(displayerId){
			var token = document.getElementById("hidden-token").textContent
			var userId = document.getElementById("hidden-userId").textContent
			var hostStatic = '${requestScope["host-static"]}'
			var context = "${pageContext.request.contextPath}"
			$.ajax({
			url: "/auth/profit/" + userId + "/payment",
			headers: {
				'Authorization' : "Bearer " +token
			},
            type:'POST',
            success: function (e){
				if(e.statusCode < 400){
					document.getElementById(displayerId).textContent = "$".concat(e.data)
				}
			},
            error: function (e){
				console.log(e)
			}
        });
		}
		var detailNovelChart, detailChapChart, detailAccountChart, detailThreadChart
		document.addEventListener("DOMContentLoaded", function () {
			getSimpleCountOnThisDate("txt-view", "view");
			getSimpleCountOnThisDate("txt-like", "like");
			getSimpleCountOnThisDate("txt-report", "report");
			getSimpleCountOnThisDate("txt-comment", "comment");
			loadProfit('current-profit')

			document.getElementById('btnSsNovel').onclick = function () {
				doAjaxAndRedrawChart('detailNovel', detailNovelChart)
			}
			document.getElementById('btnSsChap').onclick = function () {
				doAjaxAndRedrawChart('detailChap', detailChapChart)
			}
			document.getElementById('btnSsAccount').onclick = function () {
				doAjaxAndRedrawChart('detailAccount', detailAccountChart)
			}
			document.getElementById('btnSsThread').onclick = function () {
				doAjaxAndRedrawChart('detailThread', detailThreadChart)
			}

			google.charts.load('current');

			google.charts.setOnLoadCallback(drawViewCountingChart);
			google.charts.setOnLoadCallback(drawCommentCountingChart);
			google.charts.setOnLoadCallback(drawLikeCountingChart);
			google.charts.setOnLoadCallback(drawReportCountingChart);

			function drawViewCountingChart() {

				var data = new google.visualization.DataTable();
				data.addColumn('date', 'Date');
				data.addColumn('number', 'Views');
				var arrayDateTable = extractOnPairJSONDate(${dataDetailNovelOverDays });
				data.addRows(arrayDateTable);
				var options = {
					hAxis: {
						title: 'Date'
					},
					vAxis: {
						title: 'Views'
					}
				};
				var charType = '';
				if (arrayDateTable.length <= 4)
					charType = 'ColumnChart'
				else
					charType = 'LineChart'



				detailNovelChart = new google.visualization.ChartWrapper({
					chartType: charType,
					dataTable: data,
					options: options,
					containerId: 'viewCountingChart'
				});
				detailNovelChart.draw();
			}


			function drawCommentCountingChart() {

				var data = new google.visualization.DataTable();
				data.addColumn('date', 'Date')
				data.addColumn('number', 'Comments')

				var arrayTableData = extractOnPairJSONDate(${ dataDetailChapOverDays })
				data.addRows(arrayTableData);

				var options = {
					hAxis: {
						title: 'Date'
					},
					vAxis: {
						title: 'Comments'
					}
				};
				var charType = '';
				if (arrayTableData.length <= 4)
					charType = 'ColumnChart'
				else
					charType = 'LineChart'
				detailChapChart = new google.visualization.ChartWrapper({
					chartType: charType,
					dataTable: data,
					options: options,
					containerId: 'commentCountingChart'
				});
				detailChapChart.draw();
			}
			function drawLikeCountingChart() {
				var data = new google.visualization.DataTable();
				data.addColumn('date', 'Date');
				data.addColumn('number', 'Likes');
				var arrayTableData = extractOnPairJSONDate(${ dataDetailAccountOverDays });
				data.addRows(arrayTableData);

				var options = {
					hAxis: {
						title: 'Date'
					},
					vAxis: {
						title: 'Likes'
					}
				};
				var charType = '';
				if (arrayTableData.length <= 4)
					charType = 'ColumnChart'
				else
					charType = 'LineChart'
				detailAccountChart = new google.visualization.ChartWrapper({
					chartType: charType,
					dataTable: data,
					options: options,
					containerId: 'likeCountingChart'
				});
				detailAccountChart.draw();
			}


			function drawReportCountingChart() {

				var data = new google.visualization.DataTable();
				data.addColumn('date', 'Date');
				data.addColumn('number', 'Reports');
				var arrayTableData = extractOnPairJSONDate(${ dataDetailThreadOverDays })
				data.addRows(arrayTableData)

				var options = {
					hAxis: {
						title: 'Date'
					},
					vAxis: {
						title: 'Reports'
					}
				};
				var charType = '';
				if (arrayTableData.length <= 4)
					charType = 'ColumnChart'
				else
					charType = 'LineChart'
				detailThreadChart = new google.visualization.ChartWrapper({
					chartType: charType,
					dataTable: data,
					options: options,
					containerId: 'reportCountingChart'
				});
				detailThreadChart.draw();
			}
		})
	</script>
</body>

</html>