package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.service.TaskManager;
import edu.course.eventplanner.service.VenueSelector;
import edu.course.eventplanner.util.Generators;

import java.util.*;

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
        System.out.println("How many guests to generate? (1-150)");
        int numGuests = input.nextInt();
        input.nextLine();

        if (numGuests < 1 || numGuests > 150) {
            System.out.println("Invalid input, using a default of 50 guests.");
            numGuests = 50;
        }
        List<Venue> venues = Generators.generateVenues();
        venueSelector = new VenueSelector(venues);
        System.out.println("Loaded " + venues.size() + " venues:");
        for (Venue v : venues) {
        System.out.println("  - " + v.getName() + " ($" + String.format("%.0f",v.getCost()) +
                ", capacity: " + v.getCapacity() + ")");
        }
        List<Guest> generatedGuests = Generators.GenerateGuests(numGuests);
        for  (Guest guest : generatedGuests) {
            guestListManager.addGuest(guest);
        }
        System.out.println("Loaded " + numGuests + " guests:");

        // guest group for sample data
        Map<String, Integer> groupCount = new HashMap<>();
        for (Guest g : guestListManager.getAllGuests()) {
            String tag = g.getGroupTag();
            groupCount.put(tag, groupCount.getOrDefault(tag, 0) + 1);
        }
        System.out.println("  Guest breakdown:");
        for (Map.Entry<String, Integer> entry : groupCount.entrySet()) {
            System.out.println("    - " + entry.getKey() + ": " + entry.getValue());
        }

        // sample tasks
        taskManager.addTask(new Task("Send invitations."));
        taskManager.addTask(new Task("Order catering."));
        taskManager.addTask(new Task("Set up decorations."));
        System.out.println("Loaded " + taskManager.remainingTaskCount() + " preparation tasks");

        System.out.println("Sample data loaded.");
        System.out.println("You can now select a venue (Option 4) ");
    }


    private static void addGuest() {
        System.out.println("Enter Guest name: ");
        String name = input.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Invalid Guest name");
            return;
        }
        System.out.println("Enter Guest group tag (family/friends/neighbors/coworkers): ");
        String groupTag = input.nextLine().trim().toLowerCase();
        List<String> validTags = Arrays.asList("family", "friends", "neighbors", "coworkers");

        if (groupTag.isEmpty()) {
            groupTag = "untagged";
            System.out.println("No tag provided. Using 'untagged'");
        } else if (!validTags.contains(groupTag)) {
            System.out.println("Invalid tag '" + groupTag + "'. Must be: family, friends, neighbors, or coworkers");
            System.out.println("Using 'untagged' instead");
            groupTag = "untagged";
        }
        guestListManager.addGuest(new Guest(name, groupTag));
        System.out.println("Guest '" + name + "' added with tag: " + groupTag);
        System.out.println("Total guests: " + guestListManager.getGuestCount());
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
        seating = seatingPlanner.generateSeating(guestListManager.getAllGuests());
        System.out.println("Seating chart: ");
        System.out.println("Venue: " + venue.getName());
        for (Map.Entry<Integer, List<Guest>> entry : seating.entrySet()) {
            int tableNumber = entry.getKey();
            List<Guest> guests = entry.getValue();
            System.out.println("Table: " + tableNumber);
            for (Guest guest : guests) {
                System.out.println("  - " + guest.getName() + " [" + guest.getGroupTag() + "]");
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
