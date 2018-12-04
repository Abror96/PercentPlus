package com.procentplus.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.procentplus.fragments.searchFragments.ByAddressFragment;
import com.procentplus.fragments.searchFragments.ByCodeFragment;
import com.procentplus.fragments.searchFragments.ByNameFragment;

public class SearchPagerAdapter extends FragmentPagerAdapter {

    public SearchPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new ByAddressFragment();
            case 1: return new ByNameFragment();
            case 2: return new ByCodeFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "По адресу";
            case 1: return "По названию";
            case 2: return "По коду объекта";
            default: return null;
        }
    }
}
