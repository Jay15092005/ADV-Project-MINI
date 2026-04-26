package servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "Admin@123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username") == null ? "" : request.getParameter("username").trim();
        String password = request.getParameter("password") == null ? "" : request.getParameter("password").trim();

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(60 * 60); // 60 minutes
            session.setAttribute("admin", username);

            // Fallback cookie to restore session after refresh in strict browser setups.
            Cookie adminCookie = new Cookie("adminUser", username);
            adminCookie.setPath(request.getContextPath());
            adminCookie.setHttpOnly(true);
            adminCookie.setMaxAge(60 * 60);
            response.addCookie(adminCookie);

            response.sendRedirect(request.getContextPath() + "/list");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/login.jsp?error=Invalid username or password");
    }
}
