package edu.course.eventplanner.service;

import edu.course.eventplanner.model.*;

import java.util.*;

public class SeatingPlanner {
    private final Venue venue;

    public SeatingPlanner(Venue venue) {
        this.venue = venue;
    }
    // TODO review this whole section
    // rules guests with group tags sit together whenever possible
    public Map<Integer, List<Guest>> generateSeating(List<Guest> guests) {
        Map<String, Queue<Guest>> seatingChart = new HashMap<>();
        // for each guest, get their tag and add them to that tag queue
        for (Guest guest : guests) {
            String tag = guest.getGroupTag();
            // If there's no tag assigned
            if (tag == null) {
                tag = "untagged";
            }
            // creating a new queue if it doesnt exist
            if (!seatingChart.containsKey(guest.getGroupTag())) {
                seatingChart.put(guest.getGroupTag(), new LinkedList<>());
            }
            // adding guests to their tag queue
            seatingChart.get(tag).add(guest);

        }
        // Then get table capacity, they actually all have the same capacity but I will add new tables
        int tableCapacity = venue.getCapacity();
        if  (tableCapacity <= 0) {
            tableCapacity = 8;
        }

        int tableNumber = 1;
        List<Guest> currentTable = new ArrayList<>();

        // fill the tables by going thru each group until the queues are empty

        // Instructions: the returned map must associate table number with the guests seated at the table
        //finish this
        return null;
    }

}
