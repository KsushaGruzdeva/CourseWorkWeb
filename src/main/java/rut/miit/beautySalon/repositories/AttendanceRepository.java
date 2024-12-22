package rut.miit.beautySalon.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import rut.miit.beautySalon.models.Attendance;

@Repository
public interface AttendanceRepository {
    List <Attendance> findAll ();
    Attendance findById(Class<Attendance> attendanceClass, String id);
    Attendance create (Attendance attendance);
    Attendance update (Attendance attendance);
    Attendance findByName (String name);
}
