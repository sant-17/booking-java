package org.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.models.Booking;
import org.models.Client;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientAndBookingDTO {
    private Client client;
    private Booking booking;
}
