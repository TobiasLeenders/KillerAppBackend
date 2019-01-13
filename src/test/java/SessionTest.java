import domain.Activity;
import domain.Category;
import domain.User;
import junit.framework.Assert;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

public class SessionTest {

    Session session = null;
    Activity activity1 = null;
    Activity activity2 = null;

    @BeforeEach
    public void setUp(){
        session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();

        List<Activity> schedulesList = new ArrayList<Activity>();

        Category category = new Category("UnitTestCategory");

        activity1 = new Activity("UnitTestActivity1", category);

        activity2 = new Activity("UnitTestActivity2", category);

        schedulesList.add(activity1);
        schedulesList.add(activity2);

        session.save(activity1);
        session.save(activity2);

        session.getTransaction().commit();
    }

    @Test
    public void sessionGetTest() {
        Activity sessionUser = (Activity) session.get(Activity.class, activity1.getId());
        Assert.assertEquals(sessionUser, activity1);

        Activity sessionUser2 = (Activity) session.get(Activity.class, activity2.getId());
        Assert.assertEquals(sessionUser2, activity2);
    }
}
