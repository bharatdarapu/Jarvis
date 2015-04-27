package syr.labs.jarvis.app.dialog;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import syr.labs.jarvis.app.R;
import syr.labs.jarvis.app.activity.MainActivity;
import syr.labs.jarvis.app.adapter.DevicesAdapter;
import syr.labs.jarvis.app.bean.RowItem;
import syr.labs.jarvis.app.fragment.Screen1Fragment;

/**
 * Created by Bharat on 7/31/2014.
 */
public class AsyncDevicesLoad extends AsyncTask<Void, Integer, Long>{

    LayoutInflater inflater;
    Activity activity;
    private boolean flag;
    private ListView devicesListView;
    String[] deviceTitles;
    int[] deviceImages;
    DevicesAdapter adapter;
    List<RowItem> rowItems;
    public static String deviceName;

    Screen1Fragment.Screen1FragmentListener mCallback;

    public AsyncDevicesLoad(LayoutInflater inflater,boolean flag,ListView devicesListView, Activity activity,Screen1Fragment.Screen1FragmentListener mCallback) {
        this.mCallback = mCallback;
        this.inflater = inflater;
        this.flag = flag;
        this.devicesListView = devicesListView;
        this.activity = activity;
    }


    @Override
    protected void onPreExecute() {

        rowItems = new ArrayList<RowItem>();


        if(flag)
        {
            deviceTitles = new String[]{"Bharat's Phone",
                    "Max's Android",};

            deviceImages = new int[]{R.drawable.phone_image1,R.drawable.phone_image2};


            for (int i = 0; i < deviceTitles.length; i++) {
                RowItem item = new RowItem(deviceImages[i], deviceTitles[i]);
                rowItems.add(item);
            }
        }
        else
        {
            deviceTitles = new String[]{};

            deviceImages = new int[]{};

            for (int i = 0; i < deviceTitles.length; i++) {
                RowItem item = new RowItem(deviceImages[i], deviceTitles[i]);
                rowItems.add(item);
            }
        }

        adapter = new DevicesAdapter(inflater, rowItems);
        devicesListView.setAdapter(adapter);

        devicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Activity activity1 = activity;

                TextView phoneName = (TextView)view.findViewById(R.id.phoneName);
                String value = phoneName.getText().toString();

                mCallback.selectedPhoneName(value);

                MainActivity activity = (MainActivity) activity1;
                activity.switchTab(1);


            }
        });
    }

    @Override
    protected Long doInBackground(Void... params) {
        if(flag) {
            int i = 3;
            while (i < 7 && flag == true) {
                try {
                    publishProgress(i);
                    Thread.sleep(3000);
                    i++;
                } catch (Exception e) {
                    Log.i("AsyncDevicesLoad", "DoInBackground--" + e.getMessage());
                }
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        RowItem item;

        switch(values[0])
        {
            case 3:
                 item = new RowItem(R.drawable.phone_image3,"Harry's Moto");
                rowItems.add(item);
                        break;
            case 4:
                item = new RowItem(R.drawable.phone_image4,"Blackberry Curve");
                rowItems.add(item);
                break;
            case 5:
                item = new RowItem(R.drawable.phone_image5,"My Ubuntu Phone");
                rowItems.add(item);
                break;
            case 6:
                 item = new RowItem(R.drawable.phone_image6,"XX_ Naming Error _XX");
                rowItems.add(item);
                break;

        }
        adapter.notifyDataSetChanged();
   }

    @Override
    protected void onPostExecute(Long result) {
        super.onPostExecute(result);
    }

}
