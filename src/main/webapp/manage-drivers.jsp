<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>RideNow | Manage Drivers</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

<style>
body{
  background:#f4f6f9;
  font-family:'Segoe UI',Tahoma,sans-serif;
}
.sidebar{
  width:260px;
  min-height:100vh;
  background:#111827;
  color:#fff;
  position:fixed;
}
.sidebar h4{
  padding:20px;
  text-align:center;
  background:#dc2626;
  margin:0;
  font-weight:700;
}
.sidebar a{
  display:block;
  padding:14px 20px;
  color:#cbd5e1;
  text-decoration:none;
}
.sidebar a:hover,
.sidebar a.active{
  background:#1f2933;
  color:#fff;
}
.main{
  margin-left:260px;
  padding:25px;
}
.card{
  border-radius:14px;
  box-shadow:0 10px 25px rgba(0,0,0,.1);
}
.badge-available{background:#16a34a;}
.badge-busy{background:#dc2626;}
</style>
</head>

<body>

<!-- ===== SIDEBAR ===== -->
<div class="sidebar">
  <h4>🛠 Admin Panel</h4>
  <a href="Admin-Dashboard">Dashboard</a>
  <a href="ManageUsersServlet">Manage Users</a>
  <a href="ManageDriversServlet" class="active">Manage Drivers</a>
  <a href="add-driver.jsp">Add Driver</a>
  <a href="ManageRidesServlet">Manage Rides</a>
  <a href="ManagePaymentsServlet">Manage Payments</a>
  <a href="AdminLogoutServlet" class="text-danger">Logout</a>
</div>

<!-- ===== MAIN ===== -->
<div class="main">

<h3 class="fw-bold mb-4">🚖 Manage Drivers</h3>

<!-- 🔍 SEARCH -->
<div class="row mb-3">
  <div class="col-md-4">
    <input type="text" id="searchInput" class="form-control"
           placeholder="Search driver name / vehicle">
  </div>
</div>

<div class="card p-3">
<table class="table table-hover" id="driverTable">
<thead class="table-primary">
<tr>
  <th>ID</th>
  <th>Name</th>
  <th>Vehicle No</th>
  <th>Vehicle Type</th>
  <th>Status</th>
  <th>Actions</th>
</tr>
</thead>

<tbody>
<%
List<Map<String,Object>> drivers =
    (List<Map<String,Object>>) request.getAttribute("drivers");

if(drivers != null && !drivers.isEmpty()){
    for(Map<String,Object> d : drivers){
        String status = (String) d.get("status");
        String badge =
            "available".equalsIgnoreCase(status)
            ? "badge-available" : "badge-busy";
%>
<tr>
<td><%= d.get("id") %></td>
<td><%= d.get("name") %></td>
<td><%= d.get("vehicleNo") %></td>
<td><%= d.get("type") %></td>
<td>
<span class="badge <%= badge %>"><%= status %></span>
</td>
<td>
<!-- Toggle Status -->
<a href="UpdateDriverStatusServlet?id=<%=d.get("id")%>"
   class="btn btn-sm btn-warning" title="Toggle Status">
   <i class="bi bi-arrow-repeat"></i>
</a>

<!-- Delete Driver -->
<a href="DeleteDriverServlet?id=<%=d.get("id")%>"
   class="btn btn-sm btn-danger"
   onclick="return confirm('Delete this driver?')" title="Delete Driver">
   <i class="bi bi-trash"></i>
</a>

<!-- Driver-wise Monthly Report -->
<a href="DriverMonthlyReportServlet?driver_id=<%=d.get("id")%>"
   class="btn btn-sm btn-info" title="Monthly Report">
   <i class="bi bi-calendar-month"></i>
</a>
</td>
</tr>
<%
    }
}else{
%>
<tr>
<td colspan="6" class="text-center text-muted">
No drivers found
</td>
</tr>
<%
}
%>
</tbody>
</table>
</div>

</div>

<!-- 🔍 SEARCH SCRIPT -->
<script>
document.getElementById("searchInput").addEventListener("keyup", function () {
  let filter = this.value.toLowerCase();
  let rows = document.querySelectorAll("#driverTable tbody tr");

  rows.forEach(row => {
    row.style.display =
      row.textContent.toLowerCase().includes(filter)
      ? "" : "none";
  });
});
</script>

</body>
</html>
