package apploads.com.innocentminds.homepage;

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.user.ChildActivity;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.parent.GalleryActivity;
import apploads.com.innocentminds.teacher.AddDailyAgendaActivity;

public class DashboardAdapter extends BaseAdapter {

    private List<ChildActivity> root;
    private Context context;
    private LayoutInflater mInflater;
    private int userPosition;

    public DashboardAdapter(List<ChildActivity> root, Context context) {
        this.root = root;
        this.context = context;
        if (context != null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    public void reloadData(List<ChildActivity> root) {
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
        final ChildActivity activity = (ChildActivity) getItem(position);
        convertView = mInflater.inflate(R.layout.dashboard_row_item, null);

        ImageView imgBanner = convertView.findViewById(R.id.imgBanner);
        ImageView imgType = convertView.findViewById(R.id.imgType);
        ImageView imgStar1 = convertView.findViewById(R.id.imgStar1);
        ImageView imgStar2 = convertView.findViewById(R.id.imgStar2);
        ImageView imgStar3 = convertView.findViewById(R.id.imgStar3);
        ImageView imgStar4 = convertView.findViewById(R.id.imgStar4);
        LinearLayout viewStars = convertView.findViewById(R.id.viewStars);
        LinearLayout viewButtons = convertView.findViewById(R.id.viewButtons);
        LinearLayout viewEdit = convertView.findViewById(R.id.viewEdit);
        LinearLayout viewDelete = convertView.findViewById(R.id.viewDelete);
        TextView txtTitle = convertView.findViewById(R.id.txtTitle);
        TextView txtDescription = convertView.findViewById(R.id.txtDescription);
        TextView txtViewImages = convertView.findViewById(R.id.txtViewImages);
        CardView viewActivity = convertView.findViewById(R.id.viewActivity);

        viewStars.setVisibility(View.GONE);
        txtViewImages.setVisibility(View.GONE);
        viewButtons.setVisibility(View.GONE);

        drawStars(activity.getRating(), imgStar1, imgStar2, imgStar3, imgStar4);
        drawBanner(activity.getType_id(), imgBanner, txtTitle);

        switch (activity.getType_id()){
            case (StaticData.BREAKFAST):
            case (StaticData.LUNCH):
                imgType.setImageResource(R.drawable.food_icon);
                viewStars.setVisibility(View.VISIBLE);
                txtDescription.setText(activity.getDescription());
                break;
            case (StaticData.NAP):
                imgType.setImageResource(R.drawable.nap_icon);
                txtDescription.setText("From " + activity.getFrom_date() + " till " + activity.getTo_date());
                break;
            case (StaticData.BATHROOM):
                imgType.setImageResource(R.drawable.bath_icon);
                txtDescription.setText(activity.getDescription());
                break;
            case (StaticData.POTTY):
                imgType.setImageResource(R.drawable.potty_training_icon);
                txtDescription.setText("At " + activity.getTime());
                break;
            case (StaticData.ADDITIONAL):
                imgType.setImageResource(R.drawable.outing_icon);
                txtViewImages.setVisibility(View.VISIBLE);
                float height = 80 * context.getResources().getDisplayMetrics().density;
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) height);
                viewActivity.setLayoutParams(params);
                float padding = 10 * context.getResources().getDisplayMetrics().density;
                txtTitle.setPadding(0, (int) padding, 0, 0);

                break;
            default:
                break;
        }

        if (StaticData.user.getRole_id() != null) {
            if (StaticData.user.getRole_id().toString().equals(StaticData.TEACHER) ||
                    StaticData.user.getRole_id().toString().equals(StaticData.TEACHER_SUPERVISOR)) {
                txtViewImages.setVisibility(View.GONE);
                viewButtons.setVisibility(View.VISIBLE);
            }
        }

        txtTitle.setText(activity.getTitle());

        viewEdit.setTag(position);

        txtViewImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GalleryActivity.class);
                intent.putExtra("isFromDashboard",true);
                intent.putExtra("activity",(Serializable) activity);
                context.startActivity(intent);
            }
        });

        viewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.selectedDailyAgenda = (ChildActivity) getItem((Integer) view.getTag());
                Intent intent = new Intent(context, AddDailyAgendaActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("editMode", true);
                context.startActivity( intent);
            }
        });

        return convertView;
    }

    private void drawStars(String ranking, ImageView star1, ImageView star2, ImageView star3, ImageView star4) {
        switch (ranking) {
            case "0":
                star1.setImageResource(R.drawable.start_unselected);
                star2.setImageResource(R.drawable.start_unselected);
                star3.setImageResource(R.drawable.start_unselected);
                star4.setImageResource(R.drawable.start_unselected);
                break;
            case "1":
                star1.setImageResource(R.drawable.star_selected);
                star2.setImageResource(R.drawable.start_unselected);
                star3.setImageResource(R.drawable.start_unselected);
                star4.setImageResource(R.drawable.start_unselected);
                break;
            case "2":
                star1.setImageResource(R.drawable.star_selected);
                star2.setImageResource(R.drawable.star_selected);
                star3.setImageResource(R.drawable.start_unselected);
                star4.setImageResource(R.drawable.start_unselected);
                break;
            case "3":
                star1.setImageResource(R.drawable.star_selected);
                star2.setImageResource(R.drawable.star_selected);
                star3.setImageResource(R.drawable.star_selected);
                star4.setImageResource(R.drawable.start_unselected);
                break;
            case "4":
                star1.setImageResource(R.drawable.star_selected);
                star2.setImageResource(R.drawable.star_selected);
                star3.setImageResource(R.drawable.star_selected);
                star4.setImageResource(R.drawable.star_selected);
                break;

            default:
                break;
        }
    }

    private void drawBanner(String type, ImageView banner, TextView title){
        switch (type){
            case (StaticData.BREAKFAST):
                banner.setBackgroundColor(context.getResources().getColor(R.color.orange));
                title.setTextColor(context.getResources().getColor(R.color.orange));
                break;
            case (StaticData.LUNCH):
                banner.setBackgroundColor(context.getResources().getColor(R.color.orange));
                title.setTextColor(context.getResources().getColor(R.color.orange));
                break;
            case (StaticData.NAP):
                banner.setBackgroundColor(context.getResources().getColor(R.color.signinBlue));
                title.setTextColor(context.getResources().getColor(R.color.signinBlue));
                break;
            case (StaticData.BATHROOM):
                banner.setBackgroundColor(context.getResources().getColor(R.color.yellowDialogue));
                title.setTextColor(context.getResources().getColor(R.color.yellowDialogue));
                break;
            case (StaticData.POTTY):
                banner.setBackgroundColor(context.getResources().getColor(R.color.red));
                title.setTextColor(context.getResources().getColor(R.color.red));
                break;
            case (StaticData.ADDITIONAL):
                banner.setBackgroundColor(context.getResources().getColor(R.color.purple));
                title.setTextColor(context.getResources().getColor(R.color.purple));
                break;
             default:
                 break;
        }
    }
}