package apploads.com.innocentminds.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.databaseobjects.user.Child;
import apploads.com.innocentminds.databaseobjects.user.ChildActivity;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.parent.MenuView;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.parent.YourMessagesView;
import apploads.com.innocentminds.helpers.BaseActivity;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.services.ApiManager;

public class HomepageActivity extends BaseActivity {

    TextView lblDashboard, txtNotification, lblViewing, txtName, txtChangeChild, lblChangeChild;
    ImageButton btnMenu;
    Button btnCloseChilds;
    ListView listDashboard, lstChilds;
    ImageView imgNotification, imgProfile;
    DashboardAdapter dashboardAdapter;
    TextView txtNoActivities;
    LinearLayout viewNoActivities;
    RelativeLayout viewMessages, viewOverlay, viewChangeChild;
    ChangeChildAdapter changeChildAdapter;
    boolean doubleBackToExitPressedOnce = false;

    List<ChildActivity> childActivities = new ArrayList<>();
    List<Child> children = new ArrayList<>();
    Child selectedChild = new Child();

    @Override
    public int getContentViewId() {
        return R.layout.homepage_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView() {
        lblDashboard = _findViewById(R.id.lblDashboard);
        txtNotification = _findViewById(R.id.txtNotification);
        lblViewing = _findViewById(R.id.lblViewing);
        txtName = _findViewById(R.id.txtName);
        txtChangeChild = _findViewById(R.id.txtChangeChild);
        btnMenu = _findViewById(R.id.btnMenu);
        listDashboard = _findViewById(R.id.listDashboard);
        imgProfile = _findViewById(R.id.imgProfile);
        imgNotification = _findViewById(R.id.imgNotification);
        viewNoActivities = _findViewById(R.id.viewNoActivities);
        viewMessages = _findViewById(R.id.viewMessages);
        txtNoActivities = _findViewById(R.id.txtNoActivities);
        lstChilds = _findViewById(R.id.lstChilds);
        viewChangeChild = _findViewById(R.id.viewChangeChild);
        btnCloseChilds = _findViewById(R.id.btnCloseChilds);
        viewOverlay = _findViewById(R.id.viewOverlay);
        lblChangeChild = _findViewById(R.id.lblChangeChild);

        viewOverlay.setVisibility(View.generateViewId());
        viewOverlay.setAlpha(1f);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            if(bundle.getBoolean("showMenu")){
                showOverlay(true);

                final MenuView menuView = new MenuView(getApplicationContext(), HomepageActivity.this);
                viewMessages.addView(menuView);
            }
        }

        dashboardAdapter = new DashboardAdapter(childActivities, this);
        listDashboard.setAdapter(dashboardAdapter);

        txtNotification.setText(null);
        txtNotification.setVisibility(View.INVISIBLE);

        setupChildInfo();
        changeChildAdapter = new ChangeChildAdapter(children, getApplicationContext(), HomepageActivity.this);
        lstChilds.setAdapter(changeChildAdapter);
        viewChangeChild.setVisibility(View.VISIBLE);
        btnCloseChilds.setVisibility(View.GONE);
    }

    void setupChildInfo() {
        if (StaticData.user.getChildren() != null) {
            children = StaticData.user.getChildren();
        }

        if (selectedChild.getId() != null) {
            for (Child child : children) {
                if (child.getId() == selectedChild.getId()) {
                    selectedChild = child;
                    break;
                }
            }
        } else if (children.size() > 0) {
            selectedChild = children.get(0);
        }

        if (selectedChild.getActivities() != null && selectedChild.getActivities().size() > 0) {
            childActivities = selectedChild.getActivities();
            listDashboard.setVisibility(View.VISIBLE);
            viewNoActivities.setVisibility(View.GONE);
        } else {
            listDashboard.setVisibility(View.GONE);
            viewNoActivities.setVisibility(View.VISIBLE);
            txtNoActivities.setText(getResources().getString(R.string.no_activities) + " " + selectedChild.getFirstname());
        }

        if (selectedChild.getImage() != null) {
            // URLString = ApiManager.getMediaUrl()+selectedChild.getImage()
        }

        if (selectedChild.getFirstname() != null && selectedChild.getLastname() != null) {
            txtName.setText(selectedChild.getFirstname()+" "+selectedChild.getLastname());
        }

        dashboardAdapter.reloadData(childActivities);

        StaticData.selectedChild = selectedChild;
    }

    private void styleView() {
        lblDashboard.setTypeface(AppUtils.getTitleRegularFont(this));
        txtNotification.setTypeface(AppUtils.getBoldTypeface(this));
        lblViewing.setTypeface(AppUtils.getRegularTypeface(this));
        txtNoActivities.setTypeface(AppUtils.getRegularTypeface(this));
        txtName.setTypeface(AppUtils.getBoldTypeface(this));
        txtChangeChild.setTypeface(AppUtils.getBoldTypeface(this));
        lblChangeChild.setTypeface(AppUtils.getTitleRegularFont(this));
    }

    private void initListeners() {

        txtChangeChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeChildAdapter = new ChangeChildAdapter(children, getApplicationContext(), HomepageActivity.this);
                lstChilds.setAdapter(changeChildAdapter);
                viewChangeChild.setVisibility(View.VISIBLE);
            }
        });

        btnCloseChilds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewChangeChild.setVisibility(View.GONE);
                showOverlay(false);
            }
        });

        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOverlay(true);

                final YourMessagesView messagesView = new YourMessagesView(getApplicationContext(), HomepageActivity.this);
                viewMessages.addView(messagesView);
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOverlay(true);

                final MenuView menuView = new MenuView(getApplicationContext(), HomepageActivity.this);
                viewMessages.addView(menuView);
            }
        });
    }

    public void showOverlay(boolean toShow) {
        if (toShow) {
            viewOverlay.setVisibility(View.VISIBLE);
            viewOverlay.animate().alpha(1f).setDuration(300);
        } else {
            viewOverlay.animate().alpha(0f).setDuration(300);
            viewOverlay.setVisibility(View.GONE);
        }
    }

    public void changeChildPressed(){
        setupChildInfo();
        viewChangeChild.setVisibility(View.GONE);
        btnCloseChilds.setVisibility(View.VISIBLE);
        showOverlay(false);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
