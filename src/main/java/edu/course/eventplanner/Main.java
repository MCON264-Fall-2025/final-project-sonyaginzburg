package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner myScanner = new Scanner(System.in);
        int choice = myScanner.nextInt();
        switch (choice) {
            case 0:
                System.exit(0);
                break;


        }

    }
}
