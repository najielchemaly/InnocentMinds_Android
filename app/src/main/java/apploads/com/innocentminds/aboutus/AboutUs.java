package apploads.com.innocentminds.aboutus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chchidiac on 4/3/19.
 */

public class AboutUs {
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("director_note")
    @Expose
    private String directorNote;
    @SerializedName("our_mission")
    @Expose
    private String ourMission;
    @SerializedName("our_staff")
    @Expose
    private String ourStaff;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDirectorNote() {
        return directorNote;
    }

    public void setDirectorNote(String directorNote) {
        this.directorNote = directorNote;
    }

    public String getOurMission() {
        return ourMission;
    }

    public void setOurMission(String ourMission) {
        this.ourMission = ourMission;
    }

    public String getOurStaff() {
        return ourStaff;
    }

    public void setOurStaff(String ourStaff) {
        this.ourStaff = ourStaff;
    }
}
