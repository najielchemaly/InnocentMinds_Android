package apploads.com.innocentminds.databaseobjects.globalvariables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class About {
    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("director_note")
    @Expose
    private String director_note;

    @SerializedName("our_mission")
    @Expose
    private String our_mission;

    @SerializedName("our_staff")
    @Expose
    private String our_staff;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDirector_note() {
        return director_note;
    }

    public void setDirector_note(String director_note) {
        this.director_note = director_note;
    }

    public String getOur_mission() {
        return our_mission;
    }

    public void setOur_mission(String our_mission) {
        this.our_mission = our_mission;
    }

    public String getOur_staff() {
        return our_staff;
    }

    public void setOur_staff(String our_staff) {
        this.our_staff = our_staff;
    }
}

