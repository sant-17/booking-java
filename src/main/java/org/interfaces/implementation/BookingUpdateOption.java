package org.interfaces.implementation;

import lombok.AllArgsConstructor;
import org.models.Booking;
import org.models.Client;
import org.interfaces.IBookingUpdateMenu;
import org.repositories.ClientRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@AllArgsConstructor
public class BookingUpdateOption implements IBookingUpdateMenu {
    private Scanner scanner;

    @Override
    public Booking execute() {
        return handleBookingUpdateOption(scanner);
    }

    private static Booking handleBookingUpdateOption(Scanner scanner) {
        scanner.nextLine();

        Client client = getClientInfo(scanner);

        if (clientWithoutBookings(client)) {
            System.out.println("NO HA HECHO NINGUNA RESERVA");
        }

        Booking booking = getBooking(scanner, client);
        if (booking == null) return null;

        System.out.println("\nHa elegido la siguiente reserva: ");
        System.out.println(booking);

        return booking;
    }

    private static boolean clientWithoutBookings(Client client) {
        return client.getBookings().isEmpty() || client == null;
    }

    private static Booking getBooking(Scanner scanner, Client client) {
        List<Booking> clientBookings = client.getBookings();

        if (clientBookings.isEmpty()) {
            return null;
        }

        System.out.println("\nElija la reserva que desea modificar");
        for (int i = 0; i < clientBookings.size(); i++) {
            System.out.println((i + 1) + ". " + client.getBookings().get(i));
        }

        System.out.print("Ingrese el número de la reserva: ");
        int numberBookingToUpdate = scanner.nextInt() - 1;

        return client.getBookings().get(numberBookingToUpdate);
    }

    private static Client getClientInfo(Scanner scanner) {
        ClientRepository clientRepository = ClientRepository.getInstance();

        HashMap<String, Integer> additionalData = new HashMap<>();

        System.out.println("\nHa elegido actualizar una reservación ");
        System.out.print("\nIngrese el correo electrónico con el que registró la reserva: ");
        String email = scanner.nextLine();

        System.out.print("Año de nacimiento: ");
        additionalData.put("birthYear", scanner.nextInt());

        System.out.print("Mes de nacimiento (número): ");
        additionalData.put("birthMonth", scanner.nextInt());

        System.out.print("Día de nacimiento: ");
        additionalData.put("birthDay", scanner.nextInt());

        LocalDate comparisonDate = LocalDate.of(
                additionalData.get("birthYear"),
                additionalData.get("birthMonth"),
                additionalData.get("birthDay")
        );

        Optional<Client> matchingClient = clientRepository.getClients().stream()
                .filter(client -> client.getEmail().equalsIgnoreCase(email) &&
                        client.getBirthDate().isEqual(comparisonDate))
                .findFirst();

        Client selectedClient = null;

        if (matchingClient.isPresent()) {
            System.out.println("\nCliente encontrado: " + matchingClient.get());
            selectedClient = matchingClient.get();
        }
        else {
            System.out.println("CLIENTE NO ENCONTRADO");
        }
        return selectedClient;
    }
}
