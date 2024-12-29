package org.repository;

import java.util.Arrays;
import java.util.List;

public class CityRepository {
    private CityRepository() {
        throw new IllegalStateException("Repository class");
    }

    public static final List<String> CITIES = Arrays.asList("Medellín", "Bogotá", "Cali");
}
