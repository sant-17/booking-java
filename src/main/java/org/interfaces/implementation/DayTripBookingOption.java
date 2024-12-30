package org.interfaces.implementation;

import lombok.AllArgsConstructor;
import org.models.*;
import org.interfaces.IMenu;
import org.constants.CityData;
import org.repositories.BookingRepository;
import org.repositories.ClientRepository;
import org.repositories.DayTripRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.utils.StringUtility.compareIgnoringAccentsAndCase;

@AllArgsConstructor
public class DayTripBookingOption implements IMenu {

    private Scanner scanner;

    @Override
    public void execute() {
        handleDayTripBooking(scanner);
    }

    private static void handleDayTripBooking(Scanner scanner) {
        String citySelected = getCity(scanner);

        List<DayTrip> dayTripsByCity = filterDayTripByCity(citySelected);
        if (dayTripsByCity.isEmpty()) {
            System.out.println("\nNo se encontraron alojamientos disponibles para la ciudad de " + citySelected.toUpperCase());
            return;
        }

        DayTrip selectedDayTrip = selectDayTrip(scanner, dayTripsByCity);
        System.out.println("Ha seleccionado " + selectedDayTrip.getName());

        LocalDate dateBooking = getBookingDate(scanner);

        Client client = getClientInfo(scanner);

        Booking newBookingDayTrip = createBooking(
                client,
                selectedDayTrip,
                dateBooking,
                scanner
        );

        BookingRepository.getInstance().addBooking(newBookingDayTrip);
        selectedDayTrip.addBooking(newBookingDayTrip);
        client.addBooking(newBookingDayTrip);

        System.out.println("Su reserva ha sido creada exitosamente, estos son los detalles: ");
        System.out.println(newBookingDayTrip);
    }

    private static String getCity(Scanner scanner) {
        System.out.print("\nIngrese la ciudad de su interés ");
        System.out.print(CityData.CITIES + ": ");
        scanner.nextLine();
        return scanner.nextLine();
    }

    private static List<DayTrip> filterDayTripByCity(String city) {
        return DayTripRepository.getInstance().getDayTrips().stream()
                .filter(dayTrip -> compareIgnoringAccentsAndCase(dayTrip.getCity(), city))
                .collect(Collectors.toList());
    }

    private static DayTrip selectDayTrip(Scanner scanner, List<DayTrip> dayTrips) {
        System.out.println("\nEstos son los complejos disponibles para un Día de Sol: \n");

        for (int i = 0; i < dayTrips.size(); i++) {
            System.out.println((i + 1) + ". " + dayTrips.get(i));
        }

        System.out.print("Ingrese el número del Día de Sol al que desea ir: ");
        int index = scanner.nextInt() - 1;
        return dayTrips.get(index);
    }

    private static LocalDate getBookingDate(Scanner scanner) {
        System.out.println("\nIngrese la fecha de la reserva:");
        System.out.print("Mes (número del mes): ");
        int monthBookingDayTrip = scanner.nextInt();
        System.out.print("Día: ");
        int dayBookingDayTrip = scanner.nextInt();

        return LocalDate.of(2024, monthBookingDayTrip, dayBookingDayTrip);
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

        LocalDate comparisonDateDayTripBooking = LocalDate.of(
                additionalData.get("birthYear"),
                additionalData.get("birthMonth"),
                additionalData.get("birthDay")
        );

        Optional<Client> existingClient = clientRepository.getClients().stream()
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
            clientRepository.addClient(newClient);
            System.out.println("Se han guardado sus datos para futuras reservas.");
            return newClient;
        });
    }

    private static Booking createBooking(Client client, DayTrip dayTrip, LocalDate startDate, Scanner scanner) {
        System.out.print("¿Cuántos adultos vienen? ");
        int adults = scanner.nextInt();
        System.out.print("¿Cuántos niños vienen? ");
        int kids = scanner.nextInt();

        return new Booking(client, dayTrip, startDate, adults, kids);
    }

}
