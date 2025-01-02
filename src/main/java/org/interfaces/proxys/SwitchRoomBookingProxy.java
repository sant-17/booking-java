package org.interfaces.proxys;

import org.interfaces.IModifyBookingMenu;
import org.services.SwitchRoomBookingService;
import org.models.Booking;

import java.util.Scanner;

public class SwitchRoomBookingProxy implements IModifyBookingMenu {
    private SwitchRoomBookingService service;
    private Scanner scanner;

    public SwitchRoomBookingProxy(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public void execute(Booking booking) {
        if (service == null) {
            service = new SwitchRoomBookingService(scanner);
        }
        if (booking == null) {
            throw new RuntimeException("No hay reserva seleccionada.");
        }
        service.execute(booking);
    }
}
