import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;
import java.time.LocalDate;

@WebServlet("/SubmitFeedbackServlet")
public class SubmitFeedbackServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("username") == null || session.getAttribute("role") == null){
            res.sendRedirect("login.html"); // generic login page
            return;
        }

        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role"); // "Patient", "Doctor", "Pharmacist"
        String feedbackText = req.getParameter("feedback");

        String feedbackId = "FDB" + System.currentTimeMillis();
        LocalDate date = LocalDate.now();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","system","manager");

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO feedback(feedback_id, user_type, username, feedback_text, feedback_date) VALUES(?,?,?,?,?)"
            );
            ps.setString(1, feedbackId);
            ps.setString(2, role);
            ps.setString(3, username);
            ps.setString(4, feedbackText);
            ps.setDate(5, java.sql.Date.valueOf(date));

            int i = ps.executeUpdate();
            con.close();

            if(i > 0){
                out.println("<html><body><h3>Feedback Submitted Successfully!</h3>");
                out.println("<a href='" + role + "DashboardServlet'>Back to Dashboard</a></body></html>");
            } else {
                out.println("Feedback Submission Failed!");
            }

        } catch(Exception e){
            out.println(e);
        }
    }
}
