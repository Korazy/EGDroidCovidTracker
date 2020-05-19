package com.korazy.covidtracker.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.korazy.covidtracker.Interface.onCovidReceivedCallback;
import com.korazy.covidtracker.Model.Country;
import com.korazy.covidtracker.Network.RequestManager;
import com.korazy.covidtracker.R;

public class CountryActivity extends AppCompatActivity implements onCovidReceivedCallback {

    private TextView countryName;
    private TextView newCases;
    private TextView totalCases;
    private TextView recoveredCases;
    private TextView activeCases;
    private TextView criticalCases;
    private TextView newDeaths;
    private TextView totalDeaths;

    private String country;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        countryName = findViewById(R.id.tv_countryName);
        newCases = findViewById(R.id.tv_newAmount);
        totalCases = findViewById(R.id.tv_totalAmount);
        recoveredCases = findViewById(R.id.tv_recoveredAmount);
        activeCases = findViewById(R.id.tv_activeAmount);
        criticalCases = findViewById(R.id.tv_criticalAmount);
        newDeaths = findViewById(R.id.tv_deathsNewAmount);
        totalDeaths = findViewById(R.id.tv_deathsTotalAmount);

        country = getIntent().getStringExtra("country");
        countryName.setText(country);

        RequestManager covidRequest = new RequestManager(this);
        covidRequest.fetchData(RequestManager.RequestType.STATISTICS, country);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        this.menu = menu;
        SharedPreferences sharedPref = getSharedPreferences("countries", Context.MODE_PRIVATE);
        if(sharedPref.contains(country))
            menu.getItem(0).setIcon(getDrawable(R.drawable.ic_favorite_black_24dp));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorite) {
            toggleFavorite();
            Toast.makeText(CountryActivity.this, "Favorite Toggled!", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setCovidData(Country country) {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        newCases.setText(country.getNewCases());
        totalCases.setText(country.getTotalCases()+"");
        recoveredCases.setText(country.getRecoveredCases()+"");
        activeCases.setText(country.getActiveCases()+"");
        criticalCases.setText(country.getCriticalCases()+"");
        newDeaths.setText(country.getNewDeaths());
        totalDeaths.setText(country.getTotalDeaths()+"");
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void toggleFavorite(){
        SharedPreferences sharedPref = getSharedPreferences("countries", Context.MODE_PRIVATE);
        if(sharedPref.contains(country)){
            sharedPref.edit().remove(country).commit();
            menu.getItem(0).setIcon(getDrawable(R.drawable.ic_favorite_border_black_24dp));
        } else {
            sharedPref.edit().putString(country, country).commit();
            menu.getItem(0).setIcon(getDrawable(R.drawable.ic_favorite_black_24dp));
        }

    }
}
