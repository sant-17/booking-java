package org.services;

import lombok.AllArgsConstructor;
import org.interfaces.IModifyBookingMenu;
import org.models.Accommodation;
import org.models.Booking;
import org.models.Client;
import org.models.Room;
import org.repositories.BookingRepository;

import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class SwitchRoomBookingService implements IModifyBookingMenu {
    private Scanner scanner;

    @Override
    public void execute(Booking booking) {
        handleSwitchRoomBookingOption(scanner, booking);
    }

    private static void handleSwitchRoomBookingOption(Scanner scanner, Booking booking) {
        Client client = booking.getClient();

        if (booking.getDayTrip() != null) {
            System.out.println("NO PUEDE CAMBIAR HABITACIÓN DE UN DÍA DE SOL");
            return;
        }

        Accommodation accommodationToUpdate = booking.getAccommodation();

        Room newRoom = selectRoom(scanner, accommodationToUpdate, booking);
        if (newRoom == null) {
            System.out.println("\nNo hay habitaciones disponibles para hacer un cambio");
            return;
        }

        System.out.println("\nHa seleccionado la siguiente habitación: " + newRoom);

        deleteBooking(booking, accommodationToUpdate, client);

        Booking bookingUpdate = new Booking(
                client,
                accommodationToUpdate,
                newRoom,
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getAdults(),
                booking.getKids()
        );
        bookingUpdate.setStatus("Actualizado");

        booking.getAccommodation().addBooking(bookingUpdate);
        client.addBooking(bookingUpdate);

        System.out.println("Así quedó su reserva: ");
        System.out.println(bookingUpdate);
    }

    private static void deleteBooking(Booking booking, Accommodation accommodationToUpdate, Client client) {
        accommodationToUpdate.removeBooking(booking);
        booking.getRoom().removeRegisterBooking(booking.getRegisterBooking());
        client.removeBooking(booking);
        BookingRepository.getInstance().deleteBooking(booking);
    }

    private static Room selectRoom(Scanner scanner, Accommodation accommodationToUpdate, Booking booking) {
        List<Room> availableRooms = accommodationToUpdate.getAvailableRooms(booking.getStartDate(), booking.getEndDate());

        if (availableRooms.isEmpty()) {
            return null;
        }

        System.out.println("\nElija una nueva habitación (puede corresponder a gastos adicionales): ");
        for (int i = 0; i < availableRooms.size(); i++) {
            System.out.println((i + 1) + ". " + availableRooms.get(i));
        }

        System.out.print("Ingrese el número de la habitación: ");
        int newRoomNumber = scanner.nextInt() - 1;
        return availableRooms.get(newRoomNumber);
    }
}
