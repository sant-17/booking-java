package org.models.factories;

import org.models.Client;

import java.time.LocalDate;
import java.util.*;

import static org.utils.DateUtility.generateRandomDateFromYear;

public class ClientFactory {

    private static final Random RANDOM = new Random();

    private  ClientFactory() {
        throw new IllegalStateException("Factory class");
    }

    private static final List<String> NAMES = Arrays.asList("Juan", "María", "Carlos", "Laura", "Andrés", "Ana");
    private static final List<String> SURNAMES = Arrays.asList("Perez", "Gomez", "Rodriguez", "Martinez", "Garcia", "Lopez");
    private static final List<String> NATIONALITIES = Arrays.asList("Colombiana", "Mexicana", "Argentina", "Chilena", "Peruana", "Venezolana");

    public static List<Client> createClients() {
        int numberOfClients = 5;

        List<Client> clients = new ArrayList<>();

        for (int i = 0; i < numberOfClients; i++) {
            String fullName = getRandomElement(NAMES) + " " + getRandomElement(SURNAMES);
            String email = generateEmail(fullName);
            String nationality = getRandomElement(NATIONALITIES);
            String phoneNumber = generatePhoneNumber();
            LocalDate birthDate = generateRandomDateFromYear(RANDOM, 1999);

            clients.add(new Client(fullName, email, nationality, phoneNumber, birthDate));
        }

        return clients;
    }

    private static String getRandomElement(List<String> list) {
        return list.get(ClientFactory.RANDOM.nextInt(list.size()));
    }

    private static String generateEmail(String fullName) {
        String[] parts = fullName.toLowerCase().split(" ");
        String domain = "@example.com";
        return parts[0] + "." + parts[1] + ClientFactory.RANDOM.nextInt(100) + domain;
    }

    private static String generatePhoneNumber() {
        StringBuilder phoneNumber = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            phoneNumber.append(ClientFactory.RANDOM.nextInt(10));
        }
        return phoneNumber.toString();
    }
}
