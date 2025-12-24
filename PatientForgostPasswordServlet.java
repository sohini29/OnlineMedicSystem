import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/PatientForgotPasswordServlet")
public class PatientForgotPasswordServlet extends HttpServlet {

    // Display form to enter username or new password
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html><head><title>Forgot Password</title>");
        out.println("<style>");
        out.println("body{font-family:Arial; background:#e6f0ff; display:flex; justify-content:center; align-items:center; height:100vh;}");
        out.println(".box{background:white; padding:40px; border-radius:12px; box-shadow:0 8px 20px rgba(0,51,102,0.2); width:350px; text-align:center;}");
        out.println("h2{color:#003366; margin-bottom:25px;}");
        out.println("input[type=text], input[type=password]{width:100%; padding:12px; margin:10px 0; border:1px solid #66a3ff; border-radius:6px;}");
        out.println("input[type=submit]{width:100%; padding:12px; background:#3366cc; color:white; border:none; border-radius:6px; cursor:pointer; font-weight:bold;}");
        out.println("input[type=submit]:hover{background:#1a5fd0;}");
        out.println("a{color:#1a5fd0; text-decoration:none; display:inline-block; margin-top:15px;}");
        out.println("a:hover{text-decoration:underline;}");
        out.println("</style></head><body>");

        out.println("<div class='box'>");
        out.println("<h2>Reset Password</h2>");
        out.println("<form method='post'>");
        out.println("Enter Username: <input type='text' name='username' required><br>");
        out.println("Enter New Password: <input type='password' name='newpassword' required><br>");
        out.println("<input type='submit' value='Set New Password'>");
        out.println("</form>");
        out.println("<br><a href='patient_login.html'>Back to Login</a>");
        out.println("</div>");

        out.println("</body></html>");
    }

    // Update the password in the database
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String username = req.getParameter("username");
        String newPassword = req.getParameter("newpassword");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","system","manager");

            // Update password for the given username
            PreparedStatement ps = con.prepareStatement(
                "UPDATE patient_module2 SET password=? WHERE username=?");
            ps.setString(1, newPassword);
            ps.setString(2, username);

            int updated = ps.executeUpdate();

            out.println("<html><head><title>Password Reset</title>");
            out.println("<style>");
            out.println("body{font-family:Arial; background:#e6f0ff; display:flex; justify-content:center; align-items:center; height:100vh;}");
            out.println(".box{background:white; padding:40px; border-radius:12px; box-shadow:0 8px 20px rgba(0,51,102,0.2); width:350px; text-align:center;}");
            out.println("h3{color:#003366; margin-bottom:25px;}");
            out.println("a{color:#1a5fd0; text-decoration:none; display:inline-block; margin-top:15px;}");
            out.println("a:hover{text-decoration:underline;}");
            out.println("</style></head><body>");

            out.println("<div class='box'>");

            if (updated > 0) {
                out.println("<h3>Password updated successfully!</h3>");
            } else {
                out.println("<h3>Username not found!</h3>");
            }

            out.println("<br><a href='patient_login.html'>Back to Login</a>");
            out.println("</div>");
            out.println("</body></html>");

            con.close();
        } catch(Exception e){
            out.println(e);
        }
    }
}
