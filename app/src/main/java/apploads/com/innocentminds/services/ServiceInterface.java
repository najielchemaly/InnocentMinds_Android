package apploads.com.innocentminds.services;

import java.util.List;

import apploads.com.innocentminds.aboutus.AboutUsResponse;
import apploads.com.innocentminds.databaseobjects.base.BaseResponse;
import apploads.com.innocentminds.databaseobjects.globalvariables.Variables;
import apploads.com.innocentminds.databaseobjects.notification.NotificationResponse;
import apploads.com.innocentminds.databaseobjects.payment.StatementOfAccount;
import apploads.com.innocentminds.databaseobjects.user.Child;
import apploads.com.innocentminds.databaseobjects.user.ChildActivity;
import apploads.com.innocentminds.databaseobjects.user.PublishActivityId;
import apploads.com.innocentminds.databaseobjects.user.SendActivity;
import apploads.com.innocentminds.databaseobjects.user.SendTemperature;
import apploads.com.innocentminds.databaseobjects.user.UserResponse;
import apploads.com.innocentminds.events.EventResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceInterface {

    @POST("getGlobalVariables/")
    Call<Variables> getGlobalVariables();

    @FormUrlEncoded
    @POST("registerChild/")
    Call<BaseResponse> registerChild(@Field("child_firstname") String child_firstname,
                                     @Field("child_lastname") String child_lastname,
                                     @Field("child_dob") String child_dob,
                                     @Field("branch") String branch,
                                     @Field("desired_date") String desired_date,
                                     @Field("fullname") String fullname,
                                     @Field("phone") String phone,
                                     @Field("email") String email,
                                     @Field("address") String address,
                                     @Field("hear_about_us") String hear_about_us,
                                     @Field("parent_type") String parent_type,
                                     @Field("is_request") Boolean is_request,
                                     @Field("role_id") Integer role_id,
                                     @Field("lang") String lang);


    @FormUrlEncoded
    @POST("login/")
    Call<UserResponse> login(@Field("email") String email,
                             @Field("password") String password);

    @FormUrlEncoded
    @POST("loginToken/")
    Call<UserResponse> loginToken(@Field("access_token") String token);

    @FormUrlEncoded
    @POST("changePassword/")
    Call<BaseResponse> changePassowrd(@Field("user_id") String user_id,
                                      @Field("old_password") String old_password,
                                      @Field("new_password") String new_password);

    @FormUrlEncoded
    @POST("editProfile/")
    Call<BaseResponse> editProfile(@Field("id") String id,
                                   @Field("fullname") String fullname,
                                   @Field("phone") String phone,
                                   @Field("email") String email,
                                   @Field("address") String address);

//    @FormUrlEncoded
    @POST("editChild/")
    Call<BaseResponse> editChild(@Body Child child);

    @FormUrlEncoded
    @POST("logout/")
    Call<BaseResponse> logout(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("getText/")
    Call<BaseResponse> getText(@Field("user_id") String user_id,
                              @Field("query") String query);

    @FormUrlEncoded
    @POST("forgotPassword/")
    Call<BaseResponse> forgotPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("updateToken/")
    Call<BaseResponse> updateToken(@Field("user_id") String user_id,
                             @Field("firebase_token") String firebase_token);

    @FormUrlEncoded
    @POST("getNotifications/")
    Call<NotificationResponse> getNotifications(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("updateNotification/")
    Call<BaseResponse> updateNotification(@Field("user_id") String user_id,
                                          @Field("notif_id") String notif_id,
                                          @Field("is_read") Boolean is_read,
                                          @Field("is_deleted") Boolean is_deleted);

    @FormUrlEncoded
    @POST("getStatementOfAccount/")
    Call<StatementOfAccount> getStatementOfAccount(@Field("user_id") String user_id,
                                                   @Field("erp_id") String erp_id,
                                                   @Field("lang") String lang);

    @FormUrlEncoded
    @POST("sendContactUs/")
    Call<BaseResponse> sendContactUs(@Field("first_name") String first_name,
                                     @Field("last_name") String last_name,
                                     @Field("email") String email,
                                     @Field("phone") String phone,
                                     @Field("branch_id") String branch_id,
                                     @Field("inquiry") String inquiry);

    @FormUrlEncoded
    @POST("updateStudentStatus/")
    Call<BaseResponse> updateStudentStatus(@Field("child_id") String child_id,
                                           @Field("has_arrived") Boolean has_arrived,
                                           @Field("date_arrived") String date_arrived);

//    @FormUrlEncoded
    @POST("addTemperature/")
    Call<BaseResponse> addTemperature(@Body SendTemperature sendTemperature);

//    @FormUrlEncoded
    @POST("addActivity/")
    Call<BaseResponse> addActivity(@Body SendActivity sendActivity);

//    @FormUrlEncoded
    @POST("publishActivity/")
    Call<BaseResponse> publishActivity(@Body PublishActivityId publishActivityId);

    @FormUrlEncoded
    @POST("deleteActivity/")
    Call<BaseResponse> deleteActivity(@Field("id") String id);

    @FormUrlEncoded
    @POST("sendMessage/")
    Call<BaseResponse> sendMessage(@Field("user_id") String user_id,
                                   @Field("child_id") String child_id,
                                   @Field("message_ids") String message_ids);

    @FormUrlEncoded
    @POST("getActivities/")
    Call<List<ChildActivity>> getActivities(@Field("child_id") String child_id);

    @FormUrlEncoded
    @POST("getParentChildren/")
    Call<UserResponse> getParentChildren(@Field("child_id") String child_id);

    @Multipart
    @POST("uploadImage/")
    Call<Object> uploadImage(@Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("getEvents/")
    Call<EventResponse> getCalendar(@Field("userId") String user_id,
                                   @Field("lang") String lang);

    @FormUrlEncoded
    @POST("getFoodCalendar/")
    Call<EventResponse> getFoodCalendar(@Field("userId") String user_id,
                                        @Field("lang") String lang);

    @FormUrlEncoded
    @POST("getAboutUs/")
    Call<AboutUsResponse> getAboutUs(@Field("lang") String lang);
}
