import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/DoctorRegisterServlet")
public class DoctorRegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String fullname = req.getParameter("fullname");
        String speciality = req.getParameter("speciality");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            Statement stmt = con.createStatement();
            String sql = "INSERT INTO doctor_module (doctor_id, full_name, speciality, phone, email, username, password) " +
                         "VALUES (doctor_seq.NEXTVAL, '" + fullname + "','" + speciality + "','" + phone + "','" + email + "','" + username + "','" + password + "')";
            int x = stmt.executeUpdate(sql);

            if (x > 0) {
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                res.sendRedirect("DoctorDashboardServlet");
            } else {
                out.println("Registration Failed");
            }
            con.close();
        } catch(Exception e) {
            out.println("Error: " + e);
        }
    }
}
