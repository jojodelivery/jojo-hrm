<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="header.jsp"%>
<!doctype html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang=""> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8" lang=""> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9" lang=""> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js" lang="">
<!--<![endif]-->

<head>
<link href="resources/images/favicon.ico" rel="shortcut icon"
	type="image/x-icon" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Jojo Delivery: Dashboard</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="apple-touch-icon" href="apple-touch-icon.png">
<style>
body {
	padding-top: 60px;
	padding-bottom: 20px;
}

.toptext {
	padding-bottom: 40px;
}
</style>
<link href="css/bootstrap-theme.min.css" rel="stylesheet" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.7.3/css/bootstrap-select.min.css">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.7.3/js/bootstrap-select.min.js"></script>
<script src="js/modernizr-2.8.3-respond-1.4.2.min.js"></script>
<script
	src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
<script src="js/bootstrap3-typeahead.js" /></script>
<script src="js/moment.js" /></script>
<link href="css/jquery.dataTables.css" rel="stylesheet" />
<script src="js/jquery.dataTables.js"></script>
<link rel="stylesheet"
	href="https://cdn.datatables.net/responsive/1.0.7/css/responsive.dataTables.min.css">
<script
	src="https://cdn.datatables.net/responsive/1.0.7/js/dataTables.responsive.min.js"></script>
<script src="js/jqBootstrapValidation.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.8.1/bootstrap-table.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.15.35/css/bootstrap-datetimepicker.min.css">
	
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>
<link href="css/custom.css" rel="stylesheet" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/clockpicker/0.0.7/bootstrap-clockpicker.min.css">
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/clockpicker/0.0.7/bootstrap-clockpicker.min.js"></script>
<script>
	$('.selectpicker').selectpicker();
</script>
<script type="text/javascript">
	$(document).ready(function() {
		
		$('#register-da').click(function() {
			$("#home").load("register-da");
		});
		
		$('#view-da').click(function() {
			$("#home").load("view-da");
		});
		
		$('#apply-leave').click(function() {
			$("#home").load("apply-leave");
		}); 
		
		$('#view-leaves').click(function() {
			$("#home").load("view-leaves");
		});
		
		$('#approve-leave').click(function() {
			$("#home").load("manager-pending-leaves");
		});
		
		$('#fill-timecard').click(function() {
			$("#home").load("fill-timecard");
		});
		
		$('#view-timecard').click(function() {
			$("#home").load("view-timecard");
		});
		
		$('#approve-timecard').click(function() {
			$("#home").load("manager-view-timecard");
		});
		
		$('#view-payslip').click(function() {
			$("#home").load("view-payslip");
		});
	})
</script>
<script>
	$(function() {
		$("input,select,textarea").not("[type=submit]").jqBootstrapValidation();
	});
</script>
</head>

<body>
	<!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Jojo Delivery</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">

				<ul class="nav navbar-nav navbar-right">
					<%if(employee.getRole().equals("ADMIN")){ %>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><i class="fa fa-user"></i>User<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="#" id="register-da"><i
									class="fa fa-fw fa-user"></i>Register DA</a></li>
							<li class="divider"></li>
							<li><a href="#" id="view-da"><i
									class="fa fa-fw fa-power-off"></i>View DA</a></li>
						</ul>
					</li>
					
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><i class="fa fa-user"></i>Report<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="#" id="registerordermenu"><i
									class="fa fa-fw fa-user"></i>Payment Report</a></li>

							<li class="divider"></li>
							<li><a href="#" id="hub-export-report"><i
									class="fa fa-fw fa-eye"></i>Provide Bonus</a></li>
							<li class="divider"></li>
							<li><a href="#" id="hub-create-ecom-packet"><i
									class="fa fa-fw fa-eye"></i>Download Payslip</a></li>
						</ul>
					</li>
					<%} %>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><i class="fa fa-user"></i>Leave<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="#" id="apply-leave"><i
									class="fa fa-fw fa-user"></i>Apply Leave</a></li>
							<li class="divider"></li>
							<li><a href="#" id="view-leaves"><i
									class="fa fa-fw fa-user"></i>View Leave</a></li>
							<li class="divider"></li>
							<li><a href="#" id="approve-leave"><i
									class="fa fa-fw fa-user"></i>Approve Leave</a></li>
						</ul>
					</li>

					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><i class="fa fa-user"></i>Timecard<b
							class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="#" id="fill-timecard"><i
									class="fa fa-fw fa-user"></i>Submit Timecard</a></li>
							<li class="divider"></li>
							<li><a href="#" id="view-timecard"><i
									class="fa fa-fw fa-user"></i>View Timecard</a></li>
							<li class="divider"></li>
							<li><a href="#" id="approve-timecard"><i
									class="fa fa-fw fa-user"></i>Approve Timecard</a></li>
							<li class="divider"></li>
							<li><a href="#" id="view-payslip"><i
									class="fa fa-fw fa-user"></i>View Payslip</a></li>
						</ul>
					</li>

					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"><i class="fa fa-user"></i> <%-- <%=hubTO.getHubName()%> --%>
							<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="logout"><i class="fa fa-fw fa-power-off"></i>
									Log Out</a></li>
						</ul>
					</li>

				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</nav>

	<div class="container">
		<div class="row">
			<div id="home"></div>
		</div>
	</div>


</body>

</html>