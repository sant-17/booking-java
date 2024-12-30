package org.repositories;

import lombok.Getter;
import org.models.DayTrip;
import org.models.factories.DayTripFactory;

import java.util.List;
import java.util.stream.Collectors;

import static org.utils.StringUtility.compareIgnoringAccentsAndCase;

public class DayTripRepository {
    private static DayTripRepository instance;

    @Getter
    private List<DayTrip> dayTrips;

    private DayTripRepository() {
        this.dayTrips = DayTripFactory.createDayTrips();
    }

    public static DayTripRepository getInstance() {
        if (instance == null) {
            instance = new DayTripRepository();
        }

        return instance;
    }

    public void addDayTrip(DayTrip dayTrip) {
        dayTrips.add(dayTrip);
    }

    public List<DayTrip> findDayTripByCity(String city) {
        return dayTrips.stream()
                .filter(dayTrip -> compareIgnoringAccentsAndCase(dayTrip.getCity(), city))
                .collect(Collectors.toList());
    }
}
