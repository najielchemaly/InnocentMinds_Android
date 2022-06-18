package apploads.com.innocentminds.signup;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.base.BaseResponse;
import apploads.com.innocentminds.databaseobjects.globalvariables.GlobalData;
import apploads.com.innocentminds.helpers.BaseActivity;
import apploads.com.innocentminds.helpers.CustomDialogClass;
import apploads.com.innocentminds.helpers.LoadingIndicator;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.helpers.utils.StringUtils;
import apploads.com.innocentminds.services.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupChildActivity extends BaseActivity {

    ImageView btnPhoto;
    TextView lblRegister, lblIAm, lblMother, lblFather, txtDate, lblNotBornYet, txtDesiredDate;
    LinearLayout viewChildName, viewIAm, viewDesiredDate;
    NiceSpinner dropdownHow, dropdownBranch;
    EditText txtFirstName, txtLastName, txtYourName, txtYourFamilyName, txtYourPhone, txtYourEmail, txtYourAddress;
    Button btnCancel, btnNext, btnBack;
    LoadingIndicator indicatorView;

    private Animation left_to_right, right_to_left, slide_out_left, slide_out_right;
    int viewNumber = 1;
    private Calendar calendar;
    private int year, month, day;
    private int parent = 0;
    private int FATHER = 1;
    private int MOTHER = 2;

    boolean isDesiredDate;
    Integer branchId, hearAboutUsId;

    List<GlobalData> branchesList = new ArrayList<>();
    List<GlobalData> hearAboutUsList = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.register_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView() {
        btnPhoto = _findViewById(R.id.btnPhoto);
        lblRegister = _findViewById(R.id.lblRegister);
        btnNext = _findViewById(R.id.btnNext);
        btnCancel = _findViewById(R.id.btnCancel);
        btnBack = _findViewById(R.id.btnBack);
        viewChildName = _findViewById(R.id.viewChildName);
        viewIAm = _findViewById(R.id.viewIAm);
        viewDesiredDate = _findViewById(R.id.viewDesiredDate);
        lblNotBornYet = _findViewById(R.id.lblNotBornYet);
        lblIAm = _findViewById(R.id.lblIAm);
        txtFirstName = _findViewById(R.id.txtFirstName);
        txtLastName = _findViewById(R.id.txtLastName);
        txtDate = _findViewById(R.id.txtDate);
        txtYourName = _findViewById(R.id.txtYourName);
        txtYourFamilyName = _findViewById(R.id.txtYourFamilyName);
        txtYourPhone = _findViewById(R.id.txtYourPhone);
        txtYourEmail = _findViewById(R.id.txtYourEmail);
        txtYourAddress = _findViewById(R.id.txtYourAddress);
        txtDesiredDate = _findViewById(R.id.txtDesiredDate);
        dropdownHow = _findViewById(R.id.dropdownHow);
        dropdownBranch = _findViewById(R.id.dropdownBranch);
        lblMother = _findViewById(R.id.lblMother);
        lblFather = _findViewById(R.id.lblFather);
        indicatorView = _findViewById(R.id.indicatorView);

        slide_out_left = AnimationUtils.loadAnimation(this, R.anim.slide_out_left_wu);
        slide_out_right = AnimationUtils.loadAnimation(this, R.anim.slide_out_right_wu);
        left_to_right = AnimationUtils.loadAnimation(this, R.anim.left_to_right_wt);
        right_to_left = AnimationUtils.loadAnimation(this, R.anim.right_to_left_wt);

        if (StaticData.variables.getBranches() != null) {
            branchesList = StaticData.variables.getBranches();
            branchId = branchesList.get(0).getId();
        }

        if (StaticData.variables.getHear_about_us() != null) {
            hearAboutUsList = StaticData.variables.getHear_about_us();
            hearAboutUsId = hearAboutUsList.get(0).getId();
        }

        dropdownHow.attachDataSource(hearAboutUsList);
        dropdownHow.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hearAboutUsId = hearAboutUsList.get(position).getId();
            }
        });

        dropdownBranch.attachDataSource(branchesList);
        dropdownBranch.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                branchId = branchesList.get(position).getId();
            }
        });

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        viewChildName.setVisibility(View.VISIBLE);
        viewIAm.setVisibility(View.GONE);
        viewDesiredDate.setVisibility(View.GONE);

        btnBack.setVisibility(View.GONE);
    }

    private void styleView() {
        lblRegister.setTypeface(AppUtils.getTitleRegularFont(this));
        lblIAm.setTypeface(AppUtils.getTitleRegularFont(this));
        btnNext.setTypeface(AppUtils.getBoldTypeface(this));
        btnBack.setTypeface(AppUtils.getRegularTypeface(this));
        btnCancel.setTypeface(AppUtils.getBoldTypeface(this));
    }

    private void initListeners() {

        lblFather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblFather.setBackgroundResource(R.drawable.border_white_rec);
                lblFather.setTextColor(getResources().getColor(R.color.signinBlue));

                lblMother.setBackgroundResource(R.drawable.border_transparent_view);
                lblMother.setTextColor(getResources().getColor(R.color.white));

                parent = FATHER;
            }
        });

        lblMother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblMother.setBackgroundResource(R.drawable.border_white_rec);
                lblMother.setTextColor(getResources().getColor(R.color.signinBlue));

                lblFather.setBackgroundResource(R.drawable.border_transparent_view);
                lblFather.setTextColor(getResources().getColor(R.color.white));
                parent = MOTHER;
            }
        });

        lblNotBornYet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtDate.isEnabled()) {
                    txtDate.setEnabled(false);
                    txtDate.setClickable(false);
                    txtDate.setText(getResources().getString(R.string.child_dob));
                    lblNotBornYet.setBackgroundResource(R.drawable.border_white_rec);
                    lblNotBornYet.setTextColor(getResources().getColor(R.color.signinBlue));
                    txtDate.setTextColor(getResources().getColor(R.color.grey));
                    txtDate.setAlpha(0.5f);
                } else {
                    txtDate.setEnabled(true);
                    txtDate.setClickable(true);
                    lblNotBornYet.setBackgroundResource(R.drawable.border_transparent_signup_rec);
                    lblNotBornYet.setTextColor(getResources().getColor(R.color.white));
                    txtDate.setTextColor(getResources().getColor(R.color.white));
                    txtDate.setAlpha(1f);
                }
            }
        });

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDesiredDate = false;
                showDialog(999);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewNumber == 1) {
                    if (validateFirstView()) {
                        viewIAm.startAnimation(right_to_left);
                        viewChildName.startAnimation(slide_out_right);
                        viewChildName.setVisibility(View.GONE);
                        viewIAm.setVisibility(View.VISIBLE);
                        viewDesiredDate.setVisibility(View.GONE);

                        viewNumber = 2;
                        btnBack.setVisibility(View.VISIBLE);
                        btnNext.setText(getResources().getString(R.string.next));
                    }
                } else if (viewNumber == 2) {
                    if (validateSecondView()) {
                        viewChildName.setVisibility(View.GONE);
                        viewIAm.setVisibility(View.GONE);
                        viewDesiredDate.setVisibility(View.VISIBLE);

                        viewDesiredDate.startAnimation(right_to_left);
                        viewIAm.startAnimation(slide_out_right);

                        btnNext.setText(getResources().getString(R.string.request_appointment));

                        viewNumber = 3;
                    }
                } else if (validateThirdView()) {
                    if (validateThirdView()) {
                        indicatorView.show();

                        ApiManager.getService(true)
                                .registerChild(
                                        txtFirstName.getText().toString(),
                                        txtLastName.getText().toString(),
                                        txtDate.isEnabled() ? txtDate.getText().toString() : null,
                                        branchId.toString(),
                                        txtDesiredDate.getText().toString(),
                                        txtYourName.getText().toString() + " " +
                                                txtYourFamilyName.getText().toString(),
                                        txtYourPhone.getText().toString(),
                                        txtYourEmail.getText().toString(),
                                        txtYourAddress.getText().toString(),
                                        hearAboutUsId.toString(),
                                        String.valueOf(parent),
                                        true,
                                        1,
                                        AppUtils.getLang(SignupChildActivity.this))
                                .enqueue(new Callback<BaseResponse>() {
                                    @Override
                                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                        if (response.body() != null) {
                                            if (response.body().getStatus() == 1) {
                                                AppUtils.showAlertWithMessage(SignupChildActivity.this, getResources().getString(R.string.thankyou), getResources().getString(R.string.our_tem_contact_you));
                                                finish();
                                            } else if (response.body().getMessage() != null) {
                                                AppUtils.showAlertWithMessage(SignupChildActivity.this, "Innocent Minds", response.body().getMessage());
                                            } else {
                                                AppUtils.showAlertWithMessage(SignupChildActivity.this, "Innocent Minds", getResources().getString(R.string.an_error_occured));
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
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewNumber == 2) {
                    viewChildName.setVisibility(View.VISIBLE);
                    viewIAm.setVisibility(View.GONE);
                    viewDesiredDate.setVisibility(View.GONE);

                    viewChildName.startAnimation(left_to_right);
                    viewIAm.startAnimation(slide_out_left);

                    btnBack.setVisibility(View.GONE);
                    viewNumber = 1;

                    btnNext.setText(getResources().getString(R.string.next));
                } else if (viewNumber == 3) {
                    viewChildName.setVisibility(View.GONE);
                    viewIAm.setVisibility(View.VISIBLE);
                    viewDesiredDate.setVisibility(View.GONE);

                    viewIAm.startAnimation(left_to_right);
                    viewDesiredDate.startAnimation(slide_out_left);
                    btnNext.setText(getResources().getString(R.string.next));
                    viewNumber = 2;
                }
            }
        });

        txtDesiredDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDesiredDate = true;
                showDialog(999);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomDialogClass dialogClass = new CustomDialogClass(SignupChildActivity.this, new CustomDialogClass.AbstractCustomDialogListener() {
                    @Override
                    public void onConfirm(CustomDialogClass.DialogResponse response) {
                        response.getDialog().dismiss();
                        finish();
                        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
                    }

                    @Override
                    public void onCancel(CustomDialogClass.DialogResponse dialogResponse) {
                        dialogResponse.getDialog().dismiss();
                    }
                }, false, getResources().getString(R.string.yes), getResources().getString(R.string.cancel));
                dialogClass.setTitle("Innocent Minds");
                dialogClass.setMessage(getResources().getString(R.string.are_you_sure_cancel_registration));
                dialogClass.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialogClass.show();

            }
        });
    }

    private boolean validateFirstView() {
        if (!StringUtils.isValid(txtFirstName.getText())) {
            AppUtils.showAlertWithMessage(SignupChildActivity.this, "Innocent Minds", getResources().getString(R.string.firstname_empty_error));
            return false;
        } else if (!StringUtils.isValid(txtLastName.getText())) {
            AppUtils.showAlertWithMessage(SignupChildActivity.this, "Innocent Minds", getResources().getString(R.string.lastname_empty_error));
            return false;
        } else if (getResources().getString(R.string.child_dob).equals(txtDate.getText().toString()) && txtDate.isEnabled()) {
            AppUtils.showAlertWithMessage(SignupChildActivity.this, "Innocent Minds", getResources().getString(R.string.date_field_empty_error));
            return false;
        }
        return true;
    }

    private boolean validateSecondView() {
        if (!StringUtils.isValid(txtYourName.getText()) || !StringUtils.isValid(txtYourFamilyName.getText())) {
            AppUtils.showAlertWithMessage(SignupChildActivity.this, "Innocent Minds", getResources().getString(R.string.name_empty_error));
            return false;
        } else if (!StringUtils.isValid(txtYourPhone.getText())) {
            AppUtils.showAlertWithMessage(SignupChildActivity.this, "Innocent Minds", getResources().getString(R.string.phone_empty_error));
            return false;
        } else if (!StringUtils.isValid(txtYourEmail.getText())) {
            AppUtils.showAlertWithMessage(SignupChildActivity.this, "Innocent Minds", getResources().getString(R.string.email_empty_error));
            return false;
        } else if (!StringUtils.isValid(txtYourAddress.getText())) {
            AppUtils.showAlertWithMessage(SignupChildActivity.this, "Innocent Minds", getResources().getString(R.string.address_empty_error));
            return false;
        } else if (parent == 0) {
            AppUtils.showAlertWithMessage(SignupChildActivity.this, "Innocent Minds", getResources().getString(R.string.specify_relation));
            return false;
        }
        return true;
    }

    private boolean validateThirdView() {
        if (!StringUtils.isValid(txtDesiredDate.getText())) {
            AppUtils.showAlertWithMessage(SignupChildActivity.this, "Innocent Minds", getResources().getString(R.string.date_field_empty_error));
            return false;
        } else if (!StringUtils.isValid(dropdownBranch.getText())) {
            AppUtils.showAlertWithMessage(SignupChildActivity.this, "Innocent Minds", getResources().getString(R.string.branch_empty_error));
            return false;
        } else if (!StringUtils.isValid(dropdownHow.getText())) {
            AppUtils.showAlertWithMessage(SignupChildActivity.this, "Innocent Minds", getResources().getString(R.string.hear_about_us_error));
            return false;
        }
        return true;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        if (isDesiredDate) {
            txtDesiredDate.setText(new StringBuilder().append(day).append("-")
                    .append(month).append("-").append(year));
        } else {
            txtDate.setText(new StringBuilder().append(day).append("-")
                    .append(month).append("-").append(year));
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            DatePickerDialog dialog = new DatePickerDialog(this,
                    myDateListener, year, month, day);
            dialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());

            return dialog;

        }
        return null;
    }
}
