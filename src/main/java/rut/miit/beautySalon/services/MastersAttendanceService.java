package rut.miit.beautySalon.services;

import java.util.List;

import rut.miit.beautySalon.services.dtos.AttendanceDto;
import rut.miit.beautySalon.services.dtos.MasterDto;
import rut.miit.beautySalon.services.dtos.MastersAttendanceDto;
import rut.miit.beautySalon.services.dtos.createDto.MasterAttendanceCreateDto;

public interface MastersAttendanceService {
    MastersAttendanceDto findById (String id);
    List <MastersAttendanceDto> findAll ();
    MastersAttendanceDto create (MasterAttendanceCreateDto masterAttendanceCreateDto);
    void update (MastersAttendanceDto mastersAttendanceDto);
    List<MasterDto> findMasterByAttendance (String id);
    MastersAttendanceDto findByAttendanceAndMaster (AttendanceDto attendanceDto, MasterDto masterDto);
}
