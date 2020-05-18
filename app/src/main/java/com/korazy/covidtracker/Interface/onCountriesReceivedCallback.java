package com.korazy.covidtracker.Interface;

import java.util.List;

public interface onCountriesReceivedCallback{

    void setAvailCountries(List<String> countries);
    void showError(String errorMessage);

}
