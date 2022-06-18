package apploads.com.innocentminds.helpers;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.databaseobjects.globalvariables.Variables;
import apploads.com.innocentminds.databaseobjects.user.Child;
import apploads.com.innocentminds.databaseobjects.user.ChildActivity;
import apploads.com.innocentminds.databaseobjects.user.User;
import apploads.com.innocentminds.databaseobjects.user.UserClass;

public class StaticData {

    public static final String PREFS_NAME = "INNOCENTMINDS_PREFS";
    public static final String PREFS_USER = "user";
    public static final String PREFS_BADGE = "badge";
    public static final String PREFS_LANG = "lang";

    public static final String BREAKFAST = "1";
    public static final String LUNCH = "2";
    public static final String NAP = "3";
    public static final String BATHROOM = "4";
    public static final String POTTY = "5";
    public static final String ADDITIONAL = "6";

    public static final String PARENT = "1";
    public static final String TEACHER = "2";
    public static final String TEACHER_SUPERVISOR = "3";
    public static final String NURSE = "4";
    public static final String SECRETARY = "5";

    public static boolean shouldFinish = false;

    public static Context context;
//    public static int badge;
    public static User user = new User();
    public static Child tempChild = new Child();
    public static Child selectedChild = new Child();
    public static UserClass selectedClass = new UserClass();
    public static Variables variables = new Variables();
    public static List<ChildActivity> additionalActivities = new ArrayList<>();
    public static List<ChildActivity> dailyAgendas = new ArrayList<>();
    public static ChildActivity selectedDailyAgenda = new ChildActivity();
}
