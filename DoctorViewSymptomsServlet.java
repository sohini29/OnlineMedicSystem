import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/DoctorViewSymptomsServlet")
public class DoctorViewSymptomsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            res.sendRedirect("doctor_login.html");
            return;
        }

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Patient Symptoms</title>");
        out.println("<style>");

        /* LIGHT PURPLISH THEME */
        out.println("body{font-family:Arial;background:#f3eaff;}");

        out.println("h2{text-align:center;color:#6a1b9a;margin-bottom:20px;}");

        out.println("table{width:90%;margin:auto;border-collapse:collapse;"
                + "background:#ffffff;box-shadow:0 0 18px rgba(128,0,128,0.25);"
                + "border-radius:12px;overflow:hidden;}");

        out.println("th,td{border:1px solid #d9c6eb;padding:12px;text-align:center;}");

        out.println("th{background:linear-gradient(135deg,#6a1b9a,#8e24aa);color:white;}");

        out.println("tr:nth-child(even){background:#f6efff;}");

        out.println(".btn{padding:8px 18px;"
                + "background:linear-gradient(135deg,#6a1b9a,#8e24aa);"
                + "color:white;border:none;border-radius:20px;"
                + "cursor:pointer;}");

        out.println(".btn:hover{background:linear-gradient(135deg,#8e24aa,#6a1b9a);}");        

        out.println("a{color:#6a1b9a;text-decoration:none;font-weight:bold;}");

        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<h2>Patient Symptoms Submitted</h2>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "system",
                "manager"
            );

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM patient_symptoms"
            );

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Symptoms ID</th>");
            out.println("<th>Patient Username</th>");
            out.println("<th>Symptoms</th>");
            out.println("<th>Action</th>");
            out.println("</tr>");

            while (rs.next()) {

                String patientUsername = rs.getString("patient_username");

                out.println("<tr>");
                out.println("<td>" + rs.getString("symptoms_id") + "</td>");
                out.println("<td>" + patientUsername + "</td>");
                out.println("<td>" + rs.getString("symptoms") + "</td>");

                out.println("<td>");
                out.println("<form action='DoctorWritePrescriptionServlet' method='get'>");
                out.println("<input type='hidden' name='patient' value='" + patientUsername + "'>");
                out.println("<button type='submit' class='btn'>Write Prescription</button>");
                out.println("</form>");
                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</table>");
            con.close();

        } catch (Exception e) {
            out.println("<center><b>Error:</b> " + e + "</center>");
        }

        out.println("<br><center><a href='DoctorDashboardServlet'>Back to Dashboard</a></center>");
        out.println("</body></html>");
    }
}
