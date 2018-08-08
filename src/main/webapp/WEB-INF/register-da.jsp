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
		<h2 align="center" class="toptext">Register DA</h2>
	</div>
</div>

<div class="container">
	<div class="row">


		<form name="registerDA" id="registerDA" method="post" action="registerDA" autocomplete="off"
			role="form">

			<fieldset>

				<div class="col-md-4">
					<label for="empCode">Employee Code</label>
					<div class="form-group input-group">
						<span class="input-group-addon"><i class="fa fa-list-ol"></i></span>
						<input type="text" class="form-control" placeholder="Employee Code"
							id="empCode" required>
					</div>
				</div>
				
				<div class="col-md-4">
					<label for="empName">Employee Name</label>
					<div class="form-group input-group">
						<span class="input-group-addon"><i class="fa fa-list-ol"></i></span>
						<input type="text" class="form-control" placeholder="Employee Name"
							id="empName" required>
					</div>
				</div>
				
				<div class="col-md-4">
					<label for="mobile">Mobile</label>
					<div class="form-group input-group">
						<span class="input-group-addon"><i class="fa fa-list-ol"></i></span>
						<input type="text" class="form-control" placeholder="Mobile"
							id="mobile" required>
					</div>
				</div>
				
				<div class="col-md-4">
					<label for="empEmail">Employee Email</label>
					<div class="form-group input-group">
						<span class="input-group-addon"><i class="fa fa-list-ol"></i></span>
						<input type="text" class="form-control" placeholder="Employee Email"
							id="empEmail">
					</div>
				</div>
				
				<div class="col-md-4">
					<label for="empType">Employee Type</label>
					<div class="form-group input-group">
						<select class="form-control" name="empType" id="empType"
							required>
						</select>
					</div>
				</div>
				
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
						<select class="form-control" name="city" id="city" onchange="loadAvailableBand()"
							required>
						</select>
					</div>
				</div>
				
				<div class="col-md-4">
					<label for="shiftTiming">Shift Timing</label>
					<div class="form-group input-group">
						<select class="form-control" name="shiftTiming" id="shiftTiming"
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
					<label for="bandType">Band type</label>
					<div class="form-group input-group">
						<select class="form-control" name="bandType" id="bandType"
							required>
						</select>
					</div>
				</div>

				<div class="col-md-4">
					<label for="manager">Manager</label>
					<div class="form-group input-group">
						<select class="form-control" name="manager" id="manager"
							required>
						</select>
					</div>
				</div>
				
				<div class="col-md-4">
					<div class="checkbox" >
						<label> <input type="checkbox" id="managerAccess">Manager Access</label>
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

$(document).ready(function() {
	loadEmpType();
	loadStates();
	loadShifts();
	getManager();
});

function loadEmpType(){
	$select = $('#empType');
	$select.html('');
	$select.append('<option value="DA">DA</option>');
	$select.append('<option value="ADMIN">Admin</option>');
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
			loadAvailableBand();
		},
	});
}

function loadShifts(){
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		url: 'general/shifts',
		dataType:'JSON',
		data: {
		  },
    	type: "GET",
		success:function(result){
			$select = $('#shiftTiming');
			$select.html('');
			$.each(result, function(key, val) {
				$select.append('<option value="' +  val.shiftId + '">'
						+ val.shiftName +'- Start: '+val.startTime+'- End: '+val.endTime+ '</option>');
			});
		},
	});
}

function loadAvailableBand(){
	var cityId =$("#city").val();
	if(cityId=='' ||cityId ==null)
		return false;
	var url='general/band/'+ cityId;
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		url: url,
		dataType:'JSON',
		data: {
		  },
    	type: "GET",
		success:function(result){
			$select = $('#bandType');
			$select.html('');
			$.each(result, function(key, val) {
				$select.append('<option value="' +  val.bandId + '">'
						+ val.bandName +'</option>');
			});
			getStation();
		},
	});
}

function getManager(){
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		url: 'user/manager',
		dataType:'JSON',
		data: {
		  },
    	type: "GET",
		success:function(result){
			$select = $('#manager');
			$select.html('');
			$.each(result, function(key, val) {
				$select.append('<option value="' +  val.employeeId + '">'
						+ val.employeeCode+'- '+ val.name +'</option>');
			});
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
	$('#registerDA').submit(function(e) {
		var frm = $('#registerDA');
		e.preventDefault();
		var isManager = false;
		var managerAccess = $("#managerAccess").val();
		if(managerAccess =='on'){
			isManager = true;
		}
		var data;
		data = {
				employeeCode : $("#empCode").val(),
				name : $("#empName").val(),
				contactNo : $("#mobile").val(),
				email : $("#empEmail").val(),
				band : $("#bandType").val(),
				role : $("#empType").val(),
				state : $("#state").val(),
				city : $("#city").val(),
				managerAcess :isManager,
				managerId : $("#manager").val(),
				password : 'password',
				createdBy : '<%=employeeId%>',
				shiftId : $("#shiftTiming").val(),
				station : $("#station").val()
		}

		$.ajax({
			contentType : 'application/json; charset=utf-8',
			type : frm.attr('method'),
			url : 'user/employee',
			dataType : 'text',
			data : JSON.stringify(data),
			beforeSend : function() {
				$("#loading").modal('show');
			},
			success : function(result) {
				if (result !=null) {
					$("#loading").modal('hide');
					alert('DA has been created successfully.');
					$("#home").load("register-da");
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
