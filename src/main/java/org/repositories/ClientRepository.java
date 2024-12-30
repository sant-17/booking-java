package org.repositories;

import lombok.Getter;
import org.models.Client;
import org.models.factories.ClientFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ClientRepository {
    private static ClientRepository instance;

    @Getter
    private List<Client> clients;

    private ClientRepository() {
        this.clients = ClientFactory.createClients();
    }

    public static ClientRepository getInstance() {
        if (instance == null) {
            instance = new ClientRepository();
        }

        return instance;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public Optional<Client> findClientByEmailAndBirthDate(String email, LocalDate birthDate) {
        return clients.stream()
                .filter(client -> client.getEmail().equalsIgnoreCase(email) &&
                        client.getBirthDate().isEqual(birthDate))
                .findFirst();
    }
}
