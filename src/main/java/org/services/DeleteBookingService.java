package org.services;

import lombok.AllArgsConstructor;
import org.interfaces.IModifyBookingMenu;
import org.models.Accommodation;
import org.models.Booking;
import org.models.Client;
import org.models.DayTrip;
import org.repositories.BookingRepository;

@AllArgsConstructor
public class DeleteBookingService implements IModifyBookingMenu {

    @Override
    public void execute(Booking booking) {
        handleDeleteBookingOption(booking);
    }

    public static void handleDeleteBookingOption(Booking booking) {
        Client client = booking.getClient();

        if (booking.getDayTrip() != null) {
            DayTrip dayTripToDelete = booking.getDayTrip();
            dayTripToDelete.removeBooking(booking);
        } else {
            Accommodation accommodationToDelete = booking.getAccommodation();
            accommodationToDelete.removeBooking(booking);
            booking.getRoom().removeRegisterBooking(booking.getRegisterBooking());
        }

        BookingRepository.getInstance().deleteBooking(booking);
        client.removeBooking(booking);
        System.out.println("\nSu anterior reserva ha sido cancelada. Puede proceder a realizar nuevamente su reserva. Su reserva anterior: \n" + booking);

        if (client.getBookings().isEmpty()) {
            System.out.println("\nAVISO: YA NO TIENE MÁS RESERVAS");
        } else {
            System.out.println("\nEstas son sus reservas actuales: \n" + client.getBookings());
        }
    }
}
