package com.korazy.covidtracker.Interface;

import com.korazy.covidtracker.Model.Country;

import java.util.List;

public interface onCovidReceivedCallback {

    void setCovidData(Country country);
    void showError(String errorMessage);
    void onComplete();
}
