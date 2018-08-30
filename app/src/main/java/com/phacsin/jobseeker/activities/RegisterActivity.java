package com.phacsin.jobseeker.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;

import com.phacsin.jobseeker.R;
import com.phacsin.jobseeker.custom.CustomViewPager;
import com.phacsin.jobseeker.custom.FixedSpeedScroller;
import com.phacsin.jobseeker.fragments.CredentialFragment;
import com.phacsin.jobseeker.fragments.InterestFragment;
import com.phacsin.jobseeker.fragments.OptionalDetailsFragment;
import com.phacsin.jobseeker.fragments.TypeFragment;
import com.shuhart.stepview.StepView;

import java.lang.reflect.Field;

public class RegisterActivity extends AppCompatActivity {

    StepView stepView;
    private static final int NUM_PAGES = 4;
    private CustomViewPager mPager;
    private PagerAdapter mPagerAdapter;
    Button btn_next,btn_prev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        stepView = findViewById(R.id.step_view);
        btn_next = findViewById(R.id.btn_next);
        btn_prev = findViewById(R.id.btn_prev);
        mPager = findViewById(R.id.viewpager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(0);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0)
                    btn_prev.setVisibility(View.GONE);
                else if(position==3)
                    btn_next.setVisibility(View.GONE);
                else
                {
                    btn_prev.setVisibility(View.VISIBLE);
                    btn_next.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(mPager.getCurrentItem()+1,false);
                stepView.go(mPager.getCurrentItem(), true);
            }
        });
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(mPager.getCurrentItem()-1,false);
                stepView.go(mPager.getCurrentItem(), true);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
                    return new CredentialFragment();
                case 1:
                    return new OptionalDetailsFragment();
                case 2:
                    return new InterestFragment();
                case 3:
                    return new TypeFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
