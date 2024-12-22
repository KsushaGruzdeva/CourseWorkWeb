package rut.miit.beautySalon.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import rut.miit.beautySalon.models.Role;
import rut.miit.beautySalon.models.enums.UserRoles;
import rut.miit.beautySalon.repositories.UserRoleRepository;

import java.util.Optional;

@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public UserRoleRepositoryImpl(){
    }

    @Override
    public Optional<Role> findRoleByName(UserRoles role) {
        return Optional.ofNullable(entityManager.createQuery("from Role r where r.name = :name", Role.class)
                .setParameter("name", role)
                .getSingleResult());
    }

    @Override
    @Transactional
    public Role save(Role role) {
        entityManager.persist(role);
        return role;
    }

    @Override
    public int count (){
        return entityManager.createQuery("from Role r", Role.class)
                .getResultList().size();
    }
}
