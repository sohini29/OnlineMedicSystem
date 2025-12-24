import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/PatientFeedbackServlet")
public class PatientFeedbackServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            res.sendRedirect("patient_login.html");
            return;
        }

        out.println("<html><head><title>Patient Feedback</title>");

        /* BLUE PATIENT THEME */
        out.println("<style>");
        out.println("body{font-family:Arial;background:#e6f0ff;}");
        out.println(".box{width:420px;margin:80px auto;background:white;padding:30px;"
                + "border-radius:14px;box-shadow:0 0 20px rgba(0,102,204,0.3);}");
        out.println("h2{text-align:center;color:#004080;margin-bottom:15px;}");
        out.println("textarea{width:100%;height:120px;padding:12px;border-radius:8px;"
                + "border:1px solid #66a3ff;resize:none;}");
        out.println("button{width:100%;padding:12px;margin-top:15px;"
                + "background:linear-gradient(135deg,#004080,#3399ff);"
                + "color:white;border:none;border-radius:25px;font-size:16px;cursor:pointer;}");
        out.println("button:hover{background:linear-gradient(135deg,#3399ff,#004080);}");
        out.println("</style></head><body>");

        out.println("<div class='box'>");
        out.println("<h2>Submit Feedback</h2>");
        out.println("<form method='post'>");
        out.println("<textarea name='feedback' placeholder='Write your feedback here...' required></textarea>");
        out.println("<button type='submit'>Submit Feedback</button>");
        out.println("</form>");
        out.println("</div></body></html>");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        String username = (String) session.getAttribute("username");
        String feedback = req.getParameter("feedback");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE",
                    "system",
                    "manager"
            );

            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO feedback VALUES(?,?,?,?,SYSDATE)"
            );

            ps.setString(1, "FB" + System.currentTimeMillis());
            ps.setString(2, "Patient");
            ps.setString(3, username);
            ps.setString(4, feedback);

            ps.executeUpdate();
            con.close();

            /* SUCCESS PAGE */
            out.println("<html><head><title>Feedback Submitted</title>");
            out.println("<style>");
            out.println("body{font-family:Arial;background:#e6f0ff;display:flex;"
                    + "justify-content:center;align-items:center;height:100vh;}");
            out.println(".msg{background:white;padding:30px;border-radius:14px;"
                    + "box-shadow:0 0 20px rgba(0,102,204,0.3);text-align:center;}");
            out.println("h3{color:#004080;}");
            out.println("a{display:inline-block;margin-top:15px;padding:10px 22px;"
                    + "background:#004080;color:white;text-decoration:none;"
                    + "border-radius:25px;}");
            out.println("a:hover{background:#3399ff;}");
            out.println("</style></head><body>");

            out.println("<div class='msg'>");
            out.println("<h3>Feedback Submitted Successfully</h3>");
            out.println("<a href='PatientDashboard'>Back to Dashboard</a>");
            out.println("</div></body></html>");

        } catch (Exception e) {
            out.println(e);
        }
    }
}
