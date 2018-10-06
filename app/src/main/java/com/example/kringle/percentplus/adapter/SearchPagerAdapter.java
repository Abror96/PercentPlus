package com.example.kringle.percentplus.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.kringle.percentplus.fragments.searchFragments.ByAddressFragment;
import com.example.kringle.percentplus.fragments.searchFragments.ByCodeFragment;
import com.example.kringle.percentplus.fragments.searchFragments.ByNameFragment;

public class SearchPagerAdapter extends FragmentPagerAdapter {

    public SearchPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new ByAddressFragment();
            case 1: return new ByCodeFragment();
            case 2: return new ByNameFragment();
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
