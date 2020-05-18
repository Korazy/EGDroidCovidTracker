package com.korazy.covidtracker.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
}
