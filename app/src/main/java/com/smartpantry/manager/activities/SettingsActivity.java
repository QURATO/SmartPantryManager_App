package com.smartpantry.manager.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.smartpantry.manager.R;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchExpiryAlerts;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchExpiryAlerts = findViewById(R.id.switchAlerts);
        sharedPreferences = getSharedPreferences("SmartPantryPrefs", MODE_PRIVATE);

        boolean alertsEnabled = sharedPreferences.getBoolean("expiry_alerts", true);
        switchExpiryAlerts.setChecked(alertsEnabled);

        switchExpiryAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("expiry_alerts", isChecked);
            editor.apply();
        });
    }
}