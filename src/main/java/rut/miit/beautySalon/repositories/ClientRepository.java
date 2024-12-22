package rut.miit.beautySalon.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import rut.miit.beautySalon.models.Client;
import rut.miit.beautySalon.models.User;

@Repository
public interface ClientRepository{
    List <Client> findAll ();
    Client findById(Class<Client> clientClass, String id);
    Client findByName(String name);
    Client create (Client client);
    Client update (Client client);
    Client findByEmail(String email);
    Optional<Client> findByUsername(String username);
    int count();
}