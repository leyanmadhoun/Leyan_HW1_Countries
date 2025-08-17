package com.example.leyan_hw1_countries;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CountryViewModel extends AndroidViewModel {
    private final CountryRepository repo;
    private final LiveData<List<Country>> allCountries;

    public CountryViewModel(@NonNull Application application) {
        super(application);
        repo = new CountryRepository(application);
        allCountries = repo.getAllCountries();
    }

    public LiveData<List<Country>> getAllCountries() { return allCountries; }

    public void insert(Country c) { repo.insert(c); }

    public void update(Country c) { repo.update(c); }

    public void delete(Country c) { repo.delete(c); }
}
