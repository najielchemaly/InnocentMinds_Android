package apploads.com.innocentminds.teacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.user.ChildActivity;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.objects.Activity;
import apploads.com.innocentminds.objects.Child;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class AddAgendaActivity extends BaseActivity {

    Button btnCancel, btnSave;
    TextView lblAddActivity, lblSelectStudent, lblAddImages, txtImageCount;
    EditText txtTitle;
    private int GALLERY = 1, CAMERA = 2, STUDENTSIDS = 3;
    ImageView imgPicked;
    LinearLayout viewImages;
    int imageCount;
    ImageView imgAddImages;
    String studentIds;
    List<Bitmap> imagesList = new ArrayList<>();
    List<Child> childsSelected = new ArrayList<>();
    List<Child> childs = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.add_agenda_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView(){
        btnCancel = _findViewById(R.id.btnCancel);
        btnSave = _findViewById(R.id.btnSave);
        lblAddActivity = _findViewById(R.id.lblAddActivity);
        lblSelectStudent = _findViewById(R.id.lblSelectStudent);
        lblAddImages = _findViewById(R.id.lblAddImages);
        txtTitle = _findViewById(R.id.txtTitle);
        imgPicked = _findViewById(R.id.imgPicked);
        txtImageCount = _findViewById(R.id.txtImageCount);
        viewImages = _findViewById(R.id.viewImages);
        imgAddImages = _findViewById(R.id.imgAddImages);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            txtTitle.setText(bundle.getString("title"));
            childs =  (List<Child>) bundle.getSerializable("studentIds");
        }

        viewImages.setVisibility(View.GONE);
    }

    private void styleView(){
        btnSave.setTypeface(AppUtils.getBoldTypeface(this));
        lblAddActivity.setTypeface(AppUtils.getBoldTypeface(this));
        lblSelectStudent.setTypeface(AppUtils.getRegularTypeface(this));
        txtTitle.setTypeface(AppUtils.getRegularTypeface(this));
        lblAddImages.setTypeface(AppUtils.getRegularTypeface(this));
    }

    private void initListeners(){

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO NAJI
                ChildActivity activity = new ChildActivity();
                activity.setTitle(txtTitle.getText().toString());
                activity.setStudent_ids(studentIds);
//                activity.setPhotos();

                StaticData.additionalActivities.add(activity);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        lblSelectStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectChildrenActivity.class);
                intent.putExtra("studentIds",(Serializable) childs);
                startActivityForResult(intent, STUDENTSIDS);
            }
        });

        imgPicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddPhotosActivity.class);
                intent.putExtra("images",(Serializable) imagesList);
                startActivity(intent);
            }
        });

        lblAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        imgAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
//                    String path = saveImage(bitmap);
                    imgPicked.setImageBitmap(bitmap);
                    viewImages.setVisibility(View.VISIBLE);
                    lblAddImages.setVisibility(View.GONE);
                    txtImageCount.setText(String.valueOf(++imageCount));
                    imagesList.add(getResizedBitmap(bitmap, 200));

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AddAgendaActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imgPicked.setImageBitmap(thumbnail);
            viewImages.setVisibility(View.VISIBLE);
            lblAddImages.setVisibility(View.GONE);
            txtImageCount.setText(String.valueOf(++imageCount));
            imagesList.add(getResizedBitmap(thumbnail, 200));
//            saveImage(thumbnail);
        }else if(requestCode == STUDENTSIDS){
            studentIds = data.getStringExtra("studentIds");

            childsSelected = (List<Child>) data.getSerializableExtra("studentIds");
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
