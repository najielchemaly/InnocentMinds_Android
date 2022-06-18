package apploads.com.innocentminds.firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;
import java.util.Objects;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.base.BaseResponse;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.homepage.HomepageActivity;
import apploads.com.innocentminds.services.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessaginService extends FirebaseMessagingService {
    public MyFirebaseMessaginService() {}

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }

                        // Get new Instance ID token
                        String token = Objects.requireNonNull(task.getResult()).getToken();
                        if(StaticData.user != null && StaticData.user.getId() != null) {
                            sendRegistrationToServer(StaticData.user.getId().toString(), token);
                        }
                    }
                });
    }

    private void sendRegistrationToServer(String userId, String token) {
        ApiManager.getService().updateToken(userId, token).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {}

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {}
        });
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getNotification() != null){
            createNotification(remoteMessage);
        }

        remoteMessage.getData();
        String msg = remoteMessage.getData().get("");

        if(remoteMessage.getData().get("type") != null &&
                Objects.equals(remoteMessage.getData().get("type"), "update_active_matches")){
            if(StaticData.context.getClass() == HomepageActivity.class){
                HomepageActivity homepageActivity = (HomepageActivity) StaticData.context;
//                    homepageActivity.updateBadge();
            }
        }else {
            int badge = AppUtils.getBadge(getApplicationContext());
            AppUtils.updateBadge(getApplicationContext(), ++badge);

            if(StaticData.context.getClass() == HomepageActivity.class){
                HomepageActivity homepageActivity = (HomepageActivity) StaticData.context;
//                    homepageActivity.updateBadge();
            }
        }
    }

    private void createNotification(RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, HomepageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); // default sound of the notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            // Do something for lollipop and above versions
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.icon)
                            .setPriority(Notification.PRIORITY_MAX)
                            .setContentTitle(Objects.requireNonNull(remoteMessage.getNotification()).getTitle())
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(remoteMessage.getNotification().getBody()))
                            .setContentText(remoteMessage.getNotification().getBody())
                            .setColor(getResources().getColor(R.color.colorPrimary));

            mBuilder.setSound(defaultSoundUri);
            mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            mBuilder.setAutoCancel(true);
            mBuilder.setContentIntent(pendingIntent);
            notificationManager.notify(Calendar.getInstance().get(Calendar.MILLISECOND), mBuilder.build());
        } else {
            // do something for phones running an SDK before lollipop
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.icon)
                            .setContentTitle(Objects.requireNonNull(remoteMessage.getNotification()).getTitle())
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(remoteMessage.getNotification().getBody()))
                            .setContentText(remoteMessage.getNotification().getBody());

            mBuilder.setSound(defaultSoundUri);
            mBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            mBuilder.setAutoCancel(true);
            mBuilder.setContentIntent(pendingIntent);
            notificationManager.notify(Calendar.getInstance().get(Calendar.MILLISECOND), mBuilder.build());
        }
    }
}
