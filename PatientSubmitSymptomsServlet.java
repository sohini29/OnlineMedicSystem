import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/PatientSubmitSymptomsServlet")
public class PatientSubmitSymptomsServlet extends HttpServlet {

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

        out.println("<html><head><title>Submit Symptoms</title>");
        out.println("<style>");

        out.println("body{font-family:Arial;background:#e6f0ff;}");
        out.println(".box{width:420px;margin:60px auto;background:white;padding:30px;"
                + "box-shadow:0 0 20px rgba(0,102,204,0.25);border-radius:15px;}");
        out.println("h2{text-align:center;color:#004080;margin-bottom:15px;}");
        out.println("textarea{width:100%;padding:12px;margin:10px 0;"
                + "border-radius:10px;border:1px solid #66a3ff;resize:none;height:120px;}");
        out.println("button{width:100%;padding:12px;margin-top:10px;"
                + "background:linear-gradient(135deg,#004080,#3399ff);"
                + "color:white;border:none;border-radius:25px;cursor:pointer;"
                + "font-size:16px;}");
        out.println("button:hover{background:linear-gradient(135deg,#3399ff,#004080);}");        

        out.println("</style></head><body>");

        out.println("<div class='box'>");
        out.println("<h2>Submit Symptoms</h2>");
        out.println("<form method='post'>");
        out.println("<textarea name='symptoms' placeholder='Enter your symptoms...' required></textarea>");
        out.println("<button type='submit'>Submit</button>");
        out.println("</form>");
        out.println("</div></body></html>");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        String patientUser = (String) session.getAttribute("username");
        String symptoms = req.getParameter("symptoms");

        // ✅ ONLY NEW LINE ADDED
        String appointmentId = (String) session.getAttribute("appointment_id");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            // ✅ INSERT QUERY UPDATED ONLY TO ADD appointment_id
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO patient_symptoms(symptoms_id, appointment_id, patient_username, symptoms) VALUES(?,?,?,?)");

            ps.setString(1, "SYM" + System.currentTimeMillis());
            ps.setString(2, appointmentId);      // ✅ appointment_id
            ps.setString(3, patientUser);
            ps.setString(4, symptoms);

            ps.executeUpdate();
            con.close();

            res.sendRedirect("PatientDashboard");

        } catch(Exception e){
            res.getWriter().println(e);
        }
    }
}
