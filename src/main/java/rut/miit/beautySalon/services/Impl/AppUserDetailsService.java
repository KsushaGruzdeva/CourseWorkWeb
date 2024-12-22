package rut.miit.beautySalon.services.Impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import rut.miit.beautySalon.repositories.ClientRepository;
import rut.miit.beautySalon.repositories.MasterRepository;

import java.util.Optional;
import java.util.stream.Collectors;

public class AppUserDetailsService implements UserDetailsService {
    private final ClientRepository clientRepository;
    private final MasterRepository masterRepository;

    public AppUserDetailsService(ClientRepository clientRepository, MasterRepository masterRepository) {
        this.clientRepository = clientRepository;
        this.masterRepository = masterRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (masterRepository.findByUsername(username).size() != 0) {
            var master = masterRepository.findByUsername(username).get(0);
            return Optional.ofNullable(master)
                    .map(u -> new User(
                    u.getUsername(),
                    u.getPassword(),
                    u.getRoles().stream()
                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName().name()))
                            .collect(Collectors.toList())
            )).orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
        }
        else if (clientRepository.findByUsername(username).isPresent()) {
            return clientRepository.findByUsername(username).map(u -> new User(
                    u.getUsername(),
                    u.getPassword(),
                    u.getRoles().stream()
                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName().name()))
                            .collect(Collectors.toList())
            )).orElseThrow(() -> new UsernameNotFoundException(username + " was not found!"));
        }
        else throw new UsernameNotFoundException(username + " was not found!");
    }
}
