import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/AdminViewMedicineStockServlet")
public class AdminViewMedicineStockServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("username") == null){
            res.sendRedirect("admin_login.html");
            return;
        }

        out.println("<html><head><title>Medicine Stock</title>");
        out.println("<style>");
        out.println("body { font-family: Arial; background: #e8f5e9; }");
        out.println(".container { max-width: 800px; margin: 50px auto; padding: 20px; background: #fff; box-shadow: 0 0 20px rgba(46,125,50,0.2); border-radius: 10px; }");
        out.println("h2 { text-align: center; color: #2e7d32; margin-bottom: 20px; }");
        out.println("table { width: 100%; border-collapse: collapse; }");
        out.println("th, td { padding: 12px; text-align: left; border-bottom: 1px solid #c8e6c9; }");
        out.println("th { background-color: #a5d6a7; }");
        out.println("tr:hover { background-color: #e8f5e9; }");
        out.println(".btn { display: inline-block; margin-top: 20px; padding: 10px 20px; background: #2e7d32; color: white; text-decoration: none; border-radius: 5px; }");
        out.println(".btn:hover { background: #1b5e20; }");
        out.println("</style></head><body>");

        out.println("<div class='container'>");
        out.println("<h2>Medicine Stock</h2>");
        out.println("<table>");
        out.println("<tr><th>Medicine ID</th><th>Medicine Name</th><th>Quantity</th></tr>");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","system","manager");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM medicine_stock ORDER BY medicine_name");

            while(rs.next()) {
                String mid = rs.getString("medicine_id");
                String name = rs.getString("medicine_name");
                String qty = rs.getString("quantity");

                out.println("<tr>");
                out.println("<td>" + mid + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + qty + "</td>");
                out.println("</tr>");
            }

            con.close();

        } catch(Exception e){
            out.println("<tr><td colspan='3'>Error: "+e+"</td></tr>");
        }

        out.println("</table>");
        out.println("<a class='btn' href='AdminDashboardServlet'>Back to Dashboard</a>");
        out.println("</div></body></html>");
    }
}
