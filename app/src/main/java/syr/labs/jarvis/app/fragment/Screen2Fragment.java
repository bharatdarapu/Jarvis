package syr.labs.jarvis.app.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import syr.labs.jarvis.app.R;
import syr.labs.jarvis.app.activity.MainActivity;
import syr.labs.jarvis.app.custom.TimerKnobView;

/**
 * Created by kevin on 7/6/2014.
 */
public class Screen2Fragment extends Fragment {

    View rootView;
    private static String phoneName = "No Device Selected";

    public static Screen2Fragment newInstance(int sectionNumber) {
        Screen2Fragment fragment = new Screen2Fragment();
        return fragment;
    }

    public Screen2Fragment() {
    }

    Screen2FragmentListener mCallback;

    public interface Screen2FragmentListener {
        public void selectedPhoneNameTimer(String phoneName,int minuteValue,int secondValue);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (Screen2FragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.screen2_fragment, container, false);
        Button timerSetButton = (Button) rootView.findViewById(R.id.timerSetButton);

        if(getArguments()!=null ) {
            phoneName = getArguments().getString("phoneName");
        }

        TextView phoneTitleHead = (TextView) rootView.findViewById(R.id.phoneTitleHead);
        phoneTitleHead.setText(phoneName);

            final TimerKnobView rotaryKnob1 = (TimerKnobView) rootView.findViewById(R.id.rotaryKnobView);
            final NumberPicker minutePicker = (NumberPicker) rootView.findViewById(R.id.minutePicker);
            final NumberPicker secondPicker = (NumberPicker) rootView.findViewById(R.id.secondPicker);

            minutePicker.setMaxValue(60);
            minutePicker.setMinValue(0);
            minutePicker.setValue(0);

            secondPicker.setMaxValue(60);
            secondPicker.setMinValue(0);
            secondPicker.setValue(0);


            rotaryKnob1.setOnRotateListener(new TimerKnobView.OnRotateListener() {
                @Override
                public void onRotate(int arg) {
                   secondPicker.setValue(arg / 6);
                }
            });

            secondPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    rotaryKnob1.setAngle(newVal * 6);
                    //secondsvalue = newVal;
                }
            });

            minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    //minutevalue = newVal;
                }
            });

            timerSetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   mCallback.selectedPhoneNameTimer(phoneName,minutePicker.getValue(),secondPicker.getValue());
                   MainActivity activity = (MainActivity) getActivity();
                   activity.switchTab(2);
                }
            });

        return rootView;
    }

}