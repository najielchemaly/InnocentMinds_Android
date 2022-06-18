package apploads.com.innocentminds.teacher;

import android.content.Context;
import android.content.Intent;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.databaseobjects.user.Child;
import de.hdodenhof.circleimageview.CircleImageView;

//import com.squareup.picasso.Picasso;

public class ChildTeacherAdapter extends BaseAdapter {

    private List<Child> root;
    private Context context;
    private LayoutInflater mInflater;
    private int userPosition;

    public ChildTeacherAdapter(List<Child> root, Context context) {
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
        final Child child = (Child) getItem(position);
        convertView = mInflater.inflate(R.layout.children_row_item, null);

        CircleImageView imgChild = convertView.findViewById(R.id.imgChild);
        TextView txtName = convertView.findViewById(R.id.txtName);

        txtName.setTypeface(AppUtils.getBoldTypeface(context));
        imgChild.setColorFilter(ContextCompat.getColor(context, R.color.greydialogue), android.graphics.PorterDuff.Mode.MULTIPLY);
//        imgChild.setImageResource(child.getImage());

        if (child.getFirstname() != null && child.getLastname() != null) {
            txtName.setText(child.getFirstname()+" "+child.getLastname());
        }

        convertView.setTag(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.selectedChild = (Child) getItem((Integer) view.getTag());
                Intent intent = new Intent(context, ChildDailyAgendaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}