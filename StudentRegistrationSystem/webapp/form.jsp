<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Registration System</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2>Register New Student</h2>
        
        <% 
            String error = request.getParameter("error");
            if(error != null) { 
        %>
            <div class="error-msg"><%= error %></div>
        <% } %>

        <form action="<%=request.getContextPath()%>/add" method="post">
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required placeholder="Enter full name">
            
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required placeholder="Enter email address">
            
            <label for="course">Course:</label>
            <input type="text" id="course" name="course" required placeholder="Enter course name">
            
            <input type="submit" value="Register Student">
        </form>
        <br>
        <div style="text-align: center;">
            <a href="<%=request.getContextPath()%>/list" class="btn">View All Students</a>
        </div>
    </div>
</body>
</html>
