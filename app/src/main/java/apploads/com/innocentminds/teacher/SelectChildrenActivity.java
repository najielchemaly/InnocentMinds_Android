package apploads.com.innocentminds.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.user.Child;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;

public class SelectChildrenActivity extends BaseActivity {

    ListView lstChildren;
    Button btnConfirm, btnCancel;
    TextView lblSelectStudent;
    SelectChildrenAdapter selectChildrenAdapter;
    List<Child> childs = new ArrayList<>();
    List<Child> students = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.select_students_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView(){
        lstChildren = _findViewById(R.id.lstChildren);
        btnConfirm = _findViewById(R.id.btnConfirm);
        btnCancel = _findViewById(R.id.btnCancel);
        lblSelectStudent = _findViewById(R.id.lblSelectStudent);

        if (StaticData.user.getChildren() != null) {
            students = StaticData.user.getChildren();
        }

        selectChildrenAdapter = new SelectChildrenAdapter(students, this);
        lstChildren.setAdapter(selectChildrenAdapter);
    }

    private void styleView(){
        btnConfirm.setTypeface(AppUtils.getRegularTypeface(this));
        lblSelectStudent.setTypeface(AppUtils.getTitleRegularFont(this));
        btnCancel.setTypeface(AppUtils.getRegularTypeface(this));
    }

    private void initListeners(){

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("studentIds", (Serializable) selectChildrenAdapter.getSelectedChilds());
                setResult(3, intent);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
