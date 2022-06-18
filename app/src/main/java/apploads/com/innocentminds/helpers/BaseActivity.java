package apploads.com.innocentminds.helpers;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.content.pm.ActivityInfo;

import apploads.com.innocentminds.homepage.HomepageActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected final static int NULL_CONTENT_VIEW = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            int contentViewId = getContentViewId();
            if (contentViewId != NULL_CONTENT_VIEW) {
                setContentView(getContentViewId());
            }
            doOnCreate();

        } catch (Exception e) {
            Log.e("Crash", "Error in: " + e.getMessage());
        }
//        throw new RuntimeException("This is a crash");
    }

    @Override
    protected void onResume() {
        super.onResume();

        StaticData.context = this;

        if (this instanceof HomepageActivity) {
            StaticData.shouldFinish = false;
        }

        if (StaticData.shouldFinish) {
            finish();
        }
    }

    /**
     * Retune the content view of the activity
     *
     * @return
     */
    public abstract int getContentViewId();

    public abstract void doOnCreate() throws Exception;

    /**
     * Change the color of the window status bar
     *
     * @param colorId
     */
    protected void setWindowStatusBarColor(int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(colorId));
        }
    }

    /**
     * Return the view by type
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T> T _findViewById(int viewId) {
        return (T) findViewById(viewId);
    }

//    public void showToastyMessage(Activity activity, String message){
//        Toasty.custom(activity,message,getDrawable(R.drawable.ball), Color.parseColor("#071a7b"), Toast.LENGTH_SHORT,true,true).show();
//    }
}
