package apploads.com.innocentminds.nurse;

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
import apploads.com.innocentminds.databaseobjects.user.UserClass;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;

public class ClassesAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private List<UserClass> classes;

    // Constructor
    public ClassesAdapter(Context context, List<UserClass> classes) {
        this.context = context;
        this.classes = classes;
        if (context != null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    public int getCount() {
        return classes.size();
    }

    public Object getItem(int position) {
        return classes.get(position);
    }

    public long getItemId(int position) {
        return (long) Math.random();
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.class_row_item, null);
        final UserClass userClass = (UserClass) getItem(position);

        ImageView imgClass = convertView.findViewById(R.id.imgClass);
        TextView lblClass = convertView.findViewById(R.id.lblClass);

        lblClass.setTypeface(AppUtils.getRegularTypeface(context));

        imgClass.setImageResource(R.drawable.class_icon);
        lblClass.setText(userClass.getName());

        convertView.setTag(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChildListActivity.class);
                StaticData.selectedClass = (UserClass) getItem((Integer) view.getTag());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}