package com.korazy.covidtracker.View;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blongho.country_data.World;
import com.korazy.covidtracker.Interface.onCovidReceivedCallback;
import com.korazy.covidtracker.Model.Country;
import com.korazy.covidtracker.Network.RequestManager;
import com.korazy.covidtracker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CountryActivity extends AppCompatActivity implements onCovidReceivedCallback, DatePickerDialog.OnDateSetListener {

    private TextView countryName;
    private TextView newCases;
    private TextView totalCases;
    private TextView recoveredCases;
    private TextView activeCases;
    private TextView criticalCases;
    private TextView newDeaths;
    private TextView totalDeaths;
    private TextView date;
    private ImageView flag;

    private String country;

    private Menu menu;
    private RequestManager covidRequest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Details");
        countryName = findViewById(R.id.tv_countryName);
        newCases = findViewById(R.id.tv_newAmount);
        totalCases = findViewById(R.id.tv_totalAmount);
        recoveredCases = findViewById(R.id.tv_recoveredAmount);
        activeCases = findViewById(R.id.tv_activeAmount);
        criticalCases = findViewById(R.id.tv_criticalAmount);
        newDeaths = findViewById(R.id.tv_deathsNewAmount);
        totalDeaths = findViewById(R.id.tv_deathsTotalAmount);
        date = findViewById(R.id.tv_date);
        flag = findViewById(R.id.iv_countryFlag);

        country = getIntent().getStringExtra("country");
        countryName.setText(country);

        covidRequest = new RequestManager(this);
        covidRequest.fetchData(RequestManager.RequestType.STATISTICS, country);


        flag.setImageResource(World.getFlagOf(this.country));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.country_menu, menu);
        this.menu = menu;
        SharedPreferences sharedPref = getSharedPreferences("countries", Context.MODE_PRIVATE);
        if (sharedPref.contains(country))
            menu.getItem(1).setIcon(getDrawable(R.drawable.ic_favorite_white));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_favorite:
                toggleFavorite();
                //Toast.makeText(CountryActivity.this, "Favorite Toggled!", Toast.LENGTH_LONG).show();
                return true;
            case android.R.id.home:
                finish();
                return true;
            case R.id.date_picker:
                showDatePickerDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePicker = new DatePickerDialog(this,
                R.style.DialogTheme,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    @Override
    public void setCovidData(Country country) {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        if(country.getNewCases()=="null"){
            newCases.setText("0");
        } else
            newCases.setText(country.getNewCases());
        totalCases.setText(country.getTotalCases() + "");
        recoveredCases.setText(country.getRecoveredCases() + "");
        activeCases.setText(country.getActiveCases() + "");
        criticalCases.setText(country.getCriticalCases() + "");
        if(country.getNewDeaths()=="null")
            newDeaths.setText("0");
        else
            newDeaths.setText(country.getNewDeaths());
        totalDeaths.setText(country.getTotalDeaths() + "");


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        date.setText(formattedDate);

    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void toggleFavorite() {
        SharedPreferences sharedPref = getSharedPreferences("countries", Context.MODE_PRIVATE);
        if (sharedPref.contains(country)) {
            sharedPref.edit().remove(country).commit();
            menu.getItem(1).setIcon(getDrawable(R.drawable.ic_favorite_border_white));
        } else {
            sharedPref.edit().putString(country, country).commit();
            menu.getItem(1).setIcon(getDrawable(R.drawable.ic_favorite_white));
        }

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        //TODO check if date greater than today
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String m = (1+month) +"";
        String d = day + "";
        if(month+1 < 10){
            m = "0" + (month+1);
        }
        if(day < 10){
            d  = "0" + day ;
        }
        String date = year + "-" + m + "-" + d;
        //Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
        covidRequest.fetchCountryHistory(country, date);
    }
}
