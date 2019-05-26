package com.service.bus.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.service.bus.fragment.IssueFragment;
import com.service.bus.fragment.RenewFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerAdapter(FragmentManager fm, int tabCount )//int tabCount)
     {
        super(fm);
        this.tabCount = tabCount;
        //Initializing tab count
        //this.tabCount= tabCount;
    }



    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                RenewFragment tab1 = new RenewFragment();
                return tab1;
            case 1:
                IssueFragment tab2 = new IssueFragment();
                return tab2;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}