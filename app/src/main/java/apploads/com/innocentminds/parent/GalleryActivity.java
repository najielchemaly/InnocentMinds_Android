package apploads.com.innocentminds.parent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.user.ChildActivity;
import apploads.com.innocentminds.databaseobjects.user.Photo;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;

public class GalleryActivity extends BaseActivity {

    GalleryAdapter galleryAdapter;
    TextView lblGallery;
    Button btnClose;
    GridView galleryView;
    Button btnCloseImage;
    ImageView image;
    TextView lblShare;
    RelativeLayout viewImage;
    ImageView imgFacebook, imgInsta;
    boolean isFromDashboard = false;
    ChildActivity childActivity;

    @Override
    public int getContentViewId() {
        return R.layout.gallery_activity;
    }

    @Override
    public void doOnCreate() throws Exception {

        galleryView = _findViewById(R.id.galleryView);
        lblGallery = _findViewById(R.id.lblGallery);
        btnClose = _findViewById(R.id.btnClose);
        btnCloseImage = _findViewById(R.id.btnCloseImage);
        image = _findViewById(R.id.image);
        lblShare = _findViewById(R.id.lblShare);
        viewImage = _findViewById(R.id.viewImage);
        imgFacebook = _findViewById(R.id.imgFacebook);
        imgInsta = _findViewById(R.id.imgInsta);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            isFromDashboard = bundle.getBoolean("isFromDashboard");
            childActivity = (ChildActivity) bundle.getSerializable("activity");
        }

        if (isFromDashboard) {
            if(childActivity != null){
                galleryView.setAdapter(new GalleryAdapter(this, this, childActivity.getPhotos()));
            }
        } else {
            List<ChildActivity> childActivities = StaticData.selectedChild.getActivities();
            List<Photo> photos = new ArrayList<>();

            for (ChildActivity childActivity : childActivities) {
                if (childActivity.getPhotos() != null && childActivity.getPhotos().size() > 0) {
                    for (int i = 0; i < childActivity.getPhotos().size(); i++) {
                        photos.add(childActivity.getPhotos().get(i));
                    }
                }
            }
            galleryView.setAdapter(new GalleryAdapter(this, this, photos));
        }

        lblGallery.setTypeface(AppUtils.getTitleRegularFont(this));
        btnClose.setTypeface(AppUtils.getRegularTypeface(this));
        lblShare.setTypeface(AppUtils.getRegularTypeface(this));
        btnCloseImage.setTypeface(AppUtils.getRegularTypeface(this));

        viewImage.setAlpha(0f);
        viewImage.setVisibility(View.GONE);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCloseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewImage.setAlpha(0f);
                viewImage.setVisibility(View.GONE);
            }
        });

        lblShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = getBitmapFromView(image);
                try {
                    File file = new File(getExternalCacheDir(), "gallery.png");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);
                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    intent.setType("image/png");
                    startActivity(Intent.createChooser(intent, "Share image via"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        imgInsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                if (intent != null) {
                    Bitmap bitmap = getBitmapFromView(image);
                    try {
                        File file = new File(getExternalCacheDir(), "gallery.png");
                        FileOutputStream fOut = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                        fOut.flush();
                        fOut.close();
                        file.setReadable(true, false);
                        final Intent intent2 = new Intent(android.content.Intent.ACTION_SEND);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent2.setPackage("com.instagram.android");
                        intent2.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                        intent2.setType("image/png");
                        startActivity(intent2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("market://details?id=" + "com.instagram.android"));
                    startActivity(intent);
                }
            }
        });

        imgFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        } else {
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    public void showImage(int image) {
        this.image.setImageResource(image);

        viewImage.setVisibility(View.VISIBLE);
        viewImage.animate().alpha(1f).setDuration(800);
    }
}
