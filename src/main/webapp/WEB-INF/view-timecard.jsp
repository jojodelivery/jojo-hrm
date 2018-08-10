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
					<th>No of COD</th>
					<th>No of MPOS</th>
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
											"bFilter" : false,
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
														"mData" : "txnDate",
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
														"mData" : "noOfCodDelivered"
													},
													{
														"mData" : "noOfMposTxn"
													},
													{
														"mData" : "status"
													},
													{
														"mData" : "approveDate",
														"bSortable" : false,
														"mRender" : function(
																data, type,
																full) {
															if(data!=null){
															 var txnDate = new Date(data);
															 var month = txnDate.getMonth()+1;
															 return txnDate.getDate()+"-"+month+"-"+txnDate.getFullYear();
															}else{
																return "";
															}
														}
													},
													{
														"mData" : "id",
														"bSortable" : false,
														"mRender" : function(
																data, type,
																full) {
															var btn = '<div style="display: flex; width: auto;"><div style="float:left;" data-toggle="tooltip" title="Edit"  class="action-icon-style" onclick="editTimecard('
																	+ data
																	+ ')"><i class="fa fa-times"></i></div>';

															return "";
														}
													}

											]
										});

					});

	function editTimecard(requestId) {
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
