package apploads.com.innocentminds.databaseobjects.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import apploads.com.innocentminds.databaseobjects.base.BaseResponse;

public class UserResponse extends BaseResponse {
    @SerializedName("user")
    @Expose
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
