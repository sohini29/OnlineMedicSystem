import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/AdminViewPaymentsServlet")
public class AdminViewPaymentsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html><head><title>View Payments</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;background:#e8f5e9;}");
        out.println("table{width:90%;margin:auto;border-collapse:collapse;}");
        out.println("th,td{border:1px solid #ccc;padding:10px;text-align:center;}");
        out.println("th{background:#2e7d32;color:white;}");
        out.println("</style></head><body>");

        out.println("<h2 align='center'>Payment Records</h2>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","system","manager");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM payments");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Payment ID</th>");
            out.println("<th>Patient</th>");
            out.println("<th>Amount</th>");
            out.println("<th>Purpose</th>");
            out.println("</tr>");

            while(rs.next()){
                out.println("<tr>");
                out.println("<td>"+rs.getString(1)+"</td>");
                out.println("<td>"+rs.getString(2)+"</td>");
                out.println("<td>"+rs.getString(3)+"</td>");
                out.println("<td>"+rs.getString(4)+"</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            con.close();

        } catch(Exception e){
            out.println(e);
        }

        out.println("<br><center><a href='AdminDashboardServlet'>Back</a></center>");
        out.println("</body></html>");
    }
}
