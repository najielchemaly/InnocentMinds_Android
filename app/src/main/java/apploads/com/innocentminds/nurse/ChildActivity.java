package apploads.com.innocentminds.nurse;

import android.app.TimePickerDialog;

import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import org.angmarch.views.NiceSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.base.BaseResponse;
import apploads.com.innocentminds.databaseobjects.user.ChildTemperature;
import apploads.com.innocentminds.databaseobjects.user.SendTemperature;
import apploads.com.innocentminds.helpers.LoadingIndicator;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.services.ApiManager;

import de.hdodenhof.circleimageview.CircleImageView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChildActivity extends BaseActivity {

    ImageButton btnBack;
    ListView lstTemp;
    CircleImageView imgChild;
    TextView lblDesc, txtName, txtStudentArrived, lblTime, txtTime, lblAddTemp, lblAddTemprature, txtAddTime;
    CardView viewAddTemp;
    Button btnAdd, btnClose, btnSubmit, btnSubmitTemperature;
    EditText txtAddText;
    RelativeLayout viewAddTempGlobal;

    NiceSpinner dropDownTemp1, dropDownTemp2;
    LoadingIndicator indicatorView;

    boolean isStudentArrived;
    private int mHour, mMinute;

    TempratureAdapter tempratureAdapter;

    List<ChildTemperature> tempratureList = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.child_details_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView() {
        btnBack = _findViewById(R.id.btnBack);
        lstTemp = _findViewById(R.id.lstTemp);
        lblDesc = _findViewById(R.id.lblDesc);
        txtName = _findViewById(R.id.txtName);
        imgChild = _findViewById(R.id.imgChild);
        txtStudentArrived = _findViewById(R.id.txtStudentArrived);
        lblTime = _findViewById(R.id.lblTime);
        txtTime = _findViewById(R.id.txtTime);
        txtAddText = _findViewById(R.id.txtAddText);
        lblAddTemp = _findViewById(R.id.lblAddTemp);
        btnSubmit = _findViewById(R.id.btnSubmit);
        viewAddTemp = _findViewById(R.id.viewAddTemp);
        txtAddTime = _findViewById(R.id.txtAddTime);
        btnAdd = _findViewById(R.id.btnAdd);
        viewAddTempGlobal = _findViewById(R.id.viewAddTempGlobal);
        lblAddTemprature = _findViewById(R.id.lblAddTemprature);
        btnClose = _findViewById(R.id.btnClose);
        indicatorView = _findViewById(R.id.indicatorView);
        btnSubmitTemperature = _findViewById(R.id.btnSubmitTemperature);
        dropDownTemp1 = _findViewById(R.id.dropDownTemp1);
        dropDownTemp2 = _findViewById(R.id.dropDownTemp2);

        List<String> dataset = new LinkedList<>(Arrays.asList("35", "36", "37", "38", "39", "40"));
        List<String> dataset2 = new LinkedList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
        dropDownTemp1.attachDataSource(dataset);
        dropDownTemp2.attachDataSource(dataset2);

        viewAddTempGlobal.setAlpha(0f);

        if (tempratureList.size() > 0) {
            lstTemp.setVisibility(View.VISIBLE);
        } else {
            lstTemp.setVisibility(View.GONE);
        }

        if (StaticData.selectedChild.getImage() != null) {
            // URLString = ApiManager.getMediaUrl()+StaticData.selectedChild.getImage();
//            imgChild.setImageResource();
        }

        if (StaticData.selectedChild.getFirstname() != null && StaticData.selectedChild.getLastname() != null) {
            txtName.setText(StaticData.selectedChild.getFirstname()+" "+StaticData.selectedChild.getLastname());
        }

        txtTime.setEnabled(false);
        txtTime.setClickable(false);
        txtTime.setAlpha(0.5f);
    }

    private void styleView() {
        lblDesc.setTypeface(AppUtils.getLightTypeface(this));
        txtName.setTypeface(AppUtils.getRegularTypeface(this));
        txtStudentArrived.setTypeface(AppUtils.getRegularTypeface(this));
        lblTime.setTypeface(AppUtils.getRegularTypeface(this));
        txtTime.setTypeface(AppUtils.getRegularTypeface(this));
        lblAddTemp.setTypeface(AppUtils.getRegularTypeface(this));
        btnSubmit.setTypeface(AppUtils.getRegularTypeface(this));
        lblAddTemprature.setTypeface(AppUtils.getTitleBoldFont(this));
    }

    private void initListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtAddTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimePickerToText(txtAddTime, true);
            }
        });

        txtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimePickerToText(txtTime, false);
            }
        });

        txtStudentArrived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isStudentArrived) {
                    txtStudentArrived.setBackgroundResource(R.drawable.border_white_transparent_button);
                    isStudentArrived = false;

                    txtTime.setEnabled(false);
                    txtTime.setClickable(false);
                    txtTime.setAlpha(0.5f);
                } else {
                    txtStudentArrived.setBackgroundResource(R.drawable.border_green_button);
                    isStudentArrived = true;

                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat dt = new SimpleDateFormat("HH:mm");
                    String date = dt.format(currentTime);

                    txtTime.setText(date);
                    txtAddTime.setText(date);

                    txtTime.setEnabled(true);
                    txtTime.setClickable(true);
                    txtTime.setAlpha(1f);
                }
            }
        });

        viewAddTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAddTempGlobal.setVisibility(View.VISIBLE);
                viewAddTempGlobal.animate().alpha(1f).setDuration(500);
                txtAddTime.setText("Time");
                txtAddText.setText("");
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAddTempGlobal.setVisibility(View.GONE);
                viewAddTempGlobal.animate().alpha(0f).setDuration(500);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateTemprature()) {
                    String temp = dropDownTemp1.getText().toString() + "." + dropDownTemp2.getText().toString();
                    ChildTemperature temprature = new ChildTemperature();
                    temprature.setDate(txtAddTime.getText().toString());
                    temprature.setTemperature_id(temp);
                    temprature.setComment(txtAddText.getText().toString());
                    temprature.setChild_id(StaticData.selectedChild.getId());

                    tempratureList.add(temprature);

                    tempratureAdapter = new TempratureAdapter(tempratureList, ChildActivity.this);
                    lstTemp.setAdapter(tempratureAdapter);
                    lstTemp.setVisibility(View.VISIBLE);
                    viewAddTempGlobal.setAlpha(0f);
                    viewAddTempGlobal.setVisibility(View.GONE);

                    AppUtils.hideKeyboard(ChildActivity.this);
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indicatorView.show();

                String childId = StaticData.selectedChild.getId() == null ? "" : StaticData.selectedChild.getId().toString();
                String timeArrived = txtTime.getText() == null ? "" : txtTime.getText().toString();

                ApiManager.getService(true).updateStudentStatus(childId, true, timeArrived).enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                if (response.body().getMessage() != null) {
                                    AppUtils.showAlertWithMessage(ChildActivity.this, "Innocent Minds", response.body().getMessage());
                                } else {
                                    AppUtils.showAlertWithMessage(ChildActivity.this, "Innocent Minds", "Status has been updated successfully");
                                }
                            } else if (response.body().getMessage() != null) {
                                AppUtils.showAlertWithMessage(ChildActivity.this, "Innocent Minds", response.body().getMessage());
                            } else {
                                AppUtils.showAlertWithMessage(ChildActivity.this, "Innocent Minds", "An error has occured, please try again or contact support");
                            }
                        }

                        indicatorView.hide();
                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        indicatorView.hide();
                    }
                });
            }
        });

        btnSubmitTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tempratureList.size() > 1) {
                    indicatorView.show();

                    SendTemperature sendTemperature = new SendTemperature();
                    sendTemperature.setChild_id(StaticData.selectedChild.getId());
                    sendTemperature.setChild_temperatures(tempratureList);

                    ApiManager.getService(true).addTemperature(sendTemperature).enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 1) {
                                    if (response.body().getMessage() != null) {
                                        AppUtils.showAlertWithMessage(ChildActivity.this, "Innocent Minds", response.body().getMessage());
                                    } else {
                                        AppUtils.showAlertWithMessage(ChildActivity.this, "Innocent Minds", "Temperatures have been added successfully");
                                    }
                                } else if (response.body().getMessage() != null) {
                                    AppUtils.showAlertWithMessage(ChildActivity.this, "Innocent Minds", response.body().getMessage());
                                } else {
                                    AppUtils.showAlertWithMessage(ChildActivity.this, "Innocent Minds", "An error has occured, please try again or contact support");
                                }
                            }

                            indicatorView.hide();
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            indicatorView.hide();
                        }
                    });
                } else {
                    AppUtils.showAlertWithMessage(ChildActivity.this, "Innocent Minds", "You must fill at least two temperatures");
                }
            }
        });
    }

    private void setTimePickerToText(final TextView textView, final boolean isToAdd) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(ChildActivity.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        if (isToAdd) {
                            textView.setText(hourOfDay + ":" + minute);
                        } else {
                            Date currentTime = Calendar.getInstance().getTime();
                            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                            String date = dt.format(currentTime);
                            textView.setText(date + " " + hourOfDay + ":" + minute);
                        }
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private boolean validateTemprature() {
        if ("time".equals(txtAddTime.getText().toString().toLowerCase().trim())) {
            AppUtils.showAlertWithMessage(this, "Innocent Minds", "Time field cannot be empty");
            return false;
        }
//        else
//            if (!StringUtils.isValid(txtAddTemp.getText())) {
//            AppUtils.showAlertWithMessage(this, "Innocent Minds", "Temperature field cannot be empty");
//            return false;
//        }

        return true;
    }
}
