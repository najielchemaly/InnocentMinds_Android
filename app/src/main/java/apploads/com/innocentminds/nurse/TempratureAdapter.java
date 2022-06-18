package apploads.com.innocentminds.nurse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.user.ChildTemperature;
import apploads.com.innocentminds.helpers.utils.AppUtils;

public class TempratureAdapter extends BaseAdapter {

    private List<ChildTemperature> root;
    private Context context;
    private LayoutInflater mInflater;

    public TempratureAdapter(List<ChildTemperature> root, Context context) {
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
        final ChildTemperature temprature = (ChildTemperature) getItem(position);
        convertView = mInflater.inflate(R.layout.temprature_row_item, null);

        TextView txtTemp = convertView.findViewById(R.id.txtTemp);
        TextView txtDate = convertView.findViewById(R.id.txtDate);

        txtTemp.setTypeface(AppUtils.getRegularTypeface(context));
        txtDate.setTypeface(AppUtils.getRegularTypeface(context));

        if(Double.parseDouble(temprature.getTemperature_id()) <= 38.0){
            txtTemp.setTextColor(context.getResources().getColor(R.color.signinBlue));
        }else {
            txtTemp.setTextColor(context.getResources().getColor(R.color.red));
        }

        txtTemp.setText(temprature.getTemperature_id() + "°C");
        txtDate.setText(" at "+temprature.getDate());

        return convertView;
    }

}