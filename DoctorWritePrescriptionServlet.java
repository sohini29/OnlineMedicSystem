import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;

@WebServlet("/DoctorWritePrescriptionServlet")
public class DoctorWritePrescriptionServlet extends HttpServlet {

    // ===================== GET =====================
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            res.sendRedirect("doctor_login.html");
            return;
        }

        String patient = req.getParameter("patient");

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Write Prescription</title>");

        // ===== LIGHT PURPLISH CSS =====
        out.println("<style>");
        out.println("body{font-family:Arial;background:#f3eaff;margin:0;padding:0;}");

        out.println(".container{max-width:450px;margin:70px auto;background:#ffffff;");
        out.println("padding:35px;box-shadow:0 0 25px rgba(128,0,128,0.3);");
        out.println("border-radius:15px;text-align:center;}");

        out.println("h2{color:#6a1b9a;margin-bottom:25px;}");

        out.println("input{width:100%;padding:12px;margin:10px 0;");
        out.println("border:1px solid #d1b3ff;border-radius:8px;");
        out.println("font-size:15px;background:#faf5ff;}");

        out.println("input:focus{outline:none;border-color:#8e24aa;}");

        out.println("button{width:100%;padding:12px;margin-top:18px;");
        out.println("background:linear-gradient(135deg,#6a1b9a,#8e24aa);");
        out.println("color:white;border:none;font-size:16px;");
        out.println("border-radius:25px;cursor:pointer;");
        out.println("box-shadow:0 4px 10px rgba(128,0,128,0.35);}");

        out.println("button:hover{background:linear-gradient(135deg,#8e24aa,#6a1b9a);}");        

        out.println("</style>");
        out.println("</head>");

        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h2>Write Prescription</h2>");

        out.println("<form method='post'>");
        out.println("<input type='hidden' name='patient' value='" + patient + "'>");
        out.println("<input type='text' name='medicine' placeholder='Medicine Name' required>");
        out.println("<input type='text' name='dosage' placeholder='Dosage (e.g. 2 times a day)' required>");
        out.println("<button type='submit'>Save Prescription</button>");
        out.println("</form>");

        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    // ===================== POST =====================
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String doctor = (String) req.getSession().getAttribute("username");
        String patient = req.getParameter("patient");
        String medicine = req.getParameter("medicine");
        String dosage = req.getParameter("dosage");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "system",
                "manager"
            );

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO prescriptions VALUES (?,?,?,?,?,?)"
            );

            ps.setString(1, "PRESC_" + System.currentTimeMillis());
            ps.setString(2, patient);
            ps.setString(3, doctor);
            ps.setString(4, medicine);
            ps.setString(5, dosage);

            java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("dd-MM-yyyy");
            ps.setString(6, sdf.format(new java.util.Date()));

            ps.executeUpdate();
            con.close();

            res.sendRedirect("DoctorViewPrescriptionsServlet");

        } catch (Exception e) {
            res.getWriter().println(e);
        }
    }
}
