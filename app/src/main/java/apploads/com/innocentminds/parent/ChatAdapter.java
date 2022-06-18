package apploads.com.innocentminds.parent;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.objects.Message;

public class ChatAdapter extends BaseAdapter {

    List<Message> messages = new ArrayList<Message>();
    Context context;

    public ChatAdapter(Context context) {
        this.context = context;
    }

    public void add(Message message) {
        this.messages.add(message);
        notifyDataSetChanged(); // to render the list we need to notify
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = messages.get(i);

        if (message.isMyMessage()) { // this message was sent by us
            view = messageInflater.inflate(R.layout.my_message, null);
            holder.messageBody = (TextView) view.findViewById(R.id.message_body);
            view.setTag(holder);
            holder.messageBody.setText(message.getMessage());
        } else { // this message was sent by someone else so let's create an advanced chat bubble on the left
            view = messageInflater.inflate(R.layout.their_message, null);
            holder.messageBody = (TextView) view.findViewById(R.id.message_body);
            view.setTag(holder);

            holder.messageBody.setText(message.getMessage());
        }

        return view;
    }

    class MessageViewHolder {
        public View avatar;
        public TextView name;
        public TextView messageBody;
    }
}
