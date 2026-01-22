package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    CityArrayAdapter cityAdapter;
    ArrayList<String> dataList;
    int selectedPosition = 0;
    boolean citySelected = false;
    boolean confirmDelete = false;
    boolean confirmAdd = false;



    private AdapterView.OnItemClickListener clickHandler = (parent, v, position, id) -> {
        selectedPosition = position;
        citySelected = true;
        findViewById(R.id.deleteCity).setEnabled(citySelected);
    };

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        String[] cities = {"Edmonton", "Calgary", "Toronto", "Vancouver", "Montreal", "Ottawa", "Winnipeg", "Quebec City", "Hamilton", "Halifax", "Beijing"};
        String[] provinces = { "AB", "AB", "BC", "ON", "QC", "ON", "MB", "QC", "ON", "NS", "China"};
        ArrayList<City> dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);
    }

    // Add City Button Functionality
    public void addCity(View view) {
        EditText editText = findViewById(R.id.editTextCityName);
        String message = editText.getText().toString();
        Button button = findViewById(R.id.addCity);

        String message2 = message.replaceAll("\\s", ""); // prevents empty strings from being added
        if (!confirmAdd) {
            confirmAdd = true;
            button.setText("Confirm Add");
        } else {
            confirmAdd = false;
            button.setText("Add City");
            if (!message2.isEmpty()) {
                editText.getText().clear();
                dataList.add(message);
                cityAdapter.notifyDataSetChanged();
            }
        }
    }

    // Delete City Button Functionality
    public void deleteCity(View view) {
        Button button = findViewById(R.id.deleteCity);
        if (citySelected && confirmDelete) {
            citySelected = false;
            confirmDelete = false;
            findViewById(R.id.deleteCity).setEnabled(citySelected);
            dataList.remove(selectedPosition);
            cityAdapter.notifyDataSetChanged();
            button.setText("Delete City");
        }
        if (citySelected && !confirmDelete) {
            confirmDelete = true;
            button.setText("Confirm Delete");
        }
    }
}