package rut.miit.beautySalon.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import rut.miit.beautySalon.models.Attendance;
import rut.miit.beautySalon.repositories.AttendanceRepository;
import rut.miit.beautySalon.services.AttendanceService;
import rut.miit.beautySalon.services.dtos.AttendanceDto;
import rut.miit.beautySalon.services.dtos.MasterDto;
import rut.miit.beautySalon.services.dtos.createDto.AttendanceCreateDto;
import rut.miit.beautySalon.utils.ValidationUtil;

@Service
@EnableCaching
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public AttendanceServiceImpl (AttendanceRepository attendanceRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.attendanceRepository = attendanceRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    @CacheEvict(cacheNames = "attendances", allEntries = true)
    public AttendanceDto create (AttendanceCreateDto attendanceCreateDto) {

        if (!this.validationUtil.isValid(attendanceCreateDto)) {
            this.validationUtil
                    .violations(attendanceCreateDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
        Attendance attendance = modelMapper.map(attendanceCreateDto, Attendance.class);
        return modelMapper.map(this.attendanceRepository.create(attendance), AttendanceDto.class);
    }

    @Override
    @CacheEvict(cacheNames = "attendances", allEntries = true)
    public void update (AttendanceDto attendanceDto) {
        if (!this.validationUtil.isValid(attendanceDto)) {
            this.validationUtil
                    .violations(attendanceDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
        else {
            this.attendanceRepository
                    .update(this.modelMapper
                            .map(attendanceDto, Attendance.class));
        }
    }

    @Override
    public AttendanceDto findById(String id) {
        Attendance attendance = this.attendanceRepository.findById(Attendance.class, id);
        return modelMapper.map(attendance, AttendanceDto.class);
    }

    @Override
    public AttendanceDto findByName(String name) {
        Attendance attendance = this.attendanceRepository.findByName(name);
        return modelMapper.map(attendance, AttendanceDto.class);
    }

    @Override
    @Cacheable(value = "attendances", key = "'allAttendances'")
    public List <AttendanceDto> findAll() {
        return attendanceRepository.findAll().stream().map(attendance -> modelMapper.map(attendance, AttendanceDto.class))
                .collect(Collectors.toList());
    }
}
