package com.example.leyan_hw1_countries;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CountryRepository {
    private final CountryDao dao;
    private final LiveData<List<Country>> allCountries;

    public CountryRepository(Application app) {
        AppDatabase db = AppDatabase.getDatabase(app);
        dao = db.countryDao();
        allCountries = dao.getAll();
    }
    public LiveData<List<Country>> getAllCountries() {
        return allCountries;
    }
    public void insert(Country c) {
        AppDatabase.databaseWriteExecutor.execute(() -> dao.insert(c));
    }

    public void update(Country c) {
        AppDatabase.databaseWriteExecutor.execute(() -> dao.update(c));
    }
    public void delete(Country c) {
        AppDatabase.databaseWriteExecutor.execute(() -> dao.delete(c));
    }
}
