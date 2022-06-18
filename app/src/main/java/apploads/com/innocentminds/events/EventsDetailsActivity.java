package apploads.com.innocentminds.events;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.user.Child;
import apploads.com.innocentminds.helpers.utils.AppUtils;

/**
 * Created by chchidiac on 4/2/19.
 */

public class EventsDetailsActivity extends BaseActivity {

    ImageButton btnBack;
    TextView txtCalendar, txtName, txtDate, txtTime, txtDescription;
    ImageView imgEvent;

    @Override
    public int getContentViewId() {
        return R.layout.event_details_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        setFonts();
        initListeners();
    }

    private void initView(){
        btnBack = _findViewById(R.id.btnBack);
        txtCalendar = _findViewById(R.id.txtCalendar);
        txtName = _findViewById(R.id.txtName);
        txtDate = _findViewById(R.id.txtDate);
        txtTime = _findViewById(R.id.txtTime);
        txtDescription = _findViewById(R.id.txtDescription);
        imgEvent = _findViewById(R.id.imgEvent);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Event event =  (Event) bundle.getSerializable("event");
            txtName.setText(event.getTitle());
            txtDate.setText(event.getDate().toString());
            txtTime.setText(event.getTime());
            txtDescription.setText(event.getDescription());
        }
    }

    private void setFonts(){
        txtCalendar.setTypeface(AppUtils.getTitleRegularFont(getApplicationContext()));
        txtName.setTypeface(AppUtils.getBoldTypeface(getApplicationContext()));
        txtDate.setTypeface(AppUtils.getRegularTypeface(getApplicationContext()));
        txtTime.setTypeface(AppUtils.getBoldTypeface(getApplicationContext()));
        txtDescription.setTypeface(AppUtils.getRegularTypeface(getApplicationContext()));
    }

    private void initListeners(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
