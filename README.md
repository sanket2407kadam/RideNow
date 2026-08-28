# 🚗 RideNow – Online Ride Booking System

RideNow is a **web-based Online Ride Booking System** developed using **Java Web Technologies**. It provides users with a simple platform to register, log in, book rides, and manage their bookings.

The project demonstrates practical implementation of **HTML, CSS, JavaScript, JSP, Servlets, JDBC, and MySQL**.

---

## 📌 About The Project

RideNow is designed to simplify the ride-booking process through an easy-to-use web application.

Users can create an account, log in, request and book rides, view booking information, and manage their ride history.

The application uses **JSP and Java Servlets** for the web layer and application logic, **JDBC** for database connectivity, and **MySQL** for data storage.

---

## ✨ Features

### 👤 User

* User Registration
* User Login & Logout
* User Dashboard
* Book a Ride
* View Booking Details
* View Ride History
* Cancel Booking
* Manage Profile
* Session Management

### 🚘 Ride Management

* Ride Booking
* Pickup Location
* Destination
* Ride Date & Time
* Fare Information
* Booking Status
* Ride History

### 🔐 Authentication

* Secure Login
* User Registration
* Session-based Authentication
* Logout Functionality
* Client-side Validation
* Server-side Validation
* Protected User Pages

### 🛠️ Admin

* Admin Login
* Admin Dashboard
* Manage Users
* Manage Drivers
* Manage Rides
* Manage Bookings
* View Booking Information

---

## 🛠️ Technologies Used

| Technology    | Purpose                                  |
| ------------- | ---------------------------------------- |
| HTML5         | Web page structure                       |
| CSS3          | Styling and responsive design            |
| JavaScript    | Client-side functionality and validation |
| JSP           | Dynamic web pages                        |
| Java Servlets | Request handling and business logic      |
| JDBC          | Database connectivity                    |
| MySQL         | Data storage                             |
| Apache Tomcat | Web application server                   |
| Git & GitHub  | Version control                          |

---

## 🏗️ Architecture

RideNow follows a simple MVC-style web application architecture:

```text
Browser
   ↓
HTML / CSS / JavaScript
   ↓
JSP
   ↓
Servlets
   ↓
JDBC
   ↓
MySQL
```

### JSP

JSP is responsible for creating dynamic web pages and displaying application data.

### Servlets

Servlets handle HTTP requests, process application logic, and communicate with the required components.

### JDBC

JDBC provides connectivity between the Java application and MySQL database.

### MySQL

MySQL is used to store and manage application data.

---

## 🔄 Application Flow

```text
User Registration
       ↓
User Login
       ↓
User Dashboard
       ↓
Search / Request Ride
       ↓
Enter Ride Details
       ↓
Book Ride
       ↓
Booking Confirmation
       ↓
View Ride History
```

---

## 🔐 Login Flow

```text
Login Form
    ↓
Login Servlet
    ↓
JDBC
    ↓
MySQL
    ↓
Credential Validation
    ↓
User Dashboard
```

---

## ⚙️ Requirements

Before running RideNow, make sure you have:

* Java JDK 8 or above
* Apache Tomcat
* MySQL Server
* MySQL Workbench
* Eclipse / IntelliJ IDEA
* MySQL Connector/J
* Git

---

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/sanket2407kadam/RideNow.git
```

```bash
cd RideNow
```

### 2. Create the Database

Create a MySQL database:

```sql
CREATE DATABASE ridenow_db;
```

Import the required SQL tables/data for the application.

### 3. Configure Database Connection

Update the JDBC connection according to your local MySQL configuration:

```java
String url = "jdbc:mysql://localhost:3306/ridenow_db";
String username = "root";
String password = "YOUR_PASSWORD";
```

> **Important:** Never upload your actual database password or other sensitive credentials to GitHub.

### 4. Configure Apache Tomcat

Add the project to your Apache Tomcat server using Eclipse or IntelliJ IDEA.

### 5. Run the Application

Start the Tomcat server and open:

```text
http://localhost:8080/RideNow/
```

---

## 🧪 Testing

The application can be tested for:

* User Registration
* User Login
* Invalid Login
* Logout
* Session Management
* Ride Booking
* Booking Cancellation
* Ride History
* Profile Management
* Admin Operations
* CRUD Operations
* Database Connectivity

---

## 🔒 Security

RideNow implements basic web application security practices including:

* Session-based authentication
* Server-side validation
* Client-side validation
* Protected pages
* JDBC Prepared Statements
* Session management

For production use, additional security mechanisms such as password hashing, HTTPS, CSRF protection, and stronger authentication should be implemented.

---

## 🎯 Project Objectives

The project was developed to gain practical experience in:

* Java Web Development
* JSP
* Java Servlets
* JDBC
* MySQL
* HTML & CSS
* JavaScript
* MVC Architecture
* CRUD Operations
* Database Connectivity
* HTTP Request & Response Handling
* Session Management
* Form Validation
* Git & GitHub

---

## 🚀 Future Enhancements

* Google Maps integration
* Real-time ride tracking
* Online payment integration
* User and driver rating system
* Email and SMS notifications
* Mobile application
* Password hashing
* Advanced admin dashboard
* Driver-passenger chat
* Ride analytics
* Cloud deployment

---

## 👨‍💻 Author

### Sanket Kadam

**Java Full Stack Developer**

**GitHub:**
https://github.com/sanket2407kadam

**LinkedIn:**
https://www.linkedin.com/in/sanket-kadam2004

---

## ⭐ Repository

**RideNow – Online Ride Booking System**

https://github.com/sanket2407kadam/RideNow

If you find this project useful, please consider giving it a ⭐.

---

## 📄 License

This project is developed for **educational and portfolio purposes**.

© 2026 Sanket Kadam
