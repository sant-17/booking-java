package org.interfaces.proxys;

import org.interfaces.IMenu;
import org.interfaces.implementation.DayTripBookingOption;

import java.util.Scanner;

public class DayTripBookingProxy implements IMenu {
    private DayTripBookingOption option;
    private Scanner scanner;

    public DayTripBookingProxy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        if (option == null) {
            option = new DayTripBookingOption(scanner);
        }
        option.execute();
    }
}
