package apploads.com.innocentminds.pickuser;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import apploads.com.innocentminds.SigninAsGuestActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.helpers.BaseActivity;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.signin.SigninActivity;
import apploads.com.innocentminds.signup.SignupChildActivity;

public class PickUserActivity extends BaseActivity {

    ImageButton btnRegisterChild, btnSignIn, btnGuest;
    TextView lblHello;
    Button btnChangeLang;

    String preferredLang;

    String registerImageName, signinImageName, guestImageName;

    @Override
    public int getContentViewId() {
        return R.layout.pick_user_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        initListerens();
    }

    private void initView(){
        btnRegisterChild = _findViewById(R.id.btnRegisterChild);
        btnSignIn = _findViewById(R.id.btnSignIn);
        btnGuest = _findViewById(R.id.btnGuest);
        lblHello = _findViewById(R.id.lblHello);
        btnChangeLang = _findViewById(R.id.btnChangeLang);

        preferredLang = AppUtils.getLang(getApplicationContext());

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/regular.ttf");
        lblHello.setTypeface(custom_font);

        btnChangeLang.setText(AppUtils.isEn(this) ? "Fr" : "En");

        registerImageName = AppUtils.isEn(this) ? "pick_user_register_full" : "pick_user_register_full_fr";
        signinImageName = AppUtils.isEn(this) ? "pick_user_signin_full" : "pick_user_signin_full_fr";
        guestImageName = AppUtils.isEn(this) ? "pick_user_guest_full" : "pick_user_guest_full_fr";

        Resources resources = getResources();
        String packageName = getPackageName();

        btnRegisterChild.setBackground(resources.getDrawable(
                resources.getIdentifier(registerImageName, "drawable", packageName)));
        btnSignIn.setBackground(resources.getDrawable(
                resources.getIdentifier(signinImageName, "drawable", packageName)));
        btnGuest.setBackground(resources.getDrawable(
                resources.getIdentifier(guestImageName, "drawable", packageName)));
    }

    private void initListerens(){

        btnRegisterChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupChildActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });

        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SigninAsGuestActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            }
        });

        btnChangeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.setLocale(PickUserActivity.this,"en".equals(preferredLang)?"fr":"en");
                reloadActivity();
            }
        });
    }

    void reloadActivity(){
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
