import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/DoctorForgotPasswordServlet")
public class DoctorForgotPasswordServlet extends HttpServlet {

    // Show reset password form
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html><head><title>Doctor Forgot Password</title>");
        out.println("<style>");
        out.println("body{font-family:Arial; background:#f3e5f5; display:flex; justify-content:center; align-items:center; height:100vh;}");
        out.println(".box{background:white; padding:40px; border-radius:14px; width:360px;");
        out.println("box-shadow:0 8px 22px rgba(128,0,128,0.25); text-align:center;}");
        out.println("h2{color:#6a1b9a; margin-bottom:25px;}");
        out.println("input[type=text], input[type=password]{width:100%; padding:12px; margin:12px 0;");
        out.println("border:1px solid #ce93d8; border-radius:8px;}");
        out.println("input[type=submit]{width:100%; padding:12px; background:#8e24aa; color:white;");
        out.println("border:none; border-radius:25px; cursor:pointer; font-weight:bold;}");
        out.println("input[type=submit]:hover{background:#6a1b9a;}");
        out.println("a{color:#6a1b9a; text-decoration:none; display:inline-block; margin-top:15px;}");
        out.println("a:hover{text-decoration:underline;}");
        out.println("</style></head><body>");

        out.println("<div class='box'>");
        out.println("<h2>Reset Doctor Password</h2>");
        out.println("<form method='post'>");
        out.println("Username:<input type='text' name='username' required>");
        out.println("New Password:<input type='password' name='newpassword' required>");
        out.println("<input type='submit' value='Set New Password'>");
        out.println("</form>");
        out.println("<a href='doctor_login.html'>Back to Login</a>");
        out.println("</div>");

        out.println("</body></html>");
    }

    // Update password
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

            PreparedStatement ps = con.prepareStatement(
                "UPDATE doctor_module SET password=? WHERE username=?");

            ps.setString(1, newPassword);
            ps.setString(2, username);

            int updated = ps.executeUpdate();

            out.println("<html><head><title>Password Updated</title>");
            out.println("<style>");
            out.println("body{font-family:Arial; background:#f3e5f5; display:flex; justify-content:center; align-items:center; height:100vh;}");
            out.println(".box{background:white; padding:40px; border-radius:14px; width:360px;");
            out.println("box-shadow:0 8px 22px rgba(128,0,128,0.25); text-align:center;}");
            out.println("h3{color:#6a1b9a;}");
            out.println("a{color:#6a1b9a; text-decoration:none; display:inline-block; margin-top:15px;}");
            out.println("a:hover{text-decoration:underline;}");
            out.println("</style></head><body>");

            out.println("<div class='box'>");

            if (updated > 0) {
                out.println("<h3>Password updated successfully!</h3>");
            } else {
                out.println("<h3>Username not found!</h3>");
            }

            out.println("<a href='doctor_login.html'>Back to Login</a>");
            out.println("</div>");

            out.println("</body></html>");

            con.close();
        } catch(Exception e){
            out.println(e);
        }
    }
}
