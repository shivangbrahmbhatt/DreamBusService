package com.service.bus.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.service.bus.adapter.PagerAdapter;
import com.service.dream.bus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        tabLayout.addTab(tabLayout.newTab().setText("Renew Pass").setIcon(R.drawable.ic_autorenew_white_24dp));
        tabLayout.addTab(tabLayout.newTab().setText("Issue Pass").setIcon(R.drawable.ic_assignment_ind_black_24dp));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewpager.setAdapter(adapter);

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        tab.setIcon(R.drawable.ic_autorenew_white_24dp);
        tab.setIcon(R.drawable.ic_assignment_ind_white_24dp);
        viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        tab.setIcon(R.drawable.ic_autorenew_black_24dp);
        tab.setIcon(R.drawable.ic_assignment_ind_black_24dp);
        viewpager.setCurrentItem(tab.getPosition());

    }
    @Override
    public void onTabReselected(TabLayout.Tab tab) {


    }
}