package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Task;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.SeatingPlanner;
import edu.course.eventplanner.service.TaskManager;
import edu.course.eventplanner.service.VenueSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Test {

    // GUEST LIST MANAGER TESTS
    private GuestListManager guestListManager;

    @BeforeEach
    void setUp() {
        guestListManager = new GuestListManager();
    }
    // Adding guests
    @org.junit.jupiter.api.Test()
    @DisplayName("Test add one guest")
    void testAddOneGuestList() {
        Guest guest = new  Guest("Sara", "family");
        guestListManager.addGuest(guest);
        assertEquals(1, guestListManager.getGuestCount());
    }
    @org.junit.jupiter.api.Test()
    @DisplayName("Test add multiple guests")
    void testAddMultipleGuestList() {
        guestListManager.addGuest(new  Guest("Sara", "family"));
        guestListManager.addGuest(new  Guest("Avram", "friends"));
        guestListManager.addGuest(new  Guest("Rochel", "coworkers"));
        assertEquals(3, guestListManager.getGuestCount());
    }
    // Removing Guest
    @org.junit.jupiter.api.Test()
    @DisplayName("Test removing existing guest")
    void testRemovingExistingGuest() {
        guestListManager.addGuest(new  Guest("Sara", "family"));
        boolean removed = guestListManager.removeGuest("Sara");
        assertTrue(removed);
        assertEquals(0, guestListManager.getGuestCount());
    }
    @org.junit.jupiter.api.Test()
    @DisplayName("Test removing existing guest, should decrease count")
    void testRemoveGuest_DecreaseCount() {
        guestListManager.addGuest(new  Guest("Sara", "family"));
        guestListManager.addGuest(new  Guest("Leah", "friends"));
        guestListManager.removeGuest("Leah");
        assertEquals(1, guestListManager.getGuestCount());
    }
    @org.junit.jupiter.api.Test()
    @DisplayName("Test removing non -existing guest, should return false")
    void testRemoveNonExistentGuest() {
        guestListManager.addGuest(new  Guest("Sara", "family"));
        boolean removed = guestListManager.removeGuest("Chaya");
        assertFalse(removed);
    }
    // Looking Up a Guest tests
    @org.junit.jupiter.api.Test()
    @DisplayName("Test find existing guest, should return guest")
    void testFindExistingGuest() {
        guestListManager.addGuest(new  Guest("Sara", "family"));
        Guest found = guestListManager.findGuest("Sara");
        assertNotNull(found);
        assertEquals("Sara", found.getName());
    }
    @org.junit.jupiter.api.Test()
    @DisplayName("Test find non-existing guest, should return null")
    void testFindNonExistentGuest() {
        guestListManager.addGuest(new  Guest("Sara", "family"));
        Guest found = guestListManager.findGuest("Bracha");
        assertNull(found);
    }

    @org.junit.jupiter.api.Test()
    @DisplayName("Test find guest after removal, should return null")
    void testFindGuestAfterRemoval() {
        guestListManager.addGuest(new  Guest("Sara", "family"));
        guestListManager.removeGuest("Sara");
        Guest found = guestListManager.findGuest("Sara");
        assertNull(found);
    }

    // VENUE SELECTOR TESTS
    // selecting a venue within budget
    private List<Venue> venues;
    private VenueSelector venueSelector;

    @BeforeEach
    void setUpVenueSelector() {
        venues = new ArrayList<>();
        venues.add(new Venue("Community Hall",1500,40,5,8));
        venues.add(new Venue("Garden Hall",2500,60,8,8));
        venues.add(new Venue("Grand Ballroom",5000,120,15,8));
        venueSelector = new VenueSelector(venues);
    }

    @org.junit.jupiter.api.Test()
    @DisplayName("Test select cheapest venue")
    void testSelectCheapestVenue() {
        Venue selected = venueSelector.selectVenue(10000, 30);
        assertNotNull(selected);
        assertEquals("Community Hall", selected.getName());
        assertEquals(1500, selected.getCost());
    }
    @org.junit.jupiter.api.Test()
    @DisplayName("Test select venue with smallest capacity")
    void testSelectSmallestCapacityVenue() {
        List<Venue> selectedVenues = new ArrayList<>();
        selectedVenues.add(new Venue("Hall 1",1500,40,5,8));
        selectedVenues.add(new Venue("Hall 2",1500,60,8,8));
        VenueSelector selector = new VenueSelector(selectedVenues);
        Venue selected = selector.selectVenue(2000,30);
        assertNotNull(selected);
        assertEquals("Hall 1", selected.getName());
        assertEquals(40, selected.getCapacity());
    }

    @org.junit.jupiter.api.Test()
    @DisplayName("Test select venue, budget too low, returns null")
    void testSelectVenueBudgetTooLow() {
        Venue selected = venueSelector.selectVenue(1000, 30);
        assertNull(selected);
    }

    @org.junit.jupiter.api.Test()
    @DisplayName("Test select venue, too many guests, returns null")
    void testSelectVenueTooManyGuests() {
        Venue selected = venueSelector.selectVenue(10000, 150);
        assertNull(selected);
    }

    // SEATING PLANNER TESTS
    // seating guests by group
    private Venue testVenue;
    private SeatingPlanner seatingPlanner;

    @BeforeEach
    void setUpSeatingPlanner() {
        testVenue = new Venue("Test Hall", 1000, 50, 5, 8);
        seatingPlanner = new SeatingPlanner(testVenue);
    }

    @org.junit.jupiter.api.Test()
    @DisplayName("Test seating groups by tag")
    void testSeatingGroupsByTag() {
        List<Guest> guests = Arrays.asList(
                new Guest("Sara", "family"),
                new Guest("Chaim", "friends")
        );
    }


    // TASK MANAGER TESTS
    //executing tasks
    private TaskManager taskManager;

    @BeforeEach
    void setUpTaskManager() {
        taskManager = new TaskManager();
    }

    @org.junit.jupiter.api.Test()
    @DisplayName("Executing task returns task")
    void testExecutingTask_returnsTask() {
        Task task = new Task("Send invitations");
        taskManager.addTask(task);
        Task executed = taskManager.executeNextTask();

        assertNotNull(executed);
        assertEquals("Send invitations", executed.getDescription());
    }

    @org.junit.jupiter.api.Test()
    @DisplayName("Executing task decreases remaining count")
    void testExecuteNextTask_DecreasesCount() {
        taskManager.addTask(new Task("Task 1"));
        taskManager.addTask(new Task("Task 2"));

        taskManager.executeNextTask();

        assertEquals(1, taskManager.remainingTaskCount());
    }

    @org.junit.jupiter.api.Test()
    @DisplayName("Tasks should execute in FIFO order")
    void testExecuteNextTask_FIFOOrder() {
        taskManager.addTask(new Task("First"));
        taskManager.addTask(new Task("Second"));
        taskManager.addTask(new Task("Third"));
        Task first = taskManager.executeNextTask();
        Task second = taskManager.executeNextTask();

        assertEquals("First", first.getDescription());
        assertEquals("Second", second.getDescription());
    }

    @org.junit.jupiter.api.Test()
    @DisplayName("Executing from empty queue, returns null")
    void testExecuteNextTask_EmptyQueue_ReturnsNull() {
        Task executed = taskManager.executeNextTask();

        assertNull(executed);
    }

    // Undoing tasks
    @org.junit.jupiter.api.Test()
    @DisplayName("Undoing task returns the last exectued task")
    void testUndoLastTask_ReturnsLastExecuted() {
        Task task = new Task("Send invitations");
        taskManager.addTask(task);
        taskManager.executeNextTask();

        Task undone = taskManager.undoLastTask();

        assertNotNull(undone);
        assertEquals("Send invitations", undone.getDescription());
    }

    @org.junit.jupiter.api.Test()
    @DisplayName("Undoing task should increase remaining count")
    void testUndoLastTask_IncreasesCount() {
        taskManager.addTask(new Task("Task 1"));
        taskManager.executeNextTask();
        assertEquals(0, taskManager.remainingTaskCount());

        taskManager.undoLastTask();
        assertEquals(1, taskManager.remainingTaskCount());
    }

    @org.junit.jupiter.api.Test()
    @DisplayName("Undo tasks in LIFO order")
    void testUndoLastTask_LIFOOrder() {
        taskManager.addTask(new Task("First"));
        taskManager.addTask(new Task("Second"));
        taskManager.addTask(new Task("Third"));

        taskManager.executeNextTask();
        taskManager.executeNextTask();
        taskManager.executeNextTask();

        Task undone1 = taskManager.undoLastTask();
        Task undone2 = taskManager.undoLastTask();

        assertEquals("Third", undone1.getDescription());
        assertEquals("Second", undone2.getDescription());
    }

    @org.junit.jupiter.api.Test()
    @DisplayName("Execute and undo ")
    void testExecuteAndUndo_MaintainsState() {
        taskManager.addTask(new Task("Task 1"));
        taskManager.addTask(new Task("Task 2"));

        taskManager.executeNextTask();
        taskManager.undoLastTask();

        assertEquals(2, taskManager.remainingTaskCount());
    }

}

