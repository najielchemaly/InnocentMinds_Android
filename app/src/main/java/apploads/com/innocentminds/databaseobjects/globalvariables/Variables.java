package apploads.com.innocentminds.databaseobjects.globalvariables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import apploads.com.innocentminds.databaseobjects.base.BaseResponse;

public class Variables extends BaseResponse {
    @SerializedName("config")
    @Expose
    private Config config;

    @SerializedName("branches")
    @Expose
    private List<GlobalData> branches;

    @SerializedName("hear_about_us")
    @Expose
    private List<GlobalData> hear_about_us;

    @SerializedName("about")
    @Expose
    private About about;

    @SerializedName("contact")
    @Expose
    private List<Contact> contacts;

    @SerializedName("messages")
    @Expose
    private List<Messages> messages;

    @SerializedName("genders")
    @Expose
    private List<GlobalData> genders;

    @SerializedName("home_languages")
    @Expose
    private List<GlobalData> home_languages;

    @SerializedName("program_languages")
    @Expose
    private List<GlobalData> program_languages;

    @SerializedName("transportations")
    @Expose
    private List<GlobalData> transportations;

    @SerializedName("blood_types")
    @Expose
    private List<GlobalData> blood_types;

    @SerializedName("habit_ranks")
    @Expose
    private List<GlobalData> habit_ranks;

    @SerializedName("diseases")
    @Expose
    private List<GlobalData> diseases;

    @SerializedName("character_types")
    @Expose
    private List<GlobalData> character_types;

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public List<GlobalData> getBranches() {
        return branches;
    }

    public void setBranches(List<GlobalData> branches) {
        this.branches = branches;
    }

    public List<GlobalData> getHear_about_us() {
        return hear_about_us;
    }

    public void setHear_about_us(List<GlobalData> hear_about_us) {
        this.hear_about_us = hear_about_us;
    }

    public About getAbout() {
        return about;
    }

    public void setAbout(About about) {
        this.about = about;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public List<Messages> getMessages() {
        return messages;
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

    public List<GlobalData> getGenders() {
        return genders;
    }

    public void setGenders(List<GlobalData> genders) {
        this.genders = genders;
    }

    public List<GlobalData> getHome_languages() {
        return home_languages;
    }

    public void setHome_languages(List<GlobalData> home_languages) {
        this.home_languages = home_languages;
    }

    public List<GlobalData> getProgram_languages() {
        return program_languages;
    }

    public void setProgram_languages(List<GlobalData> program_languages) {
        this.program_languages = program_languages;
    }

    public List<GlobalData> getTransportations() {
        return transportations;
    }

    public void setTransportations(List<GlobalData> transportations) {
        this.transportations = transportations;
    }

    public List<GlobalData> getBlood_types() {
        return blood_types;
    }

    public void setBlood_types(List<GlobalData> blood_types) {
        this.blood_types = blood_types;
    }

    public List<GlobalData> getHabit_ranks() {
        return habit_ranks;
    }

    public void setHabit_ranks(List<GlobalData> habit_ranks) {
        this.habit_ranks = habit_ranks;
    }

    public List<GlobalData> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<GlobalData> diseases) {
        this.diseases = diseases;
    }

    public List<GlobalData> getCharacter_types() {
        return character_types;
    }

    public void setCharacter_types(List<GlobalData> character_types) {
        this.character_types = character_types;
    }
}
