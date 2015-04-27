package syr.labs.jarvis.app.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import syr.labs.jarvis.app.R;
import syr.labs.jarvis.app.adapter.TabsPagerAdapter;
import syr.labs.jarvis.app.custom.ViewPagerCustomAnimation;
import syr.labs.jarvis.app.fragment.Screen1Fragment;
import syr.labs.jarvis.app.fragment.Screen2Fragment;
import syr.labs.jarvis.app.fragment.Screen3Fragment;
import syr.labs.jarvis.app.fragment.Screen4Fragment;


public class MainActivity extends FragmentActivity implements ActionBar.TabListener, Screen1Fragment.Screen1FragmentListener, Screen2Fragment.Screen2FragmentListener, Screen3Fragment.Screen3FragmentListener{

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;

   private String[] tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initilization
        tabs = getResources().getStringArray(R.array.tab_names);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        //Animation for view pager
        viewPager.setPageTransformer(true, new ViewPagerCustomAnimation());


        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(1);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }


        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                mAdapter.notifyDataSetChanged();
                getActionBar().setSelectedNavigationItem(position);
            }
        });


    }

    public void switchTab(int position){
        getActionBar().setSelectedNavigationItem(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }else
        if (id == R.id.action_help )
        {
           Toast.makeText(getApplicationContext()," Developed by Bharath",Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
        getActionBar().setSelectedNavigationItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void selectedPhoneName(String data) {
        Screen2Fragment newFragment = new Screen2Fragment();
        Bundle args = new Bundle();
        args.putString("phoneName", data);
        newFragment.setArguments(args);

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.screen1fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void selectedPhoneNameTimer(String phoneName, int minutesValue, int secondsValue) {

        Screen3Fragment newFragment =new Screen3Fragment();
        Bundle args = new Bundle();
        args.putString("phoneName", phoneName);
        args.putInt("minutesValue",minutesValue);
        args.putInt("secondsValue", secondsValue);
        newFragment.setArguments(args);

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.screen2_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    @Override
    public void selectedPhoneNameTimerResult(String phoneName, int totalTime) {

        Screen4Fragment newFragment = new Screen4Fragment();
        Bundle args = new Bundle();
        args.putString("phoneName", phoneName);
        args.putInt("totalTime", totalTime);


        newFragment.setArguments(args);

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.screen3_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
