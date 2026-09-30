package com.smartpantry.manager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.smartpantry.manager.R;
import com.smartpantry.manager.database.DatabaseHelper;
import com.smartpantry.manager.models.PantryItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewPantry;
    private Button btnAddItem, btnSuggestedRecipes, btnSettings;
    private DatabaseHelper dbHelper;
    private List<PantryItem> pantryItemList;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> displayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        listViewPantry = findViewById(R.id.listViewPantry);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnSuggestedRecipes = findViewById(R.id.btnSuggestedRecipes);
        btnSettings = findViewById(R.id.btnSettings);

        displayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        listViewPantry.setAdapter(adapter);

        btnAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
            startActivity(intent);
        });

        btnSuggestedRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SuggestedRecipesActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        listViewPantry.setOnItemLongClickListener((parent, view, position, id) -> {
            PantryItem item = pantryItemList.get(position);
            dbHelper.deletePantryItem(item.getId());
            Toast.makeText(MainActivity.this, "Deleted: " + item.getName(), Toast.LENGTH_SHORT).show();
            loadPantryItems();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }

    private void loadPantryItems() {
        pantryItemList = dbHelper.getAllPantryItems();
        displayList.clear();
        for (PantryItem item : pantryItemList) {
            displayList.add(item.getName() + " - " + item.getQuantity() + " " + item.getUnit() + " (Exp: " + item.getExpiryDate() + ")");
        }
        adapter.notifyDataSetChanged();
    }
}