package com.example.android.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.RVIngredientsListAdapter;
import com.example.android.bakingapp.model.Ingredients;
import com.example.android.bakingapp.utilities.BakingAppUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment that displays the detailed ingredients list to the user
 */
public class DetailedIngredientsViewFragment extends Fragment {

    private ArrayList<Ingredients> ingredientsArrayList;
    public static RecyclerView ingredients_recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_ingredients_view, container, false);
        ingredients_recyclerView = rootView.findViewById(R.id.ingredients_recycler_view_id);

        ingredientsArrayList = (ArrayList<Ingredients>) getArguments().getSerializable(BakingAppUtil.INGREDIENTS_DATA_KEY);

        ingredients_recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ingredients_recyclerView.setLayoutManager(linearLayoutManager);

        RVIngredientsListAdapter rvIngredientsListAdapter = new RVIngredientsListAdapter(ingredientsArrayList);
        ingredients_recyclerView.setAdapter(rvIngredientsListAdapter);
        return rootView;
    }
}