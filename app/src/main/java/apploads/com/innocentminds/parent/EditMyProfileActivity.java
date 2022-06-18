package apploads.com.innocentminds.parent;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.base.BaseResponse;
import apploads.com.innocentminds.helpers.LoadingIndicator;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.helpers.utils.StringUtils;
import apploads.com.innocentminds.services.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMyProfileActivity extends BaseActivity {

    Button btnCancel, btnSave;
    EditText txtFullname, txtPhone, txtEmail, txtAddress;
    TextView lblEditProfile;
    LoadingIndicator indicatorView;

    @Override
    public int getContentViewId() {
        return R.layout.edit_my_profile;
    }

    @Override
    public void doOnCreate() throws Exception {
        initViews();
        initListeners();
        setupUserInfo();
    }

    void initViews() {
        btnSave = _findViewById(R.id.btnSave);
        btnCancel = _findViewById(R.id.btnCancel);
        lblEditProfile = _findViewById(R.id.lblEditProfile);
        txtFullname = _findViewById(R.id.txtFullName);
        txtPhone = _findViewById(R.id.txtPhone);
        txtEmail = _findViewById(R.id.txtEmail);
        txtAddress = _findViewById(R.id.txtAddress);
        indicatorView = _findViewById(R.id.indicatorView);

        btnSave.setTypeface(AppUtils.getRegularTypeface(this));
        btnCancel.setTypeface(AppUtils.getRegularTypeface(this));
        lblEditProfile.setTypeface(AppUtils.getTitleRegularFont(this));
        txtFullname.setTypeface(AppUtils.getRegularTypeface(this));
        txtPhone.setTypeface(AppUtils.getRegularTypeface(this));
        txtEmail.setTypeface(AppUtils.getRegularTypeface(this));
        txtAddress.setTypeface(AppUtils.getRegularTypeface(this));
    }

    void initListeners() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateView()) {
                    indicatorView.show();

                    String userId = StaticData.user.getId() == null ? "0" : StaticData.user.getId().toString();

                    ApiManager.getService(true).editProfile(userId, txtFullname.getText().toString(), txtPhone.getText().toString(),
                            txtEmail.getText().toString(), txtAddress.getText().toString()).enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 1) {
                                    if (response.body().getMessage() != null) {
                                        AppUtils.showAlertWithMessage(EditMyProfileActivity.this, "Innocent Minds", response.body().getMessage());
                                    } else {
                                        AppUtils.showAlertWithMessage(EditMyProfileActivity.this, "Innocent Minds", "You profile has been updated successfully");
                                    }
                                } else if (response.body().getMessage() != null) {
                                    AppUtils.showAlertWithMessage(EditMyProfileActivity.this, "Innocent Minds", response.body().getMessage());
                                } else {
                                    AppUtils.showAlertWithMessage(EditMyProfileActivity.this, "Innocent Minds", "An error has occured, please try again or contact support");
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

    void setupUserInfo() {
        txtFullname.setText(StaticData.user.getFullname());
        txtPhone.setText(StaticData.user.getPhone());
        txtEmail.setText(StaticData.user.getEmail());
        txtAddress.setText(StaticData.user.getAddress());
    }

    private boolean validateView() {
        if (!StringUtils.isValid(txtFullname.getText())) {
            AppUtils.showAlertWithMessage(this, "Innocent Minds", "Fullname field cannot be empty");
            return false;
        } else if (!StringUtils.isValid(txtPhone.getText())) {
            AppUtils.showAlertWithMessage(this, "Innocent Minds", "Phone number field cannot be empty");
            return false;
        } else if (!StringUtils.isValid(txtEmail.getText())) {
            AppUtils.showAlertWithMessage(this, "Innocent Minds", "Email field cannot be empty");
            return false;
        } else if (!StringUtils.isValid(txtAddress.getText())) {
            AppUtils.showAlertWithMessage(this, "Innocent Minds", "Address field cannot be empty");
            return false;
        }
        return true;
    }
}
