import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/PharmacistDashboardServlet")
public class PharmacistDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            res.sendRedirect("pharmacist_login.html");
            return;
        }

        String pharmacistUsername = (String) session.getAttribute("username");

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Pharmacist Dashboard</title>");
        out.println("<style>");
        out.println("body {font-family: Arial, sans-serif; background: #fff3e0;}"); // light orangish background
        out.println("h2 {text-align:center; color:#e65100; margin-top:20px;}"); // dark orange heading
        out.println(".container {width:80%; margin: auto; display:flex; flex-wrap:wrap; justify-content:center; margin-top:40px;}");
        out.println(".card {background:white; width:250px; margin:15px; padding:25px; text-align:center; border-radius:10px; box-shadow:0 4px 10px rgba(230,115,0,0.2);}"); // shadow in orange shade
        out.println(".card h3 {color:#ef6c00;}"); // card title color
        out.println(".card a {display:block; margin-top:15px; padding:10px; background:#ffa726; color:white; text-decoration:none; border-radius:5px;}"); // buttons in orange
        out.println(".card a:hover {background:#fb8c00;}"); // hover effect
        out.println("</style></head><body>");

        out.println("<h2>Welcome, " + pharmacistUsername + "</h2>");
        out.println("<div class='container'>");

        out.println("<div class='card'>");
        out.println("<h3>View Prescriptions</h3>");
        out.println("<a href='PharmacistViewPrescriptionsServlet'>Go</a>");
        out.println("</div>");

        out.println("<div class='card'>");
        out.println("<h3>Update Medicine Stock</h3>");
        out.println("<a href='PharmacistUpdateStockServlet'>Go</a>");
        out.println("</div>");

        out.println("<div class='card'>");
        out.println("<h3>View Payments</h3>");
        out.println("<a href='PharmacistViewPaymentServlet'>Go</a>");
        out.println("</div>");
        
        out.println("<div class='card'>");
        out.println("<h3>Give Feedback</h3>");
        out.println("<a href='feedback.html'>Go</a>");
        out.println("</div>");

        out.println("<div class='card'>");
        out.println("<h3>Logout</h3>");
        out.println("<a href='LogoutServlet2'>Logout</a>");
        out.println("</div>");

        out.println("</div></body></html>");
    }
}
