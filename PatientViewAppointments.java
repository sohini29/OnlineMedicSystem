import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/PatientViewAppointmentsServlet")
public class PatientViewAppointments extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("username") == null){
            res.sendRedirect("patient_login.html");
            return;
        }

        String patientUser = (String) session.getAttribute("username");

        out.println("<html><head><title>My Appointments</title>");
        out.println("<style>");

        /* BLUISH THEME */
        out.println("body{font-family:Arial;background:#e6f0ff;margin:0;padding:0;}");
        out.println("h2{text-align:center;color:#003366;margin:30px 0;}");

        out.println("table{width:90%;margin:auto;border-collapse:collapse;"
                + "background:white;box-shadow:0 8px 20px rgba(0,51,102,0.2);"
                + "border-radius:10px;overflow:hidden;}");

        out.println("th,td{padding:14px;border:1px solid #cce0ff;text-align:center;}");

        out.println("th{background:#3366cc;color:white;font-size:16px;}");

        out.println("tr:nth-child(even){background:#f2f7ff;}");

        out.println("a{display:inline-block;margin-top:25px;padding:10px 25px;"
                + "background:#3366cc;color:white;text-decoration:none;"
                + "border-radius:6px;font-weight:bold;}");

        out.println("a:hover{background:#1a5fd0;}");

        out.println("</style></head><body>");

        out.println("<h2>My Appointments</h2>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","system","manager");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM appointments WHERE patient_username='" + patientUser + "'");

            out.println("<table>");
            out.println("<tr><th>ID</th><th>Doctor</th><th>Date</th><th>Time</th></tr>");

            while(rs.next()){
                out.println("<tr>");
                out.println("<td>"+rs.getString("appointment_id")+"</td>");
                out.println("<td>"+rs.getString("doctor_name")+"</td>");
                out.println("<td>"+rs.getString("app_date")+"</td>");
                out.println("<td>"+rs.getString("app_time")+"</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            con.close();

        } catch(Exception e){
            out.println(e);
        }

        out.println("<center><a href='PatientDashboard'>Back</a></center>");
        out.println("</body></html>");
    }
}
