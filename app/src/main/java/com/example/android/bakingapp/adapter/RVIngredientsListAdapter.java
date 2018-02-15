package com.example.android.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredients;

import java.util.ArrayList;

/**
 *  Recycler view adapter for displaying ingredients
 */
public class RVIngredientsListAdapter extends RecyclerView.Adapter<RVIngredientsListAdapter.IngredientsNamesViewHoler> {


    private ArrayList<Ingredients> ingredientsArrayList;

    public RVIngredientsListAdapter(ArrayList<Ingredients> ingredientsArrayList) {
        this.ingredientsArrayList = ingredientsArrayList;
    }

    @Override
    public IngredientsNamesViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_view_layout, parent, false);
        IngredientsNamesViewHoler ingredientsNamesViewHoler = new IngredientsNamesViewHoler(view);
        return ingredientsNamesViewHoler;
    }

    @Override
    public void onBindViewHolder(IngredientsNamesViewHoler ingredientsNamesViewHoler, int position) {
        ingredientsNamesViewHoler.ingredientNameTextView.setText(ingredientsArrayList.get(position).getIngredient());
        ingredientsNamesViewHoler.ingredientQuantityTextView.setText(ingredientsArrayList.get(position).getQuantity()+"");
        ingredientsNamesViewHoler.ingredientMeasureTextView.setText(ingredientsArrayList.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        return ingredientsArrayList.size();
    }

    public class IngredientsNamesViewHoler extends RecyclerView.ViewHolder {

        TextView ingredientNameTextView;
        TextView ingredientQuantityTextView;
        TextView ingredientMeasureTextView;

        public IngredientsNamesViewHoler(View itemView) {
            super(itemView);
            ingredientNameTextView = itemView.findViewById(R.id.ingredient_name_text_view_id);
            ingredientQuantityTextView = itemView.findViewById(R.id.ingredient_quantity_text_view_id);
            ingredientMeasureTextView = itemView.findViewById(R.id.ingredient_measure_text_view_id);
        }
    }
}