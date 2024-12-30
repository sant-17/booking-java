package org.repositories;

import lombok.Getter;
import org.models.Booking;
import org.models.factories.BookingFactory;

import java.util.List;

public class BookingRepository {
    private static BookingRepository instance;

    @Getter
    private List<Booking> bookings;

    private BookingRepository() {
        this.bookings = BookingFactory.createBookingsForDayTrips(
                ClientRepository.getInstance().getClients(),
                DayTripRepository.getInstance().getDayTrips());

        this.bookings.addAll(BookingFactory.createBookingsForAccommodations(
                ClientRepository.getInstance().getClients(),
                AccommodationRepository.getInstance().getAccommodations()
        ));
    }

    public static BookingRepository getInstance() {
        if (instance == null) {
            instance = new BookingRepository();
        }

        return instance;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void deleteBooking(Booking booking) {
        bookings.remove(booking);
    }
}
