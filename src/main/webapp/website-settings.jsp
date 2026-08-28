<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>RideNow | Website Settings</title>
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
}

.card h5{
  font-weight:600;
}

/* ===== FORM ===== */
.form-label{
  font-weight:600;
  font-size:14px;
}

.form-control{
  border-radius:10px;
}

textarea{
  resize:none;
}

/* ===== BUTTON ===== */
.btn{
  border-radius:10px;
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

 <a href="website-setting.jsp" class="active">
  <i class="bi bi-headset"></i> website setting
</a>

  <a href="AdminLogoutServlet" class="text-danger">Logout</a>
</div>

<!-- ===== MAIN CONTENT ===== -->
<div class="main">

<h3 class="fw-bold mb-4">⚙ Website Settings</h3>

<div class="row g-4">

  <!-- WEBSITE INFO -->
  <div class="col-md-6">
    <div class="card p-4">
      <h5>🌐 Website Information</h5>

      <form action="UpdateWebsiteSettingsServlet" method="post">
        <input type="hidden" name="action" value="site">

        <label class="form-label">Website Name</label>
        <input type="text" name="websiteName" class="form-control mb-3"
               value="${settings.websiteName}">

        <label class="form-label">Support Email</label>
        <input type="email" name="supportEmail" class="form-control mb-3"
               value="${settings.supportEmail}">

        <label class="form-label">Support Phone</label>
        <input type="text" name="supportPhone" class="form-control mb-3"
               value="${settings.supportPhone}">

        <button class="btn btn-primary">Save Changes</button>
      </form>
    </div>
  </div>

  <!-- PRICING -->
  <div class="col-md-6">
    <div class="card p-4">
      <h5>💰 Pricing Rules</h5>

      <form action="UpdateWebsiteSettingsServlet" method="post">
        <input type="hidden" name="action" value="pricing">

        <label class="form-label">Base Fare (₹)</label>
        <input type="number" name="baseFare" class="form-control mb-3"
               value="${settings.baseFare}">

        <label class="form-label">Per KM Charge (₹)</label>
        <input type="number" name="perKm" class="form-control mb-3"
               value="${settings.perKmCharge}">

        <label class="form-label">Cancellation Fee (₹)</label>
        <input type="number" name="cancelFee" class="form-control mb-3"
               value="${settings.cancellationFee}">

        <button class="btn btn-success">Update Pricing</button>
      </form>
    </div>
  </div>

  <!-- CONTENT -->
  <div class="col-md-12">
    <div class="card p-4">
      <h5>🖼 Home Page Content</h5>

      <form action="UpdateWebsiteSettingsServlet" method="post">
        <input type="hidden" name="action" value="content">

        <label class="form-label">Banner Text</label>
        <textarea name="bannerText" class="form-control mb-3" rows="3">
${settings.bannerText}</textarea>

        <label class="form-label">Announcement</label>
        <textarea name="announcement" class="form-control mb-3" rows="2">
${settings.announcement}</textarea>

        <button class="btn btn-dark">Update Content</button>
      </form>
    </div>
  </div>

</div>
</div>

</body>
</html>
