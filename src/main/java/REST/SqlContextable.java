package REST;

import domain.Activity;
import domain.Schedule;
import domain.User;

import java.util.List;

public interface SqlContextable {
    void register(String username, String password, String email);
    User login(String username, String password);
    void newActivity(String activityname, String categoryname);
    void newSchedule(String activityname, String categoryname);
    List<Activity> getActivities();
    List<Schedule> getSchedules(int userid);
}
