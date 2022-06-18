package apploads.com.innocentminds.events;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chchidiac on 4/3/19.
 */

public class EventResponse {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("events")
    @Expose
    private List<Event> events = null;
    @SerializedName("food_calendar")
    @Expose
    private List<Food> foodCalendar = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Food> getFoodCalendar() {
        return foodCalendar;
    }

    public void setFoodCalendar(List<Food> foodCalendar) {
        this.foodCalendar = foodCalendar;
    }
}
