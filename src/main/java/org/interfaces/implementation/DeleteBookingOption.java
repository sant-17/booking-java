package org.interfaces.implementation;

import lombok.AllArgsConstructor;
import org.dto.ClientAndBookingDTO;
import org.interfaces.IModifyBookingMenu;
import org.models.Accommodation;
import org.models.Booking;
import org.models.Client;
import org.models.DayTrip;

import java.util.List;

@AllArgsConstructor
public class DeleteBookingOption implements IModifyBookingMenu {
    private List<Booking> bookings;

    @Override
    public void execute(ClientAndBookingDTO clientAndBookingDTO) {
        handleDeleteBookingOption(bookings, clientAndBookingDTO);
    }

    public static void handleDeleteBookingOption(List<Booking> bookings, ClientAndBookingDTO clientAndBookingDTO) {
        Booking booking = clientAndBookingDTO.getBooking();
        Client client = clientAndBookingDTO.getClient();

        if (booking.getDayTrip() != null) {
            DayTrip dayTripToDelete = booking.getDayTrip();
            dayTripToDelete.getBookings().remove(booking);
        } else {
            Accommodation accommodationToDelete = booking.getAccommodation();
            accommodationToDelete.getBookings().remove(booking);
            booking.getRoom().getBookings().remove(booking.getRegisterBooking());
        }

        bookings.remove(booking);
        client.getBookings().remove(booking);
        System.out.println("Su anterior reserva ha sido cancelada. Puede proceder a realizar nuevamente su reserva. Su reserva anterior: \n" + booking);
        System.out.println("Estas son sus reservas actuales: \n" + client.getBookings());
    }
}
