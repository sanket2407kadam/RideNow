<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>RideNow | Manage Rides</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

<style>
body{background:#f4f6f9;font-family:'Segoe UI',Tahoma,sans-serif;}
.sidebar{width:260px;min-height:100vh;background:#111827;color:#fff;position:fixed;}
.sidebar h4{padding:20px;text-align:center;background:#dc2626;margin:0;font-weight:700;}
.sidebar a{display:block;padding:14px 20px;color:#cbd5e1;text-decoration:none;font-size:15px;}
.sidebar a:hover,.sidebar a.active{background:#1f2933;color:#fff;font-weight:600;}
.main{margin-left:260px;padding:25px;}
.card{border-radius:16px;box-shadow:0 10px 25px rgba(0,0,0,.1);padding:20px;}
.badge-ongoing{background:#facc15;color:#111827;}
.badge-completed{background:#16a34a;}
.badge-cancelled{background:#dc2626;}
.badge-other{background:#6b7280;color:#fff;}
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

<div class="main">
<h3 class="fw-bold mb-4"> Manage Rides</h3>

<div class="card">
<table class="table table-striped table-hover">
<thead class="table-dark">
<tr>
<th>ID</th><th>Rider</th><th>Driver</th><th>From</th><th>To</th>
<th>Date</th><th>Status</th><th>Fare</th><th>Actions</th>
</tr>
</thead>

<tbody>
<%
List<Map<String,Object>> rides =
    (List<Map<String,Object>>) request.getAttribute("rides");

if (rides != null && !rides.isEmpty()) {
    for (Map<String,Object> r : rides) {
        String status = (String) r.get("status");
        String badgeClass =
            "completed".equalsIgnoreCase(status) ? "badge-completed" :
            "requested".equalsIgnoreCase(status) || "accepted".equalsIgnoreCase(status)
                ? "badge-ongoing" :
            "cancelled".equalsIgnoreCase(status) ? "badge-cancelled" :
            "badge-other";
%>
<tr>
<td><%= r.get("id") %></td>
<td><%= r.get("rider") %></td>
<td><%= r.get("driver") %></td>
<td><%= r.get("from") %></td>
<td><%= r.get("to") %></td>
<td><%= r.get("time") %></td>
<td><span class="badge <%= badgeClass %>"><%= status %></span></td>
<td>₹<%= r.get("fare") %></td>
<td>
<a href="ViewRideServlet?id=<%= r.get("id") %>" class="btn btn-sm btn-info">
<i class="bi bi-eye"></i> View</a>
<a href="CancelRideServlet?id=<%= r.get("id") %>"
 class="btn btn-sm btn-danger"
 onclick="return confirm('Cancel this ride?')">
<i class="bi bi-x-circle"></i> Cancel</a>
</td>
</tr>
<% } } else { %>
<tr>
<td colspan="9" class="text-center text-muted">No rides found</td>
</tr>
<% } %>
</tbody>
</table>
</div>
</div>
</body>
</html>
