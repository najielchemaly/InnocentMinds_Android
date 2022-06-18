package apploads.com.innocentminds.parent;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.base.BaseResponse;
import apploads.com.innocentminds.helpers.CustomDialogClass;
import apploads.com.innocentminds.helpers.LoadingIndicator;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.helpers.utils.StringUtils;
import apploads.com.innocentminds.services.ApiManager;
import apploads.com.innocentminds.signin.SigninActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends BaseActivity {

    EditText txtOldPassword, txtNewPassword, txtRePassword;
    TextView txtPassword;
    Button btnCancel, btnSave;
    LoadingIndicator indicatorView;

    @Override
    public int getContentViewId() {
        return R.layout.change_password_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView() {
        btnCancel = _findViewById(R.id.btnCancel);
        txtPassword = _findViewById(R.id.txtPassword);
        txtOldPassword = _findViewById(R.id.txtOldPassword);
        txtNewPassword = _findViewById(R.id.txtNewPassword);
        txtRePassword = _findViewById(R.id.txtRePassword);
        btnSave = _findViewById(R.id.btnSave);
        indicatorView = _findViewById(R.id.indicatorView);
    }

    private void styleView() {
        btnCancel.setTypeface(AppUtils.getRegularTypeface(this));
        txtPassword.setTypeface(AppUtils.getTitleRegularFont(this));
        txtOldPassword.setTypeface(AppUtils.getRegularTypeface(this));
        txtNewPassword.setTypeface(AppUtils.getRegularTypeface(this));
        txtRePassword.setTypeface(AppUtils.getRegularTypeface(this));
    }

    private void initListeners() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateView()){
                    indicatorView.show();

                    String userId = StaticData.user.getId() == null ? "0" : StaticData.user.getId().toString();

                    ApiManager.getService(true).changePassowrd(userId, txtOldPassword.getText().toString(),
                            txtNewPassword.getText().toString()).enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 1) {
                                    if (response.body().getMessage() != null) {
                                        AppUtils.showAlertWithMessage(ChangePasswordActivity.this, "Innocent Minds", response.body().getMessage());
                                    } else {
                                        AppUtils.showAlertWithMessage(ChangePasswordActivity.this, "Innocent Minds", getResources().getString(R.string.password_changed_successfully));
                                    }
                                } else if (response.body().getMessage() != null) {
                                    AppUtils.showAlertWithMessage(ChangePasswordActivity.this, "Innocent Minds", response.body().getMessage());
                                } else {
                                    AppUtils.showAlertWithMessage(ChangePasswordActivity.this, "Innocent Minds", getResources().getString(R.string.an_error_occured));
                                }
                            }

                            indicatorView.hide();
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            indicatorView.hide();
                        }
                    });
                }
            }
        });
    }

    private boolean validateView() {
        if (StringUtils.isValid(txtOldPassword.getText())) {
            if (StringUtils.isValid(txtNewPassword.getText()) && StringUtils.isValid(txtNewPassword.getText())) {
                if(txtNewPassword.getText().toString().equals(txtRePassword.getText().toString())){
                    return true;
                }else {
                    showErrorDialogue("Your passwords do not match");
                    txtNewPassword.setText("");
                    txtRePassword.setText("");
                    return false;
                }
            }else {
                showErrorDialogue("Fill your new password to continue");
                return false;
            }
        } else {
            showErrorDialogue("Fill your old password to continue");
            return false;
        }
    }

    private void showErrorDialogue(String title){
        CustomDialogClass dialogClass = new CustomDialogClass(ChangePasswordActivity.this, new CustomDialogClass.AbstractCustomDialogListener() {
            @Override
            public void onConfirm(CustomDialogClass.DialogResponse response) {
                response.getDialog().dismiss();
            }

            @Override
            public void onCancel(CustomDialogClass.DialogResponse dialogResponse) {
            }
        }, true);

        dialogClass.setTitle("Oops!");
        dialogClass.setMessage(title);
        dialogClass.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogClass.show();
    }
}
