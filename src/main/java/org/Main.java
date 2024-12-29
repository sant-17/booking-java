package org;

import org.dto.ClientAndBookingDTO;
import org.interfaces.implementation.*;
import org.models.*;
import org.models.factories.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<Client> clientsList = createAndDisplayClients();
        List<DayTrip> dayTripList = createAndDisplayDayTrips();
        List<Accommodation> accommodationList = createAndDisplayAccommodations();
        createRoomsForAccommodations(accommodationList);
        List<Booking> bookings = createAndDisplayBookings(clientsList, dayTripList, accommodationList);

        boolean whileCondition = true;
        Scanner scanner = new Scanner(System.in);

        AccommodationBookingOption accommodationBookingOption = new AccommodationBookingOption(scanner, clientsList, accommodationList, bookings);
        DayTripBookingOption dayTripBookingOption = new DayTripBookingOption(scanner, clientsList, dayTripList, bookings);
        BookingUpdateOption bookingUpdateOption = new BookingUpdateOption(scanner, clientsList);
        SwitchRoomBookingOption switchRoomBookingOption = new SwitchRoomBookingOption(scanner, bookings);
        DeleteBookingOption deleteBookingOption = new DeleteBookingOption(bookings);

        while (whileCondition) {
            try {
                displayMenu();
                System.out.print("Ingrese una opción: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1 -> accommodationBookingOption.execute();
                    case 2 -> dayTripBookingOption.execute();
                    case 3 -> updateBookingOption(bookingUpdateOption, scanner, switchRoomBookingOption, deleteBookingOption);
                    case 4 -> whileCondition = false;
                    default -> System.out.println("OPCIÓN NO VÁLIDA");
                }
            } catch (Exception e) {
                System.out.println("HUBO UN ERROR, INTENTE NUEVAMENTE");
            }
        }
    }

    private static void updateBookingOption(BookingUpdateOption bookingUpdateOption, Scanner scanner, SwitchRoomBookingOption switchRoomBookingOption, DeleteBookingOption deleteBookingOption) {
        ClientAndBookingDTO clientAndBookingDTO = bookingUpdateOption.execute();
        if (clientAndBookingDTO == null) return;
        int selectedOption = getSelectedOptionUpdate(scanner);

        switch (selectedOption) {
            case 1 -> switchRoomBookingOption.execute(clientAndBookingDTO);
            case 2 -> deleteBookingOption.execute(clientAndBookingDTO);
            default -> System.out.println("OPCIÓN NO VÁLIDA");
        }
    }


    private static int getSelectedOptionUpdate(Scanner scanner) {
        System.out.print("\n¿Qué desea hacer?" +
                "\n1. Cambiar habitación" +
                "\n2. Cambiar alojamiento" +
                "\nElija una opción: ");
        return scanner.nextInt();
    }

    private static void displayMenu() {
        System.out.println("---- SISTEMAS DE RESERVAS ----");
        System.out.println("\nBienvenido al sistema de reservas," +
                "\n¿Qué desea hacer hoy?" +
                "\n\n1. Reservar hotel, apartamento o finca" +
                "\n2. Reservar un día de sol" +
                "\n3. Actualizar una reservación" +
                "\n4. SALIR");
    }

    private static List<Client> createAndDisplayClients() {
        List<Client> clientsList = ClientFactory.createClients(5);
        for (Client client : clientsList) {
            System.out.println(client);
        }
        return clientsList;
    }

    private static List<DayTrip> createAndDisplayDayTrips() {
        List<DayTrip> dayTripList = DayTripFactory.createDayTrips(3);
        for (DayTrip dayTrip : dayTripList) {
            System.out.println(dayTrip);
        }
        return dayTripList;
    }

    private static List<Accommodation> createAndDisplayAccommodations() {
        List<Accommodation> accommodationList = AccommodationFactory.createAccommodations();
        for (Accommodation accommodation : accommodationList) {
            System.out.println(accommodation);
        }
        return accommodationList;
    }

    private static void createRoomsForAccommodations(List<Accommodation> accommodations) {
        for (Accommodation accommodation : accommodations) {
            RoomFactory.createRoomsForAccommodation(accommodation);
        }
    }

    private static List<Booking> createAndDisplayBookings(
            List<Client> clientsList,
            List<DayTrip> dayTripList,
            List<Accommodation> accommodationList) {

        List<Booking> bookings = BookingFactory.createBookingsForDayTrips(clientsList, dayTripList);
        bookings.addAll(BookingFactory.createBookingsForAccommodations(clientsList, accommodationList));

        for (Booking booking : bookings) {
            System.out.println(booking);
        }

        return bookings;
    }
}