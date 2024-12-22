package rut.miit.beautySalon.repositories.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import rut.miit.beautySalon.models.BeautySalon;
import rut.miit.beautySalon.models.Feedback;
import rut.miit.beautySalon.models.Master;
import rut.miit.beautySalon.models.User;
import rut.miit.beautySalon.repositories.MasterRepository;

@Repository
public class MasterRepositoryImpl extends BaseRepository<Master, String> implements MasterRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public MasterRepositoryImpl(){
        super(Master.class);
    }

    @Override
    public List <Master> findAll () {
        return entityManager.createQuery("from Master m", Master.class)
        .getResultList();
    }

    @Override
    public Master findByFIO (String fullName){
        return entityManager.createQuery("from Master m where concat(m.surname, ' ', m.name, ' ', m.patronymic) = :fullName", Master.class)
                .setParameter("fullName", fullName).getSingleResult();
    }

    @Override
    public Master findByEmail(String email){
        return entityManager.createQuery("from Master m where m.email = :email", Master.class)
                .setParameter("email", email).getSingleResult();
    }

    @Override
    public int count() {
        return entityManager.createQuery("from Master u", Master.class)
                .getResultList().size();
    }

    @Override
    public List<Master> findByUsername(String username) {
        return entityManager.createQuery("from Master m where m.username = :username", Master.class)
                .setParameter("username", username)
                .getResultList();
    }
}