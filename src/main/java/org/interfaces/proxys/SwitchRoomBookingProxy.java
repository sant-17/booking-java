package org.interfaces.proxys;

import org.interfaces.IModifyBookingMenu;
import org.interfaces.implementation.SwitchRoomBookingOption;
import org.models.Booking;

import java.util.Scanner;

public class SwitchRoomBookingProxy implements IModifyBookingMenu {
    private SwitchRoomBookingOption option;
    private Scanner scanner;

    public SwitchRoomBookingProxy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute(Booking booking) {
        if (option == null) {
            option = new SwitchRoomBookingOption(scanner);
        }
        option.execute(booking);
    }
}
