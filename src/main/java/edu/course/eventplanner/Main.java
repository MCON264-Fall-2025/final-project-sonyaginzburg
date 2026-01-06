package edu.course.eventplanner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Event Planner Mini — see README for instructions.");
        System.out.println("========================================");
        System.out.println("       EVENT MANAGEMENT SYSTEM          ");
        System.out.println("========================================");
        System.out.println("1. Load sample data");
        System.out.println("2. Add guest");
        System.out.println("3. Remove guest");
        System.out.println("4. Select venue");
        System.out.println("5. Generate seating chart");
        System.out.println("6. Add preparation task");
        System.out.println("7. Execute next task");
        System.out.println("8. Undo last task");
        System.out.println("9. Print event summary");
        System.out.println("0. Exit");
        System.out.println("========================================");
        System.out.print("Enter your choice: ");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 0:
                System.exit(0);
                break;
            case 1:
                loadSampleData;
                break;
            case 2:
                addGuest;
                break;
            case 3:
                removeGuest;
                break;
            case 4:
                selectVenue;
                break;
            case 5:
                generateSeatingChart;
                break;
            case 6:
                addTask;
                break;
            case 7:
                executeTask;
                break;
            case 8:
                undoTask;
            case 9:
                printEventSummary;
                break;
            default:
                System.out.println("Invalid choice. Please choose a number 0-9.");
        }
    }
}
