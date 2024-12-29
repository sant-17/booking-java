package org.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static org.utils.DateUtility.calculateDaysBetween;

@Getter
@Setter
public class Booking {
    private Client client;
    private Accommodation accommodation;
    private DayTrip dayTrip;
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer days;
    private Integer adults;
    private Integer kids;
    private Double totalPay;
    private String status;
    private RegisterBooking registerBooking;

    public Booking(Client client, Accommodation accommodation,Room room, LocalDate startDate, LocalDate endDate, Integer adults, Integer kids) {
        this.registerBooking = new RegisterBooking(startDate, endDate);
        room.addBooking(registerBooking);

        this.client = client;
        this.accommodation = accommodation;
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = calculateDaysBetween(startDate, endDate);
        this.adults = adults;
        this.kids = kids;
        this.totalPay = room.getTotalToPay(startDate, endDate);
        this.status = "Activa";
    }

    public Booking(Client client, DayTrip dayTrip, LocalDate startDate, Integer adults, Integer kids) {
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
            return "Reserva[" +
                    "\n   - Cliente: " + client.getFullName() +
                    "\n   - Alojamiento: " + accommodation.getName() + " (" + accommodation.getCity() + ")" +
                    "\n   - Descripción: " + room.getType() + " " + room.getDescription() +
                    "\n   - Check-in: " + startDate +
                    "\n   - Check-out: " + endDate +
                    "\n   - Días: " + days +
                    "\n   - Adultos: " + adults +
                    "\n   - Niños: " + kids +
                    "\n   - Pago total: $" + totalPay +
                    "\n   - Estado: '" + status + '\'' +
                    ']';
        } else {
            return "Reserva[" +
                    "\n   - Cliente: " + client.getFullName() +
                    "\n   - Alojamiento:  Día de Sol " + dayTrip.getName() + " (" + dayTrip.getCity() + ")" +
                    "\n   - Fecha: " + startDate +
                    "\n   - Adultos: " + adults +
                    "\n   - Niños: " + kids +
                    "\n   - Pago total: $" + totalPay +
                    "\n   - Estado: '" + status + '\'' +
                    ']';
        }
    }
}
