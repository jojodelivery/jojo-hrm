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

<script>
$(document).ready(function () {
		var employeeId='<%=employeeId%>';
		var d = new Date();
		var month = d.getMonth()+1;
		var year = d.getFullYear();
		var url = 'timecard/timecard/'+employeeId+'/'+month+'/'+year;
						$('#viewLeaves')
								.dataTable(
										{
											"aaSorting" : [], // Prevent initial sorting
											"sAjaxSource" : url,
											"sServerMethod" : "GET",
											"bProcessing" : false,
											"bLengthChange" : false,
											"serverSide" : false,
											"iDisplayLength" : "45",
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
															var btn = '<div style="display: flex; width: auto;"><div style="float:left;" data-toggle="tooltip" title="Edit"  class="action-icon-style" onclick="cancelLeave('
																	+ data
																	+ ')"><i class="fa fa-times"></i></div>';

															return btn;
														}
													}

											]
										});

					});

	function cancelLeave(requestId) {
		var url = 'timecard/leave/' + requestId
		$.ajax({
			contentType : 'application/json; charset=utf-8',
			url : url,
			type : "PUT",
			data : {

			},
			success : function(data) {
				alert('Leave Request has been cancel.');
				$("#tModal").modal("show");
				$("#home").load("view-leaves");
			},
			error : function() {
				$("#loading").modal('hide');
				alert('Error! Please contact administrator');
			}
		});
	}
</script>
