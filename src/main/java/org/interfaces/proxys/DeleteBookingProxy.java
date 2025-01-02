package org.interfaces.proxys;

import lombok.NoArgsConstructor;
import org.interfaces.IModifyBookingMenu;
import org.services.DeleteBookingService;
import org.models.Booking;

@NoArgsConstructor
public class DeleteBookingProxy implements IModifyBookingMenu {
    private DeleteBookingService service;

    @Override
    public void execute(Booking booking) {
        if (service == null) {
            service = new DeleteBookingService();
        }
        if (booking == null) {
            throw new RuntimeException("No hay reserva seleccionada.");
        }
        service.execute(booking);
    }
}
