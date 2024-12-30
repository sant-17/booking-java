package org.interfaces.proxys;

import org.interfaces.IBookingUpdateMenu;
import org.interfaces.implementation.BookingUpdateOption;
import org.models.Booking;

import java.util.Scanner;

public class BookingUpdateProxy implements IBookingUpdateMenu {
    private BookingUpdateOption option;
    private Scanner scanner;

    public BookingUpdateProxy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Booking execute() {
        if (option == null) {
            option = new BookingUpdateOption(scanner);
        }
        return option.execute();
    }
}
