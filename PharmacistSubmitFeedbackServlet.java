import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/PharmacistSubmitFeedbackServlet")
public class PharmacistSubmitFeedbackServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Session check
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            res.sendRedirect("pharmacist_login.html");
            return;
        }

        String pharmacistUser = (String) session.getAttribute("username");
        String feedback = req.getParameter("feedback");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "system",
                "manager"
            );

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO feedback_module VALUES (?,?,?,?)"
            );

            ps.setString(1, "FDB" + System.currentTimeMillis()); // feedback_id
            ps.setString(2, pharmacistUser);                     // username
            ps.setString(3, "Pharmacist");                        // role
            ps.setString(4, feedback);                            // feedback text

            ps.executeUpdate();
            con.close();

            // Success UI
            out.println("<html><head><title>Feedback Submitted</title>");
            out.println("<style>");
            out.println("body{font-family:Arial;background:#e8f5e9;"
                    + "display:flex;justify-content:center;align-items:center;height:100vh;}");
            out.println(".msg{background:white;padding:30px;border-radius:15px;"
                    + "box-shadow:0 0 18px rgba(46,125,50,0.3);text-align:center;}");
            out.println("h3{color:#2e7d32;margin-bottom:20px;}");
            out.println("a{display:inline-block;padding:10px 20px;"
                    + "background:linear-gradient(135deg,#2e7d32,#1b5e20);"
                    + "color:white;text-decoration:none;border-radius:20px;}");
            out.println("a:hover{background:linear-gradient(135deg,#1b5e20,#2e7d32);}");
            out.println("</style></head><body>");

            out.println("<div class='msg'>");
            out.println("<h3>Feedback Submitted Successfully</h3>");
            out.println("<a href='PharmacistDashboardServlet'>Back to Dashboard</a>");
            out.println("</div></body></html>");

        } catch (Exception e) {
            out.println("Error: " + e);
        }
    }
}
