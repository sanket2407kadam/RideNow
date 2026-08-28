<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>RideNow | Add Driver</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
body{background:#f4f6f9;font-family:'Segoe UI',Tahoma,sans-serif;}
.sidebar{width:260px;min-height:100vh;background:#111827;color:#fff;position:fixed;}
.sidebar h4{padding:20px;text-align:center;background:#dc2626;}
.sidebar a{display:block;padding:14px 20px;color:#cbd5e1;text-decoration:none;}
.sidebar a:hover{background:#1f2933;color:#fff;}
.sidebar a.active{background:#1f2933;color:#fff;font-weight:600;}
.main{margin-left:260px;padding:25px;}
.card{border-radius:16px;box-shadow:0 10px 25px rgba(0,0,0,.1);}
.form-control{border-radius:10px;padding:12px;}
.btn-add{background:#16a34a;color:#fff;border-radius:10px;font-weight:600;}
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
  <i class="bi bi-headset"></i> website settings
</a>

  <a href="AdminLogoutServlet" class="text-danger">Logout</a>
</div>
<!-- MAIN -->
<div class="main">

<h3 class="fw-bold mb-4">➕ Add New Driver</h3>

<%
String msg = request.getParameter("msg");
if ("success".equals(msg)) {
%>
<div class="alert alert-success">Driver added successfully!</div>
<%
} else if ("exists".equals(msg)) {
%>
<div class="alert alert-warning">Driver already exists for this User ID.</div>
<%
} else if ("invalid".equals(msg)) {
%>
<div class="alert alert-danger">Invalid User ID or User is not a driver.</div>
<%
}
%>

<div class="card p-4">
  <form action="AddDriverServlet" method="post">

    <div class="mb-3">
      <label class="form-label">User ID (Driver Role)</label>
      <input type="number" name="userId" class="form-control" required>
    </div>

    <div class="mb-3">
      <label class="form-label">Vehicle Number</label>
      <input type="text" name="vehicleNo" class="form-control" required>
    </div>

    <div class="mb-3">
      <label class="form-label">Vehicle Type</label>
      <input type="text" name="vehicleType" class="form-control" required>
    </div>

    <div class="text-center">
      <button type="submit" class="btn btn-add">Add Driver</button>
    </div>

  </form>
</div>

</div>
</body>
</html>
