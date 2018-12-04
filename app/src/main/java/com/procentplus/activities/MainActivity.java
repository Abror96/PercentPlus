package com.procentplus.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.procentplus.ProgressDialog.DialogConfig;
import com.procentplus.R;
import com.procentplus.SharedPrefs.PrefConfig;
import com.procentplus.adapter.CustomPagerAdapter;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    @BindView(R.id.mainViewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.preloader)
    ConstraintLayout preloader_view;
    @BindView(R.id.main_logo)
    ImageView main_logo;
    private PagerAdapter pagerAdapter;
    private int tabPosition = 0;
    private String objectName;
    private Bundle objectNameBundle;

    public static PrefConfig prefConfig;
    public static DialogConfig dialogConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        prefConfig = new PrefConfig(this);
        dialogConfig = new DialogConfig(this, "Идет загрузка");
        if (savedInstanceState != null) {
            return;
        }

        // animating logo
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                animateLogo();
            }
        }, 0, 1000);

        // if user isn't logged in
        if (!prefConfig.readLoginStatus()) {
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {

                            Intent auth_intent = new Intent(MainActivity.this, AuthActivity.class);
                            startActivity(auth_intent);
                            finish();

                        }
                    }, 1800);
        }

        if (getIntent().getIntExtra("tab_id", -1) != -1) {
            tabPosition = getIntent().getExtras().getInt("tab_id");
            objectName = getIntent().getStringExtra("object_name");
            int object_id = getIntent().getExtras().getInt("object_id");


            objectNameBundle = new Bundle();
            objectNameBundle.putString("object_name", objectName);
            objectNameBundle.putInt("object_id", object_id);

            preloader_view.setVisibility(View.GONE);
            tabLayout.setVisibility(View.VISIBLE);

            initViewPager();
        } else {
            preloader();
        }

    }

    private void animateLogo() {
        final Animation anim_out = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        try {
            anim_out.setAnimationListener(new Animation.AnimationListener()
            {
                @Override public void onAnimationStart(Animation animation) {}
                @Override public void onAnimationRepeat(Animation animation) {}
                @Override public void onAnimationEnd(Animation animation)
                {
                    anim_in.setAnimationListener(new Animation.AnimationListener() {
                        @Override public void onAnimationStart(Animation animation) {}
                        @Override public void onAnimationRepeat(Animation animation) {}
                        @Override public void onAnimationEnd(Animation animation) {}
                    });
                    main_logo.startAnimation(anim_in);

                }
            });
            main_logo.startAnimation(anim_out);
        } catch (Exception e) {
            Log.d("LOGGER MainActivity", "animateLogo: " + e.getMessage());
        }
    }

    private void initViewPager() {
        pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), objectNameBundle);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pagerAdapter);
        if (getIntent().getIntExtra("tab_id", -1) != -1)
            viewPager.setCurrentItem(tabPosition);
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
                }, 3000);

    }
}


