import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/PatientSearchDoctorServlet")
public class PatientSearchDoctorServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("username") == null){
            res.sendRedirect("patient_login.html");
            return;
        }

        String speciality = req.getParameter("speciality");

        out.println("<html><head><title>Doctors Found</title>");
        out.println("<style>");
        // Blue theme
        out.println("body{font-family:Arial;background:#e6f0ff;}");
        out.println("table{width:90%;margin:auto;border-collapse:collapse;}");
        out.println("th,td{border:1px solid #99ccff;padding:10px;text-align:center;}");
        out.println("th{background:#004080;color:white;}");
        out.println("tr:nth-child(even){background:#cce0ff;}");
        out.println("a{padding:5px 10px;background:#3366cc;color:white;text-decoration:none;border-radius:5px;}");
        out.println("a:hover{background:#004080;}");
        out.println("</style></head><body>");

        out.println("<h2 align='center'>Doctors with Speciality: " + speciality + "</h2>");

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE","system","manager");

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM doctor_module WHERE speciality=?"
            );
            ps.setString(1, speciality);

            ResultSet rs = ps.executeQuery();

            out.println("<table>");
            out.println("<tr><th>Full Name</th><th>Speciality</th><th>Phone</th><th>Email</th><th>Book Appointment</th></tr>");

            while(rs.next()){
                out.println("<tr>");
                out.println("<td>" + rs.getString("full_name") + "</td>");
                out.println("<td>" + rs.getString("speciality") + "</td>");
                out.println("<td>" + rs.getString("phone") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");

                // Book Appointment link
                out.println("<td><a href='PatientBookAppointmentServlet?doctor=" 
                    + rs.getString("username") + "'>Book Appointment</a></td>");

                out.println("</tr>");
            }
            out.println("</table>");
            con.close();

        }catch(Exception e){
            out.println("<center><b>Error:</b> " + e + "</center>");
        }

        out.println("<br><center><a href='PatientDashboard'>Back to Dashboard</a></center>");
        out.println("</body></html>");
    }
}
