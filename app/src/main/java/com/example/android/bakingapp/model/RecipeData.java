package com.example.android.bakingapp.model;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

/**
 * Model class for the Recipe data
 */
@Data
public class RecipeData implements Serializable {

    private final String name;
    private final ArrayList<Ingredients> ingredientsList;
    private final ArrayList<Steps> stepsList;
    private final int servings;
    private final String image;

    public RecipeData(String name, ArrayList<Ingredients> ingredientsList, ArrayList<Steps> stepsList, int servings, String image) {
        this.name = name;
        this.ingredientsList = ingredientsList;
        this.stepsList = stepsList;
        this.servings = servings;
        this.image = image;
    }
}
