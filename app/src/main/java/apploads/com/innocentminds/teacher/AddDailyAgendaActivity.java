package apploads.com.innocentminds.teacher;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

//import com.jaredrummler.materialspinner.MaterialSpinner;

import org.angmarch.views.NiceSpinner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.user.ChildActivity;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;

public class AddDailyAgendaActivity extends BaseActivity {

    Button btnCancel, btnSave;
    NiceSpinner dropdownAgenda;
    EditText txtLunchDesc;
    CardView viewBreakfast, viewPotty, viewNap, viewLunch, viewBathroom;
    ImageView imgStarBreakfast1, imgStarBreakfast2, imgStarBreakfast3, imgStarBreakfast4, imgBreakfastSad, imgLunchSad,
            imgStarLunch1, imgStarLunch2, imgStarLunch3, imgStarLunch4;
    TextView lblBreakfastTitle, txtBreakfastDesc, lblNap, lblAddActivity, txtNapFrom, txtNapTo, lblPottyTime, lblLunchTitle, txtBathroomTime;
    NiceSpinner spinnerType1, spinnerType2;

    boolean isBreakfastSad, isLunchSad, isEditMode;
    private int mHour, mMinute;
    private int breakfastRank, lunchRank;

    @Override
    public int getContentViewId() {
        return R.layout.add_daily_agenda_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView(){
        btnCancel = _findViewById(R.id.btnCancel);
        btnSave = _findViewById(R.id.btnSave);
        lblAddActivity = _findViewById(R.id.lblAddActivity);
        dropdownAgenda = _findViewById(R.id.dropdownAgenda);
        viewBreakfast = _findViewById(R.id.viewBreakfast);
        viewBathroom = _findViewById(R.id.viewBathroom);
        viewLunch = _findViewById(R.id.viewLunch);
        txtLunchDesc = _findViewById(R.id.txtLunchDesc);
        lblNap = _findViewById(R.id.lblNap);
        viewNap = _findViewById(R.id.viewNap);
        lblLunchTitle = _findViewById(R.id.lblLunchTitle);
        txtBathroomTime = _findViewById(R.id.txtBathroomTime);
        txtNapFrom = _findViewById(R.id.txtNapFrom);
        txtNapTo = _findViewById(R.id.txtNapTo);
        imgLunchSad = _findViewById(R.id.imgLunchSad);
        imgStarBreakfast1 = _findViewById(R.id.imgStarBreakfast1);
        imgStarBreakfast2 = _findViewById(R.id.imgStarBreakfast2);
        imgStarBreakfast3 = _findViewById(R.id.imgStarBreakfast3);
        imgStarBreakfast4 = _findViewById(R.id.imgStarBreakfast4);
        imgStarLunch4 = _findViewById(R.id.imgStarLunch4);
        imgStarLunch3 = _findViewById(R.id.imgStarLunch3);
        imgStarLunch2 = _findViewById(R.id.imgStarLunch2);
        imgStarLunch1 = _findViewById(R.id.imgStarLunch1);
        imgBreakfastSad = _findViewById(R.id.imgBreakfastSad);
        lblPottyTime = _findViewById(R.id.lblPottyTime);
        viewPotty = _findViewById(R.id.viewPotty);
        lblBreakfastTitle = _findViewById(R.id.lblBreakfastTitle);
        txtBreakfastDesc = _findViewById(R.id.txtBreakfastDesc);
        spinnerType1 = _findViewById(R.id.spinnerType1);
        spinnerType2 = _findViewById(R.id.spinnerType2);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null && bundle.getBoolean("editMode", false)) {
            isEditMode = true;
        } else {
            isEditMode = false;
        }

        initSelectedView();
        setupActivityInfo();

        List<String> dataset = new LinkedList<>(Arrays.asList("Breakfast", "Bathroom", "Lunch", "Nap", "Potty"));
        dropdownAgenda.attachDataSource(dataset);
    }

    void initSelectedView() {
        if (isEditMode) {
            if (StaticData.selectedDailyAgenda.getType_id() != null) {
                switch (StaticData.selectedDailyAgenda.getType_id()) {
                    case "1":
                        showSelectedView(viewBreakfast);
                        break;
                    case "2":
                        showSelectedView(viewLunch);
                        break;
                    case "3":
                        showSelectedView(viewNap);
                        break;
                    case "4":
                        showSelectedView(viewBathroom);
                        break;
                    case "5":
                        showSelectedView(viewPotty);
                        break;
                }
            }
        } else {
            showSelectedView(viewBreakfast);
        }
    }

