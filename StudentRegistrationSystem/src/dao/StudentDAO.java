package dao;

import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    // Database credentials
    private String jdbcURL = "jdbc:mysql://localhost:3306/student_db?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "password"; // Update this with your DB password

    private static final String INSERT_STUDENTS_SQL = "INSERT INTO students (name, email, course) VALUES (?, ?, ?);";
    private static final String SELECT_ALL_STUDENTS = "SELECT * FROM students;";
    private static final String DELETE_STUDENT_SQL = "DELETE FROM students WHERE id = ?;";

    public StudentDAO() {}

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    // Insert new student
    public void insertStudent(Student student) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENTS_SQL)) {
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setString(3, student.getCourse());
            preparedStatement.executeUpdate();
        }
    }

    // Select all students
    public List<Student> selectAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String course = rs.getString("course");
                students.add(new Student(id, name, email, course));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // Delete student (optional extra)
    public boolean deleteStudent(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_SQL)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
}
