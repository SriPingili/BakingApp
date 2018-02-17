package com.example.android.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.RVStepsShortViewAdapter;
import com.example.android.bakingapp.model.RecipeData;
import com.example.android.bakingapp.model.Steps;
import com.example.android.bakingapp.utilities.BakingAppUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment that displays the steps to the user. This includes the
 * short description of the steps
 */
public class StepsViewFragment extends Fragment {

    private RecipeData recipeData;
    private List<Steps> stepsList;

    public static RecyclerView stepsRecyclerView;

    private static OnStepsViewFragmentClickListener mlistener;

    public static void setOnStepSelected(int position) {
        mlistener.OnStepSelected(position);
    }

    public interface OnStepsViewFragmentClickListener {
        void OnStepSelected(int position);

        void onDisplayIngredientsListSelected();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mlistener = (OnStepsViewFragmentClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnStepsViewFragmentClickListener ");
        }
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail_view, container, false);
        ButterKnife.bind(this, rootView);
        stepsRecyclerView = rootView.findViewById(R.id.steps_recycler_view_id);

        stepsRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        stepsRecyclerView.setLayoutManager(linearLayoutManager);

        recipeData = (RecipeData) getArguments().getSerializable(BakingAppUtil.RECIPE_DATA_KEY);

        final RVStepsShortViewAdapter rvStepsShortViewAdapter = new RVStepsShortViewAdapter(recipeData.getStepsList(), getContext());
        stepsRecyclerView.setAdapter(rvStepsShortViewAdapter);

        final TextView ingredientsDisplayTextView = rootView.findViewById(R.id.ingredients_list_text_view_id);

        stepsList = recipeData.getStepsList();
        ingredientsDisplayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlistener.onDisplayIngredientsListSelected();
            }
        });

        return rootView;
    }
}
