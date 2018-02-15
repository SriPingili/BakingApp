package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android.bakingapp.fragments.DetailedStepsViewFragment;
import com.example.android.bakingapp.model.RecipeData;
import com.example.android.bakingapp.model.Steps;
import com.example.android.bakingapp.utilities.BakingAppUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * this class is responsible for displaying the detailed steps. This is
 * only for the phones
 *
 */
public class StepsDetailActivity extends AppCompatActivity {
    private List<Steps> steps;
    private static RecipeData recipeData;
    private static int position;
    private boolean isSavedInstanceNull = true;
    @BindView(R.id.next_button_id)
    protected Button nextButton;
    @BindView(R.id.prev_button_id)
    protected Button previousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_detail);
        ButterKnife.bind(this);

        if(savedInstanceState !=null)
        {
            isSavedInstanceNull = false;
            if (savedInstanceState.containsKey(BakingAppUtil.POSITION_KEY) ){
                position = savedInstanceState.getInt(BakingAppUtil.POSITION_KEY);
            }
            if (savedInstanceState.containsKey(BakingAppUtil.RECIPE_DATA_KEY) ) {
                recipeData =(RecipeData)savedInstanceState.getSerializable(BakingAppUtil.RECIPE_DATA_KEY);
                steps = recipeData.getStepsList();
            }
        }
        else {
            final Intent intent = getIntent();
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                recipeData = (RecipeData) intent.getSerializableExtra(Intent.EXTRA_TEXT);

                steps = recipeData.getStepsList();
                position = intent.getIntExtra(BakingAppUtil.SELECTED_STEP_POSITION, 0);
            }
        }

        determineNextPreviousButtonVisibility();
    }

    private void determineNextPreviousButtonVisibility() {
        if (position == 0)
            previousButton.setVisibility(View.INVISIBLE);
        else
            previousButton.setVisibility(View.VISIBLE);

        if (position == steps.size() - 1)
            nextButton.setVisibility(View.INVISIBLE);
        else
            nextButton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.next_button_id)
    public void showTheNextStep() {
        DetailedStepsViewFragment.releasePlayer();
        if (position + 1 < steps.size()) {
            position += 1;
            setUpFragment(position);
            determineNextPreviousButtonVisibility();
        }
    }

    @OnClick(R.id.prev_button_id)
    public void showPreviousStep() {
        DetailedStepsViewFragment.releasePlayer();
        if (position - 1 >= 0) {
            position -= 1;
            setUpFragment(position);
            determineNextPreviousButtonVisibility();
        }
    }

    private void setUpFragment(int position) {
        BakingAppUtil.setCustomActionBar(this, steps.get(position).getShortDescription());
        BakingAppUtil.setUpDetailedStepsViewFragment(this, steps.get(position), isSavedInstanceNull);
        isSavedInstanceNull=true;
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
        setUpFragment(position);
    }

    @Override
    public void onBackPressed() {
        final Intent intent = new Intent(this, RecipeDetailViewActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, recipeData);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        BakingAppUtil.saveCurrentState(outState,position, recipeData);
        super.onSaveInstanceState(outState);
    }
}