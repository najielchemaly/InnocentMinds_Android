package apploads.com.innocentminds.parent;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.base.BaseResponse;
import apploads.com.innocentminds.databaseobjects.notification.Notification;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.helpers.utils.StringUtils;
import apploads.com.innocentminds.objects.Phone;
import apploads.com.innocentminds.services.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.squareup.picasso.Picasso;

public class NotificationsAdapter extends BaseAdapter {

    private List<Notification> root;
    private Context context;
    private LayoutInflater mInflater;

    public NotificationsAdapter(List<Notification> root, Context context) {
        this.root = root;
        this.context = context;
        if (context != null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    @Override
    public int getCount() {
        return root.size();
    }

    @Override
    public Object getItem(int position) {
        return root.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) Math.random();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Notification notification = (Notification) getItem(position);
        convertView = mInflater.inflate(R.layout.notification_row_item, null);


        ImageView imgType = convertView.findViewById(R.id.imgNotificationType);
        TextView txtNotification = convertView.findViewById(R.id.txtNotification);
        TextView txtDate = convertView.findViewById(R.id.txtDate);
        TextView txtDelete = convertView.findViewById(R.id.txtDelete);

        styleNotification(notification.getType(),txtNotification);

        txtNotification.setTypeface(AppUtils.getRegularTypeface(context));
        txtDate.setTypeface(AppUtils.getRegularTypeface(context));

        txtDelete.setTag(position);
        txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Notification notificationToDelete = (Notification) getItem((Integer) view.getTag());
                if (notificationToDelete.getId() != null) {
                    String userId = StaticData.user.getId() == null ? "0" : StaticData.user.getId().toString();

                    ApiManager.getService(true).updateNotification(userId, notification.getId().toString(), true, false).enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 1) {
                                    root.remove(notificationToDelete);
                                    this.notifyAll();
                                }
                            } else if (response.body().getMessage() != null) {
                                AppUtils.showAlertWithMessage((Activity) context, "Innocent Minds", response.body().getMessage());
                            } else {
                                AppUtils.showAlertWithMessage((Activity) context, "Innocent Minds", "An error has occured, please try again or contact support");
                            }
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });

        imgType.setImageResource(notification.getIs_read() ? R.drawable.inbox_message_gray : R.drawable.inbox_message_red);
        txtNotification.setText(notification.getDescription());

        if (notification.getDate() != null) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            try {
                Date date = format.parse(notification.getDate());
                txtDate.setText(AppUtils.getTimeAgo(date.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        txtNotification.setTextColor(notification.getIs_read() ? context.getResources().getColor(R.color.red) : context.getResources().getColor(R.color.greydialogue));

        return convertView;
    }

    private void styleNotification(String type, TextView title){
        switch (type){
            case "food":
                title.setTextColor(context.getResources().getColor(R.color.orange));
                break;
            case "nap":
                title.setTextColor(context.getResources().getColor(R.color.signinBlue));
                break;
            case "bath":
                title.setTextColor(context.getResources().getColor(R.color.yellowDialogue));
                break;
        }
    }
}