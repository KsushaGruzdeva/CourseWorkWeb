package rut.miit.beautySalon.services.Impl;

import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.beautySalon.models.Client;
import rut.miit.beautySalon.models.Feedback;
import rut.miit.beautySalon.models.Master;
import rut.miit.beautySalon.repositories.ClientRepository;
import rut.miit.beautySalon.repositories.FeedbackRepository;
import rut.miit.beautySalon.repositories.MasterRepository;
import rut.miit.beautySalon.services.FeedbackService;
import rut.miit.beautySalon.services.dtos.ClientDto;
import rut.miit.beautySalon.services.dtos.FeedbackDto;
import rut.miit.beautySalon.services.dtos.MasterDto;
import rut.miit.beautySalon.services.dtos.createDto.FeedbackCreateDto;
import rut.miit.beautySalon.utils.ValidationUtil;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ClientRepository clientRepository;
    private final MasterRepository masterRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, ClientRepository clientRepository, MasterRepository masterRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.feedbackRepository = feedbackRepository;
        this.clientRepository = clientRepository;
        this.masterRepository = masterRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public FeedbackDto findById(String id) {
        Feedback feedback = this.feedbackRepository.findById(Feedback.class, id);
        return this.modelMapper.map(feedback, FeedbackDto.class);
    }

    @Override
    public List<FeedbackDto> findAll() {
        List <Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks.stream().map(feedback -> modelMapper.map(feedback, FeedbackDto.class)).toList();
    }

    @Override
    public List<FeedbackDto> findAllByClient(String id) {
        Client client = clientRepository.findById(Client.class, id);
        List <Feedback> feedbacks = feedbackRepository.findAllByClient(client);
        return feedbacks.stream().map(feedback -> modelMapper.map(feedback, FeedbackDto.class)).toList();
    }

    @Override
    public FeedbackDto create(FeedbackCreateDto feedbackCreateDto) {
        if (!this.validationUtil.isValid(feedbackCreateDto)) {
            this.validationUtil
                    .violations(feedbackCreateDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
        Client client = clientRepository.findByName(feedbackCreateDto.getClientName());
        String fullName = feedbackCreateDto.getMasterSurname() + " " + feedbackCreateDto.getMasterName() + " " + feedbackCreateDto.getMasterPatronymic();
        Master master = masterRepository.findByFIO(fullName);
        Feedback feedback = new Feedback(client, master, feedbackCreateDto.getFeedback());
        return modelMapper.map(feedbackRepository.create(feedback), FeedbackDto.class);
    }
}
