package apploads.com.innocentminds.databaseobjects.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import apploads.com.innocentminds.databaseobjects.base.BaseResponse;

public class NotificationResponse extends BaseResponse {
    @SerializedName("notifications")
    @Expose
    private List<Notification> notifications;

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
