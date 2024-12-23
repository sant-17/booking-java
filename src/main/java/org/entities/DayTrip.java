package org.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class DayTrip {
    private String id;
    private String name;
    private double rating;
    private double pricePerAdult;
    private double pricePerKid;
    private List<Booking> bookings;
    private List<Amenity> amenities;

    public DayTrip(String name, double rating, double pricePerAdult, double pricePerKid) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.rating = rating;
        this.pricePerAdult = pricePerAdult;
        this.pricePerKid = pricePerKid;
        this.bookings = new ArrayList<>();
        this.amenities = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    public void addAmenity(Amenity amenity) {
        this.amenities.add(amenity);
    }

    public double getTotalPrice(Integer adults, Integer kids, Date date) {
        int increaseLast5Days = 15;
        int increaseDays10to15 = 10;
        int discountDays5to10 = -8;
        int totalPercentage = 0;

        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(date);

        int dayOfMonth = calendarDate.get(Calendar.DAY_OF_MONTH);

        if (dayOfMonth >= 26) {
            totalPercentage = increaseLast5Days;
        }
        else if (dayOfMonth >= 10 && dayOfMonth <= 15) {
            totalPercentage = increaseDays10to15;
        }
        else if (dayOfMonth >= 5 && dayOfMonth <= 10) {
            totalPercentage = discountDays5to10;
        }

        return (((adults * pricePerAdult) + (kids * pricePerKid)) * (totalPercentage + 100))/100;
    }
}
