package apploads.com.innocentminds;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

public class ContactusFragment extends Fragment {

    private View parentView;
    TextView lblContactus, txtBliss, txtHazmieh, txtSanayeh;
    Button btnClose, btnCloseViewContactUs, btnSendMessage;
    LinearLayout viewMessage;
    ImageButton btnLogout;
    RelativeLayout viewLocateus, viewContactUs;
    ListView lstPhoneNumbers;
    PhoneNumbersAdapter phoneNumbersAdapter;
    EditText txtFirstname, txtLastname, txtEmail, txtPhone, txtBranch, txtInquiry;
    LoadingIndicator indicatorView;

    List<Contact> contactUsList = new ArrayList<>();
    Contact selectedContactUs = new Contact();

    String selectedBranchId = "1";

    public static ContactusFragment newInstance() {
        ContactusFragment fragment = new ContactusFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.contactus_activity, container, false);

        if (StaticData.variables.getContacts() != null) {
            contactUsList = StaticData.variables.getContacts();
            updateFilteredContactUsList();
        }

        initView();
        initListeners();

        return parentView;
    }

    void updateFilteredContactUsList() {
        for(Contact contactus : contactUsList) {
            if (contactus.getBranch_id().equals(selectedBranchId)) {
                selectedContactUs = contactus;
                break;
            }
        }
    }

    private void initView(){
        btnClose = parentView.findViewById(R.id.btnClose);
        lblContactus = parentView.findViewById(R.id.lblContactus);
        txtBliss = parentView.findViewById(R.id.txtBliss);
        txtHazmieh = parentView.findViewById(R.id.txtHazmieh);
        txtSanayeh = parentView.findViewById(R.id.txtSanayeh);
        btnLogout = parentView.findViewById(R.id.btnLogout);
        viewMessage = parentView.findViewById(R.id.viewMessage);
        lstPhoneNumbers = parentView.findViewById(R.id.lstPhoneNumbers);
        viewLocateus = parentView.findViewById(R.id.viewLocateus);
        viewContactUs = parentView.findViewById(R.id.viewContactUs);
        btnSendMessage = parentView.findViewById(R.id.btnSendMessage);
        txtFirstname = parentView.findViewById(R.id.txtFirstname);
        txtLastname = parentView.findViewById(R.id.txtLastname);
        txtEmail = parentView.findViewById(R.id.txtEmail);
        txtPhone = parentView.findViewById(R.id.txtPhone);
        txtBranch = parentView.findViewById(R.id.txtBranch);
        txtInquiry = parentView.findViewById(R.id.txtInquiry);
        btnCloseViewContactUs = parentView.findViewById(R.id.btnCloseViewContactUs);
        indicatorView = parentView.findViewById(R.id.indicatorView);

        btnClose.setTypeface(AppUtils.getRegularTypeface(getActivity().getApplicationContext()));
        lblContactus.setTypeface(AppUtils.getTitleRegularFont(getActivity().getApplicationContext()));
        txtBliss.setTypeface(AppUtils.getRegularTypeface(getActivity().getApplicationContext()));
        txtHazmieh.setTypeface(AppUtils.getRegularTypeface(getActivity().getApplicationContext()));
        txtSanayeh.setTypeface(AppUtils.getRegularTypeface(getActivity().getApplicationContext()));

        phoneNumbersAdapter = new PhoneNumbersAdapter(selectedContactUs.getPhone_numbers(), getActivity().getApplicationContext(),1);
        lstPhoneNumbers.setAdapter(phoneNumbersAdapter);

        btnClose.setVisibility(View.GONE);
    }

    private void initListeners(){
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.showLogoutAlert(ContactusFragment.this.getActivity(), getResources().getString(R.string.return_main_menu));
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
                try {
                    if (selectedContactUs.getLocation() != null) {
                        String[] coordinates = selectedContactUs.getLocation().split(",");

                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?daddr="+coordinates[0]+","+coordinates[1]));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                } catch (Exception ex) {

                }
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
                                        AppUtils.showAlertWithMessage(ContactusFragment.this.getActivity(), "Innocent Minds", response.body().getMessage());
                                    } else {
                                        AppUtils.showAlertWithMessage(ContactusFragment.this.getActivity(), "Innocent Minds", "Your message has been sent successfully");
                                    }
                                } else if (response.body().getMessage() != null) {
                                    AppUtils.showAlertWithMessage(ContactusFragment.this.getActivity(), "Innocent Minds", response.body().getMessage());
                                } else {
                                    AppUtils.showAlertWithMessage(ContactusFragment.this.getActivity(), "Innocent Minds", "An error has occured, please try again or contact support");
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

    boolean validateForm() {
        if (!StringUtils.isValid(txtFirstname)) {
            AppUtils.showAlertWithMessage(getActivity(), "Innocent Minds", "Firstname field cannot be empty");
            return false;
        }
        else if (!StringUtils.isValid(txtLastname)) {
            AppUtils.showAlertWithMessage(getActivity(), "Innocent Minds", "Lastname field cannot be empty");
            return false;
        }
        else if (!StringUtils.isValid(txtEmail)) {
            AppUtils.showAlertWithMessage(getActivity(), "Innocent Minds", "Email field cannot be empty");
            return false;
        }
        else if (!StringUtils.isValid(txtPhone)) {
            AppUtils.showAlertWithMessage(getActivity(), "Innocent Minds", "Phone number field cannot be empty");
            return false;
        }
        else if (!StringUtils.isValid(txtBranch)) {
            AppUtils.showAlertWithMessage(getActivity(), "Innocent Minds", "Branch field cannot be empty");
            return false;
        }
        else if (!StringUtils.isValid(txtInquiry)) {
            AppUtils.showAlertWithMessage(getActivity(), "Innocent Minds", "Inquiry field cannot be empty");
            return false;
        }

        return true;
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

    @Override
    public void onResume() {
        super.onResume();
        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    AppUtils.showLogoutAlert(ContactusFragment.this.getActivity(), getResources().getString(R.string.return_main_menu));
                    return true;
                }
                return false;
            }
        });
    }
}
