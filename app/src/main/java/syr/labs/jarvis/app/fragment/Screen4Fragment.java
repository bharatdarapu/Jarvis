package syr.labs.jarvis.app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import syr.labs.jarvis.app.R;
import syr.labs.jarvis.app.adapter.WebsiteResultsAdapter;
import syr.labs.jarvis.app.bean.WebsiteResultItem;

/**
 * Created by Bharat on 8/6/2014.
 */
public class Screen4Fragment extends Fragment {

    View rootView;
    private static int totalTime = 0;
    private static boolean flag = false;
    private static String phoneName = "No Device Selected";
    private static double minutes = 0;
    private static double seconds = 0;
    public static Screen4Fragment newInstance(int someInt) {
        Screen4Fragment myFragment = new Screen4Fragment();
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.screen4_fragment, null);

        TextView websitesFoundTitle = (TextView) rootView.findViewById(R.id.websitesFoundTitle);


        if(getArguments()!=null)
        {
            phoneName = getArguments().getString("phoneName");
            totalTime = getArguments().getInt("totalTime");
            flag = true;
        }

        if(flag)
         showresults();
        websitesFoundTitle.setText("("+phoneName+": "+totalTime+" s)");
        return rootView;
    }

    public String doubleDigitFormat(double minutes)
    {
        if (minutes < 10)
        {
            return ("0" + minutes);
        }
        return ""+minutes;
    }


    public void showresults(){

        final WebsiteResultItem[] ObjectItemData = new WebsiteResultItem[14];

        ObjectItemData[0] = new WebsiteResultItem("00:00", "http://www.cis.syr.edu/~wedu/Teaching/android/");
        ObjectItemData[1] = new WebsiteResultItem("00:05", "http://stackoverflow.com/questions/13198669/any-way-to-discover-android-devices-on-your-network");
        ObjectItemData[2] = new WebsiteResultItem("00:15", "http://stackoverflow.com/questions/6792130/network-device-discovery");
        ObjectItemData[3] = new WebsiteResultItem("00:20", "http://www.phonearena.com/news/These-are-the-11-best-designed-apps-according-to-Googles-Android-team_id50732");
        ObjectItemData[4] = new WebsiteResultItem("00:25", "http://thenextweb.com/apps/2013/11/04/30-beautifully-designed-android-apps/24/");
        ObjectItemData[5] = new WebsiteResultItem("00:30", "http://www.cis.syr.edu/~wedu/Teaching/android/");
        ObjectItemData[6] = new WebsiteResultItem("00:35", "http://www.businessinsider.com/the-12-best-looking-apps-on-android-2013-8?op=1");
        ObjectItemData[7] = new WebsiteResultItem("00:40", "http://stackoverflow.com/questions/7264261/android-the-fastest-way-to-load-and-play-sound-in-application");
        ObjectItemData[8] = new WebsiteResultItem("00:45", "http://www.curious-creature.org/2007/08/01/rounded-corners-and-shadow-for-dialogs-extreme-gui-makeover/");
        ObjectItemData[9] = new WebsiteResultItem("00:50", "http://stackoverflow.com/questions/13341560/how-to-create-a-custom-dialog-box-in-android");
        ObjectItemData[10] = new WebsiteResultItem("00:52", "http://blog.iangclifton.com/2014/01/22/mobile-time-pickers/");
        ObjectItemData[11] = new WebsiteResultItem("00:54", "https://searchcode.com/codesearch/view/39719071/");
        ObjectItemData[12] = new WebsiteResultItem("00:56", "http://wptrafficanalyzer.in/blog/android-dynamic-chart-using-achartengine/");
        ObjectItemData[13] = new WebsiteResultItem("00:58", "http://colintmiller.com/how-to-add-text-over-a-progress-bar-on-android/");


        // our adapter instance
        WebsiteResultsAdapter adapter = new WebsiteResultsAdapter(getActivity(), R.layout.website_results_rows, ObjectItemData);

        // create a new ListView, set the adapter and item click listener
        final ListView websiteResultsList = (ListView) rootView.findViewById(R.id.websiteResultList);
        websiteResultsList.setAdapter(adapter);

        websiteResultsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(),"Url visited on : "+ObjectItemData[position].time,Toast.LENGTH_SHORT).show();

            }
        });

        websiteResultsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                String url =  ObjectItemData[position].websiteUrl;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                intent.addCategory("android.intent.category.BROWSABLE");
                startActivity(intent);
                return true;
            }
        });

             }

         }
