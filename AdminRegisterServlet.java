import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/AdminRegisterServlet")
public class AdminRegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String full_Name = req.getParameter("full_name");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "system",
                "manager"
            );

            Statement stmt = con.createStatement();
            String sql = "INSERT INTO admin_module (admin_id, full_name, phone, email, username, password) " +
                         "VALUES ('ADM" + System.currentTimeMillis() + "', '" +
                         full_Name + "','" + phone + "','" + email + "','" + username + "','" + password + "')";
            
            int x = stmt.executeUpdate(sql);

            if (x > 0) {
                // ✅ Create session and redirect to dashboard like DoctorRegisterServlet
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                res.sendRedirect("AdminDashboardServlet");
            } else {
                out.println("Admin Registration Failed");
            }

            con.close();

        } catch (Exception e) {
            out.println("Error: " + e);
        }
    }
}
