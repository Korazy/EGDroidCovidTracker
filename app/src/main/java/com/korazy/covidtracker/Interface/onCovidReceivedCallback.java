package com.korazy.covidtracker.Interface;

import com.korazy.covidtracker.Model.Country;

public interface onCovidReceivedCallback{

    void setCovidData(Country country);
    void showError(String errorMessage);


}
