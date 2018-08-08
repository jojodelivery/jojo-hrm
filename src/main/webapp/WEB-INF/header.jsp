<%@page import="com.pin91.hrm.transferobject.EmployeeTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%
	EmployeeTO employee = (EmployeeTO) request.getSession().getAttribute(EmployeeTO.class.getName());
	if (employee == null) {
%>
<script>
	alert('Session Expired');
	window.location.href = "login";
</script>
<%
	return;
	}

	Long employeeId = employee.getEmployeeId();
%>
