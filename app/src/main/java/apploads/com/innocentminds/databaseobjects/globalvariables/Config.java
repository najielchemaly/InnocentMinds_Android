package apploads.com.innocentminds.databaseobjects.globalvariables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Config {
    @SerializedName("version_ios")
    @Expose
    private String version_ios;

    @SerializedName("version_android")
    @Expose
    private String version_android;

    @SerializedName("version_outdate_message")
    @Expose
    private String version_outdate_message;

    @SerializedName("base_url")
    @Expose
    private String base_url;

    @SerializedName("media_url")
    @Expose
    private String media_url;

    @SerializedName("is_review")
    @Expose
    private Boolean is_review;

    @SerializedName("now")
    @Expose
    private String now;

    public String getVersion_ios() {
        return version_ios;
    }

    public void setVersion_ios(String version_ios) {
        this.version_ios = version_ios;
    }

    public String getVersion_android() {
        return version_android;
    }

    public void setVersion_android(String version_android) {
        this.version_android = version_android;
    }

    public String getVersion_outdate_message() {
        return version_outdate_message;
    }

    public void setVersion_outdate_message(String version_outdate_message) {
        this.version_outdate_message = version_outdate_message;
    }

    public String getBase_url() {
        return base_url;
    }

    public void setBase_url(String base_url) {
        this.base_url = base_url;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public Boolean getIs_review() {
        return is_review;
    }

    public void setIs_review(Boolean is_review) {
        this.is_review = is_review;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }
}
