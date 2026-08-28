<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*,admin.User"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Assign Roles | RideNow Admin</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

<style>
body{
  background:#f4f6f9;
  font-family:'Segoe UI',Tahoma,sans-serif;
}

/* ===== SIDEBAR ===== */
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
  font-size:15px;
}

.sidebar a:hover,
.sidebar a.active{
  background:#1f2933;
  color:#fff;
  font-weight:600;
}

/* ===== MAIN ===== */
.main{
  margin-left:260px;
  padding:25px;
}

/* ===== CARD ===== */
.card{
  border-radius:16px;
  box-shadow:0 10px 25px rgba(0,0,0,.1);
  border:none;
}

.form-control{
  border-radius:10px;
  padding:12px;
}

.btn-assign{
  background:#f59e0b;
  color:#111827;
  font-weight:700;
  border-radius:10px;
}
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
<!-- ===== MAIN CONTENT ===== -->
<div class="main">

<h3 class="fw-bold mb-4">🧩 Assign User Role</h3>

<%
String msg = request.getParameter("msg");
if ("success".equals(msg)) {
%>
<div class="alert alert-success">Role updated successfully</div>
<% } else if ("error".equals(msg)) { %>
<div class="alert alert-danger">Something went wrong</div>
<% } %>

<div class="card p-4 col-md-8">

<form action="AssignRoleServlet" method="post">

<div class="mb-3">
<label class="form-label fw-bold">Select User</label>
<select name="userId" class="form-control" required>
<option value="">-- Select User --</option>

<%
List<User> users = (List<User>) request.getAttribute("users");
if (users != null) {
    for (User u : users) {
%>
<option value="<%=u.getId()%>">
<%=u.getName()%> - <%=u.getEmail()%> ( <%=u.getRole()%> )
</option>
<%
    }
}
%>
</select>
</div>

<div class="mb-4">
<label class="form-label fw-bold">Select Role</label>
<select name="role" class="form-control" required>
<option value="">-- Select Role --</option>
<option value="rider">Rider</option>
<option value="driver">Driver</option>
</select>
</div>

<div class="text-end">
<button type="submit" class="btn btn-assign px-4">
<i class="bi bi-check-circle"></i> Assign Role
</button>
</div>

</form>

</div>
</div>

</body>
</html>
