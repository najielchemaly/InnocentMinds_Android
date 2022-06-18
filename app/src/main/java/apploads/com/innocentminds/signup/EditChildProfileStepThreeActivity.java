package apploads.com.innocentminds.signup;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.helpers.BaseActivity;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.homepage.HomepageActivity;

public class EditChildProfileStepThreeActivity extends BaseActivity {

    TextView lblPediatrician;
    Button btnNext, btnCancel, btnBack;
    EditText txtFullName, txtWorkplace, txtPhone, txtEmail;

    @Override
    public int getContentViewId() {
        return R.layout.edit_child_profile_step_three;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView(){
        lblPediatrician = _findViewById(R.id.lblPediatrician);
        btnNext = _findViewById(R.id.btnNext);
        btnCancel = _findViewById(R.id.btnCancel);
        btnBack = _findViewById(R.id.btnBack);
        txtFullName = _findViewById(R.id.txtFullName);
        txtWorkplace = _findViewById(R.id.txtWorkplace);
        txtPhone = _findViewById(R.id.txtPhone);
        txtEmail = _findViewById(R.id.txtEmail);

        setupChildInfo();
    }

    private void styleView(){
        lblPediatrician.setTypeface(AppUtils.getTitleRegularFont(this));
        btnNext.setTypeface(AppUtils.getRegularTypeface(this));
        btnCancel.setTypeface(AppUtils.getRegularTypeface(this));
        btnCancel.setTypeface(AppUtils.getRegularTypeface(this));
        btnBack.setTypeface(AppUtils.getRegularTypeface(this));
        txtFullName.setTypeface(AppUtils.getRegularTypeface(this));
        txtWorkplace.setTypeface(AppUtils.getRegularTypeface(this));
        txtPhone.setTypeface(AppUtils.getRegularTypeface(this));
        txtEmail.setTypeface(AppUtils.getRegularTypeface(this));
    }

    private void initListeners(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTempChildInfo();

                Intent intent = new Intent(getApplicationContext(), EditChildProfileStepFourActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.shouldFinish = true;
                finish();
            }
        });
    }

    void setupChildInfo() {
        txtFullName.setText(StaticData.selectedChild.getPed_fullname());
        txtWorkplace.setText(StaticData.selectedChild.getPed_workplace());
        txtPhone.setText(StaticData.selectedChild.getPed_phone());
        txtEmail.setText(StaticData.selectedChild.getPed_email());
    }

    void updateTempChildInfo() {
        StaticData.tempChild.setPed_fullname(txtFullName.getText() == null ? "" : txtFullName.getText().toString());
        StaticData.tempChild.setPed_workplace(txtWorkplace.getText() == null ? "" : txtWorkplace.getText().toString());
        StaticData.tempChild.setPed_phone(txtPhone.getText() == null ? "" : txtPhone.getText().toString());
        StaticData.tempChild.setPed_email(txtEmail.getText() == null ? "" : txtEmail.getText().toString());
    }
}
