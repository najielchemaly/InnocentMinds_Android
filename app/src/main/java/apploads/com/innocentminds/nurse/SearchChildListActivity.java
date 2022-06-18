package apploads.com.innocentminds.nurse;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import apploads.com.innocentminds.BaseActivity;
import apploads.com.innocentminds.R;
import apploads.com.innocentminds.databaseobjects.user.Child;
import apploads.com.innocentminds.helpers.StaticData;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.helpers.utils.StringUtils;

public class SearchChildListActivity extends BaseActivity {

    ListView lstChilds;
    EditText txtSearch;
    Button btnCancel;
    ImageButton btnClear;
    ChildAdapter childAdapter;
    RelativeLayout viewNoResults;
    TextView lblNoResults;

    List<Child> childList = new ArrayList<>();
    List<Child> filteredChildrenList = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.search_child_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        initListeners();
    }

    private void initView() {
        lstChilds = _findViewById(R.id.lstChilds);
        btnCancel = _findViewById(R.id.btnCancel);
        txtSearch = _findViewById(R.id.txtSearch);
        btnClear = _findViewById(R.id.btnClear);
        lblNoResults = _findViewById(R.id.lblNoResults);
        viewNoResults = _findViewById(R.id.viewNoResults);

        txtSearch.setTypeface(AppUtils.getRegularTypeface(this));
        lblNoResults.setTypeface(AppUtils.getRegularTypeface(this));

        childList = StaticData.user.getChildren();
        childAdapter = new ChildAdapter(childList, this);
        lstChilds.setAdapter(childAdapter);

        viewNoResults.setVisibility(View.GONE);
        viewNoResults.setAlpha(0f);
    }

    private void initListeners() {
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtSearch.setText("");
                childAdapter.reloadData(childList);
                toShowNoResultsView(false);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (StringUtils.isValid(txtSearch.getText())) {
                    performSearch();
                } else {
                    if (filteredChildrenList.size() > 0) {
                        childAdapter.reloadData(childList);
                        filteredChildrenList = new ArrayList<>();
                        toShowNoResultsView(false);
                    }
                }
                return true;
            }
        });
    }

    private void performSearch() {
        String searchResult = txtSearch.getText().toString().trim().toLowerCase();
        String childName;
        if(StringUtils.isValid(searchResult)){
            for (int i = 0; i < childList.size(); i++) {
                childName = childList.get(i).getFirstname().toLowerCase() + childList.get(i).getLastname().toLowerCase();
                if (childName.contains(searchResult)) {
                    filteredChildrenList.add(childList.get(i));
                }
            }
            if (filteredChildrenList.size() > 0) {
                childAdapter = new ChildAdapter(filteredChildrenList, this);
                lstChilds.setAdapter(childAdapter);
                toShowNoResultsView(false);
            } else {
                toShowNoResultsView(true);
            }
        }else {
            childAdapter = new ChildAdapter(childList, this);
            lstChilds.setAdapter(childAdapter);
        }
    }

    /**
     * to show and hide no result view
     *
     * @param value
     */
    private void toShowNoResultsView(boolean value) {
        if (value) {
            viewNoResults.setVisibility(View.VISIBLE);
            viewNoResults.animate().alpha(1f).setDuration(500);
            lstChilds.setVisibility(View.GONE);
        } else {
            viewNoResults.setVisibility(View.GONE);
            viewNoResults.animate().alpha(0f).setDuration(500);
            lstChilds.setVisibility(View.VISIBLE);
        }
    }
}
