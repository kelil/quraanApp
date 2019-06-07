package com.harar.khalil.quraan.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.harar.khalil.quraan.activity.MainActivity;
import com.harar.khalil.quraan.databases.DatabaseHelp;

public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    private int tabCount;
    private DatabaseHelp databaseHelp;
    private MainActivity context;

    //Constructor to the class
    public Pager(MainActivity context, FragmentManager fm, int tabCount, DatabaseHelp databaseHelp) {
        super(fm);
        //Initializing tab count
        this.context = context;
        this.tabCount = tabCount;
        this.databaseHelp = databaseHelp;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs 
        switch (position) {
            case 0:
                SuraFragment suraFragment = SuraFragment.newInstance(databaseHelp);
                return suraFragment;
            case 1:
                return MuzhafFragment.newInstance(5);

            case 2:
                return new BookMarkFragment();
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