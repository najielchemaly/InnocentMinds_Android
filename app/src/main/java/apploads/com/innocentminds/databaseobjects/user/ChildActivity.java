package apploads.com.innocentminds.databaseobjects.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ChildActivity implements Serializable{
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("type_id")
    @Expose
    private String type_id;

    @SerializedName("child_id")
    @Expose
    private Integer child_id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("rating")
    @Expose
    private String rating;

    @SerializedName("student_ids")
    @Expose
    private String student_ids;

    @SerializedName("from_date")
    @Expose
    private String from_date;

    @SerializedName("to_date")
    @Expose
    private String to_date;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("bath_type_id")
    @Expose
    private String bath_type_id;

    @SerializedName("bath_potty_type_id")
    @Expose
    private String bath_potty_type_id;

    @SerializedName("photos")
    @Expose
    private List<Photo> photos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public Integer getChild_id() {
        return child_id;
    }

    public void setChild_id(Integer child_id) {
        this.child_id = child_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStudent_ids() {
        return student_ids;
    }

    public void setStudent_ids(String student_ids) {
        this.student_ids = student_ids;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBath_type_id() {
        return bath_type_id;
    }

    public void setBath_type_id(String bath_type_id) {
        this.bath_type_id = bath_type_id;
    }

    public String getBath_potty_type_id() {
        return bath_potty_type_id;
    }

    public void setBath_potty_type_id(String bath_potty_type_id) {
        this.bath_potty_type_id = bath_potty_type_id;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
