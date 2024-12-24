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

        Boolean haAumentado = false;
        Boolean haDisminuido = false;

        if (dayOfMonth >= 26) {
            totalPercentage = increaseLast5Days;
            haAumentado = true;
        }
        else if (dayOfMonth > 10 && dayOfMonth <= 15) {
            totalPercentage = increaseDays10to15;
            haAumentado = true;
        }
        else if (dayOfMonth >= 5 && dayOfMonth <= 10) {
            totalPercentage = discountDays5to10;
            haDisminuido = true;
        }

        double subtotal = (adults * pricePerAdult) + (kids * pricePerKid);

        if (haAumentado) {
            System.out.println("Debido a la fecha, el precio total ha aumentado un " + totalPercentage + "% = $" + ((subtotal*totalPercentage)/100));
        }
        if (haDisminuido) {
            System.out.println("Debido a la fecha de la estadía, ha recibido un descuento del " + (-totalPercentage) + "% = $" + (-(subtotal*totalPercentage)/100));
        }

        System.out.println("Subtotal -> " + subtotal);
        System.out.println("TOTAL -> " + (subtotal * (totalPercentage + 100))/100);

        return (subtotal * (totalPercentage + 100))/100;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Día de Sol [ID: ").append(id)
                .append(", Nombre: ").append(name)
                .append(", Calificación: ").append(rating)
                .append("]\nActividades:\n");
        for (Amenity amenity : amenities) {
            sb.append("- Nombre: ").append(amenity.getName())
                    .append("\n");
        }
        sb.append("Reservas:\n");
        for (Booking booking : bookings) {
            sb.append("- Reserva para: ").append(booking.getClient().getFullName())
                    .append(", Fecha: ").append(booking.getStartDate())
                    .append(", Pago total: $").append(booking.getTotalPay())
                    .append("\n");
        }
        return sb.toString();
    }
}