    void setupActivityInfo() {
        if (isEditMode) {
            if (StaticData.selectedDailyAgenda.getType_id() != null) {
                switch (StaticData.selectedDailyAgenda.getType_id()) {
                    case "1":
                        if (StaticData.selectedDailyAgenda.getRating() != null) {
                            Integer rating = Integer.parseInt(StaticData.selectedDailyAgenda.getRating());
                            drawStars(rating, imgStarBreakfast1, imgStarBreakfast2, imgStarBreakfast3, imgStarBreakfast4);
                        }
                        if (StaticData.selectedDailyAgenda.getDescription() != null) {
                            txtBreakfastDesc.setText(StaticData.selectedDailyAgenda.getDescription());
                        }
                        break;
                    case "2":
                        if (StaticData.selectedDailyAgenda.getRating() != null) {
                            Integer rating = Integer.parseInt(StaticData.selectedDailyAgenda.getRating());
                            drawStars(rating, imgStarLunch1, imgStarLunch2, imgStarLunch3, imgStarLunch4);
                        }
                        if (StaticData.selectedDailyAgenda.getDescription() != null) {
                            txtLunchDesc.setText(StaticData.selectedDailyAgenda.getDescription());
                        }
                        break;
                    case "3":
                        if (StaticData.selectedDailyAgenda.getFrom_date() != null) {
                            txtNapFrom.setText(StaticData.selectedDailyAgenda.getFrom_date());
                        }
                        if (StaticData.selectedDailyAgenda.getTo_date() != null) {
                            txtNapTo.setText(StaticData.selectedDailyAgenda.getTo_date());
                        }
                        break;
                    case "4":
                        if (StaticData.selectedDailyAgenda.getBath_type_id() != null) {
                            spinnerType1.setText(StaticData.selectedDailyAgenda.getBath_type_id());
                        }
                        if (StaticData.selectedDailyAgenda.getBath_potty_type_id() != null) {
                            spinnerType2.setText(StaticData.selectedDailyAgenda.getBath_potty_type_id());
                        }
                        if (StaticData.selectedDailyAgenda.getTime() != null) {
                            txtBathroomTime.setText(StaticData.selectedDailyAgenda.getBath_type_id());
                        }
                        break;
                    case "5":
                        if (StaticData.selectedDailyAgenda.getTime() != null) {
                            lblPottyTime.setText(StaticData.selectedDailyAgenda.getTime());
                        }
                        break;
                }
            }
        }
    }

    private void styleView(){
        dropdownAgenda.setTypeface(AppUtils.getBoldTypeface(this));
        btnSave.setTypeface(AppUtils.getRegularTypeface(this));
        lblBreakfastTitle.setTypeface(AppUtils.getRegularTypeface(this));
        txtBreakfastDesc.setTypeface(AppUtils.getRegularTypeface(this));
        lblPottyTime.setTypeface(AppUtils.getRegularTypeface(this));
    }

