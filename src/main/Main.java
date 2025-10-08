package main;

import service.AttendanceSystem;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AttendanceSystem system = new AttendanceSystem();

        while (true) {
            System.out.println("\n=== Attendance Monitoring System ===");
            System.out.println("1. Add Student");
            System.out.println("2. Add Subject");
            System.out.println("3. Mark Attendance");
            System.out.println("4. View Attendance");
            System.out.println("5. View All Students");
            System.out.println("6. Clear All Attendance");
            System.out.println("7. Exit");

            System.out.print("Enter choice: ");
            String input = sc.nextLine();   // read as text
            int choice;

            try {
                choice = Integer.parseInt(input); // convert to number
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid input! Please enter a number.");
                continue;   // back to menu
            }

            switch (choice) {
                case 1:
                    System.out.print("Student Name: ");
                    String name = sc.nextLine();
                    system.addStudent(name);
                    break;

                case 2:
                    try {
                        System.out.print("Subject Name: ");
                        String sub = sc.nextLine();
                        System.out.print("Total Classes: ");
                        int total = Integer.parseInt(sc.nextLine());
                        system.addSubject(sub, total);   // updated
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Please enter a valid number for total classes.");
                    }
                    break;

                case 3:
                    try {
                        System.out.print("Student ID: ");
                        int sid = Integer.parseInt(sc.nextLine());
                        System.out.print("Subject ID: ");
                        int subid = Integer.parseInt(sc.nextLine());
                        System.out.print("Classes Attended: ");
                        int attended = Integer.parseInt(sc.nextLine());
                        system.markAttendance(sid, subid, attended);   // updated
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Please enter numbers only for IDs and counts.");
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Student ID: ");
                        int sid2 = Integer.parseInt(sc.nextLine());
                        system.viewAttendance(sid2);
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Invalid student ID!");
                    }
                    break;

                case 5:
                    system.viewAllStudents();
                    break;

                case 6:
                    system.clearAttendance();
                    break;

                case 7:
                    System.out.println("✅ Exiting...");
                    return;

                default:
                    System.out.println("❌ Invalid choice.");
            }
        }
    }
}
