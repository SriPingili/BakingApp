package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.model.Ingredients;
import com.example.android.bakingapp.utilities.BakingAppUtil;

import java.util.ArrayList;

public class IngredientsDetailActivity extends AppCompatActivity {

    private ArrayList<Ingredients> ingredientsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_detail);
        final Intent intent = getIntent();
        if (intent!=null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            ingredientsList = (ArrayList<Ingredients>) intent.getSerializableExtra(Intent.EXTRA_TEXT);
            BakingAppUtil.setUpDetailedIngredientsViewFragment(this,ingredientsList);
        }
    }
}
