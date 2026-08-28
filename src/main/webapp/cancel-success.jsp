<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Cancel Ride | Admin</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

<style>
body{background:#f4f6f9;font-family:'Segoe UI',Tahoma;}
.sidebar{
  width:260px;min-height:100vh;background:#111827;color:#fff;position:fixed;
}
.sidebar h4{
  padding:20px;text-align:center;background:#dc2626;margin:0;
}
.sidebar a{
  display:block;padding:14px 20px;color:#cbd5e1;text-decoration:none;
}
.sidebar a:hover{background:#1f2933;color:#fff;}
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
<h3 class="fw-bold mb-4 text-danger">⚠ Cancel Ride</h3>

<div class="card p-4">
<p class="fs-5">
Are you sure you want to <b class="text-danger">cancel this ride</b>?
</p>

<form action="CancelRideServlet" method="get">


<button type="submit" class="btn btn-danger">
<i class="bi bi-x-circle"></i> Yes, Cancel Ride
</button>

<a href="ManageRidesServlet" class="btn btn-secondary ms-2">
<i class="bi bi-arrow-left"></i> No, Go Back
</a>
</form>
</div>
</div>

</body>
</html>
