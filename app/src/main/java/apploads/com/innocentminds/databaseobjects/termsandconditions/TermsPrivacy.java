package apploads.com.innocentminds.databaseobjects.termsandconditions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import apploads.com.innocentminds.databaseobjects.base.BaseResponse;

public class TermsPrivacy extends BaseResponse {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("text")
    @Expose
    private String text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
