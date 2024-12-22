package rut.miit.beautySalon.repositories.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import rut.miit.beautySalon.models.Attendance;
import rut.miit.beautySalon.repositories.AttendanceRepository;

@Repository
public class AttendanceRepositoryImpl extends BaseRepository<Attendance, String> implements AttendanceRepository{
    @PersistenceContext
    private EntityManager entityManager;

    public AttendanceRepositoryImpl(){
        super(Attendance.class);
    }

    @Override
    public Attendance findByName (String name) {
        return entityManager.createQuery("from Attendance a where a.name = :name", Attendance.class)
        .setParameter("name", name)
        .getSingleResult();
    }

    @Override
    public List <Attendance> findAll () {
        return entityManager.createQuery("from Attendance a", Attendance.class)
        .getResultList();
    }
}