package com.mukul.panorbit.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mukul.panorbit.fragments.List_medicine;
import com.mukul.panorbit.fragments.Today_intake;
import com.mukul.panorbit.fragments.Profile;


/**
 * Created by Mukul on 14-06-2017.
 */


public class PageAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Today_intake tab1 = new Today_intake();
                return tab1;
            case 1:
                List_medicine tab2 = new List_medicine();
                return tab2;
            case 2:
                Profile tab3 = new Profile();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}