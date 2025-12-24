import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import java.sql.*;

@WebServlet("/DoctorPatientsServlet")
public class DoctorPatientsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            res.sendRedirect("doctor_login.html");
            return;
        }

        String doctorUser = (String) session.getAttribute("username");

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>My Patients</title>");
        out.println("<style>");

        // ===== LIGHT PURPLE THEME =====
        out.println("body { font-family: Arial, sans-serif; background-color: #f3eaff; margin:0; padding:0; }");
        out.println(".container { max-width: 900px; margin: 50px auto; background-color: #fff; padding: 40px; box-shadow: 0 0 25px rgba(128,0,128,0.2); border-radius: 12px; text-align:center; }");
        out.println("h2 { color: #6a1b9a; margin-bottom:30px; }");

        out.println("table { width: 90%; margin:auto; border-collapse: collapse; box-shadow: 0 0 15px rgba(128,0,128,0.1); border-radius:12px; overflow:hidden; background:white; }");
        out.println("th, td { padding: 12px; border: 1px solid #e0cfff; text-align:center; }");
        out.println("th { background: linear-gradient(135deg,#6a1b9a,#8e24aa); color: white; }");
        out.println("tr:nth-child(even) { background-color: #faf5ff; }");

        out.println("a.back-btn { display:inline-block; margin-top:25px; text-decoration:none; color:white; background:linear-gradient(135deg,#6a1b9a,#8e24aa); padding:12px 25px; border-radius:25px; box-shadow:0 4px 10px rgba(128,0,128,0.35); }");
        out.println("a.back-btn:hover { background:linear-gradient(135deg,#8e24aa,#6a1b9a); }");

        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h2>My Patients</h2>");
        out.println("<table>");
        out.println("<tr><th>Patient</th><th>Appointment Date</th><th>Time</th></tr>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "system",
                "manager"
            );

            Statement stmt = con.createStatement();

            // Show all patients who booked appointments with this doctor
            ResultSet rs = stmt.executeQuery(
                "SELECT DISTINCT patient_username, app_date, app_time " +
                "FROM appointments WHERE doctor_name='" + doctorUser + "'"
            );

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("patient_username") + "</td>");
                out.println("<td>" + rs.getString("app_date") + "</td>");
                out.println("<td>" + rs.getString("app_time") + "</td>");
                out.println("</tr>");
            }

            con.close();

        } catch (Exception e) {
            out.println("<tr><td colspan='3'>Error: " + e + "</td></tr>");
        }

        out.println("</table>");
        out.println("<br><a class='back-btn' href='DoctorDashboardServlet'>Back to Dashboard</a>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
