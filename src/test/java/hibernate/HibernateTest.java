package hibernate;

import domain.*;
import junit.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.*;

import org.hibernate.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HibernateTest {

    @BeforeEach
    public void setUp(){

    }

    @Test
    public void createSchedules(){

        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        List<Activity> schedulesList = new ArrayList<Activity>();

        //List<Activity> activities = new ArrayList<>();
        Category category = new Category("UnitTestCategory");
        //Activity activity = new Activity("UnitTestActivity", category);
        //activities.add(activity);


        //User unittestUser = new User("UnitTestUser", "Password", "Unittest@email.com");

        Activity activity1 = new Activity("UnitTestActivity1", category);

        Activity activity2 = new Activity("UnitTestActivity2", category);

        schedulesList.add(activity1);
        schedulesList.add(activity2);

        session.save(activity1);
        session.save(activity2);

        session.getTransaction().commit();

        Query q = session.createQuery("From Activity ");

        List<Activity> resultList = q.list();

        // Assert if amount of matches added to the database equals the amount of matches in the database
        Assert.assertEquals(schedulesList.size(), resultList.size());

        // Assert if all the data that has been entered in the database is correct
        for (int i = 0; i < schedulesList.size(); i++){
            Assert.assertEquals(schedulesList.get(i).getName(), resultList.get(i).getCategory());
            //Assert.assertEquals(schedulesList.get(i).getFrequency(), resultList.get(i).getFrequency());
        }
    }

}