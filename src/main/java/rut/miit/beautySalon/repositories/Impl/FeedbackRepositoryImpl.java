package rut.miit.beautySalon.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import rut.miit.beautySalon.models.Client;
import rut.miit.beautySalon.models.Feedback;
import rut.miit.beautySalon.models.Master;
import rut.miit.beautySalon.repositories.FeedbackRepository;

import java.util.List;

@Repository
public class FeedbackRepositoryImpl extends BaseRepository<Feedback, String> implements FeedbackRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public FeedbackRepositoryImpl() {
        super(Feedback.class);
    }

    @Override
    public List<Feedback> findAll() {
        return entityManager.createQuery("from Feedback f", Feedback.class)
                .getResultList();
    }

    @Override
    public List<Feedback> findAllByClient(Client client) {
        return entityManager.createQuery("from Feedback f where f.client = :client", Feedback.class)
                .setParameter("client", client)
                .getResultList();
    }

    @Override
    public List<Feedback> findAllByMaster(Master master) {
        return entityManager.createQuery("from Feedback f where f.master =:master", Feedback.class)
                .setParameter("master", master)
                .getResultList();
    }
}
