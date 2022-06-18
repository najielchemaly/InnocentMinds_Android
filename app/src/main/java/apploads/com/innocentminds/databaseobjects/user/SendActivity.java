package apploads.com.innocentminds.databaseobjects.user;

import java.util.List;

public class SendActivity {
    private Integer child_id;
    private Integer user_id;
    private List<ChildActivity> child_activities;

    public Integer getChild_id() {
        return child_id;
    }

    public void setChild_id(Integer child_id) {
        this.child_id = child_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public List<ChildActivity> getChild_activities() {
        return child_activities;
    }

    public void setChild_activities(List<ChildActivity> child_activities) {
        this.child_activities = child_activities;
    }

    public SendActivity(Integer childId, Integer userId, List<ChildActivity> activities) {
        this.child_id = childId;
        this.user_id = userId;
        this.child_activities = activities;
    }
}
