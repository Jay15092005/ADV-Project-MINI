<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%
    Integer totalStudents = (Integer) request.getAttribute("totalStudents");
    Map<String, Integer> courseCounts = (Map<String, Integer>) request.getAttribute("courseCounts");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Reports</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@ include file="partials/header.jspf" %>
<div class="container py-4 page-wrap">
<div class="panel-card">
    <h2 class="title">Student Report Dashboard</h2>
    <p class="subtitle">Course-wise enrollment overview for reporting and demo presentation.</p>

    <div class="d-flex flex-wrap justify-content-between align-items-center mb-3">
        <h3 class="mb-2">Total Students: <%= totalStudents == null ? 0 : totalStudents %></h3>
        <span class="stat-chip">Live report data</span>
    </div>

    <div class="app-table-wrap">
    <table class="table table-hover app-table">
        <thead>
            <tr>
                <th>Course</th>
                <th>Total Students</th>
            </tr>
        </thead>
        <tbody>
            <% if (courseCounts != null && !courseCounts.isEmpty()) {
                for (Map.Entry<String, Integer> entry : courseCounts.entrySet()) { %>
                <tr>
                    <td><%= entry.getKey() %></td>
                    <td><%= entry.getValue() %></td>
                </tr>
            <%  }
               } else { %>
                <tr>
                    <td colspan="2" style="text-align:center;">No report data available</td>
                </tr>
            <% } %>
        </tbody>
    </table>
    </div>
</div>
</div>
</body>
</html>
