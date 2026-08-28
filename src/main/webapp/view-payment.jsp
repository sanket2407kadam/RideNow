<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Payment Details | RideNow Admin</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
body{background:#f4f6f9;font-family:'Segoe UI',Tahoma,sans-serif;}
.sidebar{width:260px;min-height:100vh;background:#111827;color:#fff;position:fixed;}
.sidebar h4{padding:20px;text-align:center;background:#dc2626;margin:0;font-weight:700;}
.sidebar a{display:block;padding:14px 20px;color:#cbd5e1;text-decoration:none;font-size:15px;}
.sidebar a:hover,.sidebar a.active{background:#1f2933;color:#fff;font-weight:600;}
.main{margin-left:260px;padding:25px;}
.card{border-radius:16px;box-shadow:0 10px 25px rgba(0,0,0,.1);padding:20px;}
</style>
</head>
<body>

<div class="sidebar">
  <h4>🛠 Admin Panel</h4>
  <a href="Admin-Dashboard">Dashboard</a>
  <a href="ManagePaymentsServlet" class="active">Manage Payments</a>
  <a href="AdminLogoutServlet" class="text-danger">Logout</a>
</div>

<div class="main">
<div class="card">
<h3 class="mb-4">💳 Payment Details</h3>
<table class="table table-bordered">
<tr><th>Payment ID</th><td><%= request.getAttribute("paymentId") %></td></tr>
<tr><th>Ride ID</th><td><%= request.getAttribute("rideId") %></td></tr>
<tr><th>Rider</th><td><%= request.getAttribute("rider") %></td></tr>
<tr><th>Driver</th><td><%= request.getAttribute("driver") %></td></tr>
<tr><th>Pickup Location</th><td><%= request.getAttribute("pickup") %></td></tr>
<tr><th>Drop Location</th><td><%= request.getAttribute("drop") %></td></tr>
<tr><th>Fare</th><td>₹<%= request.getAttribute("fare") %></td></tr>
<tr><th>Payment Method</th><td><%= request.getAttribute("method") %></td></tr>
<tr><th>Status</th><td><%= request.getAttribute("status") %></td></tr>
<tr><th>Ride Time</th><td><%= request.getAttribute("rideTime") %></td></tr>
<tr><th>Payment Time</th><td><%= request.getAttribute("time") %></td></tr>
</table>

<a href="ManagePaymentsServlet" class="btn btn-dark mt-3">⬅ Back</a>
</div>
</div>

</body>
</html>
