package rut.miit.beautySalon.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import rut.miit.beautySalon.models.Appointment;
import rut.miit.beautySalon.models.Client;
import rut.miit.beautySalon.models.Master;
import rut.miit.beautySalon.models.MastersAttendance;

@Repository
public interface AppointmentRepository{
    List <Appointment> findAll ();
    Appointment findById(Class<Appointment> appointmentClass, String id);
    Appointment create (Appointment appointment);
    Appointment update (Appointment appointment);
    List<Appointment> findAllByMastersAttendance (MastersAttendance mastersAttendance);
    List<Appointment> findAllByMaster (Master master);
    List<Appointment> findAllByClient (Client client);
}
