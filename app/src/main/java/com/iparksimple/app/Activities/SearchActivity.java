package com.iparksimple.app.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.iparksimple.app.Adapters.Pager;
import com.iparksimple.app.Fragments.ReportFragment;
import com.iparksimple.app.R;
import com.iparksimple.app.utils.Constants;

public class SearchActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView back,Image_filter;
    String selectedType="daily", type1="daily", type2="monthly", type3="airport";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if(getIntent().hasExtra(Constants.PreferenceConstants.TYPE)){
            selectedType = getIntent().getStringExtra(Constants.PreferenceConstants.TYPE);
        }
        back = findViewById(R.id.Image_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Image_filter = findViewById(R.id.Image_filter);
        Image_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this,FilterActivity.class);
                startActivity(intent);
            }
        });

        viewPager = findViewById(R.id.ViewPager);
        tabLayout = findViewById(R.id.Tab_layout);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if(selectedType.equalsIgnoreCase(type1)){
            tabLayout.getTabAt(0).select();
        }else if(selectedType.equalsIgnoreCase(type2)){
            tabLayout.getTabAt(1).select();
        }else{
            tabLayout.getTabAt(2).select();
        }

    }

    private void setupViewPager(ViewPager viewPager) {

        Pager adapter = new Pager(getSupportFragmentManager());
        adapter.addFragment(new ReportFragment(), "  Daily ");
        adapter.addFragment(new ReportFragment(), " Monthly");
        adapter.addFragment(new ReportFragment(),"Airport");
        viewPager.setAdapter(adapter);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(SearchActivity.this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }
}
