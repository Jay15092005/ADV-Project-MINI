package filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StudentValidationFilter implements Filter {

    public void init(FilterConfig filterConfig) {
        // no-op
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String name = trim(httpRequest.getParameter("name"));
        String email = trim(httpRequest.getParameter("email"));
        String course = trim(httpRequest.getParameter("course"));
        String id = trim(httpRequest.getParameter("id"));

        boolean invalid = name.isEmpty() || email.isEmpty() || course.isEmpty();
        boolean invalidEmail = !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

        if (invalid || invalidEmail) {
            String path = "/form.jsp?error=Please provide valid student details";
            if (httpRequest.getServletPath().equals("/update")) {
                path = "/list?action=edit&id=" + id + "&error=Please provide valid student details";
            }
            httpResponse.sendRedirect(httpRequest.getContextPath() + path);
            return;
        }

        chain.doFilter(request, response);
    }

    public void destroy() {
        // no-op
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
