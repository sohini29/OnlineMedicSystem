import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/DoctorSubmitFeedbackServlet")
public class DoctorSubmitFeedbackServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            res.sendRedirect("doctor_login.html");
            return;
        }

        String doctorUsername = (String) session.getAttribute("username");
        String feedback = req.getParameter("feedback");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "system",
                "manager"
            );

            // ✅ FIX: add feedback_date using SYSDATE
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO feedback (feedback_id, role, username, feedback, feedback_date) " +
                "VALUES (?, ?, ?, ?, SYSDATE)"
            );

            ps.setString(1, "FB" + System.currentTimeMillis());
            ps.setString(2, "doctor");
            ps.setString(3, doctorUsername);
            ps.setString(4, feedback);

            ps.executeUpdate();
            con.close();

            out.println("<html><head><title>Feedback Submitted</title>");
            out.println("<style>");
            out.println("body{font-family:Arial;background:#f3eaff;"
                    + "display:flex;justify-content:center;align-items:center;height:100vh;}");
            out.println(".box{background:white;padding:30px;border-radius:12px;"
                    + "box-shadow:0 0 18px rgba(128,0,128,0.3);text-align:center;}");
            out.println("h3{color:#6a1b9a;margin-bottom:20px;}");
            out.println("a{display:inline-block;padding:10px 20px;"
                    + "background:linear-gradient(135deg,#6a1b9a,#8e24aa);"
                    + "color:white;text-decoration:none;border-radius:20px;}");
            out.println("a:hover{background:linear-gradient(135deg,#8e24aa,#6a1b9a);}");
            out.println("</style></head><body>");

            out.println("<div class='box'>");
            out.println("<h3>Feedback Submitted Successfully</h3>");
            out.println("<a href='DoctorDashboardServlet'>Back to Dashboard</a>");
            out.println("</div></body></html>");

        } catch (Exception e) {
            out.println("Error: " + e);
        }
    }
}
