package org.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DayTrip {
    private String id;
    private String name;
    private double rating;
    private List<Booking> bookings;

    public DayTrip(String id, String name, double rating) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.rating = rating;
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
}
