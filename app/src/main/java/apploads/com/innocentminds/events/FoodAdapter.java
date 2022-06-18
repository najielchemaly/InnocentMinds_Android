package apploads.com.innocentminds.events;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.helpers.utils.AppUtils;

public class FoodAdapter extends BaseAdapter {

    private List<Food> root;
    private Context context;
    private LayoutInflater mInflater;
    private int userPosition;

    public FoodAdapter(List<Food> root, Context context) {
        this.root = root;
        this.context = context;
        if (context != null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    public void reloadData(List<Food> root) {
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
        final Food food = (Food) getItem(position);
        convertView = mInflater.inflate(R.layout.event_row_item, null);

        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtDate = convertView.findViewById(R.id.txtDate);
        TextView txtTime = convertView.findViewById(R.id.txtTime);
        TextView txtDescription = convertView.findViewById(R.id.txtDescription);
        txtName.setTypeface(AppUtils.getBoldTypeface(context));
        txtDate.setTypeface(AppUtils.getRegularTypeface(context));
        txtTime.setTypeface(AppUtils.getBoldTypeface(context));
        txtDescription.setTypeface(AppUtils.getRegularTypeface(context));

        txtName.setText(food.getTitle());
        txtDate.setText(food.getDate().toString());
        txtTime.setText(food.getTime());
        txtDescription.setText(food.getDescription());
        return convertView;
    }

}
