package org.interfaces.proxys;

import org.interfaces.IBookingUpdateMenu;
import org.services.BookingUpdateService;
import org.models.Booking;

import java.util.Scanner;

public class BookingUpdateProxy implements IBookingUpdateMenu {
    private BookingUpdateService service;
    private Scanner scanner;

    public BookingUpdateProxy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Booking execute() {
        if (service == null) {
            service = new BookingUpdateService(scanner);
        }
        return service.execute();
    }
}
