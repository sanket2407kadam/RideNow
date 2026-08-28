<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Support Requests | RideNow Admin</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

<style>
body{
  background:#f4f6f9;
  font-family:'Segoe UI',Tahoma,sans-serif;
}

/* ===== SIDEBAR (SAME AS MAIN PAGE) ===== */
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
  transition:.3s;
}

.sidebar a:hover{
  background:#1f2933;
  color:#fff;
}

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
  padding:20px;
  margin-bottom:18px;
}

.card p{
  margin-bottom:8px;
}

/* ===== BADGES ===== */
.status-pending{
  color:#d97706;
  font-weight:600;
}

.status-resolved{
  color:#16a34a;
  font-weight:600;
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
  <i class="bi bi-headset"></i> Website settings
</a>

  <a href="AdminLogoutServlet" class="text-danger">Logout</a>
</div>
<!-- ===== MAIN CONTENT ===== -->
<div class="main">

<h3 class="fw-bold mb-4">🎧 Support Requests</h3>

<c:if test="${empty supports}">
  <div class="alert alert-info">
    No support requests found
  </div>
</c:if>

<c:forEach var="s" items="${supports}">
  <div class="card">

    <p><strong><i class="bi bi-envelope"></i> Email:</strong> ${s.email}</p>

    <p><strong><i class="bi bi-chat-left-text"></i> Issue:</strong><br>
       ${s.message}
    </p>

    <p>
      <strong><i class="bi bi-info-circle"></i> Status:</strong>
      <span class="${s.status eq 'Resolved' ? 'status-resolved' : 'status-pending'}">
        ${s.status}
      </span>
    </p>

    <p>
      <strong><i class="bi bi-calendar-event"></i> Submitted On:</strong>
      ${s.createdAt}
    </p>

    <c:if test="${s.status eq 'Pending'}">
      <form action="ResolveSupportServlet" method="post" class="mt-3">
        <input type="hidden" name="messageId" value="${s.messageId}">
        <button class="btn btn-success btn-sm">
          <i class="bi bi-check-circle"></i> Mark as Resolved
        </button>
      </form>
    </c:if>

  </div>
</c:forEach>

</div>

</body>
</html>
