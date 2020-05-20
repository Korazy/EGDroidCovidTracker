package com.korazy.covidtracker.View;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.korazy.covidtracker.Adapter.RecyclerAdapter;
import com.korazy.covidtracker.Interface.onCountriesReceivedCallback;
import com.korazy.covidtracker.Network.RequestManager;
import com.korazy.covidtracker.R;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CountriesFragment extends Fragment implements onCountriesReceivedCallback {

    private RecyclerAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        final RequestManager covidRequest = new RequestManager(this);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (androidx.appcompat.widget.SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //TODO prevent search when less than 3 characters
                Log.i("query_submit", "onQueryTextSubmit: Text submitted " + s);
                covidRequest.fetchData(RequestManager.RequestType.COUNTRY, s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
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

