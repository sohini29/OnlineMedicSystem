import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/DoctorLoginServlet")
public class DoctorLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM doctor_module WHERE username='" + username + "' AND password='" + password + "'");
            if(rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                res.sendRedirect("DoctorDashboardServlet");
            } else {
                out.println("<html><body>");
                out.println("<h3>Login Failed. Invalid Username or Password.</h3>");
                out.println("<a href='doctor_login.html'>Try Again</a>");
                out.println("</body></html>");
            }
            con.close();
        } catch(Exception e) {
            out.println("Error: " + e);
        }
    }
}
