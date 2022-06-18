package apploads.com.innocentminds.signin;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import apploads.com.innocentminds.databaseobjects.base.BaseResponse;
import apploads.com.innocentminds.databaseobjects.user.UserResponse;
import apploads.com.innocentminds.helpers.LoadingIndicator;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.nurse.ClassesActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.services.ApiManager;
import apploads.com.innocentminds.teacher.TeacherActivity;
import apploads.com.innocentminds.helpers.BaseActivity;
import apploads.com.innocentminds.helpers.CustomDialogClass;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.helpers.utils.StringUtils;
import apploads.com.innocentminds.homepage.HomepageActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends BaseActivity {

    TextView lblSignin, txtForgotPassword, lblLoginUsing, lblForgotPass;
    EditText txtUsername, txtPassword, txtEmail;
    Button btnSignin, btnCancel, btnSubmit, btnCancelLogin;
    ImageButton btnFacebook, btnGoogle;
    RelativeLayout viewForgotPass;
    LoadingIndicator indicatorView;

    @Override
    public int getContentViewId() {
        return R.layout.signin_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView() {
        lblSignin = _findViewById(R.id.lblSignin);
        txtForgotPassword = _findViewById(R.id.txtForgotPassword);
        lblLoginUsing = _findViewById(R.id.lblLoginUsing);
        txtUsername = _findViewById(R.id.txtUsername);
        txtPassword = _findViewById(R.id.txtPassword);
        btnSignin = _findViewById(R.id.btnSignin);
        btnFacebook = _findViewById(R.id.btnFacebook);
        btnGoogle = _findViewById(R.id.btnGoogle);
        btnCancel = _findViewById(R.id.btnCancel);
        btnCancelLogin = _findViewById(R.id.btnCancelLogin);
        txtEmail = _findViewById(R.id.txtEmail);
        lblForgotPass = _findViewById(R.id.lblForgotPass);
        btnSubmit = _findViewById(R.id.btnSubmit);
        viewForgotPass = _findViewById(R.id.viewForgotPass);
        indicatorView = _findViewById(R.id.indicatorView);
    }

    private void styleView() {
        lblSignin.setTypeface(AppUtils.getTitleRegularFont(this));
        txtForgotPassword.setTypeface(AppUtils.getRegularTypeface(this));
        lblLoginUsing.setTypeface(AppUtils.getRegularTypeface(this));
        txtUsername.setTypeface(AppUtils.getBoldTypeface(this));
        txtPassword.setTypeface(AppUtils.getBoldTypeface(this));
        btnSignin.setTypeface(AppUtils.getBoldTypeface(this));
        btnCancel.setTypeface(AppUtils.getRegularTypeface(this));
        txtEmail.setTypeface(AppUtils.getRegularTypeface(this));
        lblForgotPass.setTypeface(AppUtils.getRegularTypeface(this));
        btnSubmit.setTypeface(AppUtils.getBoldTypeface(this));
        btnCancelLogin.setTypeface(AppUtils.getBoldTypeface(this));
    }

    private void initListeners() {
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateSignin()) {
                    indicatorView.show();
                    btnCancelLogin.setVisibility(View.INVISIBLE);

                    ApiManager.getService(true).login(txtUsername.getText().toString(), txtPassword.getText().toString()).enqueue(new Callback<UserResponse>() {
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

                                AppUtils.saveUser(SigninActivity.this, StaticData.user);

                                finish();
                            } else if (StringUtils.isValid(response.body().getMessage())) {
                                AppUtils.showAlertWithMessage(SigninActivity.this, "Innocent Minds", response.body().getMessage());
                            } else {
                                AppUtils.showAlertWithMessage(SigninActivity.this, "Innocent Minds", getResources().getString(R.string.an_error_occured));
                            }

                            indicatorView.hide();
                            btnCancelLogin.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            indicatorView.hide();
                            btnCancelLogin.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewForgotPass.setVisibility(View.VISIBLE);
                btnCancelLogin.setVisibility(View.GONE);
//                view.animate().alpha(1f).setDuration(2000);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewForgotPass.setVisibility(View.GONE);
                btnCancelLogin.setVisibility(View.VISIBLE);
//                view.animate().alpha(0f).setDuration(2000);
            }
        });

        btnCancelLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (StringUtils.isValid(txtEmail.getText().toString())) {
                    indicatorView.show();

                    ApiManager.getService(true).forgotPassword(txtEmail.getText().toString()).enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            if (response.body() != null && response.body().getMessage() != null) {
                                AppUtils.showAlertWithMessage(SigninActivity.this, "Innocent Minds", response.body().getMessage());
                            }

                            indicatorView.hide();
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            indicatorView.hide();
                        }
                    });
                } else {
                    AppUtils.showAlertWithMessage(SigninActivity.this,
                            "Innocent Minds", getResources().getString(R.string.email_empty_error));
                }
            }
        });
    }

    private boolean validateSignin() {
        if (!StringUtils.isValid(txtUsername.getText())) {
            AppUtils.showAlertWithMessage(SigninActivity.this,
                    "Innocent Minds", getResources().getString(R.string.username_empty_error));
            return false;
        } else if (!StringUtils.isValid(txtPassword.getText())) {
            AppUtils.showAlertWithMessage(SigninActivity.this,
                    "Innocent Minds", getResources().getString(R.string.password_empty_error));
            return false;
        }

        return true;
    }
}
