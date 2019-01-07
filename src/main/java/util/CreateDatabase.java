package util;

import com.fasterxml.classmate.AnnotationConfiguration;
import domain.Group;
import domain.Schedule;
import domain.User;
import org.hibernate.*;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static domain.Frequency.YEARLY;

public class CreateDatabase {
    public static void main(String[] args) {
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            //session.save(new User( "testusername", "testpassword", "testemail"));
            User testuser = new User( "testusername", "testpassword", "testemail");
            List<User> userlist = new ArrayList<>();
            userlist.add(testuser);
            Group testGroup = new Group();
            testGroup.setName("TestGroup");
            testGroup.setUsers(userlist);
            session.save(testGroup);
            session.getTransaction().commit();
        }
        finally {
            if (session!=null) session.close();
        }
    }
    /*public static void main(String[] args) {
        EntityManagerFactory emfactory = Persistence.createEntityManagerFactory( "killerappPersistence" );

        EntityManager entitymanager = emfactory.createEntityManager( );
        entitymanager.getTransaction( ).begin( );

        User user = new User();
        user.setEmail("test@test.com");
        user.setUsername("TestUser");
        user.setPassword("Password");

        List<User> userslist = new ArrayList<>();

        userslist.add(user);

        Group group = new Group( );
        group.setName("testGroup");
        group.setUsers(userslist);

        entitymanager.persist( group );
        entitymanager.getTransaction( ).commit( );

        entitymanager.close( );
        emfactory.close( );
    }*/
}
