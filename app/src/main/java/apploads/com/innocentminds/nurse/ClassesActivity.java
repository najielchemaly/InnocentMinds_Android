package apploads.com.innocentminds.nurse;

import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.base.BaseResponse;
import apploads.com.innocentminds.databaseobjects.user.UserClass;
import apploads.com.innocentminds.helpers.CustomDialogClass;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.objects.Class;
import apploads.com.innocentminds.pickuser.PickUserActivity;
import apploads.com.innocentminds.services.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassesActivity extends BaseActivity {

    ClassesAdapter classesAdapter;

    TextView lblHello, lblChooseAClass, lblName;
    ImageButton btnLogout, btnSearch;
    GridView gridViewClasses;

    List<UserClass> classesList = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.classes_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        initListeners();
    }

    private void initView() {
        lblHello = _findViewById(R.id.lblHello);
        lblName = _findViewById(R.id.lblName);
        lblChooseAClass = _findViewById(R.id.lblChooseAClass);
        btnLogout = _findViewById(R.id.btnLogout);
        btnSearch = _findViewById(R.id.btnSearch);
        gridViewClasses = _findViewById(R.id.gridViewClasses);

        lblHello.setTypeface(AppUtils.getBoldTypeface(this));
        lblName.setTypeface(AppUtils.getRegularTypeface(this));
        lblChooseAClass.setTypeface(AppUtils.getRegularTypeface(this));

        if (StaticData.user.getFullname() != null) {
            lblName.setText(StaticData.user.getFullname().trim() + " (nurse)");
        }

        if (StaticData.user.getClasses() != null) {
            classesList = StaticData.user.getClasses();
        }

        classesAdapter = new ClassesAdapter(this, classesList);
        gridViewClasses.setAdapter(classesAdapter);
    }

    private void initListeners() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.showLogoutAlert(ClassesActivity.this, getResources().getString(R.string.are_you_sure_logout));
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchChildListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
