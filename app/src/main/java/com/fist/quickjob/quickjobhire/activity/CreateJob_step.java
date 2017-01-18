package com.fist.quickjob.quickjobhire.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.badoualy.stepperindicator.StepperIndicator;
import com.example.quickjob.quickjobhire.R;
import com.fist.quickjob.quickjobhire.fragment.ListJobFragment;
import com.fist.quickjob.quickjobhire.fragment.createjob_step_one;
import com.fist.quickjob.quickjobhire.fragment.createjob_steptwo;
import com.fist.quickjob.quickjobhire.other.CustomViewPager;


/**
 * Created by VinhNguyen on 1/8/2017.
 */

public class CreateJob_step extends AppCompatActivity {
    Toolbar mToolbar;
   static CustomViewPager viewPager;
   static StepperIndicator indicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_step_main);

        initToolbar();
        initViewPagerAndTabs();
    }
    private void initToolbar() {
        //activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle(getString(R.string.app_name));
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));


    }
    private void initViewPagerAndTabs() {
        viewPager = (CustomViewPager) findViewById(R.id.view_pager);
        MainActivity.PagerAdapter pagerAdapter = new MainActivity.PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new createjob_step_one(),"");
        pagerAdapter.addFragment(new createjob_steptwo(), "");
        pagerAdapter.addFragment(new ListJobFragment(), "");
        pagerAdapter.addFragment(new SettingActivity(), "");
        viewPager.setAdapter(pagerAdapter);
        indicator = (StepperIndicator) findViewById(R.id.stepper);
        indicator.setViewPager(viewPager);

    }
    public static void setStepper(int a){
        indicator.setCurrentStep(a);
        viewPager.setCurrentItem(a);
    }

}
