package apploads.com.innocentminds.helpers.utils;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import org.joda.time.DateTime;
import org.joda.time.Weeks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.base.BaseResponse;
import apploads.com.innocentminds.databaseobjects.user.User;
import apploads.com.innocentminds.helpers.CustomDialogClass;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.nurse.ClassesActivity;
import apploads.com.innocentminds.objects.Payment;
import apploads.com.innocentminds.pickuser.PickUserActivity;
import apploads.com.innocentminds.services.ApiManager;
import apploads.com.innocentminds.signup.SignupChildActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public final class AppUtils {

    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_USER = "user";
    public static final String PREFS_LANG = "lang";
    public static final String PREFS_FIRST_LAUNCH = "first_launch";

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void setLocale(Context activity, String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = activity.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        saveLang(activity,lang);
    }

    public static void saveLang(Context context, String lang) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();

        editor.putString(PREFS_LANG, lang);
        editor.apply();
    }

    public static String getLang(Context context) {
        SharedPreferences shared = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return  (shared.getString(PREFS_LANG, ""));
    }

    public static Boolean isEn(Context context) {
        return "en".equals(AppUtils.getLang(context));
    }

    public static void setAppFirstLaunched(Context context, Boolean isLaunched) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();

        editor.putBoolean(PREFS_FIRST_LAUNCH, true);
        editor.apply();
    }

    public static boolean isAppLaunched(Context context) {
        SharedPreferences shared = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return  (shared.getBoolean(PREFS_FIRST_LAUNCH, false));
    }

    /**
     * for showing a native alertview with custom title and message
     *
//     * @param context do not user getapplication context , user ActivityName.this
//     * @param title   title of the dialogue
//     * @param message message of the dialogue
     */

    public static void showAlert(Context context, String message) {
        showAlert(context, "FOOTWIN", message);
    }

    public static void showAlert(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static void saveUser(Context context, User user) {
        StaticData.user = user;

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(StaticData.PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(StaticData.PREFS_USER, json);
        editor.apply();
    }

    public static User getUser(Context context) {
        SharedPreferences settings;
        String json;
        settings = context.getSharedPreferences(StaticData.PREFS_NAME, MODE_PRIVATE); //1

        Gson gson = new Gson();
        json = settings.getString(StaticData.PREFS_USER, "");
        User user = gson.fromJson(json, User.class);
        return user;
    }

    public static void removeUser(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(StaticData.PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();
        editor.remove(StaticData.PREFS_USER);
        editor.apply();
    }

    public static int getBadge(Context context) {
        SharedPreferences settings;
        settings = context.getSharedPreferences(StaticData.PREFS_NAME, MODE_PRIVATE); //1

        int badge;
        badge = settings.getInt(StaticData.PREFS_BADGE, 0);
        return badge;
    }

    public static void updateBadge(Context context, int badge) {

        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(StaticData.PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();

        editor.putInt(StaticData.PREFS_BADGE, badge);
        editor.apply();
    }

    public static void setFirstLaunch(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(StaticData.PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean("firstLaunch", true);
        editor.apply();
    }

    public static boolean isFirstLaunch(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(StaticData.PREFS_NAME, MODE_PRIVATE);
        boolean isFirstLaunch = prefs.getBoolean("firstLaunch", false);
        return isFirstLaunch;
    }

    public static void startCountAnimation(final TextView textView, int fromNumber, int toNumber, long duration) {
        ValueAnimator animator = ValueAnimator.ofInt(fromNumber, toNumber);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }

    public static Typeface getRegularTypeface(Context context){
        Typeface regFont = Typeface.createFromAsset(context.getAssets(),  "fonts/title_regular.ttf");
        return regFont;
    }

    public static Typeface getBoldTypeface(Context context){
        Typeface regFont = Typeface.createFromAsset(context.getAssets(),  "fonts/title_bold.ttf");
        return regFont;
    }

    public static Typeface getLightTypeface(Context context){
        Typeface regFont = Typeface.createFromAsset(context.getAssets(),  "fonts/light.ttf");
        return regFont;
    }

    public static Typeface getTitleBoldFont(Context context){
        Typeface regFont = Typeface.createFromAsset(context.getAssets(),  "fonts/bold.ttf");
        return regFont;
    }

    public static Typeface getTitleRegularFont(Context context){
        Typeface regFont = Typeface.createFromAsset(context.getAssets(),  "fonts/regular.ttf");
        return regFont;
    }

    public static void showAlertWithMessage(Activity activity, String title, String desc) {
        showAlertWithMessage(activity, title, desc, false);
    }

    public static void showAlertWithMessage(final Activity activity, String title, String desc, final Boolean shouldFinish) {
        CustomDialogClass dialogClass = new CustomDialogClass(activity, new CustomDialogClass.AbstractCustomDialogListener() {
            @Override
            public void onConfirm(CustomDialogClass.DialogResponse response) {
                response.getDialog().dismiss();

                if (shouldFinish) {
                    activity.finish();
                }
            }

            @Override
            public void onCancel(CustomDialogClass.DialogResponse dialogResponse) {
            }
        }, true);
        dialogClass.setTitle(title);
        dialogClass.setMessage(desc);
        dialogClass.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogClass.show();
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getTimeAgo(long timestamp) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();//get your local time zone.
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        sdf.setTimeZone(tz);//set time zone.
        String localTime = sdf.format(new Date(timestamp));
        Date date = new Date();
        try {
            date = sdf.parse(localTime);//get local date
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(date == null) {
            return null;
        }

        long time = date.getTime();

        Date curDate = currentDate();
        long now = curDate.getTime();
        if (time > now || time <= 0) {
            return null;
        }

        int timeDIM = getTimeDistanceInMinutes(time);

        String timeAgo = null;

        if (timeDIM == 0) {
            timeAgo = "less than a minute";
        } else if (timeDIM == 1) {
            return "1 minute";
        } else if (timeDIM >= 2 && timeDIM <= 44) {
            timeAgo = timeDIM + " minutes";
        } else if (timeDIM >= 45 && timeDIM <= 89) {
            timeAgo = "about an hour";
        } else if (timeDIM >= 90 && timeDIM <= 1439) {
            timeAgo = "about " + (Math.round(timeDIM / 60)) + " hours";
        } else if (timeDIM >= 1440 && timeDIM <= 2519) {
            timeAgo = "1 day";
        } else if (timeDIM >= 2520 && timeDIM <= 43199) {
            timeAgo = (Math.round(timeDIM / 1440)) + " days";
        } else if (timeDIM >= 43200 && timeDIM <= 86399) {
            timeAgo = "about a month";
        } else if (timeDIM >= 86400 && timeDIM <= 525599) {
            timeAgo = (Math.round(timeDIM / 43200)) + " months";
        } else if (timeDIM >= 525600 && timeDIM <= 655199) {
            timeAgo = "about a year";
        } else if (timeDIM >= 655200 && timeDIM <= 914399) {
            timeAgo = "over a year";
        } else if (timeDIM >= 914400 && timeDIM <= 1051199) {
            timeAgo = "almost 2 years";
        } else {
            timeAgo = "about " + (Math.round(timeDIM / 525600)) + " years";
        }

        return timeAgo + " ago";
    }

    public static Date currentDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    private static int getTimeDistanceInMinutes(long time) {
        long timeDistance = currentDate().getTime() - time;
        return Math.round((Math.abs(timeDistance) / 1000) / 60);
    }

    public static void showLogoutAlert(final Activity activity, String message) {
        CustomDialogClass dialogClass = new CustomDialogClass(activity, new CustomDialogClass.AbstractCustomDialogListener() {
            @Override
            public void onConfirm(CustomDialogClass.DialogResponse response) {
                Intent intent = new Intent(activity, PickUserActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
                activity.finish();
                response.getDialog().dismiss();
                AppUtils.removeUser(activity);

                String userId = StaticData.user.getId() == null ? "0" : StaticData.user.getId().toString();

                ApiManager.getService(true).logout(userId).enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onCancel(CustomDialogClass.DialogResponse dialogResponse) {
                dialogResponse.getDialog().dismiss();
            }
        }, false, activity.getResources().getString(R.string.yes), activity.getResources().getString(R.string.cancel));

        dialogClass.setTitle(activity.getResources().getString(R.string.logout));
        dialogClass.setMessage(message);
        dialogClass.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogClass.show();
    }

    public static int getWeeksOfMonth() {
        try {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.DATE,1);
            DateTime startDateTime = new DateTime(c.getTime());
            int lastDay = c.getActualMaximum(Calendar.DATE);
            c.set(Calendar.DATE,lastDay);
            DateTime lastDateTime = new DateTime(c.getTime());
            return Weeks.weeksBetween(startDateTime, lastDateTime).getWeeks() + 1;
        }catch (Exception e){
            Log.e("Error calculating weeks", e.getMessage());
        }
        return 0;
    }

    public static boolean isWeekDate(int week , Date date){

        Calendar passedDate = Calendar.getInstance();
        passedDate.setTime(date);
        passedDate.set(Calendar.MINUTE,0);
        passedDate.set(Calendar.HOUR,0);
        passedDate.set(Calendar.SECOND,0);
        passedDate.set(Calendar.MILLISECOND,0);

        Date d = passedDate.getTime();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.HOUR,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);

        c.set(Calendar.WEEK_OF_MONTH,week);
        c.set(Calendar.DAY_OF_WEEK,2);
        Date firstDayOfWeek = c.getTime();
        c.set(Calendar.DAY_OF_WEEK,7);
        Date lastDayOfWeek = c.getTime();

        return (firstDayOfWeek.equals(d) || d.after(firstDayOfWeek)) && (lastDayOfWeek.equals(d) || d.before(lastDayOfWeek));
    }

    public static int getWeek(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_MONTH);
    }
}
