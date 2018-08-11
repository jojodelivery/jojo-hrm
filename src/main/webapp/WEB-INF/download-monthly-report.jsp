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
		<h2 align="center" class="toptext">Download Monthly Salary Report</h2>
	</div>
</div>

<div class="container">
	<div class="row">


		<form name="downloadMonthReport" id="downloadMonthReport" method="post" action="downloadMonthReport" autocomplete="off"
			role="form">

			<fieldset>

				<div class="col-md-4">
					<label for="state">State</label>
					<div class="form-group input-group">
						<select class="form-control" name="state" id="state" onchange="loadCity()"
							required>
						</select>
					</div>
				</div>
				
				<div class="col-md-4">
					<label for="city">City</label>
					<div class="form-group input-group">
						<select class="form-control" name="city" id="city" 
							required>
						</select>
					</div>
				</div>
				
				
				<div class="col-md-4">
					<label for="station">Station</label>
					<div class="form-group input-group">
						<select class="form-control" name="station" id="station"
							required>
						</select>
					</div>
				</div>
				
				<div class="col-md-4">
					<label for="txnMonth">Select Month</label>
					<div class="form-group input-group">
						<select class="form-control" name="txnMonth" id="txnMonth"
							required>
						</select>
					</div>
				</div>
				
				<div class="col-md-4">
					<label for="txnYear">Select Year</label>
					<div class="form-group input-group">
						<select class="form-control" name="txnYear" id="txnYear"
							required>
						</select>
					</div>
				</div>
				
				<div class="form-group" style="float: right;">
					<div class="col-lg-10">
						<input type="submit" class="btn btn-primary" value="Download" />
					</div>
				</div>
			</fieldset>
		</form>
	</div>
</div>

<script>

$(document).ready(function() {
	loadStates();
	loadMonth();
	loadYear();
});

function loadMonth(){
	$select = $('#txnMonth');
	$select.html('');
	$select.append('<option value="1">January</option>');
	$select.append('<option value="2">February</option>');
	$select.append('<option value="3">March</option>');
	$select.append('<option value="4">April</option>');
	$select.append('<option value="5">May</option>');
	$select.append('<option value="6">June</option>');
	$select.append('<option value="7">July</option>');
	$select.append('<option value="8">August</option>');
	$select.append('<option value="9">September</option>');
	$select.append('<option value="10">October</option>');
	$select.append('<option value="11">November</option>');
	$select.append('<option value="12">December</option>');
} 

function loadYear(){
	$select = $('#txnYear');
	$select.html('');
	$select.append('<option value="2018">2018</option>');
	$select.append('<option value="2019">2019</option>');
} 

function loadStates(){
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		url: 'general/state',
		dataType:'JSON',
		data: {
		  },
    	type: "GET",
		success:function(result){
			$select = $('#state');
			$select.html('');
			$.each(result, function(key, val) {
				$select.append('<option value="' +  val.stateId + '">'
						+ val.stateName + '</option>');
			});
			loadCity();
		},
	});
}
function loadCity(){
	var stateId =$("#state").val();
	if(stateId=='' ||stateId ==null)
		return false;
	var url='general/city/'+ stateId;
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		url: url,
		dataType:'JSON',
		data: {
		  },
    	type: "GET",
		success:function(result){
			$select = $('#city');
			$select.html('');
			$.each(result, function(key, val) {
				$select.append('<option value="' +  val.cityId + '">'
						+ val.cityName + '</option>');
			});
			getStation();
		},
	});
}


function getStation(){
	var url='general/station/'+ $("#city").val();
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		url: url,
		dataType:'JSON',
		data: {
		  },
    	type: "GET",
		success:function(result){
			$select = $('#station');
			$select.html('');
			$.each(result, function(key, val) {
				$select.append('<option value="' +  val.stationName + '">'
						+ val.stationName +'</option>');
			});
		},
	});
}
	$('#downloadMonthReport').submit(function(e) {
		var frm = $('#downloadMonthReport');
		e.preventDefault();
		
		var url = 'report/monthly/' + $("#station").val()+'/'+$("#txnMonth").val()+'/'+$("#txnYear").val();
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			url : url,
			type : "GET",
			data : {

			},
			success : function(data) {
				$("#loading").modal('hide');
				console.log(data);
			},
			error : function() {
				$("#loading").modal('hide');
				alert('Error! Please contact administrator');
			}
		});
	});
</script>
