package ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import service.AttendanceSystem;
import db.DBConnect;

class ViewAttendancePanel extends JPanel {
    private JComboBox<String> studentDropdown;
    private JTextArea output;

    public ViewAttendancePanel(AttendanceSystem system, MainDashboard dashboard) {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        JLabel studentLabel = new JLabel("Select Student:");
        studentDropdown = new JComboBox<>();
        JButton viewBtn = new JButton("View Attendance");
        JButton backBtn = new JButton("⬅ Back");

        output = new JTextArea();
        output.setEditable(false);

        // Load students from DB into dropdown
        loadStudents();

        topPanel.add(studentLabel);
        topPanel.add(studentDropdown);
        topPanel.add(viewBtn);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(output), BorderLayout.CENTER);
        add(backBtn, BorderLayout.SOUTH);

        // View button action
        viewBtn.addActionListener(e -> {
            String selected = (String) studentDropdown.getSelectedItem();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "⚠ Please select a student.");
                return;
            }

            int studentId = Integer.parseInt(selected.split(" - ")[0]); // Extract ID
            output.setText(""); // Clear output
            output.append("=== Attendance Report ===\n");

            try (Connection conn = DBConnect.connect()) {
                String sql = "SELECT s.sub_name, a.attended, s.total_classes " +
                             "FROM attendance a " +
                             "JOIN subjects s ON a.sub_id = s.sub_id " +
                             "WHERE a.stud_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, studentId);
                ResultSet rs = stmt.executeQuery();

                boolean found = false;
                while (rs.next()) {
                    found = true;
                    String subject = rs.getString("sub_name");
                    int attended = rs.getInt("attended");
                    int total = rs.getInt("total_classes");
                    double percent = (attended * 100.0) / total;

                    output.append(subject + ": " + attended + "/" + total +
                                  " (" + String.format("%.2f", percent) + "%)\n");
                    if (percent < 75) {
                        output.append("⚠️ Low Attendance Alert for " + subject + "\n");
                    }
                }

                if (!found) {
                    output.append("No attendance records found for this student.\n");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "❌ Error fetching attendance.");
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
                studentDropdown.addItem(id + " - " + name); // Show "id - name"
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
