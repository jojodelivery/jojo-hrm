<%@include file="header.jsp"%>

<script>
		$(document).ready(function(){
		    $('[data-toggle="tooltip"]').tooltip();
		});
		var requestId;
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
		<table id="viewLeaves" class="display responsive no-wrap"
			width="100%" border='1'>
			<thead>
				<tr>
					<th>Date</th>
					<th>In Time</th>
					<th>Out Time</th>
					<th>Hours Worked</th>
					<th>No of Shipment</th>
					<th>No of MPOS Transaction</th>
					<th>Status</th>
					<th>Approval Date</th>
					<th>Action</th>
				</tr>
			</thead>
		</table>

	</div>
</div>

<!-- Reject Timecard -->
<div id="dModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Reject Timecard</h4>
      </div>
      <div class="modal-body">
   			 <div class="col-md-12">
					<div class="form-group input-group">
						<input type="text" class="form-control" placeholder="Reason"
							id="reject-reason">
					</div>
			</div>
      </div>
       <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="btn-reject-action">Reject</button>
      </div>
    </div>

  </div>
</div>

<!-- Reject Timecard -->

<script>
$(document).ready(function () {
		var employeeId='<%=employeeId%>';
		var url = 'timecard/timecard/'+employeeId+'/REQUESTED';
						$('#viewLeaves')
								.dataTable(
										{
											"aaSorting" : [], // Prevent initial sorting
											"sAjaxSource" : url,
											"sServerMethod" : "GET",
											"bProcessing" : false,
											"bLengthChange" : false,
											"serverSide" : false,
											"iDisplayLength" : "15",
											"fnServerParams" : function(aoData) {
												aoData.push({
													"name" : "employeeId",
													"value" : employeeId
												});
											},
											"rowCallback" : function(row, data) {
												
											},
											"aoColumns" : [
												{
													"mData" : "txnDate"
												},
												{
													"mData" : "inTime"
												},
												{
													"mData" : "outTime"
												},
												{
													"mData" : "hoursWorked"
												},
												{
													"mData" : "noOfShipment"
												},
												{
													"mData" : "noOfMposTxn"
												},
												{
													"mData" : "status"
												},
													{
														"mData" : "approveDate"
													},
													{
														"mData" : "id",
														"bSortable" : false,
														"mRender" : function(
																data, type,
																full) {
															var btn = '<div style="display: flex; width: auto;"><div style="float:left;" data-toggle="tooltip" title="Approve"  class="action-icon-style" onclick="approveTimecard('
																+ data
																+ ')"><i class="fa fa-check"></i></div>';
															btn = btn
																+ '<div style="display: flex; width: auto;"><div style="float:left;" data-toggle="tooltip" title="Reject"  class="action-icon-style" onclick="rejectTimecard('
																+ data
																+ ')"><i class="fa fa-times"></i></div>';
														return btn;
														}
													}

											]
										});

					});

function approveTimecard(requestId){
	this.requestId=requestId;
	updateTimecard('APPROVED')
}
function rejectTimecard(requestId){
	this.requestId=requestId;
	$("#dModal").modal('show');
}
function updateTimecard(status,rejectReason) {
	var employeeId='<%=employeeId%>';
	var rejectReason = $("#reject-reason").val();
	var url = 'timecard/approve-timecard/' + requestId + '/' + employeeId+'/'+status+'/'+ rejectReason;
	$.ajax({
		contentType : 'application/json; charset=utf-8',
		url : url,
		type : "PUT",
		data : {

		},
		success : function(data) {
			alert('Timecard has been ' + status.toLowerCase() + ".");
			$("#tModal").modal("show");
			$("#home").load("manager-view-timecard");
		},
		error : function() {
			$("#loading").modal('hide');
			alert('Error! Please contact administrator');
		}
	});
}
$(document).ready(function() {
	$('#btn-assign-da-action').click(function(e) {
			updateTimecard('REJECTED');
		});
	});
</script>
