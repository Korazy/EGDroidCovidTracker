package com.korazy.covidtracker.View;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.korazy.covidtracker.Adapter.TabAdapter;
import com.korazy.covidtracker.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends AppCompatActivity {

    private TabAdapter tabAdapter;
    private TabLayout statTabs;
    private ViewPager statPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        statPager = findViewById(R.id.statisticsPager);
        statTabs = findViewById(R.id.statisticsTabs);

        setupTabs();
    }

    //TODO check for internet connection

    private void setupTabs() {
        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new WorldFragment(), "World");
        tabAdapter.addFragment(new CountriesFragment(), "Countries");
        tabAdapter.addFragment(new SavedFragment(), "Saved");

        statPager.setAdapter(tabAdapter);
        statTabs.setupWithViewPager(statPager);

    }

}
