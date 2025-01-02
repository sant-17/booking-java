package org.interfaces.proxys;

import org.interfaces.IMenu;
import org.services.DayTripBookingService;

import java.util.Scanner;

public class DayTripBookingProxy implements IMenu {
    private DayTripBookingService service;
    private Scanner scanner;

    public DayTripBookingProxy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        if (service == null) {
            service = new DayTripBookingService(scanner);
        }
        service.execute();
    }
}
