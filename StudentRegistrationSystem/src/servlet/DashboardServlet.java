package servlet;

import dao.StudentDAO;
import model.Student;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int totalStudents = studentDAO.getTotalStudentCount();
        Map<String, Integer> courseCounts = studentDAO.getCourseWiseCounts();
        List<Student> recentStudents = studentDAO.selectRecentStudents(5);

        request.setAttribute("totalStudents", totalStudents);
        request.setAttribute("courseCounts", courseCounts);
        request.setAttribute("recentStudents", recentStudents);

        RequestDispatcher dispatcher = request.getRequestDispatcher("dashboard.jsp");
        dispatcher.forward(request, response);
    }
}
