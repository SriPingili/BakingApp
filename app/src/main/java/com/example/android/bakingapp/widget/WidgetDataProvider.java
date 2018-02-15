package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Ingredients;
import com.example.android.bakingapp.model.RecipeData;
import com.example.android.bakingapp.utilities.BakingAppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Data provider class
 *
 * used the Udacity classroom videos as reference to the
 * widget functionality
 */
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private RecipeData recipeData;
    private List<Ingredients> mCollection = new ArrayList<>();
    private Context mContext = null;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }

    private void initData() {
        mCollection.clear();
        recipeData = BakingAppUtil.getRecipeData(mContext);
        mCollection = recipeData.getIngredientsList();

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.ingredient_name_widget_text_view_id, recipeData.getName());
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCollection.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_ingredient_view_layout);
        view.setTextViewText(R.id.ingredient_text_view_widget_id, mCollection.get(position).getIngredient());

        final Intent fillInIntent = new Intent();
        view.setOnClickFillInIntent(R.id.ingredient_text_view_widget_id, fillInIntent);
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}