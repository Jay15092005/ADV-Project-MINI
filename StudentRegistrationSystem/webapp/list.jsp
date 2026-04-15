<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Student" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registered Students</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <h2>Registered Students List</h2>
    
    <div style="margin-bottom: 15px;">
        <a href="<%=request.getContextPath()%>/form.jsp" class="btn">Add New Student</a>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Course</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Student> listStudent = (List<Student>) request.getAttribute("listStudent");
                if(listStudent != null) {
                    for(Student student : listStudent) {
            %>
                <tr>
                    <td><%= student.getId() %></td>
                    <td><%= student.getName() %></td>
                    <td><%= student.getEmail() %></td>
                    <td><%= student.getCourse() %></td>
                    <td>
                        <a href="<%=request.getContextPath()%>/list?action=delete&id=<%= student.getId() %>" 
                           class="btn btn-danger" 
                           onclick="return confirm('Are you sure you want to delete this student?');">Delete</a>
                    </td>
                </tr>
            <% 
                    }
                } else {
            %>
                <tr>
                    <td colspan="5" style="text-align:center;">No students found</td>
                </tr>
            <% 
                } 
            %>
        </tbody>
    </table>
</body>
</html>
