package apploads.com.innocentminds;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import apploads.com.innocentminds.databaseobjects.globalvariables.PhoneNumber;
import apploads.com.innocentminds.helpers.utils.AppUtils;

//import com.squareup.picasso.Picasso;

public class PhoneNumbersAdapter extends BaseAdapter {

    private List<PhoneNumber> root;
    private Context context;
    private LayoutInflater mInflater;
    private int userPosition;
    private int region;

    public PhoneNumbersAdapter(List<PhoneNumber> root, Context context, int region) {
        this.root = root;
        this.context = context;
        this.region = region;
        if (context != null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    public void reloadData(List<PhoneNumber> root, int region) {
        this.root = root;
        this.region = region;
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
        final PhoneNumber phone = (PhoneNumber) getItem(position);
        convertView = mInflater.inflate(R.layout.phone_number_row_item, null);

        TextView txtNumber = convertView.findViewById(R.id.txtNumber);
        RelativeLayout viewParent = convertView.findViewById(R.id.viewParent);
        Button btnCallus = convertView.findViewById(R.id.btnCallus);

        if(region == 1){
            viewParent.setBackground(ContextCompat.getDrawable(context, R.drawable.number_view_green));
        }else if (region == 2){
            viewParent.setBackground(ContextCompat.getDrawable(context, R.drawable.number_view_red));
        }else {
            viewParent.setBackground(ContextCompat.getDrawable(context, R.drawable.number_view_blue));
        }

        txtNumber.setTypeface(AppUtils.getRegularTypeface(context));
        btnCallus.setTypeface(AppUtils.getRegularTypeface(context));
        txtNumber.setText(phone.getText());


        btnCallus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone.getText(), null));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}