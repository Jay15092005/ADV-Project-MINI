package servlet;

import dao.StudentDAO;
import model.Student;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddStudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String course = request.getParameter("course");

        // Simple Validation inside controller before inserting
        if (name == null || name.trim().isEmpty() || 
            email == null || email.trim().isEmpty() || 
            course == null || course.trim().isEmpty()) {
            response.sendRedirect("form.jsp?error=Missing Fields");
            return;
        }

        Student newStudent = new Student(name, email, course);
        try {
            studentDAO.insertStudent(newStudent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect("success.jsp");
    }
}
