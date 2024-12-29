package org.interfaces.implementation;

import lombok.AllArgsConstructor;
import org.models.*;
import org.interfaces.IMenu;

import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
public class DayTripBookingOption implements IMenu {

    private Scanner scanner;
    private List<Client> clients;
    private List<DayTrip> dayTrips;
    private List<Booking> bookings;

    @Override
    public void execute() {
        handleDayTripBooking(scanner, clients, dayTrips, bookings);
    }

    private static void handleDayTripBooking(Scanner scanner, List<Client> clients, List<DayTrip> dayTrips, List<Booking> bookings) {
        DayTrip selectedDayTrip = selectDayTrip(scanner, dayTrips);
        System.out.println("Ha seleccionado " + selectedDayTrip.getName());

        LocalDate dateBooking = getBookingDate(scanner);

        Client client = getClientInfo(scanner, clients);

        Booking newBookingDayTrip = createBooking(
                client,
                selectedDayTrip,
                dateBooking,
                scanner
        );

        bookings.add(newBookingDayTrip);
        selectedDayTrip.addBooking(newBookingDayTrip);
        client.addBooking(newBookingDayTrip);

        System.out.println("Su reserva ha sido creada exitosamente, estos son los detalles: ");
        System.out.println(newBookingDayTrip);
    }

    private static DayTrip selectDayTrip(Scanner scanner, List<DayTrip> dayTrips) {
        System.out.println("Estos son los complejos disponibles para un Día de Sol: ");

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
        int dayBokingDayTrip = scanner.nextInt();

        return LocalDate.of(2024, monthBookingDayTrip, dayBokingDayTrip);
    }

    private static Client getClientInfo(Scanner scanner, List<Client> clients) {
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

        Optional<Client> existingClient = clients.stream()
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
            clients.add(newClient);
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
