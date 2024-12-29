package org.models.factories;

import org.models.Accommodation;

import java.util.*;

public class AccommodationFactory {

    private static final Random RANDOM = new Random();

    private  AccommodationFactory() {
        throw new IllegalStateException("Factory class");
    }

    private static final List<String> HOTEL_NAMES = Arrays.asList("Hotel Las Palmas", "Hotel El Refugio", "Hotel Sol y Luna");
    private static final List<String> APARTMENT_NAMES = Arrays.asList("Apartamento Buen Vista", "Apartamento Central", "Apartamento Panorámico");
    private static final List<String> FARM_NAMES = Arrays.asList("Finca El Matorral", "Finca La Esperanza", "Finca Los Cerezos");
    private static final List<String> CITIES = Arrays.asList("Medellín", "Bogotá", "Cali");

    public static List<Accommodation> createAccommodations() {
        List<Accommodation> accommodations = new ArrayList<>();

        accommodations.addAll(createAccommodationsByType("Hotel", HOTEL_NAMES));
        accommodations.addAll(createAccommodationsByType("Apartamento", APARTMENT_NAMES));
        accommodations.addAll(createAccommodationsByType("Finca", FARM_NAMES));

        return accommodations;
    }

    private static List<Accommodation> createAccommodationsByType(String type, List<String> names) {
        List<Accommodation> accommodations = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            String city = CITIES.get(i);
            double rating = Math.round((RANDOM.nextDouble() * 4 + 1) * 10) / 10.0;

            accommodations.add(new Accommodation(name, type, city, rating));
        }

        return accommodations;
    }
}
