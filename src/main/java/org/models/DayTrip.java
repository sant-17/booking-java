package org.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
public class DayTrip {
    private String id;
    private String name;
    private Double rating;
    private Double pricePerAdult;
    private Double pricePerKid;
    private List<Booking> bookings;
    private List<String> amenities;
    private String city;

    public DayTrip(String name, Double rating, Double pricePerAdult, Double pricePerKid, List<String> amenities, String city) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.rating = rating;
        this.pricePerAdult = pricePerAdult;
        this.pricePerKid = pricePerKid;
        this.bookings = new ArrayList<>();
        this.amenities = amenities;
        this.city = city;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    public Double getTotalPrice(Integer adults, Integer kids, LocalDate date) {
        int totalPercentage = calculateTotalPercentage(date);

        double subtotal = getSubtotal(adults, kids);

        printAdjustmentMessage(totalPercentage, subtotal);

        double total = getTotal(subtotal, totalPercentage);

        System.out.println("Subtotal -> " + subtotal);
        System.out.println("TOTAL -> " + total);

        return total;
    }

    private static void printAdjustmentMessage(int totalPercentage, double subtotal) {
        if (totalPercentage > 0) {
            System.out.println("Debido a la fecha, el precio total ha aumentado un " + totalPercentage + "% = $" + calculateAdition(subtotal, totalPercentage));
        }
        if (totalPercentage < 0) {
            System.out.println("Debido a la fecha de la estadía, ha recibido un descuento del " + (-totalPercentage) + "% = $" + calculateAdition(subtotal, totalPercentage));
        }
    }

    private static double calculateAdition(double subtotal, int percentage) {
        return Math.abs((subtotal*percentage)/100);
    }

    private static double getTotal(double subtotal, int totalPercentage) {
        return (subtotal * (totalPercentage + 100)) / 100;
    }

    private double getSubtotal(Integer adults, Integer kids) {
        return (adults * pricePerAdult) + (kids * pricePerKid);
    }

    private int calculateTotalPercentage(LocalDate date) {
        int increaseLast5Days = 15;
        int increaseDays10to15 = 10;
        int discountDays5to10 = -8;

        int dayOfMonth = date.getDayOfMonth();

        if (isEndOfMonth(dayOfMonth, date.lengthOfMonth())) {
            return increaseLast5Days;
        }
        else if (isBetweenDay10And15(dayOfMonth)) {
            return increaseDays10to15;
        }
        else if (isBetweenDay5And10(dayOfMonth)) {
            return discountDays5to10;
        }

        return 0;
    }

    private static boolean isBetweenDay5And10(int dayOfMonth) {
        return dayOfMonth >= 5 && dayOfMonth <= 10;
    }

    private static boolean isBetweenDay10And15(int dayOfMonth) {
        return dayOfMonth > 10 && dayOfMonth <= 15;
    }

    private static boolean isEndOfMonth(int dayOfMonth, int daysInMonth) {
        return dayOfMonth > daysInMonth - 5;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("[ ")
                .append(" Calificación: ").append(rating)
                .append(", Precio/Adulto: ").append(pricePerAdult)
                .append(", Precio/Menor de edad: ").append(pricePerKid)
                .append(", Ciudad: ").append(city)
                .append("]\nActividades:\n");
        for (String amenity : amenities) {
            sb.append("- ").append(amenity)
                    .append("\n");
        }
        return sb.toString();
    }
}
