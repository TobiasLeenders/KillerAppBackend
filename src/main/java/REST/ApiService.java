package REST;

import com.google.gson.JsonObject;
import domain.Activity;
import domain.Frequency;
import domain.Schedule;
import domain.User;
import org.hibernate.Hibernate;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("api")
public class ApiService {
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response register(@FormParam("username") String username, @FormParam("password") String password, @FormParam("email") String email){
        try {
            Service.context.register(username, password, email);
        } catch (Exception e) {
            return Response.status(400).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String login(@FormParam("username") String username, @FormParam("password") String password){
        User loginUser = null;
        try {
            loginUser = Service.context.login(username, password);
        } catch (Exception e) {
            //return Response.status(400).header("Access-Control-Allow-Origin", "*").build();
        }
        JsonObject json = new JsonObject();
        json.addProperty("username", loginUser.getUsername());
        json.addProperty("password", loginUser.getPassword());
        return json.toString();
        //return Response.status(200).header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("/newActivity")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response newActivity(@FormParam("activityname") String activityname, @FormParam("categoryname") String categoryname){
        try {
            Service.context.newActivity(activityname, categoryname);
        } catch (Exception e) {
            return Response.status(400).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/getActivities")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMatch() {
        List<Activity> result = null;
        try {
            result = Service.context.getActivities();
        } catch (Exception e) {
            //ignore
        }

        JsonObject json = new JsonObject();
        JsonObject activitiesObject = new JsonObject();

        if (result != null){
            for (int i = 0; i < result.size(); i++){
                JsonObject innerObject = new JsonObject();

                innerObject.addProperty("ActivityId", result.get(i).getId());
                innerObject.addProperty("ActivityName", result.get(i).getName());
                innerObject.addProperty("CategoryId", result.get(i).getCategory().getId());
                innerObject.addProperty("CategoryName", result.get(i).getCategory().getName());
                activitiesObject.add("" + i, innerObject);
            }
            json.add("Activities", activitiesObject);
        }

        return json.toString();
    }

    @POST
    @Path("/newGroup")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response newSchedule(@FormParam("groupname") String groupname, @FormParam("userids") List<Integer> userids){
        try {
            Service.context.newGroup(groupname, userids);
        } catch (Exception e) {
            return Response.status(400).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").build();
    }

    @POST
    @Path("/newSchedule")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response newSchedule(@FormParam("schedulename") String schedulename, @FormParam("duration") String duration, @FormParam("frequency") String frequency, @FormParam("startTime") String startTime, @FormParam("group_id") String groupId, @FormParam("user_id") String userId, @FormParam("activities") String activitynames, @FormParam("categories") String activitycategories){
        try {
            String[] parts = activitynames.split(", ");
            Service.context.newSchedule(schedulename, Integer.parseInt(duration), frequency, startTime, Integer.parseInt(groupId), Integer.parseInt(userId), Arrays.asList(parts), Arrays.asList(parts));
        } catch (Exception e) {
            return Response.status(400).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/getSchedules/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSchedule(@PathParam("userId") int userid) {
        List<Schedule> result = null;
        try {
            result = Service.context.getSchedules(userid);
        } catch (Exception e) {
            //ignore
        }

        JsonObject json = new JsonObject();
        JsonObject schedulesObject = new JsonObject();

        if (result != null){
            for (int i = 0; i < result.size(); i++){
                JsonObject innerObject = new JsonObject();

                innerObject.addProperty("ScheduleId", result.get(i).getId());
                innerObject.addProperty("ScheduleName", result.get(i).getName());
                //innerObject.addProperty("Activities", result.get(i).getActivity());
                schedulesObject.add("" + i, innerObject);
            }
            json.add("Schedules", schedulesObject);
        }

        return json.toString();
    }

    @GET
    @Path("/getAllSchedules/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllSchedule(@PathParam("userId") int userid) {
        List<Schedule> result = null;
        try {
            result = Service.context.getAllSchedules(userid);
        } catch (Exception e) {
            //ignore
        }

        JsonObject json = new JsonObject();
        JsonObject schedulesObject = new JsonObject();

        if (result != null){
            for (int i = 0; i < result.size(); i++){
                JsonObject innerObject = new JsonObject();

                innerObject.addProperty("ScheduleId", result.get(i).getId());
                innerObject.addProperty("ScheduleName", result.get(i).getName());
                for (int j = 0; j < result.get(i).getActivity().size(); j++){
                    JsonObject activityObject = new JsonObject();

                    activityObject.addProperty("ActivityId", result.get(i).getActivity().get(j).getId());
                    activityObject.addProperty("ActivityName", result.get(i).getActivity().get(j).getName());
                    activityObject.addProperty("ActivityCategory", result.get(i).getActivity().get(j).getCategory().getName());
                    innerObject.add("Activities" + j, activityObject);
                }
                //innerObject.addProperty("Activities", result.get(i).getActivity());
                schedulesObject.add("" + i, innerObject);
            }
            json.add("Schedules", schedulesObject);
        }

        return json.toString();
    }

    @GET
    @Path("/getScheduleActivities/{scheduleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getScheduleActivities(@PathParam("scheduleId") int scheduleid) {
        List<Activity> result = null;
        try {
            result = Service.context.getScheduleActivities(scheduleid);
        } catch (Exception e) {
            //ignore
            System.err.println(e);
        }

        JsonObject json = new JsonObject();
        JsonObject activitiesObject = new JsonObject();

        if (result != null){
            for (int i = 0; i < result.size(); i++){
                JsonObject innerObject = new JsonObject();

                innerObject.addProperty("ActivityId", result.get(i).getId());
                innerObject.addProperty("ActivityName", result.get(i).getName());
                activitiesObject.add("" + i, innerObject);
            }
            json.add("Activities", activitiesObject);
        }

        return json.toString();
    }

    @GET
    @Path("/getMatchesSummonerId/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getMatchesSummonerId(@PathParam("userId") int userid) {
        List<Schedule> result = null;
        try {
            result = Service.context.getSchedules(userid);
        } catch (Exception e) {
            //ignore
        }

        JsonObject json = new JsonObject();
        JsonObject scheduleObject = new JsonObject();

        if (result != null){
            for (int i = 0; i < result.size(); i++){
                JsonObject innerObject = new JsonObject();

                innerObject.addProperty("Name", result.get(i).getName());
                innerObject.addProperty("Frequency", result.get(i).getFrequency().toString());
                innerObject.addProperty("StartTime", result.get(i).getStartTime().toString());
                innerObject.addProperty("Duration", result.get(i).getDuration());
                scheduleObject.add("" + i, innerObject);
            }
            json.add("Matches", scheduleObject);
        }

        return json.toString();
    }
}