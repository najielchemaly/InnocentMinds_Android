package apploads.com.innocentminds.databaseobjects.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("role_id")
    @Expose
    private Integer role_id;

    @SerializedName("parent_type")
    @Expose
    private Integer parent_type;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("now")
    @Expose
    private String now;

    @SerializedName("hear_about_us")
    @Expose
    private String hear_about_us;

    @SerializedName("classes")
    @Expose
    private List<UserClass> classes;

    @SerializedName("children")
    @Expose
    private List<Child> children;

    @SerializedName("additional_activities")
    @Expose
    private List<ChildActivity> additional_activities;

    @SerializedName("firebase_token")
    @Expose
    private String firebase_token;

    @SerializedName("erp_id")
    @Expose
    private String erp_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public Integer getParent_type() {
        return parent_type;
    }

    public void setParent_type(Integer parent_type) {
        this.parent_type = parent_type;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getHear_about_us() {
        return hear_about_us;
    }

    public void setHear_about_us(String hear_about_us) {
        this.hear_about_us = hear_about_us;
    }

    public List<UserClass> getClasses() {
        return classes;
    }

    public void setClasses(List<UserClass> classes) {
        this.classes = classes;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public List<ChildActivity> getAdditional_activities() {
        return additional_activities;
    }

    public void setAdditional_activities(List<ChildActivity> additional_activities) {
        this.additional_activities = additional_activities;
    }

    public String getFirebase_token() {
        return firebase_token;
    }

    public void setFirebase_token(String firebase_token) {
        this.firebase_token = firebase_token;
    }

    public String getErp_id() {
        return erp_id;
    }

    public void setErp_id(String erp_id) {
        this.erp_id = erp_id;
    }
}
