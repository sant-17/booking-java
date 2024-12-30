package org.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Client {
    private String fullName;
    private String email;
    private String nationality;
    private String phoneNumber;
    private LocalDate birthDate;
    private List<Booking> bookings;

    private static boolean isOfLegalAge(LocalDate birthDate) {
        LocalDate todaysDate = LocalDate.now();
        int age = Period.between(birthDate, todaysDate).getYears();
        return age >= 18;
    }

    public Client(String fullName, String email, String nationality, String phoneNumber, LocalDate birthDate) {
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

    public void removeBooking(Booking booking) {
        this.bookings.remove(booking);
    }

    @Override
    public String toString() {

        return "Cliente [\n   - Nombre: " + fullName +
                ",\n   - Email: " + email +
                ",\n   - Nacionalidad: " + nationality +
                ",\n   - Teléfono: " + phoneNumber +
                ",\n   - F. de Nacimiento: " + birthDate +
                " ]";
    }
}
