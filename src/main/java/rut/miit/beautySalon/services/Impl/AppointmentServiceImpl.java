package rut.miit.beautySalon.services.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import rut.miit.beautySalon.exceptions.AppointmentNotCreateException;
import rut.miit.beautySalon.models.*;
import rut.miit.beautySalon.repositories.*;
import rut.miit.beautySalon.services.AppointmentService;
import rut.miit.beautySalon.services.dtos.AppointmentDto;
import rut.miit.beautySalon.services.dtos.AttendanceDto;
import rut.miit.beautySalon.services.dtos.MasterDto;
import rut.miit.beautySalon.services.dtos.MastersAttendanceDto;
import rut.miit.beautySalon.services.dtos.createDto.AppointmentCreateDto;
import rut.miit.beautySalon.utils.ValidationUtil;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final AttendanceRepository attendanceRepository;
    private final MasterRepository masterRepository;
    private final MastersAttendanceRepository mastersAttendanceRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, ClientRepository clientRepository, AttendanceRepository attendanceRepository, MasterRepository masterRepository, MastersAttendanceRepository mastersAttendanceRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.appointmentRepository = appointmentRepository;
        this.clientRepository = clientRepository;
        this.attendanceRepository = attendanceRepository;
        this.masterRepository = masterRepository;
        this.mastersAttendanceRepository = mastersAttendanceRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public AppointmentDto create (AppointmentCreateDto appointmentCreateDto) {
//        if (!this.validationUtil.isValid(appointmentCreateDto)) {
//            this.validationUtil
//                    .violations(appointmentCreateDto)
//                    .stream()
//                    .map(ConstraintViolation::getMessage)
//                    .forEach(System.out::println);
//        }

        List<Appointment> appointments = appointmentRepository.findAll();
        Client client = clientRepository.findByName(appointmentCreateDto.getClientName());
        List<Appointment> appointmentByClient = appointmentRepository.findAllByClient(client);
        for (int i = 0; i < appointmentByClient.size(); i++) {
            if (appointmentByClient.get(i).getDate().equals(appointmentCreateDto.getDate()))
                throw new AppointmentNotCreateException(client.getId());
        }
        Attendance attendance = attendanceRepository.findByName(appointmentCreateDto.getMastersAttendanceAttendanceName());
        String fullName = appointmentCreateDto.getMastersAttendanceMasterSurname() + " " + appointmentCreateDto.getMastersAttendanceMasterName() + " " + appointmentCreateDto.getMastersAttendanceMasterPatronymic();
        Master master = masterRepository.findByFIO(fullName);
        MastersAttendance mastersAttendance = mastersAttendanceRepository.findByAttendanceAndMaster(attendance, master);
        Appointment appointment = new Appointment(mastersAttendance, client, appointmentCreateDto.getDate());
        return modelMapper.map(this.appointmentRepository.create(appointment), AppointmentDto.class);
    }

    @Override
    public void update (AppointmentDto appointmentDto) {
        if (!this.validationUtil.isValid(appointmentDto)) {
            this.validationUtil
                    .violations(appointmentDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
        else {
            this.appointmentRepository
                    .update(this.modelMapper
                            .map(appointmentDto, Appointment.class));
        }
    }

    @Override
    public AppointmentDto findById(String id) {
        Appointment appointment = this.appointmentRepository.findById(Appointment.class, id);
        return modelMapper.map(appointment, AppointmentDto.class);
    }

    @Override
    public List <AppointmentDto> findAll() {
        List <Appointment> appointments = this.appointmentRepository.findAll();
        return appointments.stream().map(appointment -> modelMapper.map(appointment, AppointmentDto.class)).toList();
    }

    @Override
    public List <AppointmentDto> findAllByClient(String clientId) {
        Client client = clientRepository.findById(Client.class, clientId);
        List <Appointment> appointments = this.appointmentRepository.findAllByClient(client);
        return appointments.stream().map(appointment -> modelMapper.map(appointment, AppointmentDto.class)).toList();
    }

    @Override
    public List <AppointmentDto> findAllByMaster(String masterId) {
        Master master = masterRepository.findById(Master.class, masterId);
        List <Appointment> appointments = this.appointmentRepository.findAllByMaster(master);
        return appointments.stream().map(appointment -> modelMapper.map(appointment, AppointmentDto.class)).toList();
    }

    @Override
    public List <MasterDto> findFreeMasters(LocalDateTime date, String attendanceId) {
        List <MastersAttendance> mastersAttendances = mastersAttendanceRepository.findAllByAttendanceId(attendanceId);

        List <Appointment> allAppointment = new ArrayList<>();

        for (int i = 0; i < mastersAttendances.size(); i++) {
            List <Appointment> appointmentByMastersAttendance = appointmentRepository.findAllByMastersAttendance(mastersAttendances.get(i));
            for (int j = 0; j < appointmentByMastersAttendance.size(); j++) {
                LocalDateTime dateAppointment = appointmentByMastersAttendance.get(j).getDate();
                if (dateAppointment.getYear() == date.getYear()  && dateAppointment.getMonth() == date.getMonth() && dateAppointment.getDayOfMonth() == date.getDayOfMonth() && dateAppointment.getHour() == date.getHour()) {
                    allAppointment.add(appointmentByMastersAttendance.get(j));
                }
            }
        }

        for (int i = 0; i < allAppointment.size(); i++) {
            mastersAttendances.remove(allAppointment.get(i).getMastersAttendance());
        }

        List<Master> masters = new ArrayList<>(mastersAttendances.stream().map(ma -> ma.getMaster()).toList());//мастера, которые свободны на эту услугу, в это время
        List<Appointment> appointments = new ArrayList<>();
        for (int i = 0; i < masters.size(); i++) {
            appointments.addAll(appointmentRepository.findAllByMaster(masters.get(i)));
        }
        for (int i = 0; i < appointments.size(); i++) {
            LocalDateTime dateAppointment = appointments.get(i).getDate();
            if (dateAppointment.getYear() == date.getYear()  && dateAppointment.getMonth() == date.getMonth() && dateAppointment.getDayOfMonth() == date.getDayOfMonth() && dateAppointment.getHour() == date.getHour()) {
                masters.remove(appointments.get(i).getMastersAttendance().getMaster());
            }
        }

        return masters.stream().map(m -> modelMapper.map(m, MasterDto.class)).toList();
    }

    @Override
    public List <MasterDto> findMastersByClient(String clientId) {
        Client client = clientRepository.findById(Client.class, clientId);
        List<Appointment> appointments = this.appointmentRepository.findAllByClient(client);
        List<Master> masters = new ArrayList<>();
        for (int i = 0; i < appointments.size(); i++){
            if (!masters.contains(appointments.get(i).getMastersAttendance().getMaster()))
                masters.add(appointments.get(i).getMastersAttendance().getMaster());
        }
        return masters.stream().map(master -> modelMapper.map(master, MasterDto.class)).toList();
    }
}
