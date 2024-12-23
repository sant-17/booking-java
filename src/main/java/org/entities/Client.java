package org.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class Client {
    private String fullName;
    private String email;
    private String nationality;
    private String phoneNumber;
    private Date birthDate;

    private Boolean isOfLegalAge(Date birthDate) {
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
            throw new IllegalArgumentException("The client must be of leagl age");
        }

        this.fullName = fullName;
        this.email = email;
        this.nationality = nationality;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }
}
