package apploads.com.innocentminds.teacher;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.user.ChildActivity;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.objects.Activity;

//import com.squareup.picasso.Picasso;

public class TeacherActivityAdapter extends BaseAdapter {

    private List<ChildActivity> root;
    private Context context;
    private LayoutInflater mInflater;
    private int userPosition;

    public TeacherActivityAdapter(List<ChildActivity> root, Context context) {
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
        final Activity activity = (Activity) getItem(position);
        convertView = mInflater.inflate(R.layout.teacher_activity_row_item, null);


        ImageView imgBanner = convertView.findViewById(R.id.imgBanner);
        ImageView imgType = convertView.findViewById(R.id.imgType);
        TextView txtTitle = convertView.findViewById(R.id.txtTitle);
        TextView lblSelectedStudents = convertView.findViewById(R.id.lblSelectedStudents);
        TextView lblViewImages = convertView.findViewById(R.id.lblViewImages);
        TextView lblEdit = convertView.findViewById(R.id.lblEdit);
        LinearLayout viewEdit = convertView.findViewById(R.id.viewEdit);
        TextView lblDelete = convertView.findViewById(R.id.lblDelete);

        txtTitle.setText(activity.getTitle());

        txtTitle.setTypeface(AppUtils.getRegularTypeface(context));
        lblSelectedStudents.setTypeface(AppUtils.getRegularTypeface(context));
        lblViewImages.setTypeface(AppUtils.getRegularTypeface(context));
        lblEdit.setTypeface(AppUtils.getRegularTypeface(context));
        lblDelete.setTypeface(AppUtils.getRegularTypeface(context));

        viewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddAgendaActivity.class);
                intent.putExtra("title", activity.getTitle());
                intent.putExtra("studentIds", (Serializable) activity.getSelectedChilds());
                context.startActivity(intent);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }
}