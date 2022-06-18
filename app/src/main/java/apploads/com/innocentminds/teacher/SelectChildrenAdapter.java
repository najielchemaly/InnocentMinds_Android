package apploads.com.innocentminds.teacher;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.databaseobjects.user.Child;
import de.hdodenhof.circleimageview.CircleImageView;


public class SelectChildrenAdapter extends BaseAdapter {

    private List<Child> root;
    private Context context;
    private LayoutInflater mInflater;
    private List<Child> selectedChilds = new ArrayList<>();

    public SelectChildrenAdapter(List<Child> root, Context context) {
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
        convertView = mInflater.inflate(R.layout.select_children_row_item, null);


        CircleImageView imgChild = convertView.findViewById(R.id.imgChild);
        imgChild.setColorFilter(ContextCompat.getColor(context, R.color.greydialogue), android.graphics.PorterDuff.Mode.MULTIPLY);

        TextView txtName = convertView.findViewById(R.id.txtName);
        final ImageView imgCheck = convertView.findViewById(R.id.imgCheck);

        txtName.setTypeface(AppUtils.getRegularTypeface(context));

        if (child.getFirstname() != null && child.getLastname() != null) {
            txtName.setText(child.getFirstname()+" "+child.getLastname());
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgCheck.setImageResource(R.drawable.checked_icon);
                selectedChilds.add(child);
            }
        });

        return convertView;
    }

    public List<Child> getSelectedChilds() {
        return selectedChilds;
    }
}