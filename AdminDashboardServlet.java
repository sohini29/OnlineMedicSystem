import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            res.sendRedirect("admin_login.html");
            return;
        }

        String adminUser = (String) session.getAttribute("username");

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Admin Dashboard</title>");

        out.println("<style>");
        out.println("body{font-family:Arial;background:#f2f2f2;margin:0;}");
        out.println(".header{background:#2c3e50;color:white;padding:20px;text-align:center;font-size:26px;}");
        out.println(".container{display:flex;flex-wrap:wrap;justify-content:center;margin-top:40px;}");
        out.println(".card{background:white;width:240px;margin:15px;padding:25px;text-align:center;");
        out.println("border-radius:10px;box-shadow:0 4px 10px rgba(0,0,0,0.2);}");
        out.println(".card h3{color:#2c3e50;margin-bottom:15px;}");
        out.println(".card a{display:block;padding:10px;background:#1abc9c;color:white;");
        out.println("text-decoration:none;border-radius:6px;margin-top:10px;}");
        out.println(".card a:hover{background:#16a085;}");
        out.println("</style>");

        out.println("</head><body>");

        out.println("<div class='header'>");
        out.println("Welcome Admin : " + adminUser);
        out.println("</div>");

        out.println("<div class='container'>");
        
        out.println("<div class='card'>");
        out.println("<h3>My Profile</h3>");
        out.println("<a href='AdminProfileServlet'>View Profile</a>");
        out.println("</div>");

        out.println("<div class='card'>");
        out.println("<h3>Doctors</h3>");
        out.println("<a href='AdminViewDoctorsServlet'>View Doctors</a>");
        out.println("</div>");

        out.println("<div class='card'>");
        out.println("<h3>Patients</h3>");
        out.println("<a href='AdminViewPatientsServlet'>View Patients</a>");
        out.println("</div>");

        out.println("<div class='card'>");
        out.println("<h3>Pharmacists</h3>");
        out.println("<a href='AdminViewPharmacistsServlet'>View Pharmacists</a>");
        out.println("</div>");
        
         out.println("<div class='card'>");
        out.println("<h3>Medicine Stock</h3>");
        out.println("<a href='AdminViewMedicineStockServlet'>View Stock</a>");
        out.println("</div>");

        out.println("<div class='card'>");
        out.println("<h3>Payments</h3>");
        out.println("<a href='AdminViewPaymentsServlet'>View Payments</a>");
        out.println("</div>");
        
        out.println("<div class='card'>");
        out.println("<h3>Feedback</h3>");
        out.println("<a href='AdminViewFeedbackServlet'>View Feedback</a>");
        out.println("</div>");

        out.println("<div class='card'>");
        out.println("<h3>Logout</h3>");
        out.println("<a href='LogoutServlet'>Logout</a>");
        out.println("</div>");

        out.println("</div>");

        out.println("</body></html>");
    }
}
