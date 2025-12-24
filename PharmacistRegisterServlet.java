import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/PharmacistRegisterServlet")
public class PharmacistRegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String full_name = req.getParameter("full_name");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            Statement st = con.createStatement();

            // Generate pharmacist ID dynamically
            String pharmacistId = "PHAR_" + System.currentTimeMillis();

            String sql = "INSERT INTO pharmacist_module(pharmacist_id, full_name, phone, email, username, password) VALUES("
                    + "'" + pharmacistId + "','" + full_name + "','" + phone + "','" + email + "','" + username + "','" + password + "')";

            int i = st.executeUpdate(sql);

            if (i > 0) {
                out.println("<html><body>");
                out.println("<h3>Registration Successful!</h3>");
                out.println("<a href='pharmacist_login.html'>Go to Login</a>");
                out.println("</body></html>");
            } else {
                out.println("<h3>Registration Failed!</h3>");
            }

            con.close();

        } catch (Exception e) {
            out.println("Error: " + e);
        }
    }
}
