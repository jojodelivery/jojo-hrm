<%@include file="header.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	$('.selectpicker').selectpicker();
	var count = 0;
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
		<h2 align="center" class="toptext">Apply Leave</h2>
	</div>
</div>

<div class="container">
	<div class="row">


		<form name="applyLeave" id="applyLeave" method="post" action="applyLeave"
			role="form">

			<fieldset>


				<div class="col-md-4">
					<label for="start-date">Select Start Date</label>
					<div class="input-group date" data-provide="datepicker">
						<input type="text" class="form-control" id="start-date" onchange="calculateDateDiff()"  required>
						<div class="input-group-addon">
							<span class="glyphicon glyphicon-th"></span>
						</div>
					</div>
				</div>

				<div class="col-md-4">
					<label for="end-date">Select End Date</label>
					<div class="input-group date" data-provide="datepicker">
						<input type="text" class="form-control" id="end-date"
							onchange="calculateDateDiff()" required>
						<div class="input-group-addon">
							<span class="glyphicon glyphicon-th"></span>
						</div>
					</div>
				</div>

				<div class="col-md-4">
					<label for="no-of-days">No of Days</label>
					<div class="form-group input-group">
						<span class="input-group-addon"><i class="fa fa-list-ol"></i></span>
						<input type="text" class="form-control" placeholder="No of Days"
							id="no-of-days" readonly>
					</div>
				</div>

				<div class="col-md-4">
					<label for="leave-reason">Leave Type</label>
					<div class="form-group input-group">
						<select class="form-control" name="leave-reason" id="leave-reason"
							required>
						</select>
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
		diffDays = 0;
		$("#no-of-days").val(diffDays);
		$select = $('#leave-reason');
		$select.html('');
		$select.append('<option value="Mandatory_Leave">Mandatory Holidays</option>');
		$select.append('<option value="Sick_Leave">Sick Leave</option>');

		$('.datepicker').datepicker({
			dateFormat : 'dd/MM/yyyy'
		});

	});

	function calculateDateDiff() {
		var startValue=$("#start-date").val();
		var endValue=$("#end-date").val();
		if(startValue==""||endValue==""){
			return false;
		}
		var startDate = Date.parse($("#start-date").val());
		var endDate = Date.parse($("#end-date").val());
		var timeDiff = endDate - startDate;
		diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));
		diffDays = diffDays + 1;
		$("#no-of-days").val(diffDays);
	}
	$('#applyLeave').submit(function(e) {
		var frm = $('#applyLeave');
		e.preventDefault();
		var data;
		data = {
			startDate : Date.parse($("#start-date").val()),
			endDate : Date.parse($("#end-date").val()),
			noOfDays : $("#no-of-days").val(),
			reason : $("#leave-reason").val(),
			employeeId : '<%=employeeId%>'
		}

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : frm.attr('method'),
			url : 'timecard/leave',
			dataType : 'text',
			data : JSON.stringify(data),
			beforeSend : function() {
				$("#loading").modal('show');
			},
			success : function(result) {
				if (result !=null) {
					$("#loading").modal('hide');
					alert('Leave Request has been raised.');
					$("#home").load("apply-leave");
				} 
			},
			error : function() {
				$("#loading").modal('hide');
				alert('Error! Please contact administrator');
			}
		});
	});
</script>
