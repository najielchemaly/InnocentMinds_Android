package apploads.com.innocentminds.parent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.objects.Message;
import apploads.com.innocentminds.parent.ChatActivity;

//import com.squareup.picasso.Picasso;

public class MessagesAdapter extends BaseAdapter {

    private List<Message> root;
    private Context context;
    private LayoutInflater mInflater;
    private int userPosition;

    public MessagesAdapter(List<Message> root, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final Message message = (Message) getItem(position);
        convertView = mInflater.inflate(R.layout.messages_row_item, null);


        ImageView imgInbox = convertView.findViewById(R.id.imgInbox);
        TextView txtMessage = convertView.findViewById(R.id.txtMessage);
        TextView txtDate = convertView.findViewById(R.id.txtDate);

        styleNotification(message.getType(),txtMessage, imgInbox);

        txtMessage.setTypeface(AppUtils.getRegularTypeface(context));
        txtDate.setTypeface(AppUtils.getRegularTypeface(context));

        txtMessage.setText(message.getMessage());
        txtDate.setText("Just Now");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    private void styleNotification(String type, TextView txtMessage,ImageView imgInbox){
        switch (type){
            case "read":
                imgInbox.setImageResource(R.drawable.inbox_message_gray);
                txtMessage.setTextColor(context.getResources().getColor(R.color.blackOpacity));
                break;
            case "unread":
                imgInbox.setImageResource(R.drawable.inbox_message_red);
                txtMessage.setTextColor(context.getResources().getColor(R.color.red));
                break;
        }
    }
}