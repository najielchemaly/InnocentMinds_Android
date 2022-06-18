package apploads.com.innocentminds.homepage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.user.Child;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import de.hdodenhof.circleimageview.CircleImageView;

//import com.squareup.picasso.Picasso;

public class ChangeChildAdapter extends BaseAdapter {

    private List<Child> root;
    private Context context;
    private HomepageActivity homepageActivity;
    private LayoutInflater mInflater;
    private int userPosition;

    public ChangeChildAdapter(List<Child> root, Context context, HomepageActivity homepageActivity) {
        this.root = root;
        this.context = context;
        this.homepageActivity = homepageActivity;
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
        convertView = mInflater.inflate(R.layout.change_child_row_item, null);


        CircleImageView imgChild = convertView.findViewById(R.id.imgChild);
        TextView txtChild = convertView.findViewById(R.id.txtChild);

        txtChild.setTypeface(AppUtils.getRegularTypeface(context));

        if (child.getImage() != null) {
            // URLString = ApiManager.getMediaUrl()+child.getImage();
            //        imgChild.setImageResource(child.getImage());
        }

        if (child.getFirstname() != null && child.getLastname() != null) {
            txtChild.setText(child.getFirstname()+" "+child.getLastname());
        }

        imgChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homepageActivity.selectedChild = child;
                homepageActivity.changeChildPressed();
            }
        });

        return convertView;
    }
}