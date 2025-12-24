import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/PharmacistViewPrescriptionsServlet")
public class PharmacistViewPrescriptionsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            res.sendRedirect("pharmacist_login.html");
            return;
        }

        String pharmacistUsername = (String) session.getAttribute("username");

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Prescriptions</title>");
        out.println("<style>");
        out.println("body {font-family: Arial, sans-serif; background:#fefcf3; color:#333;}");
        out.println("h2 {text-align:center; color:#444; margin-top:20px;}");
        out.println("table {width:90%; margin:30px auto; border-collapse:collapse;}");
        out.println("th, td {border:1px solid #ccc; padding:10px; text-align:center;}");
        out.println("th {background:#f5d76e; color:#333;}");
        out.println("tr:nth-child(even) {background:#fff9e6;}");
        out.println("tr:nth-child(odd) {background:#fdf6e3;}");
        out.println(".back {text-align:center; margin-top:20px;}");
        out.println(".back a {padding:10px 20px; background:#f5d76e; color:#333; text-decoration:none; border-radius:5px;}");
        out.println(".back a:hover {background:#e4c75a;}");
        out.println("</style></head><body>");

        out.println("<h2>Prescriptions for Pharmacist: " + pharmacistUsername + "</h2>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            Statement stmt = con.createStatement();

            // Fetch all prescriptions (you can filter by pharmacist if needed)
            ResultSet rs = stmt.executeQuery(
                    "SELECT * FROM prescriptions ORDER BY presc_date DESC"
            );

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Prescription ID</th>");
            out.println("<th>Patient Username</th>");
            out.println("<th>Doctor Name</th>");
            out.println("<th>Medicine</th>");
            out.println("<th>Dosage</th>");
            out.println("<th>Date</th>");
            out.println("</tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("presc_id") + "</td>");
                out.println("<td>" + rs.getString("patient_username") + "</td>");
                out.println("<td>" + rs.getString("doctor_name") + "</td>");
                out.println("<td>" + rs.getString("medicine") + "</td>");
                out.println("<td>" + rs.getString("dosage") + "</td>");
                out.println("<td>" + rs.getString("presc_date") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            con.close();

        } catch (Exception e) {
            out.println("<center><b>Error:</b> " + e + "</center>");
        }

        out.println("<div class='back'><a href='PharmacistDashboardServlet'>Back to Dashboard</a></div>");
        out.println("</body></html>");
    }
}
