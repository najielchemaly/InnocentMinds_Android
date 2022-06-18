package apploads.com.innocentminds;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;

import apploads.com.innocentminds.aboutus.AboutusFragment;

public class SigninAsGuestActivity extends BaseActivity {

    Fragment selectedFragment;
    public BottomNavigationView bottomNavigationView;
    public String selectedFragmentstr = "aboutus";

    @Override
    public int getContentViewId() {
        return R.layout.signin_as_guest_activity;
    }

    @Override
    public void doOnCreate() throws Exception {
        initView();
        initListeners();
    }

    private void initView() {
        bottomNavigationView = _findViewById(R.id.bottom_navigation);

        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, AboutusFragment.newInstance());
        transaction.commit();
    }

    private void initListeners() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        boolean shouldReturn;
                        try {
                            switch (item.getItemId()) {
                                case R.id.action_aboutus:
                                    if (!selectedFragmentstr.equals("aboutus")) {
                                        selectedFragmentstr = "aboutus";
                                        selectedFragment = AboutusFragment.newInstance();
                                    }
                                    break;

                                case R.id.action_contactus:
                                    if (!selectedFragmentstr.equals("contactus")) {
                                        selectedFragmentstr = "contactus";
                                        selectedFragment = ContactusFragment.newInstance();
                                    }
                                    break;
                            }
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            if (transaction != null && selectedFragment != null) {
                                transaction.replace(R.id.frame_layout, selectedFragment);
                                transaction.commit();
                                shouldReturn = true;
                            } else {
                                shouldReturn = false;
                            }
                        } catch (Exception ex) {
                            Log.e("", ex.getLocalizedMessage());
                            shouldReturn = false;
                        }

                        return shouldReturn;
                    }
                });
    }
}
