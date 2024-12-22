package rut.miit.beautySalon.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import rut.miit.beautySalon.models.Master;
import rut.miit.beautySalon.models.User;

@Repository
public interface MasterRepository{
    List<Master> findAll ();
    Master findById(Class<Master> masterClass, String id);
    Master create (Master master);
    Master update (Master master);
    Master findByFIO(String fullName);
    Master findByEmail(String email);
    List<Master> findByUsername(String username);
    int count();
}
