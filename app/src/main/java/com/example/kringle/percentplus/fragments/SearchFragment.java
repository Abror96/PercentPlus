package com.example.kringle.percentplus.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kringle.percentplus.R;
import com.example.kringle.percentplus.adapter.SearchPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment {

    @BindView(R.id.searchViewPager)
    ViewPager searchViewPager;
    @BindView(R.id.searchTabLayout)
    TabLayout searchTabLayout;
    SearchPagerAdapter searchPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);
        ButterKnife.bind(this, view);

        searchViewPager.bringToFront();
        searchPagerAdapter = new SearchPagerAdapter(getChildFragmentManager());
        searchTabLayout.setupWithViewPager(searchViewPager);
        searchViewPager.setAdapter(searchPagerAdapter);


        return view;
    }
}
