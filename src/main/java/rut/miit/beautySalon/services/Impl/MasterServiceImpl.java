package rut.miit.beautySalon.services.Impl;

import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import rut.miit.beautySalon.models.*;
import rut.miit.beautySalon.models.enums.UserRoles;
import rut.miit.beautySalon.repositories.AppointmentRepository;
import rut.miit.beautySalon.repositories.BeautySalonRepository;
import rut.miit.beautySalon.repositories.FeedbackRepository;
import rut.miit.beautySalon.repositories.MasterRepository;
import rut.miit.beautySalon.services.MasterService;
import rut.miit.beautySalon.services.dtos.BeautySalonDto;
import rut.miit.beautySalon.services.dtos.FeedbackDto;
import rut.miit.beautySalon.services.dtos.MasterDto;
import rut.miit.beautySalon.services.dtos.MasterRatingDto;
import rut.miit.beautySalon.services.dtos.createDto.MasterCreateDto;
import rut.miit.beautySalon.utils.ValidationUtil;

@Service
@EnableCaching
public class MasterServiceImpl implements MasterService {

    private final MasterRepository masterRepository;
    private final BeautySalonRepository beautySalonRepository;
    private final FeedbackRepository feedbackRepository;
    private final AppointmentRepository appointmentRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public MasterServiceImpl (MasterRepository masterRepository, BeautySalonRepository beautySalonRepository, FeedbackRepository feedbackRepository, AppointmentRepository appointmentRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.masterRepository = masterRepository;
        this.beautySalonRepository = beautySalonRepository;
        this.feedbackRepository = feedbackRepository;
        this.appointmentRepository = appointmentRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    @CacheEvict(cacheNames = "masters", allEntries = true)
    public MasterDto create (MasterCreateDto masterCreateDto) {

        if (!this.validationUtil.isValid(masterCreateDto)) {
            this.validationUtil
                    .violations(masterCreateDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }

        BeautySalon beautySalon = beautySalonRepository.findById(BeautySalon.class, masterCreateDto.getBeautySalonId());
        if (beautySalon == null){
            throw new RuntimeException();
        }
        Master master = new Master(beautySalon, masterCreateDto.getName(), masterCreateDto.getSurname(), masterCreateDto.getPatronymic(), masterCreateDto.getEmail(), masterCreateDto.getUsername(), masterCreateDto.getPassword());
        return modelMapper.map(masterRepository.create(master), MasterDto.class);
    }

    @Override
    @CacheEvict(cacheNames = "masters", allEntries = true)
    public MasterDto update (MasterDto masterDto) {
        if (!this.validationUtil.isValid(masterDto)) {
            this.validationUtil
                    .violations(masterDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
        Master master = masterRepository.findById(Master.class, masterDto.getId());
        master.setEmail(masterDto.getEmail());
        master.setName(masterDto.getName());
        master.setSurname(masterDto.getSurname());
        master.setPatronymic(masterDto.getPatronymic());
        master.setUsername(masterDto.getUsername());
        master.setPassword(masterDto.getPassword());
        return modelMapper.map(this.masterRepository.update(master), MasterDto.class);
    }

    @Override
    public MasterDto findById(String id) {
        Master master = this.masterRepository.findById(Master.class, id);
        return modelMapper.map(master, MasterDto.class);
    }

    @Override
    @Cacheable(value = "masters", key = "'allMasters'")
    public List <MasterDto> findAll() {
        return masterRepository.findAll().stream().map(master -> modelMapper.map(master, MasterDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List <MasterDto> findAllByRole(String role) {
        List <Master> masters = this.masterRepository.findAll();
        List <Master> result = new ArrayList<>();
        for (int i = 0; i < masters.size(); i++){
            if (masters.get(i).getRoles().get(0).getName().name().equals(role)){
                result.add(masters.get(i));
            }
        }
        return result.stream().map(master -> modelMapper.map(master, MasterDto.class)).toList();
    }

    @Override
    public MasterDto findByFIO(String name, String surname, String patronymic) {
        String fullName = surname + " " + name + " " + patronymic;
        Master master = this.masterRepository.findByFIO(fullName);
        return modelMapper.map(master, MasterDto.class);
    }

    public static List<String> findByStream(List<String> arr, int n) {
        return arr.stream().collect(Collectors.groupingBy(i -> i, Collectors.counting()))
                .entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(Map.Entry::getKey)
                .limit(n)
                .collect(Collectors.toList());
    }


    @Override
    public List<MasterRatingDto> rating () {
        List<Master> masterByAppointment = appointmentRepository.findAll().stream()
                .map(a -> a.getMastersAttendance().getMaster()).toList();
        HashSet<Master> noDupMaster = new HashSet<>(masterByAppointment);
        List<Master> noDupMasterList = noDupMaster.stream().toList();
        List<MasterRatingDto> masterRatingList = new ArrayList<>();
        for (int i = 0; i < noDupMaster.size(); i++){
            List<Feedback> feedbacks = feedbackRepository.findAllByMaster(noDupMasterList.get(i));
            List<FeedbackDto> feedbackDtos = feedbacks.stream().map(f -> modelMapper.map(f, FeedbackDto.class)).toList();
            String master = noDupMasterList.get(i).getSurname() + " " + noDupMasterList.get(i).getName() + " " + noDupMasterList.get(i).getPatronymic();
            int count = Collections.frequency(masterByAppointment, noDupMasterList.get(i));
            MasterRatingDto masterRatingDto = new MasterRatingDto(master, feedbackDtos, count);
            masterRatingList.add(masterRatingDto);
        }
//        List<String> mastersId = findByStream(masterByAppointmentId, 3);
//        List<Master> masters = mastersId.stream().map(m -> masterRepository.findById(Master.class, m)).toList();
//        for (int i = 0; i < masters.size(); i++) {
//            List<Feedback> feedbacks = feedbackRepository.findAllByMaster(masters.get(i));
//            List<FeedbackDto> feedbackDtos = feedbacks.stream().map(f -> modelMapper.map(f, FeedbackDto.class)).toList();
//            String master = masters.get(i).getSurname() + " " + masters.get(i).getName() + " " + masters.get(i).getPatronymic();
//            MasterRatingDto masterRatingDto = new MasterRatingDto(master, feedbackDtos, 0);
//            masterRatingList.add(masterRatingDto);
//        }
        masterRatingList.sort(new MasterComparator());
        return masterRatingList;
    }
}

class MasterComparator implements Comparator <MasterRatingDto> {
    @Override
    public int compare(MasterRatingDto master1, MasterRatingDto master2) {
        return master2.getCount() - master1.getCount();
    }
}