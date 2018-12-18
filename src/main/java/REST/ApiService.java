package REST;

import com.google.gson.JsonObject;
import domain.Activity;
import domain.Frequency;
import domain.Schedule;
import domain.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Path("/newSchedule")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response newSchedule(@FormParam("schedulename") String schedulename, @FormParam("duration") int duration, @FormParam("activities") ArrayList<String> activitynames, @FormParam("categories") ArrayList<String> activitycategories){
        try {
            Service.context.newSchedule(schedulename, duration, activitynames, activitycategories);
        } catch (Exception e) {
            return Response.status(400).header("Access-Control-Allow-Origin", "*").build();
        }
        return Response.status(200).header("Access-Control-Allow-Origin", "*").build();
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