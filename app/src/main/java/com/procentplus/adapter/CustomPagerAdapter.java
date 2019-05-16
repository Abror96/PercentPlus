package com.procentplus.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.procentplus.fragments.BonusFragment;
import com.procentplus.fragments.CategoryFragment;
import com.procentplus.fragments.SearchFragment;

public class CustomPagerAdapter extends FragmentPagerAdapter {

    private Bundle data;

    public CustomPagerAdapter(FragmentManager fm, Bundle data) {
        super(fm);
        this.data = data;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new SearchFragment();
            case 1: return new CategoryFragment();
            case 2:
                final BonusFragment bonusFragment = new BonusFragment();
                bonusFragment.setArguments(this.data);
                return bonusFragment;
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
