package org.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Room {
    private Integer id;
    private String type;
    private String description;
    private double price;
    private List<RegisterBooking> bookings;

    public Room(Integer id, String type, String description, double price) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.price = price;
        this.bookings = new ArrayList<>();
    }

    public void addBooking(RegisterBooking register) {
        this.bookings.add(register);
    }

    public Boolean isAvailable(Date startDate, Date endDate) {
        for (RegisterBooking booking : bookings) {
            if (!(endDate.before(booking.getStartDate()) || startDate.after(booking.getEndDate()))){
                return false;
            }
        }
        return true;
    }
}
