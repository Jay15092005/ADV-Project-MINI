<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Student" %>
<%
    Integer totalStudents = (Integer) request.getAttribute("totalStudents");
    Map<String, Integer> courseCounts = (Map<String, Integer>) request.getAttribute("courseCounts");
    List<Student> recentStudents = (List<Student>) request.getAttribute("recentStudents");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<%@ include file="partials/header.jspf" %>
<div class="container py-4 page-wrap">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h2 class="title mb-0">Student Dashboard</h2>
    </div>
    <p class="subtitle">Quick analytics snapshot for registrations and course distribution.</p>

    <div class="row g-3 mb-4">
        <div class="col-md-4">
            <div class="card shadow-sm border-0 rounded-4">
                <div class="card-body text-center">
                    <h6 class="text-muted">Total Students</h6>
                    <h2><%= totalStudents == null ? 0 : totalStudents %></h2>
                </div>
            </div>
        </div>
        <div class="col-md-8">
            <div class="card shadow-sm border-0 rounded-4">
                <div class="card-body">
                    <h6 class="mb-3">Students per Course</h6>
                    <canvas id="courseChart" height="110"></canvas>
                </div>
            </div>
        </div>
    </div>

    <div class="card shadow-sm border-0 rounded-4">
        <div class="card-body">
            <h6 class="mb-3">Recently Added Students</h6>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Course</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (recentStudents != null && !recentStudents.isEmpty()) {
                        for (Student s : recentStudents) { %>
                    <tr>
                        <td><%= s.getId() %></td>
                        <td><%= s.getName() %></td>
                        <td><%= s.getEmail() %></td>
                        <td><%= s.getCourse() %></td>
                    </tr>
                    <% }} else { %>
                    <tr><td colspan="4" class="text-center">No students found</td></tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    const labels = [
        <% if (courseCounts != null) {
            int i = 0;
            for (Map.Entry<String, Integer> e : courseCounts.entrySet()) {
                if (i++ > 0) out.print(","); %>
        "<%= e.getKey() %>"
        <% }} %>
    ];
    const values = [
        <% if (courseCounts != null) {
            int i = 0;
            for (Map.Entry<String, Integer> e : courseCounts.entrySet()) {
                if (i++ > 0) out.print(","); %>
        <%= e.getValue() %>
        <% }} %>
    ];

    new Chart(document.getElementById("courseChart"), {
        type: "bar",
        data: {
            labels: labels,
            datasets: [{
                label: "Students",
                data: values,
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            scales: { y: { beginAtZero: true } }
        }
    });
</script>
</body>
</html>
