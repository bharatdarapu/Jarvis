package syr.labs.jarvis.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import syr.labs.jarvis.app.fragment.Screen1Fragment;
import syr.labs.jarvis.app.fragment.Screen2Fragment;
import syr.labs.jarvis.app.fragment.Screen3Fragment;
import syr.labs.jarvis.app.fragment.Screen4Fragment;

/**
 * Created by Bharat on 7/18/2014.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    FragmentManager fragmentManager;

    public TabsPagerAdapter(FragmentManager fm ) {
        super(fm);
        this.fragmentManager = fm;

    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index)
        {
            case 0:
              return Screen1Fragment.newInstance(0);
            case 1:
              return Screen2Fragment.newInstance(0);
            case 2:
              return Screen3Fragment.newInstance(0);
            case 3:
              return Screen4Fragment.newInstance(0);
            default:
              return Screen1Fragment.newInstance(0);
        }
    }
}
