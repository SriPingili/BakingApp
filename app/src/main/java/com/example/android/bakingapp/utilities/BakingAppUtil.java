package com.example.android.bakingapp.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.DetailedIngredientsViewFragment;
import com.example.android.bakingapp.fragments.DetailedStepsViewFragment;
import com.example.android.bakingapp.model.Ingredients;
import com.example.android.bakingapp.model.RecipeData;
import com.example.android.bakingapp.model.Steps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Utility class for baking app project
 */
public class BakingAppUtil {
    public static final String RECIPE_DATA_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_INGREDIENTS = "ingredients";
    public static final String INGREDIENT_NAME = "ingredient";
    public static final String INGREDIENT_QUANTITY = "quantity";
    public static final String INGREDIENT_MEASURE = "measure";
    public static final String RECIPE_STEPS = "steps";
    public static final String STEP_SHORT_DESCRIPTION = "shortDescription";
    public static final String STEP_DESCRIPTION = "description";
    public static final String STEP_VIDEO_URL = "videoURL";
    public static final String STEP_THUMBNAIL_URL = "thumbnailURL";
    public static final String RECIPE_IMAGE = "image";
    public static final String RECIPE_SERVINGS = "servings";
    public static final String RECIPE_DATA_KEY = "recipe_data_key";
    public static final String INGREDIENTS_DATA_KEY = "ingredients_data_key";
    public static final String STEPS_DATA_KEY = "steps_data_key";
    public static final String SELECTED_STEP_POSITION = "position";
    public static final String POSITION_KEY = "position_key";


    public static void setCustomActionBar(Activity activity, final String welcomeText) {
        AppCompatActivity appActivity = (AppCompatActivity) activity;
        appActivity.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        View customActionBarView = appActivity.getLayoutInflater().inflate(R.layout.baking_app_action_bar_custom, null);

        TextView welcomeTextView = customActionBarView.findViewById(R.id.baking_app_welcome_text);
        welcomeTextView.setText(welcomeText);

        appActivity.getSupportActionBar().setCustomView(customActionBarView);
    }

    public static void saveRecipeData(RecipeData recipeData, Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(recipeData);

        editor.putString("xyz", json);
        editor.commit();
    }

    public static RecipeData getRecipeData(Context context) {
        SharedPreferences sharedPrefs2 = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson2 = new Gson();
        String json2 = sharedPrefs2.getString("xyz", null);
        Type type = new TypeToken<RecipeData>() {
        }.getType();

        return gson2.fromJson(json2, type);
    }

    public static Bundle getRecipeBundle(final String key, final Object objectToBeSaved) {
        final Bundle bundle = new Bundle();
        bundle.putSerializable(key, (Serializable) objectToBeSaved);
        return bundle;
    }

    public static void setUpDetailedStepsViewFragment(final FragmentActivity activity, final Steps steps, boolean isSavedInstanceStateNul) {
        if (isSavedInstanceStateNul) {
            final DetailedStepsViewFragment stepsViewFragmentt = new DetailedStepsViewFragment();
            stepsViewFragmentt.setArguments(BakingAppUtil.getRecipeBundle(BakingAppUtil.STEPS_DATA_KEY, steps));
            final FragmentManager fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.steps_containerr_id, stepsViewFragmentt)
                    .addToBackStack(null)
                    .commit();
        }
    }

    public static void setUpDetailedIngredientsViewFragment(final FragmentActivity activity, final ArrayList<Ingredients> ingredientsList) {
        final DetailedIngredientsViewFragment detailedIngredientsViewFragment = new DetailedIngredientsViewFragment();
        detailedIngredientsViewFragment.setArguments(BakingAppUtil.getRecipeBundle(BakingAppUtil.INGREDIENTS_DATA_KEY, ingredientsList));

        final FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.ingredients_container_id, detailedIngredientsViewFragment)
                .commit();
    }

    public static void saveCurrentState(Bundle outState, int position, RecipeData recipeData) {
        outState.putInt(POSITION_KEY, position);
        outState.putSerializable(RECIPE_DATA_KEY, recipeData);
    }
}