import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;

@WebServlet("/PatientPaymentServlet")
public class PatientPaymentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("username") == null){
            res.sendRedirect("patient_login.html");
            return;
        }

        String patient = (String) session.getAttribute("username");
        String doctor = req.getParameter("doctor");
        String pharmacist = req.getParameter("pharmacist");
        String docAmt = req.getParameter("doctorAmount");
        String pharAmt = req.getParameter("pharmaAmount");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","system","manager");

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO payments VALUES (?,?,?,?,?,?,?)");

            ps.setString(1, "PAY" + System.currentTimeMillis());
            ps.setString(2, patient);
            ps.setString(3, doctor);
            ps.setString(4, pharmacist);
            ps.setString(5, docAmt);
            ps.setString(6, pharAmt);

            java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("dd-MM-yyyy");
            ps.setString(7, sdf.format(new java.util.Date()));

            ps.executeUpdate();
            con.close();

            res.sendRedirect("PatientDashboard");

        } catch(Exception e){
            res.getWriter().println(e);
        }
    }
}
