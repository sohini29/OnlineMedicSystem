import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/AdminViewPrescriptionsServlet")
public class AdminViewPrescriptionsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // ✅ Admin session check
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            res.sendRedirect("admin_login.html");
            return;
        }

        out.println("<html><head><title>View Prescriptions</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;background:#e8f5e9;}");
        out.println("h2{text-align:center;color:#2e7d32;margin-top:20px;}");
        out.println("table{width:95%;margin:20px auto;border-collapse:collapse;background:white;}");
        out.println("th,td{border:1px solid #a5d6a7;padding:10px;text-align:center;}");
        out.println("th{background:#2e7d32;color:white;}");
        out.println("tr:nth-child(even){background:#f1f8e9;}");
        out.println(".btn{display:inline-block;padding:10px 20px;"
                + "background:#2e7d32;color:white;text-decoration:none;"
                + "border-radius:20px;margin:20px auto;}");
        out.println(".btn:hover{background:#1b5e20;}");
        out.println("</style></head><body>");

        out.println("<h2>All Prescriptions</h2>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "system",
                "manager"
            );

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM prescription ORDER BY prescription_date DESC"
            );

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Prescription ID</th>");
            out.println("<th>Patient</th>");
            out.println("<th>Doctor</th>");
            out.println("<th>Medicine</th>");
            out.println("<th>Dosage</th>");
            out.println("<th>Instructions</th>");
            out.println("<th>Date</th>");
            out.println("</tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("prescription_id") + "</td>");
                out.println("<td>" + rs.getString("patient_username") + "</td>");
                out.println("<td>" + rs.getString("doctor_username") + "</td>");
                out.println("<td>" + rs.getString("medicine") + "</td>");
                out.println("<td>" + rs.getString("dosage") + "</td>");
                out.println("<td>" + rs.getString("instructions") + "</td>");
                out.println("<td>" + rs.getDate("prescription_date") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            con.close();

        } catch (Exception e) {
            out.println("<center><b>Error:</b> " + e + "</center>");
        }

        out.println("<center><a class='btn' href='AdminDashboardServlet'>Back to Dashboard</a></center>");
        out.println("</body></html>");
    }
}
