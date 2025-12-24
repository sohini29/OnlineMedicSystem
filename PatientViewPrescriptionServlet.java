import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;

@WebServlet("/PatientViewPrescriptionServlet")
public class PatientViewPrescriptionServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Session check
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            res.sendRedirect("patient_login.html");
            return;
        }

        // Logged in patient username
        String patientUser = (String) session.getAttribute("username");

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>My Prescriptions</title>");

        // Styling
        out.println("<style>");
        out.println("body{font-family:Arial;background:#e6f0ff;}"); // light bluish
        out.println("h2{text-align:center;color:#004080;padding-top:20px;}"); // dark blue heading
        out.println("table{width:90%;margin:auto;border-collapse:collapse;padding:15px;}");
        out.println("th,td{border:1px solid #ccc;padding:12px;text-align:center;}");
        out.println("th{background:#004080;color:white;}");
        out.println("tr:nth-child(even){background:#cce0ff;}"); // lighter blue for even rows
        out.println("a{display:inline-block;margin-top:20px;padding:10px 20px;");
        out.println("background:#004080;color:white;text-decoration:none;border-radius:6px;}");
        out.println("a:hover{background:#3399ff;}"); // hover lighter blue
        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<h2>My Prescriptions</h2>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "system",
                "manager"
            );

            Statement stmt = con.createStatement();

            // IMPORTANT: patient_username must match session username
            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM prescriptions WHERE patient_username='" + patientUser + "'"
            );

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Prescription ID</th>");
            out.println("<th>Doctor Name</th>");
            out.println("<th>Medicine</th>");
            out.println("<th>Dosage</th>");
            out.println("<th>Date</th>");
            out.println("</tr>");

            boolean found = false;

            while (rs.next()) {
                found = true;
                out.println("<tr>");
                out.println("<td>" + rs.getString("presc_id") + "</td>");
                out.println("<td>" + rs.getString("doctor_name") + "</td>");
                out.println("<td>" + rs.getString("medicine") + "</td>");
                out.println("<td>" + rs.getString("dosage") + "</td>");
                out.println("<td>" + rs.getString("presc_date") + "</td>");
                out.println("</tr>");
            }

            // If no data found
            if (!found) {
                out.println("<tr>");
                out.println("<td colspan='5'>No prescriptions found</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            con.close();

        } catch (Exception e) {
            out.println("<center><b>Error:</b> " + e + "</center>");
        }

        out.println("<br><center>");
        out.println("<a href='PatientDashboard'>Back to Dashboard</a>");
        out.println("</center>");

        out.println("</body>");
        out.println("</html>");
    }
}
