package apploads.com.innocentminds.teacher;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.objects.Child;
import apploads.com.innocentminds.parent.GalleryAdapter;

public class AddPhotosActivity extends BaseActivity {

    AddPhotosAdapter addPhotosAdapter;
    TextView lblAddPhotos;
    Button btnClose, btnConfirm;
    GridView galleryView;
    List<Bitmap> imagesList = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.add_photos_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        galleryView = _findViewById(R.id.galleryView);
        lblAddPhotos = _findViewById(R.id.lblAddPhotos);
        btnClose = _findViewById(R.id.btnClose);
        btnConfirm = _findViewById(R.id.btnConfirm);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if (b != null) {
            imagesList =  (List<Bitmap>) b.getSerializable("images");
            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.add_more);
            imagesList.add(icon);

            galleryView.setAdapter(new AddPhotosAdapter(this, this, imagesList));
        }

        lblAddPhotos.setTypeface(AppUtils.getTitleRegularFont(this));
        btnClose.setTypeface(AppUtils.getRegularTypeface(this));
        btnConfirm.setTypeface(AppUtils.getRegularTypeface(this));

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }
}
