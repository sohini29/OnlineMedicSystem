import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/PatientDashboard")
public class PatientDashboard extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        String username = "";

        if (session != null && session.getAttribute("username") != null) {
            username = (String) session.getAttribute("username");
        } else {
            response.sendRedirect("patient_login.html");
            return;
        }

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Patient Dashboard</title>");
        out.println("<style>");
        out.println("body { font-family: Arial; background:#e6f0ff; }");
        out.println(".container { max-width: 900px; margin:50px auto; background:#fff; padding:40px; box-shadow:0 0 25px rgba(0,0,128,0.2); border-radius:12px; text-align:center; }");
        out.println("h2{color:#003366;} h3{color:#004080; margin-bottom:30px;}");
        out.println(".menu-container{display:flex; flex-wrap:wrap; justify-content:center; gap:20px;}");
        out.println(".menu-btn{padding:25px 35px; background:#0066cc; color:white; font-size:18px; border:none; border-radius:10px; cursor:pointer; width:200px;}");
        out.println(".menu-btn:hover{background:#3399ff;}");
        out.println("</style></head><body>");

        out.println("<div class='container'>");
        out.println("<h2>Patient Dashboard</h2>");
        out.println("<h3>Welcome, " + username + "</h3>");
        out.println("<div class='menu-container'>");

        out.println("<form action='PatientProfileServlet' method='get'><input type='submit' class='menu-btn' value='My Profile'></form>");
        out.println("<form action='search_doctor.html' method='get'><input type='submit' class='menu-btn' value='Search Doctor'></form>");
        out.println("<form action='PatientViewAppointments' method='get'><input type='submit' class='menu-btn' value='View Appointments'></form>");
        out.println("<form action='PatientSubmitSymptomsServlet' method='get'><input type='submit' class='menu-btn' value='Give Symptoms'></form>");
        out.println("<form action='PatientViewPrescriptionServlet' method='get'><input type='submit' class='menu-btn' value='View Prescriptions'></form>");
        out.println("<form action='patient_payment.html' method='get'><input type='submit' class='menu-btn' value='Payments'></form>");
        out.println("<form action='feedback.html' method='get'><input type='submit' class='menu-btn' value='Give Feedback'></form>");
        // ✅ FIXED LINE (ONLY CHANGE)
        

        out.println("<form action='LogoutServlet' method='get'><input type='submit' class='menu-btn' value='Logout'></form>");

        out.println("</div></div></body></html>");
    }
}
