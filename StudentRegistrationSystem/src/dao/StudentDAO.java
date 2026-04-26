package dao;

import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StudentDAO {
    // Database credentials
    private String jdbcURL = "jdbc:mysql://localhost:3306/student_db?useSSL=false";
    private String jdbcUsername = "root";
    private String jdbcPassword = "Chotaliya@1509"; // Update this with your DB password

    private static final String INSERT_STUDENTS_SQL = "INSERT INTO students (name, email, course) VALUES (?, ?, ?);";
    private static final String SELECT_STUDENTS_WITH_SEARCH_PAGINATION =
            "SELECT * FROM students WHERE name LIKE ? OR email LIKE ? OR course LIKE ? ORDER BY id DESC LIMIT ? OFFSET ?;";
    private static final String COUNT_STUDENTS_WITH_SEARCH =
            "SELECT COUNT(*) FROM students WHERE name LIKE ? OR email LIKE ? OR course LIKE ?;";
    private static final String DELETE_STUDENT_SQL = "DELETE FROM students WHERE id = ?;";
    private static final String SELECT_STUDENT_BY_ID = "SELECT * FROM students WHERE id = ?;";
    private static final String UPDATE_STUDENT_SQL = "UPDATE students SET name = ?, email = ?, course = ? WHERE id = ?;";
    private static final String CHECK_EMAIL_EXISTS = "SELECT COUNT(*) FROM students WHERE email = ?;";
    private static final String CHECK_EMAIL_EXISTS_FOR_OTHER = "SELECT COUNT(*) FROM students WHERE email = ? AND id <> ?;";
    private static final String COUNT_ALL_STUDENTS_SQL = "SELECT COUNT(*) FROM students;";
    private static final String COUNT_BY_COURSE_SQL = "SELECT course, COUNT(*) AS total FROM students GROUP BY course ORDER BY total DESC;";
    private static final String SELECT_RECENT_STUDENTS_SQL = "SELECT * FROM students ORDER BY id DESC LIMIT ?;";

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

    // Select students with search and pagination
    public List<Student> selectStudents(String keyword, int page, int pageSize) {
        List<Student> students = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        String searchValue = "%" + (keyword == null ? "" : keyword.trim()) + "%";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENTS_WITH_SEARCH_PAGINATION)) {
            preparedStatement.setString(1, searchValue);
            preparedStatement.setString(2, searchValue);
            preparedStatement.setString(3, searchValue);
            preparedStatement.setInt(4, pageSize);
            preparedStatement.setInt(5, offset);
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

    public int countStudents(String keyword) {
        int count = 0;
        String searchValue = "%" + (keyword == null ? "" : keyword.trim()) + "%";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(COUNT_STUDENTS_WITH_SEARCH)) {
            preparedStatement.setString(1, searchValue);
            preparedStatement.setString(2, searchValue);
            preparedStatement.setString(3, searchValue);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Student selectStudentById(int id) {
        Student student = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_STUDENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                student = new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("course")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    public boolean updateStudent(Student student) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_STUDENT_SQL)) {
            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getCourse());
            statement.setInt(4, student.getId());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public boolean emailExists(String email) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_EMAIL_EXISTS)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailExistsForOtherStudent(String email, int studentId) {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_EMAIL_EXISTS_FOR_OTHER)) {
            statement.setString(1, email);
            statement.setInt(2, studentId);
            ResultSet rs = statement.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getTotalStudentCount() {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_ALL_STUDENTS_SQL)) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Map<String, Integer> getCourseWiseCounts() {
        Map<String, Integer> courseWiseCounts = new LinkedHashMap<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(COUNT_BY_COURSE_SQL)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                courseWiseCounts.put(rs.getString("course"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseWiseCounts;
    }

    public List<Student> selectRecentStudents(int limit) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_RECENT_STUDENTS_SQL)) {
            statement.setInt(1, limit);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("course")
                ));
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
