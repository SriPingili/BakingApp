package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeDetailViewActivity;
import com.example.android.bakingapp.model.RecipeData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Recycler view adapter for displaying recipe names
 */
public class RVRecipeNamesAdapter extends RecyclerView.Adapter<RVRecipeNamesAdapter.RecipeNamesViewHoler> {

    private ArrayList<RecipeData> recipeDataArrayList;
    private Context context;

    public RVRecipeNamesAdapter(ArrayList<RecipeData> recipeDataArrayList, Context context) {
        this.recipeDataArrayList = recipeDataArrayList;
        this.context = context;
    }


    @Override
    public RecipeNamesViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_name_layout, parent, false);
        RecipeNamesViewHoler recipeNamesViewHoler = new RecipeNamesViewHoler(view);
        return recipeNamesViewHoler;
    }

    /**
     * Image source: google images
     */
    @Override
    public void onBindViewHolder(RecipeNamesViewHoler recipeNamesViewHoler, int position) {
        recipeNamesViewHoler.recipeNameTextView.setText(recipeDataArrayList.get(position).getName());
        if (recipeDataArrayList.get(position).getImage().isEmpty())
            Picasso.with(context).load(Uri.parse("https://cdn.pixabay.com/photo/2014/12/21/23/28/recipe-575434_960_720.png")).into(recipeNamesViewHoler.imageView);
        else
            Picasso.with(context).load(Uri.parse(recipeDataArrayList.get(position).getImage())).into(recipeNamesViewHoler.imageView);
    }

    @Override
    public int getItemCount() {
        return recipeDataArrayList.size();
    }

    public class RecipeNamesViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView recipeNameTextView;
        ImageView imageView;

        public RecipeNamesViewHoler(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cardView = itemView.findViewById(R.id.card_view_id);
            recipeNameTextView = itemView.findViewById(R.id.recipe_name_id);
            imageView = itemView.findViewById(R.id.recipe_image_view_id);
        }

        @Override
        public void onClick(View view) {
            int position = getPosition();
            final Intent intent = new Intent(context, RecipeDetailViewActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, recipeDataArrayList.get(position));
            context.startActivity(intent);
        }
    }
}