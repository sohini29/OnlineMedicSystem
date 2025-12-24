import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/LogoutServletPh")
public class LogoutServletPh extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false); // Get existing session
        if(session != null) {
            session.invalidate(); // Destroy session
        }

        // Redirect user to the home/login page
        res.sendRedirect("index.html"); // Or patient_login.html / doctor_login.html depending on entry point
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doGet(req, res); // Handle POST same as GET
    }
}
