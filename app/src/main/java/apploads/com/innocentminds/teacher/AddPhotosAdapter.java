package apploads.com.innocentminds.teacher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.parent.GalleryActivity;

public class AddPhotosAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private AddPhotosActivity addPhotosActivity;
    private List<Bitmap> imagesList = new ArrayList<>();

    // Constructor
    public AddPhotosAdapter(Context context, AddPhotosActivity addPhotosActivity, List<Bitmap> imagesList) {
        this.context = context;
        this.imagesList = imagesList;
        this.addPhotosActivity = addPhotosActivity;
        if (context != null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    public int getCount() {
        return imagesList.size();
    }

    public Object getItem(int position) {
        return imagesList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.add_photos_row_item, null);

        Bitmap image = (Bitmap) getItem(position);

        ImageView imgGallery = convertView.findViewById(R.id.imgGallery);
        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);

        if (position == imagesList.size() - 1){
            btnDelete.setVisibility(View.GONE);
        }
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        imgGallery.setImageBitmap(image);
        return convertView;
    }
}