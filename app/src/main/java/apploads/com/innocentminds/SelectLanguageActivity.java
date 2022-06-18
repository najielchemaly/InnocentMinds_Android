package apploads.com.innocentminds;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import apploads.com.innocentminds.helpers.LoadingIndicator;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.loading.LoadingActivity;

/**
 * Created by chchidiac on 4/12/19.
 */

public class SelectLanguageActivity extends BaseActivity {

    Button btnFrench, btnEnglish, btnCancel;
    TextView lblSelectLanguage;
    boolean isFromMenu;
    LoadingIndicator indicatorView;

    @Override
    public int getContentViewId() {
        return R.layout.select_language_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView() {
        btnCancel = _findViewById(R.id.btnCancel);
        btnEnglish = _findViewById(R.id.btnEnglish);
        btnFrench = _findViewById(R.id.btnFrench);
        lblSelectLanguage = _findViewById(R.id.lblSelectLanguage);
        indicatorView = _findViewById(R.id.indicatorView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            isFromMenu = bundle.getBoolean("isFromMenu");
        }

        if (!AppUtils.isAppLaunched(this)) {
            // from start
            btnCancel.setVisibility(View.GONE);
        } else {
            // from menu or app already started
            if (isFromMenu) {
                btnCancel.setVisibility(View.VISIBLE);
            } else {
                Intent loadingIntent = new Intent(getApplicationContext(), LoadingActivity.class);
                startActivity(loadingIntent);
                finish();
            }
        }
    }

    private void styleView() {
        btnCancel.setTypeface(AppUtils.getRegularTypeface(this));
        btnEnglish.setTypeface(AppUtils.getRegularTypeface(this));
        btnFrench.setTypeface(AppUtils.getRegularTypeface(this));
        lblSelectLanguage.setTypeface(AppUtils.getTitleRegularFont(this));
    }

    private void initListeners() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectLanguageActivity.class);
                startActivity(intent);
            }
        });

        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indicatorView.show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppUtils.setLocale(SelectLanguageActivity.this, "en");
                        Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
                        startActivity(intent);
                        finish();
                        indicatorView.hide();
                    }
                }, 1500);
            }
        });

        btnFrench.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indicatorView.show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppUtils.setLocale(SelectLanguageActivity.this, "fr");
                        Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);
                        startActivity(intent);
                        finish();
                        indicatorView.hide();
                    }
                }, 1500);
            }
        });
    }
}
