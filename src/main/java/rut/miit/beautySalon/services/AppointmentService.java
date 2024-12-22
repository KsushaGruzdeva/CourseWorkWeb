package rut.miit.beautySalon.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import rut.miit.beautySalon.services.dtos.AppointmentDto;
import rut.miit.beautySalon.services.dtos.MasterDto;
import rut.miit.beautySalon.services.dtos.createDto.AppointmentCreateDto;

public interface AppointmentService {
    AppointmentDto findById (String id);
    List <AppointmentDto> findAll ();
    List <AppointmentDto> findAllByClient (String clientId);
    List <AppointmentDto> findAllByMaster (String masterId);
    AppointmentDto create (AppointmentCreateDto appointmentCreateDto);
    void update (AppointmentDto appointmentDto);
    List<MasterDto> findMastersByClient (String clientId);
    List<MasterDto> findFreeMasters (LocalDateTime date, String attendanceId);
}
