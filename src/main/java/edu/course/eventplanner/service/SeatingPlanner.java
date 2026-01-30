package edu.course.eventplanner.service;

import edu.course.eventplanner.model.*;

import java.util.*;

public class SeatingPlanner {
    private final Venue venue;

    public SeatingPlanner(Venue venue) {
        this.venue = venue;
    }
    // rules guests with group tags sit together whenever possible
    public Map<Integer, List<Guest>> generateSeating(List<Guest> guests) {
        Map<String, Queue<Guest>> groupQueues = new HashMap<>();
        // for each guest, get their tag and add them to that tag queue
        for (Guest guest : guests) {
            String tag = guest.getGroupTag();
            // If there's no tag assigned
            if (tag == null) {
                tag = "untagged";
            }
            // creating a new queue if it doesnt exist
            if (!groupQueues.containsKey(tag)) {
                groupQueues.put(tag, new LinkedList<>());
            }
            // adding guests to their tag queue
            groupQueues.get(tag).add(guest);
        }
        // Then get table capacity, (they actually all have the same capacity but I could add new tables)
        int tableCapacity = venue.getCapacity();
        if  (tableCapacity <= 0) {
            tableCapacity = 8;
        }
        // Create seating chart
        Map<Integer, List<Guest>> seatingChart = new HashMap<>();
        int tableNumber = 1;
        List<Guest> currentTable = new ArrayList<>();

        // fill the tables by going thru each group until the queues are empty
        for (Queue<Guest> groupQueue : groupQueues.values()) {
            while (!groupQueue.isEmpty()) {
                Guest guest = groupQueue.poll();
                currentTable.add(guest);
                // check if table is full
                if (currentTable.size() >= tableCapacity) {
                    seatingChart.put(tableNumber, currentTable);
                    tableNumber++;
                    // make new table arraylist
                    currentTable = new ArrayList<>();
                }
            }
            if (!currentTable.isEmpty()) {
                seatingChart.put(tableNumber, currentTable);
                tableNumber++;
                currentTable = new ArrayList<>();
            }
        }
        return seatingChart;
    }

}
