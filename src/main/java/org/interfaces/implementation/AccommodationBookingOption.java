package org.interfaces.implementation;

import lombok.AllArgsConstructor;
import org.models.Accommodation;
import org.models.Booking;
import org.models.Client;
import org.models.Room;
import org.interfaces.IMenu;

import java.text.Normalizer;
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

        List<Accommodation> accommodationsByCity = filterAccommodationsByCity(accommodationList, citySelected);
        if (accommodationsByCity.isEmpty()) {
            System.out.println("\nNo se encontraron alojamientos disponibles para la ciudad de " + citySelected.toUpperCase());
            return;
        }

        showAccommodationsByCity(citySelected, accommodationsByCity);

        String accommodationType = getAccommodationType(scanner);

        List<Accommodation> matchedAccommodations = filterAccommodationsByCityAndType(accommodationList, citySelected, accommodationType);
        if (matchedAccommodations.isEmpty()) {
            System.out.println("\nNo se encontraron alojamientos disponibles para la ciudad y tipo seleccionados.");
            return;
        }

        Accommodation selectedAccommodation = selectAccommodation(scanner, matchedAccommodations);
        Room selectedRoom = selectRoom(scanner, selectedAccommodation, checkIn, checkOut);
        if (selectedRoom == null) {
            System.out.println("El alojamiento no está disponible en esas fechas.");
            return;
        }

        Client client = getClientInfo(scanner, clientsList);

        Booking newBooking = createBooking(
                client,
                selectedAccommodation,
                selectedRoom,
                checkIn,
                checkOut,
                scanner
        );

        bookings.add(newBooking);
        selectedAccommodation.addBooking(newBooking);
        client.addBooking(newBooking);

        System.out.println("\nSu reserva ha sido creada exitosamente, estos son los detalles: ");
        System.out.println(newBooking);
    }

    private static void showAccommodationsByCity(String citySelected, List<Accommodation> accommodationsByCity) {
        System.out.println("\nEstos son los alojamientos disponibles para la ciudad de " + citySelected.toUpperCase());
        for (Accommodation accommodation : accommodationsByCity) {
            System.out.println("- " + accommodation.getName());
        }
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

        LocalDate checkOut = LocalDate.of(2024, checkIn.getMonthValue(), dayCheckOut);
        System.out.println("\nCheck-in: " + checkIn + " | Check-out: " + checkOut);
        return checkOut;
    }

    private static String getCity(Scanner scanner) {
        System.out.print("\nIngrese la ciudad de su interés: ");
        scanner.nextLine();
        return scanner.nextLine();
    }

    private static String getAccommodationType(Scanner scanner) {
        System.out.print("\n¿Qué alojamiento desea?" +
                "\n1. Hotel" +
                "\n2. Apartamento" +
                "\n3. Finca" +
                "\nIngrese el número del tipo de alojamiento que desea: ");
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

    private static List<Accommodation> filterAccommodationsByCity(List<Accommodation> accommodations, String city) {
        return accommodations.stream()
                .filter(accommodation -> compareIgnoringAccentsAndCase(accommodation.getCity(), city))
                .collect(Collectors.toList());
    }

    private static List<Accommodation> filterAccommodationsByCityAndType(List<Accommodation> accommodations, String city, String type) {
        return accommodations.stream()
                .filter(accommodation -> compareIgnoringAccentsAndCase(accommodation.getCity(), city) && accommodation.getType().equals(type))
                .collect(Collectors.toList());
    }

    private static Accommodation selectAccommodation(Scanner scanner, List<Accommodation> accommodations) {
        System.out.println("\nOpciones disponibles:");
        for (int i = 0; i < accommodations.size(); i++) {
            System.out.println((i + 1) + ". " + accommodations.get(i).getName());
        }
        System.out.print("Elija el número del alojamiento que desea: ");
        int index = scanner.nextInt() - 1;
        return accommodations.get(index);
    }

    private static Room selectRoom(Scanner scanner, Accommodation accommodation, LocalDate checkIn, LocalDate checkOut) {
        List<Room> availableRooms = accommodation.getAvailableRooms(checkIn, checkOut);

        if (availableRooms.isEmpty()) {
            return null;
        }

        System.out.println("\nHabitaciones disponibles:");
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

        System.out.println("\nIngrese sus datos personales");
        System.out.print("- Nombre completo: ");
        scanner.nextLine();
        basicData.put("fullName", scanner.nextLine());
        System.out.print("- Correo electrónico: ");
        basicData.put("email", scanner.nextLine());
        System.out.print("- Nacionalidad: ");
        basicData.put("nationality", scanner.nextLine());
        System.out.print("- Número de teléfono: ");
        basicData.put("phoneNumber", scanner.nextLine());

        System.out.print("- Año de nacimiento: ");
        additionalData.put("birthYear", scanner.nextInt());
        System.out.print("- Mes de nacimiento (número): ");
        additionalData.put("birthMonth", scanner.nextInt());
        System.out.print("- Día de nacimiento: ");
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
            System.out.println("Se han guardado sus datos para futuras reservas.");
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

    private static boolean compareIgnoringAccentsAndCase(String firstStr, String secondStr){
        String firstNormalizedStr = Normalizer.normalize(firstStr, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase();
        String secondNormalizedStr = Normalizer.normalize(secondStr, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase();

        return firstNormalizedStr.equals(secondNormalizedStr);
    }

}
