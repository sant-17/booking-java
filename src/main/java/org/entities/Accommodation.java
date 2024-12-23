package org.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Accommodation {
    private String id;
    private String name;
    private String type;
    private double rating;
    private List<Room> rooms;
    private List<Booking> bookings;

    public Accommodation(String name, String type, double rating) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.rating = rating;
        this.rooms = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
}
