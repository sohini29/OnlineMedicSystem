import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/PharmacistUpdateStockServlet")
public class PharmacistUpdateStockServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if(session == null || session.getAttribute("username") == null){
            res.sendRedirect("pharmacist_login.html");
            return;
        }

        out.println("<html><head><title>Update Medicine Stock</title>");
        out.println("<style>");
        out.println("body{font-family:Arial;background:#fff0e6;}");
        out.println("h2{text-align:center;color:#ff6600;margin-top:30px;}");
        out.println(".form-box{width:400px;margin:20px auto;padding:20px;background:#fff;border-radius:10px;box-shadow:0 0 15px rgba(255,102,0,0.3);}");
        out.println("input[type=text]{width:90%;padding:8px;margin:5px 0;border-radius:5px;border:1px solid #ff9933;}");
        out.println("button{padding:8px 15px;background:#ff6600;color:white;border:none;border-radius:5px;cursor:pointer;}");
        out.println("button:hover{background:#ff9933;}");
        out.println("table{width:80%;margin:20px auto;border-collapse:collapse;}");
        out.println("th,td{border:1px solid #ccc;padding:10px;text-align:center;}");
        out.println("th{background:#ff6600;color:white;}");
        out.println("tr:nth-child(even){background:#ffe6cc;}");
        out.println(".btn{padding:6px 12px;background:#ff6600;color:white;text-decoration:none;border-radius:5px;}");
        out.println(".btn:hover{background:#ff9933;}");
        out.println("</style></head><body>");

        out.println("<h2>Add New Medicine</h2>");
        out.println("<div class='form-box'>");
        out.println("<form method='post'>");
        out.println("Medicine Name: <input type='text' name='medicine_name' required><br>");
        out.println("Quantity: <input type='text' name='quantity' required><br>");
        out.println("<button type='submit'>Add Medicine</button>");
        out.println("</form>");
        out.println("</div>");

        // Display all medicines in table
        out.println("<h2>Medicine Stock List</h2>");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM medicine_stock ORDER BY medicine_id");

            out.println("<table>");
            out.println("<tr><th>Medicine ID</th><th>Medicine Name</th><th>Quantity</th></tr>");

            while(rs.next()){
                String mid = rs.getString("medicine_id");
                String mname = rs.getString("medicine_name");
                String qty = rs.getString("quantity");

                out.println("<tr>");
                out.println("<td>" + mid + "</td>");
                out.println("<td>" + mname + "</td>");
                out.println("<td>" + qty + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            con.close();

        } catch(Exception e){
            out.println("<center><b>Error:</b> " + e + "</center>");
        }

        out.println("<br><center><a href='PharmacistDashboardServlet' class='btn'>Back to Dashboard</a></center>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String medicineName = req.getParameter("medicine_name");
        String quantity = req.getParameter("quantity");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:XE", "system", "manager");

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO medicine_stock(medicine_id, medicine_name, quantity) VALUES(?, ?, ?)");

            // Auto-generate medicine_id
            ps.setString(1, "MED" + System.currentTimeMillis());
            ps.setString(2, medicineName);
            ps.setString(3, quantity);

            ps.executeUpdate();
            con.close();

            res.sendRedirect("PharmacistUpdateStockServlet");

        } catch(Exception e){
            res.getWriter().println(e);
        }
    }
}
