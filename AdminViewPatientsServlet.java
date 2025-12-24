import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;
@WebServlet("/AdminViewPatientsServlet")
public class AdminViewPatientsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        out.println("<html><head><title>View Patients</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;background:#eef6fb;}");
        out.println("table{width:90%;margin:auto;border-collapse:collapse;}");
        out.println("th,td{border:1px solid #ccc;padding:10px;text-align:center;}");
        out.println("th{background:#0056b3;color:white;}");
        out.println("</style></head><body>");

        out.println("<h2 align='center'>Registered Patients</h2>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","system","manager");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM patient_module1");

            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Patient ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Age</th>");
            out.println("<th>Gender</th>");
            out.println("<th>Phone</th>");
            out.println("</tr>");

            while(rs.next()){
                out.println("<tr>");
                out.println("<td>"+rs.getString(1)+"</td>");
                out.println("<td>"+rs.getString(2)+"</td>");
                out.println("<td>"+rs.getString(3)+"</td>");
                out.println("<td>"+rs.getString(4)+"</td>");
                out.println("<td>"+rs.getString(6)+"</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            con.close();

        } catch(Exception e){
            out.println(e);
        }

        out.println("<br><center><a href='AdminDashboard'>Back</a></center>");
        out.println("</body></html>");
    }
}
