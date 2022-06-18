package apploads.com.innocentminds.databaseobjects.globalvariables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Contact {
    @SerializedName("branch_id")
    @Expose
    private String branch_id;

    @SerializedName("location")
    @Expose
    private String location;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("phone_numbers")
    @Expose
    private List<PhoneNumber> phone_numbers;

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<PhoneNumber> getPhone_numbers() {
        return phone_numbers;
    }

    public void setPhone_numbers(List<PhoneNumber> phone_numbers) {
        this.phone_numbers = phone_numbers;
    }
}
