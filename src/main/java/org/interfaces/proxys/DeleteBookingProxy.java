package org.interfaces.proxys;

import lombok.NoArgsConstructor;
import org.interfaces.IModifyBookingMenu;
import org.interfaces.implementation.DeleteBookingOption;
import org.models.Booking;

@NoArgsConstructor
public class DeleteBookingProxy implements IModifyBookingMenu {
    private DeleteBookingOption option;

    @Override
    public void execute(Booking booking) {
        if (option == null) {
            option = new DeleteBookingOption();
        }
        option.execute(booking);
    }
}
