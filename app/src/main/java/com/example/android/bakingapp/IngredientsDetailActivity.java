package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.fragments.DetailedIngredientsViewFragment;
import com.example.android.bakingapp.fragments.StepsViewFragment;
import com.example.android.bakingapp.model.Ingredients;
import com.example.android.bakingapp.utilities.BakingAppUtil;

import java.util.ArrayList;

public class IngredientsDetailActivity extends AppCompatActivity {

    private static final String SAVE_INGREDIENTS_STATE_KEY = "save_ingredients_key";
    private ArrayList<Ingredients> ingredientsList;
    private Parcelable ingredientsRecyclerViewState = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_detail);
        final Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            ingredientsList = (ArrayList<Ingredients>) intent.getSerializableExtra(Intent.EXTRA_TEXT);
            BakingAppUtil.setUpDetailedIngredientsViewFragment(this, ingredientsList);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (DetailedIngredientsViewFragment.ingredients_recyclerView != null)
            DetailedIngredientsViewFragment.ingredients_recyclerView.getLayoutManager().onRestoreInstanceState(ingredientsRecyclerViewState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (DetailedIngredientsViewFragment.ingredients_recyclerView != null)
            outState.putParcelable(SAVE_INGREDIENTS_STATE_KEY, DetailedIngredientsViewFragment.ingredients_recyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.getParcelable(SAVE_INGREDIENTS_STATE_KEY) != null)
                ingredientsRecyclerViewState = savedInstanceState.getParcelable(SAVE_INGREDIENTS_STATE_KEY);
        }
    }
}
