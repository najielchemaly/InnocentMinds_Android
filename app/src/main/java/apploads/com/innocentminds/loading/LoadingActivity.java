package apploads.com.innocentminds.loading;

import android.content.Intent;
import android.view.View;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.SelectLanguageActivity;
import apploads.com.innocentminds.databaseobjects.globalvariables.Variables;
import apploads.com.innocentminds.databaseobjects.user.User;
import apploads.com.innocentminds.databaseobjects.user.UserResponse;
import apploads.com.innocentminds.helpers.BaseActivity;
import apploads.com.innocentminds.helpers.LoadingIndicator;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.helpers.utils.StringUtils;
import apploads.com.innocentminds.homepage.HomepageActivity;
import apploads.com.innocentminds.nurse.ClassesActivity;
import apploads.com.innocentminds.pickuser.PickUserActivity;
import apploads.com.innocentminds.services.ApiManager;

import apploads.com.innocentminds.signin.SigninActivity;
import apploads.com.innocentminds.teacher.TeacherActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadingActivity extends BaseActivity{

    LoadingIndicator indicatorView;

    @Override
    public int getContentViewId() {
        return R.layout.loading_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        indicatorView = _findViewById(R.id.indicatorView);
        indicatorView.show();
        AppUtils.setAppFirstLaunched(getApplicationContext(),true);
        if("en".equals(AppUtils.getLang(getApplicationContext()))){
            AppUtils.setLocale(LoadingActivity.this, "en");
        }else {
            AppUtils.setLocale(LoadingActivity.this, "fr");
        }
        ApiManager.getService(true).getGlobalVariables().enqueue(new Callback<Variables>() {
            @Override
            public void onResponse(Call<Variables> call, Response<Variables> response) {
                if (response.body() != null) {
                    if (response.body().getStatus() == 1) {
                        StaticData.variables = response.body();
                        checkIfUserIsLoggedIn();
                    } else if (response.body().getMessage() != null) {
                        AppUtils.showAlertWithMessage(LoadingActivity.this, "Innocent Minds", response.body().getMessage());
                    } else {
                        AppUtils.showAlertWithMessage(LoadingActivity.this, "Innocent Minds", "An error has occured, please try again or contact support");
                    }
                }

                indicatorView.hide();
            }

            @Override
            public void onFailure(Call<Variables> call, Throwable t) {
                indicatorView.hide();
            }
        });

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //Do something after 100ms
//                Intent intent = new Intent(LoadingActivity.this, PickUserActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 2000);
    }

    void checkIfUserIsLoggedIn() {
        User user = AppUtils.getUser(LoadingActivity.this);
        if (user == null) {
            Intent intent = new Intent(LoadingActivity.this, PickUserActivity.class);
            startActivity(intent);
            finish();
        } else {
            StaticData.user = user;
            if (user.getId() != null && user.getEmail() != null) {
                String access_token = user.getId().toString()+user.getEmail();

                indicatorView.show();

                ApiManager.getService(true).loginToken(access_token).enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if (response.body() != null && response.body().getStatus() == 1) {
                            StaticData.user = response.body().getUser();
                            switch (StaticData.user.getRole_id()) {
                                case 1:
                                    Intent parentIntent = new Intent(getApplicationContext(), HomepageActivity.class);
                                    parentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(parentIntent);
                                    break;
                                case 2:
                                case 3:
                                    Intent teacherIntent = new Intent(getApplicationContext(), TeacherActivity.class);
                                    teacherIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(teacherIntent);
                                    break;
                                case 4:
                                    Intent nurseIntent = new Intent(getApplicationContext(), ClassesActivity.class);
                                    nurseIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(nurseIntent);
                                    break;
                                default:
                                    break;
                            }

                            AppUtils.saveUser(LoadingActivity.this, StaticData.user);

                            finish();
                        } else if (StringUtils.isValid(response.body().getMessage())) {
                            AppUtils.showAlertWithMessage(LoadingActivity.this, "Innocent Minds", response.body().getMessage());
                        } else {
                            AppUtils.showAlertWithMessage(LoadingActivity.this, "Innocent Minds", "An error has occured, please try again or contact support");
                        }

                        indicatorView.hide();
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {
                        indicatorView.hide();
                    }
                });
            }
        }
    }
}
