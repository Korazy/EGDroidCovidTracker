package com.korazy.covidtracker.Network;

import android.content.res.Resources;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.korazy.covidtracker.Interface.onCovidReceivedCallback;
import com.korazy.covidtracker.MainApplication;
import com.korazy.covidtracker.Model.Country;
import com.korazy.covidtracker.R;
import com.korazy.covidtracker.Util.CovidParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestManager implements JSONObjectRequestListener {

    private RequestType requestType;
    private onCovidReceivedCallback fragmentCallback;

    public RequestManager(onCovidReceivedCallback callback) {
        this.fragmentCallback = callback;
    }

    public void fetchStatistics(RequestType request, String countryName) {
        Resources resource = MainApplication.getContext().getResources();
        requestType = request;
        switch (request) {
            case HISTORY:
                break;
            case COUNTRIES:
                break;
            case STATISTICS:
                AndroidNetworking.get(resource.getString(R.string.api_url_statistics) + "?country={country}")
                        .addPathParameter("country", countryName)
                        .addHeaders(resource.getString(R.string.api_host_header), resource.getString(R.string.api_host))
                        .addHeaders(resource.getString(R.string.api_key_header), resource.getString(R.string.api_key))
                        .build()
                        .getAsJSONObject(this);
                break;
        }
        return;
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.i("request_success", response.toString());
        Country country = new Country();
        CovidParser cp = new CovidParser();
        JSONObject jsonObject = response;
        try {
            switch (requestType) {
                case HISTORY:
                    break;
                case COUNTRIES:
                    break;
                case STATISTICS:
                    JSONArray jsonResponse = jsonObject.getJSONArray("response");
                    country = cp.parseStatistics(jsonResponse);
                    break;
            }
            fragmentCallback.setCovidData(country);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(ANError anError) {
        Log.e("request_error", "onError: " + anError.getErrorBody());
        Log.e("request_error", "onError: " + anError.getErrorDetail());
        Log.e("request_error", "onError: " + anError.getErrorCode());
        Log.e("request_error", "onError: " + anError.getMessage());
    }

    public enum RequestType {STATISTICS, COUNTRIES, HISTORY}


}
