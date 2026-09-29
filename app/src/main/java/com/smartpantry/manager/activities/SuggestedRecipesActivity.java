package com.smartpantry.manager.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.smartpantry.manager.R;
import com.smartpantry.manager.database.DatabaseHelper;
import com.smartpantry.manager.models.PantryItem;
import com.smartpantry.manager.models.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private ListView listViewRecipes;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        listViewRecipes = findViewById(R.id.listViewRecipes);
        dbHelper = new DatabaseHelper(this);

        filterAndDisplayRecipes();
    }

    private void filterAndDisplayRecipes() {
        List<PantryItem> pantryList = dbHelper.getAllPantryItems();
        Map<String, Double> inventory = new HashMap<>();

        for (PantryItem item : pantryList) {
            inventory.put(item.getName().toLowerCase(), item.getQuantity());
        }

        List<Recipe> allRecipes = getHardcodedRecipes();
        List<String> matchingRecipesDisplay = new ArrayList<>();

        for (Recipe recipe : allRecipes) {
            boolean canMake = true;

            for (Map.Entry<String, Double> required : recipe.getRequiredIngredients().entrySet()) {
                String reqName = required.getKey().toLowerCase();
                double reqQty = required.getValue();

                if (!inventory.containsKey(reqName) || inventory.get(reqName) < reqQty) {
                    canMake = false;
                    break;
                }
            }

            if (canMake) {
                matchingRecipesDisplay.add(recipe.getTitle() + "\n" + recipe.getInstructions());
            }
        }

        if (matchingRecipesDisplay.isEmpty()) {
            matchingRecipesDisplay.add("No recipes match your current inventory.");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, matchingRecipesDisplay);
        listViewRecipes.setAdapter(adapter);
    }

    private List<Recipe> getHardcodedRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        Map<String, Double> r1 = new HashMap<>();
        r1.put("Eggs", 2.0);
        r1.put("Milk", 1.0);
        recipes.add(new Recipe(1, "Scrambled Eggs", "Whisk eggs with milk and fry on medium heat.", r1));

        Map<String, Double> r2 = new HashMap<>();
        r2.put("Rice", 1.0);
        r2.put("Chicken", 1.0);
        recipes.add(new Recipe(2, "Chicken Rice", "Boil chicken and rice together with seasoning.", r2));

        return recipes;
    }
}