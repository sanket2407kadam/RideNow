<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>RideNow | Rider Dashboard</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">

<style>
body{
    font-family:'Segoe UI',Tahoma,sans-serif;
    background:#f0f2f5;
    color:#333;
    margin:0;
    padding:0;
}

/* Navbar */
.navbar{
    display:flex;
    justify-content:space-between;
    align-items:center;
    background:#fff;
    padding:14px 40px;
    box-shadow:0 2px 12px rgba(0,0,0,.1);
    position: sticky;
    top:0;
    z-index: 1000;
}
.logo{font-size:1.8em;font-weight:800;color:#2563eb;}
nav a{
    margin-left:22px;
    color:#555;
    text-decoration:none;
    font-weight:600;
    font-size:.95em;
    transition:.3s;
}
nav a:hover{color:#2563eb;}

/* Dashboard Section */
.dashboard-section{
    display:flex;
    justify-content:center;
    padding:40px 20px;
}
.dashboard-card{
    background:#fff;
    width:100%;
    max-width:900px;
    padding:40px;
    border-radius:20px;
    box-shadow:0 20px 40px rgba(0,0,0,.1);
    text-align:center;
    transition: all 0.3s ease;
}
.dashboard-card:hover{
    transform: translateY(-5px);
    box-shadow:0 25px 50px rgba(0,0,0,.15);
}

/* Action Buttons */
.dashboard-card a{
    display:inline-block;
    padding:12px 26px;
    margin:10px;
    background: linear-gradient(135deg,#2563eb,#1e40af);
    color:#fff;
    border-radius:12px;
    text-decoration:none;
    font-weight:600;
    transition: all 0.3s ease;
}
.dashboard-card a:hover{
    background: linear-gradient(135deg,#1e40af,#2563eb);
    transform: translateY(-3px);
    box-shadow:0 8px 20px rgba(37,99,235,.4);
}

/* Profile Section */
.profile-section, .notifications, .upcoming-rides{
    text-align:left;
    margin-top:30px;
}
.profile-section h3,.notifications h3,.upcoming-rides h3{
    color:#2563eb;
    margin-bottom:15px;
    border-bottom:2px solid #2563eb;
    display:inline-block;
    padding-bottom:4px;
}

/* Notification Cards */
.notifications .alert{
    background:#eaf1ff;
    color:#2563eb;
    padding:12px 18px;
    border-radius:12px;
    margin-bottom:10px;
    font-size:.95em;
    display:flex;
    align-items:center;
}
.notifications .alert i{
    margin-right:8px;
}

/* Table */
.table th{
    background:#2563eb;
    color:#fff;
}
.table td, .table th{
    vertical-align: middle;
}
.table-striped>tbody>tr:nth-of-type(odd){
    background-color:#f9f9f9;
}
.table td span.badge{
    padding:.35em .65em;
    font-size:.85em;
    border-radius:.5rem;
    font-weight:600;
}

/* Ride Status Badges */
.badge-success{background:#28a745;color:#fff;}
.badge-warning{background:#ffc107;color:#333;}
.badge-danger{background:#dc3545;color:#fff;}
</style>
</head>
<body>

<header class="navbar">
  <div class="logo"><i class="fa-solid fa-car-side"></i> RideNow</div>
  <nav>
    <a href="index.html"><i class="fa-solid fa-house"></i> Home</a>
    <a href="LogoutServlet"><i class="fa-solid fa-right-from-bracket"></i> Logout</a>
  </nav>
</header>

<section class="dashboard-section">
<div class="dashboard-card">

<h2>Welcome, ${name}!</h2>
<p class="text-muted">Manage your rides, bookings, and payment history.</p>

<div class="action-buttons">
    <a href="Book-ride.html"><i class="fa-solid fa-map-pin"></i> Book Ride</a>
    <a href="myrides"><i class="fa-solid fa-road"></i> My Rides</a>
    <a href="payment-history"><i class="fa-solid fa-credit-card"></i> Payment History</a>
    <a href="track.html"><i class="fa-solid fa-location-crosshairs"></i> Track Ride</a>
</div>

<!-- Profile -->
<div class="profile-section">
  <h3><i class="fa-solid fa-user"></i> Your Profile</h3>
  <p><strong>Name:</strong> ${name}</p>
  <p><strong>Email:</strong> ${email}</p>
  <p><strong>Phone:</strong> ${phone}</p>
  <a href="edit-profile"><i class="fa-solid fa-pen"></i> Edit Profile</a>
</div>

<!-- Notifications -->
<div class="notifications">
  <h3><i class="fa-solid fa-bell"></i> Notifications</h3>
  ${notifications}
</div>

<!-- Upcoming Rides -->
<div class="upcoming-rides">
  <h3><i class="fa-solid fa-road-circle-check"></i> Upcoming Rides</h3>
  <div class="table-responsive">
  <table class="table table-bordered table-striped">
    <thead>
    <tr>
      <th>Ride ID</th><th>From</th><th>To</th>
      <th>Date</th><th>Fare</th><th>Status</th>
    </tr>
    </thead>
    <tbody>
      ${rides}
    </tbody>
  </table>
  </div>
</div>

</div>
</section>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/js/all.min.js"></script>
</body>
</html>
