package REST;

import domain.Activity;
import domain.Category;
import domain.Schedule;
import domain.User;

import javax.persistence.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class SqlContext implements SqlContextable {

    public void register(String username, String password, String email){
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("killerappPersistence");

        EntityManager entityManager = emFactory.createEntityManager();
        entityManager.getTransaction().begin();

        User user = new User(username, password, email);
        entityManager.persist(user);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public User login(String username, String password){
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("killerappPersistence");

        EntityManager entityManager = emFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = null;
        user = entityManager.createQuery("from User where username = :username and password = :password", User.class).setParameter("username", username).setParameter("password", password).getSingleResult();
        return user;
    }

    public void newActivity(String activityname, String categoryname){
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("killerappPersistence");

        EntityManager entityManager = emFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Category category = new Category(categoryname);
        Activity activity = new Activity(activityname, category);
        entityManager.persist(activity);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public void newSchedule(String activityname, String categoryname){
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("killerappPersistence");

        EntityManager entityManager = emFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Schedule schedule = new Schedule();
        entityManager.persist(schedule);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public List<Activity> getActivities(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("killerappPersistence");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        return em.createQuery( "from Activity ", Activity.class ).getResultList();
    }

    public List<Schedule> getSchedules(int userid){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("killerappPersistence");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        return em.createQuery( "from Schedule ", Schedule.class ).getResultList();
    }
}
