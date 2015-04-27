package syr.labs.jarvis.app.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import syr.labs.jarvis.app.R;
import syr.labs.jarvis.app.fragment.Screen1Fragment;

/**
 * Created by Bharat on 7/27/2014.
 */
public class CustomDialogClass extends Dialog implements View.OnClickListener {


    public Button button_done, button_cancel;
    AsyncDevicesLoad loadList;
    ListView devicesList;
    boolean flag;
    TextView devicesFoundTitle;
    Activity activity;
    LayoutInflater inflater;

    Screen1Fragment.Screen1FragmentListener mCallback;

    public CustomDialogClass(Activity activity,LayoutInflater inflater,AsyncDevicesLoad loadList,Screen1Fragment.Screen1FragmentListener mCallback) {
        super(activity);
        // TODO Auto-generated constructor stub
        this.mCallback = mCallback;
        this.activity = activity;
        this.inflater = inflater;
        this.loadList = loadList;
        devicesList = (ListView) activity.findViewById(R.id.devicesList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.scan_custom_dialog);

        WebView loading_view = (WebView)findViewById(R.id.loadingImageView);
        loading_view.loadUrl("file:///android_asset/loading.gif");

        button_done = (Button) findViewById(R.id.dialog_custom_done);
        button_cancel = (Button) findViewById(R.id.dialog_custom_cancel);

        button_done.setOnClickListener(this);
        button_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_custom_done:
                dismiss();
                loadList.cancel(true);
                break;
            case R.id.dialog_custom_cancel:
                activity.findViewById(R.id.devicesFoundTitle).setVisibility(View.INVISIBLE);
                AsyncDevicesLoad loadList = new AsyncDevicesLoad(inflater,false,devicesList,activity,mCallback);
                loadList.execute();
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}
