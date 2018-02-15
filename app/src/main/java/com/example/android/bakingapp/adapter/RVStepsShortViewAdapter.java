package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.StepsViewFragment;
import com.example.android.bakingapp.model.Steps;

import java.util.List;

/**
 * Recycler view adapter for displaying steps short description
 */
public class RVStepsShortViewAdapter extends RecyclerView.Adapter<RVStepsShortViewAdapter.StepsShortViewViewHolder> {

    private List<Steps> stepsList;
    private Context context;

    public RVStepsShortViewAdapter(List<Steps> stepsList, Context context) {
        this.stepsList = stepsList;
        this.context = context;
    }

    @Override
    public StepsShortViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_view_layout, parent, false);
        StepsShortViewViewHolder stepsShortViewViewHolder = new StepsShortViewViewHolder(view);
        return stepsShortViewViewHolder;
    }

    @Override
    public void onBindViewHolder(StepsShortViewViewHolder stepsShortViewViewHolder, int position) {
        stepsShortViewViewHolder.stepsTextView.setText(stepsList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class StepsShortViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView stepsTextView;

        public StepsShortViewViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            cardView = itemView.findViewById(R.id.steps_card_view_id);
            stepsTextView = itemView.findViewById(R.id.step_view_id);
        }

        @Override
        public void onClick(View view) {
            StepsViewFragment.setOnStepSelected(getPosition());
        }
    }
}
