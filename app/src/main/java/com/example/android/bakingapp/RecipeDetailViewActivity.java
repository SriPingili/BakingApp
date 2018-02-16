package com.example.android.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.android.bakingapp.fragments.DetailedStepsViewFragment;
import com.example.android.bakingapp.fragments.StepsViewFragment;
import com.example.android.bakingapp.model.RecipeData;
import com.example.android.bakingapp.utilities.BakingAppUtil;

/**
 * This class is responsible for displaying the details of the recipe
 * selected from the MainActivity.
 * <p>
 * recycler view scroll state reference :
 * https://stackoverflow.com/questions/28236390/recyclerview-store-restore-state-between-activities/28262885#28262885
 */
public class RecipeDetailViewActivity extends AppCompatActivity implements StepsViewFragment.OnStepsViewFragmentClickListener {

    private static RecipeData recipeData = null;
    private boolean isTwoPaneLayout = false;
    private static Intent intent;

    private boolean savedInstanceNull = true;
    private static int position = -1;
    private Parcelable recyclerViewState = null;
    private static final String SAVE_STATE_KEY = "save_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail_view);

        if (savedInstanceState != null) {
            savedInstanceNull = false;
            if (savedInstanceState.containsKey(BakingAppUtil.POSITION_KEY)) {
                position = savedInstanceState.getInt(BakingAppUtil.POSITION_KEY);
                if (position == -1)
                    savedInstanceNull = true;
            }
            if (savedInstanceState.containsKey(BakingAppUtil.RECIPE_DATA_KEY)) {
                recipeData = (RecipeData) savedInstanceState.getSerializable(BakingAppUtil.RECIPE_DATA_KEY);
            }
        } else {
            intent = getIntent();
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                recipeData = (RecipeData) intent.getSerializableExtra(Intent.EXTRA_TEXT);
            }
        }
        setUpCustomActionBar();
        isTwoPaneLayout = isTwoPaneLayout();
        setUpStepsViewFragment();
        if (isTwoPaneLayout && recipeData != null)
            displayIngredientsList();
    }

    private void setUpStepsViewFragment() {
        BakingAppUtil.saveRecipeData(recipeData, RecipeDetailViewActivity.this);
        final StepsViewFragment stepsViewFragment = new StepsViewFragment();
        stepsViewFragment.setArguments(BakingAppUtil.getRecipeBundle(BakingAppUtil.RECIPE_DATA_KEY, recipeData));
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.steps_container_id, stepsViewFragment)
                .commit();
    }

    @Override
    public void OnStepSelected(int position) {
        this.position = position;
        if (!isTwoPaneLayout) {
            final Intent intent = new Intent(RecipeDetailViewActivity.this, StepsDetailActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, recipeData);
            intent.putExtra(BakingAppUtil.SELECTED_STEP_POSITION, position);
            startActivity(intent);
        } else {
            DetailedStepsViewFragment.releasePlayer();
            setUpTwoPaneLayoutDetailedSteps(position);
        }

    }

    private void setUpTwoPaneLayoutDetailedSteps(int position) {
        findViewById(R.id.steps_containerr_id).setVisibility(View.VISIBLE);
        findViewById(R.id.ingredients_container_id).setVisibility(View.GONE);
        BakingAppUtil.setUpDetailedStepsViewFragment(this, recipeData.getStepsList().get(position), savedInstanceNull);
        savedInstanceNull = true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        DetailedStepsViewFragment.releasePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DetailedStepsViewFragment.releasePlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StepsViewFragment.stepsRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        if (isTwoPaneLayout && position != -1)
            setUpTwoPaneLayoutDetailedSteps(position);
    }

    @Override
    public void onDisplayIngredientsListSelected() {
        if (!isTwoPaneLayout) {
            final Intent intent = new Intent(this, IngredientsDetailActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, recipeData.getIngredientsList());
            startActivity(intent);
        } else {
            this.position = -1;
            DetailedStepsViewFragment.releasePlayer();
            findViewById(R.id.steps_containerr_id).setVisibility(View.GONE);
            findViewById(R.id.ingredients_container_id).setVisibility(View.VISIBLE);
            displayIngredientsList();
        }
    }

    private void displayIngredientsList() {
        BakingAppUtil.setUpDetailedIngredientsViewFragment(this, recipeData.getIngredientsList());
    }

    /**
     * helper method to set up the custom action bar
     */
    private void setUpCustomActionBar() {
        BakingAppUtil.setCustomActionBar(this, recipeData.getName() + " " + getString(R.string.preparation));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * helper method that returns true if it is a two pane layout,
     * false otherwise
     */
    private boolean isTwoPaneLayout() {
        return findViewById(R.id.tablet_view_linear_layout_id) != null;
    }

    @Override
    public void onBackPressed() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        BakingAppUtil.saveCurrentState(outState, position, recipeData);
        outState.putParcelable(SAVE_STATE_KEY, StepsViewFragment.stepsRecyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
            recyclerViewState = savedInstanceState.getParcelable(SAVE_STATE_KEY);
    }
}