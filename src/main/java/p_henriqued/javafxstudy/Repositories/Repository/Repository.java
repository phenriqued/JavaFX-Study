package p_henriqued.javafxstudy.Repositories.Repository;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class Repository<T> {

    private final EntityManagerFactory entityManagerFactory;

    @Getter
    private Class<T> object;

    public Repository(Class<T> objClass) {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("javafx-persistence-unit");
        object = objClass;
    }

    public void save(T object){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public T findById(Long id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        T obj = entityManager.find(this.object, id);
        entityManager.close();
        return obj;
    }

    public List<T> findAll(){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String entityName = object.getAnnotation(Entity.class).name();
        List<T> objList = entityManager
                .createQuery("SELECT e FROM " + entityName + " e", this.object)
                .getResultList();
        entityManager.close();
        return objList;
    }

    public void update(T object){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(object);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void delete(Long id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        var dep = entityManager.find(this.object, id);
        if(dep != null) entityManager.remove(dep);
        entityManager.getTransaction().commit();
        entityManager.close();
    }


}
