package org.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Room {
    private Integer id;
    private String type;
    private String description;
    private double pricePerNight;
    private List<RegisterBooking> bookings;

    public Room(Integer id, String type, String description, double pricePerNight) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.pricePerNight = pricePerNight;
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

    public double getTotalToPay(Date startDate, Date endDate) {
        Calendar startDateCal = Calendar.getInstance();
        startDateCal.setTime(startDate);
        Calendar endDateCal = Calendar.getInstance();
        endDateCal.setTime(endDate);

        int days = endDateCal.get(Calendar.DAY_OF_YEAR) - startDateCal.get(Calendar.DAY_OF_YEAR) + 1;
        System.out.println("Days: " + days);

        double subtotal = days * this.pricePerNight;

        int increaseLast5Days = 15;
        int increaseDays10to15 = 10;
        int discountDays5to10 = -8;

        int totalPercentage = 0;

        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(startDate);

        boolean appliedIncreaseLast5Days = false;
        boolean appliedIncreaseDays10to15 = false;
        boolean appliedDiscountDays5to10 = false;

        while (currentDate.before(endDateCal) || currentDate.equals(endDateCal)) {
            int dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH);

            if (dayOfMonth >= 26 && !appliedIncreaseLast5Days) {
                totalPercentage += increaseLast5Days;
                appliedIncreaseLast5Days = true;
            }

            else if (dayOfMonth >= 10 && dayOfMonth <= 15 && !appliedIncreaseDays10to15) {
                totalPercentage += increaseDays10to15;
                appliedIncreaseDays10to15 = true;
            }

            else if (dayOfMonth >= 5 && dayOfMonth <= 10 && !appliedDiscountDays5to10) {
                totalPercentage += discountDays5to10;
                appliedDiscountDays5to10 = true;
            }

            currentDate.add(Calendar.DAY_OF_MONTH, 1);
        }

        double finalPrice = (subtotal * (totalPercentage + 100))/100;

        return finalPrice;
    }

}
