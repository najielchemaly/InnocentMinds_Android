package apploads.com.innocentminds.teacher;

import android.content.Intent;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.base.BaseResponse;
import apploads.com.innocentminds.databaseobjects.user.SendActivity;
import apploads.com.innocentminds.helpers.CustomDialogClass;
import apploads.com.innocentminds.helpers.LoadingIndicator;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.homepage.DashboardAdapter;
import apploads.com.innocentminds.services.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildDailyAgendaActivity extends BaseActivity {

    ImageButton btnBack;
    ImageView imgChild;
    TextView txtName, txtNoActivityTitle, txtNoActivityDesc, txtSendMessage, lblAddActivity;
    CardView viewNoActivity, viewAddActivity;
    Button btnNoActivityAdd, btnSubmit;
    LinearLayout viewSendMessage, viewNewActivity;
    ListView lstActivities;
    DashboardAdapter dashboardAdapter;
    LoadingIndicator indicatorView;

    @Override
    public int getContentViewId() {
        return R.layout.child_daily_agenda_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView(){
        btnBack = _findViewById(R.id.btnBack);
        btnSubmit = _findViewById(R.id.btnSubmit);
        imgChild = _findViewById(R.id.imgChild);
        txtName = _findViewById(R.id.txtName);
        txtNoActivityTitle = _findViewById(R.id.txtNoActivityTitle);
        txtNoActivityDesc = _findViewById(R.id.txtNoActivityDesc);
        txtSendMessage = _findViewById(R.id.txtSendMessage);
        viewNoActivity = _findViewById(R.id.viewNoActivity);
        btnNoActivityAdd = _findViewById(R.id.btnNoActivityAdd);
        viewSendMessage = _findViewById(R.id.viewSendMessage);
        viewAddActivity = _findViewById(R.id.viewAddActivity);
        lblAddActivity = _findViewById(R.id.lblAddActivity);
        lstActivities = _findViewById(R.id.lstActivities);
        viewNewActivity = _findViewById(R.id.viewNewActivity);
        indicatorView = _findViewById(R.id.indicatorView);

        if (StaticData.selectedChild.getFirstname() != null && StaticData.selectedChild.getLastname() != null) {
            txtName.setText(StaticData.selectedChild.getFirstname()+" "+StaticData.selectedChild.getLastname());
        }

        StaticData.dailyAgendas = new ArrayList<>();
        if (StaticData.selectedChild.getActivities() != null) {
            StaticData.dailyAgendas = StaticData.selectedChild.getActivities();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(StaticData.dailyAgendas != null && StaticData.dailyAgendas.size() > 0) {
            viewAddActivity.setVisibility(View.VISIBLE);
            viewNoActivity.setVisibility(View.GONE);
            dashboardAdapter = new DashboardAdapter(StaticData.dailyAgendas, ChildDailyAgendaActivity.this);
            lstActivities.setAdapter(dashboardAdapter);
            lstActivities.setVisibility(View.VISIBLE);
        }else {
            viewAddActivity.setVisibility(View.GONE);
            viewNoActivity.setVisibility(View.VISIBLE);
            lstActivities.setVisibility(View.GONE);
        }
    }

    private void styleView() {
        txtName.setTypeface(AppUtils.getRegularTypeface(this));
        txtNoActivityTitle.setTypeface(AppUtils.getBoldTypeface(this));
        txtNoActivityDesc.setTypeface(AppUtils.getRegularTypeface(this));
        txtSendMessage.setTypeface(AppUtils.getRegularTypeface(this));
        btnNoActivityAdd.setTypeface(AppUtils.getRegularTypeface(this));
        lblAddActivity.setTypeface(AppUtils.getRegularTypeface(this));
    }

    private void initListeners(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Use the global function with delegate
                CustomDialogClass dialogClass = new CustomDialogClass(ChildDailyAgendaActivity.this, new CustomDialogClass.AbstractCustomDialogListener() {
                    @Override
                    public void onConfirm(CustomDialogClass.DialogResponse response) {
                        response.getDialog().dismiss();

                        sendDailyAgendas();
                    }

                    @Override
                    public void onCancel(CustomDialogClass.DialogResponse dialogResponse) {
                    }
                }, false, "Submit", "Cancel");
                dialogClass.setTitle("Innocent Minds");
                dialogClass.setMessage("Are you sure you want to submit child's daily agendas?");
                dialogClass.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialogClass.show();
            }
        });

        btnNoActivityAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddDailyAgendaActivity.class);
                startActivity( intent);
            }
        });

        viewSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SendMessageActivity.class);
                startActivity(intent);
            }
        });

        viewNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddDailyAgendaActivity.class);
                startActivity( intent);
            }
        });
    }

    void sendDailyAgendas() {
        if (StaticData.selectedChild.getId() != null &&
                StaticData.user.getId() != null) {
            SendActivity sendActivity = new SendActivity(StaticData.selectedChild.getId(),
                    StaticData.user.getId(), StaticData.dailyAgendas);

            indicatorView.show();
            btnNoActivityAdd.setVisibility(View.INVISIBLE);

            ApiManager.getService(true).addActivity(sendActivity).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            if (response.body().getMessage() != null) {
                                AppUtils.showAlertWithMessage(ChildDailyAgendaActivity.this, "Innocent Minds", response.body().getMessage());
                            } else {
                                AppUtils.showAlertWithMessage(ChildDailyAgendaActivity.this, "Innocent Minds", "Activities has been added successfully");
                            }
                        } else if (response.body().getMessage() != null) {
                            AppUtils.showAlertWithMessage(ChildDailyAgendaActivity.this, "Innocent Minds", response.body().getMessage());
                        } else {
                            AppUtils.showAlertWithMessage(ChildDailyAgendaActivity.this, "Innocent Minds", "An error has occured, please try again or contact support");
                        }
                    }

                    indicatorView.hide();
                    btnNoActivityAdd.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    indicatorView.hide();
                    btnNoActivityAdd.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
