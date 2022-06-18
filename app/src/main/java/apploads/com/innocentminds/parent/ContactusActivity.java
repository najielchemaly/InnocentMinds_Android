package apploads.com.innocentminds.parent;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.PhoneNumbersAdapter;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.base.BaseResponse;
import apploads.com.innocentminds.databaseobjects.globalvariables.Contact;
import apploads.com.innocentminds.helpers.LoadingIndicator;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.helpers.utils.StringUtils;
import apploads.com.innocentminds.services.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactusActivity extends BaseActivity {

    TextView lblContactus, txtBliss, txtHazmieh, txtSanayeh;
    Button btnClose;
    LinearLayout viewMessage;
    ImageButton btnLogout;
    ListView lstPhoneNumbers;
    RelativeLayout viewLocateus, viewContactUs;
    PhoneNumbersAdapter phoneNumbersAdapter;
    Button btnSendMessage, btnCloseViewContactUs;
    EditText txtFirstname, txtLastname, txtEmail, txtPhone, txtBranch, txtInquiry;
    LoadingIndicator indicatorView;

    List<Contact> contactUsList = new ArrayList<>();
    Contact selectedContactUs = new Contact();

    String selectedBranchId = "1";

    @Override
    public int getContentViewId() {
        return R.layout.contactus_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        btnClose = _findViewById(R.id.btnClose);
        lblContactus = _findViewById(R.id.lblContactus);
        txtBliss = _findViewById(R.id.txtBliss);
        txtHazmieh = _findViewById(R.id.txtHazmieh);
        txtSanayeh = _findViewById(R.id.txtSanayeh);
        btnLogout = _findViewById(R.id.btnLogout);
        viewMessage = _findViewById(R.id.viewMessage);
        lstPhoneNumbers = _findViewById(R.id.lstPhoneNumbers);
        viewLocateus = _findViewById(R.id.viewLocateus);
        viewContactUs = _findViewById(R.id.viewContactUs);
        btnSendMessage = _findViewById(R.id.btnSendMessage);
        txtFirstname = _findViewById(R.id.txtFirstname);
        txtLastname = _findViewById(R.id.txtLastname);
        txtEmail = _findViewById(R.id.txtEmail);
        txtPhone = _findViewById(R.id.txtPhone);
        txtBranch = _findViewById(R.id.txtBranch);
        txtInquiry = _findViewById(R.id.txtInquiry);
        btnCloseViewContactUs = _findViewById(R.id.btnCloseViewContactUs);
        indicatorView = _findViewById(R.id.indicatorView);

        btnClose.setTypeface(AppUtils.getRegularTypeface(this));
        lblContactus.setTypeface(AppUtils.getTitleRegularFont(this));
        txtBliss.setTypeface(AppUtils.getRegularTypeface(this));
        txtHazmieh.setTypeface(AppUtils.getRegularTypeface(this));
        txtSanayeh.setTypeface(AppUtils.getRegularTypeface(this));

        if (StaticData.variables.getContacts() != null) {
            contactUsList = StaticData.variables.getContacts();
            updateFilteredContactUsList();
        }

        phoneNumbersAdapter = new PhoneNumbersAdapter(selectedContactUs.getPhone_numbers(), this, 1);
        lstPhoneNumbers.setAdapter(phoneNumbersAdapter);

        btnLogout.setVisibility(View.GONE);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtBliss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedBranchId = "1";
                resetTextviewColors(txtBliss);
                updateFilteredContactUsList();
                phoneNumbersAdapter.reloadData(selectedContactUs.getPhone_numbers(),1);
            }
        });

        txtHazmieh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedBranchId = "2";
                resetTextviewColors(txtHazmieh);
                updateFilteredContactUsList();
                phoneNumbersAdapter.reloadData(selectedContactUs.getPhone_numbers(),2);
            }
        });

        txtSanayeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedBranchId = "3";
                resetTextviewColors(txtSanayeh);
                updateFilteredContactUsList();
                phoneNumbersAdapter.reloadData(selectedContactUs.getPhone_numbers(),3);
            }
        });

        viewLocateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
                startActivity(intent);
            }
        });

        viewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewContactUs.animate().alpha(1f).setDuration(700);
                viewContactUs.setVisibility(View.VISIBLE);
            }
        });

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    indicatorView.show();

                    ApiManager.getService(true).sendContactUs(txtFirstname.getText().toString(), txtLastname.getText().toString(), txtEmail.getText().toString(),
                            txtPhone.getText().toString(), txtBranch.getText().toString(), txtInquiry.getText().toString()).enqueue(new Callback<BaseResponse>() {
                        @Override
                        public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                            if (response.body() != null) {
                                if (response.body().getStatus() == 1) {
                                    if (response.body().getMessage() != null) {
                                        AppUtils.showAlertWithMessage(ContactusActivity.this, "Innocent Minds", response.body().getMessage());
                                    } else {
                                        AppUtils.showAlertWithMessage(ContactusActivity.this, "Innocent Minds", getResources().getString(R.string.message_sent_successfully));
                                    }
                                } else if (response.body().getMessage() != null) {
                                    AppUtils.showAlertWithMessage(ContactusActivity.this, "Innocent Minds", response.body().getMessage());
                                } else {
                                    AppUtils.showAlertWithMessage(ContactusActivity.this, "Innocent Minds", getResources().getString(R.string.an_error_occured));
                                }
                            }

                            viewContactUs.animate().alpha(0f).setDuration(700);
                            viewContactUs.setVisibility(View.GONE);

                            indicatorView.hide();
                        }

                        @Override
                        public void onFailure(Call<BaseResponse> call, Throwable t) {
                            indicatorView.hide();
                        }
                    });
                }
            }
        });

        btnCloseViewContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewContactUs.animate().alpha(0f).setDuration(700);
                viewContactUs.setVisibility(View.GONE);
            }
        });
    }

    void resetTextviewColors(TextView textView) {
        txtBliss.setTextColor(getResources().getColor(R.color.lightGrey));
        txtHazmieh.setTextColor(getResources().getColor(R.color.lightGrey));
        txtSanayeh.setTextColor(getResources().getColor(R.color.lightGrey));
//        txtBliss.setAlpha(0.5f);
//        txtHazmieh.setAlpha(0.5f);
//        txtSanayeh.setAlpha(0.5f);

        textView.setAlpha(1f);
        textView.setTextColor(getResources().getColor(R.color.darkGrey));
    }

    boolean validateForm() {
        if (!StringUtils.isValid(txtFirstname)) {
            AppUtils.showAlertWithMessage(this, "Innocent Minds", getResources().getString(R.string.firstname_empty_error));
            return false;
        }
        else if (!StringUtils.isValid(txtLastname)) {
            AppUtils.showAlertWithMessage(this, "Innocent Minds", getResources().getString(R.string.lastname_empty_error));
            return false;
        }
        else if (!StringUtils.isValid(txtEmail)) {
            AppUtils.showAlertWithMessage(this, "Innocent Minds", getResources().getString(R.string.email_empty_error));
            return false;
        }
        else if (!StringUtils.isValid(txtPhone)) {
            AppUtils.showAlertWithMessage(this, "Innocent Minds", getResources().getString(R.string.phone_empty_error));
            return false;
        }
        else if (!StringUtils.isValid(txtBranch)) {
            AppUtils.showAlertWithMessage(this, "Innocent Minds", getResources().getString(R.string.branch_empty_error));
            return false;
        }
        else if (!StringUtils.isValid(txtInquiry)) {
            AppUtils.showAlertWithMessage(this, "Innocent Minds", getResources().getString(R.string.inquiry_empty_error));
            return false;
        }

        return true;
    }

    void updateFilteredContactUsList() {
        for(Contact contactus : contactUsList) {
            if (contactus.getBranch_id().equals(selectedBranchId)) {
                selectedContactUs = contactus;
                break;
            }
        }
    }
}
