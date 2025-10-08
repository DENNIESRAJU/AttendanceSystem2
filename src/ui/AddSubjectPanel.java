package ui;

import javax.swing.*;
import java.awt.*;
import service.AttendanceSystem;

class AddSubjectPanel extends JPanel {
    public AddSubjectPanel(AttendanceSystem system, MainDashboard dashboard) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));  
        // Panel aligned to top-left

        // Inner panel with vertical stacking
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Subject Name
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JLabel nameLabel = new JLabel("Subject Name:");
        JTextField nameField = new JTextField(15);
        row1.add(nameLabel);
        row1.add(nameField);

        // Total Classes
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        JLabel totalLabel = new JLabel("Total Classes:");
        JTextField totalField = new JTextField(15);
        row2.add(totalLabel);
        row2.add(totalField);

        // Buttons row
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton saveBtn = new JButton("Save");
        JButton backBtn = new JButton("⬅ Back");
        buttonRow.add(saveBtn);
        buttonRow.add(backBtn);

        // Add rows into form
        form.add(row1);
        form.add(row2);
        form.add(buttonRow);

        add(form);

        // Save button action
        saveBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                int total = Integer.parseInt(totalField.getText().trim());

                if (!name.isEmpty()) {
                    system.addSubject(name, total);
                    JOptionPane.showMessageDialog(this, "✅ Subject Added!");
                    nameField.setText("");
                    totalField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "⚠ Please enter subject name.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "⚠ Enter valid number for total classes.");
            }
        });

        // Back button action
        backBtn.addActionListener(e -> dashboard.goBackToMenu());
    }
}
