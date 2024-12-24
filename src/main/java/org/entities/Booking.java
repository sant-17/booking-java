package org.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class Booking {
    private Client client;
    private Accommodation accommodation;
    private DayTrip dayTrip;
    private Room room;
    private Date startDate;
    private Date endDate;
    private Integer days;
    private Integer adults;
    private Integer kids;
    private double totalPay;
    private String status;
    private RegisterBooking registerBooking;

    public Booking(Client client, Accommodation accommodation,Room room, Date startDate, Date endDate, Integer adults, Integer kids) {
        this.registerBooking = new RegisterBooking(startDate, endDate);
        room.addBooking(registerBooking);

        Calendar startDateCal = Calendar.getInstance();
        startDateCal.setTime(startDate);
        Calendar endDateCal = Calendar.getInstance();
        endDateCal.setTime(endDate);

        this.client = client;
        this.accommodation = accommodation;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = endDateCal.get(Calendar.DAY_OF_YEAR) - startDateCal.get(Calendar.DAY_OF_YEAR) + 1;
        this.adults = adults;
        this.kids = kids;
        this.totalPay = room.getTotalToPay(startDate, endDate);
        this.status = "Activa";
    }

    public Booking(Client client, DayTrip dayTrip, Date startDate, Integer adults, Integer kids) {
        this.client = client;
        this.dayTrip = dayTrip;
        this.startDate = startDate;
        this.adults = adults;
        this.kids = kids;
        this.totalPay = dayTrip.getTotalPrice(adults, kids, startDate);
        this.status = "Activa";
    }

    @Override
    public String toString() {
        if (accommodation != null) {
            return "Reserva{" +
                    "Cliente=" + client.getFullName() +
                    ", Alojamiento=" + accommodation.getName() +
                    ", Habitación=" + room.getType() + " " + room.getDescription() +
                    ", Check In=" + startDate +
                    ", Check Out=" + endDate +
                    ", Días=" + days +
                    ", Adultos=" + adults +
                    ", Niños=" + kids +
                    ", Pago total=$" + totalPay +
                    ", Estado='" + status + '\'' +
                    '}';
        } else {
            return "Reserva{" +
                    "Cliente=" + client.getFullName() +
                    ", Alojamiento= Día de Sol " + dayTrip.getName() +
                    ", Fecha=" + startDate +
                    ", Adultos=" + adults +
                    ", Niños=" + kids +
                    ", Pago total=$" + totalPay +
                    ", Estado='" + status + '\'' +
                    '}';
        }
    }
}
