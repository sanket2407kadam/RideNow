package admin;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/DriverMonthlyReportServlet")
public class DriverMonthlyReportServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String driverIdParam = request.getParameter("driver_id");
        if(driverIdParam == null) {
            response.getWriter().println("Driver ID missing!");
            return;
        }

        int driverId = Integer.parseInt(driverIdParam);
        List<Map<String,Object>> report = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ridenow?useSSL=false&serverTimezone=UTC",
                "root","root"
            );

            String sql =
                "SELECT YEAR(r.ride_time) AS year, " +
                "MONTH(r.ride_time) AS month_num, " +
                "DATE_FORMAT(MIN(r.ride_time), '%M') AS month_name, " +
                "COUNT(r.ride_id) AS total_rides, " +
                "COALESCE(SUM(rd.fare),0) AS total_earning " +
                "FROM rides r " +
                "JOIN ride_details rd ON r.ride_id=rd.ride_id " +
                "JOIN payments p ON r.ride_id=p.ride_id " +
                "WHERE r.driver_id=? AND p.payment_status='SUCCESS' " +
                "GROUP BY YEAR(r.ride_time), MONTH(r.ride_time) " +
                "ORDER BY YEAR(r.ride_time) DESC, MONTH(r.ride_time) DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, driverId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Map<String,Object> row = new HashMap<>();
                row.put("year", rs.getInt("year"));
                row.put("month_num", rs.getInt("month_num"));
                row.put("month_name", rs.getString("month_name"));
                row.put("total_rides", rs.getInt("total_rides"));
                row.put("total_earning", rs.getDouble("total_earning"));
                report.add(row);
            }

            con.close();

        } catch(Exception e){
            e.printStackTrace();
        }

        request.setAttribute("report", report);
        request.getRequestDispatcher("driver-monthly-report.jsp")
               .forward(request, response);
    }
}
