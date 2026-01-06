package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Guest;

import java.util.*;

public class GuestListManager {
    private final LinkedList<Guest> guests = new LinkedList<>();
    private final Map<String, Guest> guestByName = new HashMap<>();

    // something goes here
    public GuestListManager() {
    }

    public void addGuest(Guest guest) {
        if (guest == null) return;
        if (guestByName.containsKey(guest.getName())) {
            return;
        }
        guests.add(guest);
        guestByName.put(guest.getName(), guest);
    }

    public boolean removeGuest(String guestName) {
        Guest  guest = guestByName.get(guestName);
        if (guest == null) return false;
        guests.remove(guest); // removing from list
        guestByName.remove(guestName); // removing from map
        return true;
    }

    public Guest findGuest(String guestName) {
        if (guestName == null) return null;
        return guestByName.get(guestName);
    }

    public int getGuestCount() {
        return guests.size();
    }
    public List<Guest> getAllGuests() {
        return guests;
    }

}
