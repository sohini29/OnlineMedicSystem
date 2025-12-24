import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/AdminViewFeedbackServlet")
public class AdminViewFeedbackServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Session validation
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            res.sendRedirect("admin_login.html");
            return;
        }

        out.println("<html><head><title>View Feedback</title>");

        /* ===== GREEN ADMIN THEME ===== */
        out.println("<style>");
        out.println("body{font-family:Arial;background:#e8f5e9;margin:0;padding:0;}");
        out.println(".container{max-width:1000px;margin:40px auto;padding:30px;background:white;"
                + "border-radius:12px;box-shadow:0 8px 25px rgba(46,125,50,0.25);}");
        out.println("h2{text-align:center;color:#2e7d32;margin-bottom:25px;}");
        out.println("table{width:100%;border-collapse:collapse;}");
        out.println("th,td{padding:12px;border-bottom:1px solid #c8e6c9;text-align:left;}");
        out.println("th{background:#a5d6a7;color:#1b5e20;}");
        out.println("tr:hover{background:#f1f8e9;}");
        out.println(".btn{display:inline-block;margin-top:20px;padding:10px 22px;"
                + "background:linear-gradient(135deg,#2e7d32,#1b5e20);"
                + "color:white;text-decoration:none;border-radius:25px;}");
        out.println(".btn:hover{background:linear-gradient(135deg,#1b5e20,#2e7d32);}");
        out.println("</style></head><body>");

        out.println("<div class='container'>");
        out.println("<h2>Feedback Received</h2>");

        out.println("<table>");
        out.println("<tr>");
        out.println("<th>User Type</th>");
        out.println("<th>Username</th>");
        out.println("<th>Feedback</th>");
        out.println("<th>Date</th>");
        out.println("</tr>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE",
                    "system",
                    "manager"
            );

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT user_type, username, feedback_text, feedback_date FROM feedback ORDER BY feedback_date DESC"
            );

            boolean hasData = false;

            while (rs.next()) {
                hasData = true;
                out.println("<tr>");
                out.println("<td>" + rs.getString("user_type") + "</td>");
                out.println("<td>" + rs.getString("username") + "</td>");
                out.println("<td>" + rs.getString("feedback_text") + "</td>");
                out.println("<td>" + rs.getDate("feedback_date") + "</td>");
                out.println("</tr>");
            }

            if (!hasData) {
                out.println("<tr><td colspan='4' style='text-align:center;color:gray;'>No feedback available</td></tr>");
            }

            con.close();

        } catch (Exception e) {
            out.println("<tr><td colspan='4' style='color:red;'>Error: " + e + "</td></tr>");
        }

        out.println("</table>");

        out.println("<center><a class='btn' href='AdminDashboardServlet'>Back to Dashboard</a></center>");
        out.println("</div></body></html>");
    }
}
