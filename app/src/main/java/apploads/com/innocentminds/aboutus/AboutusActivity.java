package apploads.com.innocentminds.aboutus;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.globalvariables.About;
import apploads.com.innocentminds.events.EventsActivity;
import apploads.com.innocentminds.helpers.LoadingIndicator;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.loading.LoadingActivity;
import apploads.com.innocentminds.services.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutusActivity extends BaseActivity {

    ImageButton btnLogout;
    TextView lblAboutUs, txtOverView, txtDirectorNote, txtOurMission, txtOurStaff, txtDescription;
    Button btnClose;
    LoadingIndicator indicatorView;
    ImageView imgAboutUs;
    String ourMission, ourStaff, directorNote, overView;

    @Override
    public int getContentViewId() {
        return R.layout.about_us_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        initListeners();
        styleView();
        callAboutUsService();
    }

    private void initView(){
        btnClose = _findViewById(R.id.btnClose);
        btnLogout = _findViewById(R.id.btnLogout);
        lblAboutUs = _findViewById(R.id.lblAboutUs);
        txtOverView = _findViewById(R.id.txtOverView);
        txtDirectorNote = _findViewById(R.id.txtDirectorNote);
        txtOurMission = _findViewById(R.id.txtOurMission);
        txtOurStaff = _findViewById(R.id.txtOurStaff);
        txtDescription = _findViewById(R.id.txtDescription);
        imgAboutUs = _findViewById(R.id.imgAboutUs);
        indicatorView = _findViewById(R.id.indicatorView);
        imgAboutUs.setImageResource(R.drawable.overview);
        txtDescription.setMovementMethod(new ScrollingMovementMethod());
        btnLogout.setVisibility(View.GONE);
    }

    private void initListeners(){
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtOverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtDescription.setText(overView);
                imgAboutUs.setImageResource(R.drawable.overview);
                imgAboutUs.setVisibility(View.VISIBLE);
                resetTextviewColors(txtOverView);
            }
        });
        txtDirectorNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtDescription.setText(directorNote);
                imgAboutUs.setImageResource(R.drawable.director_note);
                imgAboutUs.setVisibility(View.VISIBLE);
                resetTextviewColors(txtDirectorNote);
            }
        });
        txtOurMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtDescription.setText(ourMission);
                imgAboutUs.setImageResource(R.drawable.our_mission);
                imgAboutUs.setVisibility(View.VISIBLE);
                resetTextviewColors(txtOurMission);
            }
        });
        txtOurStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtDescription.setText(ourStaff);
                imgAboutUs.setVisibility(View.GONE);
                resetTextviewColors(txtOurStaff);
            }
        });
    }

    private void callAboutUsService(){
        indicatorView.show();
        ApiManager.getService(true).getAboutUs(AppUtils.getLang(AboutusActivity.this)).enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    AboutUs aboutUs = response.body().getAboutUs();
                    overView = aboutUs.getOverview();
                    ourStaff = aboutUs.getOurStaff();
                    ourMission = aboutUs.getOurMission();
                    directorNote = aboutUs.getDirectorNote();

                    txtDescription.setText(overView);
                    indicatorView.hide();
                }
            }

            @Override
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {
                indicatorView.hide();
            }
        });
    }

    void resetTextviewColors(TextView textView) {
        txtOverView.setTextColor(getResources().getColor(R.color.lightGrey));
        txtDirectorNote.setTextColor(getResources().getColor(R.color.lightGrey));
        txtOurMission.setTextColor(getResources().getColor(R.color.lightGrey));
        txtOurStaff.setTextColor(getResources().getColor(R.color.lightGrey));

        textView.setTextColor(getResources().getColor(R.color.darkGrey));
    }

    private void styleView(){
        lblAboutUs.setTypeface(AppUtils.getTitleRegularFont(this));
        txtDescription.setTypeface(AppUtils.getRegularTypeface(this));
    }
}
