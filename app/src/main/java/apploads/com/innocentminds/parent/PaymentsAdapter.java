package apploads.com.innocentminds.parent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.payment.Payment;
import apploads.com.innocentminds.databaseobjects.user.Child;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.services.ApiManager;

//import com.squareup.picasso.Picasso;

public class PaymentsAdapter extends BaseAdapter {

    public List<Payment> root;
    private Context context;
    private LayoutInflater mInflater;

    String selectedButton = "1";

    public PaymentsAdapter(List<Payment> root, Context context) {
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
        final Payment payment = (Payment) getItem(position);
        convertView = mInflater.inflate(R.layout.fees_row_item, null);

        ImageView imgChild = convertView.findViewById(R.id.imgChild);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtAmount = convertView.findViewById(R.id.txtAmount);
        TextView txtDate = convertView.findViewById(R.id.txtDate);
        TextView lblAmount = convertView.findViewById(R.id.lblAmount);
        TextView lblDate = convertView.findViewById(R.id.lblDate);

        txtName.setTypeface(AppUtils.getRegularTypeface(context));
        txtAmount.setTypeface(AppUtils.getBoldTypeface(context));
        txtDate.setTypeface(AppUtils.getBoldTypeface(context));

        if (StaticData.user.getChildren() != null) {
            for (Child child : StaticData.user.getChildren()) {
                if (child.getId().toString().equals(payment.getChild_id())) {
                    if (child.getImage() != null) {
                        // URLString = ApiManager.getMediaUrl()+child.getImage();
//                        imgChild.setImageResource();
                    }
                    if (child.getFirstname() != null && child.getLastname() != null) {
                        txtName.setText(child.getFirstname()+" "+child.getLastname());
                    }

                    break;
                }
            }
        }

        lblAmount.setText("1".equals(selectedButton) ? context.getResources().getText(R.string.paymentamount) :
                context.getResources().getText(R.string.feeamount));
        lblDate.setText("1".equals(selectedButton) ? context.getResources().getText(R.string.paymentdate) :
                context.getResources().getText(R.string.duedate));

        txtAmount.setText(payment.getAmount());
        txtDate.setText(payment.getDate());

        return convertView;
    }
}