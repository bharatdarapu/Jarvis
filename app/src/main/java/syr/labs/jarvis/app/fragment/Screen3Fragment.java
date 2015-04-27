package syr.labs.jarvis.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import syr.labs.jarvis.app.R;
import syr.labs.jarvis.app.activity.MainActivity;

/**
 * Created by Bharat on 7/18/2014.
 */
public class Screen3Fragment extends Fragment {

    private Handler repeatUpdateHandler = new Handler();
    private boolean mAutoIncrement = false;
    private boolean mAutoDecrement = false;

    View rootView;
    TextView phoneTitle;
    int count;
    static int minutesvalue = 0;
    static int secondsvalue = 0;
    static int totalSeconds;
    private static String phoneName = "No Device Selected";

    ProgressBar circular_progressBar;
    Button progress_doneButton;

    public static Screen3Fragment newInstance(int someInt) {
        Screen3Fragment myFragment = new Screen3Fragment();
        return myFragment;
    }

    public Screen3Fragment() {
    }


    Screen3FragmentListener mCallback;

    public interface Screen3FragmentListener {
        public void selectedPhoneNameTimerResult(String phoneName,int totalTime);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (Screen3FragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    class RptUpdater implements Runnable {


        public void run() {
            if( mAutoIncrement ){
                increment();
                repeatUpdateHandler.postDelayed( new RptUpdater(), 1000 );
            }
            else
            if( mAutoDecrement ){
                decrement();
                repeatUpdateHandler.postDelayed( new RptUpdater(), 20 * totalSeconds );
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.screen3_fragment, null);

        phoneTitle = (TextView) rootView.findViewById(R.id.screen3_fragment_phonename);
        if(getArguments()!=null)
        {
            phoneName = getArguments().getString("phoneName");
            minutesvalue = getArguments().getInt("minutesValue");
            secondsvalue = getArguments().getInt("secondsValue");
        }
        phoneTitle.setText(minutesvalue*60+secondsvalue+" seconds timer set for "+phoneName);

        totalSeconds = minutesvalue*60+secondsvalue;

        final TextView progressStatus = (TextView) rootView.findViewById(R.id.progress_status);

        progress_doneButton = (Button) rootView.findViewById(R.id.progress_doneButton);


        circular_progressBar = (ProgressBar)rootView.findViewById(R.id.circular_progressBar);
        circular_progressBar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(mAutoDecrement) {
                   progressStatus.setText("Resume");
                   mAutoDecrement = false;
               }
                else if(!mAutoDecrement) {
                   progressStatus.setText("Pause");
                   mAutoDecrement = true;
                    repeatUpdateHandler.post( new RptUpdater() );
                }
            }
        });
        /*circular_progressBar.setOnLongClickListener(
                new View.OnLongClickListener() {
                    public boolean onLongClick(View arg0) {
                        mAutoIncrement = true;
                        repeatUpdateHandler.post(new RptUpdater());
                        return false;
                    }
                }
        );*/


        /*circular_progressBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                if( (event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL)
                        && mAutoIncrement ){
                    mAutoIncrement = false;
                }
                return false;

            }
        });*/

        return rootView;
    }

    public void increment(){
        if(circular_progressBar.getProgress()<100)
            circular_progressBar.incrementProgressBy(2);
    }


    public void decrement(){
        if(circular_progressBar.getProgress()>0){
            circular_progressBar.incrementProgressBy(-2);
        }
        else
        {
            mAutoDecrement = false;
            progress_doneButton.setEnabled(true);
            progress_doneButton.setBackgroundResource(R.drawable.progress_button);
            progress_doneButton.setPressed(true);
            progress_doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    circular_progressBar.setProgress(100);
                    mCallback.selectedPhoneNameTimerResult(phoneName,totalSeconds);
                    MainActivity activity = (MainActivity) getActivity();
                    activity.switchTab(3);
                }
            });
        }
    }
}
