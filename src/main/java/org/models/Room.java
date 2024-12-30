package org.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.utils.DateUtility.calculateDaysBetween;

@Getter
@Setter
public class Room {
    private Integer id;
    private String type;
    private String description;
    private Double pricePerNight;
    private List<RegisterBooking> bookings;


    public Room(Integer id, String type, String description, Double pricePerNight) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.bookings = new ArrayList<>();
    }


    public Room(Integer id, String description, Double pricePerNight) {
        this.id = id;
        this.type = "";
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.bookings = new ArrayList<>();
    }


    public void addRegisterBooking(RegisterBooking register) {
        this.bookings.add(register);
    }


    public void removeRegisterBooking(RegisterBooking register) {
        this.bookings.remove(register);
    }


    public Boolean isAvailable(LocalDate startDate, LocalDate endDate) {
        for (RegisterBooking booking : bookings) {
            if (!(endDate.isBefore(booking.getStartDate()) || startDate.isAfter(booking.getEndDate()))){
                return false;
            }
        }
        return true;
    }


    public Double getTotalToPay(LocalDate startDate, LocalDate endDate) {
        int days = calculateDaysBetween(startDate, endDate);

        double subtotal = days * this.pricePerNight;

        int totalPercentage = calculateTotalPercentage(startDate, endDate);

        printAdjustmentMessage(totalPercentage, subtotal);

        double total = calculateTotal(subtotal, totalPercentage);

        System.out.println("Subtotal -> " + subtotal);
        System.out.println("TOTAL -> " + total);

        return total;
    }


    private void printAdjustmentMessage(int totalPercentage, double subtotal) {
        String message = getAdjustmentMessage(totalPercentage, subtotal);
        if (!message.isEmpty()) {
            System.out.println(message);
        }
    }


    private String getAdjustmentMessage(int totalPercentage, double subtotal) {
        if (totalPercentage == 0) {
            return "";
        }

        return String.format("Debido a la fecha, la tarifa tendrá un %s del %d%% = $%.2f",
                getAdjustmentType(totalPercentage),
                Math.abs(totalPercentage),
                calculateAdjustmentAmount(totalPercentage, subtotal));
    }


    private String getAdjustmentType(int totalPercentage) {
        return totalPercentage > 0 ? "aumento" : "descuento";
    }


    private double calculateAdjustmentAmount(int totalPercentage, double subtotal) {
        return Math.abs((subtotal * totalPercentage) / 100);
    }


    private int calculateTotalPercentage(LocalDate startDate, LocalDate endDate) {
        int increaseLast5Days = 15;
        int increaseDays10to15 = 10;
        int discountDays5to10 = -8;
        int totalPercentage = 0;

        LocalDate currentDate = startDate;

        boolean appliedIncreaseLast5Days = false;
        boolean appliedIncreaseDays10to15 = false;
        boolean appliedDiscountDays5to10 = false;

        while (!currentDate.isAfter(endDate)) {
            int dayOfMonth = currentDate.getDayOfMonth();

            if (isEndOfMonth(dayOfMonth, currentDate.lengthOfMonth(), appliedIncreaseLast5Days)) {
                totalPercentage += increaseLast5Days;
                appliedIncreaseLast5Days = true;
            } else if (isBetweenDay10And15(dayOfMonth, appliedIncreaseDays10to15)) {
                totalPercentage += increaseDays10to15;
                appliedIncreaseDays10to15 = true;
            } else if (isBetweenDay5And10(dayOfMonth, appliedDiscountDays5to10)) {
                totalPercentage += discountDays5to10;
                appliedDiscountDays5to10 = true;
            }

            currentDate = currentDate.plusDays(1);
        }

        return totalPercentage;
    }


    private boolean isBetweenDay5And10(int dayOfMonth, boolean alreadyApplied) {
        return !alreadyApplied && dayOfMonth >= 5 && dayOfMonth < 10;
    }


    private boolean isBetweenDay10And15(int dayOfMonth, boolean alreadyApplied) {
        return !alreadyApplied && dayOfMonth >= 10 && dayOfMonth <= 15;
    }


    private boolean isEndOfMonth(int dayOfMonth, int daysInMonth, boolean alreadyApplied) {
        return !alreadyApplied && dayOfMonth > daysInMonth - 5;
    }


    private double calculateTotal(double subtotal, int totalPercentage) {
        return (subtotal * (totalPercentage + 100)) / 100;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        if (!type.equalsIgnoreCase("")) {
            sb.append("Tipo: ").append(type).append(", ");
        }
        sb.append("Descripción: '").append(description)
                .append("', Precio/Noche: ").append(pricePerNight)
                .append(" ]");

        return sb.toString();
    }
}
