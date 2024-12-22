package rut.miit.beautySalon.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import rut.miit.beautySalon.models.BeautySalon;

@Repository
public interface BeautySalonRepository {
    List <BeautySalon> findAll ();
    BeautySalon findById(Class<BeautySalon> beautySalonClass, String id);
    BeautySalon create (BeautySalon beautySalon);
    BeautySalon update (BeautySalon beautySalon);
}
