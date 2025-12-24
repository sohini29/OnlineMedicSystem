import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/PatientProfileServlet")
public class PatientProfileServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        String username = "";
        if (session != null && session.getAttribute("username") != null) {
            username = (String) session.getAttribute("username");
        } else {
            res.sendRedirect("patient_login.html");
            return;
        }

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE",
                    "system",
                    "manager"
            );

            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM patient_module1 WHERE username='" + username + "'";
            ResultSet rs = stmt.executeQuery(sql);

            out.println("<html><head><title>Patient Profile</title>");
            out.println("<style>");
            out.println("body { font-family: Arial; background:#e6f0ff; margin:0; padding:0; }");
            out.println(".profile-container { max-width:700px; margin:50px auto; padding:30px; background:#fff; box-shadow:0 0 20px rgba(0,0,128,0.2); border-radius:12px; }");
            out.println("h2 { color:#003366; text-align:center; margin-bottom:20px; }");
            out.println("p { font-size:16px; margin:10px 0; color:#004080; }");
            out.println(".btn-back { display:inline-block; margin-top:20px; padding:10px 20px; background:#0066cc; color:white; text-decoration:none; border-radius:6px; }");
            out.println(".btn-back:hover { background:#3399ff; }");
            out.println("</style></head><body>");

            out.println("<div class='profile-container'>");
            out.println("<h2>Patient Profile</h2>");

            if (rs.next()) {
                out.println("<p><b>Full Name:</b> " + rs.getString("full_name") + "</p>");
                out.println("<p><b>Age:</b> " + rs.getInt("age") + "</p>");
                out.println("<p><b>Gender:</b> " + rs.getString("gender") + "</p>");
                out.println("<p><b>Address:</b> " + rs.getString("address") + "</p>");
                out.println("<p><b>Phone:</b> " + rs.getString("phone") + "</p>");
                out.println("<p><b>Email:</b> " + rs.getString("email") + "</p>");
                out.println("<p><b>Username:</b> " + rs.getString("username") + "</p>");
            } else {
                out.println("<p>Profile not found.</p>");
            }

            out.println("<br><a class='btn-back' href='PatientDashboardServlet'>Back to Dashboard</a>");
            out.println("</div></body></html>");

            con.close();
        } catch (Exception e) {
            out.println(e);
        }
    }
}
