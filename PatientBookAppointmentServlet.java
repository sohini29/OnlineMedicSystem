import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/PatientBookAppointmentServlet")
public class PatientBookAppointmentServlet extends HttpServlet {

    // Handle GET requests to show booking form
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("username") == null){
            res.sendRedirect("patient_login.html");
            return;
        }

        String doctorName = req.getParameter("doctor"); // from URL

        out.println("<html><head><title>Book Appointment</title>");
        out.println("<style>");
        out.println("body { font-family: Arial; background: #e6f0ff; display:flex; justify-content:center; align-items:center; height:100vh; }");
        out.println(".form-box { background:#fff; padding:30px; border-radius:10px; box-shadow:0 0 20px rgba(0,51,102,0.2); width:350px; }");
        out.println("input, button { width:100%; padding:10px; margin:10px 0; border-radius:5px; border:1px solid #99ccff; }");
        out.println("button { background:#3366cc; color:white; border:none; cursor:pointer; }");
        out.println("button:hover { background:#004080; }");
        out.println("</style></head><body>");
        out.println("<div class='form-box'>");
        out.println("<h3>Book Appointment with " + doctorName + "</h3>");
        out.println("<form method='post'>");
        out.println("<input type='hidden' name='dname' value='" + doctorName + "'>");
        out.println("Date: <input type='date' name='date' required>");
        out.println("Time: <input type='time' name='time' required>");
        out.println("<button type='submit'>Book Appointment</button>");
        out.println("</form>");
        out.println("</div></body></html>");
    }

    // Existing POST method to insert into DB
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("username") == null){
            res.sendRedirect("patient_login.html");
            return;
        }

        String patientUser = (String) session.getAttribute("username");

        String aid = "APT" + System.currentTimeMillis(); // AUTOMATIC

        // ✅ Store appointment ID in session for later use in symptoms
        session.setAttribute("appointment_id", aid);

        String doctorName = req.getParameter("dname");
        String date = req.getParameter("date");
        String time = req.getParameter("time");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","system","manager");

            Statement stmt = con.createStatement();

            String sql =
              "INSERT INTO appointments VALUES('" +
               aid + "','" +
               patientUser + "','" +
               doctorName + "','" +
               date + "','" +
               time + "')";

            stmt.executeUpdate(sql);
            con.close();

            out.println("<html><head><title>Appointment Booked</title>");
            out.println("<style>");
            out.println("body { font-family: Arial; background: #cce0ff; display:flex; justify-content:center; align-items:center; height:100vh; }");
            out.println(".message-box { background:#fff; padding:30px; border-radius:10px; box-shadow:0 0 20px rgba(0,51,102,0.2); text-align:center; }");
            out.println(".message-box h3 { color:#003366; margin-bottom:20px; }");
            out.println(".message-box a { display:inline-block; padding:10px 20px; background:#3366cc; color:white; text-decoration:none; border-radius:5px; }");
            out.println(".message-box a:hover { background:#004080; }");
            out.println("</style></head><body>");
            out.println("<div class='message-box'>");
            out.println("<h3>Appointment Booked Successfully</h3>");
            out.println("<a href='PatientDashboard'>Back to Dashboard</a>");
            out.println("</div></body></html>");

        } catch(Exception e){
            out.println(e);
        }
    }
}
