<%@include file="header.jsp"%>

<script>
		$(document).ready(function(){
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
		<table id="viewLeaves" class="display responsive no-wrap" width="100%"
			border='1'>
			<thead>
				<tr>
					<th>Start Date</th>
					<th>End Date</th>
					<th>No of Days</th>
					<th>Leave Type</th>
					<th>Status</th>
					<th>Approval Date</th>
					<th>Action</th>
				</tr>
			</thead>
		</table>

	</div>
</div>

<script>
$(document).ready(function () {
		var employeeId='<%=employeeId%>';
		var url = 'timecard/manager-view-leave/'+'<%=employeeId%>';
						$('#viewLeaves')
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
												aoData.push({
													"name" : "employeeId",
													"value" : employeeId
												});
											},
											"rowCallback" : function(row, data) {
											},
											"aoColumns" : [
													{
														"mData" : "startDate",
														"bSortable" : false,
														"mRender" : function(
																data, type,
																full) {
															 var txnDate = new Date(data);
															 var month = txnDate.getMonth()+1;
															 return txnDate.getDate()+"-"+month+"-"+txnDate.getFullYear();
												             
														}
													},
													{
														"mData" : "endDate",
														"bSortable" : false,
														"mRender" : function(
																data, type,
																full) {
															 var txnDate = new Date(data);
															 var month = txnDate.getMonth()+1;
															 return txnDate.getDate()+"-"+month+"-"+txnDate.getFullYear();
												             
														}
													},
													{
														"mData" : "noOfDays"
													},
													{
														"mData" : "reason"
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
															
															var btn = '<div style="display: flex; width: auto;"><div style="float:left;" data-toggle="tooltip" title="Approve Leave"  class="action-icon-style" onclick="approveLeave('
																+ data
																+ ')"><i class="fa fa-check"></i></div>';
															btn = btn
																+ '<div style="display: flex; width: auto;"><div style="float:left;" data-toggle="tooltip" title="Reject Leave"  class="action-icon-style" onclick="rejectLeave('
																+ data
																+ ')"><i class="fa fa-times"></i></div>';
														return btn;
														}
													}

											]
										});

					});

	function approveLeave(requestId){
		updateLeave(requestId, 'APPROVED')
	}
	function rejectLeave(requestId){
		updateLeave(requestId, 'REJECTED')
	}
	function updateLeave(requestId, status) {
		var url = 'timecard/leave/' + requestId + '/' + '<%=employeeId%>'+'/'+status;
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			url : url,
			type : "PUT",
			data : {

			},
			success : function(data) {
				alert('Leave Request has been ' + status.toLowerCase() + ".");
				$("#tModal").modal("show");
				$("#home").load("manager-pending-leaves");
			},
			error : function() {
				$("#loading").modal('hide');
				alert('Error! Please contact administrator');
			}
		});
	}
</script>
