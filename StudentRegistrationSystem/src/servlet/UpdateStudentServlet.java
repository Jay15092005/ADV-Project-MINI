package servlet;

import dao.StudentDAO;
import model.Student;

import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UpdateStudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        String name = request.getParameter("name") == null ? "" : request.getParameter("name").trim();
        String email = request.getParameter("email") == null ? "" : request.getParameter("email").trim();
        String course = request.getParameter("course") == null ? "" : request.getParameter("course").trim();

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("list?error=Invalid student id");
            return;
        }

        if (name.isEmpty() || email.isEmpty() || course.isEmpty()) {
            response.sendRedirect("list?action=edit&id=" + id + "&error=Missing Fields");
            return;
        }

        if (studentDAO.emailExistsForOtherStudent(email, id)) {
            response.sendRedirect("list?action=edit&id=" + id + "&error=Email already exists");
            return;
        }

        Student updatedStudent = new Student(id, name, email, course);
        try {
            boolean updated = studentDAO.updateStudent(updatedStudent);
            if (!updated) {
                response.sendRedirect("list?error=Student not found");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("list?action=edit&id=" + id + "&error=Unable to update student");
            return;
        }
        response.sendRedirect("list?msg=Student updated successfully");
    }
}
