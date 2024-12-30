package org.repositories;

import lombok.Getter;
import org.models.DayTrip;
import org.models.factories.DayTripFactory;

import java.util.List;

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
}
