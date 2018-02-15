package com.example.android.bakingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.bakingapp.adapter.RVRecipeNamesAdapter;
import com.example.android.bakingapp.model.Ingredients;
import com.example.android.bakingapp.model.RecipeData;
import com.example.android.bakingapp.model.Steps;
import com.example.android.bakingapp.utilities.BakingAppUtil;
import com.example.android.bakingapp.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Entry activity for the app. Displays the recipe names by making the
 * network call
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recipe_names_recycler_view_id)
    protected RecyclerView recipeNamesRecyclerView;
    @BindView(R.id.progress_bar_id)
    protected ProgressBar progressBar;
    @BindView(R.id.error_message_display)
    protected TextView errorMessageDisplay;
    @BindView(R.id.recipe_list_linear_layout_id)
    protected LinearLayout linearLayout;
    protected static ArrayList<RecipeData> recipeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BakingAppUtil.setCustomActionBar(this, getString(R.string.welcome_custom_bar_text));

        if (checkNetworkConnectivity())
            new FetchRecipeData().execute(BakingAppUtil.RECIPE_DATA_URL);
        else {
            linearLayout.setVisibility(View.INVISIBLE);
            errorMessageDisplay.setVisibility(View.VISIBLE);
            errorMessageDisplay.setText(getString(R.string.internet_error_message));
        }
    }

    /*
     * Checks if the device is connected or is connecting to the
     * network.
     * Source : Android developer documentation
     * https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html#DetermineType
     */
    private boolean checkNetworkConnectivity() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


    public class FetchRecipeData extends AsyncTask<String, Void, ArrayList<RecipeData>> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            recipeNamesRecyclerView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected ArrayList<RecipeData> doInBackground(String... strings) {
            final String url = strings[0];
            final URL recipeDataUrl = NetworkUtils.buildUrl(url);
            final ArrayList<RecipeData> recipeList = new ArrayList<>();
            try {
                String recipeListJsonRespone = NetworkUtils.getResponseFromHttpUrl(recipeDataUrl);
                try {
                    final JSONArray resultsJsonArray = new JSONArray(recipeListJsonRespone);
                    for (int i = 0; i < resultsJsonArray.length(); i++) {
                        final JSONObject tempJsonObject = resultsJsonArray.getJSONObject(i);
                        final ArrayList<Ingredients> ingredientsList = new ArrayList<>();
                        final ArrayList<Steps> stepsList = new ArrayList<>();
                        final String recipeName = tempJsonObject.getString(BakingAppUtil.RECIPE_NAME);
                        final JSONArray ingredientsJsonArray = tempJsonObject.getJSONArray(BakingAppUtil.RECIPE_INGREDIENTS);
                        for (int j = 0; j < ingredientsJsonArray.length(); j++) {
                            final JSONObject ingredientJsonObject = ingredientsJsonArray.getJSONObject(j);
                            final int quantity = ingredientJsonObject.getInt(BakingAppUtil.INGREDIENT_QUANTITY);
                            final String measure = ingredientJsonObject.getString(BakingAppUtil.INGREDIENT_MEASURE);
                            final String ingredient = ingredientJsonObject.getString(BakingAppUtil.INGREDIENT_NAME);
                            final Ingredients ingredients = new Ingredients(quantity, measure, ingredient);
                            ingredientsList.add(ingredients);
                        }

                        final JSONArray stepsJsonArray = tempJsonObject.getJSONArray(BakingAppUtil.RECIPE_STEPS);
                        for (int j = 0; j < stepsJsonArray.length(); j++) {
                            final JSONObject stepsJsonObject = stepsJsonArray.getJSONObject(j);
                            final String shortDescription = stepsJsonObject.getString(BakingAppUtil.STEP_SHORT_DESCRIPTION);
                            final String description = stepsJsonObject.getString(BakingAppUtil.STEP_DESCRIPTION);
                            final String videoURL = stepsJsonObject.getString(BakingAppUtil.STEP_VIDEO_URL);
                            final String thumbnailURL = stepsJsonObject.getString(BakingAppUtil.STEP_THUMBNAIL_URL);
                            final Steps steps = new Steps(shortDescription, description, videoURL, thumbnailURL);
                            stepsList.add(steps);
                        }
                        final int servings = tempJsonObject.getInt(BakingAppUtil.RECIPE_SERVINGS);
                        final String image = tempJsonObject.getString(BakingAppUtil.RECIPE_IMAGE);
                        final RecipeData recipeData = new RecipeData(recipeName, ingredientsList, stepsList, servings, image);
                        recipeList.add(recipeData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return recipeList;
        }

        @Override
        protected void onPostExecute(final ArrayList<RecipeData> recipeDataArrayList) {
            progressBar.setVisibility(View.INVISIBLE);
            if (recipeDataArrayList == null) {
                errorMessageDisplay.setVisibility(View.VISIBLE);
                errorMessageDisplay.setText(getString(R.string.error_message));
            } else {
                recipeData = recipeDataArrayList;
                recipeNamesRecyclerView.setVisibility(View.VISIBLE);
                recipeNamesRecyclerView.setHasFixedSize(true);
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                recipeNamesRecyclerView.setLayoutManager(linearLayoutManager);
                final RVRecipeNamesAdapter rvRecipeNamesAdapter = new RVRecipeNamesAdapter(recipeDataArrayList, MainActivity.this);
                recipeNamesRecyclerView.setAdapter(rvRecipeNamesAdapter);
            }
        }
    }
}