    private void initListeners(){
        txtBathroomTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimePickerToText(txtBathroomTime);
            }
        });

        txtNapFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimePickerToText(txtNapFrom);
            }
        });

        txtNapTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimePickerToText(txtNapTo);
            }
        });

        lblPottyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimePickerToText(lblPottyTime);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dropdownAgenda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (((AppCompatTextView) view).getText().toString()){
                    case "Breakfast":
                        showSelectedView(viewBreakfast);
                        break;
                    case "Potty":
                        showSelectedView(viewPotty);
                        break;
                    case "Lunch":
                        showSelectedView(viewLunch);
                        break;
                    case "Nap":
                        showSelectedView(viewNap);
                        break;
                    case "Bathroom":
                        showSelectedView(viewBathroom);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        imgBreakfastSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isBreakfastSad){
                    imgBreakfastSad.setImageResource(R.drawable.sad_face_icon);
                    isBreakfastSad = false;
                    drawStars(2,imgStarBreakfast1,imgStarBreakfast2,imgStarBreakfast3,imgStarBreakfast4);
                    breakfastRank = 2;
                }else {
                    imgBreakfastSad.setImageResource(R.drawable.sad_face_icon_selected);
                    isBreakfastSad = true;
                    drawStars(0,imgStarBreakfast1,imgStarBreakfast2,imgStarBreakfast3,imgStarBreakfast4);
                    breakfastRank = 0;
                }
            }
        });

        imgStarBreakfast1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawStars(1,imgStarBreakfast1,imgStarBreakfast2,imgStarBreakfast3,imgStarBreakfast4);
                imgBreakfastSad.setImageResource(R.drawable.sad_face_icon);
                isBreakfastSad = false;
                breakfastRank = 1;
            }
        });

        imgStarBreakfast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawStars(2,imgStarBreakfast1,imgStarBreakfast2,imgStarBreakfast3,imgStarBreakfast4);
                imgBreakfastSad.setImageResource(R.drawable.sad_face_icon);
                isBreakfastSad = false;
                breakfastRank = 2;
            }
        });

        imgStarBreakfast3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawStars(3,imgStarBreakfast1,imgStarBreakfast2,imgStarBreakfast3,imgStarBreakfast4);
                imgBreakfastSad.setImageResource(R.drawable.sad_face_icon);
                isBreakfastSad = false;
                breakfastRank = 3;
            }
        });

        imgStarBreakfast4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawStars(4,imgStarBreakfast1,imgStarBreakfast2,imgStarBreakfast3,imgStarBreakfast4);
                imgBreakfastSad.setImageResource(R.drawable.sad_face_icon);
                isBreakfastSad = false;
                breakfastRank = 4;
            }
        });

        imgLunchSad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isLunchSad){
                    imgLunchSad.setImageResource(R.drawable.sad_face_icon);
                    isLunchSad = false;
                    drawStars(2,imgStarLunch1,imgStarLunch2,imgStarLunch3,imgStarLunch4);
                    lunchRank = 2;
                }else {
                    imgLunchSad.setImageResource(R.drawable.sad_face_icon_selected);
                    isLunchSad = true;
                    drawStars(0,imgStarLunch1,imgStarLunch2,imgStarLunch3,imgStarLunch4);
                    lunchRank = 0;
                }
            }
        });

        imgStarLunch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawStars(1,imgStarLunch1,imgStarLunch2,imgStarLunch3,imgStarLunch4);
                imgLunchSad.setImageResource(R.drawable.sad_face_icon);
                isLunchSad = false;
                lunchRank = 1;
            }
        });

        imgStarLunch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawStars(2,imgStarLunch1,imgStarLunch2,imgStarLunch3,imgStarLunch4);
                imgLunchSad.setImageResource(R.drawable.sad_face_icon);
                isLunchSad = false;
                lunchRank = 2;
            }
        });

        imgStarLunch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawStars(3,imgStarLunch1,imgStarLunch2,imgStarLunch3,imgStarLunch4);
                imgLunchSad.setImageResource(R.drawable.sad_face_icon);
                isLunchSad = false;
                lunchRank = 3;
            }
        });

        imgStarLunch4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawStars(4,imgStarLunch1,imgStarLunch2,imgStarLunch3,imgStarLunch4);
                imgLunchSad.setImageResource(R.drawable.sad_face_icon);
                isLunchSad = false;
                lunchRank = 4;
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEditMode) {
                    if (StaticData.selectedDailyAgenda.getType_id() != null) {
                        switch (StaticData.selectedDailyAgenda.getType_id()) {
                            case "1":
                                StaticData.selectedDailyAgenda.setRating(String.valueOf(breakfastRank));
                                if (txtBreakfastDesc.getText() != null) {
                                    StaticData.selectedDailyAgenda.setDescription(txtBreakfastDesc.getText().toString());
                                }
                                break;
                            case "2":
                                StaticData.selectedDailyAgenda.setRating(String.valueOf(lunchRank));
                                if (txtLunchDesc.getText() != null) {
                                    StaticData.selectedDailyAgenda.setDescription(txtLunchDesc.getText().toString());
                                }
                                break;
                            case "3":
                                if (txtNapFrom.getText() != null) {
                                    StaticData.selectedDailyAgenda.setFrom_date(txtNapFrom.getText().toString());
                                }
                                if (txtNapTo.getText() != null) {
                                    StaticData.selectedDailyAgenda.setTo_date(txtNapTo.getText().toString());
                                }
                                break;
                            case "4":
                                if (spinnerType1.getText() != null) {
                                    StaticData.selectedDailyAgenda.setBath_type_id(spinnerType1.getText().toString());
                                }
                                if (spinnerType2.getText() != null) {
                                    StaticData.selectedDailyAgenda.setBath_potty_type_id(spinnerType2.getText().toString());
                                }
                                if (txtBathroomTime.getText() != null) {
                                    StaticData.selectedDailyAgenda.setTime(txtBathroomTime.getText().toString());
                                }
                                break;
                            case "5":
                                if (lblPottyTime.getText() != null) {
                                    StaticData.selectedDailyAgenda.setTime(lblPottyTime.getText().toString());
                                }
                                break;
                        }

                        int activityIndex = StaticData.dailyAgendas.indexOf(StaticData.selectedDailyAgenda);
                        StaticData.dailyAgendas.set(activityIndex, StaticData.selectedDailyAgenda);
                    }
                } else {
                    ChildActivity activity = new ChildActivity();

                    switch (dropdownAgenda.getText().toString().toLowerCase()) {
                        case "breakfast":
                            activity.setTitle("Breakfast");
                            activity.setType_id(StaticData.BREAKFAST);
                            activity.setRating(String.valueOf(breakfastRank));
                            activity.setDescription(txtBreakfastDesc.getText().toString());
                            break;

                        case "bathroom":
                            activity.setTitle("bathroom");
                            activity.setType_id(StaticData.BATHROOM);

                            break;

                        case "lunch":
                            activity.setTitle("Lunch");
                            activity.setType_id(StaticData.LUNCH);
                            activity.setRating(String.valueOf(lunchRank));
                            activity.setDescription(txtLunchDesc.getText().toString());
                            break;

                        case "nap":
                            activity.setTitle("Nap");
                            activity.setType_id(StaticData.NAP);
                            activity.setFrom_date(txtNapFrom.getText().toString());
                            activity.setTo_date(txtNapTo.getText().toString());
                            break;

                        case "potty":
                            activity.setTitle("Potty");
                            activity.setType_id(StaticData.POTTY);
                            activity.setTime(lblPottyTime.getText().toString());
                            break;

                        default:
                            break;
                    }

                    StaticData.dailyAgendas.add(activity);
                }

                finish();
            }
        });
    }

    private void showSelectedView(CardView view){
        viewBreakfast.setVisibility(View.GONE);
        viewPotty.setVisibility(View.GONE);
        viewNap.setVisibility(View.GONE);
        viewLunch.setVisibility(View.GONE);
        viewBathroom.setVisibility(View.GONE);

        view.setVisibility(View.VISIBLE);
    }

    private void drawStars(int ranking, ImageView star1, ImageView star2, ImageView star3, ImageView star4) {
        switch (ranking) {
            case 0:
                star1.setImageResource(R.drawable.start_unselected);
                star2.setImageResource(R.drawable.start_unselected);
                star3.setImageResource(R.drawable.start_unselected);
                star4.setImageResource(R.drawable.start_unselected);
                break;
            case 1:
                star1.setImageResource(R.drawable.star_selected);
                star2.setImageResource(R.drawable.start_unselected);
                star3.setImageResource(R.drawable.start_unselected);
                star4.setImageResource(R.drawable.start_unselected);
                break;
            case 2:
                star1.setImageResource(R.drawable.star_selected);
                star2.setImageResource(R.drawable.star_selected);
                star3.setImageResource(R.drawable.start_unselected);
                star4.setImageResource(R.drawable.start_unselected);
                break;
            case 3:
                star1.setImageResource(R.drawable.star_selected);
                star2.setImageResource(R.drawable.star_selected);
                star3.setImageResource(R.drawable.star_selected);
                star4.setImageResource(R.drawable.start_unselected);
                break;
            case 4:
                star1.setImageResource(R.drawable.star_selected);
                star2.setImageResource(R.drawable.star_selected);
                star3.setImageResource(R.drawable.star_selected);
                star4.setImageResource(R.drawable.star_selected);
                break;

            default:
                break;
        }
    }

    private void setTimePickerToText(final TextView textView){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddDailyAgendaActivity.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        textView.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
