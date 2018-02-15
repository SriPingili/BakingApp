package com.example.android.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

/**
 * Model class for the ingredients data
 */
@Data
public class Ingredients implements Serializable {

    @SerializedName("quantity")
    private final int quantity;

    @SerializedName("measure")
    private final String measure;

    @SerializedName("ingredient")
    private final String ingredient;

    public Ingredients(int quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
}
