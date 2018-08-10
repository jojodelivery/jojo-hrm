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
		var url = 'timecard/leave/'+'<%=employeeId%>';
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
															var btn = '<div style="display: flex; width: auto;"><div style="float:left;" data-toggle="tooltip" title="Cancel Leave"  class="action-icon-style" onclick="cancelLeave('
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
