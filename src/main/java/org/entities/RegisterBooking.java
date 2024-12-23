package org.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class RegisterBooking {
    private String id;
    private Date startDate;
    private Date endDate;

    public RegisterBooking(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = UUID.randomUUID().toString();
    }
}
