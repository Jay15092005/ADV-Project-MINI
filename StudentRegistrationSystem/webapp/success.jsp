<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Successful</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="container" style="text-align: center;">
        <h2 style="color: #4CAF50;">Student Registered Successfully!</h2>
        <p>The student's details have been saved to the database.</p>
        <br>
        <a href="<%=request.getContextPath()%>/form.jsp" class="btn">Register Another</a>
        <a href="<%=request.getContextPath()%>/list" class="btn">View Students</a>
    </div>
</body>
</html>
