package apploads.com.innocentminds.events;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.helpers.utils.AppUtils;

public class EventsAdapter extends BaseAdapter {


    private List<Event> root;
    private Context context;
    private LayoutInflater mInflater;
    private int userPosition;

    public  EventsAdapter(List<Event> root, Context context) {
        this.root = root;
        this.context = context;
        if (context != null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    public void reloadData(List<Event> root) {
        this.root = root;
        this.notifyDataSetChanged();
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
        final Event event = (Event) getItem(position);
        convertView = mInflater.inflate(R.layout.event_row_item, null);

        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtDate = convertView.findViewById(R.id.txtDate);
        TextView txtTime = convertView.findViewById(R.id.txtTime);
        TextView txtDescription = convertView.findViewById(R.id.txtDescription);
        txtName.setTypeface(AppUtils.getBoldTypeface(context));
        txtDate.setTypeface(AppUtils.getRegularTypeface(context));
        txtTime.setTypeface(AppUtils.getBoldTypeface(context));
        txtDescription.setTypeface(AppUtils.getRegularTypeface(context));

        txtName.setText(event.getTitle());

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        String date = dt.format(event.getDate());

        txtDate.setText(date);
        txtTime.setText(event.getTime());
        txtDescription.setText(event.getDescription());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, EventsDetailsActivity.class);
                intent.putExtra("event", event);
                context.startActivity(intent);
            }
        });


        return convertView;
    }
}