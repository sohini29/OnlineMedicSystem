import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/AdminProfileServlet")
public class AdminProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession(false);
        String username = "";
        if(session != null && session.getAttribute("username") != null){
            username = (String) session.getAttribute("username");
        } else {
            res.sendRedirect("admin_login.html");
            return;
        }

        String fullName="", phone="", email="";
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","system","manager");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM admin_module WHERE username='" + username + "'");
            if(rs.next()){
                fullName = rs.getString("full_name");
                phone = rs.getString("phone");
                email = rs.getString("email");
            }
            con.close();
        }catch(Exception e){ out.println(e); }

        out.println("<html><head><title>Admin Profile</title>");
        out.println("<style>");

        /* LIGHT GREENISH THEME */
        out.println("body{font-family:Arial; background:#e8f5e9;}");

        out.println(".container{max-width:600px;margin:50px auto;padding:30px;"
                + "background:#ffffff;box-shadow:0 0 18px rgba(46,125,50,0.25);"
                + "border-radius:14px;}");

        out.println("h2{color:#2e7d32;}");

        out.println("p{font-size:16px;margin:10px 0;color:#145a32;}");

        out.println(".btn{padding:10px 22px;"
                + "background:linear-gradient(135deg,#2e7d32,#1b5e20);"
                + "color:white;text-decoration:none;border-radius:25px;"
                + "margin-top:18px; display:inline-block;}");

        out.println(".btn:hover{background:linear-gradient(135deg,#1b5e20,#2e7d32);}");        

        out.println("</style></head><body>");

        out.println("<div class='container'>");
        out.println("<h2>Admin Profile</h2>");
        out.println("<p><b>Full Name:</b> "+fullName+"</p>");
        out.println("<p><b>Phone:</b> "+phone+"</p>");
        out.println("<p><b>Email:</b> "+email+"</p>");
        out.println("<p><b>Username:</b> "+username+"</p>");
        out.println("<a class='btn' href='AdminDashboardServlet'>Back to Dashboard</a>");
        out.println("</div></body></html>");
    }
}
