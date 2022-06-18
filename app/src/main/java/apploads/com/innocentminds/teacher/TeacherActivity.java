package apploads.com.innocentminds.teacher;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.user.Child;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherActivity extends BaseActivity {

    ListView lstChildren;
    Button btnConfirm, btnAdditionalActivities;
    ImageButton btnLogout;
    TextView lblName, lblAssignTo;
    ChildTeacherAdapter childTeacherAdapter;

    List<Child> children = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.teacher_main_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView(){
        lstChildren = _findViewById(R.id.lstChildren);
        btnConfirm = _findViewById(R.id.btnConfirm);
        btnAdditionalActivities = _findViewById(R.id.btnAdditionalActivities);
        btnLogout = _findViewById(R.id.btnLogout);
        lblName = _findViewById(R.id.lblName);
        lblAssignTo = _findViewById(R.id.lblAssignTo);

        btnConfirm.setText("Confirm & submit");

        if (StaticData.user.getChildren() != null) {
            children = StaticData.user.getChildren();
        }

        if (StaticData.user.getFullname() != null) {
            lblName.setText(StaticData.user.getFullname()+" (teacher)");
        }

        if (StaticData.user.getClasses() != null &&
                StaticData.user.getClasses().get(0) != null &&
                StaticData.user.getClasses().get(0).getName() != null) {
            lblAssignTo.setText("Assigned to\n"+StaticData.user.getClasses().get(0).getName());
        }

        if (StaticData.user.getRole_id() != null &&
                StaticData.user.getRole_id().toString().equals(StaticData.TEACHER)) {
            btnConfirm.setVisibility(View.INVISIBLE);
        } else {
            btnConfirm.setVisibility(children.size() == 0 ? View.INVISIBLE : View.VISIBLE);
        }

        childTeacherAdapter = new ChildTeacherAdapter(children, getApplicationContext());
        lstChildren.setAdapter(childTeacherAdapter);
    }

    private void styleView(){
        btnConfirm.setTypeface(AppUtils.getRegularTypeface(this));
        btnAdditionalActivities.setTypeface(AppUtils.getRegularTypeface(this));
        lblName.setTypeface(AppUtils.getRegularTypeface(this));
        lblAssignTo.setTypeface(AppUtils.getRegularTypeface(this));
    }

    private void initListeners(){
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.showLogoutAlert(TeacherActivity.this, getResources().getString(R.string.are_you_sure_logout));
            }
        });

        btnAdditionalActivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TeacherActivitiesActivity.class);
                startActivity(intent);
            }
        });
    }
}
