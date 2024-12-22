package rut.miit.beautySalon.repositories.Impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import rut.miit.beautySalon.models.BeautySalon;
import rut.miit.beautySalon.repositories.BeautySalonRepository;

@Repository
public class BeautySalonRepositoryImpl extends BaseRepository<BeautySalon, String> implements BeautySalonRepository{
    @PersistenceContext
    private EntityManager entityManager;

    public BeautySalonRepositoryImpl(){
        super(BeautySalon.class);
    }

    @Override
    public List <BeautySalon> findAll () {
        return entityManager.createQuery("from BeautySalon bs", BeautySalon.class)
        .getResultList();
    }
}
