package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.service.TaskManager;
import edu.course.eventplanner.service.VenueSelector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static GuestListManager guestListManager =  new GuestListManager();
    private static VenueSelector venueSelector;
    private static SeatingPlanner seatingPlanner;
    private static TaskManager taskManager =  new TaskManager();
    private static Scanner input = new Scanner(System.in);

    private static Venue venue;
    private static Map<Integer, List<Guest>> seating;

    public static void main(String[] args) {
        System.out.println("Welcome to the Event Planner Application");
        boolean running = true;
        while (running) {
            displayMenu();
            System.out.print("Enter your choice: ");
            int choice = input.nextInt();
            input.nextLine();
            System.out.println();

            switch (choice) {
                case 1:
                    loadSampleData();
                    break;
                case 2:
                    addGuest();
                    break;
                case 3:
                    removeGuest();
                    break;
                case 4:
                    selectVenue();
                    break;
                case 5:
                    generateSeatingChart();
                    break;
                case 6:
                    addTask();
                    break;
                case 7:
                    executeNextTask();
                    break;
                case 8:
                    undoLastTask();
                    break;
                case 9:
                    printEventSummary();
                    break;
                case 0:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            if (running) {
                System.out.println("\nPress enter to continue...");
                input.nextLine();
            }
        }
        input.close();
    }

    private static void displayMenu() {
        System.out.println("-----MENU-----");
        System.out.println("1. Load sample data");
        System.out.println("2. Add Guest");
        System.out.println("3. Remove Guest");
        System.out.println("4. Select Venue");
        System.out.println("5. Generate Seating Chart");
        System.out.println("6. Add preparation task");
        System.out.println("7. Execute next task");
        System.out.println("8. Undo last task");
        System.out.println("9. Print event summary");
        System.out.println("0. Exit");
    }

    private static void loadSampleData() {
        System.out.println("Load sample data...");
        // TODO implement this with the generators

    }
    private static void addGuest() {
        System.out.println("Enter Guest name: ");
        String name = input.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Invalid Guest name");
            return;
        }
        System.out.println("Enter Guest group tag (family/friends/neighbors/coworkers): ");
        String groupTag = input.nextLine().trim();
        if (groupTag.isEmpty()) {
            groupTag = "untagged";
        }
        guestListManager.addGuest(new Guest(name, groupTag));
        System.out.println("Guest added");
        seating = null;
    }

    private static void removeGuest() {
        if (guestListManager.getGuestCount() == 0) {
            System.out.println("Guest list is empty, no guests to remove.");
            return;
        }
        System.out.println("Current guests: ");
        List<Guest> allGuests = guestListManager.getAllGuests();
        for (int i = 0; i < allGuests.size(); i++) {
            System.out.println((i+1) + ". " + allGuests.get(i).getName());
        }
        System.out.println("Guest name to remove: ");
        String name = input.nextLine().trim();

        if (guestListManager.removeGuest(name)) {
            System.out.println("Guest removed");
            seating = null;
        } else {
            System.out.println("Guest not found");
        }
    }

    private static void selectVenue() {
        if (venueSelector == null) {
            System.out.println("Please load sample data first");
            return;
        }
        if (guestListManager.getGuestCount() == 0) {
            System.out.println("Please add guests first (Option 2)");
            return;
        }
        System.out.println("Enter your budget ($): ");
        double budget = input.nextDouble();
        input.nextLine();

        int guestCount = guestListManager.getGuestCount();
        System.out.println("Searching for venue for " + guestCount + " guests...");

        venue = venueSelector.selectVenue(budget, guestCount);

        if (venue != null) {
            System.out.println("Venue: " + venue.getName());
            System.out.println("Cost: " + venue.getCost());
            System.out.println("Capacity: " + venue.getCapacity());

            seatingPlanner = new SeatingPlanner(venue);
            seating = null;
        } else {
            System.out.println("Venue not found");

        }

    }
    private static void generateSeatingChart() {
        if (venue == null){
            System.out.println("Please select a Venue first");
            return;
        }
        if (guestListManager.getGuestCount() == 0) {
            System.out.println("No guests to seat, please add some guests");
            return;
        }
        System.out.println("Seating chart: ");
        System.out.println("Venue: " + venue.getName());
        for (Map.Entry<Integer, List<Guest>> entry : seating.entrySet()) {
            int tableNumber = entry.getKey();
            List<Guest> guests = entry.getValue();
            System.out.println("Guest: " + tableNumber);
            for (Guest guest : guests) {
                System.out.println("Guest: " + guest.getName()+ " , group: " + guest.getGroupTag());
            }
        }
        System.out.println("Total tables: " + seating.size());
    }

    private static void addTask() {
        System.out.println("Enter task: ");
        String task = input.nextLine().trim();
        if (task.isEmpty()) {
            System.out.println("Task cannot be empty");
            return;
        }
        taskManager.addTask(new Task(task));
        System.out.println("Task added");
        System.out.println("Remaining tasks: " + taskManager.remainingTaskCount());
    }

    private static void executeNextTask() {
        if (taskManager.remainingTaskCount() == 0) {
            System.out.println("No tasks to execute");
            return;
        }
        Task task = taskManager.executeNextTask();
        if (task != null ) {
            System.out.println("Task " + task.getDescription() + " executed!");
        }
    }

    private static void undoLastTask() {
        Task task = taskManager.undoLastTask();
        if (task != null) {
            System.out.println("Task " + task.getDescription() + " undone");

        } else {
            System.out.println("NO tasks to undo");
        }
    }

    private static void printEventSummary() {
        System.out.println("Event summary:");
        System.out.println("--------------");
        System.out.println("\nGUESTS: " + guestListManager.getGuestCount());
        if (guestListManager.getGuestCount() > 0) {
            Map<String, Integer> groups = new HashMap<>();
            for (Guest guest : guestListManager.getAllGuests()) {
                String tag = guest.getGroupTag();
                groups.put(tag, groups.getOrDefault(tag, 0) + 1);
            }
            for  (Map.Entry<String, Integer> entry : groups.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
        System.out.println("\nVENUE: " +
                (venue != null ? venue.getName() : "No venue selected"));
        if  (venue != null) {
            System.out.println("Cost: " + venue.getCost());
            System.out.println("Capacity: " + venue.getCapacity());
        }
            System.out.println("\nSEATING CHART: " +
                    (seating != null ? "Generated " + seating.size() + " tables": "Not generated"));

        System.out.println("\nTASKS: " + taskManager.remainingTaskCount() + " remaining");
    }


}
