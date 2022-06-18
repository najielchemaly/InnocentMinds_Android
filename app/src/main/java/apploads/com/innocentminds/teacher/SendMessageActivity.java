package apploads.com.innocentminds.teacher;


import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.base.BaseResponse;
import apploads.com.innocentminds.databaseobjects.globalvariables.Messages;
import apploads.com.innocentminds.helpers.LoadingIndicator;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.services.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendMessageActivity extends Activity {

    SendMessageAdapter sendMessageAdapter;
    ListView lstMessages;
    Button btnClose, btnSend;
    LoadingIndicator indicatorView;

    List<Messages> messages = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.send_message_activity);

        lstMessages = findViewById(R.id.lstMessages);
        btnClose = findViewById(R.id.btnClose);
        btnSend = findViewById(R.id.btnSend);
        indicatorView = findViewById(R.id.indicatorView);

        btnSend.setTypeface(AppUtils.getBoldTypeface(this));

        initListeners();

        if (StaticData.variables.getMessages() != null) {
            messages = StaticData.variables.getMessages();
        }

        sendMessageAdapter = new SendMessageAdapter(messages, this);
        lstMessages.setAdapter(sendMessageAdapter);
    }

    private void initListeners(){
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageIds = "";
                for (Messages msg : messages) {
                    if (msg.getId() != null && msg.getSelected() == true) {
                        messageIds += msg.getId().toString()+",";
                    }
                }

                StringBuilder builder = new StringBuilder(messageIds);
                builder.deleteCharAt(messageIds.length() - 1);
                messageIds = builder.toString();

                String childId = "0";
                if (StaticData.selectedChild.getId() != null) {
                    childId = StaticData.selectedChild.getId().toString();
                }

                String parentId = "0";
                if (StaticData.selectedChild.getParent_id() != null) {
                    parentId = StaticData.selectedChild.getParent_id().toString();
                }

                indicatorView.show();

                ApiManager.getService(true).sendMessage(parentId, childId, messageIds).enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                if (response.body().getMessage() != null) {
                                    AppUtils.showAlertWithMessage(SendMessageActivity.this, "Innocent Minds", response.body().getMessage());
                                } else {
                                    AppUtils.showAlertWithMessage(SendMessageActivity.this, "Innocent Minds", "Your message has been sent successfully");
                                }

                                resetMessages();
                            } else if (response.body().getMessage() != null) {
                                AppUtils.showAlertWithMessage(SendMessageActivity.this, "Innocent Minds", response.body().getMessage());
                            } else {
                                AppUtils.showAlertWithMessage(SendMessageActivity.this, "Innocent Minds", "An error has occured, please try again or contact support");
                            }
                        }

                        indicatorView.hide();
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        indicatorView.hide();
                    }
                });
            }
        });
    }

    void resetMessages() {
        messages = new ArrayList<>();

        if (StaticData.variables.getMessages() != null) {
            messages = StaticData.variables.getMessages();
        }

        sendMessageAdapter.notifyDataSetChanged();
    }
}