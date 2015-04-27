package syr.labs.jarvis.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import syr.labs.jarvis.app.R;
import syr.labs.jarvis.app.dialog.AsyncDevicesLoad;
import syr.labs.jarvis.app.dialog.CustomDialogClass;

/**
 * Created by Bharat on 7/18/2014.
 */
public class Screen1Fragment extends Fragment {

    View rootView;
    public static String deviceName;
    LayoutInflater inflater;
    AsyncDevicesLoad loadList;
    CustomDialogClass customDialog;
    static final int TIME_OUT = 7000;
    static final int MSG_DISMISS_DIALOG = 0;

    Screen1FragmentListener mCallback;

    public interface Screen1FragmentListener {
        public void selectedPhoneName(String data);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (Screen1FragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public static Fragment newInstance(int someInt) {
        Screen1Fragment myFragment = new Screen1Fragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater mInflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflater = mInflater;
        rootView = inflater.inflate(R.layout.screen1_fragment, container, false);

        final Button scan_button = (Button) rootView.findViewById(R.id.scanButton);
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                TextView devicesFoundTitle = (TextView) rootView.findViewById(R.id.devicesFoundTitle);
                devicesFoundTitle.setVisibility(View.VISIBLE);

                ListView devicesList = (ListView) rootView.findViewById(R.id.devicesList);
                loadList = new AsyncDevicesLoad(inflater,true,devicesList,getActivity(),mCallback);
                loadList.execute();

                createDialog();
            }
        });
        return rootView;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_DISMISS_DIALOG:
                    if (customDialog != null && customDialog.isShowing()) {
                        customDialog.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void createDialog() {
        customDialog = new CustomDialogClass(getActivity(),inflater,loadList,mCallback);
        customDialog.show();
        mHandler.sendEmptyMessageDelayed(MSG_DISMISS_DIALOG, TIME_OUT);
    }

}
