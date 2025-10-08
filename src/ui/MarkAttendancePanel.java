package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import service.AttendanceSystem;
import db.DBConnect;

class MarkAttendancePanel extends JPanel {
    private JComboBox<String> studentDropdown;
    private JComboBox<String> subjectDropdown;

    public MarkAttendancePanel(AttendanceSystem system, MainDashboard dashboard) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));  
        // Align to top-left with padding

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Student row
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel studentLabel = new JLabel("Select Student      :");
        studentDropdown = new JComboBox<>();
        row1.add(studentLabel);
        row1.add(studentDropdown);

        // Subject row
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel subjectLabel = new JLabel("Select Subject      :");
        subjectDropdown = new JComboBox<>();
        row2.add(subjectLabel);
        row2.add(subjectDropdown);

        // Attended row
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel attendedLabel = new JLabel("Classes Attended  :");
        JTextField attendedField = new JTextField(10);
        row3.add(attendedLabel);
        row3.add(attendedField);

        // Buttons row
        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton saveBtn = new JButton("Save");
        JButton backBtn = new JButton("⬅ Back");
        row4.add(saveBtn);
        row4.add(backBtn);

        // Add rows into form
        form.add(row1);
        form.add(row2);
        form.add(row3);
        form.add(row4);

        add(form);

        // Load students and subjects from DB
        loadStudents();
        loadSubjects();

        // Save attendance
        saveBtn.addActionListener(e -> {
            try {
                String studentItem = (String) studentDropdown.getSelectedItem();
                String subjectItem = (String) subjectDropdown.getSelectedItem();

                if (studentItem == null || subjectItem == null) {
                    JOptionPane.showMessageDialog(this, "⚠ Please select student and subject.");
                    return;
                }

                // Extract IDs from "id - name"
                int studentId = Integer.parseInt(studentItem.split(" - ")[0]);
                int subjectId = Integer.parseInt(subjectItem.split(" - ")[0]);
                int attended = Integer.parseInt(attendedField.getText().trim());

                system.markAttendance(studentId, subjectId, attended);
                JOptionPane.showMessageDialog(this, "✅ Attendance Marked!");

                attendedField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "⚠ Please enter a valid number for attended classes.");
            }
        });

        backBtn.addActionListener(e -> dashboard.goBackToMenu());
    }

    // Load students into dropdown
    private void loadStudents() {
        try (Connection conn = DBConnect.connect()) {
            String sql = "SELECT stud_id, stud_name FROM students";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("stud_id");
                String name = rs.getString("stud_name");
                studentDropdown.addItem(id + " - " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load subjects into dropdown
    private void loadSubjects() {
        try (Connection conn = DBConnect.connect()) {
            String sql = "SELECT sub_id, sub_name FROM subjects";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("sub_id");
                String name = rs.getString("sub_name");
                subjectDropdown.addItem(id + " - " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
