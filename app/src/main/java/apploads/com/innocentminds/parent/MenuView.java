package apploads.com.innocentminds.parent;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.SelectLanguageActivity;
import apploads.com.innocentminds.aboutus.AboutusActivity;
import apploads.com.innocentminds.events.EventsActivity;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.homepage.HomepageActivity;
import apploads.com.innocentminds.loading.LoadingActivity;
import apploads.com.innocentminds.signup.EditChildProfileStepOneActivity;
import apploads.com.innocentminds.signup.SignupChildActivity;

public class MenuView extends RelativeLayout {

    LayoutInflater mInflater;
    HomepageActivity homepageActivity;
    public MenuView(Context context , HomepageActivity homepageActivity) {
        super(context);
        this.homepageActivity = homepageActivity;
        mInflater = LayoutInflater.from(context);
        init(context);

    }
    public MenuView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mInflater = LayoutInflater.from(context);
        init(context);
    }
    public MenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init(context);
    }
    public void init(final Context context)
    {
        View view = mInflater.inflate(R.layout.menu_view, this, true);
        TextView txtAddChild = view.findViewById(R.id.txtAddChild);
        TextView txtEditMyProfile = view.findViewById(R.id.txtEditMyProfile);
        TextView txtEditChildProfile = view.findViewById(R.id.txtEditChildProfile);
        TextView txtChangePassword = view.findViewById(R.id.txtChangePassword);
        TextView txtImages = view.findViewById(R.id.txtImages);
        TextView txtPayments = view.findViewById(R.id.txtPayments);
        TextView txtFoodCalendar = view.findViewById(R.id.txtFoodCalendar);
        TextView txtAboutus = view.findViewById(R.id.txtAboutus);
        TextView txtSelectLanguage = view.findViewById(R.id.txtSelectLanguage);
        TextView txtEvents = view.findViewById(R.id.txtEvents);
        TextView txtContactus = view.findViewById(R.id.txtContactus);
        TextView txtLogout = view.findViewById(R.id.txtLogout);
        TextView lblSubTitle = view.findViewById(R.id.lblSubTitle);
        TextView lblHello = view.findViewById(R.id.lblHello);

        if("en".equals(AppUtils.getLang(context))){
            AppUtils.setLocale(context, "en");
        }else {
            AppUtils.setLocale(context, "fr");
        }

        lblHello.setText(getResources().getString(R.string.hello));
        lblSubTitle.setText(getResources().getString(R.string.what_to_do));
        txtLogout.setText(getResources().getString(R.string.logout));
        txtContactus.setText(getResources().getString(R.string.contact_us));
        txtSelectLanguage.setText(getResources().getString(R.string.select_language));
        txtPayments.setText(getResources().getString(R.string.payments_and_fees));
        txtImages.setText(getResources().getString(R.string.see_some_images));
        txtChangePassword.setText(getResources().getString(R.string.change_password));
        txtEditChildProfile.setText(getResources().getString(R.string.edit_child_profile));
        txtEditMyProfile.setText(getResources().getString(R.string.edit_my_profile));
        txtAddChild.setText(getResources().getString(R.string.add_a_child));
        txtEvents.setText(getResources().getString(R.string.events));

        ImageButton btnClose = view.findViewById(R.id.btnClose);

        txtAddChild.setTypeface(AppUtils.getBoldTypeface(context));
        txtEditMyProfile.setTypeface(AppUtils.getRegularTypeface(context));
        txtEditChildProfile.setTypeface(AppUtils.getRegularTypeface(context));
        txtChangePassword.setTypeface(AppUtils.getRegularTypeface(context));
        txtImages.setTypeface(AppUtils.getRegularTypeface(context));
        txtPayments.setTypeface(AppUtils.getRegularTypeface(context));
        txtAboutus.setTypeface(AppUtils.getRegularTypeface(context));
        txtSelectLanguage.setTypeface(AppUtils.getRegularTypeface(context));
        txtContactus.setTypeface(AppUtils.getRegularTypeface(context));
        txtEvents.setTypeface(AppUtils.getRegularTypeface(context));
        txtFoodCalendar.setTypeface(AppUtils.getRegularTypeface(context));
        txtLogout.setTypeface(AppUtils.getBoldTypeface(context));
        lblSubTitle.setTypeface(AppUtils.getRegularTypeface(context));
        lblHello.setTypeface(AppUtils.getTitleBoldFont(context));

        txtLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.showLogoutAlert(homepageActivity, getResources().getString(R.string.are_you_sure_logout));
            }
        });

        txtPayments.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PaymentsFeesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        txtEvents.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("isFoodCalendar", false);
                context.startActivity(intent);
            }
        });

        txtEditChildProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditChildProfileStepOneActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        txtChangePassword.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChangePasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        txtAddChild.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SignupChildActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        txtImages.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GalleryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        txtFoodCalendar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("isFoodCalendar", true);
                context.startActivity(intent);
            }
        });

        txtAboutus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AboutusActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        txtEditMyProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditMyProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        txtContactus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContactusActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        txtSelectLanguage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelectLanguageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("isFromMenu", true);
                context.startActivity(intent);
            }
        });

        btnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                homepageActivity.showOverlay(false);
                setVisibility(GONE);
            }
        });
    }
}