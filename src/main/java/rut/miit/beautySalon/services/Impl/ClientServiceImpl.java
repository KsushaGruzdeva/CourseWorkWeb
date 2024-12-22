package rut.miit.beautySalon.services.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import rut.miit.beautySalon.models.Client;
import rut.miit.beautySalon.models.Master;
import rut.miit.beautySalon.repositories.ClientRepository;
import rut.miit.beautySalon.services.ClientService;
import rut.miit.beautySalon.services.dtos.ClientDto;
import rut.miit.beautySalon.services.dtos.createDto.ClientCreateDto;
import rut.miit.beautySalon.utils.ValidationUtil;

@Service
public class ClientServiceImpl implements ClientService{
    private final ClientRepository clientRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientServiceImpl (ClientRepository clientRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public ClientDto findById(String id) {
        Client client = this.clientRepository.findById(Client.class, id);
        return this.modelMapper.map(client, ClientDto.class);
    }

    @Override
    public List<ClientDto> findAll() {
        List <Client> allClients = clientRepository.findAll();
        return allClients.stream().map(client -> modelMapper.map(client, ClientDto.class)).toList();
    }

    @Override
    public void create(ClientCreateDto clientCreateDto) {
        if (!this.validationUtil.isValid(clientCreateDto)) {
            this.validationUtil
                    .violations(clientCreateDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
        else {
            this.clientRepository
                    .create(this.modelMapper
                            .map(clientCreateDto, Client.class));
        }
    }

    @Override
    public void update(ClientDto clientDto) {
        if (!this.validationUtil.isValid(clientDto)) {
            this.validationUtil
                    .violations(clientDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
        else {
            this.clientRepository
                    .update(this.modelMapper
                            .map(clientDto, Client.class));
        }
    }
}
