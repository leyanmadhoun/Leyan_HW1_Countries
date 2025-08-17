package com.example.leyan_hw1_countries;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CountryDao {
    @Query("SELECT * FROM countries ORDER BY name ASC")
    LiveData<List<Country>> getAll();

    @Insert
    void insert(Country c);

    @Insert
    void insertAll(Country... countries);

    @Update
    void update(Country c);

    @Delete
    void delete(Country c);

    @Query("DELETE FROM countries")
    void clear();
}
