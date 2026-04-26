<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String error = request.getParameter("error");
    String msg = request.getParameter("msg");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="container py-5 page-wrap">
    <div class="panel-card mx-auto" style="max-width: 480px;">
        <h2 class="title">Admin Login</h2>

        <% if (error != null) { %>
            <div class="error-msg"><%= error %></div>
        <% } %>
        <% if (msg != null) { %>
            <div class="success-msg"><%= msg %></div>
        <% } %>

        <form action="<%=request.getContextPath()%>/login" method="post" class="row g-3">
            <div class="col-12">
            <label for="username" class="form-label">Username:</label>
            <input type="text" class="form-control" id="username" name="username" required placeholder="Enter username">
            </div>

            <div class="col-12">
            <label for="password" class="form-label">Password:</label>
            <input type="password" class="form-control" id="password" name="password" required placeholder="Enter password">
            </div>

            <div class="col-12 d-grid">
            <input type="submit" class="btn btn-primary" value="Login">
            </div>
        </form>
        <p class="note">Demo credentials: admin / Admin@123</p>
    </div>
    </div>
</body>
</html>
