import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/DoctorDashboardServlet")
public class DoctorDashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        String username = "";
        if(session != null && session.getAttribute("username") != null){
            username = (String) session.getAttribute("username");
        } else {
            response.sendRedirect("doctor_login.html");
            return;
        }

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Doctor Dashboard</title>");
        out.println("<style>");

        /* LIGHT PURPLE THEME */
        out.println("body { font-family: Arial; background:#f3eaff; }");

        out.println(".container { max-width: 900px; margin:50px auto; background:#ffffff; padding:40px; box-shadow:0 0 25px rgba(128,0,128,0.25); border-radius:15px; text-align:center; }");

        out.println("h2{color:#6a1b9a;} h3{color:#7b4fa3; margin-bottom:30px;}");

        out.println(".menu-container{display:flex; flex-wrap:wrap; justify-content:center; gap:20px;}");

        out.println(".menu-btn{padding:25px 35px; background:linear-gradient(135deg,#6a1b9a,#8e24aa); color:white; font-size:18px; border:none; border-radius:12px; cursor:pointer; width:220px; box-shadow:0 6px 12px rgba(128,0,128,0.3); transition:0.3s;}");

        out.println(".menu-btn:hover{background:linear-gradient(135deg,#8e24aa,#6a1b9a); box-shadow:0 8px 16px rgba(128,0,128,0.4);}");        

        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='container'>");
        out.println("<h2>Doctor Dashboard</h2>");
        out.println("<h3>Welcome, " + username + "</h3>");
        out.println("<div class='menu-container'>");

        out.println("<form action='DoctorProfileServlet' method='get'>");
        out.println("<input type='submit' class='menu-btn' value='View Profile'>");
        out.println("</form>");

        out.println("<form action='DoctorViewAppointmentsServlet1' method='get'>");
        out.println("<input type='submit' class='menu-btn' value='View Appointments'>");
        out.println("</form>");

        out.println("<form action='DoctorPatientsServlet' method='get'>");
        out.println("<input type='submit' class='menu-btn' value='My Patients'>");
        out.println("</form>");

        out.println("<form action='DoctorViewPrescriptionsServlet' method='get'>");
        out.println("<input type='submit' class='menu-btn' value='View Prescriptions'>");
        out.println("</form>");

        out.println("<form action='DoctorViewPaymentsServlet' method='get'>");
        out.println("<input type='submit' class='menu-btn' value='View Payments'>");
        out.println("</form>");
        
        out.println("<form action='feedback1.html' method='get'>");
        out.println("<input type='submit' class='menu-btn' value='Give Feedback'>");
        out.println("</form>");

        out.println("<form action='LogoutServlet1' method='get'>");
        out.println("<input type='submit' class='menu-btn' value='Logout'>");
        out.println("</form>");

        out.println("</div>");
        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }
}
