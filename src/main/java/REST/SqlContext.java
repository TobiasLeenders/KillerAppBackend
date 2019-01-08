package REST;

import domain.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public void newGroup(String groupName, List<Integer> userids){
        //, List<Integer> userId
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("killerappPersistence");

        EntityManager entityManager = emFactory.createEntityManager();
        entityManager.getTransaction().begin();

        GroupSchedule groupSchedule = new GroupSchedule();
        List<User> currentGroup = new ArrayList<>();
        if (groupSchedule.getUsers() != null){
            currentGroup = groupSchedule.getUsers();
        }
        else {
            currentGroup = new ArrayList<>();
        }
        for (int i = 0; i < userids.size(); i++){
            /*User userAddToGroup = entityManager.find(User.class, userids.get(i));
            groupSchedule.getUsers().add(userAddToGroup);*/
            User userAddToGroup = entityManager.find(User.class, (long)userids.get(i));

            currentGroup.add(userAddToGroup);
        }
        groupSchedule.setUsers(currentGroup);
        /*User userAddToGroup = entityManager.find(User.class, (long)userids);
        if (groupSchedule.getUsers() != null){
            List<User> currentGroup = groupSchedule.getUsers();
            currentGroup.add(userAddToGroup);
            groupSchedule.setUsers(currentGroup);
        }
        else {
            List<User> userselse = new ArrayList<>();
            userselse.add(userAddToGroup);
            groupSchedule.setUsers(userselse);
        }*/

        //User addUser = new User((long)1, "test", "test@test.com", "test@test.com");
        groupSchedule.setName(groupName);

        entityManager.merge(groupSchedule);
        entityManager.getTransaction().commit();

        entityManager.close();
        emFactory.close();
    }

    public void newSchedule(String schedulename, int duration, String frequency, String startTime, int groupId, int userId, List<String> activitynames, List<String> activitycategories){
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("killerappPersistence");

        EntityManager entityManager = emFactory.createEntityManager();
        entityManager.getTransaction().begin();

        List<Activity> addActivities = new ArrayList<>();
        for (int i = 0; i < activitynames.size(); i++){
            //Category addCategory = new Category(activitycategories.get(i));
            //Category addCategory = entityManager.find(Category.class, (long)activitycategories.get(i));
            Category addCategory = new Category("testCategory");
            Activity addActivity = new Activity(activitynames.get(i), addCategory);
            addActivities.add(addActivity);
        }

        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        //LocalDateTime formattedDateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime formattedDateTime = LocalDateTime.now();
        GroupSchedule addGroup = entityManager.find(GroupSchedule.class, (long)groupId);
        Schedule schedule = new Schedule(schedulename, formattedDateTime, duration, domain.Frequency.valueOf(frequency), addGroup, addActivities);
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

    public List<Activity> getScheduleActivities(int scheduleid) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("killerappPersistence");
        EntityManager em = emf.createEntityManager();
        //em.getTransaction().begin();
        Schedule schedule = em.find(Schedule.class, (long)scheduleid);
        Hibernate.initialize(schedule.getActivity());
        List<Activity> activitiesList = schedule.getActivity();
        //em.getTransaction().commit();
        em.close();
        emf.close();
        return activitiesList;
    }

    public List<Schedule> getSchedules(int userid){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("killerappPersistence");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        return em.createQuery( "from Schedule ", Schedule.class ).getResultList();
    }

    public List<Schedule> getAllSchedules(int userid){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("killerappPersistence");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Schedule> allschedules = em.createQuery( "from Schedule ", Schedule.class ).getResultList();
        for (Schedule scheduleaddactivities: allschedules){
            scheduleaddactivities.getActivity();
        }
        return allschedules;
    }
}
