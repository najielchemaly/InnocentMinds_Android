package apploads.com.innocentminds.parent;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.objects.Message;

public class ChatActivity extends BaseActivity {
    Button btnSend, btnBack;
    EditText txtMessage;
    ChatAdapter chatAdapter;
    ListView lstChat;
    int i = 0;

    @Override
    public int getContentViewId() {
        return R.layout.chat_activity;
    }

    @Override
    public void doOnCreate() throws Exception {

        btnSend= _findViewById(R.id.btnSend);
        btnBack= _findViewById(R.id.btnBack);
        txtMessage= _findViewById(R.id.txtMessage);
        lstChat= _findViewById(R.id.lstChat);

        chatAdapter = new ChatAdapter(this);
        lstChat.setAdapter(chatAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message();
                message.setMessage(txtMessage.getText().toString());
                if((i % 2) == 0){
                    message.setMyMessage(true);
                }else {
                    message.setMyMessage(false);
                }

                chatAdapter.add(message);

                txtMessage.setText("");
                lstChat.setSelection(lstChat.getCount() - 1);
                i++;
            }
        });

    }
}
