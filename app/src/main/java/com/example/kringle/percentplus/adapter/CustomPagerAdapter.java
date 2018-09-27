package com.example.kringle.percentplus.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.kringle.percentplus.fragments.BonusFragment;
import com.example.kringle.percentplus.fragments.CategoryFragment;
import com.example.kringle.percentplus.fragments.SearchFragment;

public class CustomPagerAdapter extends FragmentPagerAdapter {


    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new SearchFragment();
            case 1: return new CategoryFragment();
            case 2: return new BonusFragment();
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
            case 0: return "Поиск";
            case 1: return "Категория";
            case 2: return "Бонус";
            default: return null;
        }
    }
}
