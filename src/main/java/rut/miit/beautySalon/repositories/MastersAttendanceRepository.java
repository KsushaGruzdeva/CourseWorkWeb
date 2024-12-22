package rut.miit.beautySalon.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import rut.miit.beautySalon.models.Attendance;
import rut.miit.beautySalon.models.Master;
import rut.miit.beautySalon.models.MastersAttendance;

@Repository
public interface MastersAttendanceRepository {
    List <MastersAttendance> findAll ();
    MastersAttendance findById(Class<MastersAttendance> mastersAttendanceClass, String id);
    MastersAttendance create (MastersAttendance mastersAttendance);
    MastersAttendance update (MastersAttendance mastersAttendance);
    MastersAttendance findByAttendanceAndMaster (Attendance attendance, Master master);
    List<Master> findMasterByAttendance (Attendance attendance);
    List<MastersAttendance> findAllByAttendanceId (String id);
    List<MastersAttendance> findAllByMasterId (String id);
}
