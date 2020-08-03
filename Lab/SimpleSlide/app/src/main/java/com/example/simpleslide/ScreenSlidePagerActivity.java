package com.example.simpleslide;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;

public class ScreenSlidePagerActivity extends FragmentActivity {
    private static final int NUM_PAGES = 5;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_slide);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        //mPager.setBackgroundColor(Color.parseColor("#f56c42"));
        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
// Other codes
        //mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        pagerAdapter = new
                ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);

        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow
            //   the system to handle the
            // Back button. This calls finish() on this activity and pops
            //   the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    private class ScreenSlidePagerAdapter extends
            FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                mPager.setBackgroundColor(Color.parseColor("#f56c42"));
            if (position == 1)
                mPager.setBackgroundColor(Color.parseColor("#c5f542"));
            if (position == 2)
                mPager.setBackgroundColor(Color.parseColor("#7da7e3"));
            if (position == 3)
                mPager.setBackgroundColor(Color.parseColor("#e37d98"));
            if (position == 4)
                mPager.setBackgroundColor(Color.parseColor("#e37ddb"));
            Fragment fr = new ScreenSlidePageFragment();
            return fr;
        }
        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
