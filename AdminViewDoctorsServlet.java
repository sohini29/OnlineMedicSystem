import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/AdminViewDoctorsServlet")
public class AdminViewDoctorsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html><head><title>View Doctors</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;background:#f4f6f8;}");
        out.println("table{width:90%;margin:auto;border-collapse:collapse;}");
        out.println("th,td{border:1px solid #ccc;padding:10px;text-align:center;}");
        out.println("th{background:#2c3e50;color:white;}");
        out.println("</style></head><body>");

        out.println("<h2 align='center'>Registered Doctors</h2>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","system","manager");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM doctor_module");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Doctor ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Speciality</th>");
            out.println("<th>Phone</th>");
            out.println("<th>Email</th>");
            out.println("</tr>");

            while(rs.next()){
                out.println("<tr>");
                out.println("<td>"+rs.getString("doctor_id")+"</td>");
                out.println("<td>"+rs.getString("full_name")+"</td>");
                out.println("<td>"+rs.getString("speciality")+"</td>");
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
