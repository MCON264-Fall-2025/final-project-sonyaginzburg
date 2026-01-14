package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.model.Venue;
import edu.course.eventplanner.service.GuestListManager;
import edu.course.eventplanner.service.VenueSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
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


}

