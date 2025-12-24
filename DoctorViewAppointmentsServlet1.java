import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/DoctorViewAppointmentsServlet1")
public class DoctorViewAppointmentsServlet1 extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            res.sendRedirect("doctor_login.html");
            return;
        }

        String doctorUser = (String) session.getAttribute("username");

        out.println("<html><head><title>Doctor Appointments</title>");
        out.println("<style>");

        /* LIGHT PURPLISH THEME */
        out.println("body{font-family:Arial;background:#f3eaff;}");

        out.println("h2{color:#6a1b9a;}");

        out.println("table{width:90%;margin:auto;border-collapse:collapse;"
                + "background:#ffffff;box-shadow:0 0 18px rgba(128,0,128,0.25);"
                + "border-radius:12px;overflow:hidden;}");

        out.println("th,td{padding:12px;border:1px solid #d9c6eb;text-align:center;}");

        out.println("th{background:linear-gradient(135deg,#6a1b9a,#8e24aa);color:white;}");

        out.println("tr:nth-child(even){background:#f6efff;}");

        out.println("a{padding:8px 16px;background:linear-gradient(135deg,#6a1b9a,#8e24aa);"
                + "color:white;text-decoration:none;border-radius:20px;"
                + "display:inline-block;}");

        out.println("a:hover{background:linear-gradient(135deg,#8e24aa,#6a1b9a);}");        

        out.println("</style></head><body>");

        out.println("<h2 align='center'>My Appointments</h2>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "system",
                "manager"
            );

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM appointments WHERE doctor_name='" + doctorUser + "'"
            );

            out.println("<table>");
            out.println("<tr><th>ID</th><th>Patient</th><th>Date</th><th>Time</th><th>Action</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("appointment_id") + "</td>");
                out.println("<td>" + rs.getString("patient_username") + "</td>");
                out.println("<td>" + rs.getString("app_date") + "</td>");
                out.println("<td>" + rs.getString("app_time") + "</td>");

                out.println("<td>");
                out.println("<a href='DoctorViewSymptomsServlet?patient="
                        + rs.getString("patient_username") + "'>");
                out.println("Accept / View Symptoms</a>");
                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</table>");
            con.close();

        } catch (Exception e) {
            out.println(e);
        }

        out.println("<br><center>");
        out.println("<a href='DoctorDashboardServlet'>Back</a>");
        out.println("</center>");

        out.println("</body></html>");
    }
}
