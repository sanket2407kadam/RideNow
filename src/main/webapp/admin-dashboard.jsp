<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<%
    int users = (request.getAttribute("users") != null) ? (int) request.getAttribute("users") : 0;
    int drivers = (request.getAttribute("drivers") != null) ? (int) request.getAttribute("drivers") : 0;
    int rides = (request.getAttribute("rides") != null) ? (int) request.getAttribute("rides") : 0;
    double payments = (request.getAttribute("payments") != null) ? (double) request.getAttribute("payments") : 0.0;
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>RideNow | Admin Dashboard</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">

<style>
body{
  background:linear-gradient(135deg,#eef2ff,#f8fafc);
  font-family:'Inter',sans-serif;
  color:#1f2937;
}

/* ===== SIDEBAR ===== */
.sidebar{
  width:260px;
  min-height:100vh;
  background:linear-gradient(180deg,#020617,#111827);
  color:#fff;
  position:fixed;
  box-shadow:4px 0 20px rgba(0,0,0,.3);
}

.sidebar h4{
  padding:22px;
  text-align:center;
  background:linear-gradient(135deg,#dc2626,#ef4444);
  font-weight:700;
}

.sidebar a{
  display:flex;
  align-items:center;
  gap:12px;
  padding:14px 24px;
  color:#cbd5e1;
  text-decoration:none;
  transition:.3s;
}

.sidebar a:hover{
  background:#1f2933;
  color:#fff;
  padding-left:30px;
}

.sidebar a.active{
  background:#1f2933;
  color:#fff;
  font-weight:600;
  position:relative;
}

.sidebar a.active::before{
  content:'';
  position:absolute;
  left:0;
  top:0;
  width:4px;
  height:100%;
  background:#3b82f6;
}

/* ===== MAIN ===== */
.main{
  margin-left:260px;
  padding:35px;
}

/* ===== CARDS ===== */
.card,.stat-card{
  border-radius:22px;
  background:rgba(255,255,255,0.9);
  backdrop-filter: blur(15px);
  box-shadow:0 20px 40px rgba(0,0,0,.15);
  padding:28px;
  transition:.4s;
}

.card:hover,.stat-card:hover{
  transform:translateY(-8px);
  box-shadow:0 30px 60px rgba(0,0,0,.25);
}

/* ===== STAT COLORS ===== */
.card-users{background:linear-gradient(135deg,#2563eb,#60a5fa);color:#fff;}
.card-drivers{background:linear-gradient(135deg,#f59e0b,#fbbf24);color:#fff;}
.card-rides{background:linear-gradient(135deg,#10b981,#34d399);color:#fff;}
.card-payments{background:linear-gradient(135deg,#ec4899,#f472b6);color:#fff;}

/* ===== BUTTONS ===== */
.stat-card .btn{
  border-radius:14px;
  font-weight:600;
}
</style>
</head>

<body>

<!-- SIDEBAR -->
<div class="sidebar">
  <h4>🛠 Admin Panel</h4>
  <a href="Admin-Dashboard" class="active"><i class="bi bi-speedometer2"></i> Dashboard</a>
  <a href="ManageUsersServlet"><i class="bi bi-people"></i> Manage Users</a>
  <a href="ManageDriversServlet"><i class="bi bi-car-front"></i> Manage Drivers</a>
  <a href="add-driver.jsp"><i class="bi bi-person-plus"></i> Add Driver</a>
  <a href="AssignRoleServlet"><i class="bi bi-person-check"></i> Assign Role</a>
  <a href="ManageRidesServlet"><i class="bi bi-pin-map"></i> Manage Rides</a>
  <a href="ManagePaymentsServlet"><i class="bi bi-credit-card"></i> Payments</a>
  <a href="SupportServlet"><i class="bi bi-headset"></i> Support</a>
  <a href="WebsiteSettingsServlet"><i class="bi bi-gear"></i> Settings</a>
  <a href="AdminLogoutServlet" class="text-danger"><i class="bi bi-box-arrow-right"></i> Logout</a>
</div>

<!-- MAIN -->
<div class="main">

<h3 class="fw-bold mb-1">Welcome back, Admin 👋</h3>
<p class="text-muted mb-4">Here’s what’s happening with RideNow today</p>

<!-- STATS -->
<div class="row g-4">
  <div class="col-md-3">
    <div class="card card-users text-center">
      <h5>👤 Users</h5>
      <h3><%= users %></h3>
    </div>
  </div>

  <div class="col-md-3">
    <div class="card card-drivers text-center">
      <h5>🚖 Drivers</h5>
      <h3><%= drivers %></h3>
    </div>
  </div>

  <div class="col-md-3">
    <div class="card card-rides text-center">
      <h5>📍 Rides</h5>
      <h3><%= rides %></h3>
    </div>
  </div>

  <div class="col-md-3">
    <div class="card card-payments text-center">
      <h5>💳 Payments</h5>
      <h3>₹ <%= String.format("%.2f", payments) %></h3>
    </div>
  </div>
</div>

<!-- QUICK MANAGEMENT -->
<div class="row g-4 mt-5">
  <h4 class="fw-bold mb-3">🚀 Quick Management</h4>

  <div class="col-md-3">
    <div class="stat-card">
      <h5><i class="bi bi-people"></i> Users</h5>
      <p class="text-muted">Manage riders & accounts</p>
      <a href="ManageUsersServlet" class="btn btn-primary">Open</a>
    </div>
  </div>

  <div class="col-md-3">
    <div class="stat-card">
      <h5><i class="bi bi-car-front"></i> Drivers</h5>
      <p class="text-muted">Approve or suspend drivers</p>
      <a href="ManageDriversServlet" class="btn btn-warning">Open</a>
    </div>
  </div>

  <div class="col-md-3">
    <div class="stat-card">
      <h5><i class="bi bi-pin-map"></i> Rides</h5>
      <p class="text-muted">Track all rides</p>
      <a href="ManageRidesServlet" class="btn btn-danger">Open</a>
    </div>
  </div>

  <div class="col-md-3">
    <div class="stat-card">
      <h5><i class="bi bi-credit-card"></i> Payments</h5>
      <p class="text-muted">View transactions</p>
      <a href="ManagePaymentsServlet" class="btn btn-info">Open</a>
    </div>
  </div>
</div>

</div>
</body>
</html>
