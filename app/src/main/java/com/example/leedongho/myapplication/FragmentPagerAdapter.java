package com.example.leedongho.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {
    private final int PAGE_COUNT = 4;
    private String[] tabTitles = new String[] {"News", "Game", "Info", "TEST"};

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new FragmentNewsPage();
            case 1: return new FragmentGamePage();
            case 2: return new FragmentInfoPage();
            case 3: return new FragmentTestPage();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
