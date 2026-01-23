package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener {
    private ListView cityList;
    private CityArrayAdapter cityAdapter;
    private ArrayList<City> cities;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        cities = new ArrayList<>();
        seedCities();

        cityAdapter = new CityArrayAdapter(this, cities);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            findViewById(R.id.deleteCity).setEnabled(true);
        });


        findViewById(R.id.deleteCity).setEnabled(false);
    }

    private void seedCities() {
        String[] names = {
                "Edmonton", "Calgary", "Toronto", "Vancouver", "Montreal", "Ottawa", "Winnipeg", "Quebec City", "Hamilton", "Halifax", "Beijing"
        };
        String[] provinces = {
                "AB", "AB", "ON", "BC", "QC", "ON", "MB", "QC", "ON", "NS", "China"
        };

        for (int i = 0; i < names.length; i++) {
            cities.add(new City(names[i], provinces[i]));
        }
    }

    // Called from AddCityFragment
    @Override
    public void addCity(City city) {
        cities.add(city);
        cityAdapter.notifyDataSetChanged();
        onDialogCancelled();
    }

    @Override
    public void editCity(City city) {
        cities.set(selectedPosition, city);
        cityAdapter.notifyDataSetChanged();
        onDialogCancelled();
    }

    @Override
    public void onDialogCancelled() {
        selectedPosition = -1;
        findViewById(R.id.deleteCity).setEnabled(false);
    }

    public void openAddEditCityDialog(View view) {
        if (selectedPosition >= 0) {
            // EDIT mode
            City cityToEdit = cities.get(selectedPosition);
            AddCityFragment.newInstance(cityToEdit).show(getSupportFragmentManager(), "EDIT_CITY");
        } else {
            // ADD mode
            new AddCityFragment().show(getSupportFragmentManager(), "ADD_CITY");
        }
    }


    public void deleteCity(View view) {
        if (selectedPosition >= 0) {
            cities.remove(selectedPosition);
            selectedPosition = -1;
            cityAdapter.notifyDataSetChanged();
            findViewById(R.id.deleteCity).setEnabled(false);
        }
    }
}
