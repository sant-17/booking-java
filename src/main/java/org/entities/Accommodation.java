package org.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
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

    public List<Room> getAvailableRooms(Date startDate, Date endDate) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : this.rooms) {
            if (room.isAvailable(startDate, endDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Alojamiento [ID: ").append(id)
                .append(", Nombre: ").append(name)
                .append(", Tipo: ").append(type)
                .append(", Calificación: ").append(rating)
                .append("]\nHabitaciones:\n");
        for (Room room : rooms) {
            sb.append("- Habitación ID: ").append(room.getId())
                    .append(", Tipo: ").append(room.getType())
                    .append(", Descripción: ").append(room.getDescription())
                    .append(", Precio/Noche: $").append(room.getPricePerNight())
                    .append("\n");
        }
        sb.append("Reservas:\n");
        for (Booking booking : bookings) {
            sb.append("- Reserva para: ").append(booking.getClient().getFullName())
                    .append(", Habitación: ").append(booking.getRoom().getId())
                    .append(", Check in: ").append(booking.getStartDate())
                    .append(", Check out: ").append(booking.getEndDate())
                    .append(", Pago total: $").append(booking.getTotalPay())
                    .append("\n");
        }
        return sb.toString();
    }
}
