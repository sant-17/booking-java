package org.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Client {
    private String fullName;
    private String email;
    private String nationality;
    private String phoneNumber;
    private Date birthDate;
    private List<Booking> bookings;

    private static boolean isOfLegalAge(Date birthDate) {
        Calendar todaysDate = Calendar.getInstance();
        Calendar birthDateCal = Calendar.getInstance();
        birthDateCal.setTime(birthDate);

        int age = todaysDate.get(Calendar.YEAR) - birthDateCal.get(Calendar.YEAR);
        if (todaysDate.get(Calendar.DAY_OF_YEAR) < birthDateCal.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age >= 18;
    }

    public Client(String fullName, String email, String nationality, String phoneNumber, Date birthDate) {
        if (!isOfLegalAge(birthDate)) {
            throw new IllegalArgumentException("The client must be of legal age");
        }

        this.fullName = fullName;
        this.email = email;
        this.nationality = nationality;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "Nombre Completo='" + fullName + '\'' +
                ", Email='" + email + '\'' +
                ", Nacionalidad='" + nationality + '\'' +
                ", Número telefónico='" + phoneNumber + '\'' +
                ", Fecha de Nacimiento=" + birthDate +
                '}';
    }
}
