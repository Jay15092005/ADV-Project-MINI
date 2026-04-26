<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Student" %>
<%
    Student student = (Student) request.getAttribute("student");
    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Student</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <%@ include file="partials/header.jspf" %>
    <div class="container py-4 page-wrap">
    <div class="panel-card">
        <h2 class="title">Edit Student</h2>

        <% if (error != null) { %>
            <div class="error-msg"><%= error %></div>
        <% } %>

        <% if (student != null) { %>
        <form action="<%=request.getContextPath()%>/update" method="post" class="row g-3">
            <input type="hidden" name="id" value="<%= student.getId() %>">

            <div class="col-md-6">
            <label for="name" class="form-label">Name:</label>
            <input type="text" class="form-control" id="name" name="name" required value="<%= student.getName() %>">
            </div>

            <div class="col-md-6">
            <label for="email" class="form-label">Email:</label>
            <input type="email" class="form-control" id="email" name="email" required value="<%= student.getEmail() %>">
            </div>

            <div class="col-md-12">
            <label for="course" class="form-label">Course:</label>
            <input type="text" class="form-control" id="course" name="course" required value="<%= student.getCourse() %>">
            </div>

            <div class="col-md-12">
            <input type="submit" class="btn btn-primary" value="Update Student">
            </div>
        </form>
        <% } %>

    </div>
    </div>
</body>
</html>
