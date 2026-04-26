package filter;

import java.io.IOException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthFilter implements Filter {

    public void init(FilterConfig filterConfig) {
        // no-op
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean loggedIn = session != null && session.getAttribute("admin") != null;

        if (!loggedIn) {
            String adminFromCookie = extractAdminCookie(httpRequest.getCookies());
            if (adminFromCookie != null && !adminFromCookie.isEmpty()) {
                HttpSession newSession = httpRequest.getSession(true);
                newSession.setMaxInactiveInterval(60 * 60);
                newSession.setAttribute("admin", adminFromCookie);
                loggedIn = true;
            }
        }

        if (!loggedIn) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=Please login first");
            return;
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
        // no-op
    }

    private String extractAdminCookie(Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if ("adminUser".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
