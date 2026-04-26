package servlet;

import dao.StudentDAO;
import model.Student;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ViewStudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StudentDAO studentDAO;

    public void init() {
        studentDAO = new StudentDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Optional delete logic included
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                studentDAO.deleteStudent(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("list");
            return;
        }

        if ("edit".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Student existingStudent = studentDAO.selectStudentById(id);
            if (existingStudent == null) {
                response.sendRedirect("list?error=Student not found");
                return;
            }
            request.setAttribute("student", existingStudent);
            RequestDispatcher dispatcher = request.getRequestDispatcher("edit.jsp");
            dispatcher.forward(request, response);
            return;
        }

        String keyword = request.getParameter("q");
        int page = 1;
        int pageSize = 5;
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException ignored) {
                page = 1;
            }
        }

        // Fetch students with search + pagination
        int totalStudents = studentDAO.countStudents(keyword);
        int totalPages = (int) Math.ceil((double) totalStudents / pageSize);
        if (totalPages == 0) {
            totalPages = 1;
        }
        if (page > totalPages) {
            page = totalPages;
        }
        List<Student> listStudent = studentDAO.selectStudents(keyword, page, pageSize);
        request.setAttribute("listStudent", listStudent);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("keyword", keyword == null ? "" : keyword.trim());
        RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
        dispatcher.forward(request, response);
    }
}
