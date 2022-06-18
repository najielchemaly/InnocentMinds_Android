package apploads.com.innocentminds.databaseobjects.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildTemperature {
    @SerializedName("child_id")
    @Expose
    private Integer child_id;

    @SerializedName("temperature_id")
    @Expose
    private String temperature_id;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("comment")
    @Expose
    private String comment;

    public Integer getChild_id() {
        return child_id;
    }

    public void setChild_id(Integer child_id) {
        this.child_id = child_id;
    }

    public String getTemperature_id() {
        return temperature_id;
    }

    public void setTemperature_id(String temperature_id) {
        this.temperature_id = temperature_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
