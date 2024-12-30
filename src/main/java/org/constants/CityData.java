package org.constants;

import java.util.List;

public class CityData {
    private CityData() {
        throw new IllegalStateException("Repository class");
    }

    public static final List<String> CITIES = List.of("Medellín", "Bogotá", "Cali");
}
