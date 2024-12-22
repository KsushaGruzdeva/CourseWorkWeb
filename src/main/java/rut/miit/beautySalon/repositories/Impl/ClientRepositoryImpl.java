package rut.miit.beautySalon.repositories.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import rut.miit.beautySalon.models.Client;
import rut.miit.beautySalon.models.Master;
import rut.miit.beautySalon.models.User;
import rut.miit.beautySalon.repositories.ClientRepository;

@Repository
public class ClientRepositoryImpl extends BaseRepository<Client, String> implements ClientRepository{
    @PersistenceContext
    private EntityManager entityManager;

    public ClientRepositoryImpl(){
        super(Client.class);
    }

    @Override
    public List <Client> findAll () {
        return entityManager.createQuery("from Client c", Client.class)
        .getResultList();
    }

    @Override
    public Client findByName (String name) {
        return entityManager.createQuery("from Client c where c.name = :name", Client.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public int count() {
        return entityManager.createQuery("from Client u", Client.class)
                .getResultList().size();
    }

    @Override
    public Optional<Client> findByUsername(String username) {
        return Optional.ofNullable(entityManager.createQuery("from Client c where c.username = :username", Client.class)
                .setParameter("username", username)
                .getSingleResult());
    }

    @Override
    public Client findByEmail(String email) {
        return entityManager.createQuery("from Client c where c.email = :email", Client.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}