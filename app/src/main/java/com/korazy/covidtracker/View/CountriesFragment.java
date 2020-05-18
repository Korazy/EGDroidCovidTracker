package com.korazy.covidtracker.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.korazy.covidtracker.Adapter.RecyclerAdapter;
import com.korazy.covidtracker.Interface.onCountriesReceivedCallback;
import com.korazy.covidtracker.Network.RequestManager;
import com.korazy.covidtracker.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CountriesFragment extends Fragment implements onCountriesReceivedCallback {

    private RecyclerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_countries, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        RequestManager covidRequest = new RequestManager(this);
        covidRequest.fetchData(RequestManager.RequestType.COUNTRIES, "");
    }

    private void initRecyclerView() {
        //Set adapter
        mAdapter = new RecyclerAdapter();
        RecyclerView recyclerView = getView().findViewById(R.id.rv_countries);
        recyclerView.setHasFixedSize(true);
        //Create and set the layout manager
        //Use grid to have it in column
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setAvailCountries(List<String> countries) {
        mAdapter.setData(countries);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT);
    }

}

