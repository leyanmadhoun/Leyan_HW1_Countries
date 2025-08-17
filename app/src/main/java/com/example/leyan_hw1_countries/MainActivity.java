package com.example.leyan_hw1_countries;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private CountryViewModel viewModel;
    private CountryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rv = findViewById(R.id.rvCountries);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CountryAdapter(this);
        rv.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(CountryViewModel.class);
        viewModel.getAllCountries().observe(this, adapter::submitList);

    }


}
