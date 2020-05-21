package com.korazy.covidtracker.View;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.korazy.covidtracker.Interface.onCovidReceivedCallback;
import com.korazy.covidtracker.Model.Country;
import com.korazy.covidtracker.Network.RequestManager;
import com.korazy.covidtracker.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WorldFragment extends Fragment implements onCovidReceivedCallback {

    private TextView newCases;
    private TextView totalCases;
    private TextView recoveredCases;
    private TextView activeCases;
    private TextView criticalCases;
    private TextView newDeaths;
    private TextView totalDeaths;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_world, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        newCases = getView().findViewById(R.id.tv_newAmount);
        totalCases = getView().findViewById(R.id.tv_totalAmount);
        recoveredCases = getView().findViewById(R.id.tv_recoveredAmount);
        activeCases = getView().findViewById(R.id.tv_activeAmount);
        criticalCases = getView().findViewById(R.id.tv_criticalAmount);
        newDeaths = getView().findViewById(R.id.tv_deathsNewAmount);
        totalDeaths = getView().findViewById(R.id.tv_deathsTotalAmount);
        Log.i("test", "onActivityCreated: here");

        if (!checkConnectivitity())
            Toast.makeText(getContext(), "no internet", Toast.LENGTH_SHORT);
        else {
            Log.i("test", "onActivityCreated: here");
            RequestManager covidRequest = new RequestManager(this);
            covidRequest.fetchData(RequestManager.RequestType.STATISTICS, "all");
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setCovidData(Country country) {
        getView().findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        newCases.setText(country.getNewCases());
        totalCases.setText(country.getTotalCases() + "");
        recoveredCases.setText(country.getRecoveredCases() + "");
        activeCases.setText(country.getActiveCases() + "");
        criticalCases.setText(country.getCriticalCases() + "");
        newDeaths.setText(country.getNewDeaths());
        totalDeaths.setText(country.getTotalDeaths() + "");

    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    public boolean checkConnectivitity() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;

    }

}
