package apploads.com.innocentminds.databaseobjects.user;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Child {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("parent_id")
    @Expose
    private Integer parent_id;

    @SerializedName("is_born")
    @Expose
    private Boolean is_born;

    @SerializedName("firstname")
    @Expose
    private String firstname;

    @SerializedName("fathername")
    @Expose
    private String fathername;

    @SerializedName("lastname")
    @Expose
    private String lastname;

    @SerializedName("date_of_birth")
    @Expose
    private String date_of_birth;

    @SerializedName("desired_date_of_entry")
    @Expose
    private String desired_date_of_entry;

    @SerializedName("class_id")
    @Expose
    private Integer class_id;

    @SerializedName("gender_id")
    @Expose
    private String gender_id;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("place_of_birth")
    @Expose
    private String place_of_birth;

    @SerializedName("branch_id")
    @Expose
    private String branch_id;

    @SerializedName("ped_fullname")
    @Expose
    private String ped_fullname;

    @SerializedName("ped_workplace")
    @Expose
    private String ped_workplace;

    @SerializedName("ped_phone")
    @Expose
    private String ped_phone;

    @SerializedName("ped_email")
    @Expose
    private String ped_email;

    @SerializedName("transp_id")
    @Expose
    private String transp_id;

    @SerializedName("blood_type_id")
    @Expose
    private String blood_type_id;

    @SerializedName("allergy")
    @Expose
    private String allergy;

    @SerializedName("regular_medication")
    @Expose
    private String regular_medication;

    @SerializedName("disease_ids")
    @Expose
    private String disease_ids;

    @SerializedName("special_medical_conditions")
    @Expose
    private String special_medical_conditions;

    @SerializedName("sleep_habit_id")
    @Expose
    private String sleep_habit_id;

    @SerializedName("eating_habit_id")
    @Expose
    private String eating_habit_id;

    @SerializedName("clean")
    @Expose
    private Boolean clean;

    @SerializedName("pacifier")
    @Expose
    private String pacifier;

    @SerializedName("character_type_id")
    @Expose
    private String character_type_id;

    @SerializedName("home_language_id")
    @Expose
    private String home_language_id;

    @SerializedName("desired_langugage_id")
    @Expose
    private String desired_langugage_id;

    @SerializedName("activities")
    @Expose
    private List<ChildActivity> activities;

    @SerializedName("erp_id")
    @Expose
    private String erp_id;

    @SerializedName("lang")
    @Expose
    private String lang;

    private Bitmap _image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public Boolean getIs_born() {
        return is_born;
    }

    public void setIs_born(Boolean is_born) {
        this.is_born = is_born;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getDesired_date_of_entry() {
        return desired_date_of_entry;
    }

    public void setDesired_date_of_entry(String desired_date_of_entry) {
        this.desired_date_of_entry = desired_date_of_entry;
    }

    public Integer getClass_id() {
        return class_id;
    }

    public void setClass_id(Integer class_id) {
        this.class_id = class_id;
    }

    public String getGender_id() {
        return gender_id;
    }

    public void setGender_id(String gender_id) {
        this.gender_id = gender_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getBranch_id() {
        return branch_id;
    }

    public void setBranch_id(String branch_id) {
        this.branch_id = branch_id;
    }

    public String getPed_fullname() {
        return ped_fullname;
    }

    public void setPed_fullname(String ped_fullname) {
        this.ped_fullname = ped_fullname;
    }

    public String getPed_workplace() {
        return ped_workplace;
    }

    public void setPed_workplace(String ped_workplace) {
        this.ped_workplace = ped_workplace;
    }

    public String getPed_phone() {
        return ped_phone;
    }

    public void setPed_phone(String ped_phone) {
        this.ped_phone = ped_phone;
    }

    public String getPed_email() {
        return ped_email;
    }

    public void setPed_email(String ped_email) {
        this.ped_email = ped_email;
    }

    public String getTransp_id() {
        return transp_id;
    }

    public void setTransp_id(String transp_id) {
        this.transp_id = transp_id;
    }

    public String getBlood_type_id() {
        return blood_type_id;
    }

    public void setBlood_type_id(String blood_type_id) {
        this.blood_type_id = blood_type_id;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getRegular_medication() {
        return regular_medication;
    }

    public void setRegular_medication(String regular_medication) {
        this.regular_medication = regular_medication;
    }

    public String getDisease_ids() {
        return disease_ids;
    }

    public void setDisease_ids(String disease_ids) {
        this.disease_ids = disease_ids;
    }

    public String getSpecial_medical_conditions() {
        return special_medical_conditions;
    }

    public void setSpecial_medical_conditions(String special_medical_conditions) {
        this.special_medical_conditions = special_medical_conditions;
    }

    public String getSleep_habit_id() {
        return sleep_habit_id;
    }

    public void setSleep_habit_id(String sleep_habit_id) {
        this.sleep_habit_id = sleep_habit_id;
    }

    public String getEating_habit_id() {
        return eating_habit_id;
    }

    public void setEating_habit_id(String eating_habit_id) {
        this.eating_habit_id = eating_habit_id;
    }

    public Boolean getClean() {
        return clean == null ? false : clean;
    }

    public void setClean(Boolean clean) {
        this.clean = clean;
    }

    public String getPacifier() {
        return pacifier;
    }

    public void setPacifier(String pacifier) {
        this.pacifier = pacifier;
    }

    public String getCharacter_type_id() {
        return character_type_id;
    }

    public void setCharacter_type_id(String character_type_id) {
        this.character_type_id = character_type_id;
    }

    public String getHome_language_id() {
        return home_language_id;
    }

    public void setHome_language_id(String home_language_id) {
        this.home_language_id = home_language_id;
    }

    public String getDesired_langugage_id() {
        return desired_langugage_id;
    }

    public void setDesired_langugage_id(String desired_langugage_id) {
        this.desired_langugage_id = desired_langugage_id;
    }

    public List<ChildActivity> getActivities() {
        return activities;
    }

    public void setActivities(List<ChildActivity> activities) {
        this.activities = activities;
    }

    public Bitmap get_image() {
        return _image;
    }

    public void set_image(Bitmap _image) {
        this._image = _image;
    }

    public String getErp_id() {
        return erp_id;
    }

    public void setErp_id(String erp_id) {
        this.erp_id = erp_id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
