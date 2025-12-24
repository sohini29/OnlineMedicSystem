import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;@WebServlet("/AdminViewPharmacistsServlet")
public class AdminViewPharmacistsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html><head><title>View Pharmacists</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;background:#fff3e0;}");
        out.println("table{width:90%;margin:auto;border-collapse:collapse;}");
        out.println("th,td{border:1px solid #ccc;padding:10px;text-align:center;}");
        out.println("th{background:#ef6c00;color:white;}");
        out.println("</style></head><body>");

        out.println("<h2 align='center'>Registered Pharmacists</h2>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","system","manager");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM pharmacist_module");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Phone</th>");
            out.println("<th>Email</th>");
            out.println("</tr>");

            while(rs.next()){
                out.println("<tr>");
                out.println("<td>"+rs.getString("pharmacist_id")+"</td>");
                out.println("<td>"+rs.getString("full_name")+"</td>");
                out.println("<td>"+rs.getString("phone")+"</td>");
                out.println("<td>"+rs.getString("email")+"</td>");
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
