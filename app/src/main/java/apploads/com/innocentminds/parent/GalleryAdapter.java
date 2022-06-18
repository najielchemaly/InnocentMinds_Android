package apploads.com.innocentminds.parent;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.user.Photo;
import apploads.com.innocentminds.helpers.StaticData;

public class GalleryAdapter extends BaseAdapter {
    private Context context;
    private Integer[] images;
    private List<Photo> photos;
    private LayoutInflater mInflater;
    private GalleryActivity galleryActivity;

    // Constructor
    public GalleryAdapter(Context context, GalleryActivity galleryActivity, List<Photo> photos) {
        this.context = context;
        this.images = images;
        this.photos = photos;
        this.galleryActivity = galleryActivity;
        if (context != null) {
            mInflater = LayoutInflater.from(context);
        }
    }

    public int getCount() {
        return photos.size();
    }

    public Object getItem(int position) {
        return photos.get(position);
    }

    public long getItemId(int position) {
        return (long) Math.random();
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.gallery_row_item, null);

        final Photo photo = (Photo) getItem(position);

        ImageView imgGallery = convertView.findViewById(R.id.imgGallery);
        imgGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryActivity.showImage(R.drawable.gallery_dummy);
            }
        });

        Glide.with(context)
                .load(StaticData.variables.getConfig().getMedia_url() + photo.getImage())
                .into(imgGallery);

        return convertView;
    }
}