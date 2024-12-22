package rut.miit.beautySalon.repositories.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import rut.miit.beautySalon.models.Attendance;
import rut.miit.beautySalon.models.Master;
import rut.miit.beautySalon.models.MastersAttendance;
import rut.miit.beautySalon.repositories.MastersAttendanceRepository;


@Repository
public class MastersAttendanceRepositoryImpl extends BaseRepository<MastersAttendance, String> implements MastersAttendanceRepository{
    @PersistenceContext
    private EntityManager entityManager;

    public MastersAttendanceRepositoryImpl(){
        super(MastersAttendance.class);
    }

    @Override
    public List<MastersAttendance> findAllByAttendanceId (String id) {
        return entityManager.createQuery("from MastersAttendance ma where ma.attendance.id = :id", MastersAttendance.class)
        .setParameter("id", id)
        .getResultList();
    }

    @Override
    public List <MastersAttendance> findAll () {
        return entityManager.createQuery("from MastersAttendance ma", MastersAttendance.class)
        .getResultList();
    }

    @Override
    public List<MastersAttendance> findAllByMasterId (String id) {
        return entityManager.createQuery("from MastersAttendance ma where ma.master.id = :id", MastersAttendance.class)
        .setParameter("id", id)
        .getResultList();
    }

    @Override
    public List<Master> findMasterByAttendance (Attendance attendance){
        return entityManager.createQuery("select ma.master from MastersAttendance ma where ma.attendance = :attendance", Master.class)
                .setParameter("attendance", attendance)
                .getResultList();
    }

    @Override
    public MastersAttendance findByAttendanceAndMaster (Attendance attendance, Master master){
        return entityManager.createQuery("from MastersAttendance ma where ma.attendance = :attendance and ma.master =: master", MastersAttendance.class)
                .setParameter("attendance", attendance)
                .setParameter("master", master)
                .getSingleResult();
    }
}