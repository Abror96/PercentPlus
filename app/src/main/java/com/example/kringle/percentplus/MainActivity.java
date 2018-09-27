package com.example.kringle.percentplus;

import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.kringle.percentplus.adapter.CustomPagerAdapter;
import com.example.kringle.percentplus.fragments.BonusFragment;
import com.example.kringle.percentplus.fragments.CategoryFragment;
import com.example.kringle.percentplus.fragments.SearchFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    @BindView(R.id.mainViewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.preloader)
    ConstraintLayout preloader_view;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        preloader();

    }

    private void initViewPager() {
        pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pagerAdapter);
        setTabIcons();
    }

    private void setTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.search).getIcon()
                .setColorFilter(getResources()
                        .getColor(R.color.tabIconColor), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).setIcon(R.drawable.category).getIcon()
                .setColorFilter(getResources()
                        .getColor(R.color.tabIconColor), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).setIcon(R.drawable.percent).getIcon()
                .setColorFilter(getResources()
                        .getColor(R.color.tabIconColor), PorterDuff.Mode.SRC_IN);
    }

    private void preloader() {
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        preloader_view.setVisibility(View.GONE);
                        tabLayout.setVisibility(View.VISIBLE);
                        initViewPager();
                    }
                }, 1500);

    }
}
