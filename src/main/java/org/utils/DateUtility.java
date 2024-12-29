package org.utils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class DateUtility {

    private DateUtility() {
        throw new IllegalStateException("Utility class");
    }

    public static Integer calculateDaysBetween(LocalDate startDate, LocalDate endDate) {
        return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    public static LocalDate generateRandomDateFromYear(Random random, int year) {
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(YearMonth.of(year, month).lengthOfMonth()) + 1;
        return LocalDate.of(year, month, day);
    }
}
