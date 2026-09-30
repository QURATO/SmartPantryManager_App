package com.smartpantry.manager.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.smartpantry.manager.R;
import com.smartpantry.manager.database.DatabaseHelper;
import com.smartpantry.manager.models.PantryItem;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private ListView listViewRecipes;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        listViewRecipes = findViewById(R.id.listViewRecipes);
        dbHelper = new DatabaseHelper(this);

        loadSuggestions();
    }

    private void loadSuggestions() {
        List<PantryItem> pantryItems = dbHelper.getAllPantryItems();
        List<String> suggestions = new ArrayList<>();

        boolean hasEggs = false;
        boolean hasMilk = false;
        boolean hasBread = false;
        boolean hasCheese = false;

        for (PantryItem item : pantryItems) {
            String name = item.getName().toLowerCase();
            if (name.contains("egg")) hasEggs = true;
            if (name.contains("milk")) hasMilk = true;
            if (name.contains("bread")) hasBread = true;
            if (name.contains("cheese")) hasCheese = true;
        }

        if (hasEggs && hasMilk) {
            suggestions.add("Scrambled Eggs (Requires: Eggs, Milk)");
        }
        if (hasBread && hasCheese) {
            suggestions.add("Cheese Toastie (Requires: Bread, Cheese)");
        }
        if (hasEggs && hasBread && hasMilk) {
            suggestions.add("French Toast (Requires: Bread, Eggs, Milk)");
        }

        if (suggestions.isEmpty()) {
            suggestions.add("No recipes available for your current pantry items. Add more ingredients!");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, suggestions);
        listViewRecipes.setAdapter(adapter);
    }
}