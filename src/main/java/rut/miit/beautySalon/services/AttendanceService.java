package rut.miit.beautySalon.services;

import java.util.List;

import rut.miit.beautySalon.services.dtos.AttendanceDto;
import rut.miit.beautySalon.services.dtos.createDto.AttendanceCreateDto;

public interface AttendanceService {
    AttendanceDto findById (String id);
    AttendanceDto findByName (String name);
    List <AttendanceDto> findAll ();
    AttendanceDto create (AttendanceCreateDto attendanceCreateDto);
    void update (AttendanceDto attendanceDto);
}
