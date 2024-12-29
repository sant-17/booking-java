package org.interfaces.implementation;

import lombok.AllArgsConstructor;
import org.models.Accommodation;
import org.models.Booking;
import org.models.Client;
import org.models.Room;
import org.interfaces.IMenu;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AccommodationBookingOption implements IMenu {

    private Scanner scanner;
    private List<Client> clients;
    private List<Accommodation> accommodations;
    private List<Booking> bookings;

    @Override
    public void execute() {
        handleAccommodationBooking(scanner, clients, accommodations, bookings);
    }

    private static void handleAccommodationBooking(Scanner scanner, List<Client> clientsList,
                                                   List<Accommodation> accommodationList, List<Booking> bookings) {
        LocalDate checkIn = getCheckInDate(scanner);
        LocalDate checkOut = getCheckOutDate(scanner, checkIn);
        String citySelected = getCity(scanner);
        String accommodationType = getAccommodationType(scanner);

        List<Accommodation> matchedAccommodations = filterAccommodations(accommodationList, citySelected, accommodationType);
        if (matchedAccommodations.isEmpty()) {
            System.out.println("No se encontraron alojamientos disponibles para la ciudad y tipo seleccionados.");
            return;
        }

        Accommodation selectedAccommodation = selectAccommodation(scanner, matchedAccommodations);
        Room selectedRoom = selectRoom(scanner, selectedAccommodation, checkIn, checkOut);

        Client client = getClientInfo(scanner, clientsList);

        Booking newBooking = createBooking(
                client,
                selectedAccommodation,
                selectedRoom,
                checkIn,
                checkOut,
                scanner);

        bookings.add(newBooking);
        selectedAccommodation.addBooking(newBooking);
        client.addBooking(newBooking);

        System.out.println("Su reserva ha sido creada exitosamente, estos son los detalles: ");
        System.out.println(newBooking);
    }

    private static LocalDate getCheckInDate(Scanner scanner) {
        System.out.println("\nIngrese la fecha del CHECK-IN:");
        System.out.print("Mes (número del mes): ");
        int monthCheckInAndOut = scanner.nextInt();
        System.out.print("Día: ");
        int dayCheckIn = scanner.nextInt();
        return LocalDate.of(2024, monthCheckInAndOut, dayCheckIn);
    }

    private static LocalDate getCheckOutDate(Scanner scanner, LocalDate checkIn) {
        System.out.println("\nIngrese la fecha del CHECK-OUT:");
        int dayCheckOut;
        do {
            System.out.print("Día: ");
            dayCheckOut = scanner.nextInt();
            if (dayCheckOut <= checkIn.getDayOfMonth()) {
                System.out.println("FECHA INCORRECTA, EL DÍA DEL CHECKOUT NO PUEDE SER EL MISMO DÍA O ANTES DEL DÍA DE CHECK-IN");
            }
        } while (dayCheckOut <= checkIn.getDayOfMonth());
        return LocalDate.of(2024, checkIn.getMonthValue(), dayCheckOut);
    }

    private static String getCity(Scanner scanner) {
        System.out.print("Ingrese la ciudad de su interés: ");
        scanner.nextLine();
        return scanner.nextLine();
    }

    private static String getAccommodationType(Scanner scanner) {
        System.out.print("\n¿Qué alojamiento desea?" +
                "\n1. Hotel" +
                "\n2. Apartamento" +
                "\n3. Finca" +
                "\nIngrese el número del alojamiento: ");
        int type = scanner.nextInt();
        return switch (type) {
            case 1 -> "Hotel";
            case 2 -> "Apartamento";
            case 3 -> "Finca";
            default -> {
                System.out.println("No se ha elegido una opción válida.");
                yield "";
            }
        };
    }

    private static List<Accommodation> filterAccommodations(List<Accommodation> accommodations, String city, String type) {
        return accommodations.stream()
                .filter(accommodation -> accommodation.getCity().equalsIgnoreCase(city) && accommodation.getType().equals(type))
                .collect(Collectors.toList());
    }

    private static Accommodation selectAccommodation(Scanner scanner, List<Accommodation> accommodations) {
        System.out.println("Opciones disponibles:");
        for (int i = 0; i < accommodations.size(); i++) {
            System.out.println((i + 1) + ". " + accommodations.get(i).getName());
        }
        System.out.print("Elija el número del alojamiento que desea: ");
        int index = scanner.nextInt() - 1;
        return accommodations.get(index);
    }

    private static Room selectRoom(Scanner scanner, Accommodation accommodation, LocalDate checkIn, LocalDate checkOut) {
        List<Room> availableRooms = accommodation.getAvailableRooms(checkIn, checkOut);
        System.out.println("Habitaciones disponibles:");
        for (int i = 0; i < availableRooms.size(); i++) {
            System.out.println((i + 1) + ". " + availableRooms.get(i));
        }
        System.out.print("Elija la habitación que desea: ");
        int index = scanner.nextInt() - 1;
        return availableRooms.get(index);
    }

    private static Client getClientInfo(Scanner scanner, List<Client> clientsList) {
        HashMap<String, String> basicData = new HashMap<>();
        HashMap<String, Integer> additionalData = new HashMap<>();

        System.out.println("\nIngrese sus datos personales:");
        System.out.print("Nombre completo: ");
        scanner.nextLine();
        basicData.put("fullName", scanner.nextLine());
        System.out.print("Correo electrónico: ");
        basicData.put("email", scanner.nextLine());
        System.out.print("Nacionalidad: ");
        basicData.put("nationality", scanner.nextLine());
        System.out.print("Número de teléfono: ");
        basicData.put("phoneNumber", scanner.nextLine());

        System.out.print("Año de nacimiento: ");
        additionalData.put("birthYear", scanner.nextInt());
        System.out.print("Mes de nacimiento: ");
        additionalData.put("birthMonth", scanner.nextInt());
        System.out.print("Día de nacimiento: ");
        additionalData.put("birthDay", scanner.nextInt());

        LocalDate comparisonDateDayTripBooking = LocalDate.of(
                additionalData.get("birthYear"),
                additionalData.get("birthMonth"),
                additionalData.get("birthDay")
        );

        Optional<Client> existingClient = clientsList.stream()
                .filter(client -> client.getEmail().equalsIgnoreCase(basicData.get("email")) &&
                        client.getBirthDate().isEqual(comparisonDateDayTripBooking))
                .findFirst();

        return existingClient.orElseGet(() -> {
            Client newClient = new Client(
                    basicData.get("fullName"),
                    basicData.get("email"),
                    basicData.get("nationality"),
                    basicData.get("phoneNumber"),
                    comparisonDateDayTripBooking);
            clientsList.add(newClient);
            return newClient;
        });
    }

    private static Booking createBooking(Client client, Accommodation accommodation, Room room, LocalDate checkIn, LocalDate checkOut, Scanner scanner) {
        System.out.print("¿Cuántos adultos vienen? ");
        int adults = scanner.nextInt();
        System.out.print("¿Cuántos niños vienen? ");
        int kids = scanner.nextInt();

        return new Booking(client, accommodation, room, checkIn, checkOut, adults, kids);
    }

}
