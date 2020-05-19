package com.korazy.covidtracker.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.korazy.covidtracker.Adapter.RecyclerAdapter;
import com.korazy.covidtracker.Interface.Updateable;
import com.korazy.covidtracker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SavedFragment extends Fragment implements Updateable {

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
        setAvailCountries(getFavoriteCountries());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isResumed()) {
            setAvailCountries(getFavoriteCountries());
            Log.i("visibility", "setUserVisibleHint: is visible");
        } else {
            Log.i("visibility", "setUserVisibleHint: is NOT visible");

        }
    }

    private List<String> getFavoriteCountries() {
        Log.i("saved", "getting saved");
        SharedPreferences sharedPref = this.getActivity().getSharedPreferences("countries", Context.MODE_PRIVATE);
        Map<String, ?> allSaved = sharedPref.getAll();
        List<String> savedCountries = new ArrayList<>();
        for (Map.Entry<String, ?> entry : allSaved.entrySet()) {
            savedCountries.add(entry.getKey());
            Log.i("country", "getFavoriteCountries: " + entry.getKey());
        }
        return savedCountries;
    }

    private void initRecyclerView() {
        //Set adapter
        Log.i("rview", "initRecyclerView: rview init");
        mAdapter = new RecyclerAdapter();
        RecyclerView recyclerView = getView().findViewById(R.id.rv_countries);
        recyclerView.setHasFixedSize(true);
        //Create and set the layout manager
        //Use grid to have it in column
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    public void setAvailCountries(List<String> countries) {
        mAdapter.setData(countries);
    }

    @Override
    public void update() {
        setAvailCountries(getFavoriteCountries());
    }
}