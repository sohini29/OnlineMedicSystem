import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/PatientRegisterServlet")
public class PatientRegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String full_name = req.getParameter("full_name");
        String age = req.getParameter("age");
        String gender = req.getParameter("gender");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // ✅ patient_id generation
        String patientId = "PAT" + System.currentTimeMillis();

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "system",
                "manager"
            );

            Statement st = con.createStatement();

            String sql =
                "INSERT INTO patient_module1 VALUES(" +
                "'" + patientId + "'," +   // patient_id
                "'" + full_name + "'," +
                "'" + age + "'," +
                "'" + gender + "'," +
                "'" + address + "'," +
                "'" + phone + "'," +
                "'" + email + "'," +
                "'" + username + "'," +    // username
                "'" + password + "'" +
                ")";

            int i = st.executeUpdate(sql);

            if (i > 0) {
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                res.sendRedirect("PatientDashboard");
            } else {
                out.println("Registration Failed");
            }

            con.close();

        } catch (Exception e) {
            out.println(e);
        }
    }
}
