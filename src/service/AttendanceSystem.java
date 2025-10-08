package service;

import db.DBConnect;
import java.sql.*;

public class AttendanceSystem {

    // Add a student
    public void addStudent(String name) {
        try (Connection conn = DBConnect.connect()) {
            String sql = "INSERT INTO students (stud_name) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.executeUpdate();
            System.out.println("✅ Student added successfully!");
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
    }

    // Add a subject
    public void addSubject(String subjectName, int totalClasses) {
        try (Connection conn = DBConnect.connect()) {
            String sql = "INSERT INTO subjects (sub_name, total_classes) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, subjectName);
            stmt.setInt(2, totalClasses);
            stmt.executeUpdate();
            System.out.println("✅ Subject added successfully!");
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
    }

    // Mark attendance
    public void markAttendance(int studentId, int subjectId, int attended) {
        try (Connection conn = DBConnect.connect()) {
            String sql = "INSERT INTO attendance (stud_id, sub_id, attended) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentId);
            stmt.setInt(2, subjectId);
            stmt.setInt(3, attended);
            stmt.executeUpdate();
            System.out.println("✅ Attendance marked!");
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
    }

    // View attendance for one student
    public void viewAttendance(int studentId) {
        try (Connection conn = DBConnect.connect()) {
            String sql = "SELECT s.sub_name, a.attended, s.total_classes " +
                         "FROM attendance a " +
                         "JOIN subjects s ON a.sub_id = s.sub_id " +
                         "WHERE a.stud_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String subject = rs.getString("sub_name");
                int attended = rs.getInt("attended");
                int total = rs.getInt("total_classes");
                double percent = (attended * 100.0) / total;

                System.out.println(subject + ": " + attended + "/" + total +
                                   " (" + String.format("%.2f", percent) + "%)");
                if (percent < 75) {
                    System.out.println("⚠️ Low Attendance Alert for " + subject);
                }
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
    }

    // View all students
    public void viewAllStudents() {
        try (Connection conn = DBConnect.connect()) {
            String sql = "SELECT * FROM students";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n=== Student List ===");
            while (rs.next()) {
                int id = rs.getInt("stud_id");
                String name = rs.getString("stud_name");
                String course = rs.getString("course");
                System.out.println("ID: " + id + " | Name: " + name + " | Course: " + course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Clear only attendance records
    public void clearAttendance() {
        try (Connection conn = DBConnect.connect()) {
            String sql = "DELETE FROM attendance";
            Statement stmt = conn.createStatement();
            int rows = stmt.executeUpdate(sql);
            System.out.println("🗑️ Cleared " + rows + " attendance records.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Reset ALL data and restart IDs
    public void resetAllData() {
        try (Connection conn = DBConnect.connect()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("TRUNCATE TABLE attendance");
            stmt.executeUpdate("TRUNCATE TABLE students");
            stmt.executeUpdate("TRUNCATE TABLE subjects");
            System.out.println("🗑️ All data erased. IDs reset to 1.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
