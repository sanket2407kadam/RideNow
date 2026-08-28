<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Ride Details | Admin</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

<style>
body{background:#f4f6f9;font-family:'Segoe UI',Tahoma;}
.sidebar{
  width:260px;min-height:100vh;background:#111827;color:#fff;position:fixed;
}
.sidebar h4{
  padding:20px;text-align:center;background:#dc2626;margin:0;font-weight:700;
}
.sidebar a{
  display:block;padding:14px 20px;color:#cbd5e1;text-decoration:none;
}
.sidebar a:hover,.sidebar a.active{
  background:#1f2933;color:#fff;font-weight:600;
}
.main{margin-left:260px;padding:25px;}
.card{border-radius:16px;box-shadow:0 10px 25px rgba(0,0,0,.1);}
</style>
</head>

<body>

<!-- SIDEBAR -->
<div class="sidebar">
  <h4>🛠 Admin Panel</h4>
  <a href="Admin-Dashboard" class="active">Dashboard</a>
<a href="ManageUsersServlet" class="active">
  <i class="bi bi-headset"></i> Manage Users
</a>
   <a href="ManageDriversServlet" class="active">
    <i class="bi bi-car-front"></i> Manage Drivers
  </a>
 <a href="add-driver.jsp" class="active">
  <i class="bi bi-headset"></i> Add Driver
</a>
<a href="AssignRoleServlet" class="active">
  <i class="bi bi-headset"></i> Assign Role
</a> <a href="ManageRidesServlet" class="active">
  <i class="bi bi-headset"></i> Manage Rides
</a>
  <a href="ManagePaymentsServlet" class="active">
  <i class="bi bi-headset"></i> Manage payment
</a>
 <a href="SupportServlet" class="active">
  <i class="bi bi-headset"></i> Support Requests
</a>

 <a href="WebsiteSettingsServlet" class="active">
  <i class="bi bi-headset"></i> Website settings
</a>

  <a href="AdminLogoutServlet" class="text-danger">Logout</a>
</div>


<!-- MAIN -->
<div class="main">
<h3 class="fw-bold mb-4">🚖 Ride Details</h3>

<div class="card p-4">
<table class="table table-bordered align-middle">
<tr><th width="30%">Ride ID</th><td><%= request.getAttribute("rideId") %></td></tr>
<tr><th>Rider</th><td><%= request.getAttribute("rider") %></td></tr>
<tr><th>Driver</th><td><%= request.getAttribute("driver") %></td></tr>
<tr><th>Pickup Location</th><td><%= request.getAttribute("pickup") %></td></tr>
<tr><th>Drop Location</th><td><%= request.getAttribute("drop") %></td></tr>
<tr><th>Status</th><td><%= request.getAttribute("status") %></td></tr>
<tr><th>Fare</th><td>₹ <%= request.getAttribute("fare") %></td></tr>
<tr><th>Ride Time</th><td><%= request.getAttribute("time") %></td></tr>
</table>

<div class="mt-3">
<a href="ManageRidesServlet" class="btn btn-dark">
<i class="bi bi-arrow-left"></i> Back
</a>
</div>
</div>
</div>

</body>
</html>
