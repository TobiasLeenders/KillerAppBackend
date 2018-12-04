package util;

import com.fasterxml.classmate.AnnotationConfiguration;
import domain.Schedule;
import domain.User;
import org.hibernate.*;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

import static domain.Frequency.YEARLY;

public class CreateDatabase {
    public static void main(String[] args) {
        Session session = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(new User( "testusername", "testpassword", "testemail"));
            session.getTransaction().commit();
        }
        finally {
            if (session!=null) session.close();
        }
    }
}
