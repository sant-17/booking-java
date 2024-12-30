package org;

import org.interfaces.implementation.*;
import org.models.*;
import org.repositories.AccommodationRepository;
import org.repositories.BookingRepository;
import org.repositories.ClientRepository;
import org.repositories.DayTripRepository;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        displayBookings();

        boolean whileCondition = true;
        Scanner scanner = new Scanner(System.in);

        AccommodationBookingOption accommodationBookingOption = new AccommodationBookingOption(scanner);
        DayTripBookingOption dayTripBookingOption = new DayTripBookingOption(scanner);
        BookingUpdateOption bookingUpdateOption = new BookingUpdateOption(scanner);
        SwitchRoomBookingOption switchRoomBookingOption = new SwitchRoomBookingOption(scanner);
        DeleteBookingOption deleteBookingOption = new DeleteBookingOption();

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
                scanner.nextLine();
            }
        }
    }

    private static void updateBookingOption(BookingUpdateOption bookingUpdateOption, Scanner scanner, SwitchRoomBookingOption switchRoomBookingOption, DeleteBookingOption deleteBookingOption) {
        Booking clientBooking = bookingUpdateOption.execute();
        if (clientBooking == null) return;
        int selectedOption = getSelectedOptionUpdate(scanner);

        switch (selectedOption) {
            case 1 -> switchRoomBookingOption.execute(clientBooking);
            case 2 -> deleteBookingOption.execute(clientBooking);
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
        System.out.println("\n---- SISTEMAS DE RESERVAS ----");
        System.out.println("\nBienvenido al sistema de reservas, ¿qué desea hacer hoy?" +
                "\n\n1. Reservar hotel, apartamento o finca" +
                "\n2. Reservar un día de sol" +
                "\n3. Actualizar una reservación" +
                "\n4. SALIR");
    }

    private static void displayClients() {
        for (Client client : ClientRepository.getInstance().getClients()) {
            System.out.println(client);
        }
    }

    private static void displayDayTrips() {
        for (DayTrip dayTrip : DayTripRepository.getInstance().getDayTrips()) {
            System.out.println(dayTrip);
        }
    }

    private static void displayAccommodations() {
        for (Accommodation accommodation : AccommodationRepository.getInstance().getAccommodations()) {
            System.out.println(accommodation);
        }
    }

    private static void displayBookings() {
        for (Booking booking : BookingRepository.getInstance().getBookings()) {
            System.out.println(booking);
        }
    }
}