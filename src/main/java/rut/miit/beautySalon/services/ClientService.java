package rut.miit.beautySalon.services;

import java.util.List;

import rut.miit.beautySalon.services.dtos.ClientDto;
import rut.miit.beautySalon.services.dtos.createDto.ClientCreateDto;

public interface ClientService {
    ClientDto findById (String id);
    List <ClientDto> findAll ();
    void create (ClientCreateDto clientCreateDto);
    void update (ClientDto clientDto);
}
