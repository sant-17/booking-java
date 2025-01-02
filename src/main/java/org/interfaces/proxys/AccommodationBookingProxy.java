package org.interfaces.proxys;

import org.interfaces.IMenu;
import org.services.AccommodationBookingService;

import java.util.Scanner;

public class AccommodationBookingProxy implements IMenu {
    private AccommodationBookingService service;
    private Scanner scanner;

    public AccommodationBookingProxy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        if (service == null) {
            service = new AccommodationBookingService(scanner);
        }
        service.execute();
    }
}
