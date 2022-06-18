package apploads.com.innocentminds.signup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.globalvariables.GlobalData;
import apploads.com.innocentminds.databaseobjects.user.Child;
import apploads.com.innocentminds.helpers.BaseActivity;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.homepage.HomepageActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditChildProfileStepOneActivity extends BaseActivity {

    TextView lblPersonalInfo, lblChildImage, txtDob, txtPlaceOfBirth;
    NiceSpinner spinnerGender, spinnerTransportation, spinnerProgramLang, spinnerHomeLang;
    Button btnNext, btnCancel;
    EditText txtFirstName, txtFathersName, txtLastname;
    CircleImageView imgChild;
    LinearLayout focus;
    private Calendar calendar;
    private int year, month, day;

    private int GALLERY = 1, CAMERA = 2;

    private String genderHint;
    private String homeLanguageHint;
    private String programLanguageHint;
    private String transportationHint;

    List<GlobalData> gendersList = new ArrayList<>();
    List<GlobalData> homeLanguagesList = new ArrayList<>();
    List<GlobalData> programLanguagesList = new ArrayList<>();
    List<GlobalData> transportationsList = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.edit_child_profile_step_one;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView(){
        genderHint = getResources().getString(R.string.gender);
        homeLanguageHint = getResources().getString(R.string.home_language);
        programLanguageHint = getResources().getString(R.string.program_language);
        transportationHint = getResources().getString(R.string.transportation);

        lblPersonalInfo = _findViewById(R.id.lblPersonalInfo);
        lblChildImage = _findViewById(R.id.lblChildImage);
        txtDob = _findViewById(R.id.txtDob);
        spinnerGender = _findViewById(R.id.spinnerGender);
        btnNext = _findViewById(R.id.btnNext);
        btnCancel = _findViewById(R.id.btnCancel);
        txtFirstName = _findViewById(R.id.txtFirstName);
        txtFathersName = _findViewById(R.id.txtFathersName);
        txtLastname = _findViewById(R.id.txtLastname);
        txtPlaceOfBirth = _findViewById(R.id.txtPlaceOfBirth);
        spinnerHomeLang = _findViewById(R.id.spinnerHomeLang);
        spinnerProgramLang = _findViewById(R.id.spinnerProgramLang);
        spinnerTransportation = _findViewById(R.id.spinnerTransportation);
        imgChild = _findViewById(R.id.imgChild);
        focus = _findViewById(R.id.focus);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        if (StaticData.variables.getGenders() != null) {
            gendersList = StaticData.variables.getGenders();
            gendersList.add(0, new GlobalData(genderHint));
        }
        if (StaticData.variables.getHome_languages() != null) {
            homeLanguagesList = StaticData.variables.getHome_languages();
            homeLanguagesList.add(0, new GlobalData(homeLanguageHint));
        }
        if (StaticData.variables.getProgram_languages() != null) {
            programLanguagesList = StaticData.variables.getProgram_languages();
            programLanguagesList.add(0, new GlobalData(programLanguageHint));
        }
        if (StaticData.variables.getTransportations() != null) {
            transportationsList = StaticData.variables.getTransportations();
            transportationsList.add(0, new GlobalData(transportationHint));
        }

        spinnerGender.attachDataSource(gendersList);
        spinnerHomeLang.attachDataSource(homeLanguagesList);
        spinnerProgramLang.attachDataSource(programLanguagesList);
        spinnerTransportation.attachDataSource(transportationsList);

        focus.requestFocus();

        StaticData.tempChild = StaticData.selectedChild;

        setupChildInfo();
    }

    private void styleView(){
        lblPersonalInfo.setTypeface(AppUtils.getTitleRegularFont(this));
        lblChildImage.setTypeface(AppUtils.getRegularTypeface(this));
        txtDob.setTypeface(AppUtils.getRegularTypeface(this));
        spinnerGender.setTypeface(AppUtils.getRegularTypeface(this));
        btnNext.setTypeface(AppUtils.getRegularTypeface(this));
        btnCancel.setTypeface(AppUtils.getRegularTypeface(this));
        txtFirstName.setTypeface(AppUtils.getRegularTypeface(this));
        txtFathersName.setTypeface(AppUtils.getRegularTypeface(this));
        txtLastname.setTypeface(AppUtils.getRegularTypeface(this));
    }

    private void initListeners(){
        imgChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        lblChildImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();
            }
        });

        txtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(999);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTempChildInfo();

                Intent intent = new Intent(getApplicationContext(), EditChildProfileStepThreeActivity.class);
                startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.shouldFinish = true;
                finish();
            }
        });
    }

    void setupChildInfo() {
        txtFirstName.setText(StaticData.selectedChild.getFirstname());
        txtFathersName.setText(StaticData.selectedChild.getFathername());
        txtLastname.setText(StaticData.selectedChild.getLastname());
        spinnerGender.setText(StaticData.selectedChild.getGender_id() == null ? spinnerGender.getText() : StaticData.selectedChild.getGender_id());
        txtDob.setText(StaticData.selectedChild.getDate_of_birth());
        txtPlaceOfBirth.setText(StaticData.selectedChild.getPlace_of_birth());
        spinnerHomeLang.setText(StaticData.selectedChild.getHome_language_id() == null ? spinnerHomeLang.getText() : StaticData.selectedChild.getHome_language_id());
        spinnerProgramLang.setText(StaticData.selectedChild.getDesired_langugage_id() == null ? spinnerProgramLang.getText() : StaticData.selectedChild.getDesired_langugage_id());
        spinnerTransportation.setText(StaticData.selectedChild.getTransp_id() == null ? spinnerTransportation.getText() : StaticData.selectedChild.getTransp_id());
    }

    void updateTempChildInfo() {
        StaticData.tempChild.setFirstname(txtFirstName.getText() == null ? "" : txtFirstName.getText().toString());
        StaticData.tempChild.setFathername(txtFathersName.getText() == null ? "" : txtFathersName.getText().toString());
        StaticData.tempChild.setLastname(txtLastname.getText() == null ? "" : txtLastname.getText().toString());
        StaticData.tempChild.setGender_id(spinnerGender.getText() == null ? "" : spinnerGender.getText().equals(genderHint) ? "" : spinnerGender.getText().toString());
        StaticData.tempChild.setDate_of_birth(txtDob.getText() == null ? "" : txtDob.getText().toString());
        StaticData.tempChild.setPlace_of_birth(txtPlaceOfBirth.getText() == null ? "" : txtPlaceOfBirth.getText().toString());
        StaticData.tempChild.setHome_language_id(spinnerHomeLang.getText() == null ? "" : spinnerHomeLang.getText().equals(homeLanguageHint) ? "" : spinnerHomeLang.getText().toString());
        StaticData.tempChild.setDesired_langugage_id(spinnerProgramLang.getText() == null ? "" : spinnerProgramLang.getText().equals(programLanguageHint) ? "" : spinnerProgramLang.getText().toString());
        StaticData.tempChild.setTransp_id(spinnerTransportation.getText() == null ? "" : spinnerTransportation.getText().equals(transportationHint) ? "" : spinnerTransportation.getText().toString());
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        txtDob.setText(new StringBuilder().append(year).append("-")
                .append(month).append("-").append(day));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
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
                    imgChild.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(EditChildProfileStepOneActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imgChild.setImageBitmap(thumbnail);

        }
    }
}
