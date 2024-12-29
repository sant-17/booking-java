package org.interfaces.implementation;

import lombok.AllArgsConstructor;
import org.dto.ClientAndBookingDTO;
import org.interfaces.IModifyBookingMenu;
import org.models.Accommodation;
import org.models.Booking;
import org.models.Client;
import org.models.Room;

import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class SwitchRoomBookingOption implements IModifyBookingMenu {
    private Scanner scanner;
    private List<Booking> bookings;

    @Override
    public void execute(ClientAndBookingDTO clientAndBookingDTO) {
        handleSwitchRoomBookingOption(scanner, bookings, clientAndBookingDTO);
    }

    private static void handleSwitchRoomBookingOption(Scanner scanner, List<Booking> bookings, ClientAndBookingDTO clientAndBookingDTO) {
        Booking booking = clientAndBookingDTO.getBooking();
        Client client = clientAndBookingDTO.getClient();

        if (booking.getDayTrip() != null) {
            System.out.println("NO PUEDE CAMBIAR HABITACIÓN DE UN DÍA DE SOL");
            return;
        }

        Accommodation accommodationToUpdate = clientAndBookingDTO.getBooking().getAccommodation();

        Room newRoom = selectRoom(scanner, accommodationToUpdate, booking);

        System.out.println("Ha seleccionado la siguiente habitación: ");
        System.out.println(newRoom);

        accommodationToUpdate.getBookings().remove(booking);
        booking.getRoom().getBookings().remove(booking.getRegisterBooking());
        client.getBookings().remove(booking);
        bookings.remove(booking);

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

        System.out.println("Así quedó tu reserva: ");
        System.out.println(bookingUpdate);
    }

    private static Room selectRoom(Scanner scanner, Accommodation accommodationToUpdate, Booking booking) {
        System.out.println("Elija una nueva habitación (puede corresponder a gastos adicionales): ");
        List<Room> availableRooms = accommodationToUpdate.getAvailableRooms(booking.getStartDate(), booking.getEndDate());
        for (int i = 0; i < availableRooms.size(); i++) {
            System.out.println((i + 1) + ". " + availableRooms.get(i));
        }

        System.out.print("Ingrese el número de la habitación: ");
        int newRoomNumber = scanner.nextInt() - 1;
        return availableRooms.get(newRoomNumber);
    }
}
