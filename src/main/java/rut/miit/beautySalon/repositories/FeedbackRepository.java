package rut.miit.beautySalon.repositories;

import org.springframework.stereotype.Repository;
import rut.miit.beautySalon.models.Client;
import rut.miit.beautySalon.models.Feedback;
import rut.miit.beautySalon.models.Master;

import java.util.List;

@Repository
public interface FeedbackRepository {
    List<Feedback> findAll ();
    Feedback findById(Class<Feedback> feedbackClass, String id);
    Feedback create (Feedback feedback);
    List<Feedback> findAllByClient(Client client);
    List<Feedback> findAllByMaster (Master master);
}
