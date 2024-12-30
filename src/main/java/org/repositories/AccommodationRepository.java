package org.repositories;

import lombok.Getter;
import org.models.Accommodation;
import org.models.factories.AccommodationFactory;
import org.models.factories.RoomFactory;

import java.util.List;

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

}