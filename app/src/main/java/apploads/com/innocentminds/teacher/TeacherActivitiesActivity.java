package apploads.com.innocentminds.teacher;

import android.content.Intent;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;

public class TeacherActivitiesActivity extends BaseActivity {

    CardView viewNoActivity, viewAddActivity;
    ImageButton btnBack;
    TextView txtNoActivityTitle, txtNoActivityDesc;
    Button btnNoActivityAdd;
    ListView lstAgenda;
    LinearLayout viewNewActivity;
    TeacherActivityAdapter teacherActivityAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.teacher_activities_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        designView();
    }

    private void initView(){
        viewNoActivity = _findViewById(R.id.viewNoActivity);
        btnBack = _findViewById(R.id.btnBack);
        txtNoActivityTitle = _findViewById(R.id.txtNoActivityTitle);
        txtNoActivityDesc = _findViewById(R.id.txtNoActivityDesc);
        btnNoActivityAdd = _findViewById(R.id.btnNoActivityAdd);
        viewAddActivity = _findViewById(R.id.viewAddActivity);
        lstAgenda = _findViewById(R.id.lstAgenda);
        viewNewActivity = _findViewById(R.id.viewNewActivity);

        StaticData.additionalActivities = new ArrayList<>();
        if (StaticData.user.getAdditional_activities() != null) {
            StaticData.additionalActivities = StaticData.user.getAdditional_activities();
        }

        designView();
    }

    private void designView(){
        if(StaticData.additionalActivities != null && StaticData.additionalActivities.size() > 0){
            teacherActivityAdapter = new TeacherActivityAdapter(StaticData.additionalActivities, this);
            lstAgenda.setAdapter(teacherActivityAdapter);
            viewNoActivity.setVisibility(View.GONE);
            viewAddActivity.setVisibility(View.VISIBLE);
            lstAgenda.setVisibility(View.VISIBLE);
        }else {
            viewNoActivity.setVisibility(View.VISIBLE);
            lstAgenda.setVisibility(View.GONE);
            viewAddActivity.setVisibility(View.GONE);
        }
    }

    private void styleView(){
        txtNoActivityTitle.setTypeface(AppUtils.getBoldTypeface(this));
        txtNoActivityDesc.setTypeface(AppUtils.getRegularTypeface(this));
        btnNoActivityAdd.setTypeface(AppUtils.getBoldTypeface(this));
    }

    private void initListeners(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnNoActivityAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddAgendaActivity.class);
                startActivity(intent);
            }
        });

        viewNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddAgendaActivity.class);
                startActivity(intent);
            }
        });
    }
}
