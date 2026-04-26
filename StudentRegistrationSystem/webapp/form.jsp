<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Registration System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <%@ include file="partials/header.jspf" %>
    <div class="container py-4 page-wrap">
    <div class="panel-card">
        <h2 class="title">Register New Student</h2>
        <p class="subtitle">Fill in student details carefully to keep records clean and searchable.</p>
        
        <% 
            String error = request.getParameter("error");
            if(error != null) { 
        %>
            <div class="error-msg"><%= error %></div>
        <% } %>

        <form action="<%=request.getContextPath()%>/add" method="post" class="row g-3">
            <div class="col-md-6">
            <label for="name" class="form-label">Name:</label>
            <input type="text" class="form-control" id="name" name="name" required placeholder="Enter full name">
            </div>
            
            <div class="col-md-6">
            <label for="email" class="form-label">Email:</label>
            <input type="email" class="form-control" id="email" name="email" required placeholder="Enter email address">
            </div>
            
            <div class="col-md-12">
            <label for="course" class="form-label">Course:</label>
            <input type="text" class="form-control" id="course" name="course" required placeholder="Enter course name">
            </div>
            
            <div class="col-md-12 d-grid d-md-flex">
            <input type="submit" class="btn btn-primary px-4" value="Register Student">
            </div>
        </form>
    </div>
    </div>
</body>
</html>
