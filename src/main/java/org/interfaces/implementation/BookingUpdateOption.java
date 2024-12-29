package org.interfaces.implementation;

import lombok.AllArgsConstructor;
import org.dto.ClientAndBookingDTO;
import org.models.Booking;
import org.models.Client;
import org.interfaces.IBookingUpdateMenu;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@AllArgsConstructor
public class BookingUpdateOption implements IBookingUpdateMenu {
    private Scanner scanner;
    private List<Client> clients;

    @Override
    public ClientAndBookingDTO execute() {
        return handleBookingUpdateOption(scanner, clients);
    }

    private static ClientAndBookingDTO handleBookingUpdateOption(Scanner scanner, List<Client> clients) {
        scanner.nextLine();

        Client client = getClientInfo(scanner, clients);
        if (client == null) return null;

        if (client.getBookings().isEmpty()) {
            System.out.println("NO HA HECHO NINGUNA RESERVA");
        }

        Booking booking = getBooking(scanner, client);

        System.out.println("Ha elegido la siguiente reserva: ");
        System.out.println(booking);

        return new ClientAndBookingDTO(client, booking);
    }

    private static Booking getBooking(Scanner scanner, Client client) {
        System.out.println("Elija la reserva que desea modificar");
        for (int i = 0; i < client.getBookings().size(); i++) {
            System.out.println((i + 1) + ". " + client.getBookings().get(i));
        }

        System.out.print("Ingrese el número de la reserva: ");
        int numberBookingToUpdate = scanner.nextInt() - 1;
        return client.getBookings().get(numberBookingToUpdate);
    }

    private static Client getClientInfo(Scanner scanner, List<Client> clients) {
        HashMap<String, Integer> additionalData = new HashMap<>();

        System.out.println("Ha elegido actualizar una reservación ");
        System.out.println("\nIngrese el correo electrónico con el que registró la reserva: ");
        String email = scanner.nextLine();

        System.out.print("Año de nacimiento: ");
        additionalData.put("birthYear", scanner.nextInt());

        System.out.print("Mes de nacimiento: ");
        additionalData.put("birthMonth", scanner.nextInt());

        System.out.print("Día de nacimiento: ");
        additionalData.put("birthDay", scanner.nextInt());

        LocalDate comparisonDate = LocalDate.of(
                additionalData.get("birthYear"),
                additionalData.get("birthMonth"),
                additionalData.get("birthDay")
        );

        Optional<Client> matchingClient = clients.stream()
                .filter(client -> client.getEmail().equalsIgnoreCase(email) &&
                        client.getBirthDate().isEqual(comparisonDate))
                .findFirst();

        Client selectedClient = null;

        if (matchingClient.isPresent()) {
            System.out.println("Cliente encontrado: " + matchingClient.get());
            selectedClient = matchingClient.get();
        }
        else {
            System.out.println("CLIENTE NO ENCONTRADO");
        }
        return selectedClient;
    }
}
