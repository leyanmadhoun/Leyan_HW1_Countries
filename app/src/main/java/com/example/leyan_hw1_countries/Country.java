package com.example.leyan_hw1_countries;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "countries")
public class Country {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String capital;
    public String description;
    public int flagResId;

    public Country(String name, String capital, String description, int flagResId) {
        this.name = name;
        this.capital = capital;
        this.description = description;
        this.flagResId = flagResId;
    }
}
