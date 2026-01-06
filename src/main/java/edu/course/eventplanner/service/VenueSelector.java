package edu.course.eventplanner.service;

import edu.course.eventplanner.model.Venue;

import java.util.*;

public class VenueSelector {
    private final List<Venue> venues;

    public VenueSelector(List<Venue> venues) {
        this.venues = venues;
    }

    public Venue selectVenue(double budget, int guestCount) {
        // filtering valid venues
        List<Venue> validVenues = new ArrayList<>();
        for (Venue venue : venues) {
            if (venue.getCost() <= budget && venue.getCapacity() >= guestCount) {
                validVenues.add(venue);
            }
        }
        if (validVenues.isEmpty()) {
            return null;
        }
        // Sort venues using custom comparator
        validVenues.sort(new Comparator<Venue>() {
            @Override
            public int compare(Venue v1, Venue v2) {
                int costComparison = Double.compare(v1.getCost(), v2.getCost());
                if (costComparison != 0) {
                    return costComparison;
                }
                return Double.compare(v1.getCapacity(), v2.getCapacity());
            }
        });
        return validVenues.get(0);

    }

}
