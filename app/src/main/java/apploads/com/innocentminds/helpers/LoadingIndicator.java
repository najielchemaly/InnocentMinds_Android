package apploads.com.innocentminds.helpers;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class LoadingIndicator extends RelativeLayout {

    public LoadingIndicator(Context context) {
        super(context);
    }

    public LoadingIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LoadingIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void show() {
        this.setVisibility(VISIBLE);
    }

    public void hide() {
        this.setVisibility(INVISIBLE);
    }
}
