package edu.course.eventplanner.util;

import edu.course.eventplanner.model.*;
import java.util.*;

public class Generators {
    public static List<Venue> generateVenues() {
        return List.of(
                // add some venues for more variety for testing
         new Venue("Community Hall",1500,40,5,8),
         new Venue("Garden Hall",2500,60,8,8),
         new Venue("Grand Ballroom",5000,120,15,8),
         new Venue("Rooftop Terrace", 3500, 80, 10, 8),
         new Venue("Lakeside Pavilion", 4000, 100, 12, 8),
         new Venue("Historic Manor", 6000, 150, 18, 8),
         new Venue("Coastal Banquet Hall", 3000, 70, 9, 8),
         new Venue("Downtown Loft", 2000, 50, 6, 8)
        );
    }
    public static List<Guest> GenerateGuests(int n) {
        List<Guest> guests = new ArrayList<>();
        String[] groups = {"family","friends","neighbors","coworkers"};
        for(int i=1;i<=n;i++){
            guests.add(new Guest("Guest"+i, groups[i%groups.length]));
        }
        return guests;
    }
}
