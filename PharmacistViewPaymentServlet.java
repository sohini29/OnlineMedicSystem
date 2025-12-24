import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/PharmacistViewPaymentsServlet")
public class PharmacistViewPaymentServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("username") == null){
            res.sendRedirect("pharmacist_login.html");
            return;
        }

        out.println("<html><head><title>View Payments</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;background:#fff8f0;}");
        out.println("h2{text-align:center;color:#ff6600;margin-top:30px;}");
        out.println("table{width:90%;margin:20px auto;border-collapse:collapse;}");
        out.println("th,td{border:1px solid #ccc;padding:10px;text-align:center;}");
        out.println("th{background:#ff6600;color:white;}");
        out.println("tr:nth-child(even){background:#fff2e6;}");
        out.println(".btn{display:inline-block;padding:6px 12px;background:#ff6600;color:white;text-decoration:none;border-radius:5px;margin-top:15px;}");
        out.println(".btn:hover{background:#ff9933;}");
        out.println("</style></head><body>");

        out.println("<h2>Payment Records</h2>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM payments ORDER BY payment_id");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Payment ID</th>");
            out.println("<th>Patient Username</th>");
            out.println("<th>Doctor Name</th>");
            out.println("<th>Pharmacist Name</th>");
            out.println("<th>Amount</th>");
            out.println("</tr>");

            while(rs.next()){
                out.println("<tr>");
                out.println("<td>" + rs.getString("payment_id") + "</td>");
                out.println("<td>" + rs.getString("patient_username") + "</td>");
                out.println("<td>" + rs.getString("doctor_username") + "</td>");
                out.println("<td>" + rs.getString("pharmacist_username") + "</td>");
                out.println("<td>" + rs.getString("pharmacist_amount") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            con.close();

        } catch(Exception e){
            out.println("<center><b>Error:</b> " + e + "</center>");
        }

        out.println("<center><a href='PharmacistDashboardServlet' class='btn'>Back to Dashboard</a></center>");
        out.println("</body></html>");
    }
}
