package org.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RegisterBooking {
    private Date startDate;
    private Date endDate;

    public RegisterBooking(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
