<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	$('.selectpicker').selectpicker();
	var elementIds = [];
	
</script>

<div id="loading" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Response</h4>
			</div>
			<div class="modal-body">Please wait....</div>
		</div>
	</div>
</div>

<div class="container">
	<div class="row">
		<h2 align="center" class="toptext">Submit Timecard</h2>
	</div>
</div>

<div class="container">
	<div class="row">


		<form name="saveTimecard" id="saveTimecard" method="post" action="saveTimecard" autocomplete="off"
			role="form">

			<fieldset>


				<div class="col-md-4">
					<label for="tnxDate">Select Date</label>
					<div class="input-group date" data-provide="datepicker">
						<input type="text" class="form-control" id="tnxDate" required>
						<div class="input-group-addon">
							<span class="glyphicon glyphicon-th"></span>
						</div>
					</div>
				</div>
				
				<div class="col-md-4">
					<label for="inTime">In Time</label>
					<div class="input-group clockpicker" data-autoclose="true">
						<input type="text" class="form-control" onchange="calculateTimeDiff()" id="inTime" required>
						<div class="input-group-addon">
							<span class="glyphicon glyphicon-time"></span>
						</div>
					</div>
				</div>
				
				<div class="col-md-4">
					<label for="outTime">Out Time</label>
					<div class="input-group clockpicker" data-autoclose="true">
						<input type="text" class="form-control" onchange="calculateTimeDiff()" id="outTime" required>
						<div class="input-group-addon">
							<span class="glyphicon glyphicon-time"></span>
						</div>
					</div>
				</div>

				<div class="col-md-4">
					<label for="hoursWorked">Hours worked</label>
					<div class="form-group input-group">
						<span class="input-group-addon"><i class="fa fa-list-ol"></i></span>
						<input type="text" class="form-control" placeholder="Hours Worked"
							id="hoursWorked" readonly>
					</div>
				</div>

				<div class="col-md-4">
					<label for="shipmentDelivered">No Of Shipment Delivered</label>
					<div class="form-group input-group">
						<span class="input-group-addon"><i class="fa fa-list-ol"></i></span>
						<input type="text" class="form-control" placeholder="No Of Shipment Delivered"
							id="shipmentDelivered" required>
					</div>
				</div>
				
				<div class="col-md-4">
					<label for="codDelivered">No Of COD Delivered</label>
					<div class="form-group input-group">
						<span class="input-group-addon"><i class="fa fa-list-ol"></i></span>
						<input type="text" class="form-control" placeholder="No Of COD Delivered"
							id="codDelivered" required>
					</div>
				</div>

				<div class="col-md-4">
					<label for="mposTransaction">No Of MPOS Transaction</label>
					<div class="form-group input-group">
						<span class="input-group-addon"><i class="fa fa-list-ol"></i></span>
						<input type="text" class="form-control" placeholder="No Of MPOS Transaction"
							id="mposTransaction" required>
					</div>
				</div>
				
				<div class="form-group" style="float: right;">
					<div class="col-lg-10">
						<input type="submit" class="btn btn-primary" value="Submit" />
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>

<script>
	var loadList;
	var diffDays;
	$(document).ready(function() {
		$('.clockpicker').clockpicker();

		$('.datepicker').datepicker({
			dateFormat : 'dd/MM/yyyy'
		});

		var today = new Date();
		var date = (today.getMonth()+1)+'/'+today.getDate()+'/'+today.getFullYear();
		$("#tnxDate").val(date);
	});

	function calculateTimeDiff() {
		var startValue=$("#inTime").val();
		var endValue=$("#outTime").val();
		if(startValue==""||endValue==""){
			return false;
		}
		var diff = ( new Date("1970-1-1 " + endValue) - new Date("1970-1-1 " + startValue));
		$("#hoursWorked").val(msToTime(diff));
	}
	
	function msToTime(duration) {
		
	    var milliseconds = minutes = parseInt((duration/(1000*60))%60)
	        , hours = parseInt((duration/(1000*60*60))%24);
	    hours = (hours < 10) ? "0" + hours : hours;
	    minutes = (minutes < 10) ? "0" + minutes : minutes;
	    return hours + ":" + minutes ;
	}
	
	$('#saveTimecard').submit(function(e) {
		var frm = $('#saveTimecard');
		e.preventDefault();
		var data;
		data = {
			txnDate : Date.parse($("#tnxDate").val()),
			inTime : $("#inTime").val(),
			outTime : $("#outTime").val(),
			hoursWorked : $("#hoursWorked").val(),
			noOfShipment : $("#shipmentDelivered").val(),
			noOfCodDelivered : $("#codDelivered").val(),
			noOfMposTxn : $("#mposTransaction").val(),
			employeeId : '<%=employeeId%>'
		}

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : frm.attr('method'),
			url : 'timecard/timecard',
			dataType : 'text',
			data : JSON.stringify(data),
			beforeSend : function() {
				$("#loading").modal('show');
			},
			success : function(result) {
				if (result !=null) {
					$("#loading").modal('hide');
					alert('Timecard has been submitted successfully.');
					$("#home").load("fill-timecard");
				} 
			},
			error : function(data) {
				var text = JSON.parse(data.responseText);
				$("#loading").modal('hide');
				alert(text.message);
			}
		});
	});
</script>
