package rut.miit.beautySalon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rut.miit.beautySalon.models.*;
import rut.miit.beautySalon.models.enums.UserRoles;
import rut.miit.beautySalon.repositories.*;

import java.util.List;

@Component
public class CLr implements CommandLineRunner {
    private final ClientRepository clientRepository;
    private final MasterRepository masterRepository;
    private final UserRoleRepository userRoleRepository;
    private final BeautySalonRepository beautySalonRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultPassword;

    public CLr(MasterRepository masterRepository, ClientRepository clientRepository, UserRoleRepository userRoleRepository, BeautySalonRepository beautySalonRepository, PasswordEncoder passwordEncoder, @Value("${app.default.password}") String defaultPassword) {
        this.clientRepository = clientRepository;
        this.masterRepository = masterRepository;
        this.userRoleRepository = userRoleRepository;
        this.beautySalonRepository = beautySalonRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultPassword = defaultPassword;
    }

    @Override
    public void run(String... args) throws Exception {
        initBeautySalon();
        initRoles();
        initUsers();
    }

    private void initBeautySalon(){
        if (beautySalonRepository.findAll().size() == 0) {
            var beautySalon = new BeautySalon("Oasis", "Бизнес", "г. Москва, ул. Фонвизина, д. 10", "beautySalon@email.com");
            beautySalonRepository.create(beautySalon);
        }
    }

    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            var masterRole = new Role(UserRoles.MASTER);
            var adminRole = new Role(UserRoles.ADMIN);
            var normalUserRole = new Role(UserRoles.USER);
            userRoleRepository.save(masterRole);
            userRoleRepository.save(adminRole);
            userRoleRepository.save(normalUserRole);
        }
    }

    private void initUsers() {
        if (clientRepository.count() == 0) {
            initNormalUser();
        }
        if (masterRepository.count() == 0) {
            initAdmin();
            initMaster();
        }
    }

    private void initAdmin() {
        var beautySalon = beautySalonRepository.findAll().get(0);
        var adminRole = userRoleRepository.
                findRoleByName(UserRoles.ADMIN).orElseThrow();
//        "admin", passwordEncoder.encode(defaultPassword)

        var adminUser = new Master(beautySalon, "Admin", "Adminov", "Adminovich", "admin@gmail.com", "admin", passwordEncoder.encode(defaultPassword));
        adminUser.setRoles(List.of(adminRole));

        masterRepository.create(adminUser);
    }

    private void initMaster(){
        var beautySalon = beautySalonRepository.findAll().get(0);
        var masterRole = userRoleRepository.
                findRoleByName(UserRoles.MASTER).orElseThrow();

        var masterUser = new Master(beautySalon, "Master", "Masterov", "Masterovich", "master@gmail.com", "master", passwordEncoder.encode(defaultPassword));
        masterUser.setRoles(List.of(masterRole));

        masterRepository.create(masterUser);
    }

    private void initNormalUser(){
        var userRole = userRoleRepository.
                findRoleByName(UserRoles.USER).orElseThrow();

        var normalUser = new Client("User", "user@gmail.com", "user", passwordEncoder.encode(defaultPassword));
        normalUser.setRoles(List.of(userRole));

        clientRepository.create(normalUser);
    }
}
