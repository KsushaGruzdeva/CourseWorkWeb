package rut.miit.beautySalon.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rut.miit.beautySalon.models.Client;
import rut.miit.beautySalon.models.Master;
import rut.miit.beautySalon.models.enums.UserRoles;
import rut.miit.beautySalon.repositories.BeautySalonRepository;
import rut.miit.beautySalon.repositories.ClientRepository;
import rut.miit.beautySalon.repositories.MasterRepository;
import rut.miit.beautySalon.repositories.UserRoleRepository;
import rut.miit.beautySalon.services.dtos.ClientDto;
import rut.miit.beautySalon.services.dtos.MasterDto;
import rut.miit.beautySalon.services.dtos.createDto.ClientCreateDto;
import rut.miit.beautySalon.services.dtos.createDto.MasterCreateDto;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final ClientRepository clientRepository;
    private final MasterRepository masterRepository;
    private final UserRoleRepository userRoleRepository;
    private final BeautySalonRepository beautySalonRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthService (ClientRepository clientRepository, MasterRepository masterRepository, UserRoleRepository userRoleRepository, BeautySalonRepository beautySalonRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.masterRepository = masterRepository;
        this.beautySalonRepository = beautySalonRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    public void registerClient (ClientCreateDto registrationDTO) {
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new RuntimeException("passwords.match");
        }

//        Client byEmail = this.clientRepository.findByEmail(registrationDTO.getEmail());
//
//        if (byEmail != null) {
//            throw new RuntimeException("email.used");
//        }

        var userRole = userRoleRepository.
                findRoleByName(UserRoles.USER).orElseThrow();

        Client client = new Client(
                registrationDTO.getName(), registrationDTO.getEmail(),
                registrationDTO.getUsername(), passwordEncoder.encode(registrationDTO.getPassword())
        );

        client.setRoles(List.of(userRole));

        this.clientRepository.create(client);
    }

    public void registerMaster (MasterCreateDto registrationDTO) {
        if (!registrationDTO.getPassword().equals(registrationDTO.getConfirmPassword())) {
            throw new RuntimeException("passwords.match");
        }

//        Master byEmail = this.masterRepository.findByEmail(registrationDTO.getEmail());
//
//        if (byEmail != null) {
//            throw new RuntimeException("email.used");
//        }

        var userRole = userRoleRepository.
                findRoleByName(UserRoles.MASTER).orElseThrow();

        var beautySalon = beautySalonRepository.findAll().get(0);

        Master master = new Master(
                beautySalon,
                registrationDTO.getName(), registrationDTO.getSurname(),
                registrationDTO.getPatronymic(), registrationDTO.getEmail(),
                registrationDTO.getUsername(), passwordEncoder.encode(registrationDTO.getPassword())
        );

        master.setRoles(List.of(userRole));

        this.masterRepository.create(master);
    }

    public MasterDto getMaster (String username) {
        var master =  masterRepository.findByUsername(username).get(0);
        if (master == null)
           throw new UsernameNotFoundException(username + " was not found!");
        return modelMapper.map(master, MasterDto.class);
    }

    public ClientDto getClient (String username) {
        var client = clientRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
        return modelMapper.map(client, ClientDto.class);
    }
}
