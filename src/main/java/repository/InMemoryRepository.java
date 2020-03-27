package repository;

import domains.Entity;
import domains.Entity;
import validators.ValidationException;
import validators.Validator;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<ID, E extends Entity<ID>>  implements CrudRepository<ID,E>{
    private Map<ID,E> entities;
    private Validator<E> validator;

    public InMemoryRepository(Validator<E> validator) {
        this.entities = new HashMap<>();
        this.validator = validator;
    }

    /**
     *
     * @param id -the id of the entity to be returned
     * id must not be null
     * @return the entity with the specified id
     * or null - if there is no entity with the given id
     * @throws IllegalArgumentException
     * if id is null.
     */
    @Override
    public E findOne(ID id) {


        if (id==null)
            throw new IllegalArgumentException("id must not be null");
        if (entities.get(id)==null) return null;
        return entities.get(id);
    }

    /**
     * @return all entities
     */
    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    /**
     * @param entity
     * entity must be not null
     * @return null- if the given entity is saved
     * otherwise returns the entity (id already exists)
     * @throws ValidationException
     * if the entity is not valid
     * @throws IllegalArgumentException
     * if the given entity is null. *
     */
    @Override
    public E save(E entity) throws ValidationException {
        if (entity==null) throw new IllegalArgumentException("Entity must be not null!");
        E oldvalue=entities.get(entity.getId());
        if (oldvalue==null)
        {
            validator.validate(entity);
            entities.put(entity.getId(),entity);
            return null;
        }
        return entity;

    }

    /**
     * removes the entity with the specified id
     * @param id
     * id must be not null
     * @return the removed entity or null if there is no entity with the
    given id
     * @throws IllegalArgumentException
     * if the given id is null.
     */
    @Override
    public E delete(ID id) {

        if(id==null){
            throw new IllegalArgumentException("Id must not be null!");
        }
        E oldvalue = entities.get(id);
        if(oldvalue==null){
            return null;
        }
        entities.remove(id);
        return oldvalue;
    }

    /**
     *
     * @param entity
     * entity must not be null
     * @return null - if the entity is updated,
     * otherwise returns the entity - (e.g id does not
    exist).
     * @throws IllegalArgumentException
     * if the given entity is null.
     * @throws ValidationException
     * if the entity is not valid.
     */
    @Override
    public E update(E entity) {
       if(entity==null) {
            throw new IllegalArgumentException("Entity must not be null!");
        }

        E oldvalue=entities.get(entity.getId());
        if(oldvalue != null){
                validator.validate(entity);
                entities.put(entity.getId(),entity);
            return null;
        }
        return entity;
    }
}