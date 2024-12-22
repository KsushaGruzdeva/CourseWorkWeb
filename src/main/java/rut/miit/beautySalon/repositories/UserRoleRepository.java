package rut.miit.beautySalon.repositories;

import org.springframework.stereotype.Repository;
import rut.miit.beautySalon.models.Role;
import rut.miit.beautySalon.models.enums.UserRoles;

import java.util.Optional;

@Repository
public interface UserRoleRepository {
    Optional<Role> findRoleByName(UserRoles role);
    Role save (Role role);
    int count ();
}
