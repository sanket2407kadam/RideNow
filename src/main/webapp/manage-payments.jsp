<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>

<title>Payments | RideNow Admin</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

<style>
body{background:#f4f6f9;font-family:'Segoe UI',Tahoma,sans-serif;}
.sidebar{width:260px;min-height:100vh;background:#111827;color:#fff;position:fixed;}
.sidebar h4{padding:20px;text-align:center;background:#dc2626;margin:0;font-weight:700;}
.sidebar a{display:block;padding:14px 20px;color:#cbd5e1;text-decoration:none;font-size:15px;}
.sidebar a:hover{background:#1f2933;color:#fff;}
.sidebar a.active{background:#1f2933;color:#fff;font-weight:600;}
.main{margin-left:260px;padding:25px;}
.card{border-radius:16px;box-shadow:0 10px 25px rgba(0,0,0,.1);border:none;padding:20px;}
.table thead{background:#2563eb;color:#fff;}
.status-success{color:#16a34a;font-weight:600;}
.status-failed{color:#dc2626;font-weight:600;}
.status-pending{color:#facc15;font-weight:600;}
</style>
</head>

<body>
<!-- SIDEBAR -->
<div class="sidebar">
  <h4>🛠 Admin Panel</h4>
  <a href="Admin-Dashboard">Dashboard</a>
  <a href="ManageUsersServlet">Manage Users</a>
  <a href="ManageDriversServlet">Manage Drivers</a>
  <a href="add-driver.jsp">Add Driver</a>
  <a href="AssignRoleServlet">Assign Role</a>
  <a href="ManageRidesServlet">Manage Rides</a>
  <a href="ManagePaymentsServlet" class="active">Manage Payments</a>
  <a href="SupportServlet">Support Requests</a>
  <a href="WebsiteSettingsServlet">Website Settings</a>
  <a href="AdminLogoutServlet" class="text-danger">Logout</a>
</div>

<div class="main">

<h3 class="fw-bold mb-4">💳 Payments</h3>

<!-- Filter & Search -->
<div class="card mb-4">
  <form method="get" action="ManagePaymentsServlet" class="row g-3 align-items-center">
    <div class="col-auto">
      <select name="status" class="form-select">
        <option value="">All Status</option>
        <option value="Success">Success</option>
        <option value="Failed">Failed</option>
        <option value="Pending">Pending</option>
      </select>
    </div>
    <div class="col-auto">
      <input type="text" name="query" class="form-control" placeholder="Search Rider, Driver, Ride ID">
    </div>
    <div class="col-auto">
      <button type="submit" class="btn btn-primary"><i class="bi bi-search"></i> Search</button>
    </div>
  </form>
</div>

<div class="card">
<table class="table table-hover text-center align-middle">
<thead>
<tr>
  <th>ID</th>
  <th>Ride ID</th>
  <th>Rider</th>
  <th>Driver</th>
  <th>Amount</th>
  <th>Method</th>
  <th>Status</th>
  <th>Date</th>
  <th>Action</th>
</tr>
</thead>

<tbody>
<c:if test="${empty payments}">
<tr>
  <td colspan="9" class="text-muted text-center">No payment records found</td>
</tr>
</c:if>

<c:forEach var="p" items="${payments}">
<tr>
  <td>${p.paymentId}</td>
  <td>${p.rideId}</td>
  <td>${p.rider}</td>
  <td>${p.driver}</td>
  <td>&#8377;${p.amount}</td> <!-- Rupee symbol -->
  <td>${p.method}</td>
  <td class="${p.status eq 'Success' ? 'status-success' : (p.status eq 'Failed' ? 'status-failed' : 'status-pending')}">
    ${p.status}
  </td>
  <td>${p.time}</td>
  <td>
    <a href="ViewPaymentServlet?id=${p.paymentId}" class="btn btn-sm btn-info"><i class="bi bi-eye"></i> View</a>
    <c:if test="${p.status eq 'Success'}">
      <a href="RefundPaymentServlet?id=${p.paymentId}" class="btn btn-sm btn-warning"
         onclick="return confirm('Mark this payment as refunded?')">
         <i class="bi bi-arrow-counterclockwise"></i> Refund
      </a>
    </c:if>
    <c:if test="${p.status eq 'Failed'}">
      <a href="RetryPaymentServlet?id=${p.paymentId}" class="btn btn-sm btn-success"
         onclick="return confirm('Retry this failed payment?')">
         <i class="bi bi-arrow-repeat"></i> Retry
      </a>
    </c:if>
  </td>
</tr>
</c:forEach>
</tbody>
</table>
</div>

</div>
</body>
</html>
