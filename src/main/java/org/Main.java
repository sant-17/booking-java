package org;

import org.entities.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //Creando datos de día de sol
        Client client1 = new Client("Juan Perez", "juan.perez@gmail.com", "Colombiana", "123456789", getDate(1990, 5, 15));
        Client client2 = new Client("Maria Gomez", "maria.gomez@gmail.com", "Mexicana", "987654321", getDate(2000, 10, 20));

        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);

        Amenity swimmingPool = new Amenity("Disfrutar de la piscina");
        Amenity beach = new Amenity("Relajarse en la playa");
        Amenity waterSports = new Amenity("Practicar deportes acuáticos");
        Amenity hiking = new Amenity("Hacer senderismo por los alrededores");
        Amenity barbecue = new Amenity("Disfrutar de una barbacoa en el jardín");

        DayTrip dayTrip1 = new DayTrip("Aventura Acuática", 4.5, 50.0, 30.0);
        dayTrip1.addAmenity(swimmingPool);
        dayTrip1.addAmenity(beach);
        dayTrip1.addAmenity(waterSports);

        DayTrip dayTrip2 = new DayTrip("Relax Natural", 4.0, 40.0, 25.0);
        dayTrip2.addAmenity(hiking);
        dayTrip2.addAmenity(barbecue);

        DayTrip dayTrip3 = new DayTrip("Diversión Familiar", 5.0, 60.0, 35.0);
        dayTrip3.addAmenity(swimmingPool);
        dayTrip3.addAmenity(barbecue);
        dayTrip3.addAmenity(hiking);

        List<DayTrip> dayTrips = new ArrayList<>();
        dayTrips.add(dayTrip1);
        dayTrips.add(dayTrip2);
        dayTrips.add(dayTrip3);

        Booking booking1 = new Booking(client1, dayTrip1, getDate(2024, 8, 14), 2, 1);
        dayTrip1.addBooking(booking1);
        client1.addBooking(booking1);

        Booking booking2 = new Booking(client2, dayTrip2, new Date(), 4, 2);
        dayTrip2.addBooking(booking2);
        client2.addBooking(booking2);

        //Creando datos de Hoteles, Apartamentos y Fincas
        Accommodation hotel = new Accommodation("Hotel Las Palmas", "Hotel", 4.5);
        Accommodation apartment = new Accommodation("Apartamento del Sol", "Apartamento", 4.0);
        Accommodation farm = new Accommodation("Finca Los Robles", "Finca", 5.0);

        for (int i = 1; i <= 5; i++) {
            hotel.addRoom(new Room(100 + i, "Habitación Hotel " + i, "Descripción Hotel " + i, 100.0 + i * 10));
            apartment.addRoom(new Room(i, "Habitación Apartamento " + i, "Descripción Apartamento " + i, 80.0 + i * 10));
            farm.addRoom(new Room(i, "Habitación Finca " + i, "Descripción Finca " + i, 120.0 + i * 10));
        }

        // Crear reservas
        Booking reservation1 = new Booking(client1, hotel, hotel.getRooms().get(0), getDate(2024, 1, 10), getDate(2024, 1, 15), 2, 1);
        hotel.addBooking(reservation1);
        client1.addBooking(reservation1);

        Booking reservation2 = new Booking(client1, apartment, apartment.getRooms().get(1), getDate(2024, 2, 5), getDate(2024, 2, 10), 3, 2);
        apartment.addBooking(reservation2);
        client1.addBooking(reservation2);

        Booking reservation3 = new Booking(client2, farm, farm.getRooms().get(2), getDate(2024, 3, 12), getDate(2024, 3, 18), 4, 0);
        farm.addBooking(reservation3);
        client2.addBooking(reservation3);

        Booking reservation4 = new Booking(client2, hotel, hotel.getRooms().get(3), getDate(2024, 4, 15), getDate(2024, 4, 20), 1, 1);
        hotel.addBooking(reservation4);
        client2.addBooking(reservation4);

        Booking reservation5 = new Booking(client1, farm, farm.getRooms().get(4), getDate(2024, 5, 10), getDate(2024, 5, 15), 2, 2);
        farm.addBooking(reservation5);
        client1.addBooking(reservation5);

        // Imprimir reservas
        System.out.println("\nDetalles de Reservas:");
        System.out.println(reservation1);
        System.out.println(reservation2);
        System.out.println(reservation3);
        System.out.println(reservation4);
        System.out.println(reservation5);

        System.out.println("\nDetalles de Alojamiento:");
        printAccommodationDetails(hotel);
        printAccommodationDetails(apartment);
        printAccommodationDetails(farm);

        System.out.println("\nDetalles de Alojamiento y Reservas:");
        System.out.println(hotel);
        System.out.println(apartment);
        System.out.println(farm);

        Boolean whileCondition = true;

        while (whileCondition) {
            System.out.println("---- SISTEMAS DE RESERVAS ----");

            try {
                System.out.println("\nBienvenido al sistema de reservas, ");
                System.out.println("¿Qué desea hacer hoy?\n");
                System.out.println("1. Reservar hotel, apartamento o finca");
                System.out.println("2. Reservar un día de sol");
                System.out.println("3. Actualizar una reservación");
                System.out.println("4. SALIR");

                Scanner scanner = new Scanner(System.in);

                int option = 0;
                System.out.print("Ingrese una opción: ");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("\nIngrese la fecha del CHECK-IN:");
                        System.out.print("Mes (número del mes): ");
                        int monthCheckInAndOut = scanner.nextInt();
                        System.out.print("Día: ");
                        int dayCheckIn = scanner.nextInt();

                        if (monthCheckInAndOut <= 0 || monthCheckInAndOut > 12) {
                            System.out.println("ERROR CON EL MES INGRESADO");
                            break;
                        }
                        Date checkIn = getDate(2024, monthCheckInAndOut, dayCheckIn);

                        System.out.println("\nIngrese el día del CHECK-OUT:");
                        System.out.print("Día: ");
                        int dayCheckOut = scanner.nextInt();

                        if (dayCheckOut <= dayCheckIn) {
                            System.out.println("FECHA INCORRECTA, EL DÍA DEL CHECKOUT NO PUEDE SER EL MISMO DÍA O ANTES DEL DÍA DE CHECKIN");
                            break;
                        }
                        Date checkOut = getDate(2024, monthCheckInAndOut, dayCheckOut);

                        System.out.println("\n¿Qué alojamiento desea?");
                        System.out.println("1. Hotel");
                        System.out.println("2. Apartamento");
                        System.out.println("3. Finca");
                        System.out.print("Ingrese el número del alojamiento: ");
                        int accomodationType = scanner.nextInt();

                        if (accomodationType < 1 || accomodationType > 3) {
                            System.out.println("ERROR CON EL NÚMERO DE ALOJAMIENTO INGRESADO");
                            break;
                        }
                        List<Room> availableRooms = new ArrayList<>();

                        String fullName;
                        String email;
                        String nacionality;
                        String phoneNumber;
                        int adults;
                        int kids;
                        int birthYear;
                        int birthMonth;
                        int birthDay;

                        Accommodation accommodationSelected = new Accommodation();

                        switch (accomodationType) {
                            case 1:
                                System.out.println("\nHa elegido Hotel");
                                System.out.println("Estas son las habitaciones disponibles: \n");

                                System.out.println(hotel.getName() + " - Rating: " + hotel.getRating());

                                availableRooms.addAll(hotel.getAvailableRooms(checkIn, checkOut));
                                accommodationSelected = hotel;
                                break;
                            case 2:
                                System.out.println("\nHa elegido Apartamento");
                                System.out.println("Estas son las habitaciones disponibles: \n");

                                System.out.println(apartment.getName() + " - Rating: " + apartment.getRating());

                                availableRooms.addAll(apartment.getAvailableRooms(checkIn, checkOut));
                                accommodationSelected = apartment;
                                break;
                            case 3:
                                System.out.println("\nHa elegido Finca");
                                System.out.println("Estas son las habitaciones disponibles: \n");

                                System.out.println(farm.getName() + " - Rating: " + farm.getRating());

                                availableRooms.addAll(farm.getAvailableRooms(checkIn, checkOut));
                                accommodationSelected = farm;
                                break;
                            default:
                                System.out.println("\nNo se ha elegido una opción válida");
                        }
                        int i = 1;
                        for (Room room : availableRooms) {
                            System.out.println(i + ". " + room);
                            i++;
                        }
                        System.out.print("Elija el número de habitación en el listado que desea: ");
                        int roomNumber = scanner.nextInt();

                        Room room = availableRooms.get(roomNumber-1);

                        System.out.println("\nHa elegido esta habitación: " + room);

                        scanner.nextLine();

                        System.out.println("\nIngrese sus datos personales para terminar la reserva");
                        System.out.print("Nombre completo: ");
                        fullName = scanner.nextLine();

                        System.out.print("Correo electrónico: ");
                        email = scanner.nextLine();

                        System.out.print("Nacionalidad: ");
                        nacionality = scanner.nextLine();

                        System.out.print("Número de teléfono: ");
                        phoneNumber = scanner.nextLine();

                        System.out.print("¿Cuántos adultos vienen? ");
                        adults = scanner.nextInt();

                        System.out.print("¿Cuántos niños vienen? ");
                        kids = scanner.nextInt();

                        System.out.print("Año de nacimiento: ");
                        birthYear = scanner.nextInt();
                        System.out.print("Mes de nacimiento: ");
                        birthMonth = scanner.nextInt();
                        System.out.print("Día de nacimiento: ");
                        birthDay = scanner.nextInt();

                        Date birthDate = getDate(birthYear, birthMonth, birthDay);

                        Client accommodationClient = new Client(fullName, email, nacionality, phoneNumber, birthDate);

                        Booking bookingAccommodation = new Booking(accommodationClient, accommodationSelected, room, checkIn, checkOut, adults, kids);
                        accommodationSelected.addBooking(bookingAccommodation);
                        accommodationClient.addBooking(bookingAccommodation);
                        clients.add(accommodationClient);

                        System.out.println("Su reserva ha sido creada exitosamente, estos son los detalles: ");
                        System.out.println(bookingAccommodation);
                        break;
                    case 2:
                        System.out.println("Estos son los complejos disponibles para un Día de Sol: ");

                        int j = 1;
                        for (DayTrip dayTrip : dayTrips) {
                            System.out.println(j + ". " + dayTrip.getName());
                            j++;
                        }
                        System.out.print("Ingrese el número del complejo al que desea ir: ");
                        int opt = scanner.nextInt();

                        DayTrip selectedDayTrip = dayTrips.get(opt - 1);

                        System.out.println("Ha seleccionado " + selectedDayTrip.getName());
                        System.out.println("El precio por adulto es de $" + selectedDayTrip.getPricePerAdult() + " y el precio por niño/a es de $" + selectedDayTrip.getPricePerKid());

                        System.out.println("Estas son todas las actividades disponibles: ");
                        for (Amenity amenity : selectedDayTrip.getAmenities()) {
                            System.out.println("- " + amenity.getName());
                        }

                        System.out.println("\nIngrese la fecha de la reserva:");
                        System.out.print("Mes (número del mes): ");
                        int monthBooking = scanner.nextInt();
                        System.out.print("Día: ");
                        int dayBooking = scanner.nextInt();

                        Date dateBooking = getDate(2024, monthBooking, dayBooking);

                        scanner.nextLine();

                        System.out.println("Ingrese sus datos para completar la reserva: ");
                        System.out.println("\nIngrese sus datos personales para terminar la reserva");
                        System.out.print("Nombre completo: ");
                        fullName = scanner.nextLine();

                        System.out.print("Correo electrónico: ");
                        email = scanner.nextLine();

                        System.out.print("Nacionalidad: ");
                        nacionality = scanner.nextLine();

                        System.out.print("Número de teléfono: ");
                        phoneNumber = scanner.nextLine();

                        System.out.print("¿Cuántos adultos vienen? ");
                        adults = scanner.nextInt();

                        System.out.print("¿Cuántos niños vienen? ");
                        kids = scanner.nextInt();

                        System.out.print("Año de nacimiento: ");
                        birthYear = scanner.nextInt();
                        System.out.print("Mes de nacimiento: ");
                        birthMonth = scanner.nextInt();
                        System.out.print("Día de nacimiento: ");
                        birthDay = scanner.nextInt();

                        Date birthDayClient = getDate(birthYear, birthMonth, birthDay);

                        Client dayTripClient = new Client(fullName, email, nacionality, phoneNumber, birthDayClient);

                        Booking bookingDayTrip = new Booking(dayTripClient, selectedDayTrip, dateBooking, adults, kids);
                        hotel.addBooking(bookingDayTrip);
                        dayTripClient.addBooking(bookingDayTrip);
                        clients.add(dayTripClient);

                        System.out.println("Su reserva ha sido creada exitosamente, estos son los detalles: ");
                        System.out.println(bookingDayTrip);
                        break;
                    case 3:
                        scanner.nextLine();

                        System.out.println("Ha elegido actualizar una reservación:");
                        System.out.print("Ingrese el correo electrónico con el que registró la reserva: ");

                        email = scanner.nextLine();

                        System.out.print("Año de nacimiento: ");
                        birthYear = scanner.nextInt();
                        System.out.print("Mes de nacimiento: ");
                        birthMonth = scanner.nextInt();
                        System.out.print("Día de nacimiento: ");
                        birthDay = scanner.nextInt();

                        LocalDate comparisonDate = LocalDate.of(birthYear, birthMonth, birthDay);
                        Client findedClient = new Client();

                        for (Client client : clients) {
                            LocalDate birthDateLocalDate = client.getBirthDate().toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDate();

                            if (client.getEmail().equals(email) && birthDateLocalDate.equals(comparisonDate)) {
                                System.out.println("ENTRÓ ACÁ");
                                findedClient = client;
                                break;
                            }
                        }
                        if (findedClient.getEmail() == null) {
                            System.out.println("NO SE HA ENCONTRADO UN CLIENTE POR DICHOS DATOS");
                            break;
                        }
                        System.out.println("Elija la reserva que desea modificar");

                        int k = 1;

                        for (Booking booking : findedClient.getBookings()) {
                            System.out.println(k + ". " + booking);
                            k++;
                        }
                        System.out.print("Ingrese el número de la reserva: ");
                        int optUpdate = scanner.nextInt() - 1;

                        Booking selectedBooking = findedClient.getBookings().get(optUpdate);

                        System.out.println("Ha elegido la siguiente reserva: ");
                        System.out.println(selectedBooking);

                        System.out.println("\n¿Qué desea hacer?");
                        System.out.println("1. Cambiar habitación");
                        System.out.println("2. Cambiar alojamiento");
                        System.out.print("Elija una opción: ");
                        optUpdate = scanner.nextInt();

                        Accommodation accommodationToUpdate = selectedBooking.getAccommodation();
                        Client clientToUpddate = selectedBooking.getClient();
                        RegisterBooking registerBooking = selectedBooking.getRegisterBooking();

                        switch (optUpdate) {
                            case 1:
                                if (selectedBooking.getDayTrip() != null) {
                                    System.out.println("NO PUEDE CAMBIAR HABITACIÓN DE UN DÍA DE SOL");
                                    break;
                                }
                                System.out.println("Elija una nueva habitación (puede corresponder a gastos adicionales): ");

                                int l = 1;
                                for (Room roomAccommodation : selectedBooking.getAccommodation().getAvailableRooms(selectedBooking.getStartDate(), selectedBooking.getEndDate())) {
                                    System.out.println(l + ". " + roomAccommodation);
                                    l++;
                                }
                                System.out.print("Ingrese el número de la habitación: ");
                                int newRoomNumber = scanner.nextInt() - 1;
                                Room newRoom = selectedBooking.getAccommodation().getAvailableRooms(selectedBooking.getStartDate(), selectedBooking.getEndDate()).get(newRoomNumber);

                                System.out.println("Ha seleccionado la siguiente habitación: ");
                                System.out.println(newRoom);

                                accommodationToUpdate.getBookings().remove(selectedBooking);
                                clientToUpddate.getBookings().remove(selectedBooking);
                                selectedBooking.getRoom().getBookings().remove(registerBooking);

                                Booking bookingUpdate = new Booking(
                                        findedClient,
                                        accommodationToUpdate,
                                        newRoom,
                                        selectedBooking.getStartDate(),
                                        selectedBooking.getEndDate(),
                                        selectedBooking.getAdults(),
                                        selectedBooking.getKids()
                                );
                                bookingUpdate.setStatus("Actualizado");

                                accommodationToUpdate.addBooking(bookingUpdate);
                                findedClient.addBooking(bookingUpdate);

                                System.out.println("Así quedó tu reservación: ");
                                System.out.println(bookingUpdate);

                                break;
                            case 2:
                                if (selectedBooking.getDayTrip() != null) {
                                    System.out.println("NO PUEDE CAMBIAR HABITACIÓN DE UN DÍA DE SOL");
                                    break;
                                }
                                accommodationToUpdate.getBookings().remove(selectedBooking);
                                clientToUpddate.getBookings().remove(selectedBooking);
                                selectedBooking.getRoom().getBookings().remove(registerBooking);

                                System.out.println("Su anterior reserva ha sido cancelada. Puede proceder a realizar nuevamente su reserva.");
                        }
                    case 4:
                        whileCondition = false;
                        break;
                }
            } catch (Exception e) {
                System.out.println("HUBO UN ERROR, INTENTE NUEVAMENTE");
            }
        }
    }

    private static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }

    private static void printAccommodationDetails(Accommodation accommodation) {
        System.out.println("Nombre: " + accommodation.getName() + ", Tipo: " + accommodation.getType() + ", Rating: " + accommodation.getRating());
        System.out.println("Habitaciones:");
        for (Room room : accommodation.getRooms()) {
            System.out.println("- ID: " + room.getId() + ", Tipo: " + room.getType() + ", Descripción: " + room.getDescription() + ", Precio: $" + room.getPricePerNight());
        }
    }
}