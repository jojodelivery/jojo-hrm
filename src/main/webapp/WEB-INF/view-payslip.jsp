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
					<th>Month</th>
					<th>Year</th>
					<th>FileName</th>
					<th>Action</th>
				</tr>
			</thead>
		</table>

	</div>
</div>

<script>
$(document).ready(function () {
		var employeeId='<%=employeeId%>';
		var url = 'timecard/payslips/'+'<%=employeeId%>';
						$('#viewLeaves')
								.dataTable(
										{
											"aaSorting" : [], // Prevent initial sorting
											"bFilter" : false,
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
														"mData" : "txnMonth"
													},
													{
														"mData" : "txnYear"
													},
													{
														"mData" : "file"
													},
													{
														"mData" : "file",
														"bSortable" : false,
														"mRender" : function(
																data, type,
																full) {
															var btn = '<div style="display: flex; width: auto;"><div style="float:left;" data-toggle="tooltip" title="Download Payslip"  class="action-icon-style" onclick="download(\''
																	+ data
																	+ '\')"><i class="fa fa-download"></i></div>';

															return btn;
														}
													}

											]
										});

					});

	function download(fileName) {
		var url ='report/download/' + fileName+'/payslip';
		window.open(url, '_blank', 'location=yes,height=570,width=520,scrollbars=yes,status=yes');
	}
</script>
