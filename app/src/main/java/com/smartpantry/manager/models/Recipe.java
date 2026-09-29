package com.smartpantry.manager.models;

import java.util.Map;

public class Recipe {
    private int id;
    private String title;
    private String instructions;
    private Map<String, Double> requiredIngredients;

    public Recipe(int id, String title, String instructions, Map<String, Double> requiredIngredients) {
        this.id = id;
        this.title = title;
        this.instructions = instructions;
        this.requiredIngredients = requiredIngredients;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getInstructions() { return instructions; }
    public Map<String, Double> getRequiredIngredients() { return requiredIngredients; }
}