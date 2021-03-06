package com.deedat.nkrumahistcircle.adapter;


import com.deedat.nkrumahistcircle.HomeFragment;
import com.deedat.nkrumahistcircle.NewFragment;

import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;



public class pageAdapter extends FragmentPagerAdapter {
    private int numOfTabs;

    public pageAdapter(FragmentManager fm,int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }
//@NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
              return   new HomeFragment();
            case 1:
             //    return new ChatActivity();
            case 2:
                  return new NewFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return   "Home";
            case 1:
                return "Chats";
            case 2:
                return "News";
            default:
                return null;
        }
    }
}
