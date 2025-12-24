import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/DoctorViewPaymentsServlet")   // ✅ FIXED SPELLING
public class DoctorViewPaymentsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Session validation
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            res.sendRedirect("doctor_login.html");
            return;
        }

        String doctorUser = (String) session.getAttribute("username");

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Doctor Payments</title>");
        out.println("<style>");

        // ===== LIGHT PURPLE THEME =====
        out.println("body{font-family:Arial;background:#f3eaff;}");

        out.println("h2{text-align:center;color:#6a1b9a;margin:30px 0;}");

        out.println("table{width:90%;margin:auto;border-collapse:collapse;");
        out.println("background:white;box-shadow:0 0 20px rgba(128,0,128,0.25);");
        out.println("border-radius:12px;overflow:hidden;}");

        out.println("th,td{border:1px solid #e0cfff;padding:12px;text-align:center;}");

        out.println("th{background:linear-gradient(135deg,#6a1b9a,#8e24aa);color:white;}");

        out.println("tr:nth-child(even){background:#faf5ff;}");

        out.println("a{display:inline-block;margin-top:25px;text-decoration:none;");
        out.println("color:white;background:linear-gradient(135deg,#6a1b9a,#8e24aa);");
        out.println("padding:12px 25px;border-radius:25px;");
        out.println("box-shadow:0 4px 10px rgba(128,0,128,0.35);}");        

        out.println("a:hover{background:linear-gradient(135deg,#8e24aa,#6a1b9a);}");        

        out.println("</style></head><body>");

        out.println("<h2>Payments Received</h2>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "system",
                "manager"
            );

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM payments WHERE doctor_username = ?"
            );
            ps.setString(1, doctorUser);

            ResultSet rs = ps.executeQuery();

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Payment ID</th>");
            out.println("<th>Patient</th>");
            out.println("<th>Amount</th>");
            out.println("<th>Date</th>");
            out.println("</tr>");

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                out.println("<tr>");
                out.println("<td>" + rs.getString("payment_id") + "</td>");
                out.println("<td>" + rs.getString("patient_username") + "</td>");
                out.println("<td>" + rs.getString("doctor_amount") + "</td>"); // ✅ FIXED
                out.println("<td>" + rs.getString("payment_date") + "</td>");
                out.println("</tr>");
            }

            if (!hasData) {
                out.println("<tr><td colspan='4'>No payments received yet</td></tr>");
            }

            out.println("</table>");
            con.close();

        } catch (Exception e) {
            out.println("<center><b>Error:</b> " + e + "</center>");
        }

        out.println("<br><center>");
        out.println("<a href='DoctorDashboardServlet'>Back to Dashboard</a>");
        out.println("</center>");

        out.println("</body></html>");
    }
}
