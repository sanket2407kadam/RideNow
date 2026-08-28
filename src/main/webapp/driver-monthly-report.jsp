<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Monthly Driver Report</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4 bg-light">

<h3 class="fw-bold mb-4">📅 Monthly Driver Report</h3>

<div class="card p-3 shadow-sm">
<table class="table table-bordered table-hover">
<thead class="table-primary">
<tr>
  <th>Year</th>
  <th>Month</th>
  <th>Total Rides</th>
  <th>Total Earning (₹)</th>
</tr>
</thead>

<tbody>
<%
List<Map<String,Object>> report =
    (List<Map<String,Object>>) request.getAttribute("report");

if(report != null && !report.isEmpty()){
    for(Map<String,Object> r : report){
%>
<tr>
<td><%= r.get("year") %></td>
<td><%= r.get("month") %></td>
<td><%= r.get("rides") %></td>
<td>₹ <%= r.get("earning") %></td>
</tr>
<%
    }
}else{
%>
<tr>
<td colspan="4" class="text-center text-muted">
No monthly data available
</td>
</tr>
<%
}
%>
</tbody>
</table>
</div>

<a href="ManageDriversServlet" class="btn btn-secondary mt-3">
⬅ Back to Manage Drivers
</a>

</body>
</html>
