import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/PatientLoginServlet")
public class PatientLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "system",
                "manager"
            );

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT * FROM patient_module1 WHERE username='" +
                username + "' AND password='" + password + "'"
            );

            if (rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                res.sendRedirect("PatientDashboard");
            } else {
                res.getWriter().println("Invalid Username or Password");
            }

            con.close();

        } catch (Exception e) {
            res.getWriter().println(e);
        }
    }
}
