import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/AcceptAppointmentServlet")
public class AcceptAppointmentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String appointmentId = req.getParameter("aid");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            Statement stmt = con.createStatement();

            // You can also ADD a status column later
            stmt.executeUpdate(
                "DELETE FROM appointments WHERE appointment_id='" + appointmentId + "'");

            con.close();
            res.sendRedirect("DoctorViewAppointmentsServlet1");

        } catch (Exception e) {
            PrintWriter out = res.getWriter();
            out.println(e);
        }
    }
}
