package rut.miit.beautySalon.services;

import rut.miit.beautySalon.services.dtos.ClientDto;
import rut.miit.beautySalon.services.dtos.FeedbackDto;
import rut.miit.beautySalon.services.dtos.createDto.FeedbackCreateDto;

import java.util.List;

public interface FeedbackService {
    FeedbackDto findById (String id);
    List<FeedbackDto> findAll ();
    List<FeedbackDto> findAllByClient (String id);
    FeedbackDto create (FeedbackCreateDto feedbackCreateDto);
}
