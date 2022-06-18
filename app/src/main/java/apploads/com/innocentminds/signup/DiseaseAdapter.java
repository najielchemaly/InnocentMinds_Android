package apploads.com.innocentminds.signup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.globalvariables.GlobalData;
import apploads.com.innocentminds.helpers.utils.AppUtils;

//import com.squareup.picasso.Picasso;

public class DiseaseAdapter extends BaseAdapter {

    private List<GlobalData> root;
    private Context context;
    private LayoutInflater mInflater;
    private int userPosition;
    private List<String> diseasesList = new ArrayList<>();
    private EditChildProfileStepFourActivity editChildProfileStepFourActivity;

    public DiseaseAdapter(List<GlobalData> root, Context context, EditChildProfileStepFourActivity editChildProfileStepFourActivity) {
        this.root = root;
        this.context = context;
        this.editChildProfileStepFourActivity = editChildProfileStepFourActivity;
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
        final GlobalData disease = (GlobalData) getItem(position);
        convertView = mInflater.inflate(R.layout.disease_row_item, null);

        TextView txtDiseaseName = convertView.findViewById(R.id.txtDiseaseName);
        final ImageView imgCheck = convertView.findViewById(R.id.imgCheck);

        txtDiseaseName.setTypeface(AppUtils.getRegularTypeface(context));
        txtDiseaseName.setText(disease.getTitle());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (diseasesList.contains(disease.getTitle())){
                    imgCheck.setImageResource(R.drawable.unchecked_icon);
                    diseasesList.remove(disease.getTitle());
                    editChildProfileStepFourActivity.getDiseases(diseasesList);
                }else {
                    imgCheck.setImageResource(R.drawable.checked_icon);
                    diseasesList.add(disease.getTitle());
                    editChildProfileStepFourActivity.getDiseases(diseasesList);
                }
            }
        });

        return convertView;
    }
}