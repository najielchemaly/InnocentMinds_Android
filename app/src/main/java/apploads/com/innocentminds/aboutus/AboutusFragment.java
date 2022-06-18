package apploads.com.innocentminds.aboutus;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.helpers.LoadingIndicator;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.services.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutusFragment extends Fragment {

    private View parentView;

    ImageButton btnLogout;
    TextView lblAboutUs, txtOverView, txtDirectorNote, txtOurMission, txtOurStaff, txtDescription;
    Button btnClose;
    ImageView imgAboutUs;
    LoadingIndicator indicatorView;
    String ourMission, ourStaff, directorNote, overView;

    public static AboutusFragment newInstance() {
        AboutusFragment fragment = new AboutusFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.about_us_activity, container, false);

        btnClose = parentView.findViewById(R.id.btnClose);
        btnLogout = parentView.findViewById(R.id.btnLogout);
        lblAboutUs = parentView.findViewById(R.id.lblAboutUs);
        txtOverView = parentView.findViewById(R.id.txtOverView);
        txtDirectorNote = parentView.findViewById(R.id.txtDirectorNote);
        txtOurMission = parentView.findViewById(R.id.txtOurMission);
        txtOurStaff = parentView.findViewById(R.id.txtOurStaff);
        txtDescription = parentView.findViewById(R.id.txtDescription);
        indicatorView = parentView.findViewById(R.id.indicatorView);
        imgAboutUs = parentView.findViewById(R.id.imgAboutUs);
        txtDescription.setMovementMethod(new ScrollingMovementMethod());

        initListeners();
        styleView();
        callAboutUsService();
        btnClose.setVisibility(View.GONE);

        return parentView;
    }

    private void styleView(){
        lblAboutUs.setTypeface(AppUtils.getTitleRegularFont(getActivity()));
        txtDescription.setTypeface(AppUtils.getRegularTypeface(getActivity()));
    }

    private void initListeners(){
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.showLogoutAlert(AboutusFragment.this.getActivity(), getResources().getString(R.string.return_main_menu));
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

    void resetTextviewColors(TextView textView) {
        txtOverView.setTextColor(getResources().getColor(R.color.lightGrey));
        txtDirectorNote.setTextColor(getResources().getColor(R.color.lightGrey));
        txtOurMission.setTextColor(getResources().getColor(R.color.lightGrey));
        txtOurStaff.setTextColor(getResources().getColor(R.color.lightGrey));

        textView.setTextColor(getResources().getColor(R.color.darkGrey));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    AppUtils.showLogoutAlert(AboutusFragment.this.getActivity(), getResources().getString(R.string.return_main_menu));
                    return true;
                }
                return false;
            }
        });
    }

    private void callAboutUsService(){
        indicatorView.show();
        ApiManager.getService(true).getAboutUs(AppUtils.getLang(getActivity())).enqueue(new Callback<AboutUsResponse>() {
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
}
