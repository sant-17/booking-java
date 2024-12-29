package org.models.factories;

import org.models.*;

import java.time.LocalDate;
import java.util.*;

import static org.utils.DateUtility.generateRandomDateFromYear;

public class BookingFactory {

    private static final Random RANDOM = new Random();

    private  BookingFactory() {
        throw new IllegalStateException("Factory class");
    }

    public static List<Booking> createBookingsForDayTrips(List<Client> clients, List<DayTrip> dayTrips) {
        List<Booking> bookings = new ArrayList<>();

        for (Client client : clients) {
            DayTrip dayTrip = getRandomDayTrip(dayTrips);

            int adults = RANDOM.nextInt(5) + 1;
            int kids = RANDOM.nextInt(6);

            LocalDate startDate = generateRandomDateFromYear(RANDOM, 2024);

            Booking booking = new Booking(client, dayTrip, startDate, adults, kids);
            dayTrip.addBooking(booking);
            client.addBooking(booking);

            bookings.add(booking);
        }

        return bookings;
    }

    public static List<Booking> createBookingsForAccommodations(List<Client> clients, List<Accommodation> accommodations) {
        List<Booking> bookings = new ArrayList<>();

        Iterator<Accommodation> accommodationIterator = accommodations.iterator();

        for (Client client : clients) {
            if (!accommodationIterator.hasNext()) {
                break;
            }

            Accommodation accommodation = accommodationIterator.next();
            List<Room> rooms = accommodation.getRooms();
            Room room = getRandomRoom(rooms);

            int adults = RANDOM.nextInt(5) + 1;
            int kids = RANDOM.nextInt(6);

            LocalDate startDate = generateRandomDateFromYear(RANDOM, 2024);
            LocalDate endDate = generateRandomEndDate(startDate);

            Booking booking = new Booking(client, accommodation, room, startDate, endDate, adults, kids);

            accommodation.addBooking(booking);
            client.addBooking(booking);
            room.getBookings().add(new RegisterBooking(startDate, endDate));

            bookings.add(booking);
        }

        return bookings;
    }

    private static DayTrip getRandomDayTrip(List<DayTrip> list) {
        return list.get(BookingFactory.RANDOM.nextInt(list.size()));
    }

    private static Room getRandomRoom(List<Room> rooms) {
        return rooms.get(BookingFactory.RANDOM.nextInt(rooms.size()));
    }

    private static LocalDate generateRandomEndDate(LocalDate startDate) {
        int extraDays = BookingFactory.RANDOM.nextInt(7) + 1;
        return startDate.plusDays(extraDays);
    }
}
