package org.entities;

import lombok.Getter;
import lombok.Setter;

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
    private Integer adults;
    private Integer kids;
    private double totalPay;
    private String status;

    public Booking(Client client, Accommodation accommodation,Room room, Date startDate, Date endDate, Integer adults, Integer kids) {
        this.client = client;
        this.accommodation = accommodation;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
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
        this.totalPay = dayTrip.getTotalPrice(adults, kids);
        this.status = "Activa";
    }
}
