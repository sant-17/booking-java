package org.models.factories;

import org.models.Accommodation;
import org.models.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomFactory {

    private RoomFactory() {
        throw new IllegalStateException("Factory class");
    }

    public static void createRoomsForAccommodation(Accommodation accommodation) {
        List<Room> rooms = new ArrayList<>();

        if ("Hotel".equals(accommodation.getType())) {
            rooms.add(new Room(1, "Habitación sencilla", "Ideal para una persona, con una cama individual y baño privado.", 50.0));
            rooms.add(new Room(2, "Habitación doble", "Perfecta para parejas o amigos, incluye una cama doble y baño privado.", 80.0));
            rooms.add(new Room(3, "Habitación familiar", "Espaciosa habitación con capacidad para toda la familia, incluye múltiples camas y baño privado.", 120.0));
            rooms.add(new Room(4, "Suite", "Lujo y confort, con sala de estar, dormitorio principal y jacuzzi.", 200.0));

            accommodation.addRooms(rooms);
        } else {
            String description = generateDescriptionForAccommodationType(accommodation.getType());
            Room room = new Room(1, description, 100.0);
            accommodation.addRooms(Collections.singletonList(room));
        }
    }

    private static String generateDescriptionForAccommodationType(String type) {
        return switch (type) {
            case "Finca" -> "Amplia finca con vistas al campo, perfecta para desconectar y relajarse.";
            case "Apartamento" -> "Moderno y cómodo apartamento en el corazón de la ciudad, ideal para viajes urbanos.";
            default -> "Lugar acogedor.";
        };
    }
}
