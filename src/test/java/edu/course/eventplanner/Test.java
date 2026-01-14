package edu.course.eventplanner;

import edu.course.eventplanner.model.Guest;
import edu.course.eventplanner.service.GuestListManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class Test {

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


}

