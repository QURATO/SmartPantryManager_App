package com.smartpantry.manager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smartpantry.manager.models.PantryItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table 1: Pantry Items
        db.execSQL("CREATE TABLE IF NOT EXISTS pantry_items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "quantity REAL, " +
                "unit TEXT, " +
                "expiry_date TEXT)");

        // Table 2: Recipes
        db.execSQL("CREATE TABLE IF NOT EXISTS recipes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "required_ingredients TEXT, " +
                "instructions TEXT)");

        seedRecipes(db);
    }

    private void seedRecipes(SQLiteDatabase db) {
        String[][] initialRecipes = {
                {"Scrambled Eggs", "Eggs,Milk,Butter", "1. Whisk eggs and milk. 2. Melt butter in pan. 3. Cook on low heat."},
                {"Omelette", "Eggs,Cheese,Milk", "1. Beat eggs with milk. 2. Pour into skillet. 3. Top with cheese and fold."},
                {"Pancakes", "Flour,Milk,Eggs,Sugar", "1. Mix dry and wet ingredients. 2. Pour batter on griddle. 3. Flip when bubbly."},
                {"Pantry Pasta", "Pasta,Garlic,Olive Oil", "1. Boil pasta. 2. Sauté garlic in olive oil. 3. Toss pasta with garlic oil."},
                {"Cheese Toastie", "Bread,Butter,Cheese", "1. Butter bread slices. 2. Add cheese in between. 3. Grill until golden."},
                {"French Toast", "Bread,Eggs,Milk,Sugar", "1. Whisk eggs, milk, sugar. 2. Dip bread. 3. Fry until toasted."},
                {"Rice & Beans", "Rice,Beans,Onion", "1. Cook rice. 2. Sauté onion and beans. 3. Mix together and serve."},
                {"Garlic Bread", "Bread,Butter,Garlic", "1. Mix butter and garlic. 2. Spread on bread. 3. Bake until crispy."},
                {"Fried Egg Rice", "Rice,Eggs,Soy Sauce", "1. Sauté cooked rice. 2. Scramble eggs into rice. 3. Season with soy sauce."},
                {"Mashed Potatoes", "Potatoes,Butter,Milk", "1. Boil potatoes until tender. 2. Mash with butter and milk."},
                {"Tomato Soup", "Tomatoes,Garlic,Onion", "1. Sauté onion and garlic. 2. Add tomatoes and simmer. 3. Blend smooth."},
                {"Simple Salad", "Lettuce,Tomatoes,Olive Oil", "1. Chop lettuce and tomatoes. 2. Toss with olive oil dressing."},
                {"Oatmeal", "Oats,Milk,Sugar", "1. Combine oats and milk. 2. Cook on medium heat. 3. Sweeten with sugar."},
                {"Boiled Eggs", "Eggs", "1. Place eggs in boiling water for 8 minutes. 2. Cool and peel."},
                {"Fruit Bowl", "Apples,Bananas", "1. Slice apples and bananas. 2. Mix in a bowl and serve immediately."}
        };

        for (String[] r : initialRecipes) {
            ContentValues cv = new ContentValues();
            cv.put("title", r[0]);
            cv.put("required_ingredients", r[1]);
            cv.put("instructions", r[2]);
            db.insert("recipes", null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS pantry_items");
        db.execSQL("DROP TABLE IF EXISTS recipes");
        onCreate(db);
    }

    // --- CRUD OPERATIONS FOR PANTRY ITEMS ---

    public long addPantryItem(PantryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", item.getName());
        cv.put("quantity", item.getQuantity());
        cv.put("unit", item.getUnit());
        cv.put("expiry_date", item.getExpiryDate());
        return db.insert("pantry_items", null, cv);
    }

    public List<PantryItem> getAllItems() {
        List<PantryItem> itemsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM pantry_items", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                double qty = cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
                String expiry = cursor.getString(cursor.getColumnIndexOrThrow("expiry_date"));

                PantryItem item = new PantryItem(id, name, qty, unit, expiry);
                itemsList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itemsList;
    }

    // Alias method to satisfy MainActivity and SuggestedRecipesActivity
    public List<PantryItem> getAllPantryItems() {
        return getAllItems();
    }

    public int deletePantryItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("pantry_items", "id=?", new String[]{String.valueOf(id)});
    }
}