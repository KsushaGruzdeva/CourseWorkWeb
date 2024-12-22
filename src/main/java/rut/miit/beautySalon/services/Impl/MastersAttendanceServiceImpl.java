package rut.miit.beautySalon.services.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import rut.miit.beautySalon.models.Attendance;
import rut.miit.beautySalon.models.Master;
import rut.miit.beautySalon.models.MastersAttendance;
import rut.miit.beautySalon.repositories.AttendanceRepository;
import rut.miit.beautySalon.repositories.MasterRepository;
import rut.miit.beautySalon.repositories.MastersAttendanceRepository;
import rut.miit.beautySalon.services.MastersAttendanceService;
import rut.miit.beautySalon.services.dtos.AttendanceDto;
import rut.miit.beautySalon.services.dtos.MasterDto;
import rut.miit.beautySalon.services.dtos.MastersAttendanceDto;
import rut.miit.beautySalon.services.dtos.createDto.MasterAttendanceCreateDto;
import rut.miit.beautySalon.utils.ValidationUtil;

@Service
public class MastersAttendanceServiceImpl implements MastersAttendanceService {

    private final MastersAttendanceRepository mastersAttendanceRepository;
    private final MasterRepository masterRepository;
    private final AttendanceRepository attendanceRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public MastersAttendanceServiceImpl (MastersAttendanceRepository mastersAttendanceRepository, MasterRepository masterRepository, AttendanceRepository attendanceRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.mastersAttendanceRepository = mastersAttendanceRepository;
        this.masterRepository = masterRepository;
        this.attendanceRepository = attendanceRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public MastersAttendanceDto create (MasterAttendanceCreateDto mastersAttendanceCreateDto) {

        if (!this.validationUtil.isValid(mastersAttendanceCreateDto)) {
            this.validationUtil
                    .violations(mastersAttendanceCreateDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
        Master master = masterRepository.findById(Master.class, mastersAttendanceCreateDto.getMaster());
        Attendance attendance = attendanceRepository.findById(Attendance.class, mastersAttendanceCreateDto.getAttendance());
        MastersAttendance mastersAttendance = new MastersAttendance(attendance, master);
        return modelMapper.map(this.mastersAttendanceRepository.create(mastersAttendance), MastersAttendanceDto.class);
    }

    @Override
    public void update (MastersAttendanceDto mastersAttendanceDto) {
        if (!this.validationUtil.isValid(mastersAttendanceDto)) {
            this.validationUtil
                    .violations(mastersAttendanceDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
        else {
            this.mastersAttendanceRepository
                    .update(this.modelMapper
                            .map(mastersAttendanceDto, MastersAttendance.class));
        }
    }

    @Override
    public MastersAttendanceDto findById(String id) {
        MastersAttendance mastersAttendance = this.mastersAttendanceRepository.findById(MastersAttendance.class, id);
        return modelMapper.map(mastersAttendance, MastersAttendanceDto.class);
    }

    @Override
    public List <MastersAttendanceDto> findAll() {
        List <MastersAttendance> mastersAttendances = this.mastersAttendanceRepository.findAll();
        return mastersAttendances.stream().map(mastersAttendance -> modelMapper.map(mastersAttendance, MastersAttendanceDto.class)).toList();
    }

    @Override
    public List<MasterDto> findMasterByAttendance (String id){
        Attendance attendance = attendanceRepository.findById(Attendance.class, id);
        List<Master> masters = mastersAttendanceRepository.findMasterByAttendance(attendance);
        return masters.stream().map(master -> modelMapper.map(master, MasterDto.class)).toList();
    }

    @Override
    public MastersAttendanceDto findByAttendanceAndMaster (AttendanceDto attendanceDto, MasterDto masterDto){
        Attendance attendance = modelMapper.map(attendanceDto, Attendance.class);
        Master master = masterRepository.findById(Master.class, masterDto.getId());
        MastersAttendance mastersAttendance = mastersAttendanceRepository.findByAttendanceAndMaster(attendance, master);
        return modelMapper.map(mastersAttendance, MastersAttendanceDto.class);
    }
}
