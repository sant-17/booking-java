package org.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class DayTrip {
    private String id;
    private String name;
    private double rating;
    private double price;
    private List<Booking> bookings;
    private List<Amenity> amenities;

    public DayTrip(String name, double rating, double price) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.bookings = new ArrayList<>();
        this.amenities = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    public void addAmenity(Amenity amenity) {
        this.amenities.add(amenity);
    }
}
