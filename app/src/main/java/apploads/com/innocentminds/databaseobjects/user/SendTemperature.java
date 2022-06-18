package apploads.com.innocentminds.databaseobjects.user;

import java.util.List;

public class SendTemperature {
    private Integer child_id;
    private List<ChildTemperature> child_temperatures;

    public Integer getChild_id() {
        return child_id;
    }

    public void setChild_id(Integer child_id) {
        this.child_id = child_id;
    }

    public List<ChildTemperature> getChild_temperatures() {
        return child_temperatures;
    }

    public void setChild_temperatures(List<ChildTemperature> child_temperatures) {
        this.child_temperatures = child_temperatures;
    }
}
