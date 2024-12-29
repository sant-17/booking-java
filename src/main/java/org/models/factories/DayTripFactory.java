package org.models.factories;

import org.models.DayTrip;
import org.repository.CityRepository;

import java.util.*;

public class DayTripFactory {

    private static final Random RANDOM = new Random();

    private DayTripFactory() {
        throw new IllegalStateException("Utility class");
    }

    private static final List<String> DAY_TRIP_NAMES = Arrays.asList(
            "Aventura Acuática", "Relax Natural", "Diversión Familiar", "Tour Cultural", "Escapada a la Montaña",
            "Recorrido Histórico", "Rumbo a la Playa", "Aventura en la Selva", "Paseo en Globo", "Safari Nocturno"
    );

    private static final List<String> AMENITIES = Arrays.asList(
            "Disfrutar de la piscina", "Relajarse en la playa", "Practicar deportes acuáticos",
            "Hacer senderismo por los alrededores", "Disfrutar de una barbacoa en el jardín",
            "Visita guiada a monumentos", "Degustación de comida local", "Clases de yoga al amanecer",
            "Observación de aves", "Paseo en barco"
    );

    private static final List<Double> ADULT_PRICES = Arrays.asList(50.0, 80.0, 120.0, 150.0, 180.0);
    private static final List<Double> KID_PRICES = Arrays.asList(20.0, 30.0, 40.0, 50.0, 60.0);

    public static List<DayTrip> createDayTrips(int numberOfDayTrips) {
        List<DayTrip> dayTrips = new ArrayList<>();
        int availableTrips = Math.min(numberOfDayTrips, DAY_TRIP_NAMES.size());
        Set<String> usedNames = new HashSet<>();

        for (int i = 0; i < availableTrips; i++) {
            String name = getUniqueRandomElement(usedNames);
            double rating = generateRandomRating();
            double pricePerAdult = generateRandomAdultPrice();
            double pricePerKid = generateRandomKidPrice(pricePerAdult);
            String city = getRandomCity();
            List<String> amenities = generateRandomAmenities();

            dayTrips.add(new DayTrip(name, rating, pricePerAdult, pricePerKid, amenities, city));
        }

        return dayTrips;
    }

    private static double generateRandomRating() {
        double rating = 1.0 + (4.0 * DayTripFactory.RANDOM.nextDouble());
        return Math.round(rating * 10.0) / 10.0;
    }

    public static String getRandomCity() {
        int index = DayTripFactory.RANDOM.nextInt(CityRepository.CITIES.size());
        return CityRepository.CITIES.get(index);
    }

    private static double generateRandomAdultPrice() {
        return ADULT_PRICES.get(DayTripFactory.RANDOM.nextInt(ADULT_PRICES.size()));
    }

    private static double generateRandomKidPrice(double pricePerAdult) {
        List<Double> validKidPrices = new ArrayList<>();
        for (double price : KID_PRICES) {
            if (price <= pricePerAdult) {
                validKidPrices.add(price);
            }
        }
        if (validKidPrices.isEmpty()) {
            validKidPrices.add(KID_PRICES.get(0));
        }
        return validKidPrices.get(DayTripFactory.RANDOM.nextInt(validKidPrices.size()));
    }

    private static List<String> generateRandomAmenities() {
        List<String> selectedAmenities = new ArrayList<>();
        int amenitiesCount = 1 + DayTripFactory.RANDOM.nextInt(3);
        Set<String> availableAmenities = new HashSet<>(AMENITIES);

        for (int i = 0; i < amenitiesCount; i++) {
            String amenity = getRandomElement(new ArrayList<>(availableAmenities));
            selectedAmenities.add(amenity);
            availableAmenities.remove(amenity);
        }
        return selectedAmenities;
    }

    private static String getUniqueRandomElement(Set<String> usedNames) {
        String name;
        do {
            name = DayTripFactory.DAY_TRIP_NAMES.get(DayTripFactory.RANDOM.nextInt(DayTripFactory.DAY_TRIP_NAMES.size()));
        } while (usedNames.contains(name));
        usedNames.add(name);
        return name;
    }

    private static String getRandomElement(List<String> list) {
        return list.get(DayTripFactory.RANDOM.nextInt(list.size()));
    }
}