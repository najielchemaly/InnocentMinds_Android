package apploads.com.innocentminds.signup;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.helpers.BaseActivity;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.homepage.HomepageActivity;

public class EditChildProfileStepTwoActivity extends BaseActivity {

    TextView lblSchoolBus;
    Button btnNext, btnCancel, btnBack;
    EditText txtFullname, txtPhoneNumber, txtLastname;
    LinearLayout focus;

    @Override
    public int getContentViewId() {
        return R.layout.edit_child_profile_step_two;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView(){
        lblSchoolBus = _findViewById(R.id.lblSchoolBus);
        btnNext = _findViewById(R.id.btnNext);
        btnCancel = _findViewById(R.id.btnCancel);
        btnBack = _findViewById(R.id.btnBack);
        txtFullname = _findViewById(R.id.txtFullname);
        txtPhoneNumber = _findViewById(R.id.txtPhoneNumber);
        txtLastname = _findViewById(R.id.txtLastname);
        focus = _findViewById(R.id.focus);
    }


    private void styleView(){
        lblSchoolBus.setTypeface(AppUtils.getTitleRegularFont(this));
        btnNext.setTypeface(AppUtils.getRegularTypeface(this));
        btnCancel.setTypeface(AppUtils.getRegularTypeface(this));
        btnCancel.setTypeface(AppUtils.getRegularTypeface(this));
        btnBack.setTypeface(AppUtils.getRegularTypeface(this));
        txtFullname.setTypeface(AppUtils.getRegularTypeface(this));
        txtPhoneNumber.setTypeface(AppUtils.getRegularTypeface(this));
        txtLastname.setTypeface(AppUtils.getRegularTypeface(this));
    }

    private void initListeners(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditChildProfileStepThreeActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                intent.putExtra("showMenu", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
}
