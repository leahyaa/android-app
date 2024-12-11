package com.example.unitconverter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // UI Elements
    Spinner spinnerCategory, spinnerFromUnit, spinnerToUnit;
    EditText editTextValue;
    Button buttonConvert;
    TextView textViewResult;

    // Data for Spinners
    String[] categories = {"Length", "Weight", "Temperature"};
    String[] lengthUnits = {"Meters", "Feet"};
    String[] weightUnits = {"Kilograms", "Pounds"};
    String[] temperatureUnits = {"Celsius", "Fahrenheit"};

    String[] currentUnits; // Holds current units for the category

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Elements
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerFromUnit = findViewById(R.id.spinnerFromUnit);
        spinnerToUnit = findViewById(R.id.spinnerToUnit);
        editTextValue = findViewById(R.id.editTextValue);
        buttonConvert = findViewById(R.id.buttonConvert);
        textViewResult = findViewById(R.id.textViewResult);

        // Populate Category Spinner
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        spinnerCategory.setAdapter(categoryAdapter);

        // Handle Category Selection
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateUnits(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Conversion Button
        buttonConvert.setOnClickListener(v -> performConversion());
    }

    private void updateUnits(int position) {
        // Update units based on selected category
        switch (position) {
            case 0: currentUnits = lengthUnits; break; // Length
            case 1: currentUnits = weightUnits; break; // Weight
            case 2: currentUnits = temperatureUnits; break; // Temperature
        }

        // Update unit Spinners
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currentUnits);
        spinnerFromUnit.setAdapter(unitAdapter);
        spinnerToUnit.setAdapter(unitAdapter);
    }

    private void performConversion() {
        String fromUnit = spinnerFromUnit.getSelectedItem().toString();
        String toUnit = spinnerToUnit.getSelectedItem().toString();
        double inputValue = Double.parseDouble(editTextValue.getText().toString());
        double result = 0.0;

        // Conversion Logic
        if (fromUnit.equals("Meters") && toUnit.equals("Feet")) {
            result = inputValue * 3.28084;
        } else if (fromUnit.equals("Feet") && toUnit.equals("Meters")) {
            result = inputValue / 3.28084;
        } else if (fromUnit.equals("Kilograms") && toUnit.equals("Pounds")) {
            result = inputValue * 2.20462;
        } else if (fromUnit.equals("Pounds") && toUnit.equals("Kilograms")) {
            result = inputValue / 2.20462;
        } else if (fromUnit.equals("Celsius") && toUnit.equals("Fahrenheit")) {
            result = (inputValue * 9 / 5) + 32;
        } else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Celsius")) {
            result = (inputValue - 32) * 5 / 9;
        } else {
            result = inputValue; // Same units
        }

        // Display Result
        textViewResult.setText("Result: " + result);
    }
}
