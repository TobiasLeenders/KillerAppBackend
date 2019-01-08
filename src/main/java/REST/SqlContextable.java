package REST;

import domain.Activity;
import domain.Frequency;
import domain.Schedule;
import domain.User;

import java.util.ArrayList;
import java.util.List;

public interface SqlContextable {
    void register(String username, String password, String email);
    User login(String username, String password);
    void newActivity(String activityname, String categoryname);
    void newGroup(String groupname, List<Integer> userids);
    void newSchedule(String schedulename, int duration, String frequency, String startTime, int groupId, int userId, List<String> activitynames, List<String> activitycategories);
    List<Activity> getActivities();
    List<Activity> getScheduleActivities(int scheduleid);
    List<Schedule> getSchedules(int userid);
    List<Schedule> getAllSchedules(int userid);
}
