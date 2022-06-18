package apploads.com.innocentminds.signup;

import android.content.Intent;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.globalvariables.GlobalData;
import apploads.com.innocentminds.helpers.BaseActivity;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.helpers.utils.StringUtils;

public class EditChildProfileStepFourActivity extends BaseActivity {

    TextView lblMedicalInfo;
    EditText txtAllergy, txtSpecialMedication, txtMedication;
    NiceSpinner spinnerBloodType, spinnerAllergy, spinnerMedication;
    ImageButton btnCloseDiseases;
    Button btnNext, btnCancel, btnBack, btnConfirmDiseases, btnDisease;
    RelativeLayout viewDiseases;
    ListView lstDiseases;
    DiseaseAdapter diseaseAdapter;
    String diseasesStr = "";

    List<String> allergySet = new ArrayList<>();
    List<String> medicationSet = new ArrayList<>();

    List<GlobalData> bloodTypesList = new ArrayList<>();
    List<GlobalData> diseasesList = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.edit_child_profile_step_four;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView(){
        lblMedicalInfo = _findViewById(R.id.lblMedicalInfo);
        btnNext = _findViewById(R.id.btnNext);
        btnCancel = _findViewById(R.id.btnCancel);
        txtAllergy = _findViewById(R.id.txtAllergy);
        txtSpecialMedication = _findViewById(R.id.txtSpecialMedication);
        txtMedication = _findViewById(R.id.txtMedication);
        btnBack = _findViewById(R.id.btnBack);
        spinnerBloodType = _findViewById(R.id.spinnerBloodType);
        spinnerAllergy = _findViewById(R.id.spinnerAllergy);
        spinnerMedication = _findViewById(R.id.spinnerMedication);
        lstDiseases = _findViewById(R.id.lstDiseases);
        viewDiseases = _findViewById(R.id.viewDiseases);
        btnDisease = _findViewById(R.id.btnDisease);
        btnConfirmDiseases = _findViewById(R.id.btnConfirmDiseases);
        btnCloseDiseases = _findViewById(R.id.btnCloseDiseases);

        allergySet = new LinkedList<>(Arrays.asList(getResources().getString(R.string.allergy),
                getResources().getString(R.string.yes), getResources().getString(R.string.no)));
        medicationSet = new LinkedList<>(Arrays.asList(getResources().getString(R.string.regular_medication),
                getResources().getString(R.string.yes), getResources().getString(R.string.no)));

        if (StaticData.variables.getBlood_types() != null) {
            bloodTypesList = StaticData.variables.getBlood_types();
        }

        if (StaticData.variables.getDiseases() != null) {
            diseasesList = StaticData.variables.getDiseases();
        }

        spinnerBloodType.attachDataSource(bloodTypesList);
        spinnerAllergy.attachDataSource(allergySet);
        spinnerMedication.attachDataSource(medicationSet);

        diseaseAdapter = new DiseaseAdapter(diseasesList, this, EditChildProfileStepFourActivity.this);
        lstDiseases.setAdapter(diseaseAdapter);

        viewDiseases.setVisibility(View.GONE);
        viewDiseases.setAlpha(0f);

        setupChildInfo();
    }

    private void styleView(){
        lblMedicalInfo.setTypeface(AppUtils.getTitleRegularFont(this));
        btnNext.setTypeface(AppUtils.getRegularTypeface(this));
        btnCancel.setTypeface(AppUtils.getRegularTypeface(this));
        btnCancel.setTypeface(AppUtils.getRegularTypeface(this));
        btnBack.setTypeface(AppUtils.getRegularTypeface(this));
        spinnerBloodType.setTypeface(AppUtils.getRegularTypeface(this));
    }

    private void initListeners(){
        spinnerAllergy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (((AppCompatTextView) view).getText().toString()){
                    case "Allergy":
                    case "No":
                        txtAllergy.setEnabled(false);
                        txtAllergy.setAlpha(0.5f);
                        break;
                    case "Yes":
                        txtAllergy.setEnabled(true);
                        txtAllergy.setAlpha(1f);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerMedication.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (((AppCompatTextView) view).getText().toString()){
                    case "Regular medications":
                    case "No":
                        txtAllergy.setEnabled(false);
                        txtAllergy.setAlpha(0.5f);
                        break;
                    case "Yes":
                        txtAllergy.setEnabled(true);
                        txtAllergy.setAlpha(1f);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTempChildInfo();

                Intent intent = new Intent(getApplicationContext(), EditChildProfileStepFiveActivity.class);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.shouldFinish = true;
                finish();
            }
        });

        btnDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDiseases.animate().alpha(1f).setDuration(700);
                viewDiseases.setVisibility(View.VISIBLE);

                btnBack.setVisibility(View.INVISIBLE);
                btnNext.setVisibility(View.INVISIBLE);
            }
        });

        btnConfirmDiseases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDisease.setText(diseasesStr);
                viewDiseases.setVisibility(View.GONE);
                viewDiseases.setAlpha(0f);

                btnBack.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            }
        });

        btnCloseDiseases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDiseases.setVisibility(View.GONE);
                viewDiseases.setAlpha(0f);

                btnBack.setVisibility(View.VISIBLE);
                btnNext.setVisibility(View.VISIBLE);
            }
        });
    }

    void setupChildInfo() {
        spinnerBloodType.setText(StaticData.selectedChild.getBlood_type_id());
        spinnerAllergy.setText(StringUtils.isValid(StaticData.selectedChild.getAllergy()) ? getResources().getString(R.string.yes) : getResources().getString(R.string.no));
        txtAllergy.setText(StaticData.selectedChild.getAllergy());
        spinnerMedication.setText(StringUtils.isValid(StaticData.selectedChild.getRegular_medication()) ? getResources().getString(R.string.yes) : getResources().getString(R.string.no));
        txtMedication.setText(StaticData.selectedChild.getRegular_medication());
        btnDisease.setText(StaticData.selectedChild.getDisease_ids());
        txtSpecialMedication.setText(StaticData.selectedChild.getSpecial_medical_conditions());
    }

    void updateTempChildInfo() {
        StaticData.tempChild.setBlood_type_id(spinnerBloodType.getText() == null ? "" : spinnerBloodType.getText().toString());
        StaticData.tempChild.setAllergy(txtAllergy.getText() == null ? "" : txtAllergy.getText().toString());
        StaticData.tempChild.setRegular_medication(txtMedication.getText() == null ? "" : txtMedication.getText().toString());
        StaticData.tempChild.setDisease_ids(btnDisease.getText() == null ? "" : btnDisease.getText().toString());
        StaticData.tempChild.setSpecial_medical_conditions(txtSpecialMedication.getText() == null ? "" : txtSpecialMedication.getText().toString());
    }

    public void getDiseases(List<String> diseases) {
        diseasesStr = "";
        for (String disease : diseases) {
            diseasesStr = diseasesStr + disease + ",";
        }

        if (diseasesStr.length() > 0) {
            diseasesStr = diseasesStr.substring(0, diseasesStr.length() - 1);
        }else {
            diseasesStr = getResources().getString(R.string.disease);
        }
    }
}
