<%@include file="header.jsp"%>

<script>
	$(document).ready(function() {
		$('[data-toggle="tooltip"]').tooltip();
	});
</script>

<div id="loading" class="modal fade" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Alert</h4>
			</div>
			<div class="modal-body">Please wait....</div>
		</div>
	</div>
</div>

<div class="container">
	<div class="row">
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
						<select class="form-control" name="city" id="city" onchange="loadEmpData()"
							required>
						</select>
					</div>
		</div>
				
		<table id="viewDA" class="display responsive no-wrap" width="100%"
			border='1'>
			<thead>
				<tr>
					<th>Employee Code</th>
					<th>Name</th>
					<th>Mobile</th>
					<th>Email</th>
					<th>Shift</th>
					<th>ManagerName</th>
					<th>Band</th>
					<th>Station</th>
					<th>Action</th>
				</tr>
			</thead>
		</table>

	</div>
</div>

<script>
	$(document).ready(function() {
		loadStates();
	});

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
				loadEmpData();
			},
		});
	}
	function loadEmpData() {
		var cityId =$("#city").val();
		if(cityId=='' ||cityId ==null)
			return false;
		var url ='user/employee/'+cityId;
		$('#viewDA')
				.dataTable(
						{
							"aaSorting" : [], // Prevent initial sorting
							"sAjaxSource" : url,
							"bFilter" : false,
							"sServerMethod" : "GET",
							"bProcessing" : false,
							"bLengthChange" : false,
							"serverSide" : false,
							"iDisplayLength" : "15",
							"fnServerParams" : function(aoData) {
								
							},
							"rowCallback" : function(row, data) {

							},
							"aoColumns" : [
									{
										"mData" : "employeeCode"
									},
									{
										"mData" : "name"
									},
									{
										"mData" : "contactNo"
									},
									{
										"mData" : "email"
									},
									{
										"mData" : "shiftName"
									},
									{
										"mData" : "managerName"
									},
									{
										"mData" : "bandName"
									},
									{
										"mData" : "station"
									},
									{
										"mData" : "employeeId",
										"bSortable" : false,
										"mRender" : function(data, type, full) {
											var btn = '<div style="display: flex; width: auto;"><div style="float:left;" data-toggle="tooltip" title="Remove DA"  class="action-icon-style" onclick="removeDA('
													+ data
													+ ')"><i class="fa fa-times"></i></div>';

											return btn;
										}
									}

							]
						});
	}
	function removeDA(requestId) {
		var url = 'user/employee/' + requestId
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			url : url,
			type : "PUT",
			data : {

			},
			success : function(data) {
				alert('User has been removed.');
				$("#loading").modal('hide');
				$("#home").load("viewDA");
			},
			error : function() {
				$("#loading").modal('hide');
				alert('Error! Please contact administrator');
			}
		});
	}
</script>
