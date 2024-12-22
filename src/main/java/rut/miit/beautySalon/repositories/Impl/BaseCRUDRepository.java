package rut.miit.beautySalon.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public abstract class BaseCRUDRepository<Entity, T> {
    private final Class <Entity> entityClass;

    public BaseCRUDRepository(Class<Entity> entityClass) {
        this.entityClass = entityClass;
    }

    @PersistenceContext
    private EntityManager entityManager;

    public Entity findById(Class<Entity> entityClass, String id) {
        return entityManager.find(entityClass, id);
    }

    @Transactional
    public Entity create (Entity entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public Entity update (Entity entity) {
        entityManager.merge(entity);
        return entity;
    }

    @Transactional
    public Entity delete (Entity entity) {
        entityManager.remove(entity);
        return entity;
    }
}
