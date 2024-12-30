package org.repositories;

import lombok.Getter;
import org.models.Accommodation;
import org.models.factories.AccommodationFactory;
import org.models.factories.RoomFactory;

import java.util.List;
import java.util.stream.Collectors;

import static org.utils.StringUtility.compareIgnoringAccentsAndCase;

public class AccommodationRepository {
    private static AccommodationRepository instance;

    @Getter
    private List<Accommodation> accommodations;

    private AccommodationRepository() {
        this.accommodations = AccommodationFactory.createAccommodations();

        for (Accommodation accommodation : this.accommodations) {
            RoomFactory.createRoomsForAccommodation(accommodation);
        }
    }

    public static AccommodationRepository getInstance() {
        if (instance == null) {
            instance = new AccommodationRepository();
        }

        return instance;
    }

    public List<Accommodation> getAccommodationsByCity(String city) {
        return accommodations.stream()
                .filter(accommodation -> compareIgnoringAccentsAndCase(accommodation.getCity(), city))
                .collect(Collectors.toList());
    }

    public List<Accommodation> getAccommodationsByCityAndType(String city, String type) {
        return accommodations.stream()
                .filter(accommodation -> compareIgnoringAccentsAndCase(accommodation.getCity(), city)
                        && accommodation.getType().equals(type))
                .collect(Collectors.toList());
    }

}