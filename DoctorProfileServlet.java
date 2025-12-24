import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.*;

@WebServlet("/DoctorProfileServlet")
public class DoctorProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession(false);
        String username = "";
        if(session != null && session.getAttribute("username") != null){
            username = (String) session.getAttribute("username");
        } else {
            res.sendRedirect("doctor_login.html");
            return;
        }

        String fullName="", speciality="", phone="", email="";
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE","system","manager");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT * FROM doctor_module WHERE username='" + username + "'");
            if(rs.next()){
                fullName = rs.getString("full_name");
                speciality = rs.getString("speciality");
                phone = rs.getString("phone");
                email = rs.getString("email");
            }
            con.close();
        }catch(Exception e){ out.println(e); }

        out.println("<html><head><title>Doctor Profile</title>");
        out.println("<style>");

        /* LIGHT PURPLISH THEME ONLY */
        out.println("body{font-family:Arial; background:#f3eaff;}");

        out.println(".container{max-width:600px;margin:50px auto;padding:30px;"
                + "background:#ffffff;box-shadow:0 0 18px rgba(128,0,128,0.25);"
                + "border-radius:14px;}");

        out.println("h2{color:#6a1b9a;}");

        out.println("p{font-size:16px;margin:10px 0;color:#4a235a;}");

        out.println(".btn{padding:10px 22px;"
                + "background:linear-gradient(135deg,#6a1b9a,#8e24aa);"
                + "color:white;text-decoration:none;border-radius:25px;"
                + "margin-top:18px; display:inline-block;}");

        out.println(".btn:hover{background:linear-gradient(135deg,#8e24aa,#6a1b9a);}");        

        out.println("</style></head><body>");

        out.println("<div class='container'>");
        out.println("<h2>Doctor Profile</h2>");
        out.println("<p><b>Full Name:</b> "+fullName+"</p>");
        out.println("<p><b>Speciality:</b> "+speciality+"</p>");
        out.println("<p><b>Phone:</b> "+phone+"</p>");
        out.println("<p><b>Email:</b> "+email+"</p>");
        out.println("<p><b>Username:</b> "+username+"</p>");
        out.println("<a class='btn' href='DoctorDashboardServlet'>Back to Dashboard</a>");
        out.println("</div></body></html>");
    }
}
