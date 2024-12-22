package rut.miit.beautySalon.services;

import java.util.List;

import rut.miit.beautySalon.services.dtos.BeautySalonDto;
import rut.miit.beautySalon.services.dtos.createDto.BeautySalonCreateDto;

public interface BeautySalonService {
    BeautySalonDto findById (String id);
    List <BeautySalonDto> findAll ();
    BeautySalonDto create (BeautySalonCreateDto beautySalonCreateDto);
    void update (BeautySalonDto beautySalonDto);
}
