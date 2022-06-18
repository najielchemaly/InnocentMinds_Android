package apploads.com.innocentminds.nurse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.user.Child;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;

public class ChildListActivity extends BaseActivity {

    ListView lstChild;
    ImageView btnBack;
    TextView txtName;
    ChildAdapter childAdapter;

    List<Child> childrenList = new ArrayList<>();
    List<Child> filteredChildrenList = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.children_list_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        styleView();
        initListeners();
    }

    private void initView(){
        lstChild = _findViewById(R.id.lstChild);
        btnBack = _findViewById(R.id.btnBack);
        txtName = _findViewById(R.id.txtName);

        if (StaticData.selectedClass.getName() != null) {
            txtName.setText(StaticData.selectedClass.getName());
        }

        if (StaticData.user.getChildren() != null) {
            childrenList = StaticData.user.getChildren();
            filterChildrenList();
        }

        childAdapter = new ChildAdapter(filteredChildrenList,this);
        lstChild.setAdapter(childAdapter);
    }

    private void styleView(){
        txtName.setTypeface(AppUtils.getRegularTypeface(this));
    }

    private void initListeners(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void filterChildrenList() {
        for (Child child : childrenList) {
            if (child.getClass_id() == StaticData.selectedClass.getId()) {
                filteredChildrenList.add(child);
            }
        }
    }
}
