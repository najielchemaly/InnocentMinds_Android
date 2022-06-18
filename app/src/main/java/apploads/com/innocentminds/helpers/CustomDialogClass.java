package apploads.com.innocentminds.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import apploads.com.innocentminds.R;
import apploads.com.innocentminds.helpers.utils.AppUtils;
import apploads.com.innocentminds.helpers.utils.StringUtils;

public class CustomDialogClass extends Dialog {

    public Activity c;
    public Button yes, no;
    private String title = "";
    private String message = "";
    private TextView txtTitle;
    private TextView txtMessage;
    private ImageView imgMessage;
    private boolean isSingleButton;
    private AbstractCustomDialogListener listener;
    String btnYesTitle, btnNoTitle;

    public CustomDialogClass(Activity a, AbstractCustomDialogListener listener, boolean isSingleButton) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.isSingleButton = isSingleButton;
        this.listener = listener;
        this.listener.setDialog(this);
        this.listener.setDialog(this);
    }

    public CustomDialogClass(Activity a, AbstractCustomDialogListener listener, boolean isSingleButton, String btnYes, String btnNo) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.isSingleButton = isSingleButton;
        this.listener = listener;
        this.btnYesTitle = btnYes;
        this.btnNoTitle = btnNo;
        this.listener.setDialog(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        yes = findViewById(R.id.btn_yes);
        no = findViewById(R.id.btn_no);
        txtTitle = findViewById(R.id.txtTitle);
        txtMessage = findViewById(R.id.txtMessage);
        imgMessage = findViewById(R.id.imgMessage);
        yes.setOnClickListener(listener);
        no.setOnClickListener(listener);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        txtTitle.setText(title);
        txtMessage.setText(message);

        txtMessage.setTypeface(AppUtils.getRegularTypeface(c));
        txtTitle.setTypeface(AppUtils.getRegularTypeface(c));
        yes.setTypeface(AppUtils.getRegularTypeface(c));
        no.setTypeface(AppUtils.getRegularTypeface(c));

        imgMessage.bringToFront();

        if(isSingleButton){
            no.setVisibility(View.GONE);
            yes.setText("OK");
            yes.setBackground(getContext().getDrawable(R.drawable.border_yellow_button));

            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(300,130);
            parms.setMargins(0,10,0,10);
            yes.setLayoutParams(parms);

            yes.setGravity(Gravity.CENTER);
        }

        if(StringUtils.isValid(btnYesTitle) && StringUtils.isValid(btnNoTitle)){
            yes.setText(btnYesTitle);
            no.setText(btnNoTitle);
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DialogResponse{
        private Activity activity;
        private Dialog dialog;
        private View btnClicked;

        public DialogResponse(Activity activity, Dialog dialog, View btnClicked) {
            this.activity = activity;
            this.dialog = dialog;
            this.btnClicked = btnClicked;
        }

        public Activity getActivity() {
            return activity;
        }

        public Dialog getDialog() {
            return dialog;
        }

        public View getBtnClicked() {
            return btnClicked;
        }
    }

    private interface CustomDialogListener extends View.OnClickListener{
        void onConfirm(DialogResponse response);
        void onCancel(DialogResponse dialogResponse);
    }

    public static abstract class AbstractCustomDialogListener implements CustomDialogListener{
        private CustomDialogClass dialog;

        public void setDialog(CustomDialogClass dialog) {
            this.dialog = dialog;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_yes:
                  onConfirm(new DialogResponse(dialog.c, dialog, view));
                    break;
                case R.id.btn_no:
                    onCancel(new DialogResponse(dialog.c, dialog, view));
                    break;
                default:
                    break;
            }
        }
    }
}