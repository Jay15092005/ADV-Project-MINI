<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Successful</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <%@ include file="partials/header.jspf" %>
    <div class="container py-5 page-wrap">
    <div class="panel-card text-center mx-auto" style="max-width: 680px;">
        <h2 class="title text-success">Student Registered Successfully</h2>
        <p class="text-muted">The student's details have been saved to the database.</p>
        <div class="top-actions justify-content-center">
            <a href="<%=request.getContextPath()%>/form.jsp" class="btn btn-primary">Register Another</a>
            <a href="<%=request.getContextPath()%>/list" class="btn btn-success">View Students</a>
            <a href="<%=request.getContextPath()%>/report" class="btn btn-outline-success">View Report</a>
        </div>
    </div>
    </div>
</body>
</html>
