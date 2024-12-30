package org.interfaces.proxys;

import org.interfaces.IMenu;
import org.interfaces.implementation.AccommodationBookingOption;

import java.util.Scanner;

public class AccommodationBookingProxy implements IMenu {
    private AccommodationBookingOption option;
    private Scanner scanner;

    public AccommodationBookingProxy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        if (option == null) {
            option = new AccommodationBookingOption(scanner);
        }
        option.execute();
    }
}
