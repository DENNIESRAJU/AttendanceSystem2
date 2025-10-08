package ui;

import javax.swing.*;
import java.awt.*;
import service.AttendanceSystem;

class AddStudentPanel extends JPanel {
    public AddStudentPanel(AttendanceSystem system, MainDashboard dashboard) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20)); 
        // Align top-left with padding

        JLabel nameLabel = new JLabel("Student Name:");
        JTextField nameField = new JTextField(15); // fixed width field
        JButton saveBtn = new JButton("Save");
        JButton backBtn = new JButton("⬅ Back");

        // Add components one by one (compact form)
        add(nameLabel);
        add(nameField);
        add(saveBtn);
        add(backBtn);

        // Button actions
        saveBtn.addActionListener(e -> {
            String name = nameField.getText();
            if (!name.isEmpty()) {
                system.addStudent(name);
                JOptionPane.showMessageDialog(this, "✅ Student Added!");
                nameField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "⚠ Please enter a name.");
            }
        });

        backBtn.addActionListener(e -> dashboard.goBackToMenu());
    }
}
