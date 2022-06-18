package apploads.com.innocentminds.signup;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import apploads.com.innocentminds.helpers.LoadingIndicator;
import apploads.com.innocentminds.helpers.utils.AppUtils;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.base.BaseResponse;
import apploads.com.innocentminds.databaseobjects.globalvariables.GlobalData;
import apploads.com.innocentminds.helpers.BaseActivity;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.homepage.HomepageActivity;
import apploads.com.innocentminds.services.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditChildProfileStepFiveActivity extends BaseActivity {

    TextView lblHabits;
    NiceSpinner spinnerSleepHabit, spinnerEatingHabit, spinnerClean, spinnerCharacter;
    EditText txtdesc;
    Button btnFinish, btnCancel, btnBack;
    LoadingIndicator indicatorView;

    List<GlobalData> habitRanksList = new ArrayList<>();
    List<GlobalData> characterTypesList = new ArrayList<>();

    List<String> cleanSet = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.edit_child_profile_step_five;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView(){
        lblHabits = _findViewById(R.id.lblHabits);
        btnFinish = _findViewById(R.id.btnFinish);
        btnCancel = _findViewById(R.id.btnCancel);
        btnBack = _findViewById(R.id.btnBack);
        txtdesc = _findViewById(R.id.txtdesc);
        spinnerSleepHabit = _findViewById(R.id.spinnerSleepHabit);
        spinnerEatingHabit = _findViewById(R.id.spinnerEatingHabit);
        spinnerClean = _findViewById(R.id.spinnerClean);
        spinnerCharacter = _findViewById(R.id.spinnerCharacter);
        indicatorView = _findViewById(R.id.indicatorView);

        cleanSet = new LinkedList<>(Arrays.asList(getResources().getString(R.string.clean),
                getResources().getString(R.string.yes), getResources().getString(R.string.no)));

        if (StaticData.variables.getHabit_ranks() != null) {
            habitRanksList = StaticData.variables.getHabit_ranks();
        }
        if (StaticData.variables.getCharacter_types() != null) {
            characterTypesList = StaticData.variables.getCharacter_types();
        }

        spinnerSleepHabit.attachDataSource(habitRanksList);
        spinnerEatingHabit.attachDataSource(habitRanksList);
        spinnerClean.attachDataSource(cleanSet);
        spinnerCharacter.attachDataSource(characterTypesList);

        setupChildInfo();
    }

    private void styleView(){
        lblHabits.setTypeface(AppUtils.getTitleRegularFont(this));
        btnFinish.setTypeface(AppUtils.getRegularTypeface(this));
        btnCancel.setTypeface(AppUtils.getRegularTypeface(this));
        btnCancel.setTypeface(AppUtils.getRegularTypeface(this));
        btnBack.setTypeface(AppUtils.getRegularTypeface(this));
        spinnerSleepHabit.setTypeface(AppUtils.getRegularTypeface(this));
        spinnerEatingHabit.setTypeface(AppUtils.getRegularTypeface(this));
        spinnerClean.setTypeface(AppUtils.getRegularTypeface(this));
        spinnerCharacter.setTypeface(AppUtils.getRegularTypeface(this));
    }

    private void initListeners(){
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTempChildInfo();

                indicatorView.show();

                ApiManager.getService(true).editChild(StaticData.tempChild).enqueue(new Callback<BaseResponse>() {
                    @Override
                    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                StaticData.shouldFinish = true;

                                if (response.body().getMessage() != null) {
                                    AppUtils.showAlertWithMessage(EditChildProfileStepFiveActivity.this, "Innocent Minds", response.body().getMessage(), true);
                                } else {
                                    AppUtils.showAlertWithMessage(EditChildProfileStepFiveActivity.this, "Innocent Minds", "Your child's profile has been updated successfully", true);
                                }

                                // TODO To be checked
                                int selectedIndex = StaticData.user.getChildren().indexOf(StaticData.selectedChild);
                                StaticData.user.getChildren().remove(StaticData.selectedChild);
                                StaticData.selectedChild = StaticData.tempChild;
                                StaticData.user.getChildren().add(selectedIndex, StaticData.selectedChild);
                            } else if (response.body().getMessage() != null) {
                                AppUtils.showAlertWithMessage(EditChildProfileStepFiveActivity.this, "Innocent Minds", response.body().getMessage());
                            } else {
                                AppUtils.showAlertWithMessage(EditChildProfileStepFiveActivity.this, "Innocent Minds", "An error has occured, please try again or contact support");
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
    }

    void setupChildInfo() {
        spinnerSleepHabit.setText(StaticData.selectedChild.getSleep_habit_id());
        spinnerEatingHabit.setText(StaticData.selectedChild.getEating_habit_id());
        spinnerClean.setText(StaticData.selectedChild.getClean() ? getResources().getString(R.string.yes)
                : getResources().getString(R.string.no));
        txtdesc.setText(StaticData.selectedChild.getPacifier());
        spinnerCharacter.setText(StaticData.selectedChild.getCharacter_type_id());
    }

    void updateTempChildInfo() {
        StaticData.tempChild.setSleep_habit_id(spinnerSleepHabit.getText() == null ? "" : spinnerSleepHabit.getText().toString());
        StaticData.tempChild.setEating_habit_id(spinnerEatingHabit.getText() == null ? "" : spinnerEatingHabit.getText().toString());
        StaticData.tempChild.setClean(spinnerEatingHabit.getText() != null && (spinnerEatingHabit.getText().toString().equals(getResources().getString(R.string.yes))));
        StaticData.tempChild.setPacifier(txtdesc.getText() == null ? "" : txtdesc.getText().toString());
        StaticData.tempChild.setCharacter_type_id(spinnerCharacter.getText() == null ? "" : spinnerCharacter.getText().toString());
        StaticData.tempChild.setLang(AppUtils.getLang(this));
    }
}
