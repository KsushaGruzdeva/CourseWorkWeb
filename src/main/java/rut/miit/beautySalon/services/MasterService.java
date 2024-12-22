package rut.miit.beautySalon.services;

import java.util.List;
import java.util.Map;

import rut.miit.beautySalon.services.dtos.FeedbackDto;
import rut.miit.beautySalon.services.dtos.MasterDto;
import rut.miit.beautySalon.services.dtos.MasterRatingDto;
import rut.miit.beautySalon.services.dtos.createDto.MasterCreateDto;

public interface MasterService {
    MasterDto findById (String id);
    List <MasterDto> findAll ();
    List <MasterDto> findAllByRole(String role);
    MasterDto findByFIO (String name, String surname, String patronymic);
    MasterDto create (MasterCreateDto masterCreateDto);
    MasterDto update (MasterDto masterDto);
    List<MasterRatingDto> rating ();
}
