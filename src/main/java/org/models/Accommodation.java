package org.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Accommodation {
    private String id;
    private String name;
    private String type;
    private String city;
    private Double rating;
    private List<Room> rooms;
    private List<Booking> bookings;

    public Accommodation(String name, String type, String city, Double rating) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.type = type;
        this.city = city;
        this.rating = rating;
        this.rooms = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public void addRooms(List<Room> rooms) {
        this.rooms.addAll(rooms);
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    public List<Room> getAvailableRooms(LocalDate startDate, LocalDate endDate) {
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
        sb.append("Alojamiento [Nombre: ").append(name)
                .append(", Tipo: ").append(type)
                .append(", Ciudad: ").append(city)
                .append(", Calificación: ").append(rating)
                .append(" ]");

        if (!rooms.isEmpty()) {
            sb.append("\nHabitaciones:\n");
            for (Room room : rooms) {
                sb.append("- ID: ").append(room.getId());

                if (!Objects.equals(room.getType(), "")) {
                    sb.append(", Tipo: ").append(room.getType());
                }

                sb.append(", Descripción: ").append(room.getDescription())
                        .append(", Precio/Noche: $").append(room.getPricePerNight())
                        .append("\n");
            }
        }

        if (!bookings.isEmpty()) {
            sb.append("Reservas:\n");
            for (Booking booking : bookings) {
                sb.append("- Reserva para: ").append(booking.getClient().getFullName())
                        .append(", Se hospeda en: ").append(booking.getRoom().getType().isEmpty() ? booking.getRoom().getDescription() : booking.getRoom().getType())
                        .append(", Check-in: ").append(booking.getStartDate())
                        .append(", Check-out: ").append(booking.getEndDate())
                        .append(", Pago total: $").append(booking.getTotalPay())
                        .append("\n");
            }
        }
        return sb.toString();
    }
}
