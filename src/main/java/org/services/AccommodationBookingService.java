package org.services;

import lombok.AllArgsConstructor;
import org.models.Accommodation;
import org.models.Booking;
import org.models.Client;
import org.models.Room;
import org.interfaces.IMenu;
import org.constants.CityData;
import org.repositories.AccommodationRepository;
import org.repositories.BookingRepository;
import org.repositories.ClientRepository;

import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
public class AccommodationBookingService implements IMenu {

    private Scanner scanner;

    @Override
    public void execute() {
        handleAccommodationBooking(scanner);
    }

    private static void handleAccommodationBooking(Scanner scanner) {
        AccommodationRepository accommodationRepository = AccommodationRepository.getInstance();

        String citySelected = getCity(scanner);

        List<Accommodation> accommodationsByCity = accommodationRepository.getAccommodationsByCity(citySelected);
        if (accommodationNotFoundByCity(accommodationsByCity, citySelected)) return;

        showAccommodationsByCity(citySelected, accommodationsByCity);

        LocalDate checkIn = getCheckInDate(scanner);
        LocalDate checkOut = getCheckOutDate(scanner, checkIn);

        String accommodationType = getAccommodationType(scanner);

        List<Accommodation> matchedAccommodations = accommodationRepository.getAccommodationsByCityAndType(citySelected, accommodationType);
        if (accommodationsNotFound(matchedAccommodations)) return;

        Accommodation selectedAccommodation = selectAccommodation(scanner, matchedAccommodations);
        Room selectedRoom = selectRoom(scanner, selectedAccommodation, checkIn, checkOut);
        if (roomNotFound(selectedRoom)) return;

        Client client = getClientInfo(scanner);

        Booking newBooking = createBooking(
                client,
                selectedAccommodation,
                selectedRoom,
                checkIn,
                checkOut,
                scanner
        );

        BookingRepository.getInstance().addBooking(newBooking);
        selectedAccommodation.addBooking(newBooking);
        client.addBooking(newBooking);

        System.out.println("\nSu reserva ha sido creada exitosamente, estos son los detalles: ");
        System.out.println(newBooking);
    }

    private static boolean roomNotFound(Room selectedRoom) {
        if (selectedRoom == null) {
            System.out.println("El alojamiento no está disponible en esas fechas.");
            return true;
        }
        return false;
    }

    private static boolean accommodationsNotFound(List<Accommodation> matchedAccommodations) {
        if (matchedAccommodations.isEmpty()) {
            System.out.println("\nNo se encontraron alojamientos disponibles para la ciudad y tipo seleccionados.");
            return true;
        }
        return false;
    }

    private static boolean accommodationNotFoundByCity(List<Accommodation> accommodationsByCity, String citySelected) {
        if (accommodationsByCity.isEmpty()) {
            System.out.println("\nNo se encontraron alojamientos disponibles para la ciudad de " + citySelected.toUpperCase());
            return true;
        }
        return false;
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
        System.out.print("\nIngrese la ciudad de su interés ");
        System.out.print(CityData.CITIES + ": ");
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

    private static Client getClientInfo(Scanner scanner) {
        ClientRepository clientRepository = ClientRepository.getInstance();

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

        LocalDate birthDate = LocalDate.of(
                additionalData.get("birthYear"),
                additionalData.get("birthMonth"),
                additionalData.get("birthDay")
        );

        return clientRepository.findClientByEmailAndBirthDate(basicData.get("email"), birthDate).orElseGet(() -> {
            Client newClient = new Client(
                    basicData.get("fullName"),
                    basicData.get("email"),
                    basicData.get("nationality"),
                    basicData.get("phoneNumber"),
                    birthDate);
            clientRepository.addClient(newClient);
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
}
