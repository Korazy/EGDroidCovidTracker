package com.korazy.covidtracker.Util;

import android.util.Log;

import com.korazy.covidtracker.Model.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CovidParser {

    private Country country;

    public CovidParser(){
        country = new Country();
    }

    public Country parseStatistics(JSONArray jsonArray) throws JSONException {
        JSONObject covidStats = jsonArray.getJSONObject(0);
        //Log.i("request_jsonarray", "parseStatistics: " + covidStats.toString());
        //Log.i("request_jsonobject", "parseStatistics: "+covidStats.getJSONObject("cases"));
        JSONObject cases = covidStats.getJSONObject("cases");
        JSONObject deaths = covidStats.getJSONObject("deaths");
        getCases(cases);
        getDeaths(deaths);
        return country;

    }

    private void getCases(JSONObject cases) throws JSONException {
        Log.i("request_cases", "getCases: " + cases.toString());
        country.setActiveCases(cases.getInt("active"));
        country.setCriticalCases(cases.getInt("critical"));
        country.setNewCases(cases.getString("new"));
        country.setRecoveredCases(cases.getInt("recovered"));
        country.setTotalCases(cases.getInt("total"));
    }

    private void getDeaths(JSONObject deaths) throws JSONException {
        Log.i("request_deaths", "getDeaths: " + deaths.toString());
        country.setNewDeaths(deaths.getString("new"));
        country.setTotalDeaths(deaths.getInt("total"));

    }

    public List<String> parseCountries(JSONArray jsonCountries){
        List<String> countries = new ArrayList<>();
        try {
            for (int i = 0; i < jsonCountries.length(); i++) {
                String country = jsonCountries.getString(i);
                //Log.d("country", "parseCountries: "+country);
                countries.add(country);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return countries;
    }
}
