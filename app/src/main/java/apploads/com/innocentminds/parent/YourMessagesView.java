package apploads.com.innocentminds.parent;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.notification.Notification;
import apploads.com.innocentminds.databaseobjects.notification.NotificationResponse;
import apploads.com.innocentminds.helpers.LoadingIndicator;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.homepage.HomepageActivity;
import apploads.com.innocentminds.services.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YourMessagesView extends RelativeLayout {

    LayoutInflater mInflater;
    HomepageActivity homepageActivity;
    ListView lstNotifications = null;
    NotificationsAdapter notificationsAdapter = null;

    List<Notification> notifications = new ArrayList<>();

    public YourMessagesView(Context context , HomepageActivity homepageActivity) {
        super(context);
        this.homepageActivity = homepageActivity;
        mInflater = LayoutInflater.from(context);
        init(context);
    }

    public YourMessagesView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mInflater = LayoutInflater.from(context);
        init(context);
    }

    public YourMessagesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init(context);
    }

    public void init(Context context)
    {
        View view = mInflater.inflate(R.layout.your_messages_view, this, true);
        final TextView txtMessagesTitle = view.findViewById(R.id.txtMessagesTitle);
        final TextView lblMessages = view.findViewById(R.id.lblMessages);
        final TextView lblNotifications = view.findViewById(R.id.lblNotifications);
        lstNotifications = view.findViewById(R.id.lstNotifications);
        final ListView lstMessages = view.findViewById(R.id.lstMessages);
        LinearLayout viewNotifications = view.findViewById(R.id.viewNotifications);
        LinearLayout viewMessages = view.findViewById(R.id.viewMessages);
        Button btnClose = view.findViewById(R.id.btnClose);
        LoadingIndicator indicatorView = view.findViewById(R.id.indicatorView);

        txtMessagesTitle.setTypeface(AppUtils.getRegularTypeface(context));
        lblMessages.setTypeface(AppUtils.getTitleRegularFont(context));
        lblNotifications.setTypeface(AppUtils.getRegularTypeface(context));
        btnClose.setTypeface(AppUtils.getBoldTypeface(context));

//        MessagesAdapter messagesAdapter = new MessagesAdapter(generateMessages(), context);
//        lstMessages.setAdapter(messagesAdapter);

        btnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibility(GONE);
                homepageActivity.showOverlay(false);
            }
        });

        viewNotifications.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                lblNotifications.setTextColor(getResources().getColor(R.color.black));
                lblMessages.setTextColor(getResources().getColor(R.color.blackOpacity));
                lstNotifications.setVisibility(VISIBLE);
                lstMessages.setVisibility(GONE);
            }
        });

        viewMessages.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                lblMessages.setTextColor(getResources().getColor(R.color.black));
                lblNotifications.setTextColor(getResources().getColor(R.color.blackOpacity));
                lstNotifications.setVisibility(GONE);
                lstMessages.setVisibility(VISIBLE);
            }
        });

        getNotifications(context, indicatorView);
    }

    void getNotifications(final Context context, final LoadingIndicator indicatorView) {
        if (StaticData.user.getId() != null) {
            indicatorView.show();

            ApiManager.getService(true).getNotifications(StaticData.user.getId().toString()).enqueue(new Callback<NotificationResponse>() {
                @Override
                public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            if (response.body().getNotifications() != null) {
                                notifications = response.body().getNotifications();
                                notificationsAdapter = new NotificationsAdapter(notifications, context);
                                lstNotifications.setAdapter(notificationsAdapter);
                            }
                        } else if (response.body().getMessage() != null) {
                            AppUtils.showAlertWithMessage((Activity) context, "Innocent Minds", response.body().getMessage());
                        } else {
                            AppUtils.showAlertWithMessage((Activity) context, "Innocent Minds", getResources().getString(R.string.an_error_occured));
                        }
                    }

                    indicatorView.show();
                }

                @Override
                public void onFailure(Call<NotificationResponse> call, Throwable t) {
                    indicatorView.hide();
                }
            });
        }
    }
}