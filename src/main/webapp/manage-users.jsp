<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*,admin.User" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Manage Users | RideNow Admin</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

<style>
body{background:#f4f6f9;font-family:'Segoe UI',Tahoma}
.sidebar{width:260px;min-height:100vh;background:#111827;color:#fff;position:fixed}
.sidebar h4{padding:20px;text-align:center;background:#dc2626}
.sidebar a{display:block;padding:14px 20px;color:#cbd5e1;text-decoration:none}
.sidebar a:hover{background:#1f2933;color:#fff}
.sidebar a.active{background:#1f2933;color:#fff;font-weight:600}
.main{margin-left:260px;padding:25px}
.card{border-radius:16px;box-shadow:0 10px 25px rgba(0,0,0,.1);padding:20px}
.table thead{background:#2563eb;color:#fff}
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
<h3 class="fw-bold mb-4">👤 Manage Users</h3>

<div class="card">
<table class="table table-hover text-center align-middle">
<thead>
<tr>
  <th>ID</th>
  <th>Name</th>
  <th>Email</th>
  <th>Role</th>
  <th>Action</th>
</tr>
</thead>

<tbody>
<%
List<User> users = (List<User>) request.getAttribute("users");

if(users != null && !users.isEmpty()){
    for(User u : users){
%>
<tr>
<td><%= u.getId() %></td>
<td><%= u.getName() %></td>
<td><%= u.getEmail() %></td>
<td><%= u.getRole() %></td>
<td>
  <a href="DeleteUserServlet?id=<%=u.getId()%>"
     class="btn btn-danger btn-sm"
     onclick="return confirm('Delete this user?')">
     Delete
  </a>
</td>
</tr>
<%
    }
}else{
%>
<tr>
<td colspan="5" class="text-muted fw-bold">No users found</td>
</tr>
<% } %>
</tbody>
</table>
</div>
</div>

</body>
</html>
