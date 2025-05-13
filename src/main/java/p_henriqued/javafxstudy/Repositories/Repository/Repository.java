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

    @Setter @Getter
    private T object;

    public Repository() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("javafx-persistence-unit");
    }

    public void save(T object){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public T findById(Class<T> objClass,Long id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        T obj = entityManager.find(objClass, id);
        entityManager.close();
        return obj;
    }

    public List<T> findAll(Class<T> objClass){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        String entityName = objClass.getAnnotation(Entity.class).name();
        List<T> objList = entityManager
                .createQuery("SELECT e FROM " + entityName + " e", objClass)
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

    public void delete(Class<T> objClass,Long id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        var dep = entityManager.find(objClass, id);
        if(dep != null) entityManager.remove(objClass);
        entityManager.getTransaction().commit();
        entityManager.close();
    }


}
