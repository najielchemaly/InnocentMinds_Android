package apploads.com.innocentminds.teacher;

import android.content.Context;

import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.globalvariables.Messages;
import apploads.com.innocentminds.helpers.utils.AppUtils;

//import com.squareup.picasso.Picasso;

public class SendMessageAdapter extends BaseAdapter {

    private List<Messages> root;
    private Context context;
    private LayoutInflater mInflater;

    public SendMessageAdapter(List<Messages> root, Context context) {
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
        final Messages message = (Messages) getItem(position);
        convertView = mInflater.inflate(R.layout.message_row_item, null);

        final TextView txtMessage = convertView.findViewById(R.id.txtMessage);
        final CardView msgCardView = convertView.findViewById(R.id.msgCardView);

        txtMessage.setTypeface(AppUtils.getRegularTypeface(context));
        txtMessage.setText(message.getDescription());

        if (message.getSelected() != null) {
            msgCardView.setCardBackgroundColor(message.getSelected() == true ? context.getResources().getColor(R.color.signinBlue) :
                    context.getResources().getColor(R.color.white));

            txtMessage.setTextColor(message.getSelected() == true ? context.getResources().getColor(R.color.white) :
                    context.getResources().getColor(R.color.darkGrey));
        }

        convertView.setTag(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Messages message = (Messages) getItem((Integer) view.getTag());
                message.setSelected(message.getSelected() == null ? true : !message.getSelected());

                msgCardView.setCardBackgroundColor(message.getSelected() == true ? context.getResources().getColor(R.color.signinBlue) :
                        context.getResources().getColor(R.color.white));

                txtMessage.setTextColor(message.getSelected() == true ? context.getResources().getColor(R.color.white) :
                        context.getResources().getColor(R.color.darkGrey));
            }
        });

        return convertView;
    }
}