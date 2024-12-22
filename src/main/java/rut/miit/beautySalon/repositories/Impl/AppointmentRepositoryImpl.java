package rut.miit.beautySalon.repositories.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import rut.miit.beautySalon.models.Appointment;
import rut.miit.beautySalon.models.Client;
import rut.miit.beautySalon.models.Master;
import rut.miit.beautySalon.models.MastersAttendance;
import rut.miit.beautySalon.repositories.AppointmentRepository;


@Repository
public class AppointmentRepositoryImpl extends BaseRepository<Appointment, String> implements AppointmentRepository{
    @PersistenceContext
    private EntityManager entityManager;

    public AppointmentRepositoryImpl(){
        super(Appointment.class);
    }

     @Override
     public List<Appointment> findAllByClient (Client client) {
         return entityManager.createQuery("from Appointment a where a.client = :client", Appointment.class)
         .setParameter("client", client)
         .getResultList();
     }

    @Override
    public List<Appointment> findAllByMastersAttendance (MastersAttendance mastersAttendance) {
        return entityManager.createQuery("from Appointment a where a.mastersAttendance = :mastersAttendance", Appointment.class)
        .setParameter("mastersAttendance", mastersAttendance)
        .getResultList();
    }

    @Override
    public List<Appointment> findAllByMaster (Master master) {
        return entityManager.createQuery("from Appointment a where a.mastersAttendance.master = :master", Appointment.class)
                .setParameter("master", master)
                .getResultList();
    }

    @Override
    public List <Appointment> findAll () {
        return entityManager.createQuery("from Appointment a", Appointment.class)
        .getResultList();
    }
}
