package apploads.com.innocentminds.objects;

import java.util.List;

public class Activity {

    private String title;
    private String description;
    private String type;
    private int image;
    private int stars;
    private List<Child> selectedChilds;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public List<Child> getSelectedChilds() {
        return selectedChilds;
    }

    public void setSelectedChilds(List<Child> selectedChilds) {
        this.selectedChilds = selectedChilds;
    }
}
