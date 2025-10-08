package ui;

import javax.swing.*;
import java.awt.*;
import service.AttendanceSystem;

public class MainDashboard extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private AttendanceSystem system;

    public MainDashboard() {
        system = new AttendanceSystem();

        setTitle("Attendance Monitoring System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create different panels
        JPanel menuPanel = createMenuPanel();
        JPanel addStudentPanel = new AddStudentPanel(system, this);
        JPanel addSubjectPanel = new AddSubjectPanel(system, this);
        JPanel markAttendancePanel = new MarkAttendancePanel(system, this);  // ✅ NEW
        JPanel viewAttendancePanel = new ViewAttendancePanel(system, this);

        // Add panels to card layout
        mainPanel.add(menuPanel, "menu");
        mainPanel.add(addStudentPanel, "addStudent");
        mainPanel.add(addSubjectPanel, "addSubject");
        mainPanel.add(markAttendancePanel, "markAttendance"); // ✅ NEW
        mainPanel.add(viewAttendancePanel, "viewAttendance");

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        JLabel title = new JLabel("Main Menu", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JButton addStudentBtn = new JButton("Add Student");
        JButton addSubjectBtn = new JButton("Add Subject");
        JButton markAttendanceBtn = new JButton("Mark Attendance"); // ✅
        JButton viewAttendanceBtn = new JButton("View Attendance");

        addStudentBtn.addActionListener(e -> cardLayout.show(mainPanel, "addStudent"));
        addSubjectBtn.addActionListener(e -> cardLayout.show(mainPanel, "addSubject"));
        markAttendanceBtn.addActionListener(e -> cardLayout.show(mainPanel, "markAttendance")); // ✅
        viewAttendanceBtn.addActionListener(e -> cardLayout.show(mainPanel, "viewAttendance"));

        panel.add(title);
        panel.add(addStudentBtn);
        panel.add(addSubjectBtn);
        panel.add(markAttendanceBtn); // ✅
        panel.add(viewAttendanceBtn);

        return panel;
    }

    public void goBackToMenu() {
        cardLayout.show(mainPanel, "menu");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainDashboard::new);
    }
}
