<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Student" %>
<%
    String keyword = (String) request.getAttribute("keyword");
    int currentPage = request.getAttribute("currentPage") == null ? 1 : (Integer) request.getAttribute("currentPage");
    int totalPages = request.getAttribute("totalPages") == null ? 1 : (Integer) request.getAttribute("totalPages");
    String error = request.getParameter("error");
    String msg = request.getParameter("msg");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registered Students</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@ include file="partials/header.jspf" %>
<div class="container py-4 page-wrap">
<div class="panel-card">
    <h2 class="title">Registered Students List</h2>
    <p class="subtitle">Search, edit, and manage all enrolled students from one place.</p>

    <% if (error != null) { %>
        <div class="error-msg"><%= error %></div>
    <% } %>
    <% if (msg != null) { %>
        <div class="success-msg"><%= msg %></div>
    <% } %>

    <form action="<%=request.getContextPath()%>/list" method="get" class="search-box">
        <input type="text" class="form-control" name="q" placeholder="Search by name, email, or course" value="<%= keyword == null ? "" : keyword %>">
        <button type="submit" class="btn btn-primary">Search</button>
        <a href="<%=request.getContextPath()%>/list" class="btn btn-outline-secondary">Reset</a>
    </form>
    <div class="d-flex flex-wrap justify-content-between align-items-center mb-3 gap-2">
        <input type="text" id="liveSearch" class="form-control" style="max-width: 420px;" placeholder="Live filter current page rows...">
        <span class="stat-chip">Page <%= currentPage %> / <%= totalPages %></span>
    </div>

    <div class="app-table-wrap">
    <table class="table table-hover app-table">
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
                        <div class="action-btns">
                        <a href="<%=request.getContextPath()%>/list?action=edit&id=<%= student.getId() %>"
                           class="btn btn-sm btn-warning">Edit</a>
                        <a href="<%=request.getContextPath()%>/list?action=delete&id=<%= student.getId() %>" 
                           class="btn btn-sm btn-danger" 
                           onclick="return confirm('Are you sure you want to delete this student?');">Delete</a>
                        </div>
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
    </div>

    <div class="pagination">
        <% if (currentPage > 1) { %>
            <a class="btn btn-outline-primary btn-sm" href="<%=request.getContextPath()%>/list?page=<%= currentPage - 1 %>&q=<%= keyword == null ? "" : keyword %>">Previous</a>
        <% } %>
        <span class="text-muted">Page <%= currentPage %> of <%= totalPages %></span>
        <% if (currentPage < totalPages) { %>
            <a class="btn btn-outline-primary btn-sm" href="<%=request.getContextPath()%>/list?page=<%= currentPage + 1 %>&q=<%= keyword == null ? "" : keyword %>">Next</a>
        <% } %>
    </div>
    <script>
        document.getElementById("liveSearch").addEventListener("keyup", function () {
            const value = this.value.toLowerCase();
            document.querySelectorAll("tbody tr").forEach(function (row) {
                row.style.display = row.innerText.toLowerCase().includes(value) ? "" : "none";
            });
        });
    </script>
</div>
</div>
</body>
</html>
