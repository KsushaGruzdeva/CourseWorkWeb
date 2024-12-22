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
import rut.miit.beautySalon.models.BeautySalon;
import rut.miit.beautySalon.repositories.BeautySalonRepository;
import rut.miit.beautySalon.services.BeautySalonService;
import rut.miit.beautySalon.services.dtos.BeautySalonDto;
import rut.miit.beautySalon.services.dtos.createDto.BeautySalonCreateDto;
import rut.miit.beautySalon.utils.ValidationUtil;

@Service
@EnableCaching
public class BeautySalonServiceImpl implements BeautySalonService {

    private final BeautySalonRepository beautySalonRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public BeautySalonServiceImpl (BeautySalonRepository beautySalonRepository, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.beautySalonRepository = beautySalonRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    @CacheEvict(cacheNames = "beautySalon", allEntries = true)
    public BeautySalonDto create (BeautySalonCreateDto beautySalonCreateDto) {

        if (!this.validationUtil.isValid(beautySalonCreateDto)) {
            this.validationUtil
                    .violations(beautySalonCreateDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }

        BeautySalon beautySalon = modelMapper.map(beautySalonCreateDto, BeautySalon.class);
        return modelMapper.map(this.beautySalonRepository.create(beautySalon), BeautySalonDto.class);
    }

    @Override
    @CacheEvict(cacheNames = "beautySalon", allEntries = true)
    public void update (BeautySalonDto beautySalonDto) {
        if (!this.validationUtil.isValid(beautySalonDto)) {
            this.validationUtil
                    .violations(beautySalonDto)
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
        }
        else {
            this.beautySalonRepository
                    .update(this.modelMapper
                            .map(beautySalonDto, BeautySalon.class));
        }
    }

    @Override
    public BeautySalonDto findById(String id) {
        BeautySalon beautySalon = this.beautySalonRepository.findById(BeautySalon.class, id);
        return modelMapper.map(beautySalon, BeautySalonDto.class);
    }

    @Override
    @Cacheable(value = "beautySalon", key = "'allBeautySalons'")
    public List <BeautySalonDto> findAll() {
        return beautySalonRepository.findAll().stream().map(beautySalon -> modelMapper.map(beautySalon, BeautySalonDto.class))
                .collect(Collectors.toList());
    }
}
